package com.xgl.libs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xgl.libs.R;

/**
 * @Description: 自定义标题
 * @Author: xingguo.lei@luckincoffee.com
 * @Date: 2019-08-24 15:01
 */
public class TitleView extends FrameLayout {
    private TextView title;
    private TextView leftTitle;
    private TextView rightTitle;
    private ImageView leftTitleImage;
    private ImageView rightTitleImage;
    private LinearLayout leftGroup;
    private LinearLayout rightGroup;

    private OnClickLeftIconListener mOnClickLeftIconListener;
    private OnClickRightIconListener mOnClickRightIconListener;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
        initListener();
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_title, this, true);
        title = findViewById(R.id.tv_title);
        leftTitle = findViewById(R.id.tv_left_title);
        rightTitle = findViewById(R.id.tv_right_title);
        leftTitleImage = findViewById(R.id.iv_left_icon);
        rightTitleImage = findViewById(R.id.tv_right_icon);
        leftGroup = findViewById(R.id.ll_left);
        rightGroup = findViewById(R.id.ll_right);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
        title.setText(ta.getString(R.styleable.CustomTitleView_title));
        title.setTextColor(ta.getColor(R.styleable.CustomTitleView_titleColor, Color.WHITE));
        title.setTextSize(ta.getDimensionPixelSize(R.styleable.CustomTitleView_titleSize, 28));
        leftTitle.setText(ta.getString(R.styleable.CustomTitleView_leftTitle));
        rightTitle.setText(ta.getString(R.styleable.CustomTitleView_rightTitle));
        leftTitleImage.setImageResource(ta.getResourceId(R.styleable.CustomTitleView_leftImage, 0));
        rightTitleImage.setImageResource(ta.getResourceId(R.styleable.CustomTitleView_rightImage, 0));
        leftTitle.setTextSize(ta.getDimensionPixelSize(R.styleable.CustomTitleView_leftTitleSize, 18));
        rightTitle.setTextSize(ta.getDimensionPixelSize(R.styleable.CustomTitleView_rightTitleSize, 18));
        ta.recycle();
    }

    private void initListener() {
        leftGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickLeftIconListener != null) {
                    mOnClickLeftIconListener.onClickLeft(v);
                }
            }
        });

        rightGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickRightIconListener != null) {
                    mOnClickRightIconListener.onClickRight(v);
                }
            }
        });
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public TextView getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle.setText(leftTitle);
    }

    public TextView getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle.setText(rightTitle);
    }

    public void setLeftTitleImage(int resId) {
        this.leftTitleImage.setImageResource(resId);
    }

    public void setRightTitleImage(int resId) {
        this.rightTitleImage.setImageResource(resId);
    }

    public void setOnClickTitleIconListener(OnClickLeftIconListener onClickLeftIconListener) {
        this.mOnClickLeftIconListener = onClickLeftIconListener;
    }

    public void setOnClickRightIconListener(OnClickRightIconListener onClickRightIconListener) {
        this.mOnClickRightIconListener = onClickRightIconListener;
    }

    public interface OnClickLeftIconListener {

        void onClickLeft(View view);
    }

    public interface OnClickRightIconListener {

        void onClickRight(View view);
    }

}
