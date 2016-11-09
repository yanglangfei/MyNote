package com.yf.mynote;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yf.mynote.activity.AddNoteActivity;
import com.yf.mynote.adapter.MainAdapter;
import com.yf.mynote.adapter.NoteAdapter;
import com.yf.mynote.db.MyDbHelp;
import com.yf.mynote.fragment.MyBook;
import com.yf.mynote.fragment.MyNote;
import com.yf.mynote.fragment.RecoderBook;
import com.yf.mynote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *   展示日志
 */
public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mMainVp;
    private RelativeLayout mQxLay;
    private ImageView mMwIcon;
    private TextView mMwLabe;
    private RelativeLayout mXqLay;
    private ImageView mXqIcon;
    private TextView mMtLabe;
    private View currentButton;
    private MainAdapter mAdapter;
    private  List<Fragment> mFragments=new ArrayList<>();
    private ImageView mIvFinish;
    private RelativeLayout mHaoBook;
    private TextView mHaoLable;
    private ImageView mHaoIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
    }

    private void initFragments() {
        mFragments.clear();
        MyNote note=new MyNote();
        MyBook book=new MyBook();
        RecoderBook recoderBook=new RecoderBook();
        mFragments.add(book);
        mFragments.add(note);
        mFragments.add(recoderBook);
        mAdapter.notifyDataSetChanged();
    }


    private void initView() {
        mIvFinish = (ImageView) findViewById(R.id.ivFinish);
        mIvFinish.setOnClickListener(this);
        mMainVp = (ViewPager) findViewById(R.id.mainVp);
        mQxLay = (RelativeLayout) findViewById(R.id.qxLay);
        mMwIcon = (ImageView) findViewById(R.id.mwIcon);
        mMwLabe = (TextView) findViewById(R.id.mwLabe);
        mXqLay = (RelativeLayout) findViewById(R.id.xqLay);
        mHaoBook= (RelativeLayout) findViewById(R.id.hsLay);
        mHaoLable=(TextView)findViewById(R.id.hsLabe);
        mHaoIcon=(ImageView)findViewById(R.id.hsIcon);
        mXqIcon = (ImageView) findViewById(R.id.xqIcon);
        mMtLabe = (TextView) findViewById(R.id.mtLabe);
        mAdapter=new MainAdapter(getSupportFragmentManager(),mFragments);
        mMainVp.setAdapter(mAdapter);
        mQxLay.setOnClickListener(mwClick);
        mXqLay.setOnClickListener(xqClick);
        mHaoBook.setOnClickListener(hsClick);
        mQxLay.performClick();
        mMainVp.setOnPageChangeListener(this);
    }

    private View.OnClickListener mwClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setChange(0);
            setButton(view);
        }
    };


    private View.OnClickListener xqClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setChange(1);
            setButton(view);
        }
    };

    private View.OnClickListener hsClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setChange(2);
            setButton(view);
        }
    };


    private void setButton(View v) {
        // 将上个控件设置为可点击
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        // 当前按钮设置为不可点击，
        v.setEnabled(false);
        currentButton = v;
    }


    public void setChange(int change) {
        if(change==0){
            mMwIcon.setImageResource(R.drawable.mw);
            mXqIcon.setImageResource(R.drawable.xq_no);
            mHaoIcon.setImageResource(R.drawable.book_no);
            mMwLabe.setTextColor(Color.parseColor("#0790c2"));
            mMtLabe.setTextColor(Color.BLACK);
            mHaoLable.setTextColor(Color.BLACK);
            mMainVp.setCurrentItem(0);
        }else if(change==1){
            mMwIcon.setImageResource(R.drawable.mw_no);
            mXqIcon.setImageResource(R.drawable.xq);
            mHaoIcon.setImageResource(R.drawable.book_no);
            mMtLabe.setTextColor(Color.parseColor("#0790c2"));
            mMwLabe.setTextColor(Color.BLACK);
            mHaoLable.setTextColor(Color.BLACK);
            mMainVp.setCurrentItem(1);
        }else {
            mHaoIcon.setImageResource(R.drawable.book);
            mMwIcon.setImageResource(R.drawable.mw_no);
            mXqIcon.setImageResource(R.drawable.xq_no);
            mHaoLable.setTextColor(Color.parseColor("#0790c2"));
            mMwLabe.setTextColor(Color.BLACK);
            mMtLabe.setTextColor(Color.BLACK);
            mMainVp.setCurrentItem(2);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            setChange(0);
            setButton(mQxLay);
        }else if(position==1){
            setChange(1);
            setButton(mXqLay);
        }else {
            setChange(2);
            setButton(mHaoBook);
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View v) {
        this.finish();
    }
}
