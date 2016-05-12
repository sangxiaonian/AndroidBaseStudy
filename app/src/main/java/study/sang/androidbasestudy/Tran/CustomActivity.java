package study.sang.androidbasestudy.Tran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.RadioGroupActivity;
import study.sang.androidbasestudy.activity.RecycleActivity;

/**
 * 自定义控件页面
 */
public class CustomActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_recy,bt_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        bt_radio= (Button) findViewById(R.id.view_radio);
        bt_recy = (Button) findViewById(R.id.view_recycle);

        bt_recy.setOnClickListener(this);
        bt_radio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()){
            case R.id.view_radio:
                c= RadioGroupActivity.class;
                break;
            case R.id.view_recycle:
                c= RecycleActivity.class;
                break;
        }

        startActivity(new Intent(CustomActivity.this,c));
    }
}
