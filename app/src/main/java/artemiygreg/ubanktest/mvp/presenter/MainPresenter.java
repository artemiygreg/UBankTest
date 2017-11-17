package artemiygreg.ubanktest.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import artemiygreg.ubanktest.model.Data;
import artemiygreg.ubanktest.mvp.model.MainDataModel;
import artemiygreg.ubanktest.mvp.view.MainView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class MainPresenter extends BasePresenter<MainView> {
    public static final int DEFAULT_DELAY = 5; // in second;
    private long currentSecond;
    private boolean showDialog = false;
    private MainDataModel mainDataModel;

    public MainPresenter(@NonNull MainDataModel mainDataModel) {
        this.mainDataModel = mainDataModel;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("current_second", currentSecond);
        outState.putBoolean("show_dialog", showDialog);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        currentSecond = savedInstanceState.getLong("current_second", 0);
        showDialog = savedInstanceState.getBoolean("show_dialog", false);
        if(showDialog) {
            showDialog(currentSecond);
        }
    }

    public void loadData() {
        Subscription subscription = mainDataModel.loadDataAsObseravble()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Data>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(@NonNull List<Data> data) {
                        if(!data.isEmpty()) {
                            final MainView view = view();
                            if(view != null) {
                                view.hideImage();
                                view.showList(data);
                            }
                        }
                    }
                });
        unsubscribeOnUnbindView(subscription);
    }

    public void showDialog() {
        showDialog(DEFAULT_DELAY);
    }

    public void showDialog(long delay) {
        final MainView view = view();
        if(view != null) {
            if(!view.dialogIsShowing()) {
                showDialog = true;
                Subscription subscription = Observable.timer(delay, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("onError", e.toString());
                            }

                            @Override
                            public void onNext(Long aLong) {
                                if(!view.dialogIsShowing()) {
                                    view.showDialog();
                                    showDialog = false;
                                }
                            }
                        });

                unsubscribeOnUnbindView(subscription);
            }
        }
    }
}
