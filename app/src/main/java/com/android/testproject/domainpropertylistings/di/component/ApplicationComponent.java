package com.android.testproject.domainpropertylistings.di.component;

import android.content.Context;

import com.android.testproject.domainpropertylistings.common.AppController;
import com.android.testproject.domainpropertylistings.data.DataManager;
import com.android.testproject.domainpropertylistings.di.ApplicationContext;
import com.android.testproject.domainpropertylistings.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ankursharma on 3/24/18.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
        void inject(AppController app);

        @ApplicationContext
        Context context();

        DataManager getDataManager();
}
