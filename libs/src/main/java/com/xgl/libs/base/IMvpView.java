package com.xgl.libs.base;

import android.app.Activity;
import android.support.annotation.UiThread;

/**
 * @author xingguo.lei
 * date: 2018/10/31
 * explain:view层通用接口，里面的方法都是一些通用的方法，便于统一处理
 */
public interface IMvpView {
    /**
     * 弹出提示框
     *
     * @param msg
     */
    @UiThread
    void showMsg(String msg);

    /**
     * 展示等待框
     */
    @UiThread
    void showLoading();

    @UiThread
    void showErrorView();

    /**
     * 隐藏等待框
     */
    @UiThread
    void dismissLoading();

    /**
     * 获取上下文
     *
     * @return 当前view的上下文，主要不要滥用造成内存泄漏
     */
    Activity getActivity();
}
