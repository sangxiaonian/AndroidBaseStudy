package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.bezier.IndexView;

public class BesizerIndexActivity extends AppCompatActivity {

    private IndexView indexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besizer_index);
        RadioGroup radioGroup = new RadioGroup(getApplicationContext());

        indexView = (IndexView) findViewById(R.id.index_view);

        for (int i = 0; i < 5; i++) {
            indexView.addLayoutView(R.layout.item_radio,"测试");
        }
        indexView.addIndex("测试");
    }
}
