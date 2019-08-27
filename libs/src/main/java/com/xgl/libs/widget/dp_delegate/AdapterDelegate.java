package com.xgl.libs.widget.dp_delegate.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * adapter委托
 */
public interface AdapterDelegate<T> {

    /**
     * 获取ItemView类型
     */
    int getItemViewType();

    /**
     * 判断Item是否已存在委托adapter view
     */
    boolean isForViewType(@NonNull T items, int position);

    /**
     * 创建ViewHolder
     */
    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    /**
     * 绑定数据
     */
    void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder holder);
}
