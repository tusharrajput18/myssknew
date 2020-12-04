package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CountryAdapter;
import in.co.vsys.myssksamaj.adapter.TempAdapter;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class BusinessInsertOtherActivity extends AppCompatActivity {

    private Context mContext;
    private AppCompatButton btn_register;
    private static final int SERVICE_REQUEST_CODE = 101;
    private AppCompatTextView btn_selectLocation;
    private AppCompatSpinner spinner_country, spinner_state, spinner_city;
    private AppCompatSpinner spinner_monToHours, spinner_monFromHours, spinner_tuesToHours, spinner_tuesFromHours, spinner_wedToHours, spinner_wedFromHours, spinner_thusToHours, spinner_thusFromHours,
            spinner_friToHours, spinner_friFromHours, spinner_satToHours, spinner_satFromHours, spinner_sunToHours, spinner_sunFromHours;
    private AppCompatImageView img_one, img_two, img_three, img_four;
    private SharedPreferences mSharedPreferences;
    private int appLoginId, business_typeId;
    private String businessName, business_address, business_mobile, business_office_no, business_email, business_website,
            business_services, business_about_us, business_contact_person, logitude, latitude;
    private ProgressBar mProgressBar;
    private static final String TAG = BusinessInsertOtherActivity.class.getSimpleName();
    private static final String APPLOGIN_ID = "AppLoginId";
    private static final String BUSINESS_TYPE_ID = "BusinessTypeId";
    private static final String BUSINESS_NAME = "BusinessName";
    private static final String BUSINESS_ADDRESS = "Address";
    private static final String MOBILE_NUMBER = "Phone1";
    private static final String OFFICE_NUMBER = "Phone2";
    private static final String BUSINESS_WEBSITE = "Webiste";
    private static final String BUSINESS_SERVICES = "Services";
    private static final String ABOUT_US = "Aboutus";
    private static final String LONGITUDE = "Longitude";
    private static final String LATITUDE = "Latitude";
    private static final String CONTACT_PERSON = "ContactPersonName";
    private static final String IMAGE_ONE = "Image1";
    private static final String IMAGE_ONE_EXT = "Image1Extension";
    private static final String IMAGE_TWO = "Image2";
    private static final String IMAGE_TWO_EXT = "Image2Extension";
    private static final String IMAGE_THREE = "Image3";
    private static final String IMAGE_THREE_EXT = "Image3Extension";
    private static final String IMAGE_FOUR = "Image4";
    private static final String IMAGE_FOUR_EXT = "Image4Extension";
    private static final String EMAIL_ID = "EmailId";
    private static final String COUNTRY = "Country";
    private static final String STATE = "State";
    private static final String CITY = "City";
    private static final String MONDAY = "Monday";
    private static final String TUESDAY = "Tuesday";
    private static final String WEDNESDAY = "Wednesday";
    private static final String THURSDAY = "Thursday";
    private static final String FRIDAY = "Friday";
    private static final String SATURDAY = "Saturday";
    private static final String SUNDAY = "Sunday";
    private static final String STATE_ID = "StateId";

    private List<CountryModel> countryList;
    private List<CountryModel> stateList;
    private List<CountryModel> cityList;
    private CountryAdapter countryAdapter;
    private Bitmap oneBitmap, twoBitmpa, threeBitmap, fourBitmap;
    private int stateId, cityId, countryId;
    private String oneImage = "";
    private String twoImage = "";
    private String threeImage = "";
    private String fourImage = "";
    private int flag = 0;
    private Uri mCropImageUri;
    private String[] working_hours_to;
    private String[] working_hours_from;
    private String monToHOurs, monFromHours, tuesToHOurs, tuesFromHours, wednesToHOurs, wednesFromHours,
            thusToHOurs, thusFromHours, friToHOurs, friFromHours, satToHOurs, satFromHours,
            sunToHOurs, sunFromHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_insert_other);
        mContext = this;

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_insertOtherBusiness);
        spinner_state = (AppCompatSpinner) findViewById(R.id.spinner_business_state);
        spinner_country = (AppCompatSpinner) findViewById(R.id.spinner_business_country);
        spinner_city = (AppCompatSpinner) findViewById(R.id.spinner_business_city);
        btn_register = (AppCompatButton) findViewById(R.id.btn_insert_Submit);
        btn_selectLocation = (AppCompatTextView) findViewById(R.id.btn_busi_selectLocation);
        img_one = (AppCompatImageView) findViewById(R.id.img_busiInsert_one);
        img_two = (AppCompatImageView) findViewById(R.id.img_busiInsert_two);
        img_three = (AppCompatImageView) findViewById(R.id.img_busiInsert_three);
        img_four = (AppCompatImageView) findViewById(R.id.img_busiInsert_four);
        spinner_monToHours = (AppCompatSpinner) findViewById(R.id.spinner_monday_to_hours);
        spinner_monFromHours = (AppCompatSpinner) findViewById(R.id.spinner_monday_from_hours);
        spinner_tuesToHours = (AppCompatSpinner) findViewById(R.id.spinner_tuesday_to_hours);
        spinner_tuesFromHours = (AppCompatSpinner) findViewById(R.id.spinner_tuesday_from_hours);
        spinner_wedToHours = (AppCompatSpinner) findViewById(R.id.spinner_wednesday_to_hours);
        spinner_wedFromHours = (AppCompatSpinner) findViewById(R.id.spinner_wednesday_from_hours);
        spinner_thusToHours = (AppCompatSpinner) findViewById(R.id.spinner_thursday_to_hours);
        spinner_thusFromHours = (AppCompatSpinner) findViewById(R.id.spinner_thursday_from_hours);
        spinner_friToHours = (AppCompatSpinner) findViewById(R.id.spinner_friday_to_hours);
        spinner_friFromHours = (AppCompatSpinner) findViewById(R.id.spinner_friday_from_hours);
        spinner_satToHours = (AppCompatSpinner) findViewById(R.id.spinner_saturday_to_hours);
        spinner_satFromHours = (AppCompatSpinner) findViewById(R.id.spinner_saturday_from_hours);
        spinner_sunToHours = (AppCompatSpinner) findViewById(R.id.spinner_sunday_to_hours);
        spinner_sunFromHours = (AppCompatSpinner) findViewById(R.id.spinner_sunday_from_hours);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        appLoginId = mSharedPreferences.getInt("appLoginId", 0);


        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();

        loadCountryTask();
        loadState();

        working_hours_to = getResources().getStringArray(R.array.working_hours_to);
        working_hours_from = getResources().getStringArray(R.array.working_hours_from);

        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);
                countryId = countryModel.getCountryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);
                stateId = countryModel.getCountryId();

                cityList.clear();
                loadCity(stateId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);
                cityId = countryModel.getCountryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectionHelper.networkConnectivity(mContext)) {

                    insertBusinessTask();

                } else {

                    Toast.makeText(BusinessInsertOtherActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;

                try {
                    intent = builder.build(BusinessInsertOtherActivity.this);

                    startActivityForResult(intent, SERVICE_REQUEST_CODE);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 1;

                if (oneBitmap != null) {
                    oneBitmap.recycle();
                    oneBitmap = null;
                    onSelectImageClick(v);
                } else {

                    onSelectImageClick(v);
                }
            }
        });

        img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 2;

                if (twoBitmpa != null) {
                    twoBitmpa.recycle();
                    twoBitmpa = null;
                    onSelectImageClick(v);
                } else {

                    onSelectImageClick(v);
                }
            }
        });

        img_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 3;

                if (threeBitmap != null) {
                    threeBitmap.recycle();
                    threeBitmap = null;
                    onSelectImageClick(v);
                } else {

                    onSelectImageClick(v);
                }
            }
        });

        img_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 4;

                if (fourBitmap != null) {
                    fourBitmap.recycle();
                    fourBitmap = null;
                    onSelectImageClick(v);
                } else {

                    onSelectImageClick(v);
                }
            }
        });

        ArrayAdapter<String> monToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_monToHours.setAdapter(monToAdapter);

        spinner_monToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                monToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> monFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_monFromHours.setAdapter(monFromAdapter);

        spinner_monFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                monFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> tuesToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_tuesToHours.setAdapter(tuesToAdapter);

        spinner_tuesToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tuesToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> tuesFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_tuesFromHours.setAdapter(tuesFromAdapter);

        spinner_tuesFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tuesFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> wedToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_wedToHours.setAdapter(wedToAdapter);

        spinner_wedToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                wednesToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> wedFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_wedFromHours.setAdapter(wedFromAdapter);

        spinner_wedFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                wednesFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> thusToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_thusToHours.setAdapter(thusToAdapter);

        spinner_thusToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                thusToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> thusFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_thusFromHours.setAdapter(thusFromAdapter);

        spinner_thusFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                thusFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> friToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_friToHours.setAdapter(friToAdapter);

        spinner_friToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                friToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> friFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_friFromHours.setAdapter(friFromAdapter);

        spinner_friFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                friFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> satToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_satToHours.setAdapter(satToAdapter);

        spinner_satToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                satToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> satFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_satFromHours.setAdapter(satFromAdapter);

        spinner_satFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                satFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> sunToAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_to);
        spinner_sunToHours.setAdapter(sunToAdapter);

        spinner_sunToHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sunToHOurs = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> sunFromAdapter = new ArrayAdapter<String>(BusinessInsertOtherActivity.this, android.R.layout.simple_spinner_dropdown_item, working_hours_from);
        spinner_sunFromHours.setAdapter(sunFromAdapter);

        spinner_sunFromHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sunFromHours = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void insertBusinessTask() {

        getSharePrefData();

        final String monToFromAm = monToHOurs + " - " + monFromHours;
        Log.d(TAG, "montofrom am" + monToFromAm);
        final String tuesToFromAm = tuesToHOurs + " - " + tuesFromHours;
        final String wedToFromAm = wednesToHOurs + " - " + wednesFromHours;
        final String thusToFromAm = thusToHOurs + " - " + thusFromHours;
        final String friToFromAm = friToHOurs + " - " + friFromHours;
        final String satToFromAm = satToHOurs + " - " + satFromHours;
        final String sunToFromAm = sunToHOurs + " - " + sunFromHours;

        if (oneBitmap != null) {

            oneImage = getStringImage(oneBitmap);
        }

        if (twoBitmpa != null) {

            twoImage = getStringImage(twoBitmpa);
        }

        if (threeBitmap != null) {

            threeImage = getStringImage(threeBitmap);
        }

        if (fourBitmap != null) {

            fourImage = getStringImage(fourBitmap);
        }

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.insert_business_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "insert business response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                Toast.makeText(BusinessInsertOtherActivity.this, "Registration successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BusinessInsertOtherActivity.this, BusinessDashboardActivity.class));
                            } else {

                                Toast.makeText(BusinessInsertOtherActivity.this, "something went wrong...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "insert business error" + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put(APPLOGIN_ID, String.valueOf(appLoginId));
                param.put(BUSINESS_TYPE_ID, String.valueOf(business_typeId));
                param.put(BUSINESS_NAME, businessName);
                param.put(BUSINESS_ADDRESS, business_address);
                param.put(MOBILE_NUMBER, business_mobile);
                param.put(OFFICE_NUMBER, business_office_no);
                param.put(BUSINESS_WEBSITE, business_website);
                param.put(BUSINESS_SERVICES, business_services);
                param.put(ABOUT_US, business_about_us);
                param.put(LONGITUDE, logitude);
                param.put(LATITUDE, latitude);
                param.put(CONTACT_PERSON, business_contact_person);
                param.put(IMAGE_ONE, oneImage);
                param.put(IMAGE_ONE_EXT, ".jpg");
                param.put(IMAGE_TWO, twoImage);
                param.put(IMAGE_TWO_EXT, ".jpg");
                param.put(IMAGE_THREE, threeImage);
                param.put(IMAGE_THREE_EXT, ".jpg");
                param.put(IMAGE_FOUR, fourImage);
                param.put(IMAGE_FOUR_EXT, ".jpg");
                param.put(EMAIL_ID, business_email);
                param.put(COUNTRY, String.valueOf(countryId));
                param.put(STATE, String.valueOf(stateId));
                param.put(CITY, String.valueOf(cityId));
                param.put(MONDAY, monToFromAm);
                param.put(TUESDAY, tuesToFromAm);
                param.put(WEDNESDAY, wedToFromAm);
                param.put(THURSDAY, thusToFromAm);
                param.put(FRIDAY, friToFromAm);
                param.put(SATURDAY, satToFromAm);
                param.put(SUNDAY, sunToFromAm);

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getSharePrefData() {

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        business_typeId = mPreferences.getInt("b_insert_bType", 0);
        businessName = mPreferences.getString("b_insert_bName", null);
        business_address = mPreferences.getString("b_insert_bAddress", null);
        business_mobile = mPreferences.getString("b_insert_bMobile", null);
        business_office_no = mPreferences.getString("b_insert_bOffNo", null);
        business_email = mPreferences.getString("b_insert_bEmail", null);
        business_website = mPreferences.getString("b_insert_bWebsite", null);
        business_services = mPreferences.getString("b_insert_bServices", null);
        business_about_us = mPreferences.getString("b_insert_bAboutUs", null);
        business_contact_person = mPreferences.getString("b_insert_bContPerson", null);
    }

    private void hideKeyboard(View v) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    private void loadCountryTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.countryListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "countryList response :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int countryId = object.getInt("countrycode");
                                    String countryName = object.getString("countryname");

                                    CountryModel countryModel = new CountryModel(countryId, countryName);
                                    countryList.add(countryModel);
                                }

                                countryAdapter = new CountryAdapter(BusinessInsertOtherActivity.this, countryList);
                                spinner_country.setAdapter(countryAdapter);
                                countryAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(BusinessInsertOtherActivity.this, "Country not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "countryList Error :" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadState() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.stateListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "state response" + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray;

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int countryId = object.getInt("id");
                                    String countryName = object.getString("statename");

                                    CountryModel countryModel = new CountryModel(countryId, countryName);
                                    stateList.add(countryModel);
                                }

                                TempAdapter tempAdapter = new TempAdapter(BusinessInsertOtherActivity.this, stateList);
                                spinner_state.setAdapter(tempAdapter);
                                tempAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(BusinessInsertOtherActivity.this, "state list not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "state list error" + error);
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadCity(final int stateId) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.cityListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "city response" + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray;

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int countryId = object.getInt("id");
                                    String countryName = object.getString("cityname");

                                    CountryModel countryModel = new CountryModel(countryId, countryName);
                                    cityList.add(countryModel);
                                }

                                TempAdapter tempAdapter = new TempAdapter(BusinessInsertOtherActivity.this, cityList);

                                spinner_city.setAdapter(tempAdapter);
                                tempAdapter.notifyDataSetChanged();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "city list error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                param.put(STATE_ID, String.valueOf(stateId));

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(2, 1)
                .start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SERVICE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String address = String.valueOf(place.getLatLng());

                String address1[] = address.split("lat/lng:");

                String address2 = address1[1];

                String address3[] = address2.split(",");

                latitude = address3[0];
                logitude = address3[1];

                latitude = latitude.replace("(", "");
                logitude = logitude.replace(")", "");

                //latitude = lat1.substring(1, lat1.length() - 1);

                btn_selectLocation.setText(latitude + "," + logitude);
            }
        }

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri path = result.getUri();

                if (flag == 1) {

                    img_one.setImageURI(result.getUri());
                    try {

                        oneBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 2) {

                    img_two.setImageURI(result.getUri());
                    try {
                        twoBitmpa = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 3) {

                    img_three.setImageURI(result.getUri());
                    try {
                        threeBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 4) {

                    img_four.setImageURI(result.getUri());
                    try {
                        fourBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startCropImageActivity(mCropImageUri);
        } else {

            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }

    }



   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SERVICE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("Place: %s", place.getLatLng());
                btn_selectLocation.setText(address);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
