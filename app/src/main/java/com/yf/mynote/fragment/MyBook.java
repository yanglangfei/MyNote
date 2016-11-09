package com.yf.mynote.fragment;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yf.mynote.R;
import com.yf.mynote.activity.BookDetail;
import com.yf.mynote.adapter.BookAdapter;
import com.yf.mynote.model.Book;
import com.yf.mynote.utils.Contact;
import com.yf.mynote.utils.JsonpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */

public class MyBook extends Fragment {
    private ListView lvBooks;
    private BookAdapter adapter;
    private List<Book> books=new ArrayList<>();
    private View view;
    private ProgressBar bsPb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ui_book_list,container,false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,1,0,"查看");
        menu.add(0,2,0,"删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                //查看书籍


                break;
            case 2:
                //删除书籍

                break;
            default:
                break;
        }

        return true;

    }

    @Override
    public void onResume() {
        super.onResume();
        new GetBookList().execute();
    }

    private void initView() {
        lvBooks= (ListView) view.findViewById(R.id.gv_books);
        bsPb= (ProgressBar) view.findViewById(R.id.bsPb);
        adapter=new BookAdapter(books,getActivity());
        lvBooks.setAdapter(adapter);
        registerForContextMenu(lvBooks);
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),BookDetail.class);
                intent.putExtra("link",books.get(position).getLink());
                MyBook.this.startActivity(intent);
            }
        });
        lvBooks.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_IDLE:
                        adapter.resumeLoad();
                        break;
                    case SCROLL_STATE_FLING:
                        adapter.pauseLoad();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }



    class  GetBookList extends AsyncTask<String,Void,List<Book>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bsPb.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Book> doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(Contact.JIANSHU).get();
                List<Book> bs= JsonpUtils.getBookList(doc);
                books=bs;
                return  bs;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Book> s) {
            bsPb.setVisibility(View.GONE);
            adapter.setBooks(s);
            adapter.notifyDataSetChanged();
        }
    }


}
