package study.sang.androidbasestudy.recycleview.touchhelper;

/**
 * 这是拖动事件中所使用的类，用来通知底层数据的变化
 */
public interface ItemTouchHelperInterface {

    /**
     * recycleView 拖动时候调用
     * @param fromPosition 开始拖动的位置
     * @param toPosition 结束时候的位置
     */
    void onItemMove(int fromPosition, int toPosition);


    /**
     * 在移除条目的时候调用
     * @param position 条目所在的位置
     */
    void onItemDismiss(int position);

}
