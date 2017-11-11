package com.mnidersoft.movieclient.core.lifecycles;

import android.arch.lifecycle.LifecycleOwner;

import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class LifecycleStrategist {

    private DisposeStrategy mStrategy;

    public LifecycleStrategist(LifecycleOwner owner, DisposeStrategy strategy) {
        mStrategy = strategy;
        owner.getLifecycle().addObserver(strategy);
    }

    public void applyStrategy(Disposable toDispose) {
        mStrategy.addDisposable(toDispose);
    }
}
