package com.xgl.libs.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author xingguo.lei
 * date: 2018/10/31
 * explain:
 */
public abstract class BasePresenter<V extends IMvpView> implements IMvpPresenter<V> {
    private WeakReference<V> mView;
    protected CompositeDisposable completable = new CompositeDisposable();

    @Override
    public void createView(V v) {
        if (v != null) {
            mView = new WeakReference<>(v);
        }
    }

    public V getView() {
        if (mView != null && mView.get() != null) {
            return mView.get();
        }

        //创建no-op对象，避免引起空view异常
        Type genericSuperType = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();
        Class<V> genericType = (Class<V>) types[0];
        return NoOp.of(genericType);
    }

    public CompositeDisposable getCompletable() {
        return completable;
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroyView() {
        completable.clear();
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }
}
