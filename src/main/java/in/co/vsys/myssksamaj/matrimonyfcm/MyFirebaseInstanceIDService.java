package in.co.vsys.myssksamaj.matrimonyfcm;

/**
 * Created by nilesh on 19/7/16.
 */

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import in.co.vsys.myssksamaj.matrimonyutils.Constants;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private SharedPreferences sharedPref = null;
    private static final String TAG = "My Firebase Instance Id";

    @Override
    public void onTokenRefresh() {

        Constants.ARG_FIREBASE_TOKEN = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "onTokenRefresh: " + Constants.sFCM_KEY);
        if (Constants.sFCM_KEY.length() > 50) {
            Log.e(TAG, "onTokenRefresh: " + Constants.sFCM_KEY);
        }
        //  sendUpdatedFCMkey();
    }

    /*private void sendUpdatedFCMkey() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Constants.sUSER_ID.length() > 0)

                    Log.d(TAG, "run: " + sharedPref.getString(Constants.shk_FCM_REGISTRATION, ""));

                methods.connectLong(Constants.sAPI + "UserDeviceIdUpdate?UserId=" + Constants.sUSER_ID + "&UserType=" + Constants.sUSER_TYPE + "&DeviceId=" + sharedPref.getString(Constants.shk_FCM_REGISTRATION, ""));
            }
        }).start();
    }*/

}