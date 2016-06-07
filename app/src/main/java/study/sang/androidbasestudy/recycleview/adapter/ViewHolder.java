package study.sang.androidbasestudy.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description：这是一个RecycleView类的通用holder，通过Id来获取控件
 * Author：桑小年
 * Data：2016/6/7 10:16
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    //用来储存控件的集合
    private SparseArray<View> mViews;

    //返回的控件
    private View mConverView;

    //上下文
    private Context mContext;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConverView = itemView;
        mViews = new SparseArray<>();
    }

    /**
     * 通过ViewId来获取控件
     * @param viewID 控件的ID
     * @param <T> 返回的类型
     * @return 返回对应ID的控件
     */
    public <T extends View> T getView(int viewID){
        View view = mViews.get(viewID);
        if (view==null){
            view = mConverView.findViewById(viewID);
            mViews.put(viewID,view);
        }

        return (T) view;
    }

    public static ViewHolder get(Context context, ViewGroup parent, int layoutID){
        View itemView = LayoutInflater.from(context).inflate(layoutID,parent,false);

       return new ViewHolder(context,itemView,parent);
    }
}
