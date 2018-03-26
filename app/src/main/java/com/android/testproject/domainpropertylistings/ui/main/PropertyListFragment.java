package com.android.testproject.domainpropertylistings.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.android.testproject.domainpropertylistings.R;
import com.android.testproject.domainpropertylistings.common.AppController;
import com.android.testproject.domainpropertylistings.data.network.model.Listings;
import com.android.testproject.domainpropertylistings.di.component.DaggerActivityComponent;
import com.android.testproject.domainpropertylistings.di.module.ActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ankursharma on 3/24/18.
 */

public class PropertyListFragment extends Fragment implements PropertyListContract.View{

    //For logger
    private static final String TAG = "PropertyListFragment";

    //Use Butter Knife library to bind the views
    //RecyclerView
    @BindView(R.id.propertyRecyclerView)
    RecyclerView mRecyclerView;

    //Progress bar
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.toolBarListings)
    Toolbar mListingAppBar;

    public interface CallBacks {
        void onListingSelected(String adId, String description);
    }

    CallBacks mPropertySelectionListener;

    //SwipeRefreshLayout is a part of support library and is a standard way to implement
    //common pull to refresh pattern in Android

    @Inject
    public PropertyListContract.Presenter mPresenter;

    public static PropertyListFragment newInstance() {

        Bundle args = new Bundle();
        PropertyListFragment fragment = new PropertyListFragment();
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


        View rootView = inflater.inflate(R.layout.fragment_listing_property, container, false);

        ButterKnife.bind(this, rootView);

        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule((AppCompatActivity) getActivity()))
                .applicationComponent(((AppController) getActivity().getApplication()).getApplicationComponent())
                .build().inject(this);

        mPresenter.onAttach(this);
        //Initialize the views
        initializeViews();

        getAndDisplayListOfProperties();

        return rootView;
    }

    /**
     * Helper method to initialize the widgets
     */
    private void initializeViews() {

        if(mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    /**
     * Ask presenter to get the data from the specified URL.
     */
    private void getAndDisplayListOfProperties() {
        if(mPresenter != null) {
            mPresenter.getDataFromURL();
        }
    }

    @Override
    public void displayListOfItems() {
        if(mRecyclerView != null) {
            Log.d(TAG, "Got list of items");
            mRecyclerView.setAdapter(new PropertyListAdapter(mPresenter));
        }
    }

    @Override
    public void showErrorDialog(int errorId) {
        showErrorDialog(getString(errorId));
    }

    @Override
    public void showErrorDialog(String message) {
        Log.e(TAG, message);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //To handle use case where user tries to refresh the view and device is not connected.
        builder.setTitle(R.string.error_message)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //For now if any error comes just exit the application.
                        //Todo: This can be later optimized to find different kinds of errors and
                        //prompting the user to take action accordingly
                        getActivity().finish();
                    }
                })
                .show();
    }

    @Override
    public void showWait() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public int getDisplayWidth() {
        DisplayMetrics resolution = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(resolution);
        //The retina display and logo images need to be reized based on different screen sizes
        int width = resolution.widthPixels;
        if(getResources().getBoolean(R.bool.isWideScreen))
        {
            //if the display is wider than the screen is occupied by both listing and details view,
            //in that case - calculate the width based on the relative weight
            int nListingWeight = getResources().getInteger(R.integer.layout_listing_weight);
            int nDetailsWeight = getResources().getInteger(R.integer.layout_details_weight);
            //The layout weight between the list and detail views are distributed in ratio of 3:2
            width = (int)(width * nListingWeight/(nListingWeight+nDetailsWeight));
            //In case of wide screen disable app bar scrolling.
            disableAppBarScrolling();
        }
        return width;
    }

    /**
     * Helper function to disable scrolling behavior for AppBar
     */
    private void disableAppBarScrolling() {
        //For a wider screen, don't hide the tool bar when scrolling up
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams)mListingAppBar.getLayoutParams();
        if(layoutParams != null) {
            layoutParams.setScrollFlags(0);
        }
    }


    @Override
    public void listingClicked(Listings listing) {
        if(mPropertySelectionListener != null) {
            mPropertySelectionListener.onListingSelected(listing.getAdId(), listing.getDescription());
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallBacks) {
            mPropertySelectionListener = (CallBacks) context;
        } else {
            mPropertySelectionListener = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPropertySelectionListener = null;
    }
}
