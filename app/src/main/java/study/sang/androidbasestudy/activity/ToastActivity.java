package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import study.sang.androidbasestudy.R;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

    }

    public void simple(View view){
        Toast.makeText(this,"简单的吐司",Toast.LENGTH_SHORT).show();
    }

    public void img(View view){
        Toast toast = new Toast(this);

        toast.setGravity(Gravity.CENTER,100,0);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.watch));

        TextView textView = new TextView(this);
        textView.setText("AAAAAAAAA");
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        toast.setView(linearLayout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }















}
