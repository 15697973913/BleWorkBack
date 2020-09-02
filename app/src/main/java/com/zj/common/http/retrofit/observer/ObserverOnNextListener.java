package com.zj.common.http.retrofit.observer;

public interface ObserverOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable e);
}
