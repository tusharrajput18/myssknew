package in.co.vsys.myssksamaj;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.businessAdapter.BusinessCityAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessCountryAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessStateAdapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.businessmodels.SellectAllCity;
import in.co.vsys.myssksamaj.businessmodels.SellectAllCountry;
import in.co.vsys.myssksamaj.businessmodels.SellectAllState;
import in.co.vsys.myssksamaj.mainMobileModel.RegistrationModel;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommonRegisterActivity extends AppCompatActivity {

    TextInputEditText et_common_first_name,et_common_middle_name,et_common_reister_last_name,et_common_mobile_no,et_common_email,et_common_address;
    EditText et_common_password,et_common_confirm_password;
    TextView tv_common_country,tv_common_state,tv_common_city;
    ImageView common_register_adhar,common_register_profile;
    TextView btn_common_register_insert;
    private ProgressBar con_progressbar;

    String fname,mname,lname,mobile,emailid,address,password,commonpassword;
    String contryid="",stateid="",cityid="";
    private String IMAGE_1 = "";
    private String IMAGE_2 = "";
    int flag = 0;
    private static final int PERMS_REQUEST_CODE = 124;
    private Uri mCropImageUri;
    private Bitmap bitmap,bitmap1;

    private SharedPreferences mPreference;




    ArrayList<SellectAllCountry.AllCountryList> arrayList;
    ArrayList<SellectAllState.AllStateList> stateLists_arraylist;
    ArrayList<SellectAllCity.AllCityList> allCityLists_arraylist;
    BusinessCountryAdapter businessCountryAdapter;
    BusinessStateAdapter businessStateAdapter;
    BusinessCityAdapter businessCityAdapter;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_register);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        mToolbar=(Toolbar)findViewById(R.id.common_regis_toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Register");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CommonRegisterActivity.this, CommonLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void init() {
        arrayList = new ArrayList<>();
        stateLists_arraylist = new ArrayList<>();
        allCityLists_arraylist = new ArrayList<>();

        et_common_first_name=(TextInputEditText)findViewById(R.id.et_common_first_name);
        et_common_middle_name=(TextInputEditText)findViewById(R.id.et_common_middle_name);
        et_common_reister_last_name=(TextInputEditText)findViewById(R.id.et_common_reister_last_name);
        et_common_mobile_no=(TextInputEditText)findViewById(R.id.et_common_mobile_no);
        et_common_email=(TextInputEditText)findViewById(R.id.et_common_email);
        et_common_address=(TextInputEditText)findViewById(R.id.et_common_address);

        et_common_password=(EditText) findViewById(R.id.et_common_password);
        et_common_confirm_password=(EditText) findViewById(R.id.et_common_confirm_password);

        common_register_adhar=(ImageView) findViewById(R.id.common_register_adhar);
        common_register_profile=(ImageView) findViewById(R.id.common_register_profile);

        btn_common_register_insert=(TextView) findViewById(R.id.btn_common_register_insert);

        tv_common_country=(TextView) findViewById(R.id.tv_common_country);
        tv_common_state=(TextView) findViewById(R.id.tv_common_state);
        tv_common_city=(TextView) findViewById(R.id.tv_common_city);

        con_progressbar=(ProgressBar)findViewById(R.id.common_register_progress);

        tv_common_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadBusinescountry();

            }
        });

        tv_common_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadBusinesState();
            }
        });

        tv_common_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(stateid.isEmpty()){
                    Toast.makeText(CommonRegisterActivity.this, "Sellect State", Toast.LENGTH_SHORT).show();
                }else {
                    loadBusinesCity(stateid);
                }

            }
        });


        btn_common_register_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bitmap != null) {

                    IMAGE_1 = getStringImage(bitmap);
                }

                if (bitmap1 != null) {
                    IMAGE_2 = getStringImage(bitmap1);
                }

                fname=et_common_first_name.getText().toString();
                mname=et_common_middle_name.getText().toString();
                lname=et_common_middle_name.getText().toString();
                mobile=et_common_mobile_no.getText().toString();
                emailid=et_common_email.getText().toString();
                address=et_common_address.getText().toString();
                password=et_common_password.getText().toString();


                if(isValidate1()){

                    registerUser(fname,mname,lname,mobile,emailid,address,password,contryid,stateid,cityid);

                }



                Log.d("mytag", "onClick: "+IMAGE_1);
                Log.d("mytag", "onClick "+IMAGE_2);

            }
        });

        common_register_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = 1;

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
        });

        common_register_adhar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag = 2;

                if (hasPermission()) {

                    if (bitmap1 != null) {

                        bitmap1.recycle();
                        bitmap1 = null;

                        onSelectImageClick(view);

                    } else {

                        onSelectImageClick(view);
                    }

                } else {

                    requestPermission();
                }

            }
        });
    }

    private void registerUser(String fname, String mname, String lname, String mobile, String emailid, String address, String password, String contryid, String stateid, String cityid) {
        //Toast.makeText(this, ""+fname+"\n"+lname+"\n"+mname+"\n"+mobile+"\n"+emailid+"\n"+address+"\n"+password+"\n"+contryid+"\n"+stateid+"\n"+cityid, Toast.LENGTH_SHORT).show();
        Log.d("mytag", "registerUser: "+IMAGE_1);
        Log.d("MYTAG", "registerUser: "+fname+"\n"+lname+"\n"+mname+"\n"+mobile+"\n"+emailid+"\n"+address+"\n"+password+"\n"+contryid+"\n"+stateid+"\n"+cityid);
        Log.d("mytag", "registerUser: "+IMAGE_2);
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<RegistrationModel> call = service.insertCommonRegister(fname,mname,lname,mobile,emailid,password,IMAGE_1,"jpg",IMAGE_2,"jpg",address,contryid,stateid,cityid);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                        mPreference.edit().putString("common_fname", response.body().getRegistrationDetailsModels().get(0).getFname()).apply();
                        mPreference.edit().putString("common_mname", response.body().getRegistrationDetailsModels().get(0).getMname()).apply();
                        mPreference.edit().putString("common_lname", response.body().getRegistrationDetailsModels().get(0).getLname()).apply();
                        mPreference.edit().putString("common_mobileNo", response.body().getRegistrationDetailsModels().get(0).getMobile()).apply();
                        mPreference.edit().putString("App_common_userId", response.body().getRegistrationDetailsModels().get(0).getApploginid()).apply();
                        mPreference.edit().putString("App_userprofile_img", response.body().getRegistrationDetailsModels().get(0).getPhoto()).apply();
                        mPreference.edit().putString("App_useradhar_img", response.body().getRegistrationDetailsModels().get(0).getAadhar()).apply();
                        mPreference.edit().putString("otp",response.body().getRegistrationDetailsModels().get(0).getOtpNo() ).apply();

                        Intent intent=new Intent(CommonRegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(CommonRegisterActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                       // startActivity(new Intent(CommonRegisterActivity.this,CommonLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                } else {
                    Log.d("mytag", "onResponse: "+response.message());
                    Log.d("mytag", "onResponse: "+response.errorBody());
                    Toast.makeText(CommonRegisterActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(CommonRegisterActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private boolean isValidate1() {
        if (sT(et_common_first_name).length() < 3) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sT(et_common_middle_name).length() < 3) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Middle Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sT(et_common_reister_last_name).length() < 3) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Last Name.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sT(et_common_mobile_no).length() < 10) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Mobile No.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sT(et_common_email).length() < 3) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Email.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (contryid.isEmpty()) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Country.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (stateid.isEmpty()) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid State.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (cityid.isEmpty()) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid City.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sT(et_common_address).length() < 3) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_common_password.getText().toString().length() < 3) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Password.", Toast.LENGTH_LONG).show();
            return false;
        }
       /* if (et_common_confirm_password.getText().toString().length() < 3 ) {
            Toast.makeText(CommonRegisterActivity.this, "Invalid Password.", Toast.LENGTH_LONG).show();*/
            if(!et_common_confirm_password.getText().toString().equals(et_common_password.getText().toString())){
                Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
                return false;
          /*  }
            return false;*/
        }
        if(IMAGE_1.length()<1){
            Toast.makeText(this, "Invalid Profile Image.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(IMAGE_2.length()<1){
            Toast.makeText(this, "Invalid Id Image.", Toast.LENGTH_SHORT).show();
            return false;
        }

            return true;

    }


    private String sT(TextInputEditText editText) {
        return "" + editText.getText();
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
                .setGuidelines(CropImageView.Guidelines.OFF)
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

                if (flag == 1) {
                    common_register_profile.setImageURI(result.getUri());
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (flag == 2)
                {
                    common_register_adhar.setImageURI(result.getUri());
                    try {

                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();

            }
        }

    }

    private void loadBusinescountry() {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllCountry> call = service.getAllCountry();
        call.enqueue(new Callback<SellectAllCountry>() {
            @Override
            public void onResponse(Call<SellectAllCountry> call, Response<SellectAllCountry> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    arrayList = response.body().getAllCountryLists();
                    if (arrayList.size() > 0) {
                        businessCountryAdapter = new BusinessCountryAdapter(CommonRegisterActivity.this, arrayList);
                        displayCountryAlert();

                    } else {
                        Toast.makeText(CommonRegisterActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CommonRegisterActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllCountry> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(CommonRegisterActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayCountryAlert() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CommonRegisterActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);
        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessCountryAdapter);
        businessCountryAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllCountry.AllCountryList countryModel = (SellectAllCountry.AllCountryList) parent.getItemAtPosition(position);
                contryid = countryModel.getId();
                tv_common_country.setText(countryModel.getCountryname());
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
                businessCountryAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void loadBusinesState() {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllState> call = service.getAllState();
        call.enqueue(new Callback<SellectAllState>() {
            @Override
            public void onResponse(Call<SellectAllState> call, Response<SellectAllState> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    stateLists_arraylist = response.body().getAllStateLists();
                    if (stateLists_arraylist.size() > 0) {

                        businessStateAdapter = new BusinessStateAdapter(CommonRegisterActivity.this, stateLists_arraylist);
                        displayStateAlert();
                    } else {
                        Toast.makeText(CommonRegisterActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CommonRegisterActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllState> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(CommonRegisterActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayStateAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CommonRegisterActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessStateAdapter);
        businessStateAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllState.AllStateList stateModel = (SellectAllState.AllStateList) parent.getItemAtPosition(position);
                stateid = stateModel.getId();
                tv_common_state.setText(stateModel.getStatename());
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
                businessStateAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void loadBusinesCity(String stateid) {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllCity> call = service.getallCity(stateid);
        call.enqueue(new Callback<SellectAllCity>() {
            @Override
            public void onResponse(Call<SellectAllCity> call, Response<SellectAllCity> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    allCityLists_arraylist = response.body().getAllCityLists();
                    if (allCityLists_arraylist.size() > 0) {

                        businessCityAdapter = new BusinessCityAdapter(CommonRegisterActivity.this, allCityLists_arraylist);
                        displayCityAlert();
                    } else {
                        Toast.makeText(CommonRegisterActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CommonRegisterActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllCity> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(CommonRegisterActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayCityAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CommonRegisterActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessCityAdapter);
        businessCityAdapter.notifyDataSetChanged();
        final AlertDialog dialog = builder.create();
        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllCity.AllCityList cityModel = (SellectAllCity.AllCityList) parent.getItemAtPosition(position);

                cityid = cityModel.getId();
                tv_common_city.setText(cityModel.getCityname());

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
                businessCityAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}
