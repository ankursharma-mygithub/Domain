package com.android.testproject.domainpropertylistings.ui.main;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.testproject.domainpropertylistings.R;
import com.android.testproject.domainpropertylistings.ui.details.PropertyDetailFragment;

public class MainActivity extends AppCompatActivity implements PropertyListFragment.CallBacks {

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //Update the fragment container with the
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null) {
            fragment = PropertyListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onListingSelected(String adId, String description) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment newDetail = PropertyDetailFragment.newInstance(adId, description);
        //based on whether the detail container is visible or not displays the detail of the selected fragment.
        if(findViewById(R.id.fragment_detail_container) == null) {
            fm.beginTransaction()
                    .replace(R.id.fragment_container, newDetail)
                    .addToBackStack(null)
                    .commit();

        } else {
            fm.beginTransaction()
                    .replace(R.id.fragment_detail_container, newDetail)
                    .commit();
        }
    }
}
