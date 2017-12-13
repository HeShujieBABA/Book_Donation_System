package com.example.book_donation_system.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_donation_system.BookContentActivity;
import com.example.book_donation_system.MyItemClickListener;
import com.example.book_donation_system.R;
import com.example.book_donation_system.TabAdapter;
import com.example.book_donation_system.db.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 何书杰 on 2017/9/30.
 */

public class Tab_Fragment_1 extends android.support.v4.app.Fragment {
    private Book[] books = {new Book("Book_1",R.drawable.book_1),
            new Book("Book_2",R.drawable.book_2),
            new Book("Book_3",R.drawable.book_3),
            new Book("Book_4",R.drawable.book_4),
            new Book("Book_5",R.drawable.book_5)};

    private List<Book> bookList = new ArrayList<>();
    private TabAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewTab_1 = inflater.inflate(R.layout.tab_1, container, false);
        initBooks();
        RecyclerView recyclerView = (RecyclerView) viewTab_1.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new TabAdapter(bookList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                startActivity(new Intent(getContext(),BookContentActivity.class));
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) viewTab_1.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBooks();
            }
        });
        return viewTab_1;
    }
    public void initBooks(){
        bookList.clear();
        for(int i=0;i<10;i++){
            Random random = new Random();
            int index = random.nextInt(books.length);
            bookList.add(books[index]);
        }
    }
    public void refreshBooks(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                initBooks();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }).start();
    }
}
