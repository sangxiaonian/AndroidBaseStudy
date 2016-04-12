package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

public class SeekBarActivity extends AppCompatActivity {

    private ImageView img;
    private SeekBar seekBar;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);

        img  = (ImageView) findViewById(R.id.seek_img);
        seekBar= (SeekBar) findViewById(R.id.seek);
        ratingBar= (RatingBar) findViewById(R.id.seek_rating);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                img.setAlpha(progress);
                JLog.i("变化中"+progress+"-----"+seekBar.getMax());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                JLog.i("开始");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                JLog.i("结束");

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                img.setImageAlpha((int) (rating*255/5));
                JLog.i(rating+"----");
            }
        });
    }
}
