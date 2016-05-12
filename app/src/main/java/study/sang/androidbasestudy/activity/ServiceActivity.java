package study.sang.androidbasestudy.activity;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import study.sang.androidbasestudy.MainActivity;
import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.service.MyService;
import study.sang.androidbasestudy.utils.JLog;

public class ServiceActivity extends AppCompatActivity {

    private MyService.MyBinder myBinder = null;

    private MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            JLog.i("-------------onServiceConnected------------");

            myBinder = (MyService.MyBinder) iBinder;
            myBinder.start();
//            myService = (MyService) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            JLog.i("-------------onServiceDisconnected------------");
        }
    };

    public void start(View view){
        startService(new Intent(this, MyService.class));
    }

    public void stop(View view){
        stopService(new Intent(this ,MyService.class));
    }

    public void bind(View view){
        bindService(new Intent(this,MyService.class),serviceConnection,BIND_AUTO_CREATE);
    }

    public void unbind(View view){
        unbindService(serviceConnection);
    }

    public void call(View view){
        myBinder.call();
    }

    public void reception(View view){


    }

}
