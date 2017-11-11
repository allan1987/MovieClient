package com.mnidersoft.movieclient.core.modules;

import android.app.Application;
import android.content.Context;

import com.mnidersoft.movieclient.core.qualifiers.SchedulerIO;
import com.mnidersoft.movieclient.core.qualifiers.SchedulerUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    @SchedulerUI
    static Scheduler uiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @SchedulerIO
    static Scheduler ioScheduler() {
        return Schedulers.io();
    }
}
