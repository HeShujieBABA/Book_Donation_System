package com.example.book_donation_system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_donation_system.db.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 何书杰 on 2017/10/13.
 */

public class Message_Fragment_2 extends Fragment {
    private Message[] messages = {new Message("通知",R.drawable.message_news,R.drawable.item_right),
    new Message("回复",R.drawable.message_notes,R.drawable.item_right)};
    private List<Message> messageList = new ArrayList<>();
    private SwipeRefreshLayout MessageSwipeRefreshLayout;
    private RecyclerView MessageRecyclerView;
    private MessageTabAdapter messageTabAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_tab2_demo,container,false);
     /**   initMessage();
        MessageRecyclerView = (RecyclerView) view.findViewById(R.id.Message_recyclerView);
        MessageSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Message_SwipeRefreshLayout);

        MessageRecyclerView.setHasFixedSize(true);
        messageTabAdapter = new MessageTabAdapter(messageList);
        MessageRecyclerView.setAdapter(messageTabAdapter);

        MessageSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        MessageSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMessages();
            }
        }); **/
        return view;
    }
    public void refreshMessages(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initMessage();
                    messageTabAdapter.notifyDataSetChanged();
                    MessageSwipeRefreshLayout.setRefreshing(false);
                }
            }).start();
    }
    public void initMessage(){
        messageList.clear();
        for(int i=0;i<2;i++){
            messageList.add(messages[i]);
        }
    }
}
