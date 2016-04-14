package study.sang.androidbasestudy.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

public class TextSwitchActivity extends AppCompatActivity {


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            item++;
            ts.setText(strs[item%strs.length]);
            JLog.i("-------"+item+"---------");
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };

    private TextSwitcher ts;
    private String []strs = {
            "AAAAAA",
            "疯狂讲义",
            "bbbbbbbbbbbbbb",
            "FADFADFASDFASF"
    };

    int item = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switch);

        ts = (TextSwitcher) findViewById(R.id.ts);
        ts.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(TextSwitchActivity.this);
                textView.setTextSize(80);
                textView.setTextColor(Color.RED);
                return textView;
            }
        });
        ts.setText(strs[item%strs.length]);
//        ts.setAnimation(getAnimation());
        handler.sendEmptyMessageDelayed(0,2000);

    }

    public void next(View view){
        JLog.i("--------被点击了----------");
//        ts.setText(strs[item++%strs.length]);
//        ts.setAnimation(getAnimation());
//        if (handler.hasMessages(0)){
//            handler.removeMessages(0);
//        }
//        handler.sendEmptyMessageDelayed(0,3000);

    }

    private AlphaAnimation getAnimation() {
      AlphaAnimation alphaAnimation =   new AlphaAnimation(1,0);
        alphaAnimation.setDuration(1000);
        return alphaAnimation ;
    }
}
