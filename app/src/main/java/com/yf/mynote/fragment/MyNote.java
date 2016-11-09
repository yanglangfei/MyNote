package com.yf.mynote.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yf.mynote.MainActivity;
import com.yf.mynote.R;
import com.yf.mynote.activity.AddNoteActivity;
import com.yf.mynote.adapter.NoteAdapter;
import com.yf.mynote.db.MyDbHelp;
import com.yf.mynote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class MyNote extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View view;
    private ListView lvnote;
    private NoteAdapter adapter;
    private List<Note> notesArray=new ArrayList<>();
    private SQLiteDatabase writter;
    private ProgressBar notePb;
    private ImageView addBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ui_note,container,false);
        initView();
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addBtn= (ImageView) getActivity().findViewById(R.id.addBtn);
        addBtn.setVisibility(View.VISIBLE);
        addBtn.setOnClickListener(this);
    }

    private void initView() {
        lvnote=(ListView)view.findViewById(R.id.lv_note);
        notePb= (ProgressBar) view.findViewById(R.id.notePb);
        adapter=new NoteAdapter(notesArray,getActivity());
        lvnote.setAdapter(adapter);
        lvnote.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        notesArray.clear();
        initData();
    }



    private void initData() {
        MyDbHelp help=new MyDbHelp(getActivity(),"myNote.db",null,1);
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
        notePb.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }




    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(getActivity(),AddNoteActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(),AddNoteActivity.class);
        intent.putExtra("id",notesArray.get(position).getId());
        intent.putExtra("type",2);
        startActivity(intent);
    }


    public void delNote(int position){
        //删除
        int result = writter.delete("myNote", "id=?", new String[]{notesArray.get(position).getId() + ""});
        if(result>0){
            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            notesArray.clear();
            initData();
        }else{
            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
}
