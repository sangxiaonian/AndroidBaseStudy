package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.Calendar;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.ToastUtil;

public class PickerActivity extends AppCompatActivity {
    TimePicker time;
    DatePicker data;
    NumberPicker num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        time = (TimePicker) findViewById(R.id.time);
        data = (DatePicker) findViewById(R.id.data);
        time.setIs24HourView(true);
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                ToastUtil.showTextToast(PickerActivity.this, hourOfDay + "时" + minute + "分");

            }
        });
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);

        data.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ToastUtil.showTextToast(PickerActivity.this, year + "年" + monthOfYear + "月" + dayOfMonth + "日");

            }
        });

        num = (NumberPicker) findViewById(R.id.num);
        num.setMaxValue(10);
        num.setMinValue(1);
        num.setValue(0);
        num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ToastUtil.showTextToast(PickerActivity.this, oldVal + ">>>>" + newVal);
            }
        });
    }
}
