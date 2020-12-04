package in.co.vsys.myssksamaj.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.PVisitorVPagerAdapter;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.model.PagerModel;
import in.co.vsys.myssksamaj.model.RecentlyProfileVisitorModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class ProfileVisitorViewActivity extends AppCompatActivity {

    private static final String MEMBER_ID = "MemberId";
    private static final String TAG = ProfileVisitorViewActivity.class.getSimpleName();
    private ViewPager viewPager;
    public static String memberName;
    private int viewPosition;
    public List<PagerModel> phList;
    private SharedPreferences mPreference;
    private int memberId;
    private String role;
    private List<RecentlyProfileVisitorModel> recentlyProfileVisitorModelList;
    private PVisitorVPagerAdapter viewProfileAdapter;

    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_visitor_view);

        viewPager = (ViewPager) findViewById(R.id.profileVisited_viewpager);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_profileVisited);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        phList = new ArrayList<>();

        Intent p = getIntent();
        viewPosition = p.getExtras().getInt("PVisitedPosition");
        role=p.getExtras().getString("Role");
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mUserType = SharedPrefsHelper.getInstance(ProfileVisitorViewActivity.this).getIntVal(SharedPrefsHelper.USERTYPE);

        memberId = mPreference.getInt("memberId", 0);
        recentlyProfileVisitorModelList = new ArrayList<>();

        if (viewPosition == 0) {

            mToolbar.setTitle("" + memberName);
        }

        loadProfileVisitorData();

        /*viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                String toolbarFName = recentlyProfileVisitorModelList.get(position).getFirstName();

                toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();

                String toolbarLName = recentlyProfileVisitorModelList.get(position).getLastName();

                toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();

                mToolbar.setTitle(toolbarFName + " " + toolbarLName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                viewPosition = position;

                String toolbarFName = recentlyProfileVisitorModelList.get(viewPosition).getFirstName();

                toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();

                String toolbarLName = recentlyProfileVisitorModelList.get(viewPosition).getLastName();

                toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();

                mToolbar.setTitle(toolbarFName + " " + toolbarLName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                //   startActivity(new Intent(ProfileVisitorViewActivity.this, HomeActivity.class));
                //    finish();
                onBackPressed();
                return true;

            case R.id.nav_share:
                Toast.makeText(this, "Share Click", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = NavUtils.getParentActivityIntent(ProfileVisitorViewActivity.this);

        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(intent);

        finish();

    }

    private void loadProfileVisitorData() {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage(getResources().getString(R.string.please_wait));
        mDialog.show();

        StringRequest recentlYProfileVisitors = new StringRequest(Request.Method.POST, Config.getVisitorsByRole,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mDialog.dismiss();

                        try {
                            JSONArray jsonArray;
                            JSONObject rJsonObject = new JSONObject(response);

                            int success = rJsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = rJsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    RecentlyProfileVisitorModel model = new RecentlyProfileVisitorModel();

                                    model.setUserMemberId(object.getInt("MemberId"));
                                    model.setUniqueId(object.getString("UniqueId"));
                                    model.setProfileCreatedBy(object.getString("AccountCreatedBy"));
                                    model.setProfileManagedBy(object.getString("Account Manage By"));
                                    model.setFirstName(object.getString("FirstName"));
                                    model.setLastName(object.getString("LastName"));
                                    model.setGender(object.getString("Gender"));
                                    model.setDOB(object.getString("DOB"));
                                    model.setUserHeight(object.getString("MemberHeight"));
                                    model.setUserCountry(object.getString("MemberCountry"));
                                    model.setUserState(object.getString("MemberState"));
                                    model.setUserCity(object.getString("MemberCity"));
                                    model.setMotherTongue(object.getString("MotherTongue"));
                                    model.setMarriedStatus(object.getString("MarriedStatus"));
                                    model.setEduction(object.getString("MemberEducationName"));
                                    model.setFoodType(object.getString("foodtype"));
                                    model.setDrinkingHabit(object.getString("drinkhabit"));
                                    model.setSmokingHabit(object.getString("smokehabit"));
                                    model.setComplexion(object.getString("complexion"));
                                    model.setBodyType(object.getString("bodytype"));
                                    model.setPhysicalChallege(object.getString("physicalchallenge"));
                                    model.setManglik(object.getString("manglik"));
                                    model.setEductionIn(object.getString("EducationInName"));
                                    model.setWorkWith(object.getString("EducationWithName"));
                                    model.setWorkAs(object.getString("MemberOccupation"));
                                    model.setUserIncome(object.getString("MemberInCome"));
                                    model.setFamilyType(object.getString("FamilyType"));
                                    model.setFatherStatus(object.getString("fatherstatus"));
                                    model.setFatherCompany(object.getString("fathercompany"));
                                    model.setFatherPost(object.getString("fatherpost"));
                                    model.setMotherstatus(object.getString("motherstatus"));
                                    model.setMotherCompany(object.getString("mothercompany"));
                                    model.setMotherPost(object.getString("motherpost"));
                                    model.setNoOfBrothers(object.getString("NoOfBorther"));
                                    model.setNoOfBrotherMarried(object.getString("NoOfBortherMarried"));
                                    model.setNoOfSisters(object.getString("NoOfSister"));
                                    model.setNoOfSistersMarried(object.getString("NoOfSisterMarried"));
                                    model.setBirthTime(object.getString("BirthTime"));
                                    model.setBirthPlace(object.getString("BirthPlace"));
                                    model.setGotra(object.getString("gotraname"));
                                    model.setRashi(object.getString("Rashi"));
                                    model.setNakstra(object.getString("Nakshtra"));
                                    model.setCharan(object.getString("Charan"));
                                    model.setNaaddi(object.getString("Naddi"));
                                    model.setGan(object.getString("Gan"));
                                    model.setKundliPhoto(object.getString("KundaliPhoto"));
                                    model.setUserProfileUrl(object.getString("MainProfilePhoto"));
                                    model.setSelfIntroduction(object.getString("SelfIntroduction"));
                                    model.setShortlistedFlag(object.getString("Shorted"));//model.setShortlistedFlag(object.getString("ShortListed"));
                                    model.setInvitedFlag(object.getString("Invited"));
                                    model.setTokenId(object.getString("DeviceId"));
                                    model.setOnlieTime(object.getString("Online Time"));
                                    model.setOfflineTime(object.getString("Offline Time"));
                                    model.setBlocked(object.getString("Blocked"));
                                    model.setOnlinestatus(object.getString("onlinestatus"));
                                   /* model.setCaste(object.getString("Caste"));
                                    model.setSubCaste(object.getString("Subcaste"));*/

                                   if(model.getProfileCreatedBy().equalsIgnoreCase("P")){
                                       if(model.getBlocked().equalsIgnoreCase("0") || model.getBlocked().equalsIgnoreCase("") ){
                                           recentlyProfileVisitorModelList.add(model);
                                       }
                                   }else if( model.getProfileCreatedBy().equalsIgnoreCase("C")){
                                       recentlyProfileVisitorModelList.add(model);
                                   }

                                  /* if(model.getBlocked().equalsIgnoreCase("0") || model.getBlocked().equalsIgnoreCase("") ){

                                   }*/


                                }

                                viewProfileAdapter = new PVisitorVPagerAdapter(ProfileVisitorViewActivity.this, recentlyProfileVisitorModelList);
                                viewPager.setAdapter(viewProfileAdapter);
                                viewPager.setCurrentItem(viewPosition);


                            } else {

                                Toast.makeText(ProfileVisitorViewActivity.this, "Details not found...", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(memberId));
                param.put("role",role);
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(recentlYProfileVisitors);
    }
}