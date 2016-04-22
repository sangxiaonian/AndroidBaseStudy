package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.ToastUtil;
import study.sang.androidbasestudy.view.MyrecycleView.AllAdapter;

public class RecycleActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView mRecyclerView;

    private List<String> datas=new ArrayList();

    private Button bt_setData,bt_grid,bt_state,bt_add,bt_delete;
   private AllAdapter allAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        mRecyclerView= (RecyclerView) findViewById(R.id.my_recycle);
        bt_setData= (Button) findViewById(R.id.bt_setdata);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_delete= (Button) findViewById(R.id.bt_delet);
        bt_grid= (Button) findViewById(R.id.bt_grid);
        bt_state= (Button) findViewById(R.id.bt_state);



        bt_setData.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_state.setOnClickListener(this);
        bt_grid.setOnClickListener(this);

        for (int i=0; i<100;i++){
            datas.add("测试数据"+i);
        }


        allAdapter = new AllAdapter(this,AllAdapter.GRID);
        allAdapter.addHeader(View.inflate(this,R.layout.item_head,null));
        allAdapter.addFoot(View.inflate(this,R.layout.activity_picker,null));
        mRecyclerView.setAdapter(allAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.bt_setdata:
                allAdapter.setData(datas);
                break;
            case R.id.bt_grid:
               allAdapter.setmGridSpan(1);
                mRecyclerView.setAdapter(allAdapter);
                break;
            case R.id.bt_add:

                mRecyclerView.scrollToPosition(0);
                break;
            case R.id.bt_delet:

                break;
            case R.id.bt_state:
                RecyclerView.LayoutManager l = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(l);
                break;
        }
    }
}
