package com.mnidersoft.movieclient.core.states.empty;

import io.reactivex.functions.Action;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public interface EmptyView {

    Action showEmpty();
    Action hideEmpty();
}
