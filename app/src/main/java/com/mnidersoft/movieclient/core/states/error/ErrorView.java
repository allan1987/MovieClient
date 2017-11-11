package com.mnidersoft.movieclient.core.states.error;

import io.reactivex.functions.Action;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public interface ErrorView {

    Action showError();
    Action hideError();
}
