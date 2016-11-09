package com.yf.mynote.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yf.mynote.R;
import com.yf.mynote.model.Book;
import com.yf.mynote.utils.Contact;
import com.yf.mynote.utils.JsonpUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/9.
 */

public class BookDetail extends Activity implements View.OnClickListener {
    private TextView mDetailTitle;
    private WebView mBookDetailBody;
    private ImageView mIvFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_book_del);
        //安全设置  禁止进行屏幕捕捉   ----防止对屏幕进行截取
       // this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        initView();
    }

    private void initView() {
        mIvFinish = (ImageView) findViewById(R.id.ivFinish);
        mDetailTitle = (TextView) findViewById(R.id.detailTitle);
        mBookDetailBody = (WebView) findViewById(R.id.bookDetailBody);
        final WebSettings setting = mBookDetailBody.getSettings();
        mIvFinish.setOnClickListener(this);
        setting.setBlockNetworkImage(true);
        mBookDetailBody.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                setting.setBlockNetworkImage(false);
            }
        });

        String link= getIntent().getStringExtra("link");
        new GetDetail().execute(link);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    class  GetDetail extends AsyncTask<String,Void,Book>{

          @Override
          protected Book doInBackground(String... params) {
              try {
                  Document doc = Jsoup.connect(Contact.JIANSHU + params[0]).get();
                  Book book= JsonpUtils.getBookDetail(doc);
                  return  book;
              } catch (IOException e) {
                  e.printStackTrace();
              }
              return null;
          }

          @Override
          protected void onPostExecute(Book book) {
              mDetailTitle.setText(book.getTitle());
              mBookDetailBody.loadDataWithBaseURL(null,book.getBody(),"text/html","utf-8",null);
          }
      }

    }
