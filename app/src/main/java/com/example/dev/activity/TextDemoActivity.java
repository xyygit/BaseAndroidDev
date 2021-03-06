package com.example.dev.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.adev.activity.BaseActivity;
import com.base.adev.utils.ScreenUtil;
import com.base.adev.view.MarqueeTextView;
import com.base.adev.view.NoticeView;
import com.example.dev.R;
import com.example.dev.view.NoticeMF;
import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TextDemoActivity extends BaseActivity {

    private String title;
    private MarqueeTextView marqueeTextView;//跑马灯控件
    private NoticeView noticeView;//广告条控件
    private TextView mTextView;//LED文字控件
    private final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。",
            "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
    private MarqueeView marqueeView2;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_text_demo);

    }

    @Override
    protected void initView() {
        marqueeTextView = (MarqueeTextView) findViewById(R.id.tvScroll);
        noticeView = (NoticeView) findViewById(R.id.switcherView);
        mTextView = (TextView) findViewById(R.id.main_clock_time);
    }

    @Override
    protected void initLogic() {
        mToolbar.setTitle(title);
        int screenWidth = ScreenUtil.getScreenWidthPix(context);
        ViewGroup.MarginLayoutParams margin1 = new ViewGroup.MarginLayoutParams(
                marqueeTextView.getLayoutParams());
        margin1.setMargins(20, 0, 0, 0);//设置滚动区域位置：在左边距400像素，顶边距10像素的位置
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(margin1);
        layoutParams1.height = 158;//设置滚动区域高度
        layoutParams1.width = screenWidth; //设置滚动区域宽度

        marqueeTextView.setLayoutParams(layoutParams1);
        marqueeTextView.setScrollWidth(screenWidth);
        marqueeTextView.setCoordinateY(140);
        marqueeTextView.setCurrentPosition(screenWidth);//设置滚动信息从滚动区域的右边出来
        marqueeTextView.setSpeed(3);

        marqueeTextView.setText("我是滚动字幕啊12345，我是滚动字幕啊12345，我是滚动字幕啊12345");

        final ArrayList<String> strs = new ArrayList<>();
        strs.add("哎呦，不错哦");
        strs.add("天道酬勤");
        strs.add("上善若水");
        strs.add("Hello world");
        noticeView.setResource(strs);

        noticeView.setOnItemClick(new NoticeView.OnItemClick() {
            @Override
            public void Click(int index) {
                showShortToast(strs.get(index));
            }
        });

        noticeView.start(2000);

        marqueeView2 = (MarqueeView) findViewById(R.id.marqueeView2);
        final MarqueeFactory<TextView, String> marqueeFactory2 = new NoticeMF(this);
        marqueeFactory2.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                showShortToast(holder.data);

            }
        });
        marqueeFactory2.setData(datas);
        marqueeView2.setMarqueeFactory(marqueeFactory2);
        marqueeView2.startFlipping();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(mTimeRefresher);
        marqueeView2.startFlipping();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(mTimeRefresher);
        marqueeView2.stopFlipping();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noticeView.onDestroy();
    }

    private static final int REFRESH_DELAY = 500;

    private final Handler handler = new Handler();
    private final Runnable mTimeRefresher = new Runnable() {

        @Override
        public void run() {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
            final Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            mTextView.setText(str);
            handler.postDelayed(this, REFRESH_DELAY);
        }
    };
}
