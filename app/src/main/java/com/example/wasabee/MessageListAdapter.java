package com.example.wasabee;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wasabee.data.model.Message;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_SYSTEM = 3;

    private Context mContext;
    private List<Message> mMessageList;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    public MessageListAdapter(List<Message> messageList, Context context) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);
        String preferenceFile = mContext.getString(R.string.preference_file_key);
        String username = mContext.getSharedPreferences(preferenceFile, 0).getString("username", null);
        String systemUsername = "bunbun";
        if (message.getUsername().equals(username)) return VIEW_TYPE_MESSAGE_SENT;
        else {
            if (message.getUsername().equals(systemUsername)) return VIEW_TYPE_MESSAGE_SYSTEM;
            else return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        }
        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_system, parent, false);
        return new SystemMessageHolder(view);

    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_SYSTEM:
                Log.d("checkingMessage", message.toString());
                ((SystemMessageHolder) holder).bind(message);
                break;
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
            timeText.setText(dateFormatter.format(message.getDate()));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.time);
            nameText = (TextView) itemView.findViewById(R.id.sender_name);
        }

        void bind(Message message) {
/*
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            if (minute < 10)
                timeText.setText(hour + ":0" + minute);
            else
                timeText.setText(hour + ":" + minute);
*/
            messageText.setText(message.getMessage());
            timeText.setText(dateFormatter.format(message.getDate()));
            nameText.setText(message.getUsername());
        }
    }

    private class SystemMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SystemMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
        }
    }
}
