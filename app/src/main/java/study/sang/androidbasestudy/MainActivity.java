package study.sang.androidbasestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.Tran.ColockActivity;
import study.sang.androidbasestudy.Tran.ViewswitchActivity;
import study.sang.androidbasestudy.activity.CalendarActivity;
import study.sang.androidbasestudy.activity.CameraActivity;
import study.sang.androidbasestudy.activity.ClockActivity;
import study.sang.androidbasestudy.activity.DialoActivity;
import study.sang.androidbasestudy.activity.DrawCricleActivity;
import study.sang.androidbasestudy.activity.ExpandableActivity;
import study.sang.androidbasestudy.activity.FragmnetTestActivity;
import study.sang.androidbasestudy.activity.Image3DActivity;
import study.sang.androidbasestudy.activity.NotifictionActivity;
import study.sang.androidbasestudy.activity.PickerActivity;
import study.sang.androidbasestudy.activity.RecycleActivity;
import study.sang.androidbasestudy.activity.SeekBarActivity;
import study.sang.androidbasestudy.activity.StackViewActivity;
import study.sang.androidbasestudy.activity.TextSwitchActivity;
import study.sang.androidbasestudy.activity.ToastActivity;
import study.sang.androidbasestudy.activity.ViewSwitchActivity;
import study.sang.androidbasestudy.utils.JLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_run,bt_seekBar ,bt_toast;

    private Button bt_noti,bt_dialog,bt_recycle,bt_frag,bt_camera,bt_time,bt_sitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListener();
    }

    private void initListener() {
        bt_run.setOnClickListener(this);

        bt_seekBar.setOnClickListener(this);

        bt_toast.setOnClickListener(this);

        bt_noti.setOnClickListener(this);
        bt_dialog.setOnClickListener(this);
        bt_recycle.setOnClickListener(this);
        bt_frag.setOnClickListener(this);

        bt_camera.setOnClickListener(this);
        bt_time.setOnClickListener(this);
        bt_sitch .setOnClickListener(this);

    }

    private void initViews() {

        bt_run = (Button) findViewById(R.id.bt_run);
bt_time= (Button) findViewById(R.id.time);
        bt_seekBar= (Button) findViewById(R.id.bt_seekBar);
bt_sitch= (Button) findViewById(R.id.viewStitvh);
        bt_toast= (Button) findViewById(R.id.bt_toast);

        bt_noti= (Button) findViewById(R.id.bt_noti);
        bt_dialog = (Button) findViewById(R.id.bt_dialog);
        bt_recycle = (Button) findViewById(R.id.bt_recycle);
        bt_frag= (Button) findViewById(R.id.bt_frag);
        bt_camera= (Button) findViewById(R.id.bt_camera);


    }

    @Override
    public void onClick(View v) {
       int id =  v.getId();
        Class c =null;
        switch (id){
            case R.id.bt_run:
                c= DrawCricleActivity.class;
                break;

            //星级进度条
            case R.id.bt_seekBar:
                c= SeekBarActivity.class;
                break;

            //自定义吐司
            case R.id.bt_toast:
                c= ToastActivity.class;
                break;

            //通知栏
            case R.id.bt_noti:
                c= NotifictionActivity.class;
                break;
            case R.id.bt_dialog:
                c= ExpandableActivity.class;
                break;

            //带头布局的Recycle
            case R.id.bt_recycle:
                c= RecycleActivity.class;
                break;
            //Fragment
            case R.id.bt_frag:
                c= FragmnetTestActivity.class;
                break;

            //照相
            case R.id.bt_camera:
                c= CameraActivity.class;
                break;

            //日历等时间相关
            case R.id.time:
                c=ColockActivity.class;
                break;

            //视图切换
            case R.id.viewStitvh:
                c= ViewswitchActivity.class;
                break;

        }
        startActivity(new Intent(MainActivity.this,c));
    }
}
