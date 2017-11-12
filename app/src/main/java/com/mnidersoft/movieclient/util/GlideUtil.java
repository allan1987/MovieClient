package com.mnidersoft.movieclient.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public class GlideUtil {

    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185";

    public static void loadImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(BASE_IMG_URL + path).into(imageView);
    }
}
