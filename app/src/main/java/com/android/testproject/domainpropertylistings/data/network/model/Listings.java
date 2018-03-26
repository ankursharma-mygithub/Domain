package com.android.testproject.domainpropertylistings.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents each individual property that is displayed in the listings
 * Created by ankursharma on 3/17/18.
 */


public class Listings {

    @SerializedName("AgencyLogoUrl")
    private String mAgencyLogoUrl;
    @SerializedName("Bathrooms")
    private String mBathrooms;
    @SerializedName("Bedrooms")
    private String mBedrooms;
    @SerializedName("Carspaces")
    private String mCarspaces;
    @SerializedName("DisplayPrice")
    private String mDisplayPrice;
    @SerializedName("DisplayableAddress")
    private String mDisplayableAddress;
    @SerializedName("RetinaDisplayThumbUrl")
    private String mRetinaDisplayThumbUrl;
    @SerializedName("SecondRetinaDisplayThumbUrl")
    private String mSecondRetinaDisplayThumbUrl;
    @SerializedName("TruncatedDescription")
    private String mTruncatedDescription;
    @SerializedName("IsElite")
    private int mIsElite;
    @SerializedName("AdId")
    private String mAdId;
    @SerializedName("Description")
    private String mDescription;


    public String getAgencyLogoUrl() {
        return mAgencyLogoUrl;
    }

    public String getBathrooms() {
        return mBathrooms;
    }

    public String getBedrooms() {
        return mBedrooms;
    }

    public String getCarspaces() {
        return mCarspaces;
    }

    public String getDisplayPrice() {
        return mDisplayPrice;
    }

    public String getDisplayableAddress() {
        return mDisplayableAddress;
    }

    public String getRetinaDisplayThumbUrl() {
        return mRetinaDisplayThumbUrl;
    }

    public String getSecondRetinaDisplayThumbUrl() {
        return mSecondRetinaDisplayThumbUrl;
    }

    public String getTruncatedDescription() {
        return mTruncatedDescription;
    }

    public int getIsElite() {
        return mIsElite;
    }

    public String getAdId() {
        return mAdId;
    }

    public String getDescription() {
        return mDescription;
    }
}
