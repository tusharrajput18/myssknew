package in.co.vsys.myssksamaj.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class MainLoginActivity extends AppCompatActivity {

    private AppCompatEditText edt_username, edt_password;
    private Button btn_login;
    private AppCompatTextView tv_forgotPassword;
    private String username, password;
    private ProgressBar mProgressBar;
    AppCompatTextView btn_registration, btn_guest;
    private RelativeLayout mLinearLayout;
    private int appLoginId;
    private static final String TAG = MainLoginActivity.class.getSimpleName();
    private static final String USERNAME = "mobile";
    private static final String PASSWORD = "password";
    private SharedPreferences mPreferences;
    private Snackbar mSnackbar;
    private int session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        edt_username = (AppCompatEditText) findViewById(R.id.edt_username);
        edt_password = (AppCompatEditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_registration = (AppCompatTextView) findViewById(R.id.btn_log_reg);
        btn_guest = (AppCompatTextView) findViewById(R.id.btn_log_guest);
        tv_forgotPassword = (AppCompatTextView) findViewById(R.id.tv_forgotPassword);
        mProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        mLinearLayout = (RelativeLayout) findViewById(R.id.login_layout);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        session = mPreferences.getInt("appLoginId", 0);

        if (session > 0) {
            startActivity(new Intent(MainLoginActivity.this, MainActivity.class));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edt_username.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                if ((TextUtils.isEmpty(username)) || (TextUtils.isEmpty(password))) {

                    mSnackbar = Snackbar.make(
                            mLinearLayout, getResources().getString(R.string.empty_editText), Snackbar.LENGTH_LONG);
                    mSnackbar.show();
                } else {

                    loginTask();
                }
            }
        });

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLoginActivity.this, MainRegistrationActivity.class));
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainLoginActivity.this, MainActivity.class);

                int guestLogin = 1;

                mPreferences.edit().putInt("guestLogin", guestLogin).apply();

                startActivity(intent);
            }
        });

        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainLoginActivity.this, ForgetPasswordActivity.class));
            }
        });
    }

    private void loginTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.mainLoginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "loginResponse :" + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    appLoginId = object.getInt("AppLoginId");

                                    if (appLoginId >= 1) {

                                        mPreferences.edit().putInt("appLoginId", appLoginId).apply();
                                        System.out.println("appLoginId" + appLoginId);
                                        mPreferences.edit().remove("guestLogin").apply();

                                        startActivity(new Intent(MainLoginActivity.this, MainActivity.class));
                                    }

                                }
                            } else {

                                Toast.makeText(MainLoginActivity.this, "Please try again...!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "login error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(USERNAME, username);
                params.put(PASSWORD, password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}