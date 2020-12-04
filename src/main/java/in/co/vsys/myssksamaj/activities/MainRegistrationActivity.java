package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
import in.co.vsys.myssksamaj.adapter.CityAdapter;
import in.co.vsys.myssksamaj.adapter.CountryAdapter;
import in.co.vsys.myssksamaj.adapter.StateAdapter;
import in.co.vsys.myssksamaj.model.CityModel;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.StateModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class MainRegistrationActivity extends AppCompatActivity {

    private static final String TAG = MainRegistrationActivity.class.getSimpleName();
    private static final String FIRST_NAME = "FirstName";
    private static final String MIDDLE_NAME = "MiddleName";
    private static final String LAST_NAME = "LastName";
    private static final String MABILE_NUMBER = "Mobile";
    private static final String EMAIL_ID = "Email";
    private static final String PASSWORD = "Password";
    private static final String ID_PROOF_PHOTO = "IdProofPhoto";
    private static final String PHOTO_EXTENSION = "IdProofPhotoExtension";
    private static final String ADDRESS = "Address";
    private static final String COUNTRY = "Country";
    private static final String STATE = "State";
    private static final String CITY = "City";
    private static final String STATE_ID = "StateId";
    private AppCompatTextView tv_selectImage;
    private EditText edt_firstName, edt_middleName, edt_lastName, edt_emailId, edt_mobileNumber, edt_address, edt_password;
    private Spinner spinner_country, spinner_state, spinner_city;
    private Button btn_submit;
    private ImageView img_proof;
    private String firstName, middleName, password, lastName, emailId, mobileNumber, address;
    private int countryId, stateId, cityId;
    private String imageView;
    private Snackbar mSnackbar;
    private RelativeLayout registrationLayout;
    private ProgressBar mProgressBar;
    private int PICK_IMAGE_REQUEST_PROFILE_PICK = 1;
    private Bitmap bitmap;
    private List<CountryModel> countryList;
    private List<StateModel> stateList;
    private List<CityModel> cityList;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private static final int PERMS_REQUEST_CODE = 124;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registration);

        edt_firstName = (EditText) findViewById(R.id.edt_regFirstName);
        edt_middleName = (EditText) findViewById(R.id.edt_regMiddleName);
        edt_lastName = (EditText) findViewById(R.id.edt_regLastName);
        edt_emailId = (EditText) findViewById(R.id.edt_regemailId);
        edt_mobileNumber = (EditText) findViewById(R.id.edt_regMobileNumber);
        edt_password = (EditText) findViewById(R.id.edt_regPassword);
        edt_address = (EditText) findViewById(R.id.edt_regAddress);
        spinner_country = (Spinner) findViewById(R.id.spinner_country);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        btn_submit = (Button) findViewById(R.id.btn_registratioSubmit);
        img_proof = (ImageView) findViewById(R.id.img_uploadIdProof);
        registrationLayout = (RelativeLayout) findViewById(R.id.registrationLayout);
        mProgressBar = (ProgressBar) findViewById(R.id.register_progressBar);
        tv_selectImage = (AppCompatTextView) findViewById(R.id.tv_register_select);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_mainregistration);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Register");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();

        loadCountryTask();
        loadStateTask();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = edt_firstName.getText().toString().trim();
                middleName = edt_middleName.getText().toString().trim();
                lastName = edt_lastName.getText().toString().trim();
                emailId = edt_emailId.getText().toString().trim();
                mobileNumber = edt_mobileNumber.getText().toString().trim();
                address = edt_address.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                String cId = String.valueOf(countryId);
                String sId = String.valueOf(stateId);
                String ciId = String.valueOf(cityId);

                if ((TextUtils.isEmpty(firstName)) || (TextUtils.isEmpty(middleName)) || (TextUtils.isEmpty(lastName)) || (TextUtils.isEmpty(emailId))
                        || (TextUtils.isEmpty(mobileNumber)) || (TextUtils.isEmpty(cId))
                        || (TextUtils.isEmpty(sId)) || (TextUtils.isEmpty(ciId)) || (TextUtils.isEmpty(password)) || (TextUtils.isEmpty(address))) {

                    mSnackbar = Snackbar.make(
                            registrationLayout, getResources().getString(R.string.empty_editText), Snackbar.LENGTH_LONG);
                    mSnackbar.show();
                } else {

                    registrationTask();

                }
            }
        });

        tv_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasPermission()) {

                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                        showFileChooser(PICK_IMAGE_REQUEST_PROFILE_PICK);
                    } else {

                        showFileChooser(PICK_IMAGE_REQUEST_PROFILE_PICK);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        /*img_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/

        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                CountryModel countryModel = (CountryModel) adapterView.getItemAtPosition(i);
                countryId = countryModel.getCountryId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                StateModel stateModel = (StateModel) adapterView.getItemAtPosition(i);
                stateId = stateModel.getStateId();

                System.out.println("stateId" + stateId);

                cityList.clear();
                loadCityTask(stateId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                CityModel cityModel = (CityModel) adapterView.getItemAtPosition(i);
                cityId = cityModel.getCityId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(MainRegistrationActivity.this, MainLoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(MainRegistrationActivity.this, MainLoginActivity.class));
    }

    private void registrationTask() {

        if (bitmap != null) {

            imageView = getStringImage(bitmap);
        }

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.mainRegistrationUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "regResponse :" + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                startActivity(new Intent(MainRegistrationActivity.this, MainLoginActivity.class));
                                Toast.makeText(MainRegistrationActivity.this, "Registration successfully...!", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(MainRegistrationActivity.this, "Mobile number already registered...!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "regResponse error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(FIRST_NAME, firstName);
                params.put(MIDDLE_NAME, middleName);
                params.put(LAST_NAME, lastName);
                params.put(MABILE_NUMBER, mobileNumber);
                params.put(EMAIL_ID, emailId);
                params.put(PASSWORD, password);
                params.put(ID_PROOF_PHOTO, imageView);
                params.put(PHOTO_EXTENSION, ".jpg");
                params.put(ADDRESS, address);
                params.put(COUNTRY, String.valueOf(countryId));
                params.put(STATE, String.valueOf(stateId));
                params.put(CITY, String.valueOf(cityId));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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

                                countryAdapter = new CountryAdapter(MainRegistrationActivity.this, countryList);
                                spinner_country.setAdapter(countryAdapter);
                                countryAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(MainRegistrationActivity.this, "Country not available", Toast.LENGTH_SHORT).show();
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

    private void loadStateTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.stateListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "StateList response :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int stateId = object.getInt("id");
                                    String stateName = object.getString("statename");

                                    StateModel stateModel = new StateModel(stateId, stateName);
                                    stateList.add(stateModel);
                                }

                                stateAdapter = new StateAdapter(MainRegistrationActivity.this, stateList);
                                spinner_state.setAdapter(stateAdapter);
                                stateAdapter.notifyDataSetChanged();


                            } else {

                                Toast.makeText(MainRegistrationActivity.this, "State not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "state Error :" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadCityTask(final int temp) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.cityListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "CityList response :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int cityId = object.getInt("id");
                                    String cityName = object.getString("cityname");

                                    CityModel cityModel = new CityModel(cityId, cityName);
                                    cityList.add(cityModel);
                                }

                                cityAdapter = new CityAdapter(MainRegistrationActivity.this, cityList);
                                spinner_city.setAdapter(cityAdapter);
                                cityAdapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "city Error :" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(STATE_ID, String.valueOf(temp));

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void showFileChooser(int requestCode) {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, requestCode);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri filePath = data.getData();

            if (requestCode == PICK_IMAGE_REQUEST_PROFILE_PICK) {

                try {
                    //Getting the Bitmap from Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    //Setting the Bitmap to ImageView
                    img_proof.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    private boolean hasPermission() {

        int res = 0;

        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perems : permissions) {

            res = checkCallingOrSelfPermission(perems);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {

                return false;
            }
        }

        return true;
    }

    private void requestPermission() {

        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean allowed = true;

        switch (requestCode) {

            case PERMS_REQUEST_CODE:

                for (int res : grantResults) {

                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;

            default:

                allowed = false;
                break;
        }

        if (allowed) {

            showFileChooser(PICK_IMAGE_REQUEST_PROFILE_PICK);

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if ((shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))) {

                    Toast.makeText(this, "Phone state permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
