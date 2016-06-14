package study.sang.androidbasestudy.view.MyrecycleView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;
import study.sang.androidbasestudy.utils.ToastUtil;

/**
 * Created by ping on 2016/4/22.
 */
public class AllAdapter extends RecyclerView.Adapter {

    /**
     * 普通
     */
    public final static int LINER = 0;
    /**
     * GrideView
     */
    public final static int GRID = 1;
    /**
     * 瀑布六
     */
    public final static int STATE = 2;


    /**
     * 布局排列方式
     */
    private int mOr = LinearLayoutManager.VERTICAL;

    private int mGridSpan = 2;

    private int type;

    private List data = new ArrayList();
    private Context context;

    private List<View> headViews = new ArrayList<>();
    private List<View> footViews = new ArrayList<>();


    public AllAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    private int anInt;

    /**
     * 设置显示的数据
     *
     * @param data
     */
    public void setData(List data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /***
     * 添加头布局
     *
     * @param view 头布局中显示的view
     */
    public void addHeader(View view) {
        headViews.add(view);

    }

    /**
     * 添加脚布局
     *
     * @param footView
     */
    public void addFoot(View footView) {
        footViews.add(footView);

    }

    /**
     * 设置排布方向 默认为竖直方向
     *
     * @param orientation
     */
    public void setmOrientation(int orientation) {
        this.mOr = orientation;
    }

    /**
     * 设置GridView的列数，默认为2
     *
     * @param spanCount
     */
    public void setmGridSpan(int spanCount) {
        this.mGridSpan = spanCount;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        //脚布局
        if (viewType >= getItemCount() - footViews.size()) {
            holder = new FootView(footViews.get(viewType - headViews.size() - data.size()));

            //头布局
        } else if (viewType < headViews.size()) {
            holder = new HeadView(headViews.get(viewType));
        } else {
            holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        JLog.i(anInt + "-----点击-----" + position);
        if (getItemViewType(position) == headViews.size() + 1) {
            ((MyViewHolder) holder).tv.setText((CharSequence) data.get(position - headViews.size()));
            ((MyViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInt = holder.getPosition();
                    data.remove(anInt);
                    ToastUtil.showToast(context, anInt + ">>>>>>>>>" + data.get(anInt));
                    notifyItemRemoved(anInt);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + headViews.size() + footViews.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position < headViews.size()) {
            return position;
        } else if (position >= headViews.size() + data.size()) {

            return position;
        } else {
            return headViews.size() + 1;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager = null;

        //gridView
        if (type == GRID) {
            layoutManager = new GridLayoutManager(context, mGridSpan);

            /*************设置GRIDview头布局****************/

            final LinearLayoutManager finalLayoutManager = layoutManager;
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    //头布局
                    if (position < headViews.size()) {
                        return ((GridLayoutManager) finalLayoutManager).getSpanCount();

                        //脚布局
                    } else if (position >= getItemCount() - footViews.size()) {
                        return ((GridLayoutManager) finalLayoutManager).getSpanCount();

                        //普通布局
                    } else {
                        return 1;
                    }

                }
            });
            layoutManager.setOrientation(mOr);
            recyclerView.setLayoutManager(layoutManager);

            //listView
        } else if (type == LINER) {
            layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(mOr);
            recyclerView.setLayoutManager(layoutManager);
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    class HeadView extends RecyclerView.ViewHolder {


        public HeadView(View itemView) {
            super(itemView);
        }
    }

    static class FootView extends RecyclerView.ViewHolder {

        public FootView(View itemView) {
            super(itemView);
        }
    }
}
