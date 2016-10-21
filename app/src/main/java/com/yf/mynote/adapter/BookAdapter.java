package com.yf.mynote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yf.mynote.R;
import com.yf.mynote.model.Book;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class BookAdapter extends BaseAdapter{
    private final List<Book> books;
    private final Context context;

    public BookAdapter(List<Book> books, Context context) {
        this.books=books;
        this.context=context;
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
        return view;
    }
}
