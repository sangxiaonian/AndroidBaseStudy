package study.sang.androidbasestudy.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.zip.Inflater;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.bezier.HeartView;

public class HeartViewActivity extends AppCompatActivity {

    private HeartView heart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_view);
        heart = (HeartView) findViewById(R.id.heart);
    }

    public void click(View view){
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.heart_item,null);
//        heart.addView(imageView,params);
    }





}
