package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.interfaces.RadioSelectionListener;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class UploadImagesActivity extends AppCompatActivity {

    private static final String TAG = UploadImagesActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    private static final String PROFILE_PHOTO = "MainProfilePhoto";
    private static final String PROFILE_EXTENSION = "MainProfilePhotoExtension";
    private static final String MAINPHOTO_SHOWTO = "mainphoto_showTo";
    private static final String MAINPHOTO_STATUS = "mainphoto_status";

    private static final String PHOTO_ONE = "Photo1";
    private static final String PHOTO_ONE_EXTENSION = "Photo1Extension";
    private static final String PHOTO_SHOWTO1 = "photo_showTo1";
    private static final String PHOTO_STATUS1 = "photo_status1";

    private static final String PHOTO_TWO = "Photo2";
    private static final String PHOTO_TWO_EXTENSION = "Photo2Extension";
    private static final String PHOTO_SHOWTO2 = "photo_showTo2";
    private static final String PHOTO_STATUS2 = "photo_status2";

    private static final String PHOTO_THREE = "Photo3";
    private static final String PHOTO_THREE_EXTENSION = "Photo3Extension";
    private static final String PHOTO_SHOWTO3 = "photo_showTo3";
    private static final String PHOTO_STATUS3 = "photo_status3";

    private static final String PHOTO_FOUR = "Photo4";
    private static final String PHOTO_FOUR_EXTENSION = "Photo4Extension";
    private static final String PHOTO_SHOWTO4 = "photo_showTo4";
    private static final String PHOTO_STATUS4 = "photo_status4";

    private static final String PHOTO_FIVE = "Photo5";
    private static final String PHOTO_FIVE_EXTENSION = "Photo5Extension";
    private static final String PHOTO_SHOWTO5 = "photo_showTo5";
    private static final String PHOTO_STATUS5 = "photo_status5";

    private static final String PHOTO_SIX = "Photo6";
    private static final String PHOTO_SIX_EXTENSION = "Photo6Extension";
    private static final String PHOTO_SHOWTO6 = "photo_showTo6";
    private static final String PHOTO_STATUS6 = "photo_status6";

    private static final String PHOTO_SEVEN = "Photo7";
    private static final String PHOTO_SEVEN_EXTENSION = "Photo7Extension";
    private static final String PHOTO_SHOWTO7 = "photo_showTo7";
    private static final String PHOTO_STATUS7 = "photo_status7";

    private static final String PHOTO_EIGHT = "Photo8";
    private static final String PHOTO_EIGHT_EXTENSION = "Photo8Extension";
    private static final String PHOTO_SHOWTO8 = "photo_showTo8";
    private static final String PHOTO_STATUS8 = "photo_status8";

    private static final String PHOTO_NINE = "Photo9";
    private static final String PHOTO_NINE_EXTENSION = "Photo9Extension";
    private static final String PHOTO_SHOWTO9 = "photo_showTo9";
    private static final String PHOTO_STATUS9 = "photo_status9";

    private static final String PHOTO_TEN = "Photo10";
    private static final String PHOTO_TEN_EXTENSION = "Photo10Extension";
    private static final String PHOTO_SHOWTO10 = "photo_showTo10";
    private static final String PHOTO_STATUS10 = "photo_status10";

    private int mainPhotoShowTo = 0, photoShowTo1 = 0, photoShowTo2 = 0, photoShowTo3 = 0, photoShowTo4 = 0,
            photoShowTo5 = 0, photoShowTo6 = 0, photoShowTo7 = 0, photoShowTo8 = 0, photoShowTo9 = 0,
            photoShowTo10 = 0;

    private static final String PHOTO_PROFILE_PERCENTAGE = "PhotoInformationPercentage";
    private ImageView img_profile, img_one, img_two, img_three, img_four, img_five, img_six, img_seven, img_eight, img_nine, img_ten;
    private ImageView clik_img_profile, clik_img_one, clik_img_two, clik_img_three, clik_img_four, clik_img_five, clik_img_six, clik_img_seven, clik_img_eight, clik_img_nine, clik_img_ten;
    private LinearLayout layout_otherPhotos;
    private Button btn_finish;
    private Bitmap profileBitmap, oneBitmap, twoBitmpa, threeBitmap, fourBitmap, fiveBitmap, sixBitmap, sevenBitmap, eightBitmap, nineBitmap, tenBitmap;
    private String profileImage = "";
    private String oneImage = "";
    private String twoImage = "";
    private String threeImage = "";
    private String fourImage = "";
    private String fiveImage = "";
    private String sixImage = "";
    private String sevenImage = "";
    private String eightImage = "";
    private String nineImage = "";
    private String tenImage = "";
    private int memberId;
    private int flag = 0;
    private SharedPreferences mPreferences;
    private ProgressBar Upload_progressBar;
    private Uri mCropImageUri;
    private static final int PERMS_REQUEST_CODE = 124;
    private String accountCreatedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        img_profile = (ImageView) findViewById(R.id.img_profilePicture);
        img_one = (ImageView) findViewById(R.id.img_OhterPictureOne);
        img_two = (ImageView) findViewById(R.id.img_OhterPictureTwo);
        img_three = (ImageView) findViewById(R.id.img_OhterPictureThree);
        img_four = (ImageView) findViewById(R.id.img_OhterPictureFour);
        img_five = (ImageView) findViewById(R.id.img_OhterPictureFive);
        img_six = (ImageView) findViewById(R.id.img_OhterPictureSix);
        img_seven = (ImageView) findViewById(R.id.img_OhterPictureSeven);
        img_eight = (ImageView) findViewById(R.id.img_OhterPictureEight);
        img_nine = (ImageView) findViewById(R.id.img_OhterPictureNine);
        img_ten = (ImageView) findViewById(R.id.img_OhterPictureTen);
        clik_img_profile = (ImageView) findViewById(R.id.img_addProfileImage);
        clik_img_one = (ImageView) findViewById(R.id.img_addOneImage);
        clik_img_two = (ImageView) findViewById(R.id.img_addTwoImage);
        clik_img_three = (ImageView) findViewById(R.id.img_addThreeImage);
        clik_img_four = (ImageView) findViewById(R.id.img_addFourImage);
        clik_img_five = (ImageView) findViewById(R.id.img_addFiveImage);
        clik_img_six = (ImageView) findViewById(R.id.img_addSixImage);
        clik_img_seven = (ImageView) findViewById(R.id.img_addSevenImage);
        clik_img_eight = (ImageView) findViewById(R.id.img_addEightImage);
        clik_img_nine = (ImageView) findViewById(R.id.img_addNineImage);
        clik_img_ten = (ImageView) findViewById(R.id.img_addTenImage);
        btn_finish = (Button) findViewById(R.id.btn_matroFinish);
        layout_otherPhotos = (LinearLayout) findViewById(R.id.layout_uploadOtherPhtos);
        Upload_progressBar = (ProgressBar) findViewById(R.id.Upload_progressBar);

        selectShowToSpinner(R.id.main_pic_spinner, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                mainPhotoShowTo = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner1, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo1 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner2, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo2 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner3, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo3 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner4, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo4 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner5, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo5 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner6, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo6 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner7, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo7 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner8, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo8 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner9, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo9 = (int) selectedObject;
            }
        });

        selectShowToSpinner(R.id.show_to_spinner10, new RadioSelectionListener() {
            @Override
            public void onRadioSelected(Object selectedObject) {
                photoShowTo10 = (int) selectedObject;
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_upload);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Upload Photos");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreferences.getInt("memberId", 0);
        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");

        if (accountCreatedBy.equals("P")) {

            img_profile.setVisibility(View.VISIBLE);
            layout_otherPhotos.setVisibility(View.GONE);

        } else if (accountCreatedBy.equals("C")) {

            layout_otherPhotos.setVisibility(View.VISIBLE);
        }

        loadImages(memberId);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadPhotosTask();
            }
        });


        clik_img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 1;
                    if (profileBitmap != null) {
                        profileBitmap.recycle();
                        profileBitmap = null;
                        onSelectImageClick(v);
                    } else {

                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 2;

                    if (oneBitmap != null) {
                        oneBitmap.recycle();
                        oneBitmap = null;
                        onSelectImageClick(v);
                    } else {

                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 3;

                    if (twoBitmpa != null) {
                        twoBitmpa.recycle();
                        twoBitmpa = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }

                } else {

                    requestPermission();
                }


            }
        });

        clik_img_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 4;

                    if (threeBitmap != null) {
                        threeBitmap.recycle();
                        threeBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 5;

                    if (fourBitmap != null) {
                        fourBitmap.recycle();
                        fourBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 6;

                    if (fiveBitmap != null) {
                        fiveBitmap.recycle();
                        fiveBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 7;

                    if (sixBitmap != null) {
                        sixBitmap.recycle();
                        sixBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });


        clik_img_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 8;

                    if (sevenBitmap != null) {
                        sevenBitmap.recycle();
                        sevenBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 9;

                    if (eightBitmap != null) {
                        eightBitmap.recycle();
                        eightBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 10;

                    if (nineBitmap != null) {
                        nineBitmap.recycle();
                        nineBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });

        clik_img_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {

                    flag = 11;

                    if (tenBitmap != null) {
                        tenBitmap.recycle();
                        tenBitmap = null;
                        onSelectImageClick(v);
                    } else {
                        onSelectImageClick(v);
                    }
                } else {

                    requestPermission();
                }


            }
        });
    }

    private void selectShowToSpinner(int id, final RadioSelectionListener radioSelectionListener) {
        Spinner spinner = findViewById(id);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                radioSelectionListener.onRadioSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                .setAspectRatio(1, 1)
                .start(this);
    }

    private void startCropImageActivity1(Uri imageUri) {
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

                if (flag == 1) {

                    startCropImageActivity(imageUri);
                } else {

                    startCropImageActivity1(imageUri);
                }


            }

        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri path = result.getUri();

                if (flag == 1) {

                    img_profile.setImageURI(result.getUri());
                    try {

                        profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 2) {

                    img_one.setImageURI(result.getUri());
                    try {
                        oneBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 3) {

                    img_two.setImageURI(result.getUri());
                    try {
                        twoBitmpa = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 4) {

                    img_three.setImageURI(result.getUri());
                    try {
                        threeBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 5) {

                    img_four.setImageURI(result.getUri());
                    try {
                        fourBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 6) {

                    img_five.setImageURI(result.getUri());
                    try {
                        fiveBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 7) {

                    img_six.setImageURI(result.getUri());
                    try {
                        sixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 8) {

                    img_seven.setImageURI(result.getUri());
                    try {
                        sevenBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 9) {

                    img_eight.setImageURI(result.getUri());
                    try {
                        eightBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 10) {

                    img_nine.setImageURI(result.getUri());
                    try {
                        nineBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
                }

                if (flag == 11) {

                    img_ten.setImageURI(result.getUri());
                    try {
                        tenBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

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

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startCropImageActivity(mCropImageUri);
        } else {

            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }

    }*/


    private void loadImages(final int mId) {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.IMEAGES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mDialog.dismiss();

                        Log.d(TAG, "images url response" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                String profileUlr = "", photoOneUrl = "", photoTwoUrl = "", photoThreeUrl = "";

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    profileUlr = object.getString("MainProfilePhoto");
                                    photoOneUrl = object.getString("MemberPhoto1");
                                    photoTwoUrl = object.getString("MemberPhoto2");
                                    photoThreeUrl = object.getString("MemberPhoto3");

                                }

                                if (profileUlr.length() > 10 && !TextUtils.isEmpty(profileUlr)) {

                                    Picasso.get()
                                            .load(profileUlr)
                                            .into(img_profile);
                                }

                                if (photoOneUrl.length() > 10 && !TextUtils.isEmpty(photoOneUrl)) {

                                    Picasso.get()
                                            .load(photoOneUrl)
                                            .into(img_one);
                                }

                                if (photoTwoUrl.length() > 10 && !TextUtils.isEmpty(photoTwoUrl)) {

                                    Picasso.get()
                                            .load(photoTwoUrl)
                                            .into(img_two);
                                }

                                if (photoThreeUrl.length() > 10 && !TextUtils.isEmpty(photoThreeUrl)) {

                                    Picasso.get()
                                            .load(photoThreeUrl)
                                            .into(img_three);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "photo url error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(mId));

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void uploadPhotosTask() {

        if (profileBitmap != null) {

            profileImage = getStringImage(profileBitmap);
        }

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

        if (fiveBitmap != null) {

            fiveImage = getStringImage(fiveBitmap);
        }
        if (sixBitmap != null) {

            sixImage = getStringImage(sixBitmap);
        }
        if (sevenBitmap != null) {

            sevenImage = getStringImage(sevenBitmap);
        }
        if (eightBitmap != null) {

            eightImage = getStringImage(eightBitmap);
        }
        if (nineBitmap != null) {

            nineImage = getStringImage(nineBitmap);
        }

        if (tenBitmap != null) {

            tenImage = getStringImage(tenBitmap);
        }

        Upload_progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.uploadImagesUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Upload_progressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "MatroRegResponse :" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {

                                mPreferences.edit().putInt("session_photos", 1).apply();

                                startActivity(new Intent(UploadImagesActivity.this, EditMyProfileActivity.class));
                                profileImage = "";
                                oneImage = "";
                                twoImage = "";
                                threeImage = "";
                                fourImage = "";
                                fiveImage = "";
                                sixImage = "";
                                sevenImage = "";
                                eightImage = "";
                                nineImage = "";
                                tenImage = "";

                                Toast.makeText(UploadImagesActivity.this, "uploaded successfully...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "MatroRegError :" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(MEMBER_ID, String.valueOf(memberId));
                params.put(PROFILE_PHOTO, profileImage);
                params.put(PROFILE_EXTENSION, ".jpg");

                params.put(MAINPHOTO_SHOWTO, "" + mainPhotoShowTo);
                params.put(MAINPHOTO_STATUS, "1");

                params.put(PHOTO_ONE, oneImage);
                params.put(PHOTO_ONE_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO1, "" + photoShowTo1);
                params.put(PHOTO_STATUS1, "1");

                params.put(PHOTO_TWO, twoImage);
                params.put(PHOTO_TWO_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO2, "" + photoShowTo2);
                params.put(PHOTO_STATUS2, "1");

                params.put(PHOTO_THREE, threeImage);
                params.put(PHOTO_THREE_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO3, "" + photoShowTo3);
                params.put(PHOTO_STATUS3, "1");

                params.put(PHOTO_FOUR, fourImage);
                params.put(PHOTO_FOUR_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO4, "" + photoShowTo4);
                params.put(PHOTO_STATUS4, "1");

                params.put(PHOTO_FIVE, fiveImage);
                params.put(PHOTO_FIVE_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO5, "" + photoShowTo5);
                params.put(PHOTO_STATUS5, "1");

                params.put(PHOTO_SIX, sixImage);
                params.put(PHOTO_SIX_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO6, "" + photoShowTo6);
                params.put(PHOTO_STATUS6, "1");

                params.put(PHOTO_SEVEN, sevenImage);
                params.put(PHOTO_SEVEN_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO7, "" + photoShowTo7);
                params.put(PHOTO_STATUS7, "1");

                params.put(PHOTO_EIGHT, eightImage);
                params.put(PHOTO_EIGHT_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO8, "" + photoShowTo8);
                params.put(PHOTO_STATUS8, "1");

                params.put(PHOTO_NINE, nineImage);
                params.put(PHOTO_NINE_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO9, "" + photoShowTo9);
                params.put(PHOTO_STATUS9, "1");

                params.put(PHOTO_TEN, tenImage);
                params.put(PHOTO_TEN_EXTENSION, ".jpg");
                params.put(PHOTO_SHOWTO10, "" + photoShowTo10);
                params.put(PHOTO_STATUS10, "1");

                params.put(PHOTO_PROFILE_PERCENTAGE, "10");

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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

            // showFileChooser(PICK_IMAGE_REQUEST_PROFILE_PICK);

            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (flag == 1) {

                    startCropImageActivity(mCropImageUri);
                } else {

                    startCropImageActivity1(mCropImageUri);
                }

            }

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if ((shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))) {

                    Toast.makeText(this, "Phone state permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(UploadImagesActivity.this, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(UploadImagesActivity.this, EditMyProfileActivity.class));
//    }
}