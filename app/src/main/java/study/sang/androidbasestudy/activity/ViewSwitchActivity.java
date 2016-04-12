package study.sang.androidbasestudy.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

public class ViewSwitchActivity extends AppCompatActivity {

    /**
     * 没屏幕的数目
     */
    private static final int NUMBER_PER_SCREN = 30;
    /**
     * 保存所有程序的集合
     */
    private List<DataItem> items = new ArrayList<>();
    /**
     * 当前屏幕数
     */
    private int screenNo = -1;
    /**
     * 总屏幕数
     */
    private int screenCount;

    private ViewSwitcher viewSwitcher;
    private GridView gv;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_switch);

        for (int i = 0; i < 200; i++) {
            DataItem item = new DataItem();
            item.dataName = "name" + i;
            item.drawable = getResources().getDrawable(R.mipmap.ic_launcher);

            items.add(item);
        }

        screenCount = (items.size() % NUMBER_PER_SCREN == 0 ? items.size() / NUMBER_PER_SCREN : items.size() / NUMBER_PER_SCREN + 1);


        inflater = LayoutInflater.from(this);

        viewSwitcher = (ViewSwitcher) findViewById(R.id.vs);
        viewSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {

                return inflater.inflate(R.layout.slidelistview, null);
            }
        });
        next(null);

    }


    public void prev(View view) {
        JLog.i("----------" + screenNo + "-----------" + screenCount);
        screenNo--;
        if (screenNo >= 0) {

            JLog.i(screenNo);
            viewSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
            viewSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
            ((GridView) viewSwitcher.getNextView()).setAdapter(adapter);
            viewSwitcher.showPrevious();
        } else {
            screenNo = 0;
        }
    }

    public void next(View view) {
        JLog.i("----------" + screenNo + "-----------" + screenCount);
        screenNo++;
        if (screenNo < screenCount) {

            JLog.i(screenNo);
            viewSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
            viewSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
            ((GridView) viewSwitcher.getNextView()).setAdapter(adapter);
            viewSwitcher.showNext();
        } else {
            screenNo = screenCount - 1;
        }
    }

    private MyAdapter adapter = new MyAdapter();

    public static class DataItem {
        public String dataName;
        public Drawable drawable;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            JLog.i("==========================" + items.size() % NUMBER_PER_SCREN);
            if (items.size() % NUMBER_PER_SCREN != 0 && screenNo == screenCount - 1) {
                return items.size() % NUMBER_PER_SCREN;
            } else {
                return NUMBER_PER_SCREN;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = inflater.inflate(R.layout.labelicon, null);
            } else {
                view = convertView;
            }

            ImageView img = (ImageView) view.findViewById(R.id.imageview);
            TextView title = (TextView) view.findViewById(R.id.textview);
            int index = position + screenNo * NUMBER_PER_SCREN;

            img.setImageDrawable(items.get(index).drawable);
            title.setText(items.get(index).dataName);

            return view;
        }
    }
}

