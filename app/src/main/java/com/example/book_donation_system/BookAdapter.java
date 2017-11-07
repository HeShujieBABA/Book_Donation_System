package com.example.book_donation_system;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book_donation_system.db.Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 何书杰 on 2017/9/28.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    private Context mContent;
    private List<Book> mBookList;
    private MyItemClickListener listener;
    private double currentTime,oldTime = 0;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookImage;
        TextView bookName,bookWriter,bookUpdateTime;
        public ViewHolder(View view,final MyItemClickListener mListener){
            super(view);
            cardView = (CardView) view;
            bookImage = (ImageView) view.findViewById(R.id.lv_item_iv);
            bookName = (TextView) view.findViewById(R.id.tv_item_bookName);
            bookWriter = (TextView) view.findViewById(R.id.tv_item_bookWriter);
            bookUpdateTime = (TextView) view.findViewById(R.id.tv_item_UpdateTime);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }

    public BookAdapter (List<Book> bookList){
        mBookList = bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContent == null){
            mContent = parent.getContext();
        }
        View view = LayoutInflater.from(mContent).inflate(R.layout.book_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.bookName.setText("书名："+book.getBookName());
        holder.bookWriter.setText("捐赠者：何书杰");
        holder.bookUpdateTime.setText("捐赠时间："+getTime());
        Glide.with(mContent).load(book.getImageId()).into(holder.bookImage);
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
}

