package com.android.testproject.domainpropertylistings.ui.main;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.android.testproject.domainpropertylistings.R;
import com.android.testproject.domainpropertylistings.common.Utils;
import com.android.testproject.domainpropertylistings.data.DataManager;
import com.android.testproject.domainpropertylistings.data.network.RetrofitApiHelper;
import com.android.testproject.domainpropertylistings.data.network.model.Listings;
import com.android.testproject.domainpropertylistings.data.network.model.PropertyList;

import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Url;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ankursharma on 3/24/18.
 */

public class PropertyListPresenter implements PropertyListContract.Presenter {

    //For logger purpose.
    private static final String TAG = "PropertyListPresenter";

    //Calculate sizes of different areas.
    private int mThumbnailWidth;
    private int mThumbnailHeight;

    //Main Activity
    private PropertyListContract.View mPropertyListFragment;

    //Data Manager
    private DataManager mDataManager;

    //To manage multiple RxJava subscriptions
    private CompositeSubscription mSubscriptions;

    private Context mContext;

    //Data structure initialized from the JSON string
    private PropertyList mPropertyListingResults;

    //Listing of properties
    private List<Listings> mPropertyList;

    @Inject
    public PropertyListPresenter(DataManager dataManager, CompositeSubscription subscription, Context context) {
        mDataManager = dataManager;
        mSubscriptions = subscription;
        mContext = context;
    }

    @Override
    public void getDataFromURL() {
        if(Utils.isNetworkConnected(mContext)) {
            mPropertyListFragment.showWait();
            Subscription subscription = mDataManager.getItemsList(new RetrofitApiHelper.GetPropertyListCallback() {
                @Override
                public void onSuccess(PropertyList galleryItemsList) {
                    //If there is an item in the list without any information, don't display.
                    mPropertyListingResults = galleryItemsList;
                    mPropertyList = mPropertyListingResults.getListingResults().getListings();
                    mPropertyListFragment.removeWait();
                    mPropertyListFragment.displayListOfItems();
                }

                @Override
                public void onError(String message) {
                   mPropertyListFragment.removeWait();
                   mPropertyListFragment.showErrorDialog(message);
                }

            });
            mSubscriptions.add(subscription);
        } else {
            Log.e(TAG, "Internet disconnected");
            mPropertyListFragment.removeWait();
            mPropertyListFragment.showErrorDialog(R.string.internet_not_connected);
        }
    }

    @Override
    public void onBindItemAtPosition(PropertyListContract.RowItemHolder holder, int position) {
            Listings property = mPropertyList.get(position);

            holder.updateDisplayPrice(getDisplayPrice(property));

            holder.updateDisplaySpec(getPropertySpec(property));

            holder.updateDisplayAddress(property.getDisplayableAddress());

            String urlMain = property.getRetinaDisplayThumbUrl();

             downloadImages(mContext, urlMain, holder.getMainRetinaDisplay(), true);


        if(property.getIsElite() == 1) {
            String urlMain2 = property.getSecondRetinaDisplayThumbUrl();

                downloadImages(mContext, urlMain2, holder.getSecondRetinaDisplay(), true);

        }

        String urlLogo = property.getAgencyLogoUrl();
        downloadImages(mContext, urlLogo, holder.getAgencyLogo(), false);


    }

    /**
     * Helper function to doanload imaged
     * @param mContext
     * @param url
     * @param imageView
     * @param bThumbNail
     */
    private void downloadImages(Context mContext, String url, ImageView imageView, boolean bThumbNail) {
        imageView.setVisibility(View.INVISIBLE);
        if(bThumbNail) {
            imageView.getLayoutParams().height = mThumbnailHeight;
            imageView.getLayoutParams().width = mThumbnailWidth;
        } else {
            imageView.getLayoutParams().height = (mThumbnailHeight/3);
            imageView.getLayoutParams().width = (mThumbnailWidth/2);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if(url!= null && !url.isEmpty()) {
            mDataManager.downloadImage(mContext, url, imageView);
        }
    }

    //Gets display price
    private String getDisplayPrice(Listings property) {
        String displayPrice = property.getDisplayPrice();
        if(displayPrice == null || displayPrice.isEmpty()) {
            displayPrice = mContext.getString(R.string.label_zero_price);
        }
        return displayPrice;
    }

    //gets property specifications
    private String getPropertySpec(Listings property) {
        String numBed = property.getBedrooms();
        String numBath = property.getBathrooms();
        String numCar = property.getCarspaces();
        //Todo: use plural strings
        return numBed + " bed, " + numBath + " bath, " + numCar + " car";
    }

    @Override
    public int getItemsCount() {
        return mPropertyListingResults.getListingResults().getListings().size();
    }

    @Override
    public void onStop() {
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onAttach(PropertyListContract.View view) {
        mPropertyListFragment = view;
        mThumbnailWidth = view.getDisplayWidth();
        int cardWidth = mThumbnailWidth -
                ((int)mContext.getResources().getDimension(R.dimen.card_bar_width) +
                        2*(int)mContext.getResources().getDimension(R.dimen.card_padding) );
        mThumbnailWidth = cardWidth /2 - 2*(int)mContext.getResources().
                getDimension(R.dimen.card_image_spacing);

        TypedValue tmp = new TypedValue();
        mContext.getResources().getValue(R.dimen.image_aspect_ratio, tmp, true);
        mThumbnailHeight = (int)(mThumbnailWidth / tmp.getFloat());
    }

    @Override
    public boolean isPropertyElite(int position) {
        return (mPropertyList.get(position).getIsElite() == 1);
    }

    @Override
    public void listingClicked(int position) {
        mPropertyListFragment.listingClicked(mPropertyList.get(position));
    }


}
