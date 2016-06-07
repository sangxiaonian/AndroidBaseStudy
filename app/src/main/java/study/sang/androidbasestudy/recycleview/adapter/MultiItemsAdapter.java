package study.sang.androidbasestudy.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Description： 这是一个适用于多条目情况的recycleView的适配器，由于这里面的ViewHolder是万能的，仅仅依赖一个LayoutID，因此，可以根据LayoutId来区分条目类型，在这里，引入一个接口，来实现相应的方法
 * @Author： 桑小年
 * @Data： 2016/6/7 11:49
 */
public abstract class MultiItemsAdapter<T> extends CommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;


    /**
     * 由于要根据不同的ID来判断条目类型，这里就不能一开始就直接穿进去一个布局，而是根据接口来实现
     * @param context 上下文
     * @param datas  要显示处理的数据
     * @param mMultiItemTypeSupport 接口，必须实现
     */
    public MultiItemsAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> mMultiItemTypeSupport) {
        super(context, -1, datas);
        this.mMultiItemTypeSupport = mMultiItemTypeSupport;
    }

    //获取Item的类型
    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position,mDatas.get(position));
    }

    //由于要根据position来确定不同的ViewHolder,因此这个方法必须重写
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        JLog.i("viewType:::"+viewType +"   layoutId:"+layoutId);
        return ViewHolder.get(context,parent,layoutId);
    }


}
