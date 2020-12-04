package in.co.vsys.myssksamaj.maindirectory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.TransformedResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BasicInfoActivity;
import in.co.vsys.myssksamaj.activities.EditAstroInfoActivity1;
import in.co.vsys.myssksamaj.activities.EditMyProfileActivity;
import in.co.vsys.myssksamaj.activities.HomeActivity;
import in.co.vsys.myssksamaj.contracts.LoginContract;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.matrimonyutils.ChatContract;
import in.co.vsys.myssksamaj.matrimonyutils.ChatPresenter;
import in.co.vsys.myssksamaj.matrimonyutils.Constants;
import in.co.vsys.myssksamaj.model.Chat;
import in.co.vsys.myssksamaj.model.FirebaseModel;
import in.co.vsys.myssksamaj.model.MemberModel;
import in.co.vsys.myssksamaj.model.data_models.UserLoginModel;
import in.co.vsys.myssksamaj.presenters.LoginPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class LoginActivity extends AppCompatActivity implements ChatContract.View, LoginContract.LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Context mContext;
    private AppCompatEditText edt_mobileNumber, edt_password;
    private AppCompatButton btn_login;
    private ConnectionHelper mDetector;
    private Snackbar mSnackbar;
    private RelativeLayout mRelativeLayout;
    private SharedPreferences mPreferences;
    private static final String MOBILE_NO = "mobile";
    private static final String PASSWORD = "password";
    private static final String DEVICE_ID = "deviceid";
    private ProgressBar mProgressBar;
    private ChatPresenter mChatPresenter;
    private TextView tv_response;
    private LinearLayout mLinearLayout;
    private String deviceId;
    private String mobileNumber, password;
    private LoginContract.LoginOps loginPresenter;
    private MemberModel memberModel;
    private static String loginType;
    private String birthPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_login);
        mContext = this;
        FirebaseApp.initializeApp(this);

        if (SharedPrefsHelper.getInstance(mContext).getLoginStatus()) {
            proceedToHome();
        }

        deviceId = FirebaseInstanceId.getInstance().getToken();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_matrimonyLogin);

        edt_mobileNumber = (AppCompatEditText) findViewById(R.id.edt_matriLoginMobile);
        edt_password = (AppCompatEditText) findViewById(R.id.edt_matriLoginPassword);
        btn_login = (AppCompatButton) findViewById(R.id.btn_matriLogin);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.layout_login_matri);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_parentLogin);

        mChatPresenter = new ChatPresenter(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Matrimony Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        edt_mobileNumber.setText("" + mPreferences.getString("loginMobile", ""));
        edt_password.setText("" + mPreferences.getString("loginPassword", ""));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileNumber = edt_mobileNumber.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                if (ConnectionHelper.networkConnectivity(mContext)) {
                    if (!isValidMobile(mobileNumber)) {
                        edt_mobileNumber.setError("Please enter valid mobile no.");
                    } else if (!isValidPassword(password)) {
                        edt_password.setError("Please enter at least 4 characters password");
                    } else {
                        loginPresenter.loginUser(mobileNumber, password, deviceId);
//                        loginTask(mobileNumber, password);
                    }
                } else {
                    mSnackbar = Snackbar.make(
                            mRelativeLayout, getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG);
                    mSnackbar.show();
                }
            }
        });
        findViewById(R.id.tv_matriLoginForgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayForgotPasswordDialog();
            }
        });
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(mContext, LoginOptionsActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void forgotMatrimonyPassword(final String mobNo) {

        mLinearLayout.setVisibility(View.GONE);
        //mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matrimony_forgot_password_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  mProgressBar.setVisibility(View.GONE);

                        try {
                            JSONObject object = new JSONObject(response);
                            int success = object.getInt("success");
                            String message = object.getString("message");

                            mLinearLayout.setVisibility(View.VISIBLE);
                            if (success == 1) {
                                tv_response.setText("" + message);
                            } else {
                                tv_response.setText("" + message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   mProgressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("mobile", mobNo);
                return param;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void displayForgotPasswordDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);

        mBuilder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_matrimony_forgat, null);
        tv_response = view.findViewById(R.id.row_matriForgotResponse);
        mLinearLayout = view.findViewById(R.id.row_matriForgotLayout);
        final AppCompatEditText edt_mobileNo = view.findViewById(R.id.row_matriForgotMobile);
        AppCompatButton btn_submit = view.findViewById(R.id.row_matriForgotSubmit);

        mBuilder.setView(view);
        mBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = mBuilder.create();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = edt_mobileNo.getText().toString().trim();
                forgotMatrimonyPassword(no);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(mContext, LoginOptionsActivity.class));
    }

    private boolean isValidPassword(String bName) {
        if (bName != null && bName.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isValidMobile(String bMobile) {
        if (bMobile != null && bMobile.length() > 9 && bMobile.length() < 11) {
            return true;
        }
        return false;
    }

    @Override
    public void onSendMessageSuccess() {

    }

    @Override
    public void onUpdateMessageSuccess() {

    }

    @Override
    public void onSendMessageFailure(String message) {

    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {

    }

    @Override
    public void onGetMessagesFailure(String message) {

    }


    @Override
    public void showCandidateLogin(final UserLoginModel userLoginModel) {

        try {
            FirebaseModel firebaseModel = new FirebaseModel();

            final int memberId = Integer.parseInt(userLoginModel.getMemberId());

            String uniqueId = userLoginModel.getUnqid();
            String firstName = userLoginModel.getFname();
            String lastName = userLoginModel.getLname();
            String accountCreatedBy = userLoginModel.getCreatedby();
            loginType = userLoginModel.getLoginType();

            Log.d(TAG, "showCandidateLogin:11111111 " + loginType+" "+uniqueId);

            firebaseModel.setName(firstName + " " + lastName);
            firebaseModel.setPhotoUrl(userLoginModel.getSelfphoto());
            firebaseModel.setUid(String.valueOf(memberId));
            firebaseModel.setOnline(true);

            String message="Login Successfully...";

            Toast.makeText(mContext, ""+message, Toast.LENGTH_SHORT).show();


            mPreferences.edit().putString("nav_memberName", firstName + " " + lastName).apply();
            mPreferences.edit().putString("nav_profileUrl", userLoginModel.getSelfphoto()).apply();
            mPreferences.edit().putString("accountCreatedBY", accountCreatedBy).apply();
            mPreferences.edit().putString("loginMobile", userLoginModel.getMobile()).apply();
            mPreferences.edit().putString("gender", userLoginModel.getGender()).apply();
            mPreferences.edit().putString("loginPassword", password).apply();
            mPreferences.edit().putString(Constants.ARG_FIREBASE_TOKEN, userLoginModel.getDeviceid()).apply();
            mPreferences.edit().putString("basicInfoPer", userLoginModel.getBasicinformationpercentage()).apply();
            mPreferences.edit().putString("photoInfoPer", userLoginModel.getPhotoinformationpercentage()).apply();
            mPreferences.edit().putString("introInfoPer", userLoginModel.getCandidateIntroduction()).apply();
            mPreferences.edit().putString("desiredInfoPer", userLoginModel.getDesiredpartnerinformationpercentage()).apply();
            mPreferences.edit().putString("familyInfoPer", userLoginModel.getFamilyinformationpercentage()).apply();
            mPreferences.edit().putString("higherEduInfoPer", userLoginModel.getHighereducationpercentage()).apply();
            mPreferences.edit().putString("horoscopeInfoPer", userLoginModel.getHoroscopeinformationpercentage()).apply();
            mPreferences.edit().putString("lifeStyleInfoPer", userLoginModel.getLifeStyleInfoPercentage()).apply();
            mPreferences.edit().putInt("alertPercentFlag", 1).apply();
            mPreferences.edit().putString("loginType", loginType).apply();



            // Log.d(TAG, "showCandidateLogin: "+userLoginModel.getLoginType());

            int userType = Utilities.getInt(userLoginModel.getUserType());
            mPreferences.edit().putInt("userType", userType).apply();

            String mobile = userLoginModel.getMobile();
            mPreferences.edit().putString("mobile", mobile).apply();

            String emailid = userLoginModel.getEmailid();
            mPreferences.edit().putString("emailid", emailid).apply();

            mPreferences.edit().putString("nav_currentUserID", "" + uniqueId).apply();

            MainPresenter.getInstance().setUniqueId(mContext, "" + uniqueId);
            MainPresenter.getInstance().setMemberId(mContext, memberId);
            MainPresenter.getInstance().setUserType(mContext, userType);

            MainPresenter.getInstance().setEmailId(mContext, Utilities.getString(emailid));
            MainPresenter.getInstance().setPhone(mContext, mobile);
            MainPresenter.getInstance().setMemberName(mContext, firstName + " " + lastName);

            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.ACCOUNT_CREATED_BY, accountCreatedBy);

            long timeStamp = new Date().getTime();
            firebaseModel.setTimestamp(timeStamp);

            //SharedPrefsHelper.getInstance(mContext).saveLoginStatus(true);



            mChatPresenter.sendUserDetails(mContext, firebaseModel);

            if(loginType.equalsIgnoreCase("P")){

                SharedPrefsHelper.getInstance(mContext).saveLoginStatus(true);
                Log.d(TAG, "showCandidateLogin: proceedhome");
                startActivity(new Intent(mContext, HomeActivity.class));
                finish();

            }else if(loginType.equalsIgnoreCase("C")){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        showProfileInfo(memberId);
                    }
                });

            }




           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    uploadFile(selectedFilePath);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    data_submit();
                                }
                            });
                        }
                    }).start();
                }
            }).start();*/




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void proceedToHome() {
       /* startActivity(new Intent(mContext, HomeActivity.class));
        finish();*/

        if (loginType.equalsIgnoreCase("C") ) {

            Log.d(TAG, "proceedToHome: "+loginType);
            if (birthPlace.equalsIgnoreCase("")) {

                Log.d(TAG, "showCandidateLogin: " + birthPlace);

                Intent intent = new Intent(LoginActivity.this, EditAstroInfoActivity1.class);
                startActivity(intent);
                finish();

            }else if(birthPlace!=null){

                SharedPrefsHelper.getInstance(mContext).saveLoginStatus(true);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        } else if(loginType.equalsIgnoreCase("P")) {
            Log.d(TAG, "proceedToHome: "+loginType);
            Log.d(TAG, "showCandidateLogin: homeactivity");
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

  /*  private void proceedToHome() {
        startActivity(new Intent(mContext, HomeActivity.class));
        finish();
    }*/

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        if (message.equals(in.co.vsys.myssksamaj.utils.Constants.ERROR_HANDLING.SERVER_ERROR)) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } else {
            if(message.equalsIgnoreCase("There is no row at position 0."))
                message="Username and Password do not match";
            alertAccountStatus(message);
        }
    }

    private void alertAccountStatus(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(LoginActivity.this, LoginOptionsActivity.class));
                finish();
            }
        });

        alertDialog.show();
    }

    private void showProfileInfo(final int memberId) {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.allInfoUsingMemeberIdUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, "all member info response" + response);
                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");
                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int memberId = object.getInt("MemberId");
                                    birthPlace = object.getString("BirthPlace");
                                    proceedToHome();
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, EditAstroInfoActivity1.class);
                                startActivity(intent);
                                finish();
                                //Toast.makeText(LoginActivity.this, "Details not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "memberInfo error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put("MemberId", String.valueOf(memberId));

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
