package com.quizmagic.flashgame;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private ImageView m_img_duke;
    private AnimationDrawable m_fram_animation;
    private TextView m_tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFrameAnimation();
    }

    private void initView(){
        m_img_duke = (ImageView) findViewById(R.id.img_duke);
        m_tv_message = (TextView)findViewById(R.id.tv_message);
    }

    private void initFrameAnimation(){
        m_img_duke.setBackgroundResource(R.drawable.flashgame);
        m_fram_animation = (AnimationDrawable)m_img_duke.getBackground();
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.btn_start:
                m_fram_animation.start();
                break;
            case R.id.btn_stop:
                m_fram_animation.stop();
                break;
            case R.id.btn_5_secs:
                animation5secs();
                break;
        }
    }

    private Handler m_Handler = new Handler();

    private void animation5secs(){
        int delayMillis = 5 * 1000;
        Runnable task = new Task();

        boolean result = m_Handler.postDelayed(task,delayMillis);

        m_tv_message.setText(result ?"交付成功":"交付失敗");
        m_fram_animation.start();

    }

    private class Task implements Runnable{
        @Override
        public void run(){

            m_fram_animation.stop();
            m_tv_message.setText("時間到");
        }
    }
}
