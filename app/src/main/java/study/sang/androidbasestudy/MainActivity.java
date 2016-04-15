package study.sang.androidbasestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.activity.CalendarActivity;
import study.sang.androidbasestudy.activity.ClockActivity;
import study.sang.androidbasestudy.activity.DialoActivity;
import study.sang.androidbasestudy.activity.DrawCricleActivity;
import study.sang.androidbasestudy.activity.NotifictionActivity;
import study.sang.androidbasestudy.activity.PickerActivity;
import study.sang.androidbasestudy.activity.SeekBarActivity;
import study.sang.androidbasestudy.activity.TextSwitchActivity;
import study.sang.androidbasestudy.activity.ToastActivity;
import study.sang.androidbasestudy.activity.ViewSwitchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_run,bt_clock,bt_seekBar,bt_viewSwitch,bt_textwitch ,bt_toast,bt_clander;

    private Button bt_picker,bt_noti,bt_dialog;
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
        bt_clander.setOnClickListener(this);
        bt_picker.setOnClickListener(this);
        bt_noti.setOnClickListener(this);
        bt_dialog.setOnClickListener(this);
    }

    private void initViews() {
        bt_run = (Button) findViewById(R.id.bt_run);
        bt_clock= (Button) findViewById(R.id.bt_Clock);
        bt_seekBar= (Button) findViewById(R.id.bt_seekBar);
        bt_viewSwitch= (Button) findViewById(R.id.bt_viewswitcher);
        bt_textwitch= (Button) findViewById(R.id.bt_textswitcher);
        bt_toast= (Button) findViewById(R.id.bt_toast);
        bt_clander = (Button) findViewById(R.id.bt_clander);
        bt_picker = (Button) findViewById(R.id.bt_picker);
        bt_noti= (Button) findViewById(R.id.bt_noti);
        bt_dialog = (Button) findViewById(R.id.bt_dialog);

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
            case R.id.bt_clander:
                c= CalendarActivity.class;
                break;
            case R.id.bt_picker:
                c= PickerActivity.class;
                break;
            case R.id.bt_noti:
                c= NotifictionActivity.class;
                break;
            case R.id.bt_dialog:
                c= DialoActivity.class;
                break;
        }
        startActivity(new Intent(MainActivity.this,c));
    }
}
