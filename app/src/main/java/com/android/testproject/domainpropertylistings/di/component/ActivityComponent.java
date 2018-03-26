package com.android.testproject.domainpropertylistings.di.component;

import com.android.testproject.domainpropertylistings.di.PerActivity;
import com.android.testproject.domainpropertylistings.di.module.ActivityModule;
import com.android.testproject.domainpropertylistings.ui.main.PropertyListFragment;

import dagger.Component;

/**
 * Created by ankursharma on 3/24/18.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(PropertyListFragment fragment);

}
