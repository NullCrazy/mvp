package com.xgl.libs.widget.dp_delegate.delegate;


/**
 * 抽象adapter 抽离ItemView类型
 * */
public abstract class AbsAdapterDelegate<T> implements AdapterDelegate<T> {

    protected int viewType;

    /**
     * view类型通过构造参数传入
     * */
    public AbsAdapterDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }
}
