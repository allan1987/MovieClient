package com.mnidersoft.movieclient.core.lifecycles;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class DisposeStrategy implements LifecycleObserver {

    private CompositeDisposable mComposite = new CompositeDisposable();

    public void addDisposable(Disposable toDispose) {
        mComposite.add(toDispose);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mComposite.dispose();
    }
}
