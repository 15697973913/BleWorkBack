package com.zj.zhijue.util;

import android.os.Handler;

import java.lang.ref.WeakReference;

public class UIHandler<T> extends Handler {
    protected WeakReference<T> ref;

    public UIHandler(T t) {
        this.ref = new WeakReference(t);
    }

    public T getRef() {
        return this.ref != null ? this.ref.get() : null;
    }
}