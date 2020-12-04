package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CityAdapter;
import in.co.vsys.myssksamaj.adapter.CountryAdapter;
import in.co.vsys.myssksamaj.adapter.CustomDesBasicAdapter;
import in.co.vsys.myssksamaj.adapter.HeightAdapter;
import in.co.vsys.myssksamaj.adapter.MatrimonyCasteAdapter;
import in.co.vsys.myssksamaj.adapter.MatrimonySubcasteAdapter;
import in.co.vsys.myssksamaj.adapter.MotherTongueAdapter;
import in.co.vsys.myssksamaj.adapter.StateAdapter;
import in.co.vsys.myssksamaj.contracts.BasicRegistrationContract;
import in.co.vsys.myssksamaj.contracts.CasteContract;
import in.co.vsys.myssksamaj.contracts.CityContract;
import in.co.vsys.myssksamaj.contracts.CountryContract;
import in.co.vsys.myssksamaj.contracts.ImagesContract;
import in.co.vsys.myssksamaj.contracts.MotherTongueContract;
import in.co.vsys.myssksamaj.contracts.StateContract;
import in.co.vsys.myssksamaj.contracts.SubcasteContract;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.helpers.ImageHelper;
import in.co.vsys.myssksamaj.maindirectory.LoginOptionsActivity;
import in.co.vsys.myssksamaj.model.CasteModel;
import in.co.vsys.myssksamaj.model.CityModel;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.DrawerItem;
import in.co.vsys.myssksamaj.model.StateModel;
import in.co.vsys.myssksamaj.model.data_models.MotherTongueModel;
import in.co.vsys.myssksamaj.model.data_models.SubcasteModel;
import in.co.vsys.myssksamaj.presenters.BasicRegistrationPresenter;
import in.co.vsys.myssksamaj.presenters.CastePresenter;
import in.co.vsys.myssksamaj.presenters.CityPresenter;
import in.co.vsys.myssksamaj.presenters.CountryPresenter;
import in.co.vsys.myssksamaj.presenters.ImagesPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.MotherTonguePresenter;
import in.co.vsys.myssksamaj.presenters.StatePresenter;
import in.co.vsys.myssksamaj.presenters.SubcastePresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

public class BasicInfoActivity extends AppCompatActivity implements ImagesContract.UploadImagesView,
        MotherTongueContract.MotherTongueView, CountryContract.CountryView, StateContract.StateView,
        CityContract.CityView, CasteContract.CasteView, BasicRegistrationContract.BasicRegistrationView,
        SubcasteContract.SubcasteView {

    //9420300145

    private Context mContext;
    private ProgressBar mProgressBar;
    private MotherTongueAdapter motherTongueAdapter;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private MatrimonyCasteAdapter casteAdapter;
    private MatrimonySubcasteAdapter subcasteAdapter;
    private RelativeLayout mRelativeLayout;
    private CustomDesBasicAdapter customDesBasicAdapter;
    private static final String TAG = BasicInfoActivity.class.getSimpleName();
    private List<DrawerItem> heightList;
    private List<MotherTongueModel> motherTongueList;

    private HeightAdapter heightAdapter;
    // Commented caste and subcaste code
    // casteLayout, subCasteLayout,
    private RelativeLayout motherLayout, marriedLayout, heightLayout,
            countryLayout, stateLayout, cityLayout;
    private TextView tv_caste, tv_subCaste, tv_motherTongue, tv_marriedStatus, tv_countryName,
            tv_stateName, tv_city, tv_height;
    private ImageView img_upload;
    private static final int PERMS_REQUEST_CODE = 124;
    private AppCompatEditText edt_Address;
    private EditText edt_password, edt_confirmPassword;
    private Button btn_next;
    private List<DrawerItem> marriedList;
    private List<CountryModel> countryList;
    private List<StateModel> stateList;
    private List<CityModel> cityList;
    private List<CasteModel> casteList;
    private List<SubcasteModel> subCastList;
    private SharedPreferences mPreference;
    private int casteId, subCasteId, motherTongueId, countryId, stateId, cityId;
    private Snackbar mSnackbar;
    private String firstName, middleName, lastName, mobileNumber, emailId, selectedDate, radioGender,
            marriedStatus, heightName, profileManagedBy, address, password, confirmPassword;

    private Uri mCropImageUri = null;
    private String tokenId;

    private boolean imageUploaded = false;

    // Presenters
    private ImagesContract.ImageOps uploadImagesPresenter;
    private MotherTongueContract.MotherTongueOps motherTonguePresenter;
    private CountryContract.CountryOps countryPresenter;
    private StateContract.StateOps statePresenter;
    private CityContract.CityOps cityPresenter;
    private CasteContract.CasteOps castePresenter;
    private SubcasteContract.SubcasteOps subcastePresenter;
    private BasicRegistrationContract.BasicRegistrationOps basicRegistrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matro_basic_info);
        mContext = this;

        tokenId = FirebaseInstanceId.getInstance().getToken();

        mProgressBar = (ProgressBar) findViewById(R.id.matroBasic_progressBar);
        motherLayout = (RelativeLayout) findViewById(R.id.motherTongueLayout);
//        casteLayout = (RelativeLayout) findViewById(R.id.castLayout);
//        subCasteLayout = (RelativeLayout) findViewById(R.id.subCastLayout);
        marriedLayout = (RelativeLayout) findViewById(R.id.marriedStatusLayout);
        heightLayout = (RelativeLayout) findViewById(R.id.HeightLayout);
        countryLayout = (RelativeLayout) findViewById(R.id.MatroBasicCountryLayout);
        stateLayout = (RelativeLayout) findViewById(R.id.matroBasicstateLayout);
        cityLayout = (RelativeLayout) findViewById(R.id.matroBasiccityLayout);
        tv_motherTongue = (TextView) findViewById(R.id.tv_matroBasicMTongue);
        tv_caste = (TextView) findViewById(R.id.tv_matroBasicCast);
        tv_subCaste = (TextView) findViewById(R.id.tv_matroBasicSubCast);
        tv_marriedStatus = (TextView) findViewById(R.id.tv_matroBasicmarriedStatus);
        tv_countryName = (TextView) findViewById(R.id.tv_matroBasicCountry);
        tv_stateName = (TextView) findViewById(R.id.tv_matroBasicState);
        tv_height = (TextView) findViewById(R.id.tv_matroBasicHeight);
        tv_city = (TextView) findViewById(R.id.tv_matroBasicCity);
        btn_next = (Button) findViewById(R.id.btn_matroBasicNext);
        edt_Address = (AppCompatEditText) findViewById(R.id.edt_matroBasicAddress);
        edt_password = (EditText) findViewById(R.id.edt_matroBasicPassword);
        edt_confirmPassword = (EditText) findViewById(R.id.edt_matroBasicConfirmPassword);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.matroBasicRelative);
        img_upload = (ImageView) findViewById(R.id.img_parentCamera);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_basicInfo);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Personal Information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        motherTongueList = new ArrayList<>();
        marriedList = new ArrayList<>();
        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        heightList = new ArrayList<>();
        casteList = new ArrayList<>();
        subCastList = new ArrayList<>();

        marriedList.add(new DrawerItem("--Select Married Status--"));
        marriedList.add(new DrawerItem("Never Married"));
        /* marriedList.add(new DrawerItem("Married"));*/
        marriedList.add(new DrawerItem("Awaiting Divorce"));
        marriedList.add(new DrawerItem("Divorced"));
        marriedList.add(new DrawerItem("Widowed"));
        marriedList.add(new DrawerItem("Annulled"));

        heightList.add(new DrawerItem(1, "", "--Select Height--"));
        heightList.add(new DrawerItem(1, "", "4.0 ft"));
        heightList.add(new DrawerItem(1, "", "4.1 ft"));
        heightList.add(new DrawerItem(1, "", "4.2 ft"));
        heightList.add(new DrawerItem(1, "", "4.3 ft"));
        heightList.add(new DrawerItem(1, "", "4.4 ft"));
        heightList.add(new DrawerItem(1, "", "4.5 ft"));
        heightList.add(new DrawerItem(1, "", "4.6 ft"));
        heightList.add(new DrawerItem(1, "", "4.7 ft"));
        heightList.add(new DrawerItem(1, "", "4.8 ft"));
        heightList.add(new DrawerItem(1, "", "4.9 ft"));
        heightList.add(new DrawerItem(1, "", "4.10 ft"));
        heightList.add(new DrawerItem(1, "", "4.11 ft"));
        heightList.add(new DrawerItem(1, "", "5.0 ft"));
        heightList.add(new DrawerItem(1, "", "5.1 ft"));
        heightList.add(new DrawerItem(1, "", "5.2 ft"));
        heightList.add(new DrawerItem(1, "", "5.3 ft"));
        heightList.add(new DrawerItem(1, "", "5.4 ft"));
        heightList.add(new DrawerItem(1, "", "5.5 ft"));
        heightList.add(new DrawerItem(1, "", "5.6 ft"));
        heightList.add(new DrawerItem(1, "", "5.7 ft"));
        heightList.add(new DrawerItem(1, "", "5.8 ft"));
        heightList.add(new DrawerItem(1, "", "5.9 ft"));
        heightList.add(new DrawerItem(1, "", "5.10 ft"));
        heightList.add(new DrawerItem(1, "", "5.11 ft"));
        heightList.add(new DrawerItem(1, "", "6.0 ft"));
        heightList.add(new DrawerItem(1, "", "6.1 ft"));
        heightList.add(new DrawerItem(1, "", "6.2 ft"));
        heightList.add(new DrawerItem(1, "", "6.3 ft"));
        heightList.add(new DrawerItem(1, "", "6.4 ft"));
        heightList.add(new DrawerItem(1, "", "6.5 ft"));
        heightList.add(new DrawerItem(1, "", "6.6 ft"));
        heightList.add(new DrawerItem(1, "", "6.7 ft"));
        heightList.add(new DrawerItem(1, "", "6.8 ft"));
        heightList.add(new DrawerItem(1, "", "6.9 ft"));
        heightList.add(new DrawerItem(1, "", "6.10 ft"));
        heightList.add(new DrawerItem(1, "", "6.11 ft"));
        heightList.add(new DrawerItem(1, "", "7.0 ft"));

        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermission()) {
                    onSelectImageClick(view);
                } else {
                    requestPermission();
                }
            }
        });

        motherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motherTongueList.clear();
                motherTonguePresenter.getAllMotherTongue();
            }
        });

        marriedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDesBasicAdapter = new CustomDesBasicAdapter(BasicInfoActivity.this, marriedList);
                displayMarriedStatusAlert();
            }
        });

        countryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryList.clear();
                countryPresenter.getCountries();
            }
        });

        stateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateList.clear();
                statePresenter.getStateByCountry("" + countryId);
            }
        });

        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityList.clear();
                cityPresenter.getCityByState("" + stateId);
            }
        });

        heightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heightAdapter = new HeightAdapter(BasicInfoActivity.this, heightList);
                displayHeightAlert();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBlankFields();
            }
        });

//        casteLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                casteList.clear();
//                castePresenter.getAllCastes();
//            }
//        });
//
//        subCasteLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                subCastList.clear();
//                subcastePresenter.getAllSubCasteByCaste("" + casteId);
//            }
//        });

        initPresenters();
    }

    private void initPresenters() {
        uploadImagesPresenter = new ImagesPresenter(this);
        motherTonguePresenter = new MotherTonguePresenter(this);
        countryPresenter = new CountryPresenter(this);
        statePresenter = new StatePresenter(this);
        cityPresenter = new CityPresenter(this);
        castePresenter = new CastePresenter(this);
        subcastePresenter = new SubcastePresenter(this);
        basicRegistrationPresenter = new BasicRegistrationPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                /*startActivity(new Intent(BasicInfoActivity.this, CandidateRegistrationActivity.class));
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void checkBlankFields() {
        radioGender = mPreference.getString("radioGender", null);
        firstName = mPreference.getString("firstName", null);
        middleName = mPreference.getString("middleName", null);
        lastName = mPreference.getString("lastName", null);
        selectedDate = mPreference.getString("selectedDate", null);
        mobileNumber = mPreference.getString("mobileNumber", null);
        emailId = mPreference.getString("emailId", null);
        profileManagedBy = mPreference.getString("profileManagedBy", null);

        address = edt_Address.getText().toString();
        password = edt_password.getText().toString().trim();
        confirmPassword = edt_confirmPassword.getText().toString().trim();

        try {
            if (ConnectionHelper.networkConnectivity(mContext)) {

               /* if ((TextUtils.isEmpty(mTonge)) || (TextUtils.isEmpty(marriedStatus)) || (TextUtils.isEmpty(heightName)) || (TextUtils.isEmpty(cName)) || (TextUtils.isEmpty(sName)) || (TextUtils.isEmpty(cityName))
                        || (TextUtils.isEmpty(address)) || confirm_password.equals(password))*/
                if (motherTongueId < 1) {

                    tv_motherTongue.setError("Please select Mother Tongue");
                    Toast.makeText(mContext, "Invalid Mother Tongue", Toast.LENGTH_SHORT).show();

                }else if (marriedStatus==null) {

                    tv_marriedStatus.setError("Please select Marital Status");
                    Toast.makeText(mContext, "Invalid Marital Status", Toast.LENGTH_SHORT).show();

                }else  if (heightName==null) {

                    tv_height.setError("Please select Height");
                    Toast.makeText(mContext, "Invalid Height", Toast.LENGTH_SHORT).show();

                } else if (countryId < 1) {

                    tv_countryName.setError("Please select Country");
                    Toast.makeText(mContext, "Invalid Country", Toast.LENGTH_SHORT).show();
                } else if (stateId < 1) {

                    tv_stateName.setError("Please select state");
                    Toast.makeText(mContext, "Invalid State", Toast.LENGTH_SHORT).show();
                } else if (cityId < 1) {

                    tv_city.setError("Please select city");
                    Toast.makeText(mContext, "Invalid City", Toast.LENGTH_SHORT).show();
                } else if (address.length() < 2) {

                    edt_Address.setError("Please enter address");
                    Toast.makeText(mContext, "Invalid Address", Toast.LENGTH_SHORT).show();
                } else if (MainPresenter.getInstance().getProfileImage() == null) {
                    Toast.makeText(this, "Please select profile image", Toast.LENGTH_SHORT).show();
                    return;
                } else if (MainPresenter.getInstance().getIdProofImage() == null) {
                    Toast.makeText(this, "Please select ID proof image", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 4) {

                    edt_password.setError("Please enter at least 4 digit password");
                    Toast.makeText(mContext, "Invalid Password", Toast.LENGTH_SHORT).show();

                } else if (!confirmPassword.equals(password)) {

                    edt_confirmPassword.setError("Password not matching");
                    Toast.makeText(mContext, "Invalid Password", Toast.LENGTH_SHORT).show();

                } else {
//
                   /* if (MainPresenter.getInstance().getProfileImage() == null) {
                        Toast.makeText(this, "Please select profile image", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (MainPresenter.getInstance().getIdProofImage() == null) {
                        Toast.makeText(this, "Please select ID proof image", Toast.LENGTH_SHORT).show();
                        return;
                    }*/

//                    clientRegistrationTask();
                    basicRegistrationPresenter.registerBasicInfo("1", firstName, middleName,
                            lastName, radioGender, selectedDate, mobileNumber, "",
                            emailId, String.valueOf(motherTongueId), marriedStatus, heightName, String.valueOf(countryId),
                            String.valueOf(stateId), String.valueOf(cityId), "C",
                            tokenId, confirmPassword, ImageHelper.getStringImageFromUri(mContext, MainPresenter.getInstance().getIdProofImage()),
                            ".jpg", "0", "0", "0", profileManagedBy,
                            "20");
                    Log.d(TAG, "checkBlankFields: " +

                            "\nfname " + firstName +
                            "\nmname " + middleName +
                            "\nlname " + lastName +
                            "\ngender " + radioGender +
                            "\ndob " + selectedDate +
                            "\nnumber " + mobileNumber +
                            "\n" + "" +
                            "\nemail " + emailId +
                            "\nmother " + motherTongueId +
                            "\nmarried " + marriedStatus +
                            "\nheight " + heightName +
                            "\ncountry " + countryId +
                            "\nstateid " + stateId +
                            "\ncity " + cityId +
                            "\naddress " + address +
                            "\ncretedby " + "c" +
                            "\ntoken " + tokenId +
                            "\npass " + confirmPassword +
                            "\nimg " + ImageHelper.getStringImageFromUri(mContext, MainPresenter.getInstance().getIdProofImage()) +
                            "\n" + ".jpg" +
                            "\n" + "0" +
                            "\n" + "0" +
                            "\n" + "0" +
                            "\nmanaged by " + profileManagedBy +
                            "\npercent " + 20);

                    Log.d(TAG, "checkBlankFields: " + "managed by " + profileManagedBy);


//                            , String.valueOf(casteId), String.valueOf(subCasteId));
                }
            } else {

                mSnackbar = Snackbar
                        .make(mRelativeLayout, getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mSnackbar.dismiss();

                            }
                        });
                mSnackbar.setActionTextColor(Color.RED);
                mSnackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(BasicInfoActivity.this, CandidateRegistrationActivity.class));
    }*/

    private void displayMotherTongueSpinnerAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        if (motherTongueAdapter == null) {
            motherTongueAdapter = new MotherTongueAdapter(BasicInfoActivity.this, motherTongueList);
        }
        mListView.setAdapter(motherTongueAdapter);
        motherTongueAdapter.notifyDataSetChanged();


        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MotherTongueModel motherTongueModel = (MotherTongueModel) parent.getItemAtPosition(position);
                motherTongueId = Integer.parseInt(motherTongueModel.getMothertongueid());
                tv_motherTongue.setText(motherTongueModel.getMothertongue());
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

    private void displayMarriedStatusAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
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

                if(drawerItem.getMarriedStatus().equals("Never Married")){
                    marriedStatus = "UnMarried";
                }else {
                    marriedStatus = drawerItem.getMarriedStatus();
                }


                tv_marriedStatus.setText(drawerItem.getMarriedStatus());

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

    private void displayHeightAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);


        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
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
                heightName = drawerItem.getHeight();
                tv_height.setText(drawerItem.getHeight());
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
        Button btn_ok_sub = alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        //mSearchView.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
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

                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);
                countryId = countryModel.getCountryId();
                tv_countryName.setText(countryModel.getCountryName());
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
                countryAdapter.getFilter().filter(newText);
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
        Button btn_ok_sub = alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        //mSearchView.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
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

                StateModel stateModel = (StateModel) parent.getItemAtPosition(position);
                stateId = stateModel.getStateId();
                tv_stateName.setText(stateModel.getStateName());
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
        Button btn_ok_sub = alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setQueryHint("Search Here");
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

                CityModel cityModel = (CityModel) parent.getItemAtPosition(position);

                cityId = cityModel.getCityId();
                tv_city.setText(cityModel.getCityName());

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

    private void displayCasteAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        mListView.setAdapter(casteAdapter);
        casteAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();

        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CasteModel cityModel = (CasteModel) parent.getItemAtPosition(position);
                casteId = cityModel.getCasteId();
                tv_caste.setText(cityModel.getCasteName());
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
                casteAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void displaySubCasteAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        mListView.setAdapter(subcasteAdapter);
        subcasteAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CasteModel cityModel = (CasteModel) parent.getItemAtPosition(position);
                subCasteId = cityModel.getCasteId();
                tv_subCaste.setText(cityModel.getCasteName());
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
                casteAdapter.getFilter().filter(newText);
                return false;
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                MainPresenter.getInstance().setIdProofImage(imageUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();

                img_upload.setImageURI(imageUri);
                MainPresenter.getInstance().setIdProofImage(imageUri);
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

    private void showFileChooser(int requestCode) {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, requestCode);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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
            if (MainPresenter.getInstance().getIdProofImage() != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCropImageActivity(MainPresenter.getInstance().getIdProofImage());
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if ((shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                        && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))) {
                    Toast.makeText(this, "Phone state permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void showImageUploaded() {
        // Toast.makeText(BasicInfoActivity.this, "Information successfully submitted...!", Toast.LENGTH_SHORT).show();
        alertAccountStatus();
        /*startActivity(new Intent(BasicInfoActivity.this, LoginOptionsActivity.class));
        finish();*/
    }

    private void alertAccountStatus() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Registration successful, Your account is under Admin observation");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(BasicInfoActivity.this, LoginOptionsActivity.class));
                finish();
            }
        });

        alertDialog.show();
    }

    @Override
    public void showImageUploadFailed() {

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
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMotherTongues(List<MotherTongueModel> motherTongueModels) {
        motherTongueList.addAll(motherTongueModels);
        displayMotherTongueSpinnerAlert();
    }

    @Override
    public void showCountries(List<CountryModel> countryModels) {
        countryList.addAll(countryModels);
        countryAdapter = new CountryAdapter(BasicInfoActivity.this, countryList);
        displayCountryAlert();
    }

    @Override
    public void showStates(List<in.co.vsys.myssksamaj.model.StateModel> stateModels) {

        if (!Utilities.isListEmpty(stateModels)) {
            stateList.clear();
            stateList.addAll(stateModels);
        }

        stateList.addAll(stateModels);
        stateAdapter = new StateAdapter(BasicInfoActivity.this, stateList);
        displayStateAlert();
    }

    @Override
    public void showCities(List<CityModel> cityModels) {
        if (!Utilities.isListEmpty(cityModels)) {
            cityList.clear();
            cityList.addAll(cityModels);
        }

        cityAdapter = new CityAdapter(BasicInfoActivity.this, cityList);
        displayCityAlert();
    }

    @Override
    public void showCasteList(List<CasteModel> casteModels) {
        casteList.addAll(casteModels);
        casteAdapter = new MatrimonyCasteAdapter(BasicInfoActivity.this, casteList);
        displayCasteAlert();
    }

    @Override
    public void showBasicInfoRegistered(String message, String memberId) {
        String mainProfileImage = ImageHelper.getStringImageFromUri(mContext, MainPresenter.getInstance().getProfileImage());
        uploadImagesPresenter.uploadImages(memberId,
                mainProfileImage, ".jpg", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                "", "", "3", "1",
                10
        );
    }

    @Override
    public void showSubcasteList(List<SubcasteModel> subcasteModels) {
        subCastList.addAll(subcasteModels);
        subcasteAdapter = new MatrimonySubcasteAdapter(BasicInfoActivity.this, subCastList);
        //mListView.setAdapter(cityAdapter);
        displaySubCasteAlert();
    }
}