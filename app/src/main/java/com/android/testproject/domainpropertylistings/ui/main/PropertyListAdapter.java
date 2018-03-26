package com.android.testproject.domainpropertylistings.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.testproject.domainpropertylistings.R;
import com.android.testproject.domainpropertylistings.ui.main.PropertyListContract.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for the recyclerView for the main screen.The adapter doesn't store the data and works with presenter layer to
 * get and display the data.
 * Created by ankursharma on 3/8/18.
 */

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder>{

    //To select a particular view of ViewHolder.
    private static final int PROPERTY_LISTING_OTHERS = 0;
    private static final int PROPERTY_LISTING_ELITE = 1;

    //Presenter class responsible for updating adapter and ViewHolder
    private Presenter mPresenter;

    //Constructor
    public PropertyListAdapter(Presenter presenter) {
        mPresenter = presenter;
    }

    //Selected item position
    private int mSelectedItemPos = -1;

    public int getSelectedItemPos() {
        return mSelectedItemPos;
    }

    public void setSelectedItemPos(int selectedItemPos) {
        mSelectedItemPos = selectedItemPos;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = PROPERTY_LISTING_OTHERS;
        if(mPresenter.isPropertyElite(position)) {
            itemType = PROPERTY_LISTING_ELITE;
        }
        return itemType;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == PROPERTY_LISTING_ELITE) {
            view = inflater.inflate(R.layout.elite_property_card, parent, false);
        } else {
            view = inflater.inflate(R.layout.property_card, parent, false);
        }
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PropertyViewHolder holder, final int position) {
        mPresenter.onBindItemAtPosition(holder, position);
        holder.itemView.setSelected(mSelectedItemPos == position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.listingClicked(position);
                notifyItemChanged(mSelectedItemPos);
                mSelectedItemPos = position;
                notifyItemChanged(mSelectedItemPos);
            }
        });
        holder.mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isActive = !holder.mFavoriteButton.isActivated();
                holder.mFavoriteButton.setActivated(isActive);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPresenter.getItemsCount();
    }


    /**
     * View holdler for recyclerview
     */
    public class PropertyViewHolder extends RecyclerView.ViewHolder implements PropertyListContract.RowItemHolder {

        @BindView(R.id.mainRetinaDisplay)
        ImageView mMainRetinaDisplay;

        @Nullable
        @BindView(R.id.secondRetinaDisplay)
        ImageView mSecondaryRetinaDisplay;

        @BindView(R.id.display_price)
        TextView mDisplayPrice;

        @BindView(R.id.display_specifications)
        TextView mDisplayPropertySpecs;

        @BindView(R.id.displayable_address)
        TextView mDisplayableAddress;

        @BindView(R.id.agency_logo)
        ImageView mAgencyLogo;

        @BindView(R.id.favorite)
        ImageButton mFavoriteButton;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void updateDisplayPrice(String displayPrice) {
            mDisplayPrice.setText(displayPrice);
        }

        @Override
        public void updateDisplaySpec(String displaySpec) {
            mDisplayPropertySpecs.setText(displaySpec);

        }

        @Override
        public void updateDisplayAddress(String displayAddress) {
            mDisplayableAddress.setText(displayAddress);
        }

        @Override
        public ImageView getMainRetinaDisplay() {
            return mMainRetinaDisplay;
        }

        @Override
        public ImageView getSecondRetinaDisplay() {
            return mSecondaryRetinaDisplay;
        }

        @Override
        public ImageView getAgencyLogo() {
            return mAgencyLogo;
        }

    }
}
