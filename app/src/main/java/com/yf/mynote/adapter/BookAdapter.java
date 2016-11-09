package com.yf.mynote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yf.mynote.R;
import com.yf.mynote.model.Book;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class BookAdapter extends BaseAdapter{
    private  List<Book> books;
    private  Context context;

    public BookAdapter(List<Book> books, Context context) {
        this.books=books;
        this.context=context;
    }


    public void   pauseLoad(){
        Glide.with(context).pauseRequests();
    }

    public  void  resumeLoad(){
        Glide.with(context).resumeRequests();
    }



    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.ui_book_item,null);
        }
        ImageView mBookImg = (ImageView) view.findViewById(R.id.bookImg);
        TextView mBookTitle = (TextView) view.findViewById(R.id.bookTitle);
        TextView mBookAuth = (TextView) view.findViewById(R.id.bookAuth);
        TextView mBookTime = (TextView) view.findViewById(R.id.bookTime);
        Book book=books.get(i);
        Glide.with(context).load(book.getImage()).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.def).into(mBookImg);
        mBookTitle.setText(book.getTitle());
        mBookAuth.setText(book.getAuto());
        mBookTime.setText(book.getTime());
        return view;
    }
}
