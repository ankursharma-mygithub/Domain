package com.android.testproject.domainpropertylistings.data.network;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by ankursharma on 3/24/18.
 */

/**
 * Interface that must be implemented by Image Downloader component of application
 */
public interface ImageDownloadHelper {
    void downloadImage(Context context, String url, ImageView imageView);
}
