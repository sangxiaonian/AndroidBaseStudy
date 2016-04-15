package study.sang.androidbasestudy.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.DrawCirleView;

public class NotifictionActivity extends AppCompatActivity {

    private NotificationManager noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiction);
        noti= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void send(View view){
        Intent intent = new Intent(NotifictionActivity.this, DrawCirleView.class);
        PendingIntent pi=PendingIntent.getActivity(NotifictionActivity.this,0,intent,0);

        Notification notification = new Notification.Builder(this)
                //设置点击后自动消失
                .setAutoCancel(true)
                //设置显示在状态栏的信息
                .setTicker("有新消息了")
                //设置显示的小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置消息的标题
                .setContentTitle("这是消息的标题")
                //设置消息的内容
                .setContentText("这是消息的内容")
                //设置时间
                .setWhen(SystemClock.currentThreadTimeMillis())
                //设置点击时启动的程序
                .setContentIntent(pi)
                .build();
        noti.notify(0, notification);
    }













}
