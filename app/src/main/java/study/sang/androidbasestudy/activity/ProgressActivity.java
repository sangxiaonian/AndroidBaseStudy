package study.sang.androidbasestudy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ping.com.customprogress.DownLoadProgress;
import ping.com.customprogress.WaveBgProgress;
import ping.com.customprogress.progress.FlikerProgress;
import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.progress.HorizontalProgress;
import study.sang.androidbasestudy.view.progress.SquareWaveView;

public class ProgressActivity extends AppCompatActivity {

    private HorizontalProgress pro_down, pro_roun_up;
    private SquareWaveView pro_h;
    private WaveBgProgress wb, wv;
    private FlikerProgress flik;
    private DownLoadProgress down;

    int pro;

    boolean isFail ;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pro_down.setProgress(pro++ % pro_down.getMax());

            pro_roun_up.setProgress(pro++ % pro_down.getMax());


            if (isFail) {
                if (pro == 50) {
                    down.downLoadFail();
                }
            } else {
                if (pro % 10 == 0)
                    down.setProgress(pro);
            }
            if (pro == down.getMax()) {
                pro = 0;
                isFail = !isFail;
            }


            wb.setProgress(((wb.getProgress()) + 1) % wb.getMax());
            wv.setProgress(((wv.getProgress()) + 1) % wv.getMax());
            flik.setProgress(((flik.getProgress()) + 1) % flik.getMax());
            sendEmptyMessageDelayed(0, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        wb = (WaveBgProgress) findViewById(R.id.pro_wave);
        wv = (WaveBgProgress) findViewById(R.id.pro_w);
        flik = (FlikerProgress) findViewById(R.id.fliker);
        pro_roun_up = (HorizontalProgress) findViewById(R.id.pro_roun_up);
        pro_down = (HorizontalProgress) findViewById(R.id.pro_down);
        down = (DownLoadProgress) findViewById(R.id.down);


        down.setMax(100);
        down.setProgress(0);
        pro_roun_up.setMax(100);
        pro_roun_up.setProgress(0);
        pro_down.setMax(100);
        pro_down.setProgress(0);


        flik.setMax(100);
        flik.setProgress(0);
        wb.setMax(200);
        wb.setProgress(0);
        wv.setMax(400);
        wv.setProgress(0);
        wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        handler.sendEmptyMessageDelayed(0, 100);
    }


}
