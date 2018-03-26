package com.android.testproject.domainpropertylistings.data;

import android.content.Context;
import android.widget.ImageView;

import com.android.testproject.domainpropertylistings.data.network.ApiHelper;
import com.android.testproject.domainpropertylistings.data.network.ImageDownloadHelper;
import com.android.testproject.domainpropertylistings.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

/**
 * Created by ankursharma on 3/24/18.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    //helper for downloading and parsing JSON data
    private final ApiHelper mAPIHelper;
    private final Context mContext;
    //helper for downloading the image.
    private final ImageDownloadHelper mImageDownloader;

    @Inject
    public AppDataManager(@ApplicationContext Context context, ApiHelper apiHelper, ImageDownloadHelper imageDownloadHelper) {
        mContext = context;
        mAPIHelper = apiHelper;
        mImageDownloader = imageDownloadHelper;
    }

    @Override
    public Subscription getItemsList(GetPropertyListCallback callback) {
        return mAPIHelper.getItemsList(callback);
    }

    @Override
    public void downloadImage(Context context, String url, ImageView imageView) {
        mImageDownloader.downloadImage(context, url, imageView);
    }
}
