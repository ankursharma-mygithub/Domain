package com.android.testproject.domainpropertylistings.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ankursharma on 3/17/18.
 */

public class ListingResults {

    @SerializedName("Listings")
    private List<Listings> mListings = null;

    public List<Listings> getListings() {
        return mListings;
    }

    public void setListings(List<Listings> listings) {
        mListings = listings;
    }
}
