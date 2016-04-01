package com.quizmagic.flashgame;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private ImageView m_img_duke;
    private AnimationDrawable m_fram_animation;
    private TextView m_tv_message;

    private View m_view_logo;
    private TextView m_logo_name;
    private TextView m_view_message;
    private Button m_btn_go;

    private TypedArray mNbaLogos;
    private int mNbaLogosCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFrameAnimation();
        initNbaLogos();
    }

    private void initNbaLogos(){
        mNbaLogos = getNbaLogos();
        mNbaLogosCount = getNbaLogos().length();
        m_view_logo.setBackground(mNbaLogos.getDrawable(0));
    }

    private TypedArray getNbaLogos(){

        TypedArray logos = getResources().obtainTypedArray(R.array.nba_logos);
        return logos;
    }

    private void initView(){
        m_img_duke = (ImageView) findViewById(R.id.img_duke);
        m_tv_message = (TextView)findViewById(R.id.tv_message);

        m_view_logo = findViewById(R.id.view_logo);
        m_logo_name = (TextView)findViewById(R.id.tv_logo_name);
        m_tv_message = (TextView)findViewById(R.id.view_message);
        m_btn_go = (Button)findViewById(R.id.m_btn_go);
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

    public void go(View view) {
        m_Handler.post(mStart);
        m_Handler.postDelayed(mStop, 3000);
        m_btn_go.setEnabled(false);
    }

    private class Task implements Runnable{
        @Override
        public void run(){

            m_fram_animation.stop();
            m_tv_message.setText("時間到");
        }
    }

    private Start mStart = new Start();
    private Stop mStop = new Stop();

    private class Start implements Runnable{
        @Override
        public void run(){

            int index = (int)(Math.random()*mNbaLogosCount);

            m_view_logo.setBackground(mNbaLogos.getDrawable(index));

            m_Handler.postDelayed(this, 10);
        }
    }
    private class Stop implements Runnable{
        @Override
        public void run(){

            m_Handler.removeCallbacks(mStart);

            m_btn_go.setEnabled(true);
        }
    }
}
