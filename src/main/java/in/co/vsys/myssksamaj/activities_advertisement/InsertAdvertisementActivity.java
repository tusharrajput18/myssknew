package in.co.vsys.myssksamaj.activities_advertisement;

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

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapterAdvertisement.AdvertisementTypeAdapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisemtnTypeList;
import in.co.vsys.myssksamaj.modelAdvertisement.ModelAdvertisementType;
import in.co.vsys.myssksamaj.network1.AdvertisementAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertAdvertisementActivity extends AppCompatActivity {

    ArrayList<AdvertisemtnTypeList> advertisemtnTypeLists;


    Toolbar toolbar_insertAdvertisement;
    private String IMAGE_1 = "";
    private String IMAGE_2 = "";
    int flag = 0;
    private static final int PERMS_REQUEST_CODE = 124;
    private Uri mCropImageUri;
    private Bitmap bitmap,bitmap1;

    TextView tv_insert_adds,tv_advertisement_type;
    TextInputEditText tv_adds_short_discription,tv_adds_longdiscription;
    ImageView adds_img_1,adds_img_2;
    ProgressBar insert_adds_progress;

    AdvertisementTypeAdapter advertisementTypeAdapter;
    String addtypeid="";
    String userid;

    private SharedPreferences mPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_advertisement);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        toolbar_insertAdvertisement=(Toolbar)findViewById(R.id.toolbar_insertAdvertisement);
        setSupportActionBar(toolbar_insertAdvertisement);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Insert Advertisement");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(InsertAdvertisementActivity.this, AdvertisementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void init() {

   userid=mPreference.getString("App_common_userId","");

        advertisemtnTypeLists=new ArrayList<>();
        insert_adds_progress=(ProgressBar) findViewById(R.id.insert_adds_progress);

        tv_insert_adds=(TextView)findViewById(R.id.tv_insert_adds);
        tv_advertisement_type=(TextView)findViewById(R.id.tv_advertisement_type);

        tv_adds_short_discription=(TextInputEditText)findViewById(R.id.tv_adds_short_discription);
        tv_adds_longdiscription=(TextInputEditText)findViewById(R.id.tv_adds_longdiscription);

        adds_img_1=(ImageView) findViewById(R.id.adds_img_1);
        adds_img_2=(ImageView) findViewById(R.id.adds_img_2);


        tv_insert_adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bitmap != null) {

                    IMAGE_1 = getStringImage(bitmap);
                }

                if (bitmap1 != null) {
                    IMAGE_2 = getStringImage(bitmap1);
                }

                if(isValidate1()){
                    String shortdisc=tv_adds_short_discription.getText().toString();
                    String longdisc=tv_adds_longdiscription.getText().toString();
                    insertAdvertisement(userid,addtypeid,shortdisc,longdisc);
                }
            }
        });

        tv_advertisement_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAllAdvertisemtnType();
            }
        });


        adds_img_1.setOnClickListener(new View.OnClickListener() {
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

        adds_img_2.setOnClickListener(new View.OnClickListener() {

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


    private boolean isValidate1() {

        if (addtypeid.isEmpty()) {
            Toast.makeText(InsertAdvertisementActivity.this, "Invalid Advertisement Type.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (tv_adds_short_discription.getText().toString().length() < 3) {
            Toast.makeText(InsertAdvertisementActivity.this, "Invalid Text.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (tv_adds_longdiscription.getText().toString().length() < 3) {
            Toast.makeText(InsertAdvertisementActivity.this, "Invalid Text.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(IMAGE_1.length()<1){
            Toast.makeText(InsertAdvertisementActivity.this, "Invalid Image.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(IMAGE_2.length()<1){
            Toast.makeText(InsertAdvertisementActivity.this, "Invalid Image.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    private void insertAdvertisement( String userid,String addtypeid, String shortdisc, String longdisc) {
        insert_adds_progress.setVisibility(View.VISIBLE);
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertAdvertisement(userid,addtypeid,shortdisc,longdisc,"",IMAGE_1,"jpg",IMAGE_2,"jpg");
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                insert_adds_progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()==1){
                        Toast.makeText(InsertAdvertisementActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(InsertAdvertisementActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(InsertAdvertisementActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                insert_adds_progress.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(InsertAdvertisementActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void selectAllAdvertisemtnType() {
        insert_adds_progress.setVisibility(View.VISIBLE);
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<ModelAdvertisementType> call = service.getAllAdvertisementType();
        call.enqueue(new Callback<ModelAdvertisementType>() {
            @Override
            public void onResponse(Call<ModelAdvertisementType> call, Response<ModelAdvertisementType> response) {
                insert_adds_progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    advertisemtnTypeLists = response.body().getAdvertisemtnTypeLists();
                    if (advertisemtnTypeLists.size() > 0) {
                        advertisementTypeAdapter = new AdvertisementTypeAdapter(InsertAdvertisementActivity.this, advertisemtnTypeLists);
                        displayAdvertisementypeAlert();

                    } else {
                        Toast.makeText(InsertAdvertisementActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InsertAdvertisementActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelAdvertisementType> call, Throwable t) {
                insert_adds_progress.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(InsertAdvertisementActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void displayAdvertisementypeAlert() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(InsertAdvertisementActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);
        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(advertisementTypeAdapter);
        advertisementTypeAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AdvertisemtnTypeList Model = (AdvertisemtnTypeList) parent.getItemAtPosition(position);
                addtypeid = Model.getAdvertisementTypeId();
                tv_advertisement_type.setText(Model.getAdvertisementType());
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
                advertisementTypeAdapter.getFilter().filter(newText);
                return false;
            }
        });
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
                    adds_img_1.setImageURI(result.getUri());
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                        flag = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (flag == 2)
                {
                    adds_img_2.setImageURI(result.getUri());
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

}
