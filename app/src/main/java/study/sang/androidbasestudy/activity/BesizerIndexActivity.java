package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.view.bezier.IndexView;
import study.sang.androidbasestudy.view.progress.CircleWaveView;
import study.sang.androidbasestudy.view.index.BesizerChangePagerIndex;
import study.sang.androidbasestudy.view.index.BesizerPagerIndex;
import study.sang.androidbasestudy.view.index.LinePagerIndex;
import study.sang.androidbasestudy.view.index.TrianglePagerIndex;

public class BesizerIndexActivity extends AppCompatActivity {

    private EditText et;
    private IndexView indexView;
    private LinePagerIndex linePagerIndex;
    private TrianglePagerIndex trianglePagerIndex;
    private BesizerPagerIndex besizerPagerIndex;
    private BesizerChangePagerIndex changePagerIndex;
    private CircleWaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besizer_index);

        et = (EditText) findViewById(R.id.et_index);
        indexView = (IndexView) findViewById(R.id.index_view);
        linePagerIndex = (LinePagerIndex) findViewById(R.id.lin_index);
        trianglePagerIndex = (TrianglePagerIndex) findViewById(R.id.index_tria);
        besizerPagerIndex = (BesizerPagerIndex) findViewById(R.id.index_besizer);
        changePagerIndex = (BesizerChangePagerIndex) findViewById(R.id.change_besizer);
        waveView = (CircleWaveView) findViewById(R.id.wave);
        for (int i = 0; i < 11; i++) {
            indexView.addLayoutView(R.layout.item_radio, "测试");
            linePagerIndex.addIndexView("测试" + i);
            trianglePagerIndex.addIndexView("测试" + i);
            besizerPagerIndex.addIndexView("测试" + i);
            changePagerIndex.addIndexView("测试" + i);
        }
        indexView.addIndex("测试");
    }

    private int index;

    public void next(View view) {
        String et_content = et.getText().toString().trim();
        if (!TextUtils.isEmpty(et_content)) {
            index = Integer.parseInt(et_content);
        } else {
            index++;
        }

        linePagerIndex.setCurrentSelect(index);
        trianglePagerIndex.setCurrentSelect(index);
        besizerPagerIndex.setCurrentSelect(index);
        waveView.setProgerss((int) (index*0.1f));
    }


}
