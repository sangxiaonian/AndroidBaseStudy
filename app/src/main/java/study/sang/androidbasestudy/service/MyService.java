package study.sang.androidbasestudy.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import study.sang.androidbasestudy.MainActivity;
import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

public class MyService extends Service {

    @Override
    public void onCreate() {
        JLog.i("-----------onCreate----------");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        JLog.i("-----------onStart----------");

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JLog.i("-----------onStartCommand----------");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        JLog.i("-----------onBind----------");

        return new MyBinder();
    }


    @Override
    public void onRebind(Intent intent) {
        JLog.i("-----------onRebind----------");

        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        JLog.i("-----------onUnbind----------");

        return super.onUnbind(intent);
    }




    @Override
    public void onDestroy() {
        JLog.i("-----------onDestroy----------");
        super.onDestroy();
    }




    public  class MyBinder extends Binder{

        public void start(){
            JLog.i("-------执行服务中的方法------------");
        }



        public void call(){

        }

    }
}
