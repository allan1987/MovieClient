package com.mnidersoft.movieclient.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public class GlideUtil {

    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185";

    public static Target<Drawable> loadImage(Context context, String path, ImageView imageView) {
        if (AppUtil.isNullOrEmpty(path)) return null;
        return Glide.with(context).load(BASE_IMG_URL + path).into(imageView);
    }
}
