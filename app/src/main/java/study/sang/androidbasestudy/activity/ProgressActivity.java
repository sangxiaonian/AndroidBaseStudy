package study.sang.androidbasestudy.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ping.com.customprogress.WaveBgProgress;
import ping.com.customprogress.progress.FlikerProgress;
import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.progress.CircleWaveView;
import study.sang.androidbasestudy.view.progress.HorizontalProgress;
import study.sang.androidbasestudy.view.progress.SquareWaveView;

public class ProgressActivity extends AppCompatActivity {

    private HorizontalProgress pro_down, pro_defaul, pro_roun_up, pro_roun_down;
    private SquareWaveView pro_h;
    private CircleWaveView pro_cri;
    private WaveBgProgress wb, wv;
    private FlikerProgress flik;

    int pro;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pro_down.setProgress(pro++ % pro_down.getMax());
            pro_defaul.setProgress(pro++ % pro_defaul.getMax());

            pro_roun_down.setProgress((pro_roun_down.getProgress() + 1) % pro_roun_down.getMax());
            pro_roun_up.setProgress((pro_roun_down.getProgress() + 1) % pro_roun_down.getMax());
            pro_roun_up.setProgress((pro_roun_down.getProgress() + 1) % pro_roun_down.getMax());
            pro_h.setProgerss((pro_roun_down.getProgress() + 1) % pro_roun_down.getMax());
            pro_cri.setProgerss((pro_cri.getProgress() + 1) % pro_cri.getMax());

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
        pro_down = (HorizontalProgress) findViewById(R.id.pro_down);
        pro_defaul = (HorizontalProgress) findViewById(R.id.pro_defaul);
        pro_roun_down = (HorizontalProgress) findViewById(R.id.pro_roun_down);
        pro_roun_up = (HorizontalProgress) findViewById(R.id.pro_roun_up);
        pro_cri = (CircleWaveView) findViewById(R.id.criwave);
        pro_h = (SquareWaveView) findViewById(R.id.wave);
        wb = (WaveBgProgress) findViewById(R.id.pro_wave);
        wv = (WaveBgProgress) findViewById(R.id.pro_w);
        flik= (FlikerProgress) findViewById(R.id.fliker);

        pro_roun_down.setMax(100);
        pro_roun_up.setMax(100);

        pro_down.setMax(100);
        pro_down.setProgress(0);

        pro_defaul.setMax(200);
        pro_defaul.setProgress(0);

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
