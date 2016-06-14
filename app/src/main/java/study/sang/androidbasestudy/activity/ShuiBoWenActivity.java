package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

/**
 * 水波纹效果的演示
 */
public class ShuiBoWenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shui_bo_wen);
    }

    public void click(View view) {
        JLog.i("被点击了");
    }

    public void click1(View view) {
        JLog.i("一般的");
    }
}
