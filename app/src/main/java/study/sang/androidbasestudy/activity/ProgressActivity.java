package study.sang.androidbasestudy.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.progress.HorizontalProgress;

public class ProgressActivity extends AppCompatActivity {

    private HorizontalProgress pro_down,pro_defaul,pro_roun_up,pro_roun_down;

    int pro;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pro_down.setProgress(pro++%pro_down.getMax());
            pro_defaul.setProgress(pro++%pro_defaul.getMax());

            pro_roun_down.setProgress((pro_roun_down.getProgress()+1)%pro_roun_down.getMax());
            pro_roun_up.setProgress((pro_roun_down.getProgress()+1)%pro_roun_down.getMax());

            sendEmptyMessageDelayed(0,100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        pro_down = (HorizontalProgress) findViewById(R.id.pro_down);
        pro_defaul = (HorizontalProgress) findViewById(R.id.pro_defaul);
        pro_roun_down= (HorizontalProgress) findViewById(R.id.pro_roun_down);
        pro_roun_up = (HorizontalProgress) findViewById(R.id.pro_roun_up);

        pro_roun_down.setMax(100);
        pro_roun_up.setMax(100);

        pro_down.setMax(100);
        pro_down.setProgress(0);

        pro_defaul.setMax(200);
        pro_defaul.setProgress(0);
        handler.sendEmptyMessageDelayed(0,100);
    }


}
