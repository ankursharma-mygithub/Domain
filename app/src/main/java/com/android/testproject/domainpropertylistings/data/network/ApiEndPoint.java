package com.android.testproject.domainpropertylistings.data.network;


import com.android.testproject.domainpropertylistings.common.Constants;
import com.android.testproject.domainpropertylistings.data.network.model.PropertyList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ankursharma on 3/24/18.
 */

/**
 * Used by retrofit library to download JSON file
 */
public interface ApiEndPoint {
    @GET(Constants.API_END_POINT)
    Observable<PropertyList> getItems();
}
