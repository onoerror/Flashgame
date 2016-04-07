package com.quizmagic.flashgame;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private ImageView m_img_duke;
    private AnimationDrawable m_fram_animation;
    private TextView m_tv_message;

    private View m_view_logo;
    private TextView m_logo_name;
    private TextView m_view_message;
    private Button m_btn_go;
    private SeekBar m_skb_duration;
    private TextView m_tv_duration;

    private TypedArray mNbaLogos;//資源檔陣列
    private int mNbaLogosCount;//一共有多少張圖

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFrameAnimation();
        initNbaLogos();
        initSeekBar();
    }

    private void initNbaLogos(){
        mNbaLogos = getNbaLogos();
        mNbaLogosCount = getNbaLogos().length();
        m_view_logo.setBackground(mNbaLogos.getDrawable(0));
    }

    private TypedArray getNbaLogos(){
        //取得 nba logo drawables
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

        m_skb_duration = (SeekBar)findViewById(R.id.skb_duration);
        m_tv_duration = (TextView)findViewById(R.id.tv_duration);
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
    //Handler 處理代辦事項
    private Handler m_Handler = new Handler();

    private void animation5secs(){
        int delayMillis = 5 * 1000;//5秒
        //建立工作
        Runnable task = new Task();
        //交付工作，並在指定時間過後執行
        boolean result = m_Handler.postDelayed(task,delayMillis);

        m_tv_message.setText(result ?"交付成功":"交付失敗");
        m_fram_animation.start();//開始動畫

    }
    //按下go
    public void go(View view) {
        m_Handler.post(mStart);//立刻執行任務 隨機換圖
        m_Handler.postDelayed(mStop, 20_000);//3秒後執行任務 停止隨機換圖
        m_btn_go.setEnabled(false);//go按鈕不可以案
    }
    //將來被執行的工作(內部類別，存在於外部類別實體當中，像 引擎 存在於 車子 實體)
    private class Task implements Runnable{
        @Override
        public void run(){//工作內容
            //可直接存取 外部類別實體的成員(包含 Private 成員)
            m_fram_animation.stop();//結束動畫
            m_tv_message.setText("時間到");
        }
    }
    //建立任務物件
    private Start mStart = new Start();
    private Stop mStop = new Stop();
    //任務 開始 隨機換圖
    private class Start implements Runnable{
        @Override
        public void run(){
            //隨機產生 0 ~ 圖數量-1
            int index = (int)(Math.random()*mNbaLogosCount);
            //換圖
            m_view_logo.setBackground(mNbaLogos.getDrawable(index));
            //0.1秒後再執行一次本任務
            m_Handler.postDelayed(this, mDuration);
        }
    }
    //任務 停止 隨機換圖
    private class Stop implements Runnable{
        @Override
        public void run(){
            //取消任務 StartRandomTask
            m_Handler.removeCallbacks(mStart);
            //go按鈕恢復能按
            m_btn_go.setEnabled(true);
        }
    }

    private int mDuration;//隨機間隔換圖時間

    private void initSeekBar(){
        m_tv_duration.setText(String.valueOf((mDuration)));//顯示目前所設定的間隔時間
        m_skb_duration.setMax(20);//SeekBar 可拖曳的最大值，最小值固定為0(不可改)

        //設定 當 SeekBar被操作時要執行什麼
        m_skb_duration.setOnSeekBarChangeListener(new 屠龍刀());
    }

    private class 屠龍刀 implements SeekBar.OnSeekBarChangeListener{

        //拖曳 SeekBar
        @Override
        public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
            //progress 代表目前拖曳中所代表的值
            int duration = progress*50;
            m_tv_duration.setText(String.valueOf(duration));//更新目前顯示
            mDuration = duration;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }


}
