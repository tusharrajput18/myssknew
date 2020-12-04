package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import in.co.vsys.myssksamaj.CommonRegisterActivity;
import in.co.vsys.myssksamaj.PrivacyActivity;
import in.co.vsys.myssksamaj.R;

import in.co.vsys.myssksamaj.activities_advertisement.AdvertisementActivity;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.adapter.BusinessSliderAdapter;
import in.co.vsys.myssksamaj.adapter.GridDashboardAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessStateAdapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.CommonUserModel;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.businessmodels.SellectAllState;
import in.co.vsys.myssksamaj.mainMobileModel.OtpModel;
import in.co.vsys.myssksamaj.maindirectory.LoginOptionsActivity;
import in.co.vsys.myssksamaj.maindirectory.MainMobileNumberActivity;
import in.co.vsys.myssksamaj.maindirectory.OtpVerificationActivity;
import in.co.vsys.myssksamaj.model.SliderModel;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.UpdateApp;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity
//        implements TransactionDataContract.TransactionView
{

    private Context mContext;
    private android.app.AlertDialog alertDialog;
    private Dialog dialog;
    private static final int READ_PERMISSION = 1;
    private static String[] PERMISSIONS = {
            READ_CONTACTS,
            WRITE_CONTACTS,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE,
            READ_PHONE_STATE,
    };

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String APP_LOGIN_ID = "MainAppId";
    private static long back_pressed;
    private SharedPreferences mPreferences;
    private int appLoginId, matroSucess;
    private GridView mGridView;
    private ProgressDialog mDialog;
    private static int currentPage = 0;
    private static int numOfPages = 0;
    private int guestLogin;
    private ViewPager mViewPager;
    private int session;
    private ImageView img_logout;
    private CircleIndicator indicator;
    private LinearLayout contactUsLayout;
    private static final Integer[] IMAGES = {R.drawable.ttt, R.drawable.slider_two, R.drawable.slider_bus};
    private ArrayList<Integer> imagesArray = new ArrayList<>();

    private List<SliderModel.SliderModelList> sliderModelListList;
    private HashMap<String, String> Hash_file_maps1;

    private int memberId;
    private int mMemberId;
    private String mTransactionId = "", mPackageId = "1";
    //    private TransactionDataContract.TransactionOps transactionDataPresenter;
    LinearLayout tc_layout;
    LinearLayout aboutUs_layout;
    private String image, title;

    UpdateApp updateApp = new UpdateApp(this);
    String mainappid = "";
    String deviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        updateApp.getAppUpdatedVersionNumber();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_mainlogin);


        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        indicator = (CircleIndicator) findViewById(R.id.circleIndictorMain);
        mGridView = (GridView) findViewById(R.id.main_menuGridView);
        // img_logout = (ImageView) findViewById(R.id.img_logout);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        appLoginId = mPreferences.getInt("appLoginId", 0);
        mainappid = mPreferences.getString("App_common_userId", "");
        contactUsLayout = (LinearLayout) findViewById(R.id.contactUs_layout);

        tc_layout = (LinearLayout) findViewById(R.id.tc_layout);
        aboutUs_layout = (LinearLayout) findViewById(R.id.aboutUs_layout);


        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dashboard");
        }

        tc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrivacyActivity.class);
                intent.putExtra("webviewtag", "privacypolicy");
                startActivity(intent);
            }
        });

        aboutUs_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrivacyActivity.class);
                intent.putExtra("webviewtag", "aboutus");
                startActivity(intent);
            }
        });


        deviceId = FirebaseInstanceId.getInstance().getToken();
        mainDeviceId(deviceId,mainappid);


        Log.d(TAG, "onCreate: " + memberId);
        Hash_file_maps1 = new HashMap<>();
        loadImages();

      /*  if(mPreferences.contains("memberId")){
            memberId = mPreferences.getInt("memberId", 0);
            insertImei();
        }*/


        Log.d(TAG, "getUniqueIMEIId: " + getUniqueIMEIId(MainActivity.this));

        mGridView.setAdapter(new GridDashboardAdapter(this));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });

        guestLogin = mPreferences.getInt("guestLogin", 0);
        session = mPreferences.getInt("appLoginId", 0);

        /*if (guestLogin == 1) {

            img_logout.setVisibility(View.GONE);
        } else {

            img_logout.setVisibility(View.VISIBLE);
        }*/

        /*img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutOperation();
            }
        });*/

        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PrivacyActivity.class);
                intent.putExtra("webviewtag", "contactus");
                startActivity(intent);
                //displayContactUs();
            }
        });

//        transactionDataPresenter = new TransactionPresenter(this);
//        transactionDataPresenter.getTransactionData("" + mMemberId, "1");
        if (isPermissionGranted()) {
            Utilities.addContact(this);
        }
    }

    private void shareApplication() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
//            String sAux = getResources().getString(R.string.app_name)+"\n\n";
            String sAux = "My SSK Samaj." + "\n\n";
//            sAux = sAux + "https://play.google.com/store/apps/details?id=in.co.vsys.krushisangam";
            sAux = sAux + "https://play.google.com/store/apps/details?id=in.co.vsys.myssksamaj" +
                    "";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
       /* MenuItem search = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setIconifiedByDefault(true);

        search(searchView);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mainactivity_share:

                shareApplication();

                //Toast.makeText(mContext, "this is share", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(ActivityProductList.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                return true;
            case R.id.mainactivity_notification:

                Toast.makeText(mContext, "this is notification", Toast.LENGTH_SHORT).show();
               /* Intent cartIntent = new Intent(ActivityProductList.this, ActivityViewOrder.class);
                cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cartIntent);*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            String phone = telephonyManager.getLine1Number();
            Log.d(TAG, "getUniqueIMEIId: " + phone);
            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }


    private void logoutOperation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("MySSK Samaj");
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(MainActivity.this, MainLoginActivity.class);
                mPreferences.edit().clear().apply();
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void displayView(int position) {

        switch (position) {

            case 0:
                startActivity(new Intent(MainActivity.this, NewsDashboardActivity.class));
                // startActivity(new Intent(MainActivity.this, NewsListActivity.class));
                break;

            case 1:
                startActivity(new Intent(MainActivity.this, AdvertisementActivity.class));
                break;

            case 2:
                startActivity(new Intent(MainActivity.this, LoginOptionsActivity.class));
                finish();
                break;

            case 3:
                startActivity(new Intent(MainActivity.this, BusinessDashboardActivity.class));
                break;

            case 4:
                startActivity(new Intent(MainActivity.this, AdvtListActivity.class));
                //  startActivity(new Intent(MainActivity.this, AdvertisementActivity.class));
                break;

            case 5:
                startActivity(new Intent(MainActivity.this, DonationActivity.class));
                break;

            default:
                new MainActivity();

        }
    }

    private void checkAvailable() {

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Please wait...");
        mDialog.setMessage("loading...");
        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.checkMatromonyUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mDialog.dismiss();
                        Log.d(TAG, "check availability :" + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            matroSucess = jsonObject.getInt("success");

                            if (matroSucess == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    mMemberId = object.getInt("MemberId");
                                    mPreferences.edit().putInt("memberId", mMemberId).apply();
                                }

                                startActivity(new Intent(MainActivity.this, MatrimonyHomeActivity.class));

                            } else {

                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "available error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(APP_LOGIN_ID, String.valueOf(appLoginId));
                System.out.println("appLoginId" + appLoginId);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void insertImei() {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "Please wait ......");
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertLoginusercount(String.valueOf(memberId), getUniqueIMEIId(MainActivity.this));
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, retrofit2.Response<JsonResult> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        Toast.makeText(MainActivity.this, "Loaded Successfull", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void mainDeviceId(String dev_id,String user_id) {
        Log.d(TAG, "mainDeviceId: "+dev_id+" "+user_id);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertDeviceId(user_id,dev_id);
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, retrofit2.Response<JsonResult> response) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Device Id Updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("mytag", "onFailure: " + t.getMessage());

            }
        });

    }

    private void loadImages() {
        final ProgressDialog progressDialog;
        sliderModelListList = new ArrayList<>();
        progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "Please wait ......");
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<SliderModel> call = service.getAllSliderImages();
        call.enqueue(new Callback<SliderModel>() {
            @Override
            public void onResponse(Call<SliderModel> call, retrofit2.Response<SliderModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        sliderModelListList = response.body().getSliderModelLists();
                        // imageSlider(sliderModelListList);


                        Log.d("mytag", "onResponse: " + sliderModelListList.size());
                        mViewPager.setAdapter(new BusinessSliderAdapter(MainActivity.this, sliderModelListList));
                        indicator.setViewPager(mViewPager);
                        //  mIndicator.setRadius(5 * density);

                        numOfPages = sliderModelListList.size();

                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {

                                if (currentPage == numOfPages) {
                                    currentPage = 0;
                                }
                                mViewPager.setCurrentItem(currentPage++, true);
                            }
                        };

                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 3000, 3000);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SliderModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


    @Override
    public void onBackPressed() {


        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        }
    }

    private void displayContactUs() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.row_contact_us_layout, null);

        AppCompatTextView imgWhats = (AppCompatTextView) view.findViewById(R.id.tv_contactUsWhats);

        builder.setView(view);


        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });


        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        imgWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // openWhatsApp();

                String number = "+919923449493";
                try {
                    number = number.replace(" ", "").replace("+", "");

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Log.e("ERROR_OPEN_MESSANGER", e.toString());
                }

                alertDialog.dismiss();
                /*String number = "9923449493";

                Uri uri = Uri.parse("smsto:" + number);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(i);*/
            }
        });


    }

//    @Override
//    public void showTransactionData(String transactionId, String amount) {
//
//    }
//
//    @Override
//    public void showTransactionUpdate(String message) {
//
//    }

//    @Override
//    public void showLoading() {
//        if (dialog != null) {
//            if (dialog.isShowing())
//                dialog.dismiss();
//        }
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (dialog == null) {
//                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
//                    //View view = getLayoutInflater().inflate(R.layout.progress);
//                    builder.setView(R.layout.progress);
//                    dialog = builder.create();
//                }
//                try {
//                    dialog.show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void hideLoading() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (dialog != null && dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void showError(String message) {
//        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * NOTE: Payment SDK implementation and methods from here onwards:
//     */
//
//    @Override
//    public void showTransactionData(String transactionId, String amount) {
//        try {
//            mTransactionId = transactionId;
//            float fAmount = Float.parseFloat(amount);
//
//            String memberId = "" + MainPresenter.getInstance().getMemberId(mContext);
//            String memberName = MainPresenter.getInstance().getMemberName(mContext);
//            String emailId = MainPresenter.getInstance().getEmailId(mContext);
//            String phone = MainPresenter.getInstance().getPhone(mContext);
//            String productInfo = "Yearly subscription";
//
//            initiatePayment(mContext, memberId, transactionId, fAmount, productInfo, memberName, emailId, phone);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // When payment is done through easebuzz then invoke this method.
//
//    // This will set SharedPreferences and update the drawer view with appropriate "Paid/Premium member"
//    private void successfulTransactionCompletion() {
//        try {
//            mPreferences.edit().putInt("userType", 1).apply();
//            MainPresenter.getInstance().setUserType(mContext, 1);
//            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void transactionFailure() {
//        try {
//            mPreferences.edit().putInt("userType", 0).apply();
//            MainPresenter.getInstance().setUserType(mContext, 0);
//            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void showTransactionUpdate(String message) {
////        mDrawerLayout.closeDrawer(GravityCompat.START);
//
////        displayNavText(navigationView);
//    }
//
//    public void initiatePayment(Context context, String memberId, String transactionId, float amount,
//                                String productInfo, String firstName, String emailId,
//                                String phone) {
//        try {
//            Intent intent = new Intent(context, PWECouponsActivity.class);
//
//            intent.putExtra("trxn_id", transactionId);
//            intent.putExtra("trxn_amount", amount);
//            intent.putExtra("trxn_prod_info", productInfo);
//            intent.putExtra("trxn_firstname", firstName);
//            intent.putExtra("trxn_email_id", emailId);
//            intent.putExtra("trxn_phone", phone);
//            intent.putExtra("trxn_key", PaymentHelper.MerchantKey);
//            intent.putExtra("trxn_is_coupon_enabled", PaymentHelper.merchant_is_coupon_enabled);
//            intent.putExtra("trxn_salt", PaymentHelper.Salt);
//            intent.putExtra("unique_id", memberId);
//            intent.putExtra("enable_save_card", 1);
//            intent.putExtra("pay_mode", PaymentHelper.payment_mode);
//
//            startActivityForResult(intent, StaticDataModel.PWE_REQUEST_CODE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String result = data.getStringExtra("result");
//
//        String status;
////        result = EasebuzzConstants.PAYMENT_SUCCESSFUL;
//
//        /**
//         * Note:
//         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
//         * for other constants check the documentation of easebuzz.in here:
//         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
//         */
//        if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
//            status = "1";
//            successfulTransactionCompletion();
//        } else {
//            status = "0";
//            transactionFailure();
//        }
//
////        transactionDataPresenter.updateTransactionData("" + mMemberId, "" + mPackageId,
////                mTransactionId, status);
//    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permissionStr : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permissionStr) != PackageManager.PERMISSION_GRANTED) {
                verifyStoragePermissions();
                return false;
            }
        }
        return true;
    }

    private void verifyStoragePermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, READ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permissionStr : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(mContext, permissionStr) != PackageManager.PERMISSION_GRANTED) {
                verifyStoragePermissions();
                return;
            }
        }
        Utilities.addContact(mContext);
    }


//    public static void showPermissionDialog(Context context) {
//
//        final AlertDialog dialog = new AlertDialog.Builder(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen).create();
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setTitle("Allow permission for a smooth experience");
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Deny", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
}
