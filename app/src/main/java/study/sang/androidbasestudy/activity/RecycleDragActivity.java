package study.sang.androidbasestudy.activity;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.Tran.CustomActivity;
import study.sang.androidbasestudy.recycleview.adapter.CommonAdapter;
import study.sang.androidbasestudy.recycleview.adapter.ViewHolder;
import study.sang.androidbasestudy.view.imageView.GlideCircleTransform;

/**
 * 带有拖拽动画的Activity
 */
public class RecycleDragActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_drag);

        recyclerView = (RecyclerView) findViewById(R.id.recy_drag);

        for (int i = 0; i < 100; i++) {
            mDatas.add("开始拖拽"+i+"个条目");
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(new CommonAdapter<String>(this,R.layout.item_recy_ani,mDatas) {
            @Override
            public void convert(ViewHolder holder, String o, int position) {
               TextView tv=holder.getView(R.id.item_tv);
                tv.setText(0);

                ImageView img = holder.getView(R.id.item_img);
                Glide.with(RecycleDragActivity.this).load(R.drawable.d).transform(new GlideCircleTransform(RecycleDragActivity.this));
            }
        });

    }
}
