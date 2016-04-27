package study.sang.androidbasestudy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.camera.CameraPreviewInApiDemo;
import study.sang.androidbasestudy.camera.CustomCamera;
import study.sang.androidbasestudy.utils.JLog;

public class CameraActivity extends AppCompatActivity {

    private static int CAMERA_CODE1 = 1;
    private static int CAMERA_CODE2 = 2;
    private String mFilePath = "";
    private ImageView mImageViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageViewShow = (ImageView) findViewById(R.id.iv_show);
        String tempPath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = tempPath + "/" + "test1.png";
    }

    public void camera1(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, CAMERA_CODE1);
    }

    public void camera2(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CODE2);
    }

    public void camera3(View view) {
        startActivity(new Intent(this, CustomCamera.class));
    }

    public void camera4(View view) {
        startActivity(new Intent(this, CameraPreviewInApiDemo.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CODE1) {
                /**
                 * 通过暂存路径取得图片
                 */
                FileInputStream fis = null;
                Bitmap bitmap = null;
                try {
                    fis = new FileInputStream(mFilePath);
                    bitmap = BitmapFactory.decodeStream(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mImageViewShow.setImageBitmap(bitmap);
            } else if (requestCode == CAMERA_CODE2) {
                /**
                 * 通过data取得图片
                 */
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                mImageViewShow.setImageBitmap(bitmap);
            }
        }
    }
}
