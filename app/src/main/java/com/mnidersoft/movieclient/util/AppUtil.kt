@file:JvmName("AppUtil")

package com.mnidersoft.movieclient.util

/**
 * Created by Allan.Menezes on 11/11/17.
 */

fun isNullOrEmpty(objects: Collection<*>?): Boolean {
    return objects?.let { it.isEmpty() } ?: true
}