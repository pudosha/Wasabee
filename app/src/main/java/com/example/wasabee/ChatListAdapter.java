package com.example.wasabee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wasabee.data.model.Chat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Chat> mChatList;
    private OnChatListener mOnChatListener;

    public ChatListAdapter(ArrayList<Chat> chats, OnChatListener onChatListener) {
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
        Chat chat = mChatList.get(position);
        ((ChatHolder) holder).bind(chat);
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        }

        void bind(Chat chat) {
            chatNameText.setText(chat.getChatName());
            lastMessageText.setText(chat.getLastMessage().substring(0, Math.min(40, chat.getLastMessage().toString().length())));//доделать
            timeText.setText(chat.getDate());
            senderNameText.setText(chat.getSenderID() + ":");
        }

        @Override
        public void onClick(View view) {
            onChatListener.onChatClick(getAdapterPosition());
        }
    }

    public interface OnChatListener{
        void onChatClick(int position);
    }

}
