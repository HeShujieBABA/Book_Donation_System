package com.example.book_donation_system;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book_donation_system.db.Friends;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 何书杰 on 2017/10/17.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContent;
    private List<Friends> friendsList;
    public FriendsAdapter(List<Friends> friendsList){
        this.friendsList = friendsList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView friends_item_CircleView;
        TextView friends_item_qianMing;
        TextView friends_item_userName;
        public ViewHolder(View view){
            super(view);
            friends_item_CircleView = (CircleImageView) view.findViewById(R.id.friends_item_circleImageView);
            friends_item_userName = (TextView) view.findViewById(R.id.friends_item_userName);
            friends_item_qianMing = (TextView) view.findViewById(R.id.friends_item_qianMing);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
        }
    }
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContent == null){
            mContent = parent.getContext();
        }
        View myView = LayoutInflater.from(mContent).inflate(R.layout.friends_layout_item,parent,false);
        FriendsAdapter.ViewHolder viewHolder = new FriendsAdapter.ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {
        Friends friends = friendsList.get(position);
        holder.friends_item_userName.setText(friends.getFriends_item_userName());
        holder.friends_item_qianMing.setText(friends.getFriends_item_qianMing());
        Glide.with(mContent).load(friends.getFriends_item_Circleimageid()).into(holder.friends_item_CircleView);
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
