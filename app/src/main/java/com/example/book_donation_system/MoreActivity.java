package com.example.book_donation_system;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvQuestion;
    private LinearLayout settingLayout,helpLayout,giftLayout,feebbackLayout,telephoneLayout;
    private Button btnFBcancel,btnFBfeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        settingLayout.setOnClickListener(this);
        helpLayout.setOnClickListener(this);
        giftLayout.setOnClickListener(this);
        feebbackLayout.setOnClickListener(this);
        telephoneLayout.setOnClickListener(this);
        tvQuestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settingLayout:
                break;
            case R.id.helpLayout:
                startActivity(new Intent(MoreActivity.this,AboutActivity.class));
                break;
            case R.id.giftLayout:
                break;
            case R.id.feedbackLayout:
                show_FeedBack_Dialog();
                break;
            case R.id.telephoneLayout:
                break;
            case R.id.tvQuestion:
                showQuestions();
                break;
            default:
                break;
        }
    }
    public void showQuestions(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, new String[]{
                "Question 1",
                "Question 2",
                "Question 3",
                "Question 4"}
        );
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                    }
                })
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }
    public void show_FeedBack_Dialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.feedback_layout,null);
        btnFBfeedback = (Button) view.findViewById(R.id.btFBfeedback);
        btnFBcancel = (Button) view.findViewById(R.id.btFBcancel);
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
                Toast.makeText(MoreActivity.this,"反馈成功，程序员正在卖力修复！",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnFBcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
    public void initView(){
        settingLayout = (LinearLayout) findViewById(R.id.settingLayout);
        helpLayout = (LinearLayout) findViewById(R.id.helpLayout);
        giftLayout = (LinearLayout)findViewById(R.id.giftLayout);
        feebbackLayout = (LinearLayout) findViewById(R.id.feedbackLayout);
        telephoneLayout = (LinearLayout) findViewById(R.id.telephoneLayout);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
    }
}
