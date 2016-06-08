package study.sang.androidbasestudy.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：
 * Author：桑小年
 * Data：2016/6/7 13:07
 */
public abstract class HeadAdapter<T> extends MultiItemsAdapter<T> {

    //头布局接口
    private HeaderSuppor mHeaderSuppor;

    //头布局对应的类型
    private static final int TYPE_HEAD = 0;

    private List<Integer> mHeards;

    //初始化接口
    private MultiItemTypeSupport<T> head = new MultiItemTypeSupport<T>() {
        @Override
        public int getLayoutId(int itemType) {
            if (itemType == TYPE_HEAD) {
                return mHeaderSuppor.getHeadLayouID();
            } else {
                return mLayoutID;
            }
        }

        @Override
        public int getItemViewType(int position, T t) {
            JLog.i("position:"+position+"  mHeards:"+mHeards.size());

                return mHeards.size()<=position ? 1 : TYPE_HEAD;
        }
    };

    /**
     * 这是recycleView头布局实现方法
     *
     * @param context       上下文
     * @param layoutID      一般布局的ID
     * @param datas         数据
     * @param mHeaderSuppor 头布局监听，必须实现
     */
    public HeadAdapter(Context context, int layoutID, List<T> datas, HeaderSuppor<T> mHeaderSuppor) {
        super(context, datas, null);
        mLayoutID = layoutID;
        this.mHeaderSuppor = mHeaderSuppor;
        mHeards = new ArrayList<>();
        mMultiItemTypeSupport = head;
        mHeards.add(this.mHeaderSuppor.getHeadLayouID());
    }

    //在这里获取对应position的type
    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, null);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + mHeards.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TYPE_HEAD!=getItemViewType(position)){
            position = getIndexForPosition(position);
            super.onBindViewHolder(holder, position);
        }


    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (TYPE_HEAD == viewType) {

        }
        return super.onCreateViewHolder(parent, viewType);
    }

    //格式化position
    public int getIndexForPosition(int position) {
        int head = 0;
        if (position < mHeards.size()) {
            head = position;
        } else {
            head = position - mHeards.size();
        }

        return head;
    }

    public void addHeads(int layoutID){
        mHeards.add(layoutID);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
