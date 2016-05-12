package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.radioGroup.CustomRadioGoup;

public class RadioGroupActivity extends AppCompatActivity {

    private CustomRadioGoup my;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group);
        my= (CustomRadioGoup) findViewById(R.id.my_radio);

        my.setChild(R.layout.item_radio);

        editText = (EditText) findViewById(R.id.radio_et);
    }

    int i = 0;

    public void add(View view){
        String name = editText.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            name="添加"+i++;
        }

        my.addchild(name);
    }
}
