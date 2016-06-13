package study.sang.androidbasestudy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.fragment.RecycleViewGrideFragment;
import study.sang.androidbasestudy.fragment.RecycleViewLinFragment;
import study.sang.androidbasestudy.recycleview.adapter.CommonAdapter;

/**
 * 带有拖拽动画的Activity
 */
public class RecycleDragActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> mDatas;
    private CommonAdapter<String> adapter;
    private FrameLayout fl;

    private Fragment grid, lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_drag);
        fl = (FrameLayout) findViewById(R.id.frag);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add(i + "");
        }

        grid = new RecycleViewGrideFragment();

        lin = new RecycleViewLinFragment();

    }

    public void grid(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, grid).commit();
    }

    public void lin(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, lin).commit();
    }

    public List<String> getmDatas() {
        return mDatas;
    }

}
