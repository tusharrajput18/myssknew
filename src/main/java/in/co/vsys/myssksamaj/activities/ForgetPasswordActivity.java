package in.co.vsys.myssksamaj.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText edt_mobileNumber;
    private AppCompatButton btn_submit;
    private String mobileNumber;
    private static final String MOBILE_NUMBER = "mobile";
    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        edt_mobileNumber = (EditText) findViewById(R.id.edt_forgotPassword);
        btn_submit = (AppCompatButton) findViewById(R.id.btn_submitPassword);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_forgotPassword);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_forgotPass);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Forgot password");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileNumber = edt_mobileNumber.getText().toString().trim();

                if ((TextUtils.isEmpty(mobileNumber))) {

                    Toast.makeText(ForgetPasswordActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                } else {

                    forgotPassowrd();
                }

            }
        });

    }

    private void forgotPassowrd() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.forgotPasswordUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, "forgotResponse :" + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                Toast.makeText(ForgetPasswordActivity.this, "Password successfully sent On Register Email Id", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, MainLoginActivity.class));
                            } else {

                                Toast.makeText(ForgetPasswordActivity.this, "Please enter register mobile number...!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, "forgot error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(MOBILE_NUMBER, mobileNumber);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(ForgetPasswordActivity.this, MainLoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ForgetPasswordActivity.this, MainLoginActivity.class));
    }


}
