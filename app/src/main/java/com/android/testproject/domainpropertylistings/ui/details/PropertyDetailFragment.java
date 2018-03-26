package com.android.testproject.domainpropertylistings.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.testproject.domainpropertylistings.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ankursharma on 3/25/18.
 */

public class PropertyDetailFragment extends Fragment {

    //Fragment arguments for the data to be displayed.
    private static final String ARG_AD_ID = "ad_id";
    private static final String ARG_PROPERTY_DESC = "property_desc";

    @BindView(R.id.app_toolbarDetail)
    Toolbar mAppToolBar;

    @BindView(R.id.listingAdId)
    TextView mListingAdIdTextView;

    @BindView(R.id.listingDescription)
    TextView mListingDescTextView;

    /**
     * Creates Fragment Instance
     * @param adId  : Id of the listing ad
     * @param propertyDescription : Description of the property
     * @return
     */
    public static PropertyDetailFragment newInstance(String adId, String propertyDescription) {
        Bundle args = new Bundle();
        args.putString(ARG_AD_ID, adId);
        args.putString(ARG_PROPERTY_DESC, propertyDescription);
        PropertyDetailFragment fragment = new PropertyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_property, container, false);
        ButterKnife.bind(this, rootView);
        initializeView(savedInstanceState);
        return rootView;
    }

    /**
     * Helper method to initialize the view
     * @param savedInstanceState
     */
    private void initializeView(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args != null) {
            mListingAdIdTextView.setText(args.getString(ARG_AD_ID));
            mListingDescTextView.setText(args.getString(ARG_PROPERTY_DESC));
        }

        if(mAppToolBar != null) {
            mAppToolBar.setTitle(R.string.property_detail);
        }
    }
}
