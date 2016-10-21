package com.yf.mynote.activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import com.yf.mynote.R;
import com.yf.mynote.adapter.BookAdapter;
import com.yf.mynote.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */

public class MyBook extends Activity {
    private GridView lvBooks;
    private BookAdapter adapter;
    private List<Book> books=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_book_list);
        initView();
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
    protected void onResume() {
        super.onResume();
        initBooks();
    }

    private void initBooks() {


    }

    private void initView() {
        lvBooks= (GridView) findViewById(R.id.gv_books);
        adapter=new BookAdapter(books,this);
        lvBooks.setAdapter(adapter);
        registerForContextMenu(lvBooks);
    }

    public  void  onAdd(View view){
        //今日电子书商店

    }
}
