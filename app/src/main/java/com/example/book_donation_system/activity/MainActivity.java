package com.example.book_donation_system.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.book_donation_system.*;
import com.example.book_donation_system.AboutActivity;
import com.example.book_donation_system.CenterActivity;
import com.example.book_donation_system.db.FeedBack;
import com.example.book_donation_system.fragment.Tab_Fragment_1;
import com.example.book_donation_system.unity.ActivityCollector;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by 何书杰 on 2017/9/29.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ivImageView_1,ivImageView_2;
    private PopupWindow popupWindow;
    private LinearLayout layout;
    private ListView listView;
    private String[] more = {"个人中心","关于我们"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RollPagerView mRollPagerView;
    private Button btnQuit,btnCancel;
    private List<Fragment> list;
    private MyAdapter adapter;
    private Toolbar toolbar;
    private String[] titles = {"最新", "文学", "小说","漫画","军事","国学","传媒","艺术"};
    private String[] items ={"拍照","从相册选择"};
    private EditText etFeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());

        ActivityCollector.addActivity(this);
        //实例化
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        mRollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mRollPagerView.setPlayDelay(2000);
        mRollPagerView.setAnimationDurtion(500);
        mRollPagerView.setAdapter(new TestNormalAdapter());
        mRollPagerView.setHintView(new ColorPointHintView(this,Color.YELLOW,Color.WHITE));

        ivImageView_1 = (ImageView) findViewById(R.id.ivImageView_1);
        ivImageView_2 = (ImageView )findViewById(R.id.ivImageView_2);

        ivImageView_1.setOnClickListener(this);
        ivImageView_2.setOnClickListener(this);
        list = new ArrayList<>();
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        list.add(new Tab_Fragment_1());
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
        };
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }
        @Override
        public int getCount() {
            return imgs.length;
        }
    }
    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
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
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivImageView_1:
                show_FeedBack_Dialog();
                break;
            case R.id.ivImageView_2:
                int x = getWindowManager().getDefaultDisplay().getWidth();
                int y = (toolbar.getHeight()-13)*3/2;
                showMorePopupWindow(x,y);
                break;
            default:
                break;
        }
    }

    public void show_FeedBack_Dialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.feedback_layout,null);
        Button btnFBfeedback = (Button) view.findViewById(R.id.btFBfeedback);
        Button btnFBcancel = (Button) view.findViewById(R.id.btFBcancel);
        etFeedBack = (EditText) view.findViewById(R.id.etFeedBack);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setIcon(R.drawable.user_icon)
                .setTitle("用户反馈")
                .setView(view)
                .create();
        final AlertDialog dialog = builder.show();
        btnFBfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBack feedBack = new FeedBack();
                feedBack.setFeedback(etFeedBack.getText().toString());
                feedBack.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Toast.makeText(MainActivity.this,"反馈成功，程序员正在卖力修复！",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        btnFBcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showMorePopupWindow(int x, int y) {
        layout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(
                R.layout.popupwindow_dialog_layout, null);
        listView = (ListView) layout.findViewById(R.id.lv_dialog);
        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.text, R.id.tv_text, more));

        popupWindow = new PopupWindow(MainActivity.this);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow
                .setWidth(getWindowManager().getDefaultDisplay().getWidth() / 3);
        popupWindow.setHeight(230);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        popupWindow.showAtLocation(findViewById(R.id.toolbar), Gravity.LEFT
                | Gravity.TOP, x, y);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (arg2){
                    case 0:
                        startActivity(new Intent(MainActivity.this,CenterActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        break;
                    default:
                        break;
                }
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.quit_app_layout, null);
            btnQuit = (Button) view.findViewById(R.id.btQuit);
            btnCancel  = (Button) view.findViewById(R.id.btnCancel);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view).setCancelable(false);
            final AlertDialog dialog = builder.show();
            btnQuit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCollector.finishAll();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return false;
        }else {
            return super.onKeyDown(keyCode,event);
        }

    }
}
