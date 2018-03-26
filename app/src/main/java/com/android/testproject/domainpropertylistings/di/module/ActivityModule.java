package com.android.testproject.domainpropertylistings.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.testproject.domainpropertylistings.data.DataManager;
import com.android.testproject.domainpropertylistings.di.ActivityContext;
import com.android.testproject.domainpropertylistings.ui.main.PropertyListContract;
import com.android.testproject.domainpropertylistings.ui.main.PropertyListPresenter;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ankursharma on 3/24/18.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    CompositeSubscription providesCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    PropertyListContract.Presenter providesPropertyListPresenter(DataManager dataManager, CompositeSubscription subscription, @ActivityContext Context context) {
        return new PropertyListPresenter(dataManager, subscription, context);
    }

}
