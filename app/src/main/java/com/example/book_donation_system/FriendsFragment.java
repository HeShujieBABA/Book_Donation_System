package com.example.book_donation_system;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.book_donation_system.db.Book;
import com.example.book_donation_system.db.Friends;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 何书杰 on 2017/10/14.
 */

public class FriendsFragment extends android.app.Fragment {
    private Friends[] friends = {new Friends("何书杰","stay hungry,stay,foolish.",R.drawable.user_icon),
            new Friends("陈湘","stay hungry,stay,foolish.",R.drawable.user_icon),
            new Friends("政委","stay hungry,stay,foolish.",R.drawable.user_icon),
            new Friends("小磊","stay hungry,stay,foolish.",R.drawable.user_icon),
            new Friends("Demo","stay hungry,stay,foolish.",R.drawable.user_icon)};
    private List<Friends> friendsList = new ArrayList<>();
    private FriendsAdapter adapter;
    private SwipeRefreshLayout friends_refreshLayout;
    private RecyclerView friends_recyclerView;
    public static FriendsFragment newInstance(String param1) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public FriendsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        initFriends();
        friends_refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.friends_refreshLayout);
        friends_recyclerView = (RecyclerView) view.findViewById(R.id.friends_recycleView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        friends_recyclerView.setLayoutManager(layoutManager);
        friends_recyclerView.setHasFixedSize(true);
        adapter = new FriendsAdapter(friendsList);
        friends_recyclerView.setAdapter(adapter);

        friends_refreshLayout.setColorSchemeResources(R.color.colorAccent);
        friends_refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFriends();
            }
        });


        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        return view;
    }
    public void initFriends(){
            friendsList.clear();
            for(int i=0;i<50;i++){
                Random random = new Random();
                int index = random.nextInt(friends.length);
                friendsList.add(friends[index]);
            }
    }
    public void refreshFriends(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                initFriends();
                adapter.notifyDataSetChanged();
                friends_refreshLayout.setRefreshing(false);
            }
        }).start();
    }

}
