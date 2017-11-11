package com.mnidersoft.movieclient.util;

import java.util.Collection;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class AppUtil {

    public static boolean isNullOrEmpty(Collection<?> objects) {
        return objects == null || objects.isEmpty();
    }
}
