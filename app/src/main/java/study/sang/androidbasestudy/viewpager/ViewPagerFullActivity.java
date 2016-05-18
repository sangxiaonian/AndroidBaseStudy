package study.sang.androidbasestudy.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.viewpager.transformer.DepthPageTransformer;
import study.sang.androidbasestudy.viewpager.transformer.RotaPagerTransformer;

public class ViewPagerFullActivity extends AppCompatActivity {

    private ViewPager vp;

    int[] imgs = {R.drawable.full1, R.drawable.full2, R.drawable.full3, R.drawable.full4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_full);
        vp = (ViewPager) findViewById(R.id.vp_full);
        vp.setPageMargin(20);
//        vp.setPageTransformer(true,new RotaPagerTransformer());
        vp.setPageTransformer(true,new DepthPageTransformer());
        vp.setAdapter(new FullAdapter());

    }

    class FullAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView = new ImageView(ViewPagerFullActivity.this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imgs[position]);
            container.addView(imageView);


            return imageView;
        }
    }
}
