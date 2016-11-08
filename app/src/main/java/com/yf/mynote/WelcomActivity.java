package com.yf.mynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.yf.mynote.activity.MyBook;
import com.yf.mynote.db.MyDbHelp;
/**
 * Created by Administrator on 2016/10/8.
 */
public class WelcomActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.welcom);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(imageView);
        ininView();
    }

    private void ininView() {
        MyDbHelp help=new MyDbHelp(this,"myNote.db",null,1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             WelcomActivity.this.startActivity(new Intent(WelcomActivity.this,MyBook.class));
            }
        },2000);
    }
}
