package com.android.testproject.domainpropertylistings.di.module;

import android.app.Application;
import android.content.Context;

import com.android.testproject.domainpropertylistings.common.Constants;
import com.android.testproject.domainpropertylistings.data.AppDataManager;
import com.android.testproject.domainpropertylistings.data.DataManager;
import com.android.testproject.domainpropertylistings.data.network.ApiEndPoint;
import com.android.testproject.domainpropertylistings.data.network.ApiHelper;
import com.android.testproject.domainpropertylistings.data.network.GlideImageDownloader;
import com.android.testproject.domainpropertylistings.data.network.ImageDownloadHelper;
import com.android.testproject.domainpropertylistings.data.network.RetrofitApiHelper;
import com.android.testproject.domainpropertylistings.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ankursharma on 3/24/18.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ApiEndPoint providesApiEndPoint(
            Retrofit retrofit) {
        return retrofit.create(ApiEndPoint.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ApiHelper providesService(
            ApiEndPoint networkService) {
        return new RetrofitApiHelper(networkService);
    }

    @Provides
    @Singleton
    public ImageDownloadHelper providesImageDownloadHelper() {
        return new GlideImageDownloader();
    }
}
