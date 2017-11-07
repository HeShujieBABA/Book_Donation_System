package com.example.book_donation_system;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book_donation_system.db.Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 何书杰 on 2017/10/1.
 */

public class TabAdapter extends  RecyclerView.Adapter<TabAdapter.ViewHolder> {
    private Context mContent;
    private List<Book> mBookList;
    private MyItemClickListener listener;
    private double currentTime,oldTime = 0;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView book_image_item;
        TextView book_title_item;
        TextView book_update_time;
        TextView book_content_item;
        public ViewHolder(View view,final MyItemClickListener mListener){
            super(view);
            cardView = (CardView) view;
            book_image_item = (ImageView) view.findViewById(R.id.book_image_item);
            book_title_item = (TextView) view.findViewById(R.id.book_title_item);
            book_update_time = (TextView) view.findViewById(R.id.book_update_time);
            book_content_item = (TextView) view.findViewById(R.id.book_content_item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }

    public TabAdapter (List<Book> bookList){
        mBookList = bookList;
    }

    @Override
    public TabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContent == null){
            mContent = parent.getContext();
        }
        View view = LayoutInflater.from(mContent).inflate(R.layout.tab_item,parent,false);
        TabAdapter.ViewHolder viewHolder = new TabAdapter.ViewHolder(view,listener);
        return viewHolder;
    }

    public String getBookContent(){
        StringBuffer stringBuffer = new StringBuffer();
        String string  = "这里显示书的一部分内容！";
        for(int i=0;i<100;i++){
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
    @Override
    public void onBindViewHolder(TabAdapter.ViewHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.book_title_item.setText(book.getBookName());
        holder.book_update_time.setText(getTime());
        holder.book_content_item.setText(getBookContent());
        Glide.with(mContent).load(book.getImageId()).into(holder.book_image_item);
        //对控件实现监听
        //TODO
    }
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public String getTime(){
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        Date curDate = new Date();
        String string = format.format(curDate);
        if(currentTime - oldTime >= 200){
            currentTime = oldTime;
            return string;
        }else {
            return "";
        }
    }
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }
}

