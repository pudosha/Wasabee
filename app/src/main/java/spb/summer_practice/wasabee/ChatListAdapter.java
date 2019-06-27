package spb.summer_practice.wasabee;

import android.content.Context;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import spb.summer_practice.wasabee.R;
import spb.summer_practice.wasabee.data.model.ChatPreview;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ChatPreview> mChatList;
    private OnChatListener mOnChatListener;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    public ChatListAdapter(ArrayList<ChatPreview> chats, OnChatListener onChatListener) {
        this.mChatList = chats;
        this.mOnChatListener = onChatListener;
    }

    /*delete this..?
    public ChatListAdapter(ArrayList<Chat> chatList, Context context) {
        mContext = context;
        mChatList = chatList;
    }
    */
    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int randomInt) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatHolder(view, mOnChatListener);
    }

    // Passes the chat object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        ChatPreview chat = mChatList.get(position);
        ((ChatHolder) holder).bind(chat);
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView chatNameText, lastMessageText, timeText, senderNameText;
        OnChatListener onChatListener;

        public ChatHolder(@NonNull View itemView, OnChatListener onChatListener) {
            super(itemView);
            chatNameText = (TextView) itemView.findViewById(R.id.chat_name);
            lastMessageText = (TextView) itemView.findViewById(R.id.last_message);
            timeText = (TextView) itemView.findViewById(R.id.time);
            senderNameText = (TextView) itemView.findViewById(R.id.sender_name);

            this.onChatListener = onChatListener;
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(ChatPreview chat) {
            chatNameText.setText(chat.getChat().getChatName());
            String lastMessage = chat.getMessage();
            int indexOfEnter = lastMessage.indexOf('\n');
            int maxMessageLength = 50;

            if (lastMessage.length() < maxMessageLength && indexOfEnter < 0)
                lastMessageText.setText(chat.getMessage());

            if (indexOfEnter >= 0 && indexOfEnter < maxMessageLength)
                lastMessageText.setText(chat.getMessage().substring(0, indexOfEnter) + "...");

            if (lastMessage.length() >= maxMessageLength && (indexOfEnter < 0 || indexOfEnter > maxMessageLength))
                lastMessageText.setText(chat.getMessage().substring(0, maxMessageLength) + "...");

            timeText.setText(dateFormatter.format(chat.getDate()));
            senderNameText.setText(chat.getUsername() + ":");
        }

        @Override
        public void onClick(View view) {
            onChatListener.onChatClick(getAdapterPosition());
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) { }


        /*
        public boolean onContextItemSelected (MenuItem item) {
            //Toast.makeText(this, "oheloo", Toast.LENGTH_SHORT).show();
            Toast.makeText(ChatListActivity, "This is my Toast message!",
                    Toast.LENGTH_LONG).show();
            return true;
        }*/

    }


    public interface OnChatListener {
        void onChatClick(int position);
    }
}
