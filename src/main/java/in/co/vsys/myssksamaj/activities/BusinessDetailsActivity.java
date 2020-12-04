package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.data_models.BusinessModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class BusinessDetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private static final String TAG = BusinessDetailsActivity.class.getSimpleName();
    private static final String BUSINESS_ID = "BusinessId";

    private LinearLayout mTitleContainer;
    private AppCompatImageView img_profile;
    private AppCompatTextView mTitle, tv_businessName, tv_cityName, tv_call, tv_direction, tv_mail, tv_website, tv_officeNumber, tv_mobileNo,
            tv_address, tv_services, tv_aboutUs, tv_personName,
            tv_monHours, tv_tuesHours, tv_wendHOurs, tv_thursHour, tv_friHours, tv_satHours, tv_sunHOurs;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private int busiId;
    private BusinessModel businessModel;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_busiDetails);

        bindActivity();

        mAppBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            busiId = bundle.getInt("businessId", 0);
            loadBusinessDetails(busiId);
        } else {

            Toast.makeText(this, "something went wrong...", Toast.LENGTH_SHORT).show();
        }


        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeCall();

            }
        });

        tv_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();
            }
        });

        tv_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BusinessDetailsActivity.this, BusinessMapDisplayActivity.class);

                intent.putExtra("busi_lat", businessModel.getLatitude());
                intent.putExtra("busi_long", businessModel.getLongitude());

                startActivity(intent);
            }
        });


    }

    private void bindActivity() {

        img_profile = (AppCompatImageView) findViewById(R.id.img_busiDisplay_image);
        mTitle = (AppCompatTextView) findViewById(R.id.tv_text_title1);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_busiDetails);
        tv_businessName = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_title);
        tv_cityName = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_city);
        tv_call = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_call);
        tv_direction = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_direction);
        tv_personName = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_contactPerson);
        tv_mail = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_mail);
        tv_website = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_website);
        tv_officeNumber = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_contactNumber);
        tv_mobileNo = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_mobileNumber);
        tv_address = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_address);
        tv_services = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_services);
        tv_aboutUs = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_aboutUs);
        mTitleContainer = (LinearLayout) findViewById(R.id.linearLayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout_busi);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_busiDetails);
        tv_monHours = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_monHours);
        tv_tuesHours = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_tuesHours);
        tv_wendHOurs = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_wendsHours);
        tv_thursHour = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_thusHours);
        tv_friHours = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_fridHours);
        tv_satHours = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_satHours);
        tv_sunHOurs = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_sunHours);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                mToolbar.setVisibility(View.VISIBLE);
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {

                mToolbar.setVisibility(View.INVISIBLE);
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void loadBusinessDetails(final int bId) {

        mProgressBar.setVisibility(View.VISIBLE);

        final StringRequest businessRequest = new StringRequest(Request.Method.POST, Config.business_details_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "business details response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = null;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int businessId = object.getInt("businessid");
                                    String businessName = object.getString("businessname");
                                    String businesstYPE = object.getString("businessType");
                                    String emailId = object.getString("EmailId");
                                    String address = object.getString("address");
                                    String mobileNumber = object.getString("phone1");
                                    String officeNumber = object.getString("phone2");
                                    String website = object.getString("website");
                                    String services = object.getString("services");
                                    String aboutUs = object.getString("aboutus");
                                    String longitude = object.getString("longitude");
                                    String latitude = object.getString("latitude");
                                    String personName = object.getString("contactpersonname");
                                    String imageOne = object.getString("Image1");
                                    String imageTwo = object.getString("Image2");
                                    String imageThree = object.getString("Image3");
                                    String imageFour = object.getString("Image4");
                                    String businessStatus = object.getString("businessStatus");
                                    String cityName = object.getString("City");
                                    String stateName = object.getString("State");
                                    String countryName = object.getString("Country");
                                    String monHours = object.getString("monday");
                                    String tuesHours = object.getString("tuesday");
                                    String WendsHours = object.getString("wednesday");
                                    String thusHours = object.getString("thursday");
                                    String fridHours = object.getString("friday");
                                    String saturdayHours = object.getString("saturday");
                                    String sunDayHours = object.getString("sunday");

                                    businessModel = new BusinessModel(businessId, businessName, cityName, officeNumber, businesstYPE, website, address, mobileNumber, services, aboutUs, longitude, latitude,
                                            personName, imageOne, imageTwo, imageThree, imageFour, businessStatus, emailId, countryName, stateName, monHours, tuesHours, WendsHours,
                                            thusHours, fridHours, saturdayHours, sunDayHours);

                                }

                                String imgUrl = businessModel.getImageOne();

                                if (imgUrl != null) {

                                    Picasso.get()
                                            .load(imgUrl)
                                            .placeholder(R.drawable.circle_preview)
                                            .into(img_profile);
                                } else {

                                    Picasso.get()
                                            .load(R.drawable.circle_preview)
                                            .placeholder(R.drawable.circle_preview)
                                            .into(img_profile);
                                }


                                mTitle.setText(businessModel.getBusinessName());
                                tv_businessName.setText(businessModel.getBusinessName());
                                tv_cityName.setText(businessModel.getBusinessCity());
                                tv_personName.setText(businessModel.getPersonName());
                                tv_website.setText(businessModel.getBusinessUrl());
                                tv_officeNumber.setText(businessModel.getContactNo());
                                tv_mobileNo.setText(businessModel.getMobileNumber());

                                String address = businessModel.getBusinessAddress() + ", " + businessModel.getBusinessCity() + ", " + businessModel.getState() + ".";

                                tv_address.setText(address);

                                tv_services.setText(businessModel.getServices());
                                tv_aboutUs.setText(businessModel.getAboutUs());

                                tv_monHours.setText(businessModel.getMondayHours());
                                tv_tuesHours.setText(businessModel.getTuesdayHours());
                                tv_wendHOurs.setText(businessModel.getWednsdayHours());
                                tv_thursHour.setText(businessModel.getThusdayHours());
                                tv_friHours.setText(businessModel.getFridayHours());
                                tv_satHours.setText(businessModel.getSaturdayHours());
                                tv_sunHOurs.setText(businessModel.getSundayHours());


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "business details error" + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put(BUSINESS_ID, String.valueOf(bId));
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(businessRequest);
    }

    private void makeCall() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BusinessDetailsActivity.this);

        alertDialog.setTitle("Call!");

        alertDialog.setMessage("Do you want to Call?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + businessModel.getContactNo()));
                if (ActivityCompat.checkSelfPermission(BusinessDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();

    }

    private void sendEmail() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BusinessDetailsActivity.this);

        alertDialog.setTitle("Email!");

        alertDialog.setMessage("Do you want to Send Email?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{businessModel.getEmailId()});
                emailIntent.setType("message/rfc822");

                startActivity(emailIntent);

            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }
}
