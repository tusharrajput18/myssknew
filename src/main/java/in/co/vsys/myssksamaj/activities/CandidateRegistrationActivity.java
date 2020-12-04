package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.contracts.NumberIfExistContract;
import in.co.vsys.myssksamaj.helpers.ImageHelper;
import in.co.vsys.myssksamaj.maindirectory.LoginOptionsActivity;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.presenters.NumberifExistPresenter;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CandidateRegistrationActivity extends AppCompatActivity implements NumberIfExistContract.NumberIfExistview {

    private Context mContext;
    private EditText edt_firstName, edt_middleName, edt_lastName, edt_mobileNumber, edt_emailId;
    private TextView tv_DOB;
    private RadioButton radio_male, radio_female;
    private String firstName, middleName, lastName, mobileNumber, emailId, selectedDate, radioProfile, radioGender;
    private RelativeLayout mRelativeLayout;
    private Snackbar mSnackbar;
    private Button next;
    private int mYear, mMonth, mDay;
    private SharedPreferences mPreference;
    private RadioGroup radioGroupProfileManaged;
    private AppCompatRadioButton radio_self, radio_parent, radio_sibling;
    private CharSequence accountManagedBy = null;
    Uri imageUri;

    ProgressBar register_progress;

    private String numbermsg;

    private NumberIfExistContract.NumberifExistOps numberifExistOps;


    private ImageView img_upload;

    private static final int PERMS_REQUEST_CODE = 124;
    private int PICK_IMAGE_REQUEST_PROFILE_PICK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matro_client_registration);
        mContext = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_clientReg);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Personal Information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        numberifExistOps = new NumberifExistPresenter(this);

        next = (Button) findViewById(R.id.btn_matroRegNext);
        edt_firstName = (EditText) findViewById(R.id.edt_matroRegFirstName);
        edt_middleName = (EditText) findViewById(R.id.edt_matroRegMiddleName);
        edt_lastName = (EditText) findViewById(R.id.edt_matroRegLastName);
        edt_mobileNumber = (EditText) findViewById(R.id.edt_matroRegMobile);
        edt_emailId = (EditText) findViewById(R.id.edt_matroRegEmailId);
        tv_DOB = (TextView) findViewById(R.id.tv_matroRegDOB);
        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);
        radioGroupProfileManaged = (RadioGroup) findViewById(R.id.radioGroup_profileManageBy);
        radio_self = (AppCompatRadioButton) findViewById(R.id.radio_profileManageBySelf);
        radio_parent = (AppCompatRadioButton) findViewById(R.id.radio_profileManageByParent);
        radio_sibling = (AppCompatRadioButton) findViewById(R.id.radio_profileManageBySibling);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.matro_Reglayout);
        register_progress = (ProgressBar) findViewById(R.id.register_progress);


        mPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        firstName = mPreference.getString("firstName", "");
        middleName = mPreference.getString("middleName", "");
        lastName = mPreference.getString("lastName", "");
        mobileNumber = mPreference.getString("mobileNumber", "");
        emailId = mPreference.getString("emailId", "");
        selectedDate = mPreference.getString("selectedDate", "");
        edt_firstName.setText("" + firstName);
        edt_middleName.setText("" + middleName);
        edt_lastName.setText("" + lastName);
        edt_mobileNumber.setText("" + mobileNumber);
        edt_emailId.setText("" + emailId);
        tv_DOB.setText("" + selectedDate);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldsEmpty();
            }
        });

        edt_mobileNumber.addTextChangedListener(new TextWatcher() {
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

        /*img_back_matroReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(mContext, LoginOptionsActivity.class));
            }
        });*/

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

        img_upload = (ImageView) findViewById(R.id.img_parentCamera);
        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasPermission()) {
                    onSelectImageClick();
                } else {
                    requestPermission();
                }
            }
        });
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

    private void checkFieldsEmpty() {

        firstName = edt_firstName.getText().toString().trim();
        middleName = edt_middleName.getText().toString().trim();
        lastName = edt_lastName.getText().toString().trim();
        mobileNumber = edt_mobileNumber.getText().toString().trim();
        emailId = edt_emailId.getText().toString().trim();

        if (radio_self.isChecked()) {
            accountManagedBy = "Self";
        } else if (radio_parent.isChecked()) {
            accountManagedBy = "Parent";
        } else if (radio_sibling.isChecked()) {
            accountManagedBy = "Sibling";
        }

        if (radio_male.isChecked()) {
            radioGender = "Male";
        } else if (radio_female.isChecked()) {
            radioGender = "Female";
        }

        if (ConnectionHelper.networkConnectivity(mContext)) {

            if ((TextUtils.isEmpty(accountManagedBy))) {
                Toast.makeText(mContext, "please select profile managed by", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidName(firstName)) {
                edt_firstName.setError(" minimum 3 characters required...! ");
                Toast.makeText(mContext, "Invalid First Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidName(middleName)) {
                edt_middleName.setError(" minimum 3 characters required...! ");
                Toast.makeText(mContext, "Invalid Father Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidName(lastName)) {
                edt_lastName.setError(" minimum 3 characters required...! ");
                Toast.makeText(mContext, "Invalid Last Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(selectedDate) || selectedDate.length() < 5) {
                tv_DOB.setError("Invalid Date of Birth");
                Toast.makeText(mContext, "Invalid Date of Birth", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidMobile(mobileNumber)) {
                edt_mobileNumber.setError(" Please enter valid 10 digit phone number...! ");
                Toast.makeText(mContext, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidEmail(emailId)) {
                edt_emailId.setError("Please enter valid emailId...!");
                Toast.makeText(mContext, "Invalid Email-Id", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((TextUtils.isEmpty(radioGender))) {
                Toast.makeText(this, "Invalid Gender", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("mytag", "checkFieldsEmpty: "+numbermsg);

            if(numbermsg.equals("Mobile number already exists...")){
                Toast.makeText(mContext, "Mobile number already exists...", Toast.LENGTH_SHORT).show();
                edt_mobileNumber.setError(" Mobile number already exists...! ");
                return;
            }

            String abc = ImageHelper.getStringImageFromUri(this, MainPresenter.getInstance().getProfileImage());

            if (abc == null || abc.isEmpty()) {
                Toast.makeText(mContext, "Invalid profile image", Toast.LENGTH_SHORT).show();
                return;
            }

            mPreference.edit().putString("radioGender", radioGender).apply();
            mPreference.edit().putString("firstName", firstName).apply();
            mPreference.edit().putString("middleName", middleName).apply();
            mPreference.edit().putString("lastName", lastName).apply();
            mPreference.edit().putString("selectedDate", selectedDate).apply();
            mPreference.edit().putString("mobileNumber", mobileNumber).apply();
            mPreference.edit().putString("emailId", emailId).apply();
            mPreference.edit().putString("profileManagedBy", String.valueOf(accountManagedBy)).apply();

            startActivity(new Intent(mContext, BasicInfoActivity.class));

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

    public void onSelectImageClick() {
        CropImage.startPickImageActivity(this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()

//                MainPresenter.getInstance().setIdProofImage(imageUri);
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
                imageUri = result.getUri();

                img_upload.setImageURI(imageUri);
                MainPresenter.getInstance().setProfileImage(imageUri);
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
            onSelectImageClick();
//            if (MainPresenter.getInstance().getProfileImage() != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCropImageActivity(MainPresenter.getInstance().getProfileImage());
//            }
        } else {
            Toast.makeText(this, "Please allow permission to select image.", Toast.LENGTH_SHORT).show();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                if ((shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))) {
//                    Toast.makeText(this, "Phone state permission denied...!", Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }

    @Override
    public void showNumbermsg(String message) {
        numbermsg=message;
        register_progress.setVisibility(View.GONE);
        if (message.equals("Mobile number already exists...")) {
            numbermsg=message;
            Toast.makeText(mContext, "Mobile number already exists...", Toast.LENGTH_SHORT).show();
        }else {
            numbermsg=message;
        }

    }

    @Override
    public void showLoading() {
        register_progress.setVisibility(View.VISIBLE);
       // Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideLoading() {

        register_progress.setVisibility(View.GONE);
        //Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showError(String message) {
        register_progress.setVisibility(View.GONE);
       // Toast.makeText(mContext, "3", Toast.LENGTH_SHORT).show();

    }
}