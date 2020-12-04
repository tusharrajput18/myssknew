package in.co.vsys.myssksamaj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.activities_advertisement.AdvertisementActivity;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.CommonUserModel;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonLoginActivity extends AppCompatActivity {

    LinearLayout tv_get_started;
    TextInputEditText et_common_login_password,et_commonLogin_number;
    TextView tv_common_login,tv_may_belatter;
    private SharedPreferences mPreference;
    ProgressBar common_login_progressbar;
    ArrayList<CommonUserModel.CommonUserModelList> commonUserModelLists;

    String navigateAFLogin;
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_login);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            navigateAFLogin = bundle.getString("Login");
        }
        Log.d("mytag", "onCreate: "+navigateAFLogin);
        init();
    }

    private void init() {
        commonUserModelLists=new ArrayList<>();
        common_login_progressbar=(ProgressBar)findViewById(R.id.common_login_progressbar);
        et_commonLogin_number=(TextInputEditText)findViewById(R.id.et_commonLogin_number);
        et_common_login_password=(TextInputEditText)findViewById(R.id.et_common_login_password);
        tv_get_started=(LinearLayout)findViewById(R.id.tv_get_started);
        tv_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommonLoginActivity.this,CommonRegisterActivity.class));
            }
        });

        tv_common_login=(TextView)findViewById(R.id.tv_common_login);
        tv_may_belatter=(TextView)findViewById(R.id.tv_may_belatter);

        tv_may_belatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(navigateAFLogin.equals("news")){
                    Log.d("mytag", "onResponse: 123456");
                    startActivity(new Intent(CommonLoginActivity.this, NewsDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }else if(navigateAFLogin.equals("adds")){
                    startActivity(new Intent(CommonLoginActivity.this, AdvertisementActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }else if(navigateAFLogin.equals("business")){
                    startActivity(new Intent(CommonLoginActivity.this,BusinessDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });


       /* tv_may_belatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommonLoginActivity.this, BusinessDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });*/

        tv_common_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=et_commonLogin_number.getText().toString();
                String pass=et_common_login_password.getText().toString();
                if(isValidate1()){
                    loginUser(number,pass);
                }
            }
        });
    }

    private boolean isValidate1() {
        if (et_commonLogin_number.getText().toString().length() < 10 ){
            Toast.makeText(CommonLoginActivity.this, "Invalid Mobile No.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_common_login_password.getText().toString().length() < 3) {
            Toast.makeText(CommonLoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void loginUser(String mobil, String password) {
        Log.d("mytag", "loginUser: "+navigateAFLogin);
        common_login_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<CommonUserModel> call = service.loginUser(mobil,password);
        call.enqueue(new Callback<CommonUserModel>() {
            @Override
            public void onResponse(Call<CommonUserModel> call, Response<CommonUserModel> response) {
                common_login_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()==1){
                        commonUserModelLists=response.body().getCommonUserModelLists();
                        mPreference.edit().putString("common_fname", commonUserModelLists.get(0).getFirstName()).apply();
                        mPreference.edit().putString("common_mname", commonUserModelLists.get(0).getMiddle()).apply();
                        mPreference.edit().putString("common_lname", commonUserModelLists.get(0).getLName()).apply();
                        mPreference.edit().putString("common_mobileNo", commonUserModelLists.get(0).getMobile()).apply();
                        mPreference.edit().putString("App_common_userId", commonUserModelLists.get(0).getAppLoginId()).apply();
                        Toast.makeText(CommonLoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if(navigateAFLogin.equals("news")){
                            Log.d("mytag", "onResponse: 123456");
                            startActivity(new Intent(CommonLoginActivity.this, NewsDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }else if(navigateAFLogin.equals("adds")){
                            startActivity(new Intent(CommonLoginActivity.this, AdvertisementActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }else if(navigateAFLogin.equals("business")){
                            startActivity(new Intent(CommonLoginActivity.this,BusinessDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }

                    }else {
                        Toast.makeText(CommonLoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CommonLoginActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CommonUserModel> call, Throwable t) {
                common_login_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(CommonLoginActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
