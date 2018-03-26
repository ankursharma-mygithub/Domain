package com.android.testproject.domainpropertylistings.data.network;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by ankursharma on 3/24/18.
 */

/**
 * Image downloader which uses GLIDE library.
 */
public class GlideImageDownloader implements ImageDownloadHelper {
    @Override
    public void downloadImage(Context context, String imageUrl, final ImageView imageView) {
        //Glide will download and cache the images by default.
        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setVisibility(android.view.View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageView.setVisibility(android.view.View.VISIBLE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
