package com.mnidersoft.movieclient.core.modules;

import com.mnidersoft.movieclient.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
