package study.sang.androidbasestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import study.sang.androidbasestudy.Tran.ColockActivity;
import study.sang.androidbasestudy.Tran.CustomActivity;
import study.sang.androidbasestudy.Tran.ViewSwitchActivity1;
import study.sang.androidbasestudy.activity.CameraActivity;
import study.sang.androidbasestudy.activity.DrawCricleActivity;
import study.sang.androidbasestudy.activity.ExpandableActivity;
import study.sang.androidbasestudy.activity.FragmnetTestActivity;
import study.sang.androidbasestudy.activity.NotifictionActivity;
import study.sang.androidbasestudy.activity.ProgressActivity;
import study.sang.androidbasestudy.activity.SeekBarActivity;
import study.sang.androidbasestudy.activity.ToastActivity;
import study.sang.androidbasestudy.activity.ViewSwitchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_run, bt_seekBar, bt_toast,bt_progress;

    private Button bt_noti, bt_dialog, bt_recycle, bt_frag, bt_camera, bt_time, bt_sitch;

    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListener();
    }

    private void initListener() {
        bt_progress.setOnClickListener(this);
        bt_run.setOnClickListener(this);
        bt_seekBar.setOnClickListener(this);
        bt_toast.setOnClickListener(this);
        bt_noti.setOnClickListener(this);
        bt_dialog.setOnClickListener(this);
        bt_recycle.setOnClickListener(this);
        bt_frag.setOnClickListener(this);
        bt_camera.setOnClickListener(this);
        bt_time.setOnClickListener(this);
        bt_sitch.setOnClickListener(this);

    }

    private void initViews() {

        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        bt_run = (Button) findViewById(R.id.bt_run);
        bt_time = (Button) findViewById(R.id.time);
        bt_seekBar = (Button) findViewById(R.id.bt_seekBar);
        bt_sitch = (Button) findViewById(R.id.viewStitvh);
        bt_toast = (Button) findViewById(R.id.bt_toast);

        bt_noti = (Button) findViewById(R.id.bt_noti);
        bt_dialog = (Button) findViewById(R.id.bt_dialog);
        bt_recycle = (Button) findViewById(R.id.bt_recycle);
        bt_frag = (Button) findViewById(R.id.bt_frag);
        bt_camera = (Button) findViewById(R.id.bt_camera);
        bt_progress = (Button) findViewById(R.id.bt_progress);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Class c = null;
        switch (id) {
            case R.id.bt_run:
                c = DrawCricleActivity.class;
                break;

            //星级进度条
            case R.id.bt_seekBar:
                c = SeekBarActivity.class;
                break;

            //自定义吐司
            case R.id.bt_toast:
                c = ToastActivity.class;
                break;

            //通知栏
            case R.id.bt_noti:
                c = NotifictionActivity.class;
                break;
            case R.id.bt_dialog:
                c = ExpandableActivity.class;
                break;

            //带头布局的Recycle
            case R.id.bt_recycle:
                c = CustomActivity.class;
                break;
            //Fragment
            case R.id.bt_frag:
                c = FragmnetTestActivity.class;
                break;

            //照相
            case R.id.bt_camera:
                c = CameraActivity.class;
                break;

            //日历等时间相关
            case R.id.time:
                c = ColockActivity.class;
                break;

            //视图切换
            case R.id.viewStitvh:
                c = ViewSwitchActivity1.class;
                break;
            case R.id.bt_progress:
                c= ProgressActivity.class;
                break;

        }
        startActivity(new Intent(MainActivity.this, c));
    }
}
