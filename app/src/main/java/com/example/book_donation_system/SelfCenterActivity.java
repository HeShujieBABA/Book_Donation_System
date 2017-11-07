package com.example.book_donation_system;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_donation_system.db.Book;
import com.example.book_donation_system.db.User;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.litepal.crud.DataSupport;

import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 何书杰 on 2017/9/29.
 */

public class SelfCenterActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private LinearLayout layout;
    private ListView listView;
    private String[] more = {"个人中心","关于我们"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RollPagerView mRollPagerView;
    private SwipeRefreshLayout refreshLayout;
    private Button btnQuit,btnCancel;
    private List<Fragment> list;
    private MyAdapter adapter;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private String[] titles = {"最新", "文学", "小说","漫画","军事","国学","传媒","艺术"};
    private String[] items ={"拍照","从相册选择"};
    private CircleImageView circleImage;
    private TextView qianming,username;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slef_center_layout);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        ActivityCollector.addActivity(this);
        //实例化
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRollPagerView.setPlayDelay(2000);
        mRollPagerView.setAnimationDurtion(500);
        mRollPagerView.setAdapter(new TestNormalAdapter());
        mRollPagerView.setHintView(new ColorPointHintView(this,Color.YELLOW,Color.WHITE));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_home);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        break;
                    case R.id.nav_pics:
                        startActivity(new Intent(SelfCenterActivity.this,FriendsActivity.class));
                        break;
                    case R.id.schedule:
                        startActivity(new Intent(SelfCenterActivity.this,DayActivity.class));
                        break;
                    case R.id.alarm_clock:
                        startActivity(new Intent(SelfCenterActivity.this,AlarmClockActivity.class));
                        break;
                    case R.id.envelope:
                        startActivity(new Intent(SelfCenterActivity.this,MessageActivity.class));
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        final View mView = navView.inflateHeaderView(R.layout.nav_header);
        circleImage = (CircleImageView) mView.findViewById(R.id.circleView);
        qianming = (TextView) mView.findViewById(R.id.qianming);

        List<User> users = DataSupport.findAll(User.class);
        for(User user:users) {
            if (user.getUserQingMing() != null) {
                qianming.setText(user.getUserQingMing());
            }
        }
        username = (TextView) mView.findViewById(R.id.username);
        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelfCenterActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                takePics();
                                break;
                            case 1:
                                choosePics();
                               break;
                            default:
                                break;
                        }
                    }
                }).create().show();
            }
        });

        qianming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editText = new EditText(SelfCenterActivity.this);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelfCenterActivity.this);
                mBuilder.setTitle("修改签名").setView(editText).setCancelable(false)
                        .setPositiveButton("取消",null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = new User();
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(SelfCenterActivity.this,"不能设置为空，写点东西吧~",Toast.LENGTH_SHORT).show();
                        }else {
                            qianming.setText(editText.getText().toString());
                            user.setUserQingMing(editText.getText().toString());
                            user.save();
                        }
                    }
                });
                mBuilder.show();
            }

        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelfCenterActivity.this,"用户名不可修改~",Toast.LENGTH_SHORT).show();
            }
        });
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

    public void choosePics(){
        if(ContextCompat.checkSelfPermission(SelfCenterActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SelfCenterActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            openAlbum();
        }
    }
    public void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(SelfCenterActivity.this,"请设置相关权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //将图片以二进制的形式存储
    private byte[]img(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public void takePics(){
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri = FileProvider.getUriForFile(SelfCenterActivity.this,"com.example.book_donation_system.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode ==RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        circleImage.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode ==RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                       handleImageOnKitKat(data);
                    }else {
                        handleImageforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(SelfCenterActivity.this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            circleImage.setImageBitmap(bitmap);
        }else {
            Toast.makeText(SelfCenterActivity.this,"获取图片失败",Toast.LENGTH_SHORT).show();
        }
    }

    private void handleImageforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public void show_FeedBack_Dialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.feedback_layout,null);
        Button btnFBfeedback = (Button) view.findViewById(R.id.btFBfeedback);
        Button btnFBcancel = (Button) view.findViewById(R.id.btFBcancel);
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
                Toast.makeText(SelfCenterActivity.this,"反馈成功，程序员正在卖力修复！",Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId())
        {
           /** case R.id.add_item:
                startActivity(new Intent(SelfCenterActivity.this,CenterActivity.class));
                break;
            case R.id.more_item:
             //   Toast.makeText(SelfCenterActivity.this,"此功能尚未实现，敬请期待",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelfCenterActivity.this,AboutActivity.class));
                break;**/
            case R.id.showMore:
                
                int x = getWindowManager().getDefaultDisplay().getWidth();
                int y = (toolbar.getHeight()-13)*3/2;
                showMorePopupWindow(x,y);
                break;
           /** case R.id.search:
                Toast.makeText(SelfCenterActivity.this,"页面刷新成功",Toast.LENGTH_SHORT).show();
                break;**/
            case R.id.showEdit:
                show_FeedBack_Dialog();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    public void showMorePopupWindow(int x, int y) {
        layout = (LinearLayout) LayoutInflater.from(SelfCenterActivity.this).inflate(
                R.layout.popupwindow_dialog, null);
        listView = (ListView) layout.findViewById(R.id.lv_dialog);
        listView.setAdapter(new ArrayAdapter<String>(SelfCenterActivity.this,
                R.layout.text, R.id.tv_text, more));

        popupWindow = new PopupWindow(SelfCenterActivity.this);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow
                .setWidth(getWindowManager().getDefaultDisplay().getWidth() / 3);
        popupWindow.setHeight(230);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        popupWindow.showAtLocation(findViewById(R.id.toolbar), Gravity.LEFT
                | Gravity.TOP, x, y);//需要指定Gravity，默认情况是center.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (arg2){
                    case 0:
                        startActivity(new Intent(SelfCenterActivity.this,CenterActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(SelfCenterActivity.this,AboutActivity.class));
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
