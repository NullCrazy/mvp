package com.xgl.libs.widget.dp_delegate.delegate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 *  代理和被代理者manager，所有的adapterDelegate都受控于此
 * */
public class AdapterDelegatesManager<T> {

    SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat();
    private AdapterDelegate<T> fallbackDelegate;

    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        return addDelegate(delegate, false);
    }

    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate,
                                                  boolean allowReplacingDelegate) {
        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null!");
        }

        int viewType = delegate.getItemViewType();

        if (fallbackDelegate != null && fallbackDelegate.getItemViewType() == viewType) {
            throw new IllegalArgumentException(
                    "Conflict: the passed AdapterDelegate has the same ViewType integer (value = " + viewType
                            + ") as the fallback AdapterDelegate");
        }
        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = " + viewType
                            + ". Already registered AdapterDelegate is " + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    /**
     * 从管理的adapterDelegate集合种移除viewType对应adapterDelegate
     * */
    public AdapterDelegatesManager<T> removeDelegate(@NonNull AdapterDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null");
        }

        AdapterDelegate<T> queried = delegates.get(delegate.getItemViewType());
        if (queried != null && queried == delegate) {
            delegates.remove(delegate.getItemViewType());
        }

        return this;
    }

    /**
     * 从管理的adapterDelegate集合种移除viewType对应adapterDelegate
     * */
    public AdapterDelegatesManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }

    /**
     * 获得Item与之适配的viewType值
     * */
    public int getItemViewType(@NonNull T items, int position) {
        if (items == null) {
            throw new NullPointerException("Items datasource is null!");
        }

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            AdapterDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(items, position)) {
                return delegate.getItemViewType();
            }
        }

        if (fallbackDelegate != null) {
            return fallbackDelegate.getItemViewType();
        }

        throw new IllegalArgumentException(
                "No AdapterDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 拿到被代理者viewHolder
     * */
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate<T> delegate = delegates.get(viewType);
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
            } else {
                delegate = fallbackDelegate;
            }
        }

        RecyclerView.ViewHolder vh = delegate.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException(
                    "ViewHolder returned from AdapterDelegate " + delegate + " for ViewType =" + viewType
                            + " is null!");
        }
        return vh;
    }

    /**
     * 绑定被代理视图数据
     * */
    public void onBindViewHolder(@NonNull T items, int position,
                                 @NonNull RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = delegates.get(viewHolder.getItemViewType());
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw new NullPointerException(
                        "No AdapterDelegate added for ViewType " + viewHolder.getItemViewType());
            } else {
                delegate = fallbackDelegate;
            }
        }

        delegate.onBindViewHolder(items, position, viewHolder);
    }

    public AdapterDelegatesManager<T> setFallbackDelegate(@Nullable AdapterDelegate<T> fallbackDelegate) {
        if (fallbackDelegate != null) {
            // Setting a new fallback delegate
            int delegatesCount = delegates.size();
            int fallbackViewType = fallbackDelegate.getItemViewType();
            for (int i = 0; i < delegatesCount; i++) {
                AdapterDelegate<T> delegate = delegates.valueAt(i);
                if (delegate.getItemViewType() == fallbackViewType) {
                    throw new IllegalArgumentException(
                            "Conflict: The given fallback - delegate has the same ViewType integer (value = "
                                    + fallbackViewType + ")  as an already assigned AdapterDelegate "
                                    + delegate.getClass().getName());
                }
            }
        }
        this.fallbackDelegate = fallbackDelegate;

        return this;
    }
}
