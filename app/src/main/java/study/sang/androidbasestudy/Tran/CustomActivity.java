package study.sang.androidbasestudy.Tran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.BesizerIndexActivity;
import study.sang.androidbasestudy.activity.DrawLayoutActivity;
import study.sang.androidbasestudy.activity.HeartViewActivity;
import study.sang.androidbasestudy.activity.RadioGroupActivity;
import study.sang.androidbasestudy.activity.RecycleActivity;
import study.sang.androidbasestudy.activity.RecycleDragActivity;
import study.sang.androidbasestudy.activity.Recycle_Ani_Activity;
import study.sang.androidbasestudy.activity.ShuiBoWenActivity;
import study.sang.androidbasestudy.activity.StereoActivity;
import study.sang.androidbasestudy.activity.ViewDragHelperActivity;
import study.sang.androidbasestudy.view.BezierActivity;

/**
 * 自定义控件页面
 */
public class CustomActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_recy, bt_radio, bt_recy_ani, bt_cus_drag, bt_shui,bt_drag,bt_draw,bt_besizer
            ,bt_heart ,bt_index,stereo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        bt_radio = (Button) findViewById(R.id.view_radio);
        bt_recy = (Button) findViewById(R.id.view_recycle);
        bt_recy_ani = (Button) findViewById(R.id.cus_recycle);
        bt_cus_drag = (Button) findViewById(R.id.cus_drag);
        bt_shui = (Button) findViewById(R.id.cus_shui);
        bt_drag = (Button) findViewById(R.id.cus_dragview);
        bt_draw= (Button) findViewById(R.id.cus_draw);
        bt_besizer = (Button) findViewById(R.id.besizer);
        bt_heart = (Button) findViewById(R.id.heart_view);
        bt_index = (Button) findViewById(R.id.index_view);
        stereo = (Button) findViewById(R.id.stereo_view);

        stereo.setOnClickListener(this);
        bt_index.setOnClickListener(this);
        bt_heart.setOnClickListener(this);
        bt_besizer.setOnClickListener(this);
        bt_draw.setOnClickListener(this);
        bt_shui.setOnClickListener(this);
        bt_drag.setOnClickListener(this);
        bt_recy.setOnClickListener(this);
        bt_radio.setOnClickListener(this);
        bt_recy_ani.setOnClickListener(this);
        bt_cus_drag.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()) {

            //自定义多行多列的RadioGroup
            case R.id.view_radio:
                c = RadioGroupActivity.class;
                break;
            //自定义带有头布局的RecycleView
            case R.id.view_recycle:
                c = RecycleActivity.class;
                break;
            //rectcleView的万能适配器
            case R.id.cus_recycle:
                c = Recycle_Ani_Activity.class;
                break;

            //带有拖拽功能的adapter
            case R.id.cus_drag:
                c = RecycleDragActivity.class;
                break;

            //水波纹效果
            case R.id.cus_shui:
                c = ShuiBoWenActivity.class;
                break;

            //ViewDragHelper 联系
            case R.id.cus_dragview:
                c = ViewDragHelperActivity.class;
                break;
            //ViewDragHelper 联系
            case R.id.cus_draw:
                c = DrawLayoutActivity.class;
                break;

            //贝塞尔曲线练习
            case R.id.besizer:
                c = BezierActivity.class;
                break;
            //心联系
            case R.id.heart_view:
                c= HeartViewActivity.class;
                break;

            //指示器
            case R.id.index_view:
                c= BesizerIndexActivity.class;
                break;
            case R.id.stereo_view:
                c = StereoActivity.class;
                break;
        }

        startActivity(new Intent(CustomActivity.this, c));
    }
}
