package com.example.book_donation_system;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.example.book_donation_system.R.id.login_toolbar;

public class MessageActivity extends AppCompatActivity {

    private String[] titles = {"朋友私信","系统通知"};
    private TabLayout Message_tablayout;
    private ViewPager Message_viewpager;
    private MyMessageAdapter myMessageAdapter;
    private List<Fragment> list;
    private Toolbar login_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        login_toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        login_toolbar.setTitle("");
        setSupportActionBar(login_toolbar);
        initView();
        addList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myMessageAdapter = new MyMessageAdapter(getSupportFragmentManager());
        Message_viewpager.setAdapter(myMessageAdapter);
        Message_tablayout.setupWithViewPager(Message_viewpager);
    }
    public void addList(){
        list = new ArrayList<>();
        list.add(new Message_Fragment_1());
        list.add(new Message_Fragment_2());
    }
    public void initView(){
        Message_tablayout = (TabLayout) findViewById(R.id.message_tablayout);
        Message_viewpager = (ViewPager) findViewById(R.id.message_viewpager);
    }

    public class MyMessageAdapter extends FragmentPagerAdapter{
        public MyMessageAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }
}
