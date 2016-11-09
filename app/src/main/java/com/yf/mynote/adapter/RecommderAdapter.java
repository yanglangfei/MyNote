package com.yf.mynote.adapter;

import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yf.mynote.R;
import com.yf.mynote.model.Recommder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class RecommderAdapter extends BaseAdapter implements View.OnClickListener {
    private final Context context;
    private final List<Recommder> recommders;

    public RecommderAdapter(Context context, List<Recommder> recommders) {
        this.context = context;
        this.recommders = recommders;
    }

    @Override
    public int getCount() {
        return recommders.size();
    }

    @Override
    public Object getItem(int position) {
        return recommders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ui_recommder_item, null);
        }
        ImageView mRecIcon = (ImageView) convertView.findViewById(R.id.recIcon);
        TextView mRecoTitle = (TextView) convertView.findViewById(R.id.recoTitle);
        TextView mRecCata = (TextView) convertView.findViewById(R.id.recCata);
        TextView mRecSub1 = (TextView) convertView.findViewById(R.id.recSub1);
        TextView mRecSub2 = (TextView) convertView.findViewById(R.id.recSub2);
        TextView mRecRead = (TextView) convertView.findViewById(R.id.recRead);
        TextView mRecOnline = (TextView) convertView.findViewById(R.id.recOnline);
        TextView mRecTime = (TextView) convertView.findViewById(R.id.recTime);
        TextView recTags = (TextView) convertView.findViewById(R.id.recTags);
        ImageView more = (ImageView) convertView.findViewById(R.id.more);
        Recommder recommder = recommders.get(position);
        recTags.setText(recommder.getTags());
        Glide.with(context).load(recommder.getImg()).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.def)
                .into(mRecIcon);
        mRecoTitle.setText(recommder.getTitle());
        mRecCata.setText(recommder.getCatalog());
        mRecSub1.setText(recommder.getSub1());
        mRecSub2.setText(recommder.getSub2().length()>=20 ? recommder.getSub2().substring(0, 20) : recommder.getSub2());
        mRecRead.setText(recommder.getReading());
        mRecOnline.setText(recommder.getOnline());
        mRecTime.setText(recommder.getBytime());
        more.setTag(R.string.textView, mRecSub2);
        more.setTag(R.string.index, position);
        more.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        TextView p = (TextView) v.getTag(R.string.textView);
        int index = (int) v.getTag(R.string.index);
        String sub2 = recommders.get(index).getSub2();
        int lenth = p.getText().length();
        if (lenth <= 20) {
            p.setText(sub2);
            ((ImageView) v).setImageResource(R.drawable.close);
            //   p.setFilters(new InputFilter[]{new InputFilter.LengthFilter(sub2.length())});
        } else {
            // p.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
            p.setText(sub2.length()>=20 ? sub2.substring(0, 20) : sub2);
            ((ImageView) v).setImageResource(R.drawable.more);
        }
        //  p.setText(sub2);
    }
}
