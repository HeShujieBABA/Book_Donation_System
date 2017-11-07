package com.example.book_donation_system;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_donation_system.db.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CenterActivity extends AppCompatActivity {

    private Book[] books = {new Book("Book_1",R.drawable.book_1),
            new Book("Book_2",R.drawable.book_2),
            new Book("Book_3",R.drawable.book_3),
            new Book("Book_4",R.drawable.book_4),
            new Book("Book_5",R.drawable.book_5)};

    private List<Book> bookList = new ArrayList<>();
    private BookAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initBooks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                startActivity(new Intent(CenterActivity.this,BookContentActivity.class));
            }
        });
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
        inflater.inflate(R.menu.add_friends, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.add_friends:
                final EditText editText = new EditText(CenterActivity.this);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CenterActivity.this);
                mBuilder.setIcon(R.drawable.user_icon).setTitle("好友昵称：").setView(editText).setCancelable(false)
                        .setPositiveButton("取消",null).setNegativeButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CenterActivity.this,"已成功向"+editText.getText().toString()+"发送请求",Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.show();
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
