package study.sang.androidbasestudy.activity;


import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;


import study.sang.androidbasestudy.R;

public class ClockActivity extends AppCompatActivity {

    private Chronometer chro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        chro = (Chronometer) findViewById(R.id.chr);
//

        chro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime()-chronometer.getBase()>=5000){
//                    chronometer.stop();
                    chro.stop();
                    chro.setBase(SystemClock.elapsedRealtime());
                    chro.start();
                }

            }
        });
    }

    public void start(View v) {
        chro.setBase(SystemClock.elapsedRealtime());
        chro.start();
    }

    public void stop(View view) {
        chro.stop();
    }
}
