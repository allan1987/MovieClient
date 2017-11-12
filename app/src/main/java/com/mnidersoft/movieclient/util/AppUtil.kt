@file:JvmName("AppUtil")

package com.mnidersoft.movieclient.util

/**
 * Created by Allan.Menezes on 11/11/17.
 */

fun isNullOrEmpty(objects: Collection<*>?): Boolean {
    return objects?.isEmpty() ?: true
}

fun isNullOrEmpty(string: String?): Boolean {
    return string?.isEmpty() ?: true
}