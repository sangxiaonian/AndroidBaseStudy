package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.recycleview.adapter.CommonAdapter;
import study.sang.androidbasestudy.recycleview.adapter.DragAdapter;
import study.sang.androidbasestudy.recycleview.adapter.HeadAdapter;
import study.sang.androidbasestudy.recycleview.adapter.HeaderSuppor;
import study.sang.androidbasestudy.recycleview.adapter.MultiItemTypeSupport;
import study.sang.androidbasestudy.recycleview.adapter.MultiItemsAdapter;
import study.sang.androidbasestudy.recycleview.adapter.ViewHolder;
import study.sang.androidbasestudy.recycleview.touchhelper.ItemTouchHelperInterface;
import study.sang.androidbasestudy.recycleview.touchhelper.SimpleTouchHelperCallBack;
import study.sang.androidbasestudy.utils.JLog;
import study.sang.androidbasestudy.view.imageView.GlideCircleTransform;

/**
 * RecycleView 的万能适配器演示页面
 */
public class Recycle_Ani_Activity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<String> mDatas;
    private GridLayoutManager manager;
    private Button bt_com, bt_head, bt_mm, bt_drag;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle__ani_);
        bt_com = (Button) findViewById(R.id.recy_com);
        bt_head = (Button) findViewById(R.id.recy_head);
        bt_mm = (Button) findViewById(R.id.recy_mum);
        bt_drag = (Button) findViewById(R.id.recy_drag);

        bt_drag.setOnClickListener(this);
        bt_com.setOnClickListener(this);
        bt_mm.setOnClickListener(this);
        bt_head.setOnClickListener(this);


        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add(i + "");
        }



    }
    RecyclerView.Adapter adapter = null;

    @Override
    public void onClick(View v) {



        switch (v.getId()) {
            //一般的布局
            case R.id.recy_com:
                JLog.i("被电击了");
                adapter = new CommonAdapter<String>(this, R.layout.item_recy_ani, mDatas) {
                    @Override
                    public void convert(ViewHolder holder, String o, int position) {
                        TextView view = holder.getView(R.id.item_tv);
                        view.setText(o);

                        ImageView img = holder.getView(R.id.item_img);
                        //这里利用Glide来加载图片，并且实现圆形图片，在transform来实现
                        Glide.with(Recycle_Ani_Activity.this).load(R.drawable.full2).transform(new GlideCircleTransform(Recycle_Ani_Activity.this)).placeholder(R.drawable.f)
                                .error(R.drawable.f).into(img);
                    }
                };
                break;
            //头布局
            case R.id.recy_head:
                adapter = new HeadAdapter<String>(Recycle_Ani_Activity.this, R.layout.item_recy_ani, mDatas, new HeaderSuppor<String>() {
                    @Override
                    public int getHeadLayouID() {

                        return R.layout.item_head;
                    }
                }) {
                    @Override
                    public void convert(ViewHolder holder, String s, int position) {

                        TextView view = holder.getView(R.id.item_tv);
                        view.setText(s);
                        ImageView img = holder.getView(R.id.item_img);
                        //这里利用Glide来加载图片，并且实现圆形图片，在transform来实现
                        Glide.with(Recycle_Ani_Activity.this).load(R.drawable.full2).transform(new GlideCircleTransform(Recycle_Ani_Activity.this)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.f)
                                .error(R.drawable.f).into(img);
                    }
                };
                break;
            //多条目
            case R.id.recy_mum:
                adapter = new MultiItemsAdapter<String>(Recycle_Ani_Activity.this, mDatas, new MultiItemTypeSupport<String>() {
                    @Override
                    public int getLayoutId(int itemType) {
                        if (itemType == 0) {
                            return R.layout.item_head;
                        }
                        return R.layout.item_recy_ani;
                    }

                    @Override
                    public int getItemViewType(int position, String o) {
                        if (position % 10 == 0) {
                            return 0;
                        } else
                            return 1;
                    }
                }) {
                    @Override
                    public void convert(ViewHolder holder, String o, int position) {
                        if (getItemViewType(position) == 1) {
                            TextView view = holder.getView(R.id.item_tv);
                            view.setText(o);
                        }
                    }
                };
                break;
            default:
                break;
        }

        recyclerView = null;
        recyclerView = (RecyclerView) findViewById(R.id.recy_ani);
        manager = new GridLayoutManager(Recycle_Ani_Activity.this,3);

        recyclerView.setLayoutManager(manager);
        //这里实现拖拽的功能
//        ItemTouchHelper helper = new ItemTouchHelper(new SimpleTouchHelperCallBack((ItemTouchHelperInterface) adapter));
//        helper.attachToRecyclerView(recyclerView);

        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

    }
}
