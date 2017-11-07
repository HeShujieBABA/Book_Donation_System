package com.example.book_donation_system;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class FriendsActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private FriendsFragment mFriendsFragment;
    private FindFragment mFindFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.friends_user, "好友").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.friends_eye, "发现").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.friends_love, "爱好").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.friends_book, "图书").setActiveColor(R.color.colorAccent))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFriendsFragment = FriendsFragment.newInstance("好友");
        transaction.replace(R.id.tb, mFriendsFragment);
        transaction.commit();
    }
    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mFriendsFragment == null) {
                    mFriendsFragment = mFriendsFragment.newInstance("好友");
                }
                transaction.replace(R.id.tb, mFriendsFragment);
                break;
            case 1:
                if(mFindFragment == null){
                    mFindFragment = mFindFragment.newInstance("发现");
                }
                transaction.replace(R.id.tb,mFindFragment);
                break;
            case 2:

                break;
            case 3:

                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
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
