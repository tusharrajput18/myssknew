package in.co.vsys.myssksamaj.maindirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import in.co.vsys.myssksamaj.CommonRegisterActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.activities_advertisement.AdvertisementActivity;
import in.co.vsys.myssksamaj.activities_advertisement.Advertisement_Detail_activity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.mainMobileModel.OtpModel;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {

    private SharedPreferences mPreference;
    String usertype="";

    Toolbar toolbar_otp_verification;
    PinEntryEditText app_pin;

    TextView tv_verify;

    String otpfromgetstarted;
    ProgressBar progressbar_otpverification;

    TextView tv_resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        usertype= mPreference.getString("usertypenewold","0");
        Log.d("mytag", "onCreate: usertype "+usertype);

        toolbar_otp_verification=(Toolbar)findViewById(R.id.toolbar_otp_verification);
        setSupportActionBar(toolbar_otp_verification);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Otp Verification");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        app_pin=(PinEntryEditText)findViewById(R.id.app_pin);
        tv_verify=(TextView) findViewById(R.id.tv_verify);
        tv_resend=(TextView) findViewById(R.id.tv_resend);


        progressbar_otpverification=(ProgressBar)findViewById(R.id.progressbar_otpverification);

        Bundle bundle = getIntent().getExtras();
        otpfromgetstarted = bundle.getString("otp");

        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp=app_pin.getText().toString();
                String confirmotp=otpfromgetstarted;

                if(otp.equals(confirmotp)){
                    if(mPreference.getString("usertypenewold","0").equals("new")){
                        Intent intent=new Intent(OtpVerificationActivity.this, CommonRegisterActivity.class);
                        startActivity(intent);
                    }else if(mPreference.getString("usertypenewold","0").equals("old")) {
                        mPreference.edit().putString("otp",confirmotp ).apply();
                        Intent intent =new Intent(OtpVerificationActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }else {
                    Toast.makeText(OtpVerificationActivity.this, "Please enter valid otp ?", Toast.LENGTH_SHORT).show();
                }


            }
        });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser( mPreference.getString("mobileno",""));
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                  onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void loginUser(String mobil) {
        progressbar_otpverification.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<OtpModel> call = service.logingetOtp(mobil);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                progressbar_otpverification.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {
                    String mobile = response.body().getDetailOtpModels().get(0).getMobile();
                    String otp = response.body().getDetailOtpModels().get(0).getOtpNo();
                    otpfromgetstarted=otp;
                    Toast.makeText(OtpVerificationActivity.this, ""+otp, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtpVerificationActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                progressbar_otpverification.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("mytag", "onFailure: " + t.getMessage());

            }
        });

    }
}