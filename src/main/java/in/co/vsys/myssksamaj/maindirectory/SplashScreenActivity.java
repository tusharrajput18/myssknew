package in.co.vsys.myssksamaj.maindirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.matrimonyfcm.MyFirebaseInstanceIDService;
import io.fabric.sdk.android.Fabric;

public class SplashScreenActivity extends AppCompatActivity {

    private Context mContext;
    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences mPreference;

    String checksharde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = SplashScreenActivity.this;

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        FirebaseApp.initializeApp(this);
        Fabric.with(mContext, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        checksharde=mPreference.getString("otp","");

        Log.d("mytag", "onCreate: otp "+checksharde);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new MyFirebaseInstanceIDService().onTokenRefresh();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (checksharde == null || checksharde.isEmpty())
                {
                    Intent i = new Intent(mContext, MainMobileNumberActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(mContext, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

}
