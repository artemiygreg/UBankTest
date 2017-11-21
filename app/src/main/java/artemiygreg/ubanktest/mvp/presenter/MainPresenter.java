package artemiygreg.ubanktest.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import artemiygreg.ubanktest.model.Data;
import artemiygreg.ubanktest.mvp.model.MainDataModel;
import artemiygreg.ubanktest.mvp.view.MainView;
import artemiygreg.ubanktest.mvp.view.MainViewState;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static artemiygreg.ubanktest.utils.Constants.DEFAULT_SECOND;
import static artemiygreg.ubanktest.utils.Constants.DEFAULT_VALUE_PASSED_SECONDS;
import static artemiygreg.ubanktest.utils.Constants.EXTRA_LIST_DATA;
import static artemiygreg.ubanktest.utils.Constants.EXTRA_PASSES_SECONDS;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class MainPresenter extends BasePresenter<MainView> {
    private int passedSeconds = DEFAULT_VALUE_PASSED_SECONDS;
    private MainDataModel mainDataModel;
    private MainViewState state = MainViewState.TIMER_FINISHED;

    public MainPresenter(@NonNull MainDataModel mainDataModel) {
        this.mainDataModel = mainDataModel;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_PASSES_SECONDS, passedSeconds);
        final MainView view = view();
        if(view != null) {
            outState.putParcelableArrayList(EXTRA_LIST_DATA, view.getDataFromAdapter());
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            passedSeconds = savedInstanceState.getInt(EXTRA_PASSES_SECONDS, DEFAULT_VALUE_PASSED_SECONDS);
            ArrayList<Data> listData = savedInstanceState.getParcelableArrayList(EXTRA_LIST_DATA);
            if (passedSeconds != DEFAULT_VALUE_PASSED_SECONDS) {
                showDialog(DEFAULT_SECOND - passedSeconds); // show to after through remaining seconds
            }
            if(listData != null) {
                showListData(listData);
            }
        }
    }

    public void loadData() {
        Subscription subscription = mainDataModel.loadDataAsObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Data>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Data> data) {
                        showListData(data);
                    }
                });
        unsubscribeOnUnbindView(subscription);
    }

    private void showListData(@Nullable ArrayList<Data> data) {
        if(data != null && !data.isEmpty()) {
            final MainView view = view();
            if(view != null) {
                view.hideImage();
                view.showList(data);
            }
        }
    }

    public void showDialog() {
        showDialog(DEFAULT_SECOND);
    }

    public void showDialog(int count) {
        final MainView view = view();
        if(view != null) {
            if(!view.dialogIsShowing() && state != MainViewState.TIMER_IN_PROGRESS) {
                Subscription subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                        .doOnSubscribe(() -> state = MainViewState.TIMER_IN_PROGRESS)
                        .doOnUnsubscribe(() -> state = MainViewState.TIMER_FINISHED)
                        .take(count)
                        .doOnNext(integer -> passedSeconds++)
                        .last()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {
                                state = MainViewState.TIMER_FINISHED;
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("onError", e.toString());
                            }

                            @Override
                            public void onNext(Long result) {
                                if(!view.dialogIsShowing()) {
                                    view.showDialog();
                                    passedSeconds = DEFAULT_VALUE_PASSED_SECONDS; // reset for saving state
                                }
                            }
                        });

                unsubscribeOnUnbindView(subscription);
            }
        }
    }
}
