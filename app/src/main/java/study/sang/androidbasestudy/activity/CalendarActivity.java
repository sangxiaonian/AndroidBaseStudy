package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.ToastUtil;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        cal= (CalendarView) findViewById(R.id.cal);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                ToastUtil.showToast(CalendarActivity.this,year+"年"+month+"月"+dayOfMonth+"日");
            }
        });
    }
}
