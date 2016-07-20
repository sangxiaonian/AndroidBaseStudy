package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.bezier.IndexView;
import study.sang.androidbasestudy.view.index.LinePagerIndex;

public class BesizerIndexActivity extends AppCompatActivity {

    private IndexView indexView;
    private LinePagerIndex linePagerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besizer_index);
        RadioGroup radioGroup = new RadioGroup(getApplicationContext());

        indexView = (IndexView) findViewById(R.id.index_view);
        linePagerIndex = (LinePagerIndex) findViewById(R.id.lin_index);

        for (int i = 0; i < 11; i++) {
            indexView.addLayoutView(R.layout.item_radio,"测试");
            linePagerIndex.addIndexView("测试"+i);
        }
        indexView.addIndex("测试");
    }
}
