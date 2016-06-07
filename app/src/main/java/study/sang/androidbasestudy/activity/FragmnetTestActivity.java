package study.sang.androidbasestudy.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.fragment.Frangment1;
import study.sang.androidbasestudy.fragment.Frangment2;
import study.sang.androidbasestudy.utils.JLog;

public class FragmnetTestActivity extends FragmentActivity {

    private FrameLayout fl;
    private FragmentManager supportFragmentManager;
    private Frangment1 frangment1;
    private Frangment2 frangment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmnet_test);
        fl = (FrameLayout) findViewById(R.id.fg);

        supportFragmentManager = getSupportFragmentManager();
        frangment1 = new Frangment1();
        frangment2 = new Frangment2();

    }

    public void one(View view)
    {
        supportFragmentManager.beginTransaction().replace(R.id.fg,frangment1).commit();
        JLog.i("第一个");

    }

    public void two(View view){
        supportFragmentManager.beginTransaction().replace(R.id.fg,frangment2).commit();
        JLog.i("第2个");
    }
}
