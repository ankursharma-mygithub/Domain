package com.android.testproject.domainpropertylistings.ui.main;

import android.widget.ImageView;

import com.android.testproject.domainpropertylistings.data.network.model.Listings;

/**
 * Created by ankursharma on 3/24/18.
 */

public interface PropertyListContract {
    //Presenter layer contract
    interface Presenter {
        void getDataFromURL();
        void onBindItemAtPosition(RowItemHolder holder, int position);
        int getItemsCount();
        void onStop();
        void onAttach(View view);
        boolean isPropertyElite(int position);
        void listingClicked(int position);
    }

    //View layer contract
    interface View {
        void displayListOfItems();
        void showErrorDialog(int errorId);
        void showErrorDialog(String message);
        void showWait();
        void removeWait();
        int getDisplayWidth();
        void listingClicked(Listings listing);
    }

    //Contract for the view holder
    interface RowItemHolder {
        void updateDisplayPrice(String displayPrice);
        void updateDisplaySpec(String displaySpec);
        void updateDisplayAddress(String displayAddress);
        ImageView getMainRetinaDisplay();
        ImageView getSecondRetinaDisplay();
        ImageView getAgencyLogo();
    }
}
