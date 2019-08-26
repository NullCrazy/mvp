package com.xgl.libs.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.xgl.libs.widget.Tip;

/**
 * @author xingguo.lei
 * @date 2018/10/31
 * explain: Activity 抽象类，主要用于与对应的Presenter绑定，通用方法处理等
 */
public abstract class BaseActivity<P extends IMvpPresenter> extends AppCompatActivity implements IMvpView {
    protected P presenter;
    private Fragment lastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = createPresenter();
        if (presenter != null) {
            presenter.createView(this);
        }
        init(savedInstanceState);
    }

    /**
     * 获取布局
     *
     * @return 布局文件id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化操作
     *
     * @param savedInstanceState 临时数据
     */
    protected abstract void init(@Nullable Bundle savedInstanceState);

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract @CheckResult
    P createPresenter();

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroyView();
        }
    }

    @Override
    public void showMsg(String msg) {
        Tip.show(this, Gravity.BOTTOM, msg);
    }

    protected void showFragment(int containerId, Class<? extends Fragment> toClazz) {
        showFragment(containerId, toClazz, false);
    }

    protected void showFragment(int containerId, Class<? extends Fragment> toClazz, boolean addBack) {
        String clazzName = toClazz.getName();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Fragment toFragment = manager.findFragmentByTag(clazzName);
        if (toFragment == null) {
            toFragment = Fragment.instantiate(this, clazzName);
        }
        if (!toFragment.isAdded()) {
            fragmentTransaction.add(containerId, toFragment, clazzName);
        } else {
            if (lastFragment != null) {
                fragmentTransaction.hide(lastFragment);
            }
            fragmentTransaction.show(toFragment);
        }

        if (addBack) {
            fragmentTransaction.addToBackStack(clazzName).commitAllowingStateLoss();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }
        lastFragment = toFragment;
    }
}

