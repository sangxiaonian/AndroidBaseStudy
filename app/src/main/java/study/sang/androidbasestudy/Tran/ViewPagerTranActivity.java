package study.sang.androidbasestudy.Tran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.ToastUtil;
import study.sang.androidbasestudy.viewpager.ViewPagerActivity;
import study.sang.androidbasestudy.viewpager.ViewPagerFullActivity;

public class ViewPagerTranActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_single,bt_multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_tran);

        bt_multiple= (Button) findViewById(R.id.pager_multiple);
        bt_single = (Button) findViewById(R.id.pager_single);

        bt_single.setOnClickListener(this);
        bt_multiple.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()){
            //多个
            case R.id.pager_multiple:
                c= ViewPagerActivity.class;
                break;

            //单个
            case R.id.pager_single:
                c= ViewPagerFullActivity.class;
                break;
        }

        if (c==null){
            ToastUtil.showTextToast(ViewPagerTranActivity.this,"还没有定义");
        }else {
            startActivity(new Intent(ViewPagerTranActivity.this,c));
        }
    }
}
