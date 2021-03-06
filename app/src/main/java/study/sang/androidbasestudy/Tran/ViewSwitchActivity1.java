package study.sang.androidbasestudy.Tran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.Image3DActivity;
import study.sang.androidbasestudy.activity.StackViewActivity;
import study.sang.androidbasestudy.activity.TextSwitchActivity;
import study.sang.androidbasestudy.utils.JLog;

/**
 * 各种视图切换的类
 */
public class ViewSwitchActivity1 extends AppCompatActivity implements View.OnClickListener{

    private Button bt_3D;
    private Button bt_stack;
    private Button bt_viewSwitch;
    private Button bt_textwitch,bt_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_switch2);
        bt_3D = (Button) findViewById(R.id.bt_image3D);
        bt_stack = (Button) findViewById(R.id.bt_stackView);
        bt_viewSwitch = (Button) findViewById(R.id.bt_viewswitcher);
        bt_textwitch = (Button) findViewById(R.id.bt_textswitcher);
        bt_viewpager = (Button) findViewById(R.id.bt_viewpager);

        bt_3D.setOnClickListener(this);
        bt_viewSwitch.setOnClickListener(this);
        bt_textwitch.setOnClickListener(this);
        bt_stack.setOnClickListener(this);
        bt_viewpager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class c = null;
        switch (v.getId()){
            case R.id.bt_stackView:
                c= StackViewActivity.class;
                JLog.i("----------");
                break;
            case R.id.bt_image3D:
                c = Image3DActivity.class;
                break;
            case R.id.bt_viewswitcher:
                c= ViewSwitchActivity1.class;
                break;
            case R.id.bt_textswitcher:
                c= TextSwitchActivity.class;
                break;
            case R.id.bt_viewpager:
                c= ViewPagerTranActivity.class;
                break;

        }

        startActivity(new Intent(ViewSwitchActivity1.this,c));
    }
}
