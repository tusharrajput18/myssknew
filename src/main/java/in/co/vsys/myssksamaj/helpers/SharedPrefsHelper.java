package in.co.vsys.myssksamaj.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPrefsHelper {

    private static final String TAG = "SharedPrefsHelper";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String USER_MODEL = "USER_MODEL";
    public static final String FCM_TOKEN = "FCM_TOKEN";
    public static final String FCM_TOKEN_SYNCED = "FCM_TOKEN_SYNCED";

    public static final String MEMBER_NAME = "memberName";
    public static final String MEMBER_ID = "memberId";
    public static final String UNIQUE_ID = "uniqueId";
    public static final String PROFILE_URL = "nav_profileUrl";
    public static final String USERTYPE = "userType";
    public static final String EMAIL_ID = "emailid";
    public static final String MOBILE = "mobile";
    public static final String ACCOUNT_CREATED_BY = "accountCreatedBY";
    public static final String TOKEN_ID = "tokenId";
    public static final String USER_IMAGE_URL = "userImageUrl";
    public static final String BASIC_INFO_PER = "basicInfoPer";
    public static final String PHOTO_INFO_PER = "photoInfoPer";
    public static final String INTRO_INFO_PER = "introInfoPer";
    public static final String DESIRED_INFO_PER = "desiredInfoPer";
    public static final String FAMILY_INFO_PER = "familyInfoPer";
    public static final String HIGHER_EDU_INFO_PER = "higherEduInfoPer";
    public static final String HOROSCOPE_INFO_PER = "horoscopeInfoPer";
    public static final String LIFESTYLE_INFO_PER = "lifeStyleInfoPer";
    public static final String ALERT_PERCENT_FLAG = "alertPercentFlag";
    public static final String RECEIVER_TOKEN_ID = "receiverTokenId";
    public static final String MESSAGE_RECEIVER_IMAGE = "messageReceiverImage";
    public static final String MESSAGE_RECEIVER_NAME = "messageReceiverName";
    public static final String MESSAGE_RECEIVER_ID = "messageReceiverId";
    public static final String COMPANY_NAME = "companyName";
    public static final String CONTACTS_VIEWED = "ContactsViewed";
    public static final String BIRTHPLACE = "birthplace";
    public static SharedPrefsHelper instance;
    public Context context;

    private SharedPrefsHelper(Context context) {
        this.context = context;
    }

    public static SharedPrefsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsHelper(context);
        }
        return instance;
    }

    public void clearAll() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Return false if not logged in
     *
     * @return
     */
    public boolean getLoginStatus() {
        try {
            Log.d(TAG, "context: " + context);
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(LOGIN_STATUS,
                    false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveLoginStatus(boolean bl) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(LOGIN_STATUS, bl);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIntVal(String prefsKey) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(prefsKey,0);
    }

    public void saveIntVal(String prefsKey, int val) {
        try {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putInt(prefsKey, val);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringVal(String prefsKey) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(prefsKey,
                "");
    }

    public void saveStringVal(String prefsKey, String val) {
        try {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString(prefsKey, val);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBooleanVal(String prefsKey) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(prefsKey,
                false);
    }

    public void saveBooleanVal(String prefsKey, boolean bl) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(prefsKey, bl);
        editor.commit();
    }
}