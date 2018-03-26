package com.android.testproject.domainpropertylistings.common;

import android.app.Application;

import com.android.testproject.domainpropertylistings.data.DataManager;
import com.android.testproject.domainpropertylistings.di.component.ApplicationComponent;
import com.android.testproject.domainpropertylistings.di.component.DaggerApplicationComponent;
import com.android.testproject.domainpropertylistings.di.module.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

/**
 * Created by ankursharma on 3/24/18.
 */

/**
 * * The extended application class
 */
public class AppController extends Application {

    public DataManager getDataManager() {
        return mDataManager;
    }

    @Inject
    DataManager mDataManager;

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        //Add LeakCanary lib for memory leak detection.
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
