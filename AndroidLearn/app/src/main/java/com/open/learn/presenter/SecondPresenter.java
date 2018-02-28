package com.open.learn.presenter;

import android.util.Log;

import com.open.learn.listener.BaseMessageEvent;
import com.open.learn.listener.HomeMessageEvent;
import com.open.learn.model.SecondDataApi;
import com.open.learn.utils.Const;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vivian on 2017/9/26.
 * MVP's presenter for Second Fragment
 */

public class SecondPresenter {
    private static final String TAG = "SecondPresenter";
    // rx
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // is refresh data finish or not
    public boolean isRefreshing;

    public void getData() {
        Log.d(TAG, "getData");
        // load start notify
        EventBus.getDefault().post(new HomeMessageEvent(Const.MODULE_SECOND)
                .setLoadStatus(BaseMessageEvent.STATUS_START));

        // load data
        Disposable dispose = Observable.fromCallable(SecondDataApi::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                            Log.d(TAG, "getData Success" + StringUtils.join(model.toString(), ","));
                            EventBus.getDefault().post(new HomeMessageEvent(Const.MODULE_SECOND)
                                    .setLoadStatus(BaseMessageEvent.STATUS_SUCCESS)
                                    .setDataModel(model));
                        },
                        error -> {
                            Log.w(TAG, "getData error:", error);
                            EventBus.getDefault().post(new HomeMessageEvent(Const.MODULE_SECOND)
                                    .setLoadStatus(BaseMessageEvent.STATUS_FAIL)
                                    .setErrorMsg(error.toString()));
                        });

        compositeDisposable.add(dispose);
    }

    public void refreshData() {
        if(isRefreshing){
            Log.d(TAG, "refresh task already exist");
            return;
        }

        isRefreshing = true;
        // refresh data
        Disposable dispose = Observable.fromCallable(SecondDataApi::refreshData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                            Log.d(TAG, "refreshData Success" + StringUtils.join(model.toString(), ","));
                            EventBus.getDefault().post(new HomeMessageEvent(Const.MODULE_SECOND)
                                    .setLoadStatus(BaseMessageEvent.STATUS_REFRESH_SUCCESS)
                                    .setDataModel(model));
                        },
                        error -> {
                            Log.w(TAG, "refreshData error:", error);
                            EventBus.getDefault().post(new HomeMessageEvent(Const.MODULE_SECOND)
                                    .setLoadStatus(BaseMessageEvent.STATUS_REFRESH_FAIL)
                                    .setErrorMsg(error.toString()));
                        }, () -> {
                            isRefreshing = false;
                        }
                );

        compositeDisposable.add(dispose);
    }

    public void destroy() {
        // to avoid okhttp leak
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


}
