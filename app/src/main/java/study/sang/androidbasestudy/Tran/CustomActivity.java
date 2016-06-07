package study.sang.androidbasestudy.Tran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.RadioGroupActivity;
import study.sang.androidbasestudy.activity.RecycleActivity;
import study.sang.androidbasestudy.activity.Recycle_Ani_Activity;

/**
 * 自定义控件页面
 */
public class CustomActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_recy,bt_radio,bt_recy_ani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        bt_radio= (Button) findViewById(R.id.view_radio);
        bt_recy = (Button) findViewById(R.id.view_recycle);
        bt_recy_ani = (Button) findViewById(R.id.cus_recycle);


        bt_recy.setOnClickListener(this);
        bt_radio.setOnClickListener(this);
        bt_recy_ani.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()){

            //自定义多行多列的RadioGroup
            case R.id.view_radio:
                c= RadioGroupActivity.class;
                break;
            //自定义带有头布局的RecycleView
            case R.id.view_recycle:
                c= RecycleActivity.class;
                break;

            case R.id.cus_recycle:
                c= Recycle_Ani_Activity.class;
                break;
        }

        startActivity(new Intent(CustomActivity.this,c));
    }
}
