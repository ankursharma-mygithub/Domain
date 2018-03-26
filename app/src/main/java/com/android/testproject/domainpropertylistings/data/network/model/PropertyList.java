package com.android.testproject.domainpropertylistings.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ankursharma on 3/24/18.
 */

public class PropertyList {

    public ListingResults getListingResults() {
        return mListingResults;
    }

    public void setListingResults(ListingResults listingResults) {
        mListingResults = listingResults;
    }

    //The list of properties that will be displayed.
    @SerializedName("ListingResults")
    private ListingResults mListingResults;

}
