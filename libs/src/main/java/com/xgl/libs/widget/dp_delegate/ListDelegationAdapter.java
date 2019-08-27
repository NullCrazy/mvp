package com.xgl.libs.widget.dp_delegate.delegate;

import java.util.List;

/**
 * 针对集合数据源做了简单封装
 * */
public class ListDelegationAdapter<T extends List<?>> extends AbsDelegationAdapter<T> {

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
