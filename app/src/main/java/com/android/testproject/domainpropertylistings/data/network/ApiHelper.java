package com.android.testproject.domainpropertylistings.data.network;

import com.android.testproject.domainpropertylistings.data.network.model.PropertyList;

import rx.Subscription;

/**
 * Created by ankursharma on 3/24/18.
 */

/**
 * This must be implemented by the component that downloads the JSON file.
 */
public interface ApiHelper {
    Subscription getItemsList(final GetPropertyListCallback callback);
    //Callback(or subscriber, or observer) interface for JSON downloader
    interface GetPropertyListCallback{
        void onSuccess(PropertyList propertyItemsList);
        void onError(String message);
    }
}
