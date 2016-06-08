package study.sang.androidbasestudy.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import study.sang.androidbasestudy.recycleview.touchhelper.ItemTouchHelperInterface;

/**
 * Description：这是一个通用的RecycleView的adapter适配器，由于每次使用的数据的Bean肯顶不同，因此引入泛型来代表我们的数据，内部则通过一个List来代表我的数据
 *              同时，这个适配器并不适用于多条目的情况，多条目使用 MultiItemsAdapter
 * Author：桑小年
 * Data：2016/6/7 10:27
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter implements ItemTouchHelperInterface {

    //用来储存使用的数据
    protected List<T> mDatas;
    protected int mLayoutID;
    protected Context context;
    protected LayoutInflater mInflater;


    public CommonAdapter(Context context, int layoutID, List<T> datas) {
        this.context=context;
        mLayoutID = layoutID;
        mDatas=datas;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = ViewHolder.get(context,parent,mLayoutID);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((ViewHolder) holder,mDatas.get(position),position);
    }





    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 在绑定viewholder中进行的对item控件进行控制操作的方法，必须重写，因此抽象化
     * @param holder
     * @param t
     */
    public abstract void convert(ViewHolder holder, T t,int position);


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
