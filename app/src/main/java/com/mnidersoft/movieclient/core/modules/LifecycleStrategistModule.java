package com.mnidersoft.movieclient.core.modules;

import android.arch.lifecycle.LifecycleOwner;

import com.mnidersoft.movieclient.core.lifecycles.DisposeStrategy;
import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

@Module
public class LifecycleStrategistModule {

    @Provides
    static LifecycleStrategist strategist(LifecycleOwner owner) {
        return new LifecycleStrategist(owner, new DisposeStrategy());
    }
}
