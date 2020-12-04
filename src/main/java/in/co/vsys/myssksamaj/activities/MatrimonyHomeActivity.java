package in.co.vsys.myssksamaj.activities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.maindirectory.LoginOptionsActivity;
import in.co.vsys.myssksamaj.matrimonyfragment.HomeMatrimonyFragment;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class MatrimonyHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LifecycleObserver {

    private Context mContext;
    private static final String TAG_HOME = "nav_home_event";
    private static final String TAG = MatrimonyHomeActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private Handler mHandler;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private SharedPreferences mPreferences;
    private int memberId;
    long back_pressed;
    private Fragment select;
    private String accountCreatedBy;
    private TextView inviteCount;



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
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
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

        memberId = mPreferences.getInt("memberId", 0);
        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");

        if (accountCreatedBy.equals("P")) {

            hideItems();
        }

        inviteCount = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_matro_invite_list));

        //  initializeCountDrawer();
        displayNavText(navigationView);
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

        Menu nav_menu = navigationView.getMenu();

        nav_menu.findItem(R.id.nav_matro_recently_matches).setVisible(false);
        nav_menu.findItem(R.id.nav_matro_profile_visitors).setVisible(false);
        nav_menu.findItem(R.id.nav_matro_profile_views).setVisible(false);
        nav_menu.findItem(R.id.nav_matro_nearBy).setVisible(false);
        nav_menu.findItem(R.id.nav_matro_invite_list).setVisible(false);

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
                startActivity(new Intent(mContext, MatriNearByActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_profile_visitors:
                startActivity(new Intent(mContext, ProfileVisitorListActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_chat:
                startActivity(new Intent(mContext, ChatListActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_matro_notification:
                startActivity(new Intent(mContext, MatrimonyNotificationActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            default:
                navItemIndex = 0;
        }
    }

    public void logout(){
        Intent intent = new Intent(mContext, LoginOptionsActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sendLogoutTimeToServer();

        //  mPreferences.edit().clear().apply();
        mPreferences.edit().remove("nav_uniqueId").apply();
        mPreferences.edit().remove("memberId").apply();
        mPreferences.edit().remove("nav_memberName").apply();
        mPreferences.edit().remove("nav_profileUrl").apply();
        mPreferences.edit().remove("accountCreatedBY").apply();
        mPreferences.edit().remove("tokenId").apply();
        mPreferences.edit().remove("basicInfoPer").apply();
        mPreferences.edit().remove("photoInfoPer").apply();
        mPreferences.edit().remove("introInfoPer").apply();
        mPreferences.edit().remove("desiredInfoPer").apply();
        mPreferences.edit().remove("familyInfoPer").apply();
        mPreferences.edit().remove("higherEduInfoPer").apply();
        mPreferences.edit().remove("horoscopeInfoPer").apply();
        mPreferences.edit().remove("lifeStyleInfoPer").apply();
        mPreferences.edit().remove("alertPercentFlag").apply();
        mPreferences.edit().remove("receiverTokenId").apply();
        mPreferences.edit().remove("messageReceiverImage").apply();
        mPreferences.edit().remove("messageReceiverName").apply();
        mPreferences.edit().remove("messageReceiverId").apply();

        startActivity(intent);
        finish();
    }

    private void displayNavText(NavigationView navigationView) {

        View header = navigationView.getHeaderView(0);
        CircularImageView circleImageView = header.findViewById(R.id.img_profileNav_image);
        AppCompatTextView tv_uniquId = header.findViewById(R.id.tv_headerNav_uniqueId);
        AppCompatTextView tv_name = header.findViewById(R.id.tv_headerNav_name);

        String username = mPreferences.getString("nav_memberName", "");
        String uniqueId = mPreferences.getString("nav_uniqueId", "");
        String profileUrl = mPreferences.getString("nav_profileUrl", "");

        tv_uniquId.setText("" + uniqueId);
        tv_name.setText("" + username);


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

    }

    private void sendLogoutTimeToServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,/*Config.ToggleOnlineStatus */Config.matrimony_log_out_url,
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

                param.put(MEMBER_ID, String.valueOf(memberId));

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
                Intent intent = new Intent(mContext, MainActivity.class);
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
}