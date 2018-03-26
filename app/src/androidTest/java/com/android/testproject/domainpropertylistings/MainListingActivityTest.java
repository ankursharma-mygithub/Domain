package com.android.testproject.domainpropertylistings;

/**
 * Created by ankursharma on 3/8/18.
 */

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.testproject.domainpropertylistings.common.Utils;
import com.android.testproject.domainpropertylistings.ui.main.MainActivity;
import com.android.testproject.domainpropertylistings.ui.main.PropertyListAdapter;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.assertNotEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class MainListingActivityTest {

    private static final String TAG = "MainListingActivityTest";

    private static final long WAIT_TIME = 5000;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

   /**
     * UI test assering screen state when all data is present.
    * Also tests if all the items in the JSON file are listed or not.
    * ASSUMPTION : Device is connected to internet.
    * SUCCESS: When device is connected to internet.
     */
    @Test
    public void checkAllItemsFromJsonLoaded() throws InterruptedException {
        //Put sleep here to wait for recyclerview to initialize.
        try {
            Thread.sleep(WAIT_TIME);
        }catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
        if(Utils.isNetworkConnected(activityTestRule.getActivity())) {
            //Get recyclerview
            RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.propertyRecyclerView);
            Espresso.onView(ViewMatchers.withId(R.id.propertyRecyclerView)).check(matches(isDisplayed()));
            //Check that items are loaded
            PropertyListAdapter adapter = (PropertyListAdapter) (recyclerView.getAdapter());
                //Ensure that all elements from list are displayed in the recyclerview.
            if(!Utils.isNetworkConnected(activityTestRule.getActivity())) {
                assertNotEquals(adapter.getItemCount(), 0);
            }
        }
    }

    /**
     * Asserting the screen state in error state(no internet)
     * Assumption : Device is not connected to internet.
     * SUCCESS: When device is disconnected from internet.
     */
    @Test
    public void checkErrorMessageIsDisplayedForNoInternet()  {
        //Put sleep here to wait for recyclerview to initialize.
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
        //If device is not connected to internet than the applications hould show an error dialog.
        //Here checking if the dialog si displayed with proper message or not.
        String errorMessage = "Please connect to internet to view property listings.";
        if(!Utils.isNetworkConnected(activityTestRule.getActivity())) {
            Espresso.onView(ViewMatchers.withText(errorMessage)).check(matches(isDisplayed()));
        }
    }

}
