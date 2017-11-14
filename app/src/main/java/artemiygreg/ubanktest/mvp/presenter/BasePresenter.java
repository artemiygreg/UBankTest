package artemiygreg.ubanktest.mvp.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class BasePresenter<V> {
    @NonNull
    private final CompositeSubscription toUnsubscribeOnUnbindView = new CompositeSubscription();

    @Nullable
    private volatile V view;

    @CallSuper
    public void bindView(@NonNull V view) {
        this.view = view;
    }

    @CallSuper
    public void unbindView(@NonNull V view) {
        this.view = null;
    }

    @CallSuper
    public void unbindViewAndUnsubscribe(@NonNull V view) {
        this.view = null;
        toUnsubscribeOnUnbindView.clear();
    }

    @Nullable
    protected V view() {
        return view;
    }

    protected final void unsubscribeOnUnbindView(@NonNull Subscription subscription, @NonNull Subscription... subscriptions) {
        toUnsubscribeOnUnbindView.add(subscription);

        for (Subscription s : subscriptions) {
            toUnsubscribeOnUnbindView.add(s);
        }
    }

    public void unsubsribe() {
        toUnsubscribeOnUnbindView.clear();
    }
}
