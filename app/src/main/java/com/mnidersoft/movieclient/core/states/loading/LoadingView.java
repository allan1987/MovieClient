package com.mnidersoft.movieclient.core.states.loading;

import io.reactivex.functions.Action;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public interface LoadingView {

    Action showLoading();
    Action hideLoading();
}
