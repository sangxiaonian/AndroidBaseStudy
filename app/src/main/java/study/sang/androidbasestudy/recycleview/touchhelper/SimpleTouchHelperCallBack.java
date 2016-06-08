package study.sang.androidbasestudy.recycleview.touchhelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Description：这是一个用于RecycleView的拖动效果的类
 * Author：桑小年
 * Data：2016/6/7 16:20
 */
public class SimpleTouchHelperCallBack extends ItemTouchHelper.Callback {


    /**
     * 拖动的接口
     */
    private ItemTouchHelperInterface helperInterface;

    public SimpleTouchHelperCallBack(ItemTouchHelperInterface helperInterface){
        this.helperInterface = helperInterface;
    }


    //指定在支持的拖动和滑动事件
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        //制定拖动的方向
        int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        //制定滑动的方向
        int swipeFlag = ItemTouchHelper.START|ItemTouchHelper.END;

        //返回支持纵向拖动，横向滑动
        return makeMovementFlags(dragFlag,swipeFlag);
    }


    //返回为true时候，支持长按进入拖动事件
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //可以在View任意位置触摸事件发生时候，启用滑动操作
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    //拖动时候调用
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        helperInterface.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    //滑动时候调用
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        helperInterface.onItemDismiss(viewHolder.getAdapterPosition());
    }




}
