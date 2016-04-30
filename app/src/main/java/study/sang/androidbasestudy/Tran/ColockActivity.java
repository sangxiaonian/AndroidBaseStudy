package study.sang.androidbasestudy.Tran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.MainActivity;
import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.CalendarActivity;
import study.sang.androidbasestudy.activity.ClockActivity;
import study.sang.androidbasestudy.activity.PickerActivity;

/**
 * 时钟类别的组件
 */
public class ColockActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_clock;
    private Button bt_clander;
    private Button bt_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colock);


        bt_clock = (Button) findViewById(R.id.bt_Clock);
        bt_clander = (Button) findViewById(R.id.bt_clander);
        bt_picker = (Button) findViewById(R.id.bt_picker);

        bt_clander.setOnClickListener(this);
        bt_picker.setOnClickListener(this);
        bt_clock.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Class c =null;
        switch (v.getId()){
            case R.id.bt_Clock:
                c= ClockActivity.class;
                break;
            case R.id.bt_clander:
                c= CalendarActivity.class;
                break;
            case R.id.bt_picker:
                c= PickerActivity.class;
                break;

        }
        startActivity(new Intent(ColockActivity.this,c));
    }
}
