package com.voices.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class AppUtils {

    /**
     * Method to get version name of the application
     *
     * @param mContext
     * @return VersionName
     */
    public static String getAppVersionName(Context mContext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    /**
     * Method to show Toast
     *
     * @param context
     * @param toastMsg
     */
    public static void showToast(Context context, String toastMsg) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to check internet connection
     *
     * @param mContext
     * @return boolean
     */
    public static boolean checkNetworkConnection(Context mContext) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }
}
