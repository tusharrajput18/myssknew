package in.co.vsys.myssksamaj.maindirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import in.co.vsys.myssksamaj.CommonLoginActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.activities_advertisement.AdvertisementActivity;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.CommonUserModel;
import in.co.vsys.myssksamaj.mainMobileModel.OtpModel;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMobileNumberActivity extends AppCompatActivity {


    EditText et_main_mobileno;
    TextView tv_sendotp;


    ProgressBar mainmobile_number_progressbar;
    private SharedPreferences mPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mobile_number);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        init();
    }


    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "Onbackpressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        Intent i=new Intent(this,MainMobileNumberActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void init() {

        et_main_mobileno = (EditText) findViewById(R.id.et_main_mobileno);
        tv_sendotp = (TextView) findViewById(R.id.tv_sendotp);

        mainmobile_number_progressbar = (ProgressBar) findViewById(R.id.mainmobile_number_progressbar);

        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate1()) {
                    loginUser(et_main_mobileno.getText().toString().trim());
                }
            }
        });


    }

    private boolean isValidate1() {
        if (et_main_mobileno.getText().toString().length() < 10) {
            Toast.makeText(MainMobileNumberActivity.this, "Invalid Mobile No.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void loginUser(String mobil) {
        mainmobile_number_progressbar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<OtpModel> call = service.logingetOtp(mobil);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                mainmobile_number_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {
                        String mobile = response.body().getDetailOtpModels().get(0).getMobile();
                        String otp = response.body().getDetailOtpModels().get(0).getOtpNo();
                        String mobileno = et_main_mobileno.getText().toString();

                    Toast.makeText(MainMobileNumberActivity.this, ""+otp, Toast.LENGTH_SHORT).show();

                        Log.d("mytag", "onResponse: mobileotp"+mobile);

                        if (mobile.length()>0) {
                            Log.d("mytag", "onResponse: olduser ");
                            mPreference.edit().putString("usertypenewold", "old").apply();
                            mPreference.edit().putString("mobileno", mobileno).apply();
                            mPreference.edit().putString("common_fname", response.body().getDetailOtpModels().get(0).getFname()).apply();
                            mPreference.edit().putString("common_mname", response.body().getDetailOtpModels().get(0).getMname()).apply();
                            mPreference.edit().putString("common_lname", response.body().getDetailOtpModels().get(0).getLname()).apply();
                            mPreference.edit().putString("common_mobileNo", response.body().getDetailOtpModels().get(0).getMobile()).apply();
                            mPreference.edit().putString("App_common_userId", response.body().getDetailOtpModels().get(0).getApploginid()).apply();
                            mPreference.edit().putString("App_userprofile_img", response.body().getDetailOtpModels().get(0).getPhoto()).apply();
                            mPreference.edit().putString("App_useradhar_img", response.body().getDetailOtpModels().get(0).getAadhar()).apply();

                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(MainMobileNumberActivity.this, OtpVerificationActivity.class);
                            bundle.putString("otp", otp);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        } else {
                            Log.d("mytag", "onResponse: new user ");
                            mPreference.edit().putString("usertypenewold", "new").apply();
                            mPreference.edit().putString("mobileno", mobileno).apply();
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(MainMobileNumberActivity.this, OtpVerificationActivity.class);
                            bundle.putString("otp", otp);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //Toast.makeText(MainMobileNumberActivity.this, ""+otp, Toast.LENGTH_SHORT).show();
                        }



                } else {
                    Toast.makeText(MainMobileNumberActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                mainmobile_number_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("mytag", "onFailure: " + t.getMessage());

            }
        });

    }


}
/*9923449493

        HomeActivity
        HomeMatrimonyFragment
        ListActivity
        MatrimonyProfileViewActivity*/

