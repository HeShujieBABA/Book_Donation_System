package com.example.book_donation_system.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_donation_system.MainActivity;
import com.example.book_donation_system.R;
import com.example.book_donation_system.db.User;
import com.example.book_donation_system.unity.ActivityCollector;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText mPassword,mUsername,etREName,etREPass,etRESurePass;
    private Button btLogin,btRegister;
    private TextView tvForgetPassword;
    private Button btnREregister,btnREcancel;
    private Toolbar login_toolbar;
    private long firstTime,sceondTime,spaceTime;
    private String APP_ID =  "a4cbdefd13f0338e5a60df048b8fd08b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        ActivityCollector.addActivity(this);

        Bmob.initialize(LoginActivity.this,APP_ID);

        mUsername = (EditText) findViewById(R.id.etName);
        mPassword = (EditText) findViewById(R.id.etPass);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);
        login_toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        login_toolbar.setTitle("");


        setSupportActionBar(login_toolbar);
        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null) {
                            for (int i = 0; i < list.size(); i++) {
                                String _mUsername = mUsername.getText().toString();
                                String _mPassword = mPassword.getText().toString();
                                if (_mUsername.equals(list.get(i).getUsername().toString()) && _mPassword.equals(list.get(i).getPassword().toString())) {
                                    Log.d(TAG,list.get(i).getObjectId());
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            }
                        }
                    }
                });
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
                //Toast.makeText(LoginActivity.this,"此功能尚未实现，敬请期待",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showCustomViewDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.register_layout, null);
        btnREregister = (Button) view.findViewById(R.id.btREregister);
        btnREcancel = (Button) view.findViewById(R.id.btREcancel);

        etRESurePass = (EditText) view.findViewById(R.id.etRESurePass); //输入密码
        etREPass = (EditText) view.findViewById(R.id.etREPass); //确认密码
        etREName = (EditText) view.findViewById(R.id.etREName); //用户名

       final AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.user_icon)
                .setTitle("用户注册")
                .setView(view)
                .setCancelable(false);
        final AlertDialog dialog = builder.show();
        btnREregister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String _etREName = etREName.getText().toString();
                String _etREPass = etREPass.getText().toString();
                String _etRESurePass = etRESurePass.getText().toString();
                //处理注册信息
                if(_etREName == null){
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }
                if(!_etREPass.equals(_etRESurePass)){
                    Toast.makeText(LoginActivity.this,"请重新输入密码",Toast.LENGTH_SHORT).show();
                    etREPass.setText("");
                    etRESurePass.setText("");
                }
                if(_etREName!=null && _etREPass.equals(_etRESurePass)){
                    //插入Bmob云数据库
                    User user = new User();
                    user.setUsername(_etREName);
                    user.setPassword(_etREPass);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){
                                //Toast.makeText(LoginActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this,"注册成功，快去登录吧~",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else{
                                //Toast.makeText(LoginActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        btnREcancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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

