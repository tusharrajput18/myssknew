package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CityAdapter;
import in.co.vsys.myssksamaj.adapter.CountryAdapter;
import in.co.vsys.myssksamaj.adapter.CustomDesBasicAdapter;
import in.co.vsys.myssksamaj.adapter.HeightAdapter;
import in.co.vsys.myssksamaj.adapter.MatrimonyCasteAdapter;
import in.co.vsys.myssksamaj.adapter.MatrimonySubcasteAdapter;
import in.co.vsys.myssksamaj.adapter.MotherTongueAdapter;
import in.co.vsys.myssksamaj.adapter.StateAdapter;
import in.co.vsys.myssksamaj.contracts.CasteContract;
import in.co.vsys.myssksamaj.contracts.CityContract;
import in.co.vsys.myssksamaj.contracts.CountryContract;
import in.co.vsys.myssksamaj.contracts.EditUserContract;
import in.co.vsys.myssksamaj.contracts.MotherTongueContract;
import in.co.vsys.myssksamaj.contracts.StateContract;
import in.co.vsys.myssksamaj.contracts.SubcasteContract;
import in.co.vsys.myssksamaj.helpers.ImageHelper;
import in.co.vsys.myssksamaj.matrimonyutils.Constants;
import in.co.vsys.myssksamaj.model.CasteModel;
import in.co.vsys.myssksamaj.model.CityModel;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.DrawerItem;
import in.co.vsys.myssksamaj.model.StateModel;
import in.co.vsys.myssksamaj.model.data_models.MotherTongueModel;
import in.co.vsys.myssksamaj.model.data_models.SubcasteModel;
import in.co.vsys.myssksamaj.presenters.CastePresenter;
import in.co.vsys.myssksamaj.presenters.CityPresenter;
import in.co.vsys.myssksamaj.presenters.CountryPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.MotherTonguePresenter;
import in.co.vsys.myssksamaj.presenters.StatePresenter;
import in.co.vsys.myssksamaj.presenters.SubcastePresenter;
import in.co.vsys.myssksamaj.presenters.UpdateUserPresenter;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditBasicInfoActivity extends AppCompatActivity implements EditUserContract.UpdateUserView,
        SubcasteContract.SubcasteView, CasteContract.CasteView, MotherTongueContract.MotherTongueView,
        CountryContract.CountryView, StateContract.StateView, CityContract.CityView {

    private Context mContext;
    private String abc="";
    private List<CountryModel> countryList;
    private List<DrawerItem> heightList, marriedList;
    private List<StateModel> stateList;
    private List<CityModel> cityList;
    private List<CasteModel> casteList;
    private MatrimonyCasteAdapter casteAdapter;
    private MatrimonySubcasteAdapter subcasteAdapter;
    private List<SubcasteModel> subCastList;
    private List<MotherTongueModel> motherToungeList;
    private ProgressBar mProgressBar;
    private TextView
//            tv_caste, tv_subCaste,
            mCountrySpinner, mStateSpinner, mCitySpinner,
            mMotherTSpinner, mHeightSpinner, mMarriedSpinner;
    private AppCompatEditText edt_firstName, edt_fatherName, edt_lastName, edt_emailId, edt_introduction, edt_address;
    private AppCompatTextView tv_DOB, edt_mobileNumber;
    private AppCompatButton btn_update;
    private static final String TAG = EditBasicInfoActivity.class.getSimpleName();
    private SharedPreferences mPreferences;
    private int memberId, casteId = 0, subCasteId = 0;
    private String accountType;
    private CountryAdapter countryAdapter;
    private String firstName, fatherName, lastName, emailId, mobileNumber, dob, mTongue, height, marriedStatus,
            gender, country, state, city, caste, subCaste, radioGender;
    private String address = "", countryId, stateId, cityId, mTId;
    private static final String MEMBER_ID = "MemberId";
    private static final String FIRST_NAME = "FirstName";
    private static final String MIDDLE_NAME = "MiddleName";
    private static final String LAST_NAME = "LastName";
    private static final String GENDER = "Gender";
    private static final String DOB = "DOB";
    private static final String MOBILE_NO = "Mobile";
    private static final String OTHER_MOBILE_NO = "OtherMobileNo";
    private static final String EMAIL_ID = "EmailId";
    private static final String MOTHER_TOUNGUE = "MotherTongue";
    private static final String MARRIED_STATUS = "MarriedStatus";
    private static final String HEIGHT = "Height";
    private static final String COUNTRY_ID = "CountryId";
    private static final String STATE_ID = "StateId";
    private static final String CITY = "CityId";
    private static final String DEVICE_ID = "DeviceId";
    private static final String ACCOUNT_MANAGED_BY = "AccountMangeBy";
    private static final String IDENTITY_PHOTO = "IdentityPhoto";
    private static final String IDENTITY_EXTENSION = "IdentityPhotoExtension";
    private static final String COUNTRY_ID_STATE = "CountryID";
    private static final String CASTE_ID = "Caste";
    private static final String SUB_CASTE_ID = "SubCaste";
    private HeightAdapter heightAdapter;
    private CustomDesBasicAdapter customDesBasicAdapter;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private MotherTongueAdapter motherTongueAdapter;
    private TextView tv_dobHeading, tv_motherTHeading, tv_heightHeading, tv_marriedSHeading;
    private ImageView imgIdentity;
    private static final int PERMS_REQUEST_CODE = 124;
    private String identyImage, photoIdPath;
    private AppCompatRadioButton radio_self, radio_parent, radio_sibling;
    private CharSequence accountManagedBy = null;
    private LinearLayout layout_profileManagedBy;
    private String tokenId;

    private RadioButton radio_male, radio_female;

    private EditUserContract.UpdateUserOps updateUserPresenter;
    private CasteContract.CasteOps castePresenter;
    private SubcasteContract.SubcasteOps subcastePresenter;
    private MotherTongueContract.MotherTongueOps motherTonguePresenter;
    private CountryContract.CountryOps countryPresenter;
    private StateContract.StateOps statePresenter;
    private CityContract.CityOps cityPresenter;
    String imgString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_basic_edit);
        mContext = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_editBasicInfo);

        edt_firstName = (AppCompatEditText) findViewById(R.id.edt_eProfile_firstName);
        edt_fatherName = (AppCompatEditText) findViewById(R.id.edt_eProfile_middle_name);
        edt_lastName = (AppCompatEditText) findViewById(R.id.edt_eProfile_LastName);
        edt_address = (AppCompatEditText) findViewById(R.id.edt_address);

        edt_mobileNumber = (AppCompatTextView) findViewById(R.id.edt_eProfile_MNumber);
        edt_introduction = (AppCompatEditText) findViewById(R.id.edt_about_partner);
//        tv_caste = (TextView) findViewById(R.id.spinner_eProfile_caste);
//        tv_subCaste = (TextView) findViewById(R.id.spinner_eProfile_subCaste);
        tv_DOB = (AppCompatTextView) findViewById(R.id.tv_eProfile_DOB);
        edt_emailId = (AppCompatEditText) findViewById(R.id.edt_eProfile_EID);
        mCitySpinner = (TextView) findViewById(R.id.spinner_eProfile_city);
        mStateSpinner = (TextView) findViewById(R.id.spinner_eProfile_state);
        mCountrySpinner = (TextView) findViewById(R.id.spinner_eProfile_country);
        mMotherTSpinner = (TextView) findViewById(R.id.spinner_eProfile_language);
        mHeightSpinner = (TextView) findViewById(R.id.spinner_eProfile_height);
        mMarriedSpinner = (TextView) findViewById(R.id.spinner_eProfile_mStatus);
        tv_dobHeading = (TextView) findViewById(R.id.tv_eProfile_DOBHeading);
        tv_heightHeading = (TextView) findViewById(R.id.tv_eProfile_HeightHeading);
        tv_marriedSHeading = (TextView) findViewById(R.id.tv_eProfile_MarriedHeading);
        tv_motherTHeading = (TextView) findViewById(R.id.tv_eProfile_MotherHeading);
        imgIdentity = (ImageView) findViewById(R.id.imo_eProfileIdentity);
        radio_self = (AppCompatRadioButton) findViewById(R.id.radio_profileManageBySelf);
        radio_parent = (AppCompatRadioButton) findViewById(R.id.radio_profileManageByParent);
        radio_sibling = (AppCompatRadioButton) findViewById(R.id.radio_profileManageBySibling);

        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);

        layout_profileManagedBy = (LinearLayout) findViewById(R.id.layout_eBasicManagedBy);

        btn_update = (AppCompatButton) findViewById(R.id.btn_eProfile_update);
        mProgressBar = (ProgressBar) findViewById(R.id.basic_edit_progressBar);

//        tv_caste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                casteList.clear();
//                castePresenter.getAllCastes();
//            }
//        });
//
//        tv_subCaste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                subCastList.clear();
//                subcastePresenter.getAllSubCasteByCaste("" + casteId);
//            }
//        });


        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Update Personal Information" );
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        stateList = new ArrayList<>();
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        casteList = new ArrayList<>();
        subCastList = new ArrayList<>();
        motherToungeList = new ArrayList<>();
        marriedList = new ArrayList<>();
        heightList = new ArrayList<>();


        heightList.add(new DrawerItem(1, "", "--Select Height--" ));
        heightList.add(new DrawerItem(1, "", "4.0 ft" ));
        heightList.add(new DrawerItem(1, "", "4.1 ft" ));
        heightList.add(new DrawerItem(1, "", "4.2 ft" ));
        heightList.add(new DrawerItem(1, "", "4.3 ft" ));
        heightList.add(new DrawerItem(1, "", "4.4 ft" ));
        heightList.add(new DrawerItem(1, "", "4.5 ft" ));
        heightList.add(new DrawerItem(1, "", "4.6 ft" ));
        heightList.add(new DrawerItem(1, "", "4.7 ft" ));
        heightList.add(new DrawerItem(1, "", "4.8 ft" ));
        heightList.add(new DrawerItem(1, "", "4.9 ft" ));
        heightList.add(new DrawerItem(1, "", "4.10 ft" ));
        heightList.add(new DrawerItem(1, "", "4.11 ft" ));
        heightList.add(new DrawerItem(1, "", "5.0 ft" ));
        heightList.add(new DrawerItem(1, "", "5.1 ft" ));
        heightList.add(new DrawerItem(1, "", "5.2 ft" ));
        heightList.add(new DrawerItem(1, "", "5.3 ft" ));
        heightList.add(new DrawerItem(1, "", "5.4 ft" ));
        heightList.add(new DrawerItem(1, "", "5.5 ft" ));
        heightList.add(new DrawerItem(1, "", "5.6 ft" ));
        heightList.add(new DrawerItem(1, "", "5.7 ft" ));
        heightList.add(new DrawerItem(1, "", "5.8 ft" ));
        heightList.add(new DrawerItem(1, "", "5.9 ft" ));
        heightList.add(new DrawerItem(1, "", "5.10 ft" ));
        heightList.add(new DrawerItem(1, "", "5.11 ft" ));
        heightList.add(new DrawerItem(1, "", "6.0 ft" ));
        heightList.add(new DrawerItem(1, "", "6.1 ft" ));
        heightList.add(new DrawerItem(1, "", "6.2 ft" ));
        heightList.add(new DrawerItem(1, "", "6.3 ft" ));
        heightList.add(new DrawerItem(1, "", "6.4 ft" ));
        heightList.add(new DrawerItem(1, "", "6.5 ft" ));
        heightList.add(new DrawerItem(1, "", "6.6 ft" ));
        heightList.add(new DrawerItem(1, "", "6.7 ft" ));
        heightList.add(new DrawerItem(1, "", "6.8 ft" ));
        heightList.add(new DrawerItem(1, "", "6.9 ft" ));
        heightList.add(new DrawerItem(1, "", "6.10 ft" ));
        heightList.add(new DrawerItem(1, "", "6.11 ft" ));
        heightList.add(new DrawerItem(1, "", "7.0 ft" ));

        marriedList.add(new DrawerItem("--Select Married Status--" ));
        marriedList.add(new DrawerItem("UnMarried" ));
        marriedList.add(new DrawerItem("Married" ));
        marriedList.add(new DrawerItem("Awaiting Divorce" ));
        marriedList.add(new DrawerItem("Divorced" ));
        marriedList.add(new DrawerItem("Widowed" ));
        marriedList.add(new DrawerItem("Annulled" ));

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreferences.getInt("memberId", 0);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            firstName = bundle.getString("edit_firstName" );
            fatherName = bundle.getString("edit_middleName" );
            Log.e("fatherName",fatherName);
            lastName = bundle.getString("edit_lastName" );
            emailId = bundle.getString("edit_emailId" );
            mobileNumber = bundle.getString("edit_mobileNumber" );
            dob = bundle.getString("edit_dob" );
            mTongue = bundle.getString("edit_mTongue" );
            Log.d(TAG, "mo" + mTongue);
            height = bundle.getString("edit_height" );
            marriedStatus = bundle.getString("edit_marriedStatus" );
            gender = bundle.getString("edit_gender" );
            country = bundle.getString("edit_country" );
            state = bundle.getString("edit_state" );
            city = bundle.getString("edit_city" );
            countryId = bundle.getString("edit_memberCountryId" );
            stateId = bundle.getString("edit_memberStateId" );
            cityId = bundle.getString("edit_memberCityId" );
            mTId = bundle.getString("edit_memberMotherTongue" );
            accountType = bundle.getString("edit_memberAccountCreatedBy" );
            accountManagedBy = bundle.getString("edit_memberAccountManagedBy" );
            photoIdPath = bundle.getString("edit_memberIdentityImage" );

           /* Log.d(TAG, "onCreate11111111111111111: "+photoIdPath);

            encodeImage(photoIdPath);*/
            caste = bundle.getString("edit_memberCaste" );
            subCaste = bundle.getString("edit_memberSubCaste" );

            tokenId = mPreferences.getString(Constants.ARG_FIREBASE_TOKEN, "" );

            Log.e(TAG, "tokenId============: " + tokenId);

            /*if (TextUtils.isEmpty(tokenId) && tokenId.length() < 10) {

                tokenId = FirebaseInstanceId.getInstance().getToken();
            }*/

            if (!firstName.equals("" )) {

                edt_firstName.setText(firstName);
            } else {

                edt_firstName.setHint("First Name" );
            }

            if (fatherName != null) {
                if (!fatherName.equals("" )) {
                    edt_fatherName.setText(fatherName);
                } else {
                    edt_fatherName.setHint("Father's Name" );
                }
            }

            if (!address.equals("" )) {

                edt_address.setText(address);
            } else {

                edt_address.setHint("Address" );
            }

            if (!emailId.equals("" )) {

                edt_emailId.setText(emailId);
            } else {

                edt_emailId.setHint("Email Id" );
            }

            if (!mobileNumber.equals("" )) {

                edt_mobileNumber.setText(mobileNumber);
            }

            if (!lastName.equals("" )) {

                edt_lastName.setText(lastName);
            } else {

                edt_lastName.setHint("Last Name" );
            }

            if (!country.equals("" )) {

                mCountrySpinner.setText(country);
            }

            if (!state.equals("" )) {

                mStateSpinner.setText(state);
            }

            if (!city.equals("" )) {

                mCitySpinner.setText(city);
            }

            if (!mTongue.equals("" )) {

                mMotherTSpinner.setText(mTongue);
            }

            if (!height.equals("" )) {

                mHeightSpinner.setText(height);
            }

            if (!marriedStatus.equals("" )) {

                mMarriedSpinner.setText(marriedStatus);
            }

//            if (!caste.equals("" )) {
//
//                tv_caste.setText(caste);
//            } else {
//
//                tv_caste.setText("Select Caste" );
//            }
//
//            if (!subCaste.equals("" )) {
//
//                tv_subCaste.setText(subCaste);
//            } else {
//
//                tv_subCaste.setText("Select Sub-Caste" );
//            }


            if (!dob.equals("" )) {

                tv_DOB.setText(dob);
            } else {

                tv_DOB.setText(getResources().getString(R.string.date_of_birth));
            }

            switch (String.valueOf(accountManagedBy)) {

                case "Self":
                    radio_self.setChecked(true);
                    break;

                case "Parent":
                    radio_parent.setChecked(true);
                    break;

                case "Sibling":
                    radio_sibling.setChecked(true);
                    break;

                default:
                    radio_self.setChecked(true);
                    break;
            }

            if (!photoIdPath.equals("" ) && photoIdPath.length() > 10) {

                Picasso.get()
                        .load(photoIdPath)
                        .placeholder(R.drawable.photo)
                        .into(imgIdentity);
            } else {

                Picasso.get()
                        .load(R.drawable.photo)
                        .placeholder(R.drawable.photo)
                        .into(imgIdentity);
            }

           /* imgIdentity.buildDrawingCache();
            Bitmap bmap = imgIdentity.getDrawingCache();*/

           /* Bitmap bitmap = null;
            try {
                URL url = new URL(photoIdPath);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }

           // bitmap = ((BitmapDrawable) imgIdentity.getDrawable()).getBitmap();
            getEncoded64ImageStringFromBitmap(bitmap);*/

        }

        if (accountType.equals("P" )) {

            tv_dobHeading.setVisibility(View.GONE);
            tv_DOB.setVisibility(View.GONE);
            tv_motherTHeading.setVisibility(View.GONE);
            mMotherTSpinner.setVisibility(View.GONE);
            tv_marriedSHeading.setVisibility(View.GONE);
            mMarriedSpinner.setVisibility(View.GONE);
            tv_heightHeading.setVisibility(View.GONE);
            mHeightSpinner.setVisibility(View.GONE);
            layout_profileManagedBy.setVisibility(View.GONE);
            mTId = "0";
            accountManagedBy = "Parent";

        } else if (accountType.equals("C" )) {

            tv_dobHeading.setVisibility(View.VISIBLE);
            tv_DOB.setVisibility(View.VISIBLE);
            tv_motherTHeading.setVisibility(View.VISIBLE);
            mMotherTSpinner.setVisibility(View.VISIBLE);
            tv_marriedSHeading.setVisibility(View.VISIBLE);
            mMarriedSpinner.setVisibility(View.VISIBLE);
            tv_heightHeading.setVisibility(View.VISIBLE);
            mHeightSpinner.setVisibility(View.VISIBLE);
            layout_profileManagedBy.setVisibility(View.VISIBLE);
        }

        mMotherTSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                motherToungeList.clear();
                motherTonguePresenter.getAllMotherTongue();
            }
        });

        if (gender.equalsIgnoreCase("Male" )) {
            radio_male.setChecked(true);
        } else {
            radio_female.setChecked(true);
        }

//        ((RadioGroup)findViewById(R.id.radioGroup_Gender)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId == radio_male.getId()){
//                    gender = "Male";
//                }
//
//                if(checkedId == radio_female.getId()){
//                    gender = "Female";
//                }
//            }
//        });

        mMarriedSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDesBasicAdapter = new CustomDesBasicAdapter(mContext, marriedList);
                displayMarriedStatusAlert();
                // mListView.setAdapter(customDesBasicAdapter);
            }
        });

        mCountrySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countryList.clear();
                countryPresenter.getCountries();

            }
        });

        mStateSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateList.clear();
                statePresenter.getStateByCountry(countryId);
            }
        });

        mCitySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityList.clear();
                cityPresenter.getCityByState(stateId);
            }
        });

        mHeightSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                heightAdapter = new HeightAdapter(mContext, heightList);
                displayHeightAlert();
            }
        });

       /* imgIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasPermission()) {
                    onSelectImageClick(view);
                } else {
                    requestPermission();
                }
            }
        });*/

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectionHelper.networkConnectivity(mContext)) {
                    firstName = edt_firstName.getText().toString().trim();
                    fatherName = edt_fatherName.getText().toString().trim();
                    lastName = edt_lastName.getText().toString().trim();
                    emailId = edt_emailId.getText().toString().trim();

                    if (radio_male.isChecked()) {
                        gender = "Male";
                    } else if (radio_female.isChecked()) {
                        gender = "Female";
                    }

                    if (radio_self.isChecked()) {

                        accountManagedBy = "Self";

                    } else if (radio_parent.isChecked()) {

                        accountManagedBy = "Parent";

                    } else if (radio_sibling.isChecked()) {

                        accountManagedBy = "Sibling";
                    }


                   /* updateBasicInfo();*/

                    updateTask();

                } else {

                    Toast.makeText(mContext, "No internet connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateUserPresenter = new UpdateUserPresenter(this);
        initPresenters();
    }

    private void initPresenters() {
        motherTonguePresenter = new MotherTonguePresenter(this);
        countryPresenter = new CountryPresenter(this);
        statePresenter = new StatePresenter(this);
        cityPresenter = new CityPresenter(this);
        castePresenter = new CastePresenter(this);
        subcastePresenter = new SubcastePresenter(this);
    }

   /* public void getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        Log.d(TAG, "getEncoded64ImageStringFromBitmap: "+imgString);
    }*/


//    private void displayCasteAlert() {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
//
//        builder.setView(alertView);
//
//        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
//        SearchView mSearchView = alertView.findViewById(R.id.searchView);
//
//        mSearchView.setQueryHint("Search Here" );
//        mSearchView.setIconified(false);
//        mSearchView.setFocusable(false);
//        mSearchView.clearFocus();
//
//
//        mListView.setAdapter(casteAdapter);
//        casteAdapter.notifyDataSetChanged();
//
//
//        final AlertDialog dialog = builder.create();
//
//        dialog.show();
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                CasteModel cityModel = (CasteModel) parent.getItemAtPosition(position);
//
//                casteId = cityModel.getCasteId();
//                tv_caste.setText(cityModel.getCasteName());
//
//                dialog.dismiss();
//
//            }
//        });
//
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                casteAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//    }
//
//    private void displaySubCasteAlert() {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
//
//        builder.setView(alertView);
//
//        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
//        SearchView mSearchView = alertView.findViewById(R.id.searchView);
//
//        mSearchView.setQueryHint("Search Here" );
//        mSearchView.setIconified(false);
//        mSearchView.setFocusable(false);
//        mSearchView.clearFocus();
//
//
//        mListView.setAdapter(casteAdapter);
//        casteAdapter.notifyDataSetChanged();
//
//
//        final AlertDialog dialog = builder.create();
//
//        dialog.show();
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                CasteModel cityModel = (CasteModel) parent.getItemAtPosition(position);
//
//                subCasteId = cityModel.getCasteId();
//                tv_subCaste.setText(cityModel.getCasteName());
//
//                dialog.dismiss();
//
//            }
//        });
//
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                casteAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//    }

    private void displayMarriedStatusAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);

        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here" );
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(customDesBasicAdapter);
        customDesBasicAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DrawerItem drawerItem = (DrawerItem) parent.getItemAtPosition(position);

                marriedStatus = drawerItem.getMarriedStatus();
                mMarriedSpinner.setText(drawerItem.getMarriedStatus());

                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customDesBasicAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void displaySpinnerAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);

        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here" );
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        mListView.setAdapter(motherTongueAdapter);
        motherTongueAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DrawerItem drawerItem = (DrawerItem) parent.getItemAtPosition(position);

                mTId = String.valueOf(drawerItem.getMotherTongueId());

                mMotherTSpinner.setText(drawerItem.getMotherTongueName());

                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                motherTongueAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void displayHeightAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);

        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here" );
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(heightAdapter);
        heightAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DrawerItem drawerItem = (DrawerItem) parent.getItemAtPosition(position);

                height = drawerItem.getHeight();
                mHeightSpinner.setText(drawerItem.getHeight());

                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                heightAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void displayCountryAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);

        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here" );
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    mListView.setSelection(getIndex(mListView, country));

                }

                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);

                countryId = String.valueOf(countryModel.getCountryId());
                mCountrySpinner.setText(countryModel.getCountryName());

                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                heightAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void displayStateAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);

        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here" );
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(stateAdapter);
        stateAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    mListView.setSelection(getIndex(mListView, state));

                }

                StateModel stateModel = (StateModel) parent.getItemAtPosition(position);

                stateId = String.valueOf(stateModel.getStateId());
                mStateSpinner.setText(stateModel.getStateName());

                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stateAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void displayCityAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);

        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here" );
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    mListView.setSelection(getIndex(mListView, city));

                }

                CityModel cityModel = (CityModel) parent.getItemAtPosition(position);

                cityId = String.valueOf(cityModel.getCityId());
                mCitySpinner.setText(cityModel.getCityName());

                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cityAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private int getIndex(ListView listView, String string) {

        int index = 0;

        for (int i = 0; i < listView.getCount(); i++) {

            String checkString = listView.getItemAtPosition(i).toString();

            if (checkString != null && checkString.equalsIgnoreCase(string)) {

                index = i;
                break;
            }
        }

        return index;
    }

    private void updateTask() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.member_basic_edit_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "edit astro response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success" );

                            if (success == 1) {

                                Toast.makeText(EditBasicInfoActivity.this, "Basic details updated successfully...", Toast.LENGTH_SHORT).show();
                                finish();
                                //startActivity(new Intent(EditAstroInfoActivity.this, HomeActivity.class));
                            } else {

                                Toast.makeText(EditBasicInfoActivity.this, "Details not updated...", Toast.LENGTH_SHORT).show();
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
                astrParam.put("MemberId", String.valueOf(memberId));
                astrParam.put("FirstName", firstName);
                astrParam.put("MiddleName", fatherName );
                astrParam.put("LastName", lastName);
                astrParam.put("Gender", gender);
                astrParam.put("DOB", dob);
                astrParam.put("Mobile", mobileNumber);
                astrParam.put("OtherMobileNo", "");
                astrParam.put("EmailId", emailId);
                astrParam.put("MotherTongue", mTId);
                astrParam.put("MarriedStatus", marriedStatus);
                astrParam.put("Height", "height" );
                astrParam.put("CountryId", countryId );
                astrParam.put("StateId", stateId);
                astrParam.put("CityId", cityId);
                astrParam.put("DeviceId", tokenId);
                astrParam.put("AccountMangeBy", String.valueOf(accountManagedBy));
                astrParam.put("IdentityPhoto", "");
                astrParam.put("IdentityPhotoExtension", ".jpg");

                return astrParam;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void updateBasicInfo() {

        try {
            if (MainPresenter.getInstance().getIdProofImage() != null)
                identyImage = ImageHelper.getStringImageFromFile(mContext, MainPresenter.getInstance().getIdProofImage().getPath());

            Log.d(TAG, "updateBasicInfo: "+identyImage);

            updateUserPresenter.updateUser(memberId, firstName, fatherName, lastName, gender,
                    dob, mobileNumber, "", emailId, "" + mTId,
                    "" + marriedStatus, "" + height, address, Integer.parseInt(countryId),
                    Integer.parseInt(stateId), Integer.parseInt(cityId), "" + tokenId, "" + accountManagedBy,
                    Utilities.getString(identyImage), ".jpg",
                    "" + casteId, "" + subCasteId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(mContext, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, EditMyProfileActivity.class));
    }*/

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
                MainPresenter.getInstance().setIdProofImage(imageUri);
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

                imgIdentity.setImageURI(result.getUri());
                try {
                    imgIdentity.setImageURI(path);
                    MainPresenter.getInstance().setIdProofImage(path);
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();

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
    public void showUserEditted(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        finish();
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

    }

    @Override
    public void showSubcasteList(List<SubcasteModel> subcasteModels) {
        subCastList.clear();
        subCastList.addAll(subcasteModels);
        subcasteAdapter = new MatrimonySubcasteAdapter(mContext, subCastList);
//        displaySubCasteAlert();
    }

    @Override
    public void showCasteList(List<CasteModel> casteModels) {
        casteList.clear();
        casteList.addAll(casteModels);
        casteAdapter = new MatrimonyCasteAdapter(mContext, casteList);
//        displayCasteAlert();
    }

    @Override
    public void showMotherTongues(List<MotherTongueModel> motherTongueModels) {
        motherToungeList.clear();
        motherToungeList.addAll(motherTongueModels);
        motherTongueAdapter = new MotherTongueAdapter(mContext, motherToungeList);
        displaySpinnerAlert();
    }

    @Override
    public void showCountries(List<CountryModel> countryModels) {
        countryList.clear();
        countryList.addAll(countryModels);
        countryAdapter = new CountryAdapter(mContext, countryList);
        displayCountryAlert();
    }

    @Override
    public void showStates(List<StateModel> stateModels) {
        stateList.clear();
        stateList.addAll(stateModels);
        stateAdapter = new StateAdapter(mContext, stateList);
        displayStateAlert();
    }

    @Override
    public void showCities(List<CityModel> cityModels) {
        cityList.clear();
        cityList.addAll(cityModels);
        cityAdapter = new CityAdapter(mContext, cityList);
        displayCityAlert();
    }
}