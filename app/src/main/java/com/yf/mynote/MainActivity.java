package com.yf.mynote;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yf.mynote.activity.AddNoteActivity;
import com.yf.mynote.adapter.NoteAdapter;
import com.yf.mynote.db.MyDbHelp;
import com.yf.mynote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *   展示日志
 */
public class MainActivity extends Activity {
    private SQLiteDatabase writter;
    private List<Note> notesArray=new ArrayList<>();
    private NoteAdapter adapter;
    private ListView lvnote;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesArray.clear();
        initData();
    }

    private void initData() {
        MyDbHelp help=new MyDbHelp(this,"myNote.db",null,1);
        writter = help.getWritableDatabase();
        Cursor notes = writter.query("myNote", null, null, null, null, null, "id desc");
        int titleIndex=notes.getColumnIndex("title");
        int contentIndex=notes.getColumnIndex("content");
        int timeIndex=notes.getColumnIndex("time");
        int idIndex=notes.getColumnIndex("id");
        while (notes.moveToNext() ){
            String title= notes.getString(titleIndex);
            String time= notes.getString(timeIndex);
            String content= notes.getString(contentIndex);
            int id=notes.getInt(idIndex);
            Note note=new Note();
            note.setId(id);
            note.setTitle(title);
            note.setContent(content);
            note.setTime(time);
            notesArray.add(note);
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        lvnote=(ListView)findViewById(R.id.lv_note);
        adapter=new NoteAdapter(notesArray,this);
        lvnote.setAdapter(adapter);
         registerForContextMenu(lvnote);
        lvnote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position=i;
                return false;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       // menu.setHeaderTitle("选择");
        menu.add(0,1,0,"查看");
        menu.add(0,2,0,"删除");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  1:
                //查看
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,AddNoteActivity.class);
                intent.putExtra("id",notesArray.get(position).getId());
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case 2:
                 //删除
                int result = writter.delete("myNote", "id=?", new String[]{notesArray.get(position).getId() + ""});
                if(result>0){
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                    notesArray.clear();
                    initData();
                }else{
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    public  void  onAdd(View view){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,AddNoteActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }
}
