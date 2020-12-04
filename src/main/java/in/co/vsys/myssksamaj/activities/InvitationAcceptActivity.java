package in.co.vsys.myssksamaj.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import in.co.vsys.myssksamaj.adapter.InvitationPAcceptAdapter;
import in.co.vsys.myssksamaj.model.InvitationModel;

import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class InvitationAcceptActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private List<InvitationModel> invitationModelList;
    private InvitationPAcceptAdapter invitationAdapter;
    private static final String MEMBER_ID = "MemberId";
    private SharedPreferences mPreferences;
    private int memberId, viewPosition;
    public static String memberName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_accept);

        // mProgressBar = (ProgressBar) findViewById(R.id.invitationAccepted_progressbar);
        mToolbar = (Toolbar) findViewById(R.id.invitationAccept_toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.invitationAccept_viewpager);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent p = getIntent();
        viewPosition = p.getExtras().getInt("position");

        if (viewPosition == 0) {

            mToolbar.setTitle("" + memberName);
        }

        invitationModelList = new ArrayList<>();

        loadInvitationAccepted();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        memberId = mPreferences.getInt("memberId", 0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                viewPosition = position;

                String toolbarFName = invitationModelList.get(viewPosition).getFirstName();

                toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();

                String toolbarLName = invitationModelList.get(viewPosition).getLastName();

                toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();

                mToolbar.setTitle(toolbarFName + " " + toolbarLName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                //  startActivity(new Intent(InvitationAcceptActivity.this, MatriInvitationActivity.class));
                /// finish();
                onBackPressed();
                return true;

           /* case R.id.nav_share:
                Toast.makeText(this, "Share Click", Toast.LENGTH_SHORT).show();
                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void loadInvitationAccepted() {

        //  mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matrimony_invitation_accepted_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //    mProgressBar.setVisibility(View.INVISIBLE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray;

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    InvitationModel shortlistedModel = new InvitationModel();

                                    shortlistedModel.setMemberId(jsonObject1.getInt("MemberId"));
                                    shortlistedModel.setUniqueId(jsonObject1.getString("UniqueId"));
                                    shortlistedModel.setProfileCreatedBy(jsonObject1.getString("AccountCreatedBy"));
                                    shortlistedModel.setProfileManagedBy(jsonObject1.getString("Account Manage By"));
                                    shortlistedModel.setFirstName(jsonObject1.getString("FirstName"));
                                    shortlistedModel.setLastName(jsonObject1.getString("LastName"));
                                    shortlistedModel.setGender(jsonObject1.getString("Gender"));
                                    shortlistedModel.setDateOfBirth(jsonObject1.getString("DOB"));
                                    shortlistedModel.setUserHeight(jsonObject1.getString("MemberHeight"));
                                    shortlistedModel.setUserCountry(jsonObject1.getString("MemberCountry"));
                                    shortlistedModel.setUserState(jsonObject1.getString("MemberState"));
                                    shortlistedModel.setUserCity(jsonObject1.getString("MemberCity"));
                                    shortlistedModel.setMotherTounge(jsonObject1.getString("MotherTongue"));
                                    shortlistedModel.setMarriedStatus(jsonObject1.getString("MarriedStatus"));
                                    shortlistedModel.setEduction(jsonObject1.getString("MemberEducationName"));
                                    shortlistedModel.setEductionIn(jsonObject1.getString("EducationInName"));
                                    shortlistedModel.setWorkingWith(jsonObject1.getString("EducationWithName"));
                                    shortlistedModel.setWorkingAs(jsonObject1.getString("MemberOccupation"));
                                    shortlistedModel.setSalary_details(jsonObject1.getString("MemberInCome"));
                                    shortlistedModel.setFamily_type(jsonObject1.getString("FamilyType"));
                                    shortlistedModel.setFather_status(jsonObject1.getString("fatherstatus"));
                                    shortlistedModel.setFather_company(jsonObject1.getString("fathercompany"));
                                    shortlistedModel.setFather_post(jsonObject1.getString("fatherpost"));
                                    shortlistedModel.setMother_status(jsonObject1.getString("motherstatus"));
                                    shortlistedModel.setMother_company(jsonObject1.getString("mothercompany"));
                                    shortlistedModel.setMother_post(jsonObject1.getString("motherpost"));
                                    shortlistedModel.setNoOfBrothers(jsonObject1.getString("NoOfBorther"));
                                    shortlistedModel.setNoOfBrotherMarried(jsonObject1.getString("NoOfBortherMarried"));
                                    shortlistedModel.setNoOfSisters(jsonObject1.getString("NoOfSister"));
                                    shortlistedModel.setNoOfSistersMarried(jsonObject1.getString("NoOfSisterMarried"));
                                    shortlistedModel.setBirthTime(jsonObject1.getString("BirthTime"));
                                    shortlistedModel.setBirthPlae(jsonObject1.getString("BirthPlace"));
                                    shortlistedModel.setGotra(jsonObject1.getString("gotraname"));
                                    shortlistedModel.setManglik(jsonObject1.getString("manglik"));
                                    shortlistedModel.setRashi(jsonObject1.getString("Rashi"));
                                    shortlistedModel.setNakshatra(jsonObject1.getString("Nakshtra"));
                                    shortlistedModel.setCharan(jsonObject1.getString("Charan"));
                                    shortlistedModel.setNaadi(jsonObject1.getString("Naddi"));
                                    shortlistedModel.setGan(jsonObject1.getString("Gan"));
                                    shortlistedModel.setKundli_photo(jsonObject1.getString("KundaliPhoto"));
                                    shortlistedModel.setPartner_min_height(jsonObject1.getString("PartnerMinHeight"));
                                    shortlistedModel.setPartner_max_height(jsonObject1.getString("PartnerMaxHeight"));
                                    shortlistedModel.setPartner_min_age(jsonObject1.getString("PartnerMinAge"));
                                    shortlistedModel.setPartner_max_age(jsonObject1.getString("PartnerMaxAge"));
                                    shortlistedModel.setPartner_married_status(jsonObject1.getString("PartnerMarriedStatus"));
                                    shortlistedModel.setPartner_eduction(jsonObject1.getString("PartnerEducationType"));
                                    shortlistedModel.setEductionIn(jsonObject1.getString("PartnerEducationIn"));
                                    shortlistedModel.setPartner_working_with(jsonObject1.getString("PartnerEducationWith"));
                                    shortlistedModel.setPartner_working_as(jsonObject1.getString("PartnerOccupation"));
                                    shortlistedModel.setPartner_mother_tounge(jsonObject1.getString("PartnerMotherTongue"));
                                    shortlistedModel.setPartner_income(jsonObject1.getString("PartnerIncome"));
                                    shortlistedModel.setPartner_country(jsonObject1.getString("PartnerCountry"));
                                    shortlistedModel.setPartner_state(jsonObject1.getString("PartnerState"));
                                    shortlistedModel.setPartner_city(jsonObject1.getString("PartnerCity"));
                                    shortlistedModel.setPartner_eating_habits(jsonObject1.getString("PartnerFoodType"));
                                    shortlistedModel.setPartner_smoking_habits(jsonObject1.getString("partnerSmokeHabit"));
                                    shortlistedModel.setPartner_drinking_habits(jsonObject1.getString("PartnerDrinkHabit"));
                                    shortlistedModel.setPartner_physical_status(jsonObject1.getString("PartnerPhysicalChallenge"));
                                    shortlistedModel.setPartner_introduction(jsonObject1.getString("PartnerIntroduction"));
                                    shortlistedModel.setPartner_introduction_video(jsonObject1.getString("IntroductionVideo"));
                                    shortlistedModel.setUserProfileUrl(jsonObject1.getString("MainProfilePhoto"));
                                    shortlistedModel.setPartnerBodyType(jsonObject1.getString("PartnerBodyType"));
                                    shortlistedModel.setShortlistedFlag(jsonObject1.getString("Shorted"));
                                    shortlistedModel.setInvitedFlag(jsonObject1.getString("Invited"));
                                    shortlistedModel.setFoodType(jsonObject1.getString("foodtype"));
                                    shortlistedModel.setDrinkingHabit(jsonObject1.getString("drinkhabit"));
                                    shortlistedModel.setSmokingHabit(jsonObject1.getString("smokehabit"));
                                    shortlistedModel.setComplexion(jsonObject1.getString("complexion"));
                                    shortlistedModel.setBodyType(jsonObject1.getString("bodytype"));
                                    shortlistedModel.setPhysicalChallege(jsonObject1.getString("physicalchallenge"));
                                    shortlistedModel.setOnlieTime(jsonObject1.getString("Online Time"));
                                    shortlistedModel.setOfflineTime(jsonObject1.getString("Offline Time"));
                                    shortlistedModel.setTokenId(jsonObject1.getString("DeviceId"));
                                    shortlistedModel.setOnlinestatus(jsonObject1.getString("onlinestatus"));
                                  //  shortlistedModel.setCaste(jsonObject1.getString("Caste"));
                                  //  shortlistedModel.setSubCaste(jsonObject1.getString("Subcaste"));

                                    invitationModelList.add(shortlistedModel);

                                }

                                invitationAdapter = new InvitationPAcceptAdapter(InvitationAcceptActivity.this, invitationModelList);
                                mViewPager.setAdapter(invitationAdapter);
                                mViewPager.setCurrentItem(viewPosition);
                                //   invitationAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(InvitationAcceptActivity.this, "No list available...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //  mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(memberId));

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = NavUtils.getParentActivityIntent(InvitationAcceptActivity.this);

        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(intent);

        finish();
    }
}