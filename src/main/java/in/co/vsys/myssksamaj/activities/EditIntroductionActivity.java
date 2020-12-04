package in.co.vsys.myssksamaj.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class EditIntroductionActivity extends AppCompatActivity {

    private EditText edt_textIntro;
    private Button btn_next;
    private String textIntroduction;
    private int memeberId;
    private SharedPreferences mPreferences;
    private ProgressBar mProgressBar;
    private static final String TAG = EditIntroductionActivity.class.getSimpleName();
    private static final String MEMEBER_ID = "MemberId";
    private static final String INTRODUCTION = "Introduction";
    private static final String VIDEO_PATH = "Video";
    private static final String INTRODUCTION_PROFILE_PERCENTAGE = "CandidateIntroductionPercentage";
    private String introduction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matro_intro);

        btn_next = (Button) findViewById(R.id.btn_introNext);
        edt_textIntro = (EditText) findViewById(R.id.edt_textIntroduction);
        mProgressBar = (ProgressBar) findViewById(R.id.introduction_progressBar);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_intro);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Self Introduction");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        memeberId = mPreferences.getInt("memberId", 0);

        Bundle lookingIntent = getIntent().getExtras();

        if (lookingIntent != null) {

            introduction = lookingIntent.getString("edit_introduction");

            if (introduction != null) {

                edt_textIntro.setText(introduction);
            } else {

                edt_textIntro.setText("");
            }

        }


        btn_next.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                textIntroduction = edt_textIntro.getText().toString().trim();
                sendIntroduction();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
            //    startActivity(new Intent(EditIntroductionActivity.this, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void sendIntroduction() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.uploadCandidateIntroUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "introduction response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {
                                Toast.makeText(EditIntroductionActivity.this, "Introduction uploaded successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditIntroductionActivity.this, EditMyProfileActivity.class));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "introduction error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put(MEMEBER_ID, String.valueOf(memeberId));
                param.put(INTRODUCTION, textIntroduction);
                param.put(VIDEO_PATH, "");
                param.put(INTRODUCTION_PROFILE_PERCENTAGE, "10");
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditIntroductionActivity.this, EditMyProfileActivity.class));
    }*/
}
