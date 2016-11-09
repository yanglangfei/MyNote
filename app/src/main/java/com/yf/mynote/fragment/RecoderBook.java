package com.yf.mynote.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yf.mynote.R;
import com.yf.mynote.adapter.RecommderAdapter;
import com.yf.mynote.model.Recommder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class RecoderBook extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private View view;
    private RadioGroup group;
    private ListView recoderLv;
    private RecommderAdapter mAdapter;
    private  int page=0;
    private  int currentId;
    private  boolean isBootom;
    private ProgressBar pb;

    private String getType="http://apis.juhe.cn/goodbook/catalog";
    private  String getRecommder="http://apis.juhe.cn/goodbook/query";

    private List<Recommder> mRecommders=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ui_recoder_bos,container,false);
        initView();
        initGroup();
        return view;
    }

    private void initGroup() {
        group.removeAllViews();
        final RequestParams param=new RequestParams(getType);
        param.addParameter("key","ffe1ba95a0c3e5b69287a6ab05a3118d");
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    try {
                        JSONObject object=new JSONObject(result);
                        int resultcode=object.getInt("resultcode");
                        if(resultcode==200){
                            JSONArray array=object.getJSONArray("result");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject type=array.getJSONObject(i);
                                String catalog=type.getString("catalog");
                                int id=type.getInt("id");
                                RadioButton button = new RadioButton(getActivity());
                                button.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
                                button.setTextColor(Color.BLACK);
                                button.setGravity(Gravity.CENTER);
                                button.setPadding(10, 10, 10, 10);
                                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                button.setLayoutParams(params);
                                button.setId(id);
                                button.setText(catalog);
                                group.addView(button);
                            }
                            if (group.getChildCount() > 0) {
                                RadioButton button = (RadioButton) group.getChildAt(0);
                                button.setChecked(true);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void initView() {
        final View footer=LayoutInflater.from(getActivity()).inflate(R.layout.ui_footview,null);
        recoderLv= (ListView) view.findViewById(R.id.recoderLv);
        group = (RadioGroup) view.findViewById(R.id.news_group);
        pb= (ProgressBar) view.findViewById(R.id.pb);
        mAdapter=new RecommderAdapter(getActivity(),mRecommders);
        recoderLv.addFooterView(footer);
        recoderLv.setAdapter(mAdapter);
        recoderLv.removeFooterView(footer);
        group.setOnCheckedChangeListener(this);
        if (group.getChildCount() > 0) {
            RadioButton button = (RadioButton) group.getChildAt(0);
            button.setChecked(true);
        }
        recoderLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_IDLE:
                            // 判断是否滚动到底部
                            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        if (isBootom) {
                           recoderLv.removeFooterView(footer);
                        } else {
                            recoderLv.addFooterView(footer);
                            page += 30;
                            initRecommderData(currentId, page);

                        }
                    }
                        break;

                    case SCROLL_STATE_FLING:
                        break;

                        }

                }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton radioButton= (RadioButton) group.getChildAt(i);
            if(radioButton.isChecked()){
                mRecommders.clear();
                mAdapter.notifyDataSetChanged();
                pb.setVisibility(View.VISIBLE);
                currentId=checkedId;
                initRecommderData(checkedId,0);
                radioButton.setTextColor(Color.parseColor("#cc3636"));
            }else {
                radioButton.setTextColor(Color.BLACK);
            }

        }

    }

    private void initRecommderData(int id,int page) {
        RequestParams param=new RequestParams(getRecommder);
        param.addParameter("key","ffe1ba95a0c3e5b69287a6ab05a3118d");
        param.addParameter("catalog_id",id);
        param.addParameter("pn",page);
        param.addParameter("rn",30);
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                pb.setVisibility(View.GONE);
                if(result!=null){
                    try {
                        JSONObject object=new JSONObject(result);
                        int resultcode=object.optInt("resultcode");
                        if(resultcode==200){
                            JSONObject res=object.getJSONObject("result");
                            JSONArray array=res.optJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject book=array.getJSONObject(i);
                                String title=book.optString("title");
                                String catalog=book.optString("catalog");
                                String tags=book.optString("tags");
                                String sub1=book.optString("sub1");
                                String sub2=book.optString("sub2");
                                String img=book.optString("img");
                                String online=book.optString("online");
                                String bytime=book.optString("bytime");

                                Recommder recommder=new Recommder();
                                recommder.setTitle(title);
                                recommder.setCatalog(catalog);
                                recommder.setTags(tags);
                                recommder.setSub1(sub1);
                                recommder.setSub2(sub2);
                                recommder.setImg(img);
                                recommder.setOnline(online);
                                recommder.setBytime(bytime);
                                mRecommders.add(recommder);
                            }
                            mAdapter.notifyDataSetChanged();
                        }else {
                            isBootom=true;
                            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
