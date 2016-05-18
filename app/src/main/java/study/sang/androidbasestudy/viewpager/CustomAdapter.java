package study.sang.androidbasestudy.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by ping on 2016/5/18.
 */
public class CustomAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
