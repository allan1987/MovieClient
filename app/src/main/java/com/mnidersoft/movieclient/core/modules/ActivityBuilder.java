package com.mnidersoft.movieclient.core.modules;

import com.mnidersoft.movieclient.ui.details.DetailsActivity;
import com.mnidersoft.movieclient.ui.main.MainActivity;
import com.mnidersoft.movieclient.ui.search.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchActivity searchActivity();

    @ContributesAndroidInjector(modules = DetailsModule.class)
    abstract DetailsActivity detailsActivity();
}
