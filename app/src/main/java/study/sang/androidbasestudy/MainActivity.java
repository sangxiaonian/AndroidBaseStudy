package study.sang.androidbasestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.activity.ClockActivity;
import study.sang.androidbasestudy.activity.DrawCricleActivity;
import study.sang.androidbasestudy.activity.SeekBarActivity;
import study.sang.androidbasestudy.activity.TextSwitchActivity;
import study.sang.androidbasestudy.activity.ToastActivity;
import study.sang.androidbasestudy.activity.ViewSwitchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_run,bt_clock,bt_seekBar,bt_viewSwitch,bt_textwitch ,bt_toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListener();
    }

    private void initListener() {
        bt_run.setOnClickListener(this);
        bt_clock.setOnClickListener(this);
        bt_seekBar.setOnClickListener(this);
        bt_viewSwitch.setOnClickListener(this);
        bt_textwitch.setOnClickListener(this);
        bt_toast.setOnClickListener(this);
    }

    private void initViews() {
        bt_run = (Button) findViewById(R.id.bt_run);
        bt_clock= (Button) findViewById(R.id.bt_Clock);
        bt_seekBar= (Button) findViewById(R.id.bt_seekBar);
        bt_viewSwitch= (Button) findViewById(R.id.bt_viewswitcher);
        bt_textwitch= (Button) findViewById(R.id.bt_textswitcher);
        bt_toast= (Button) findViewById(R.id.bt_toast);
    }

    @Override
    public void onClick(View v) {
       int id =  v.getId();
        Class c =null;
        switch (id){
            case R.id.bt_run:
                c= DrawCricleActivity.class;
                break;
            case R.id.bt_Clock:
                c= ClockActivity.class;
                break;

            case R.id.bt_seekBar:
                c= SeekBarActivity.class;
                break;
            case R.id.bt_viewswitcher:
                c= ViewSwitchActivity.class;
                break;
            case R.id.bt_textswitcher:
                c= TextSwitchActivity.class;
                break;
            case R.id.bt_toast:
                c= ToastActivity.class;
                break;
        }
        startActivity(new Intent(MainActivity.this,c));
    }
}
