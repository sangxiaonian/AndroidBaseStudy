package study.sang.androidbasestudy.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

/**
 * 这是个ViewPaget的类，利用ViewPager打造各种炫酷的广告轮播效果
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vp;
    private MyPagerAdapter adapter;

    private int[] imgRes = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,
    R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        vp = (ViewPager) findViewById(R.id.up);

        //设置pager间距
        vp.setPageMargin(20);

//        JLog.i("-----设置20---");
        //设置页面缓存数量
        vp.setOffscreenPageLimit(3);

        vp.setAdapter(new MyPagerAdapter());

    }


    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = new ImageView(ViewPagerActivity.this);
            img.setImageResource(imgRes[position]);
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
