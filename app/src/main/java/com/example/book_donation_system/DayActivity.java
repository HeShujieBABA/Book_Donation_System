package com.example.book_donation_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DayActivity extends AppCompatActivity {

    private RecyclerView rvTrace;
    private List<Trace> traceList = new ArrayList<>(10);
    private TraceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvTrace = (RecyclerView) findViewById(R.id.rvTrace);
        initData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void initData() {
        // 模拟一些假的数据
        traceList.add(new Trace("2017-10-25 17:48:00", "[武理航海楼] 我和我的儿子们一起打王者排位一起吃饭一起上课!"));
        traceList.add(new Trace("2017-10-25 14:13:00", "[武理H4-513] 我和我的儿子们一起打王者排位一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 13:01:04", "[武理主楼] 我和我的儿子们一起学习学习一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 12:19:47", "[武理航海楼] 我和我的儿子们一起写代码一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 11:12:44", "[武理H4-513] 我和我的儿子们一起看书一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-24 03:12:12", "[武理H4-513] 我和我的儿子们一起打王者排位一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-23 21:06:46", "[武理航海楼] 我和我的儿子们一起打王者排位一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-23 18:59:41", "[武理航海楼] 我和我的儿子们一起学习SQL一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-23 18:35:32", "[武理主楼] 我和我的儿子们一起学习Android一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 17:48:00", "[武理航海楼] 我和我的儿子们一起打王者排位一起吃饭一起上课!"));
        traceList.add(new Trace("2017-10-25 14:13:00", "[武理H4-513] 我和我的儿子们一起打王者排位一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 13:01:04", "[武理主楼] 我和我的儿子们一起学习学习一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 12:19:47", "[武理航海楼] 我和我的儿子们一起写代码一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-25 11:12:44", "[武理H4-513] 我和我的儿子们一起看书一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-24 03:12:12", "[武理H4-513] 我和我的儿子们一起打王者排位一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-23 21:06:46", "[武理航海楼] 我和我的儿子们一起打王者排位一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-23 18:59:41", "[武理航海楼] 我和我的儿子们一起学习SQL一起吃饭一起上课"));
        traceList.add(new Trace("2017-10-23 18:35:32", "[武理主楼] 我和我的儿子们一起学习Android一起吃饭一起上课"));
        adapter = new TraceListAdapter(this, traceList);
        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        rvTrace.setAdapter(adapter);
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
