package in.co.vsys.myssksamaj.maindirectory;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BasicInfoActivity;
import in.co.vsys.myssksamaj.adapter.CityAdapter;
import in.co.vsys.myssksamaj.adapter.CountryAdapter;
import in.co.vsys.myssksamaj.adapter.MatrimonyCasteAdapter;
import in.co.vsys.myssksamaj.adapter.StateAdapter;
import in.co.vsys.myssksamaj.contracts.CityContract;
import in.co.vsys.myssksamaj.contracts.CountryContract;
import in.co.vsys.myssksamaj.contracts.ImagesContract;
import in.co.vsys.myssksamaj.contracts.NumberIfExistContract;
import in.co.vsys.myssksamaj.contracts.ParentRegistrationContract;
import in.co.vsys.myssksamaj.contracts.StateContract;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.helpers.ImageHelper;
import in.co.vsys.myssksamaj.model.CityModel;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.StateModel;
import in.co.vsys.myssksamaj.presenters.CityPresenter;
import in.co.vsys.myssksamaj.presenters.CountryPresenter;
import in.co.vsys.myssksamaj.presenters.ImagesPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.NumberifExistPresenter;
import in.co.vsys.myssksamaj.presenters.RegisterParentPresenter;
import in.co.vsys.myssksamaj.presenters.StatePresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

public class ParentRegisterActivity extends AppCompatActivity implements ParentRegistrationContract.RegisterParentView, ImagesContract.UploadImagesView , NumberIfExistContract.NumberIfExistview{

    private Context mContext;
    private static final String TAG = ParentRegisterActivity.class.getSimpleName();
    private EditText edt_firstName, edt_lastName, edt_mobileNo, edt_address, edt_emailId, edt_confirmPassword, edt_password;
    private AppCompatButton btn_register;
    private ProgressBar mProgressBar;
    private ImageView img_upload;
    private TextView tv_DOB;
    private static final int PERMS_REQUEST_CODE = 124;
    private int PICK_IMAGE_REQUEST_PROFILE_PICK = 1;
    private Snackbar mSnackbar;
    private RelativeLayout mRelativeLayout;
    private SharedPreferences mPreference;
    private static final String MAIN_USER_ID = "AppLoginId";
    private static final String FIRST_NAME = "FirstName";
    private static final String MIDDLE_NAME = "MiddleName";
    private static final String LAST_NAME = "LastName";
    private static final String GENDER = "Gender";
    private static final String DATE_OF_BIRTH = "DOB";
    private static final String MOBILE_NUMBER = "Mobile";
    private static final String OTHER_MOBILE = "OtherMobileNo";
    private static final String EMAIL_ID = "EmailId";
    private static final String MOTHER_TONGUE = "MotherTongue";
    private static final String MARRIED_STATUS = "MarriedStatus";
    private static final String HEIGHT = "Height";
    private static final String ADDRESS = "Address";
    private static final String COUNTRY_ID = "CountryId";
    private static final String STATE_ID = "StateId";
    private static final String CITY_ID = "CityId";
    private static final String FOR_PROFILE_CREATED = "AccountCreatedBy";
    private static final String DEVICE_ID = "DeviceId";
    private static final String PASSWORD = "Password";
    private static final String PHOTO_IDENTITY = "IdentityPhoto";
    private static final String PHOTO_EXTENSION = "IdentityPhotoExtension";

    private static final String COUNTRY_ID_STATE = "CountryID";
    private static final String PARENT_COUNTRY_ID = "ParentCountryId";
    private static final String PARENT_STATE_ID = "ParentStateId";
    private static final String PARENT_CITY_ID = "ParentCityId";
    private static final String ACCOUNT_MANAGED_BY = "AccountMangeBy";
    private static final String BASIC_PROFILE_PERCENTAGE = "BasicInformationPercentage";
    private static final String CASTE_ID = "Caste";
    private static final String SUB_CASTE_ID = "SubCaste";

    int flag = 0;

    private RelativeLayout
//            casteLayout, subCasteLayout,
            countryLayout, stateLayout, cityLayout;
    private List<CountryModel> countryList;
    private List<StateModel> stateList;
    private List<CityModel> cityList;
    //    private List<CasteModel> casteList;
//    private List<CasteModel> subCastList;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private MatrimonyCasteAdapter casteAdapter;
    private int
//            casteId, subCasteId,
            countryId, stateId, cityId;
    private TextView tv_caste, tv_subCaste, tv_countryName, tv_stateName, tv_city;

    private String accountManagedBy;
    private Uri mCropImageUri;
    private String tokenId, selectedDate;
    private int mYear, mMonth, mDay;

    private ImageView img_parent_id_Camera;
    private TextView tv_profile_picture_name;

    private NumberIfExistContract.NumberifExistOps numberifExistOps;


    private ImagesContract.ImageOps uploadImagesPresenter;
    private ParentRegistrationContract.RegisterParentOps registerParentPresenter;
    private String numbermsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_register);
        mContext = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_matrimonyRegister);
        numberifExistOps = new NumberifExistPresenter(this);

        tv_DOB = (TextView) findViewById(R.id.tv_matroRegDOB);
        tv_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                                selectedDate = (convertTime(dayOfMonth) + "/" + convertTime((monthOfYear + 1)) + "/" + year);

                                //age = mYear - year;
                                tv_DOB.setText(selectedDate);
                            }
                        }, mYear, mMonth, mDay);

                c.add(Calendar.YEAR, -18);
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        countryLayout = (RelativeLayout) findViewById(R.id.MatroBasicCountryLayout);
        stateLayout = (RelativeLayout) findViewById(R.id.matroBasicstateLayout);
        cityLayout = (RelativeLayout) findViewById(R.id.matroBasiccityLayout);
//        casteLayout = (RelativeLayout) findViewById(R.id.MatroBasicCasteLayout);
//        subCasteLayout = (RelativeLayout) findViewById(R.id.MatroBasicSubCasteLayout);
        tv_countryName = (TextView) findViewById(R.id.tv_matroBasicCountry);
        tv_stateName = (TextView) findViewById(R.id.tv_matroBasicState);
        tv_city = (TextView) findViewById(R.id.tv_matroBasicCity);
        tv_caste = (TextView) findViewById(R.id.tv_matroBasicCaste);
        tv_subCaste = (TextView) findViewById(R.id.tv_matroBasicSubCaste);
        edt_firstName = (EditText) findViewById(R.id.edt_parentRegFirstName);
        edt_lastName = (EditText) findViewById(R.id.edt_parentRegLastName);
        edt_mobileNo = (EditText) findViewById(R.id.edt_parentRegMobile);
        edt_address = (EditText) findViewById(R.id.edt_parentRegAddress);
        edt_emailId = (EditText) findViewById(R.id.edt_parentRegEmailId);
        edt_password = (EditText) findViewById(R.id.edt_parentRegPass);
        edt_confirmPassword = (EditText) findViewById(R.id.edt_parentRegConfirmPass);
        btn_register = (AppCompatButton) findViewById(R.id.btn_parentRegister);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_parentRegister);
        img_upload = (ImageView) findViewById(R.id.img_parentCamera);

        img_parent_id_Camera = (ImageView) findViewById(R.id.img_parent_id_Camera);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.layout_parentRegister);

        edt_mobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Number = charSequence.toString().trim();

                if (Number.toString().isEmpty()) {

                }
                if (Number.length() == 10) {
                    numberifExistOps.numberifexist(Number);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_firstName.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );


        edt_lastName.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Parent Registration");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();

//        casteList = new ArrayList<>();
//        subCastList = new ArrayList<>();

        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=1;

                if (hasPermission()) {
                    CropImage.startPickImageActivity(ParentRegisterActivity.this);
                } else {
                    requestPermission();
                }
            }
        });

        img_parent_id_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=2;
                if (hasPermission()) {
                    CropImage.startPickImageActivity(ParentRegisterActivity.this);
                } else {
                    requestPermission();
                }
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = edt_firstName.getText().toString().trim();
                String lastName = edt_lastName.getText().toString().trim();
                String mobileNumber = edt_mobileNo.getText().toString().trim();
                String emailId = edt_emailId.getText().toString().trim();
                String address = edt_address.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String confirm_pass = edt_confirmPassword.getText().toString().trim();
                accountManagedBy = "Parent";

                if (ConnectionHelper.networkConnectivity(mContext)) {

                    Log.d(TAG, "onClick123: "+MainPresenter.getInstance().getProfileImage());

                  //  String imgString=ImageHelper.getStringImageFromUri(mContext,MainPresenter.getInstance().getProfileImage());
                    String imgString=ImageHelper.getStringImageFromUri(mContext,MainPresenter.getInstance().getIdProofImage());
                    Log.d(TAG, "onClick123: "+imgString);

                    if (!isValidName(firstName)) {
                        Toast.makeText(mContext, "Please enter valid first name", Toast.LENGTH_SHORT).show();
                        edt_firstName.setError("Please enter valid first name");
                        return;
                    }
                    if (!isValidName(lastName)) {
                        Toast.makeText(mContext, "Please enter valid last name", Toast.LENGTH_SHORT).show();
                        edt_lastName.setError("Please enter valid last name");
                        return;
                    }
                    if (TextUtils.isEmpty(selectedDate) || selectedDate.length() < 5) {
                        Toast.makeText(mContext, "Please select Date of Birth", Toast.LENGTH_SHORT).show();
                        tv_DOB.setError("Please select Date of Birth");
                        return;
                    }

                    if (!isValidMobile(mobileNumber)) {
                        Toast.makeText(mContext, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                        edt_mobileNo.setError("Please enter valid mobile number");
                        return;
                    }

                    if(numbermsg.equals("Mobile number already exists...")){
                        Toast.makeText(mContext, "Mobile number already exists...", Toast.LENGTH_SHORT).show();
                        edt_mobileNo.setError(" Mobile number already exists...! ");
                        return;
                    }
                    if (!isValidEmail(emailId)) {
                        Toast.makeText(mContext, "Please enter valid emailId", Toast.LENGTH_SHORT).show();
                        edt_emailId.setError("Please enter valid emailId");
                        return;
                    }

                    if (tv_countryName.getText().toString().isEmpty()) {
                        Toast.makeText(mContext, "Please Select valid country", Toast.LENGTH_SHORT).show();
                        tv_countryName.setError("Please Select valid country");
                        return;
                    }

                    if (tv_stateName.getText().toString().isEmpty()) {
                        Toast.makeText(mContext, "Please Select valid State", Toast.LENGTH_SHORT).show();
                        tv_stateName.setError("Please Select valid State");
                        return;
                    }

                    if (tv_city.getText().toString().isEmpty()) {
                        Toast.makeText(mContext, "Please Select valid City", Toast.LENGTH_SHORT).show();
                        tv_city.setError("Please Select valid City");
                        return;
                    }
                    if (!isValidName(address)) {
                        Toast.makeText(mContext, "Please enter valid address", Toast.LENGTH_SHORT).show();
                        edt_address.setError("Please enter valid address");
                        return;
                    }
                    if (!isValidPassword(password)) {
                        Toast.makeText(mContext, "Please enter at least 4 character password", Toast.LENGTH_SHORT).show();
                        edt_password.setError("Please enter at least 4 character password");
                        return;
                    }
                    if (!confirm_pass.equals(password)) {
                        Toast.makeText(mContext, "Password not matching", Toast.LENGTH_SHORT).show();
                        edt_confirmPassword.setError("Password not matching");
                        return;
                    }

                    // Check image mandatory
                    if (MainPresenter.getInstance().getProfileImage() == null) {
                        Toast.makeText(mContext, "Please select profile image", Toast.LENGTH_SHORT).show();
                        return;
                    }

                  //  String abc=ImageHelper.getStringImageFromUri(ParentRegisterActivity.this,MainPresenter.getInstance().getIdProofImage());

                    if(MainPresenter.getInstance().getIdProofImage()==null){
                        Toast.makeText(mContext, "Please Select Id Proof.", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    if (MainPresenter.getInstance().getIdProofImage() == null) {
//                        Toast.makeText(mContext, "Please select ID proof image", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                    tokenId = FirebaseInstanceId.getInstance().getToken();

                    registerParentPresenter.registerParent(1, firstName, "", lastName,
                            "Male", selectedDate, mobileNumber, "", emailId,
                            0,
//                                String.valueOf(casteId), String.valueOf(subCasteId),
                            "", "", Utilities.extractDataFromEditText(edt_address),
                            countryId, stateId, cityId, "P", tokenId,
                            password,""+imgString,".jpg", 0, 0,
                            0, accountManagedBy, 20);

                    //                        clientRegistrationTask(firstName, lastName, mobileNumber, emailId, confirm_pass, accountManagedBy);

                    Log.d(TAG, "onClick: " + 1 + "\n " + firstName + "\n" + " " + lastName + "\n" +
                            "Male" + "\n" + selectedDate + "\n" + mobileNumber + "\n" + "" + "\n" + emailId + "\n" + 0 + "" + "" + "" + "" + "\n" + Utilities.extractDataFromEditText(edt_address));

                        Log.d(TAG, "onClick: " + countryId + "\n" + stateId + "\n" + cityId + "\n" + "P" + "\n" + tokenId + "\n" + password + "\n" + imgString+ "\n" +
                            ".jpg" + "\n" + 0 + "\n" + 0 + "\n" + 0 + "\n" + accountManagedBy + "\n" + 20);
                } else {

                    mSnackbar = Snackbar.make(
                            mRelativeLayout, getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG);
                    mSnackbar.show();
                }

            }
        });

//        casteLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadCasteList();
//            }
//        });
//
//        subCasteLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadSubCasteList(casteId);
//            }
//        });

        countryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryList.clear();
                CountryContract.CountryOps countryPresenter = new CountryPresenter(new CountryContract.CountryView() {
                    @Override
                    public void showCountries(List<CountryModel> countryModels) {
                        countryList.clear();
                        countryList.addAll(countryModels);
                        if (countryAdapter == null)
                            countryAdapter = new CountryAdapter(mContext, countryList);
                        else
                            countryAdapter.notifyDataSetChanged();
                        displayCountryAlert();
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
                });
                countryPresenter.getCountries();

            }
        });

        stateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StateContract.StateOps statePresenter = new StatePresenter(new StateContract.StateView() {
                    @Override
                    public void showStates(List<StateModel> stateModels) {
                        stateList.clear();
                        stateList.addAll(stateModels);
                        if (stateAdapter == null)
                            stateAdapter = new StateAdapter(mContext, stateList);
                        else
                            stateAdapter.notifyDataSetChanged();
                        displayStateAlert();
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
                });
                statePresenter.getStateByCountry("" + countryId);
            }
        });

        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CityContract.CityOps cityPresenter = new CityPresenter(new CityContract.CityView() {
                    @Override
                    public void showCities(List<CityModel> cityModels) {
                        cityList.clear();
                        cityList.addAll(cityModels);

                        if (cityAdapter == null)
                            cityAdapter = new CityAdapter(mContext, cityList);
                        else
                            cityAdapter.notifyDataSetChanged();
                        displayCityAlert();
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
                });
                cityPresenter.getCityByState("" + stateId);
//                loadCityTask(stateId);
            }
        });

        registerParentPresenter = new RegisterParentPresenter(this);
        uploadImagesPresenter = new ImagesPresenter(this);
    }

    public String convertTime(int input) {

        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(mContext, LoginOptionsActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(mContext, LoginOptionsActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

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
                Uri imageUri = result.getUri();
                if(flag==1){

                    img_upload.setImageURI(imageUri);
                    Log.d(TAG, "onActivityResult: "+imageUri);
                    MainPresenter.getInstance().setProfileImage(imageUri);
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                    flag=0;
                }

                if(flag==2){

                    img_parent_id_Camera.setImageURI(imageUri);
                    Log.d(TAG, "onActivityResult: "+imageUri);
                    MainPresenter.getInstance().setIdProofImage(imageUri);
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                    flag=0;
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();

            }
        }
//        // handle result of CropImageActivity
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri path = result.getUri();
//
//                img_upload.setImageURI(result.getUri());
//                try {
//
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
//
//            }
//        }

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

    private boolean isValidMobile(String bMobile) {

        if (bMobile != null && bMobile.length() > 9 && bMobile.length() < 11) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String bName) {
        if (bName != null && bName.length() > 2) {
            return true;
        }
        return false;
    }

    private boolean isValidPassword(String bName) {

        if (bName != null && bName.length() > 3) {
            return true;
        }

        return false;
    }

    private void displayCountryAlert() {

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
//        mSearchView.setQueryHint("Search Here");
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
//                CasteModel cityModel = (CasteModel) parent.getItemAtPosition(position);
//                casteId = cityModel.getCasteId();
//                tv_caste.setText(cityModel.getCasteName());
//                dialog.dismiss();
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
//        mSearchView.setQueryHint("Search Here");
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




    private void alertAccountStatus() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Registration successful, Your account is under Admin observation");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(ParentRegisterActivity.this, LoginOptionsActivity.class));
                finish();
            }
        });

        alertDialog.show();
    }

    @Override
    public void showParentRegistered(String message, String memberId) {

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
    public void showParentRegisteredError() {

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
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showImageUploaded() {

        alertAccountStatus();

    }

    @Override
    public void showImageUploadFailed() {

    }

    @Override
    public void showNumbermsg(String message) {

        numbermsg=message;
        mProgressBar.setVisibility(View.VISIBLE);
        if (message.equals("Mobile number already exists...")) {
            numbermsg=message;
            Toast.makeText(mContext, "Mobile number already exists...", Toast.LENGTH_SHORT).show();
        }else {
            numbermsg=message;
        }

    }
}