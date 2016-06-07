package study.sang.androidbasestudy.recycleview.adapter;

/**
 * Description：这是多条目RecyclerView的adapter中使用到的接口
 * @Author：桑小年
 * @Data：2016/6/7 11:52
 */
public interface MultiItemTypeSupport<T> {


    /**
     * 根据条目类型获取相应的ID
     * @param itemType recycleView中的条目类型
     * @return
     */
    int getLayoutId(int itemType);

    /**
     * 根据position 和数据来区分不同的itemType
     * @param position 该条目所在位置
     * @param t 对应的数据（泛型）
     * @return
     */
    int getItemViewType(int position, T t);
}
