package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easebuzz.payment.kit.PWECouponsActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.fragments.ShowComingSoonFragment;
import in.co.vsys.myssksamaj.helpers.LocationHelper;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.LocationUpdateListener;
import in.co.vsys.myssksamaj.maindirectory.LoginOptionsActivity;
import in.co.vsys.myssksamaj.matrimonyfragment.HomeMatrimonyFragment;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.AddressModel;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import in.co.vsys.myssksamaj.views.MainView;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainView {

    private Context mContext;
    private static final String TAG_HOME = "nav_home_event";
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";


    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private Handler mHandler;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private SharedPreferences mPreferences;

    long back_pressed;
    private Fragment select;

    // Strings
    private String accountCreatedBy, username, uniqueId, profileUrl, mTransactionId = "", mPackageId = "1";
    public static String CURRENT_TAG = TAG_HOME;

    // Integers
    private int mMemberId, mUserType;
    public static int navItemIndex = 0;

    //    Textviews
    private TextView inviteCount, tv_uniquId, tv_name, contactViewCredits, subscriptionTypeTxt, buySubscriptionTxt;

    private Dialog dialog;

    public  static  int chat_count_number=0;
    public  static  TextView tv_chat_counter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_home);
        mContext = this;


        mToolbar = (Toolbar) findViewById(R.id.toolbar_event);
        setSupportActionBar(mToolbar);
        mHandler = new Handler();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.matroHomeDrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view_matro);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mToggle);

        mToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setItemIconTintList(null);
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;

            loadHomeFragment();
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mMemberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        if(mPreferences.contains("memberId")){
            insertImei();
        }



        accountCreatedBy = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.ACCOUNT_CREATED_BY);
        MainPresenter.getInstance().setEmailId(mContext, SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.EMAIL_ID));

        if (accountCreatedBy.equals("P")) {
            hideItems();
        }

        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);
        if(mUserType==0){
            hidemenu();
        }

        inviteCount = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_matro_invite_list));

        //initializeCountDrawer();
        displayNavText(navigationView);

        if (!Utilities.isIntentPresent(getIntent()))
            return;

        if (getIntent().getExtras().containsKey(Constants.ShareableIntents.Screen)) {
            Intent intent = new Intent(mContext, ChatListActivity.class);
            startActivity(intent);
        }


        chat_count_number=18;
        LayoutInflater li=LayoutInflater.from(HomeActivity.this);
        tv_chat_counter=(TextView)li.inflate(R.layout.counter_chat_layout,null);
        navigationView.getMenu().findItem(R.id.nav_matro_chat).setActionView(tv_chat_counter);
        showCahtCounter(chat_count_number);


    }

    public static void showCahtCounter(int countnumber){
        if(countnumber>0){
            tv_chat_counter.setText(""+countnumber);
        }else {

            tv_chat_counter.setText("");
        }
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

    private void insertImei() {
        Log.d(TAG, "insertImei: ");
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(HomeActivity.this, "Loading", "Please wait ......");
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertLoginusercount(String.valueOf(mMemberId),getUniqueIMEIId(HomeActivity.this));
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, retrofit2.Response<JsonResult> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int success = response.body().getSuccess();
                    Log.d(TAG, "onResponse: "+"insertIme");
                    if (success == 1) {
                        Toast.makeText(HomeActivity.this, "Loaded Successfull", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void initializeCountDrawer() {

        inviteCount.setGravity(Gravity.CENTER_VERTICAL);
        inviteCount.setTypeface(null, Typeface.BOLD);
        inviteCount.setTextColor(getResources().getColor(R.color.colorPrimary));
        inviteCount.setText("99");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment select) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_layout, select);
        transaction.commit();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeMatrimonyFragment();
            default:
                return new HomeMatrimonyFragment();
        }
    }

    private void loadHomeFragment() {
        selectNavMenu();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            mDrawerLayout.closeDrawers();

            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_layout, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        //Closing drawer on item click
        mDrawerLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void hideItems() {
        try {
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.nav_matro_recently_matches).setVisible(false);
            nav_menu.findItem(R.id.nav_matro_profile_visitors).setVisible(false);
            nav_menu.findItem(R.id.nav_matro_profile_views).setVisible(false);
            //  nav_menu.findItem(R.id.nav_matro_nearBy).setVisible(false);
            nav_menu.findItem(R.id.nav_matro_invite_list).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hidemenu() {
        try {
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.nav_consummed_contact).setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        displayView(item.getItemId());

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();
        return true;

    }

    private void displayView(int position) {
        // Fragment fragment = null;

        switch (position) {
            case R.id.nav_matro_home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                select = new HomeMatrimonyFragment();
                loadFragment(select);
                break;

            case R.id.nav_matro_recently_joint:
                startActivity(new Intent(mContext, ListActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_recently_matches:
                startActivity(new Intent(mContext, MatriMatchesActivity.class));
                mDrawerLayout.closeDrawers();
                break;


            case R.id.nav_matro_profile_views:
                startActivity(new Intent(mContext, ProfileVisitedListActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_shortlist:
                startActivity(new Intent(mContext, ShortlistedActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_logout:
                logoutDialog();
                // startActivity(new Intent(mContext, MainActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_search:
                startActivity(new Intent(mContext, ProfileSearchActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_my_profile:
                startActivity(new Intent(mContext, MyProfileActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_invite_list:
                startActivity(new Intent(mContext, MatriInvitationActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_nearBy:
                if(mUserType==1){
                startActivity(new Intent(mContext, MatriNearByActivity.class));
                }else {
                    displayPreimiumalert();
                }

                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_profile_visitors:
                startActivity(new Intent(mContext, ProfileVisitorListActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_chat:
                if(mUserType==1){
                    startActivity(new Intent(mContext, ChatListActivity.class));
                }else {
                    displayPreimiumalert();
                }

                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_notification:
                startActivity(new Intent(mContext, MatrimonyNotificationActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id. nav_consummed_contact:
                startActivity(new Intent(mContext, MatrimonyMyContactsActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id. nav_contact_myssk:
                startActivity(new Intent(mContext, MatriContactUsActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            default:
                navItemIndex = 0;
        }
    }

    public void displayPreimiumalert(){
        final Dialog dialogChoice = new Dialog(HomeActivity.this);
        dialogChoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChoice.setContentView(R.layout.row_chat_premium_dilaog);
        dialogChoice.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogChoice.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogChoice.show();
        dialogChoice.setCancelable(true);

        TextView chat_go_premium = (TextView) dialogChoice.findViewById(R.id.chat_go_premium);
        chat_go_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChoice.dismiss();
                Intent intent=new Intent(HomeActivity.this,DonationActivity.class);
                startActivity(intent);

            }
        });
    }

    private void displayNavText(NavigationView navigationView) {

        try {
            View header = navigationView.getHeaderView(0);
            CircularImageView circleImageView = header.findViewById(R.id.img_profileNav_image);
            tv_uniquId = header.findViewById(R.id.tv_headerNav_uniqueId);
            tv_name = header.findViewById(R.id.tv_headerNav_name);

            subscriptionTypeTxt = header.findViewById(R.id.subscription_type);
            contactViewCredits = header.findViewById(R.id.contact_view_credits);
            buySubscriptionTxt = header.findViewById(R.id.buy_subscription);


            buySubscriptionTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      Intent intent = new Intent(mContext, DonationActivity.class);
                    startActivity(intent);
                  /*  Intent intent = new Intent(mContext, PaymentInfoActivity.class);
                    startActivity(intent);*/
                    /* startActivityForResult(intent, 111);*/
                 /*   new ShowComingSoonFragment().show(getSupportFragmentManager(), "Coming soon");
                    PaymentActivity.startPaymentActivity(HomeActivity.this, PaymentActivity.REQUEST_CODE,
                            mMemberId, Utilities.getInt(mPackageId));*/
                }
            });

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MatrimonyUtils.loadImages(mContext, mMemberId, null);
                }
            });

            username = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
            //    String uniqueId = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.UNIQUE_ID);
            uniqueId = PreferenceManager.getDefaultSharedPreferences(this).getString("nav_currentUserID", "");
            profileUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);
           // mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);
            /* mUserType = 0;*/



            if (mUserType == 1) {
                Log.d(TAG, "displayNavText: "+mUserType);
                buySubscriptionTxt.setClickable(false);
                //getRemaingProfilecount(mMemberId);
                //buySubscriptionTxt.setText("");


            } else {
                buySubscriptionTxt.setClickable(true);
            }

            tv_uniquId.setText("" + uniqueId);
            tv_name.setText("" + username);

          /*  if (mUserType == 0) {
                subscriptionTypeTxt.setText("Free");
                buySubscriptionTxt.setVisibility(View.VISIBLE);
            } else {
                subscriptionTypeTxt.setText("Premium Member");
                buySubscriptionTxt.setVisibility(View.GONE);
            }*/

            if (profileUrl.equals("") || profileUrl.equalsIgnoreCase("")) {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .error(R.drawable.img_preview)
                        .into(circleImageView);
            } else {

                Picasso.get()
                        .load(profileUrl)
                        .placeholder(R.drawable.img_preview)
                        .into(circleImageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getRemaingProfilecount(mMemberId);
        return super.onPrepareOptionsMenu(menu);
    }



    private void getRemaingProfilecount(final int mMemberId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getContactCredits,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, "logout response====: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");
                            int data = jsonObject.getInt("data");

                            if (success == 1) {
                                buySubscriptionTxt.setText("" + data);
                                Log.d(TAG, "onResponse: " + data);
                                //  Toast.makeText(mContext, "Log out time send", Toast.LENGTH_SHORT).show();
                            } else {

                                //  Toast.makeText(mContext, "Log out time not send...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "logout error===========: ", error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put("userId", String.valueOf(mMemberId));

                return param;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    private void sendLogoutTimeToServer() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matrimony_log_out_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, "logout response====: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                //  Toast.makeText(mContext, "Log out time send", Toast.LENGTH_SHORT).show();
                            } else {

                                //  Toast.makeText(mContext, "Log out time not send...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "logout error===========: ", error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put(MEMBER_ID, String.valueOf(mMemberId));

                return param;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void logoutDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Do you want to logout?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void exitFromMatrimony() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(mContext, MainActivity.class);
//                startActivity(intent);

                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        if (select instanceof HomeMatrimonyFragment) {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                exitFromMatrimony();
            }
        } else {

            select = new HomeMatrimonyFragment();
            loadFragment(select);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }
    }

    public void logout() {
        Intent intent = new Intent(mContext, LoginOptionsActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        sendLogoutTimeToServer();

        SharedPrefsHelper.getInstance(mContext).clearAll();

        startActivity(intent);
        finish();
    }

    public void initiatePayment(Context context, String memberId, String transactionId, float amount,
                                String productInfo, String firstName, String emailId,
                                String phone) {
        try {
            Intent intent = new Intent(context, PWECouponsActivity.class);

            intent.putExtra("trxn_id", transactionId);
            intent.putExtra("trxn_amount", amount);
            intent.putExtra("trxn_prod_info", productInfo);
            intent.putExtra("trxn_firstname", firstName);
            intent.putExtra("trxn_email_id", emailId);
            intent.putExtra("trxn_phone", phone);
            intent.putExtra("trxn_key", PaymentHelper.MerchantKey);
            intent.putExtra("trxn_is_coupon_enabled", PaymentHelper.merchant_is_coupon_enabled);
            intent.putExtra("trxn_salt", PaymentHelper.Salt);
            intent.putExtra("unique_id", memberId);
            intent.putExtra("enable_save_card", 1);
            intent.putExtra("pay_mode", PaymentHelper.payment_mode);

            startActivityForResult(intent, StaticDataModel.PWE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == 111) {
            displayNavText(navigationView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocationHelper.getInstance(this).getCurrentLocation(mContext, new LocationUpdateListener() {
            @Override
            public void onLocationUpdated(Location location, AddressModel addressModel) {
                try {
                    sendMemberLatLongToServer(addressModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMemberLatLongToServer(final AddressModel addressModel) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matri_near_by_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            int success = object.getInt("success");
//                            Toast.makeText(mContext, success == 1 ? "Location updated..." :
//                                    "location not updated...", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(mMemberId));
                param.put(LATITUDE, String.valueOf(addressModel.getLatitude()));
                param.put(LONGITUDE, String.valueOf(addressModel.getLongitude()));
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainPresenter.getInstance().setMainView(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainPresenter.getInstance().setMainView(this);
        showContactViewCredits(MainPresenter.getInstance().getContactsCredits(mContext));
    }

    @Override
    public void showContactViewCredits(String credits) {
        try {
            Utilities.setDataAndVisibility(contactViewCredits, credits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUserType(int userType) {
        try {
            mUserType = userType;

            tv_uniquId.setText("" + uniqueId);
            tv_name.setText("" + username);

            if (mUserType == 0) {
                subscriptionTypeTxt.setText("Free");
                buySubscriptionTxt.setVisibility(View.VISIBLE);
            } else {
                subscriptionTypeTxt.setText("Premium Member");
                buySubscriptionTxt.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}