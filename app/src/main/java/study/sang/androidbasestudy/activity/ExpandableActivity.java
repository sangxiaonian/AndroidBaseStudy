package study.sang.androidbasestudy.activity;

import android.app.ExpandableListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.sang.androidbasestudy.MainActivity;
import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.ToastUtil;

public class ExpandableActivity extends ExpandableListActivity {

    private int[] icans = {R.drawable.p,R.drawable.t,R.drawable.z};
    private String [] items = {"人族","虫族","兽族"};
    private String[][] contents = {{"皇子","盖伦","剑圣","蛮子","寒冰"},{"大嘴","大虫子"},{"狗头","狗熊","鳄鱼","螃蟹"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_expandable);
        setListAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return items.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return contents[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return items[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return contents[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView==null) {
                LinearLayout linearLayout = new LinearLayout(ExpandableActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                ImageView imageView = new ImageView(ExpandableActivity.this);
                imageView.setImageResource(icans[groupPosition]);

                TextView textView =new TextView(ExpandableActivity.this);
                textView.setText((String)getGroup(groupPosition));
                linearLayout.addView(imageView);
                linearLayout.addView(textView);


            convertView=linearLayout;
            return  convertView;
        }else {
            return convertView;
        }


        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = new TextView(ExpandableActivity.this);
            textView.setText((String) getChild(groupPosition,childPosition));
            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            ToastUtil.showTextToast(ExpandableActivity.this, (String) getChild(groupPosition,childPosition));
            return true;
        }


    }

}
