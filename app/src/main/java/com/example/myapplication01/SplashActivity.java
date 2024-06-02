package com.example.myapplication01;

import static com.example.myapplication01.util.RoundRectImageView.getRoundBitmapByShader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication01.util.CTextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ImageView iv = findViewById(R.id.iv_0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.openpage_insert);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap, 500,300,20, 3);
        iv.setImageBitmap(outBitmap);
        try {
            CTextView cTextView = (CTextView) findViewById(R.id.ct);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);
            if (cTextView != null) {
                cTextView.setText("一站式导航", AnimationUtils.loadAnimation(this, R.anim.myanim), 300);
            }
            else {
                // 如果cTextView为null，记录错误或进行其他错误处理
                Log.e("SplashActivity", "CTextView is null");
            }
        }
        catch (NullPointerException e) {
            // 处理NullPointerException
            Log.e("SplashActivity", "NullPointerException caught", e);
        } catch (Exception e) {
            // 捕获其他类型的异常
            Log.e("SplashActivity", "Exception caught", e);
        }
        //隐藏状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_splash);
        //创建子线程
        Thread mThread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);//使程序休眠3秒
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();//启动线程
    }
}