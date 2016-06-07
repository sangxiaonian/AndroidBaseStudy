package study.sang.androidbasestudy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Created by ping on 2016/5/20.
 */
public class UpgradeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        String packageName = intent.getData().getSchemeSpecificPart();
        JLog.i("AndBasy收到广播了"+packageName);
    }
}
