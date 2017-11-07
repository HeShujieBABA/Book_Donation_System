package com.example.book_donation_system;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static cn.jpush.android.api.JPushInterface.a.v;

public class LoginActivity extends AppCompatActivity {

    private EditText mPassword,mUsername;
    private Button btLogin,btRegister;
    private TextView tvForgetPassword;
    private Button btnREregister,btnREcancel;
    private Toolbar login_toolbar;
    private long firstTime,sceondTime,spaceTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        ActivityCollector.addActivity(this);

        btLogin = (Button) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);
        login_toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        login_toolbar.setTitle("");
        setSupportActionBar(login_toolbar);
        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SelfCenterActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        tvForgetPassword = (TextView) findViewById(R.id.tvForgetPass);

        btRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomViewDialog();
            }
        });
        tvForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"此功能尚未实现，敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomViewDialog() {
        // 第一种方式 将布局文件转换为view
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.register_layout, null);
        btnREregister = (Button) view.findViewById(R.id.btREregister);
        btnREcancel = (Button) view.findViewById(R.id.btREcancel);
       final AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.user_icon)
                .setTitle("用户注册")
                .setView(view)
                .setCancelable(false);
        final AlertDialog dialog = builder.show();
        btnREregister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"注册成功，快去登录吧~",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnREcancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //TODO
                dialog.dismiss();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - sceondTime;
            sceondTime = firstTime;
            if (spaceTime > 2000) {
                Toast.makeText(LoginActivity.this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
            }else
                LoginActivity.this.finish();
        }
            return true;
    }
}

