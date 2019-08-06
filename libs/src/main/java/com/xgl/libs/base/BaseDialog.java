package com.xgl.libs.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xgl.libs.R;
import com.xgl.libs.utils.ScreenUtils;

/**
 * Created by leixingguo on 2017/3/16.
 */

public abstract class BaseDialog extends DialogFragment {

    private TextView mTextTitle;
    private FrameLayout mFrameContent;
    private LinearLayout mLinearBottom;
    private LinearLayout mLinearTop;
    private TextView mTextConfirm;
    private TextView mTextCancel;

    protected int screenHeight;
    protected int screenWidth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_base_dialog, container, false);
        mTextTitle = (TextView) view.findViewById(R.id.text_title);
        mFrameContent = (FrameLayout) view.findViewById(R.id.frame_content);
        mLinearBottom = (LinearLayout) view.findViewById(R.id.linear_bottom);
        mTextConfirm = (TextView) view.findViewById(R.id.text_confirm);
        mTextCancel = (TextView) view.findViewById(R.id.text_cancel);
        mLinearTop = (LinearLayout) view.findViewById(R.id.linear_top);

        mFrameContent.addView(getChildView());

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screenHeight = ScreenUtils.getScreenHeight(getActivity());
        screenWidth = ScreenUtils.getScreenWidth(getActivity());
        mTextConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConfirm();
            }
        });
        mTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(getLayoutWidth(), getLayoutHeight());
    }

    protected int getLayoutWidth() {
        int dialogWidth = screenWidth * 9 / 10;
        return dialogWidth;
    }

    protected int getLayoutHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    protected abstract View getChildView();

    protected final void showBottom(boolean isShow) {
        if (isShow) {
            mLinearBottom.setVisibility(View.VISIBLE);
        } else {
            mLinearBottom.setVisibility(View.GONE);
        }
    }

    protected final void showTop(boolean isShow) {
        if (isShow) {
            mLinearTop.setVisibility(View.VISIBLE);
        } else {
            mLinearTop.setVisibility(View.GONE);
        }
    }

    protected final void setTitle(String title) {
        mTextTitle.setText(title);
    }

    protected void onClickConfirm() {

    }
}
