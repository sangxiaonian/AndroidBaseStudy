package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.StackView;

import study.sang.androidbasestudy.R;

public class StackViewActivity extends AppCompatActivity {

    private StackView sv;
    int[]ids = {R.drawable.liu,R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.e,R.drawable.d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view);
        sv= (StackView) findViewById(R.id.sv);
        sv.setAdapter(new MyAdapter());

    }

    public void next(View view){
        sv.showNext();
    }

    public void prev(View view){
        sv.showPrevious();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(StackViewActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(ids[position]);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }
    }


}
