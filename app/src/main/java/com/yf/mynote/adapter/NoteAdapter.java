package com.yf.mynote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yf.mynote.MainActivity;
import com.yf.mynote.R;
import com.yf.mynote.model.Note;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */

public class NoteAdapter extends BaseAdapter {
    private final List<Note> notes;
    private final Context context;

    public NoteAdapter(List<Note> notes, Context context) {
        this.notes=notes;
        this.context=context;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.ui_note_item,null);
        }
        TextView note_item_title= (TextView) view.findViewById(R.id.note_item_title);
        TextView note_item_time= (TextView) view.findViewById(R.id.note_item_time);
        note_item_title.setText(notes.get(i).getTitle());
        note_item_time.setText(notes.get(i).getTime());
        return view;
    }
}
