package com.android.testproject.domainpropertylistings.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ankursharma on 3/24/18.
 */

/**
 *  Utility class
 */
public class Utils {

    /**
     * Check if the device is connected to internet or not.
     */
    public static boolean isNetworkConnected(Context context) {
        boolean bConnected = false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            bConnected = ((activeNetwork != null) && (activeNetwork.isConnectedOrConnecting()));
        }
        return bConnected;
    }

}
