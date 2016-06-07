package study.sang.androidbasestudy.recycleview.adapter;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import study.sang.androidbasestudy.recycleview.touchhelper.ItemTouchHelperInterface;

/**
 * Description：带有拖动动画的万能适配器
 *
 * @Author：桑小年
 * @Data：2016/6/7 16:44
 */
public abstract class DragAdapter<T> extends CommonAdapter<T> implements ItemTouchHelperInterface {


    public DragAdapter(Context context, int layoutID, List<T> datas) {
        super(context, layoutID, datas);
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
