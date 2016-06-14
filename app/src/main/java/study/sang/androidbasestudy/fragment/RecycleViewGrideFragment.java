package study.sang.androidbasestudy.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.RecycleDragActivity;
import study.sang.androidbasestudy.bean.ClickItem;
import study.sang.androidbasestudy.recycleview.adapter.CommonAdapter;
import study.sang.androidbasestudy.recycleview.adapter.ViewHolder;
import study.sang.androidbasestudy.utils.JLog;
import study.sang.androidbasestudy.utils.ToastUtil;


public class RecycleViewGrideFragment extends Fragment {


    private RecyclerView recyclerView;
    private View inflate;
    private List<ClickItem> mDatas;
    private CommonAdapter<ClickItem> adapter;

    private int c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        mDatas=((RecycleDragActivity)getActivity()).getmDatas();

        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ClickItem item = new ClickItem();
            item.title=i+"";
            mDatas.add(item);
        }
        adapter = new CommonAdapter<ClickItem>(getActivity(), R.layout.item_blank, mDatas) {
            @Override
            public void convert(final ViewHolder holder, final ClickItem s, final int position) {
                 Button button = holder.getView(R.id.tv_blank);
                button.setText(s.title);
                if (s.isClick){
                    button.setBackgroundColor(Color.RED);
                }else {
                    button.setBackgroundColor(Color.WHITE);
                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast(getActivity(),position+"  被点击了 "+s.title);
                        s.isClick=true;
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        };


        inflate = inflater.inflate(R.layout.fragment_recycle_view_gride, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.fra_gride);

        GridLayoutManager manager =new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.RIGHT|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT,0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(mDatas, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyDataSetChanged();
                return true;
            }

            //由于Gride本身并不合适华东删除，因此在这里，并不支持，仅仅支持拖动更改位置就好
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        helper.attachToRecyclerView(recyclerView);


        return inflate;
    }



}
