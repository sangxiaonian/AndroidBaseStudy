package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.image3DView.StereoView;

public class StereoActivity extends AppCompatActivity {

    private StereoView stereoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stereo);
        stereoView = (StereoView) findViewById(R.id.stereo);
    }

    private int index;

    public void next(View view){
        index+=1;
        stereoView.scrollToIndex( index);
    }

    public void last(View view){
        index-=1;
        stereoView.scrollToIndex(index);
    }
}
