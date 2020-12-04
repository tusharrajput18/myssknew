package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
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
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.GotraItemAdapter;
import in.co.vsys.myssksamaj.contracts.GotraContract;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.model.DrawerItem;
import in.co.vsys.myssksamaj.model.data_models.GotraModel;
import in.co.vsys.myssksamaj.presenters.GotraPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAstroInfoActivity extends AppCompatActivity implements GotraContract.GotraView {

    private AppCompatEditText edt_birthPlace, edt_gotra;
    private AppCompatTextView tv_birthTime,tv_select_gotra;
    private AppCompatSpinner spinner_rashi, spinner_nakshtra, spinner_charan, spinner_naadi, spinner_gan, spinner_gotra;
    private AppCompatRadioButton radio_manglik_yes, radio_manglik_no;
    private ImageView img_kundliPhoto;
    private AppCompatButton btn_update;
    private ProgressBar mProgressBar;
    private Bitmap bitmap;
    private String imageOne = "";
    private static final String TAG = EditAstroInfoActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    private static final String BIRTH_TIME = "BirthTime";
    private static final String BIRTH_COUNTRY = "BirthCountry";
    private static final String BIRTH_STATE = "BirthState";
    private static final String GOTRA = "Gotra";
    private static final String RASHI = "Rashi";
    private static final String NAKSHTRA = "Nakshtra";
    private static final String CHARAN = "Charan";
    private static final String NAADI = "Naadi";
    private static final String GAN = "Gan";
    private static final String BIRTH_PLACE = "BirthPlace";
    private static final String KUNDLI_PHOTO = "KundaliPhoto";
    private static final String KUNDLI_PHOTO_EXTENSION = "KundaliPhotoExtension";
    private static final String MANGLIK = "isManglik";
    private static final String ASTRO_PROFILE_PERCENTAGE = "HoroScopeInformationPercentage";

    private SharedPreferences mPreferences;
    private String rashi, nakshtra, charan, naadi, gan, birthTime, birthPlace, gotra, kundliPhotoUrl, manglik;
    private String physicalchallenged,income_id,occupation_id;
    private String format = "";
    private String gotraId;
    private static final int PERMS_REQUEST_CODE = 124;
    private int PICK_IMAGE_REQUEST_PROFILE_PICK = 1;
    private List<GotraModel> gotraList;
    private Uri mCropImageUri;

    private GotraContract.GotraOps gotraPresenter;

    RadioGroup radiogroup_manglik;
    private int mMemberId, mUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_edit_astro);
        Log.d(TAG, "onCreate: EditAstroInfoActivity");
        tv_select_gotra=(AppCompatTextView)findViewById(R.id.text_select_gotra);
        tv_birthTime = (AppCompatTextView) findViewById(R.id.tv_edit_astroBirthTime);
        edt_birthPlace = (AppCompatEditText) findViewById(R.id.edt_edit_astroBirthPlace);
        spinner_rashi = (AppCompatSpinner) findViewById(R.id.spinner_eAstro_rashi);
        spinner_nakshtra = (AppCompatSpinner) findViewById(R.id.spinner_eAstro_nakshatra);
        spinner_gotra = (AppCompatSpinner) findViewById(R.id.spinner_eAstro_gotra);
        spinner_charan = (AppCompatSpinner) findViewById(R.id.spinner_eAstro_charan);
        spinner_naadi = (AppCompatSpinner) findViewById(R.id.spinner_eAstro_naadi);
        spinner_gan = (AppCompatSpinner) findViewById(R.id.spinner_eAstro_gan);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_astro_edit);
        img_kundliPhoto = (ImageView) findViewById(R.id.img_eAstro_camera);
        btn_update = (AppCompatButton) findViewById(R.id.btn_eAstro_update);
        radio_manglik_yes = (AppCompatRadioButton) findViewById(R.id.astro_radio_ManglikYes);
        radio_manglik_no = (AppCompatRadioButton) findViewById(R.id.astro_radio_ManglikNo);
        radiogroup_manglik=(RadioGroup)findViewById(R.id.radiogroup_manglik);



        mMemberId = SharedPrefsHelper.getInstance(this).getIntVal(SharedPrefsHelper.MEMBER_ID);
        Log.d(TAG, "onCreate: "+mMemberId);
        /*mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        memberId = mPreferences.getInt("memberId", 0);*/

        getIntentAstro();

        init();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_editAstroInfo);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Horoscope Details" );
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

        }

        gotraList = new ArrayList<>();

       /* img_kundliPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasPermission()) {

                    if (bitmap != null) {

                        bitmap.recycle();
                        bitmap = null;
                        onSelectImageClick(view);
                    } else {

                        onSelectImageClick(view);
                    }
                } else {

                    requestPermission();
                }
            }
        });*/

        tv_birthTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertTimePicker();
            }
        });

        gotraPresenter = new GotraPresenter(this);
        gotraPresenter.getAllGotra();
        tv_select_gotra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotraPresenter.getAllGotra();
            }
        });
        spinner_rashi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));

                if (i == 0) {

                    spinner_rashi.setSelection(getIndex(spinner_rashi, rashi));
                }
                rashi = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_nakshtra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));

                if (i == 0) {

                    spinner_nakshtra.setSelection(getIndex(spinner_nakshtra, nakshtra));
                }
                nakshtra = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_charan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));
                if (i == 0) {

                    spinner_charan.setSelection(getIndex(spinner_charan, charan));
                }

                charan = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_naadi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));
                if (i == 0) {

                    spinner_naadi.setSelection(getIndex(spinner_naadi, naadi));
                }
                naadi = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_gan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));
                if (i == 0) {

                    spinner_gan.setSelection(getIndex(spinner_gan, gan));
                }
                gan = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_gotra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {

                        spinner_gotra.setSelection(getIndex(spinner_gotra, gotra));
                    }
                    gotraId=gotraList.get(position).getGotraid();
                 /*  DrawerItem item = (DrawerItem) parent.getItemAtPosition(position);

                  gotraId = item.getMotherTongueId();*/
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                birthPlace = edt_birthPlace.getText().toString().trim();

                if (radio_manglik_yes.isChecked()) {

                    manglik = "Yes";

                } else if (radio_manglik_no.isChecked()) {

                    manglik = "No";
                }

                if(validateLogin()){
                    updateAstroTask();
                }else {
                    Toast.makeText(EditAstroInfoActivity.this, "Invalid details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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

                img_kundliPhoto.setImageURI(result.getUri());
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();

            }
        }

    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(EditAstroInfoActivity.this, EditMyProfileActivity.class));
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
    //            startActivity(new Intent(EditAstroInfoActivity.this, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void init() {

        ArrayAdapter<String> rashiAdapter = new ArrayAdapter<>(EditAstroInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_rashi));
        spinner_rashi.setAdapter(rashiAdapter);
        ArrayAdapter<String> nakshtraAdpater = new ArrayAdapter<>(EditAstroInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_nakshtra));
        spinner_nakshtra.setAdapter(nakshtraAdpater);
        ArrayAdapter<String> charanAdapter = new ArrayAdapter<>(EditAstroInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_charan));
        spinner_charan.setAdapter(charanAdapter);
        ArrayAdapter<String> naadiAdapter = new ArrayAdapter<>(EditAstroInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_nadi));
        spinner_naadi.setAdapter(naadiAdapter);
        ArrayAdapter<String> ganAdapter = new ArrayAdapter<>(EditAstroInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_gan));
        spinner_gan.setAdapter(ganAdapter);
    }


    private Boolean validateLogin() {
        Boolean flag = true;
        if(tv_birthTime.getText().toString().isEmpty()){
            flag=false;
            Toast.makeText(this, "Select Birth Time", Toast.LENGTH_SHORT).show();

        }
        if(edt_birthPlace.getText().toString().isEmpty()){
            flag=false;
            Toast.makeText(this, "Enter Birth Place", Toast.LENGTH_SHORT).show();

        }
        if(spinner_rashi.getSelectedItem().toString().trim().equals("--Select Rashi--")){
            flag=false;
            Toast.makeText(this, "Select Rashi", Toast.LENGTH_SHORT).show();
        }
        if(spinner_nakshtra.getSelectedItem().toString().trim().equals("--Select Nakshatra--")){
            flag=false;
            Toast.makeText(this, "Select Nakshatra", Toast.LENGTH_SHORT).show();
        }
        if(spinner_charan.getSelectedItem().toString().trim().equals("--Select Charan--")){
            flag=false;
            Toast.makeText(this, "Select Charan", Toast.LENGTH_SHORT).show();
        }
        if(spinner_naadi.getSelectedItem().toString().trim().equals("--Select Nadi--")){
            flag=false;
            Toast.makeText(this, "Select Nadi", Toast.LENGTH_SHORT).show();
        }
        if(spinner_gan.getSelectedItem().toString().trim().equals("--Select Gan--")){
            flag=false;
            Toast.makeText(this, "Select Gan", Toast.LENGTH_SHORT).show();
        }
        if(radiogroup_manglik.getCheckedRadioButtonId()==-1){
            flag=false;
            Toast.makeText(this, "Select if Manglik or Not", Toast.LENGTH_SHORT).show();
        }
       /* if(bitmap==null){
            flag=false;
            Toast.makeText(this, "Upload Kundil", Toast.LENGTH_SHORT).show();
        }*/

        return flag;
    }

    private void updateAstroTask() {

        Log.d(TAG, "updateAstroTask: "+mMemberId);

      //  mPreferences.edit().putString(SharedPrefsHelper.BIRTHPLACE, edt_birthPlace.getText().toString()).apply();


        if (bitmap != null) {

            imageOne = getStringImage(bitmap);
        }

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.member_astro_edit_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "edit astro response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success" );

                            if (success == 1) {

                                Toast.makeText(EditAstroInfoActivity.this, "Astro details updated successfully...", Toast.LENGTH_SHORT).show();
                                finish();
                                //startActivity(new Intent(EditAstroInfoActivity.this, HomeActivity.class));
                            } else {

                                Toast.makeText(EditAstroInfoActivity.this, "Details not updated...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "astro edit error " + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> astrParam = new HashMap<>();
                astrParam.put(MEMBER_ID, String.valueOf(mMemberId));
                astrParam.put(BIRTH_TIME, birthTime);
                astrParam.put(BIRTH_COUNTRY, "0" );
                astrParam.put(BIRTH_STATE, "0" );
                astrParam.put(GOTRA, gotraId);
                astrParam.put(RASHI, rashi);
                astrParam.put(NAKSHTRA, nakshtra);
                astrParam.put(CHARAN, charan);
                astrParam.put(NAADI, naadi);
                astrParam.put(GAN, gan);
                astrParam.put(BIRTH_PLACE, birthPlace);
                astrParam.put(KUNDLI_PHOTO, imageOne);
                astrParam.put(KUNDLI_PHOTO_EXTENSION, ".jpg" );
                astrParam.put(ASTRO_PROFILE_PERCENTAGE, "10" );
                astrParam.put(MANGLIK, manglik);
                astrParam.put("occupation", occupation_id);
                astrParam.put("income", income_id);
                astrParam.put("physicalchallenge", physicalchallenged);

                return astrParam;
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

            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startCropImageActivity(mCropImageUri);
            }

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if ((shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))) {

                    Toast.makeText(this, "Phone state permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void alertTimePicker() {

        int mHour, mMinute;

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                showTime(hourOfDay, minute);
            }
        }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        birthTime = new StringBuilder().append(hour).append(" : " ).append(min).append(" " ).append(format).toString();

        tv_birthTime.setText(getString(R.string.birth_time_d) + birthTime);
        /*tv_birthTime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));*/
    }

    private void getIntentAstro() {

        Bundle astroIntent = getIntent().getExtras();
        if (astroIntent != null) {

            birthTime = astroIntent.getString("edit_birthTime" );
            birthPlace = astroIntent.getString("edit_birthPlace" );
            gotra = astroIntent.getString("edit_gotra" );
            Log.e("gotra",gotra);
            rashi = astroIntent.getString("edit_rashi" );
            nakshtra = astroIntent.getString("edit_nakshtra" );
            charan = astroIntent.getString("edit_charan" );
            naadi = astroIntent.getString("edit_naadi" );
            gan = astroIntent.getString("edit_gan" );
            manglik = astroIntent.getString("edit_manglik" );
            kundliPhotoUrl = astroIntent.getString("edit_kundliPhoto" );

            physicalchallenged=astroIntent.getString("edit_physicalchallenge");
            income_id=astroIntent.getString("edit_income_id");
            occupation_id=astroIntent.getString("edit_occupation_id");


            if (manglik.equals("Yes" )) {

                radio_manglik_yes.setChecked(true);
            }

            if (manglik.equals("No" )) {

                radio_manglik_no.setChecked(true);
            }

            if (!birthTime.equals("" )) {

                tv_birthTime.setText(birthTime);
            }

            if (!birthPlace.equals("" )) {

                edt_birthPlace.setText(birthPlace);
            } else {

                edt_birthPlace.setHint("Birth Place" );
            }

            if (kundliPhotoUrl.equals("" )) {

                Picasso.get()
                        .load(R.drawable.kundli_preview)
                        .placeholder(R.drawable.kundli_preview)
                        .into(img_kundliPhoto);
            } else {

                Picasso.get()
                        .load(kundliPhotoUrl)
                        .placeholder(R.drawable.kundli_preview)
                        .into(img_kundliPhoto);
            }

        }
    }

    private int getIndex(AppCompatSpinner spinner, String string) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {

            String checkString = spinner.getItemAtPosition(i).toString();

            if (checkString != null && checkString.equalsIgnoreCase(string)) {

                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public void showAllGotra(List<GotraModel> gotraModels) {
        try {
            gotraList.clear();
            gotraList.addAll(gotraModels);

            Log.d(TAG, "showAllGotra: 111111111111"+gotraList.size());
            if(gotraList.size()==0){
                tv_select_gotra.setVisibility(View.VISIBLE);
                spinner_gotra.setVisibility(View.GONE);
            }else {
                tv_select_gotra.setVisibility(View.GONE);
                spinner_gotra.setVisibility(View.VISIBLE);
                GotraItemAdapter adapter = new GotraItemAdapter(EditAstroInfoActivity.this, gotraList);
                Log.d(TAG, "showAllGotra: "+gotraList.size());
                spinner_gotra.setAdapter(adapter);
                spinner_gotra.setSelection(getGotraSpinnerPosition(gotra));
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getGotraSpinnerPosition(String gotra) {
        int id=0;
        for(int i=0;i<gotraList.size();i++){
            if(gotraList.get(i).getGotraname().equals(gotra)){
                id=i;
                break;
            }
        }
        return id;
    }

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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

