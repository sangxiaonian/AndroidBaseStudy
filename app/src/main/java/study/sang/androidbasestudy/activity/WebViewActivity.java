package study.sang.androidbasestudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import study.sang.androidbasestudy.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView= (WebView) findViewById(R.id.web);
        et= (EditText) findViewById(R.id.edit_url);
//        mWebView.setWebViewClient(new WebViewClient(){
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }

    public void jump(View view){
        String url = et.getText().toString().trim();
        mWebView.loadUrl(url);

    }

}
