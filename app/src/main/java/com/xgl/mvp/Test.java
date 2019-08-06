package com.xgl.mvp;

import android.view.LayoutInflater;
import android.view.View;

import com.xgl.libs.base.BaseDialog;

/**
 * @Description: TODO
 * @Author: xingguo.lei@luckincoffee.com
 * @Date: 2019-08-05 19:43
 */
public class Test extends BaseDialog {
    @Override
    protected View getChildView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_test,null);
        return view;
    }

    @Override
    protected int getLayoutHeight() {
        return (int) (screenHeight * 0.3);
    }
}
