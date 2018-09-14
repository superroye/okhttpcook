package com.wolf.lib.okhttpcool.response;

import com.wolf.lib.okhttpcool.listener.IProgressDialog;
import com.wolf.lib.okhttpcool.listener.IRxObserveDisposer;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Roye on 2016-12-7.
 */

public abstract class BaseResponseObserver<T> implements Observer<T>, SupportResponseLifecycle<T> {

    private Disposable disposable;
    public SupportProcedure procedure;
    WeakReference<IRxObserveDisposer> mRxObserveDisposer;

    public BaseResponseObserver(IRxObserveDisposer rxDisposer) {
        this(rxDisposer, new SupportProcedure());
    }

    public BaseResponseObserver(IRxObserveDisposer rxDisposer, SupportProcedure procedure) {
        this.procedure = procedure;
        this.procedure.setResponseLifecycle(this);
        if (rxDisposer != null) {
            this.mRxObserveDisposer = new WeakReference<>(rxDisposer);
        }
    }

    public void setProgressDialog(IProgressDialog progressDialog) {
        procedure.setProgressDialog(progressDialog);
    }

    public void setProgressDialog(IProgressDialog progressDialog, String loadingText) {
        procedure.setProgressDialog(progressDialog, loadingText);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mRxObserveDisposer != null) {
            IRxObserveDisposer rxDisposer = mRxObserveDisposer.get();
            if (rxDisposer != null) {
                rxDisposer.addDisposable(d);
            }
        }
        this.disposable = d;
        onStart();
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        this.onFinish();
    }

    @Override
    public void onError(Throwable e) {
        procedure.handle(e);
        if (disposable != null) {
            disposable.dispose();
        }
        this.onFinish();
    }

    @Override
    public void onFinish() {
        procedure.hideLoading();
    }

    @Override
    public void onStart() {
        procedure.showLoading();
    }

    @Override
    public void onFailed(HttpResponse<T> result) {

    }
}
