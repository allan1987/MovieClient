package com.mnidersoft.movieclient.core.components;

import android.app.Application;

import com.mnidersoft.movieclient.core.application.CustomApplication;
import com.mnidersoft.movieclient.core.modules.ActivityBuilder;
import com.mnidersoft.movieclient.core.modules.AppModule;
import com.mnidersoft.movieclient.core.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Allan.Menezes on 11/10/17.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,
        NetworkModule.class, ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(CustomApplication application);
}
