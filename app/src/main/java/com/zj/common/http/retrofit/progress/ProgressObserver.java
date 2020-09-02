package com.zj.common.http.retrofit.progress;

import android.content.Context;

import com.zj.common.http.retrofit.observer.ObserverOnNextListener;
import com.android.common.baselibrary.log.MLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;



public class ProgressObserver<T> implements Observer<T>, ProgressCancelListener {
    private static final String TAG = ProgressObserver.class.getSimpleName();
    private ObserverOnNextListener listener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private Disposable d;

    public ProgressObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }


    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        MLog.d("onSubscribe: ");
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        MLog.e("onError: ", e);
        listener.onError(e);
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        MLog.d("onComplete: ");
    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}
