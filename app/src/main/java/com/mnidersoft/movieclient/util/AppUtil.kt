@file:JvmName("AppUtil")

package com.mnidersoft.movieclient.util

import java.io.File

/**
 * Created by Allan.Menezes on 11/11/17.
 */

fun isNullOrEmpty(objects: Collection<*>?): Boolean {
    return objects?.isEmpty() ?: true
}

fun isNullOrEmpty(string: String?): Boolean {
    return string?.isEmpty() ?: true
}

fun getBasePath(): String {
    var basePath = "./src/"
    val file = File(basePath)
    if (!file.exists()) basePath = "./app/src/"
    return basePath
}