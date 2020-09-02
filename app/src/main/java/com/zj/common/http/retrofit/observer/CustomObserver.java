package com.zj.common.http.retrofit.observer;

import android.content.Context;

import com.android.common.baselibrary.log.MLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CustomObserver<T> implements Observer<T> {
    private static final String TAG = CustomObserver.class.getSimpleName();
    private ObserverOnNextListener listener;
    private Context context;
    private Disposable disposable;

    public CustomObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        MLog.d("onSubscribe: ");
        //添加业务处理
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        MLog.e( "onError: ", e);
        //添加业务处理
        listener.onError(e);
    }

    @Override
    public void onComplete() {
        MLog.d("onComplete: ");
        //添加业务处理
    }

    public void cancelDispose() {
        if (null != disposable) {
           if (!disposable.isDisposed()) {
                disposable.dispose();
           }
        }
    }
}
