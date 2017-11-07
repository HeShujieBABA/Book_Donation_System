package com.example.book_donation_system;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book_donation_system.db.Message;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 何书杰 on 2017/10/13.
 */

public class MessageTabAdapter extends RecyclerView.Adapter<MessageTabAdapter.ViewHolder> {
    private Context mContext;
    private List<Message> messageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Message_item_right;
        CircleImageView Message_item_CircleView;
        TextView Message_item_content;
        public ViewHolder(View view){
            super(view);
            Message_item_CircleView = (CircleImageView) view.findViewById(R.id.Message_item_circleImageView);
            Message_item_right = (ImageView) view.findViewById(R.id.Message_item_right);
            Message_item_content = (TextView) view.findViewById(R.id.Message_item_content);
        }
    }

    @Override
    public void onBindViewHolder(MessageTabAdapter.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.Message_item_content.setText(message.getMessageContent());
        Glide.with(mContext).load(message.getMessage_Id_1()).into(holder.Message_item_CircleView);
        Glide.with(mContext).load(message.getMessage_Id_2()).into(holder.Message_item_right);
    }
    public MessageTabAdapter(List<Message>messageList){
        this.messageList = messageList;
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public MessageTabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);
        MessageTabAdapter.ViewHolder viewHolder = new MessageTabAdapter.ViewHolder(view);
        return viewHolder;
    }
}
