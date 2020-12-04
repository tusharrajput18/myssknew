package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import in.co.vsys.myssksamaj.CommonLoginActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.fragmentsBusines.BusinessHomeFragment;
import in.co.vsys.myssksamaj.fragments.BusinessProfileFragment;
import in.co.vsys.myssksamaj.fragments.BusinessSearchFragment;
import in.co.vsys.myssksamaj.fragmentsBusines.InsertBusinessFragment;
import in.co.vsys.myssksamaj.maindirectory.MainMobileNumberActivity;

public class BusinessDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_HOME = "nav_home_business";
    private static int navItemIndex = 0;
    private static String CURRENT_TAG = TAG_HOME;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private Handler mHandler;
    private Fragment select;
    long back_pressed;

    private boolean shouldLoadHomeFragOnBackPress = true;


    Context mContext;
    private SharedPreferences mPreference;
    TextView tv_business_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_dashboard);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_businessHome);
        setSupportActionBar(mToolbar);
        mHandler = new Handler();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.businessDrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_businessNavigation);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        tv_business_login=(TextView)findViewById(R.id.tv_business_login);

        mToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        displayNavText(navigationView);
        navigationView.setItemIconTintList(null);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;

            loadHomeFragment();
        }

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPreference.contains("App_common_userId")){
                    select = new InsertBusinessFragment();
                    loadFragment(select,InsertBusinessFragment.TAG);
                }else {
                    Toast.makeText(BusinessDashboardActivity.this, "Login First..", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if(mPreference.contains("App_common_userId")){
            String fname=  mPreference.getString("common_fname","");
            String lname=mPreference.getString("common_lname","");
            tv_business_login.setText(fname+" "+lname);
            tv_business_login.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_logout,0);
        }else {
            tv_business_login.setText("Login");
        }

        tv_business_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPreference.contains("App_common_userId")){
                    logoutAlertDialog();
                }else {
                    startActivity(new Intent(BusinessDashboardActivity.this, CommonLoginActivity.class).putExtra("Login","business"));
                }
            }
        });
    }

    private void logoutAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.logout_popup, null);
        builder.setView(view);

        AppCompatButton btn_self = view.findViewById(R.id.btn_rowSelf);
        AppCompatButton btn_parent = view.findViewById(R.id.btn_rowParent);
        TextView tv_login = view.findViewById(R.id.tv_rowLogin_login);
        TextView tv_register = view.findViewById(R.id.tv_rowLogin_register);

        tv_login.setVisibility(View.VISIBLE);
        tv_register.setVisibility(View.GONE);

        final AlertDialog alertDialog = builder.create();


        btn_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(BusinessDashboardActivity.this, MainMobileNumberActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                mPreference.edit().remove("common_fname").commit();
                mPreference.edit().remove("common_mname").commit();
                mPreference.edit().remove("common_lname").commit();
                mPreference.edit().remove("common_mobileNo").commit();
                mPreference.edit().remove("App_common_userId").commit();
                mPreference.edit().remove("otp").commit();

                alertDialog.dismiss();
            }
        });

        btn_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(mContext, LoginActivity.class));
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
            case R.id.nav_business_home:
                navItemIndex = 0;
                CURRENT_TAG = BusinessHomeFragment.TAG;
                select = new BusinessHomeFragment();
                loadFragment(select,BusinessHomeFragment.TAG);
                break;

            case R.id.nav_business_login:
                navItemIndex = 1;
                startActivity(new Intent(BusinessDashboardActivity.this, CommonLoginActivity.class));
               /* CURRENT_TAG = TAG_HOME;
                select = new BusinessHomeFragment();
                loadFragment(select,BusinessHomeFragment.TAG);*/
                break;

            case R.id.nav_business_search:
                navItemIndex = 2;
                select = new BusinessSearchFragment();
                loadFragment(select,BusinessSearchFragment.TAG);
                break;

            case R.id.nav_business_insert:
                if(mPreference.contains("App_common_userId")) {
                    navItemIndex = 3;
                    select = new InsertBusinessFragment();
                    loadFragment(select, InsertBusinessFragment.TAG);
                /*InsertBusinessFragment insertBusinessFragment = new InsertBusinessFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_business_layout, insertBusinessFragment)
                        .addToBackStack(null)
                        .commit();*/
                }else {
                    Toast.makeText(mContext, "Login First", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_business_profile:
                navItemIndex = 4;

                select = new BusinessProfileFragment();
                loadFragment(select,BusinessProfileFragment.TAG);

               /* BusinessProfileFragment businessProfileFragment = new BusinessProfileFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_business_layout, businessProfileFragment)
                        .addToBackStack(null)
                        .commit();*/
                break;
            case R.id.nav_business_exit:

                startActivity(new Intent(BusinessDashboardActivity.this, MainActivity.class));

            default:
                navItemIndex = 0;

        }

    }


    private void displayNavText(NavigationView navigationView) {

        try {
            View header = navigationView.getHeaderView(0);

            TextView name=header.findViewById(R.id.tv_common_name);
            TextView mobile=header.findViewById(R.id.tv_common_mobile);

            if(mPreference.contains("App_common_userId")){

                String fname=mPreference.getString("common_fname","");
                String lname=mPreference.getString("common_lname","");
                String commonMobileNo=mPreference.getString("common_mobileNo","");
                name.setText(fname+" "+lname);
                mobile.setText(commonMobileNo);
            }




            //CircularImageView circleImageView = header.findViewById(R.id.img_profileNav_image);
         /*   tv_uniquId = header.findViewById(R.id.tv_headerNav_uniqueId);
            tv_name = header.findViewById(R.id.tv_headerNav_name);

            subscriptionTypeTxt = header.findViewById(R.id.subscription_type);
            contactViewCredits = header.findViewById(R.id.contact_view_credits);
            buySubscriptionTxt = header.findViewById(R.id.buy_subscription);


            buySubscriptionTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   *//* Intent intent = new Intent(mContext, NewsListActivity.class);
                    startActivity(intent);*//*
                    *//* startActivityForResult(intent, 111);*//*
                    new ShowComingSoonFragment().show(getSupportFragmentManager(), "Coming soon");
                    PaymentActivity.startPaymentActivity(HomeActivity.this, PaymentActivity.REQUEST_CODE,
                            mMemberId, Utilities.getInt(mPackageId));
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
            *//* mUserType = 0;*//*



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

          *//*  if (mUserType == 0) {
                subscriptionTypeTxt.setText("Free");
                buySubscriptionTxt.setVisibility(View.VISIBLE);
            } else {
                subscriptionTypeTxt.setText("Premium Member");
                buySubscriptionTxt.setVisibility(View.GONE);
            }*//*

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
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                Log.d("mytag", "getHomeFragment:1234 ");
                return new BusinessHomeFragment();

            case 2:
                return new BusinessSearchFragment();

            case 3:
                return new InsertBusinessFragment();

            case 4:
                return new BusinessProfileFragment();


            default:
                Log.d("mytag", "getHomeFragment: ");
                return new BusinessHomeFragment();
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
                fragmentTransaction.replace(R.id.nav_business_layout, fragment, BusinessHomeFragment.TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);
        //Closing drawer on item click
        mDrawerLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {

      /*  FragmentManager manager = getSupportFragmentManager();
            FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(0);
            String str = backEntry.getName();
            if (str.equals(BusinessHomeFragment.TAG)) {
                if (back_pressed + 2000 > System.currentTimeMillis()) {

                    super.onBackPressed();
                } else {

                    exitFromMatrimony();
                }
            }*/



        if (select instanceof BusinessHomeFragment) {

            if (back_pressed + 2000 > System.currentTimeMillis()) {

                super.onBackPressed();
            } else {

                exitFromMatrimony();
            }
        } else {
            Log.d("mytag", "onBackPressed: business home");
            select = new BusinessHomeFragment();
            loadFragment(select,BusinessHomeFragment.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }

       /* if(select instanceof  InsertBusinessFragment){

            select = new BusinessHomeFragment();
            loadFragment(select,BusinessHomeFragment.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }
        if(select instanceof  InsertBusinessFragment2){

            select = new InsertBusinessFragment();
            loadFragment(select,InsertBusinessFragment.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }

        if(select instanceof  InsertBusinessFragment3){
            select = new InsertBusinessFragment2();
            loadFragment(select,InsertBusinessFragment2.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }*/


     /*   if(select instanceof InsertBusinessFragment){
            select = new BusinessHomeFragment();
            loadFragment(select,BusinessHomeFragment.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);

        }
        if(select instanceof InsertBusinessFragment2){
            select = new InsertBusinessFragment();
            loadFragment(select,InsertBusinessFragment.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }

        if(select instanceof InsertBusinessFragment3){
            select = new InsertBusinessFragment2();
            loadFragment(select,InsertBusinessFragment2.TAG);
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }*/
    }

    private void loadFragment(Fragment select,String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_business_layout, select);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("tag");
        transaction.commit();
    }

    private void exitFromMatrimony() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BusinessDashboardActivity.this, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);


        alertDialog.setMessage("Do you want to exit?");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(BusinessDashboardActivity.this, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                startActivity(intent);
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

    public void setActionBarTitle(String title) {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(title);
        }

    }
}
