package com.mnidersoft.movieclient.core.application;

import android.app.Activity;
import android.app.Application;

import com.mnidersoft.movieclient.core.components.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

public class CustomApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return mInjector;
    }
}
