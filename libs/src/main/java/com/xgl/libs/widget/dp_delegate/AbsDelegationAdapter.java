package com.xgl.libs.widget.dp_delegate.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 *  抽象DelegationAdapter继承自RecyclerView.Adapter，把行为转移至代理AdapterDelegatesManager，达到解耦的目的
 * */
public abstract class AbsDelegationAdapter<T> extends RecyclerView.Adapter {

    /**
     *
     * */
    protected AdapterDelegatesManager<T> delegatesManager = new AdapterDelegatesManager<T>();
    protected T items;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }
}
