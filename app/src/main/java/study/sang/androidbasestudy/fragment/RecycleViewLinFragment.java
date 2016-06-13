package study.sang.androidbasestudy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.activity.RecycleDragActivity;
import study.sang.androidbasestudy.recycleview.adapter.CommonAdapter;
import study.sang.androidbasestudy.recycleview.adapter.ViewHolder;


public class RecycleViewLinFragment extends Fragment {


    private RecyclerView recyclerView;
    private View inflate;
    private List<String> mDatas;
    private CommonAdapter<String> adapter;

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
            mDatas.add(i + "");
        }

        adapter = new CommonAdapter<String>(getActivity(), R.layout.item_blank, mDatas) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {

            }
        };


        inflate = inflater.inflate(R.layout.fragment_recycle_view_gride, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.fra_gride);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(mDatas, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            //由于Gride本身并不合适华东删除，因此在这里，并不支持，仅仅支持拖动更改位置就好
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mDatas.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });
        helper.attachToRecyclerView(recyclerView);


        return inflate;
    }



}
