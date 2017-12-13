package com.example.book_donation_system.activity;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.book_donation_system.R;
import com.example.book_donation_system.adapter.BookAdapter;
import com.example.book_donation_system.db.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CenterActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private Toolbar center_toolbar;
    private Book[] books = {new Book("Book_1",bitmap,"",""),
            new Book("Book_2",bitmap,"",""),
            new Book("Book_3",bitmap,"",""),
            new Book("Book_4",bitmap,"",""),
            new Book("Book_5",bitmap,"","")};

    private List<Book> bookList = new ArrayList<>();
    private BookAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_layout);
        center_toolbar = (Toolbar) findViewById(R.id.center_toolbar);
        center_toolbar.setTitle("");
        setSupportActionBar(center_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initBooks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBooks();
            }
        });

    }

    public void initBooks(){
        bookList.clear();
        for(int i=0;i<50;i++){
            Random random = new Random();
            int index = random.nextInt(books.length);
            bookList.add(books[index]);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    public void refreshBooks(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){

                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBooks();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
