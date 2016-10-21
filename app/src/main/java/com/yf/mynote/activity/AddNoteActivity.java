package com.yf.mynote.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yf.mynote.R;
import com.yf.mynote.db.MyDbHelp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/8.
 */

public class AddNoteActivity extends Activity {
    private EditText input_title;
    private  EditText input_content;
    private SQLiteDatabase writter;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private int type;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_add);
        initView();
    }

    private void initView() {
        input_title= (EditText) findViewById(R.id.input_title);
        input_content= (EditText) findViewById(R.id.input_content);
        MyDbHelp help=new MyDbHelp(this,"myNote.db",null,1);
        writter = help.getWritableDatabase();
        type=getIntent().getIntExtra("type",1);
        if(type==1){
            //添加
        }else {
            //查看
            id=getIntent().getIntExtra("id",0);
            queryNote(id);
        }
    }

    private void queryNote(int id) {
        Cursor curous = writter.rawQuery("select * from myNote where id=?", new String[]{id + ""});
        if(curous.moveToFirst()){
            int titleIndex=curous.getColumnIndex("title");
            int contentIndex=curous.getColumnIndex("content");
            int timeIndex=curous.getColumnIndex("time");
            String title= curous.getString(titleIndex);
            String time= curous.getString(timeIndex);
            String content= curous.getString(contentIndex);
            input_content.setText(content);
            input_title.setText(title);
        }

    }

    public  void onAdd(View view){
        String title = input_title.getText().toString();
        String content = input_content.getText().toString();
        if(title.length()<=0){
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.length()<=0){
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("time",sdf.format(new Date()));
        if(type==1){
            //添加
            long result = writter.insert("myNote", "id", values);
            if(result>0){
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }else{
            //修改
            long result= writter.update("myNote",values,"id=?",new String[]{id+""});
            if(result>0){
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }
        this.finish();
    }
}
