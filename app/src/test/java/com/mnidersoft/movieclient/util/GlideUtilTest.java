package com.mnidersoft.movieclient.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by allan on 15/11/17.
 */

public class GlideUtilTest {

    @Mock
    Context mContext;

    @Mock
    ImageView mImageView;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadImageWithNullPath() throws Exception {
        Target<Drawable> result = GlideUtil.loadImage(mContext, null, mImageView);
        Assert.assertNull(result);
    }
}
