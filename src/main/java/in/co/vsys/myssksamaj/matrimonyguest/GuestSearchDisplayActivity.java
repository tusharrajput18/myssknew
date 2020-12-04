package in.co.vsys.myssksamaj.matrimonyguest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.ProfileSearchActivity;
import in.co.vsys.myssksamaj.contracts.InviteContract;
import in.co.vsys.myssksamaj.contracts.ShortListContract;
import in.co.vsys.myssksamaj.model.ShortlistedModel;
import in.co.vsys.myssksamaj.presenters.InvitePresenter;
import in.co.vsys.myssksamaj.presenters.ShortListPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GuestSearchDisplayActivity extends AppCompatActivity implements ShortListContract.ShortListView, InviteContract.InviteView {

    private Context mContext;
    private static final String TAG = ProfileSearchActivity.class.getSimpleName();
    private static final String UNIQUE_ID = "UniqueId";
    private String uniqueId;
    private int memberId;
    private String candidate = "In my own words";
    private String parents = "a few words about my daughter";
    private ShortlistedModel shortlistedModel;
    private CollapsingToolbarLayout mCoordinatorLayout;
    private String username;
    //  private CircleImageView ;
    private CircularImageView img_profileViewverPic;
    private ImageView profileUrl, imgInvite, imgShortlist, imgShortlisted, imgInvited, kundli_photo;
    private TextView tv_memberId, tv_otherDetails, tv_profile_created_by, tv_profileView_introduction, tv_profileView_name, tv_profileView_gender,
            tv_profileView_age, tv_profileView_height, tv_profileView_married_status, tv_profileView_mother_tongue, tv_profileView_physical_status, tv_profileView_body_type,
            tv_profileView_profile_created_by, tv_profileView_eating_habit, tv_profileView_drinking_habit, tv_profileView_smoking_habit, tv_profileView_eduction,
            tv_profileView_eduction_in, tv_profileView_working_with, tv_profileView_working_as, tv_profileView_annual_income, tv_profileView_country, tv_profileView_state,
            tv_profileView_city, tv_profileView_family_type, tv_profileView_father_status, tv_profileView_mother_status, tv_profileView_no_of_brother,
            tv_profileView_no_of_brother_married, tv_profileView_no_of_sister, tv_profileView_no_of_sister_married, tv_profileView_birth_date, tv_profileView_birth_time,
            tv_profileView_birth_place, tv_profileView_gotra, tv_profileView_rashi, tv_profileView_nakshtra, tv_profileView_charan, tv_profileView_naadi, tv_profileView_gan;
    private TextView tv_shortlist, tv_shortlisted, tv_invite, tv_invited, tv_profileView_kundli;
    private View view_kundli_view;
    private SharedPreferences mPreferences;
    private AppBarLayout appBarLayout;
    private ShortListContract.ShortListOps shortListPresenter;
    private InviteContract.InviteOps invitePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_search_display);
        mContext = this;

        mCoordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.search_collapsing_toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.search_appBarLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        profileUrl = (ImageView) findViewById(R.id.img_search_Profileimage);
        tv_memberId = (TextView) findViewById(R.id.tv_search_uniqueId);
        view_kundli_view = (View) findViewById(R.id.view_kundli_view);
        tv_otherDetails = (TextView) findViewById(R.id.tv_search_otherDetails);
        imgInvite = (ImageView) findViewById(R.id.img_search_invite);
        kundli_photo = (ImageView) findViewById(R.id.img_profileView_kundli);
        imgInvited = (ImageView) findViewById(R.id.img_search_invited);
        imgShortlist = (ImageView) findViewById(R.id.img_search_shortlist);
        imgShortlisted = (ImageView) findViewById(R.id.img_search_shortlisted);
        tv_profile_created_by = (TextView) findViewById(R.id.tv_search_created_by);
        tv_profileView_kundli = (TextView) findViewById(R.id.tv_profileView_kundli);
        tv_profileView_introduction = (TextView) findViewById(R.id.tv_search_introduction);
        tv_profileView_name = (TextView) findViewById(R.id.tv_search_name);
        tv_profileView_gender = (TextView) findViewById(R.id.tv_search_gender);
        tv_profileView_age = (TextView) findViewById(R.id.tv_search_age);
        tv_profileView_height = (TextView) findViewById(R.id.tv_search_height);
        tv_profileView_married_status = (TextView) findViewById(R.id.tv_search_married_status);
        tv_profileView_mother_tongue = (TextView) findViewById(R.id.tv_search_mother_tongue);
        tv_profileView_physical_status = (TextView) findViewById(R.id.tv_search_physical_status);
        tv_profileView_body_type = (TextView) findViewById(R.id.tv_search_body_type);
        // TextView tv_profileView_complexion = findViewById(R.id.tv_profileView_complexion);
        tv_profileView_profile_created_by = (TextView) findViewById(R.id.tv_search_profile_created_by);
        tv_profileView_eating_habit = (TextView) findViewById(R.id.tv_search_eating_habit);
        tv_profileView_drinking_habit = (TextView) findViewById(R.id.tv_search_drinking_habit);
        tv_profileView_smoking_habit = (TextView) findViewById(R.id.tv_search_smoking_habit);
        tv_profileView_eduction = (TextView) findViewById(R.id.tv_search_eduction);
        tv_profileView_eduction_in = (TextView) findViewById(R.id.tv_search_eduction_in);
        tv_profileView_working_with = (TextView) findViewById(R.id.tv_search_working_with);
        tv_profileView_working_as = (TextView) findViewById(R.id.tv_search_working_as);
        tv_profileView_annual_income = (TextView) findViewById(R.id.tv_search_annual_income);
        tv_profileView_country = (TextView) findViewById(R.id.tv_search_country);
        tv_profileView_state = (TextView) findViewById(R.id.tv_search_state);
        tv_profileView_city = (TextView) findViewById(R.id.tv_search_city);
        tv_profileView_family_type = (TextView) findViewById(R.id.tv_search_family_type);
        tv_profileView_father_status = (TextView) findViewById(R.id.tv_search_father_status);
        tv_profileView_mother_status = (TextView) findViewById(R.id.tv_search_mother_status);
        tv_profileView_no_of_brother = (TextView) findViewById(R.id.tv_search_no_of_brother);
        tv_profileView_no_of_brother_married = (TextView) findViewById(R.id.tv_search_no_of_brother_married);
        tv_profileView_no_of_sister = (TextView) findViewById(R.id.tv_search_no_of_sister);
        tv_profileView_no_of_sister_married = (TextView) findViewById(R.id.tv_search_no_of_sister_married);
        tv_profileView_birth_date = (TextView) findViewById(R.id.tv_search_birth_date);
        tv_profileView_birth_time = (TextView) findViewById(R.id.tv_search_birth_time);
        tv_profileView_birth_place = (TextView) findViewById(R.id.tv_search_birth_place);
        tv_profileView_gotra = (TextView) findViewById(R.id.tv_search_gotra);
        tv_profileView_rashi = (TextView) findViewById(R.id.tv_search_rashi);
        tv_profileView_nakshtra = (TextView) findViewById(R.id.tv_search_nakshtra);
        tv_profileView_charan = (TextView) findViewById(R.id.tv_search_charan);
        tv_profileView_naadi = (TextView) findViewById(R.id.tv_search_naadi);
        tv_profileView_gan = (TextView) findViewById(R.id.tv_search_gan);
        tv_shortlist = findViewById(R.id.tv_shortlist);
        tv_shortlisted = findViewById(R.id.tv_shortlisted);
        tv_invite = findViewById(R.id.tv_search_invite);
        tv_invited = findViewById(R.id.tv_search_invited);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreferences.getInt("memberId", 0);
        // Intent p = getIntent();


        /*if (p != null) {


            uniqueId = p.getExtras().getString("uniqueId");
        }*/

        uniqueId = mPreferences.getString("uniqueId", "");

        searchProfile();
        shortListPresenter = new ShortListPresenter(this);
        invitePresenter = new InvitePresenter(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(GuestSearchDisplayActivity.this, GuestSearchListActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(SearchDisplayActivity.this, SearchListActivity.class));

    }*/

    private void searchProfile() {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.please_wait));
        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.searchByIdUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "search response" + response);
                        mDialog.dismiss();

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    shortlistedModel = new ShortlistedModel();

                                    shortlistedModel.setUserMemberId(jsonObject1.getInt("MemberId"));
                                    shortlistedModel.setUniqueId(jsonObject1.getString("UniqueId"));
                                    shortlistedModel.setProfileCreatedBy(jsonObject1.getString("AccountCreatedBy"));
                                    shortlistedModel.setFirstName(jsonObject1.getString("FirstName"));
                                    shortlistedModel.setLastName(jsonObject1.getString("LastName"));
                                    shortlistedModel.setGender(jsonObject1.getString("Gender"));
                                    shortlistedModel.setDateOfBirth(jsonObject1.getString("DOB"));
                                    shortlistedModel.setUserHeight(jsonObject1.getString("MemberHeight"));
                                    shortlistedModel.setUserCountry(jsonObject1.getString("MemberCountry"));
                                    shortlistedModel.setUserState(jsonObject1.getString("MemberState"));
                                    shortlistedModel.setUserCity(jsonObject1.getString("MemberCity"));
                                    shortlistedModel.setMotherTongue(jsonObject1.getString("MotherTongue"));
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
                                    shortlistedModel.setPartner_eduction_In(jsonObject1.getString("PartnerEducationIn"));
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
                                    shortlistedModel.setSelfIntroduction(jsonObject1.getString("SelfIntroduction"));
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
                                    shortlistedModel.setFoodType(jsonObject1.getString("foodtype"));
                                    shortlistedModel.setDrinkingHabit(jsonObject1.getString("drinkhabit"));
                                    shortlistedModel.setSmokingHabit(jsonObject1.getString("smokehabit"));
                                    shortlistedModel.setComplexion(jsonObject1.getString("complexion"));
                                    shortlistedModel.setBodyType(jsonObject1.getString("bodytype"));
                                    shortlistedModel.setPhysicalChallege(jsonObject1.getString("physicalchallenge"));

                                }

                                // setDataTOText();

                                tv_profileView_introduction.setText(shortlistedModel.getSelfIntroduction());
                                tv_profileView_physical_status.setText(shortlistedModel.getPhysicalChallege());
                                tv_profileView_eating_habit.setText(shortlistedModel.getFoodType());
                                tv_profileView_smoking_habit.setText(shortlistedModel.getSmokingHabit());
                                tv_profileView_drinking_habit.setText(shortlistedModel.getDrinkingHabit());
                                tv_profileView_eduction.setText(shortlistedModel.getEduction());
                                tv_profileView_eduction_in.setText(shortlistedModel.getEductionIn());
                                tv_profileView_working_with.setText(shortlistedModel.getWorkingWith());
                                tv_profileView_working_as.setText(shortlistedModel.getWorkingAs());


                                if (TextUtils.isEmpty(shortlistedModel.getShortlistedFlag()) || shortlistedModel.getShortlistedFlag().contains("0")) {
                                    imgShortlist.setVisibility(View.VISIBLE);
                                    imgShortlisted.setVisibility(View.GONE);
                                    tv_shortlist.setVisibility(View.VISIBLE);
                                    tv_shortlisted.setVisibility(View.GONE);
                                } else {

                                    imgShortlisted.setVisibility(View.VISIBLE);
                                    tv_shortlist.setVisibility(View.GONE);
                                    tv_shortlist.setVisibility(View.GONE);
                                    tv_shortlisted.setVisibility(View.VISIBLE);
                                }

                                if (TextUtils.isEmpty(shortlistedModel.getInvitedFlag()) || shortlistedModel.getInvitedFlag().contains("0")) {
                                    imgInvite.setVisibility(View.VISIBLE);
                                    imgInvited.setVisibility(View.GONE);
                                    tv_invite.setVisibility(View.VISIBLE);
                                    tv_invited.setVisibility(View.GONE);
                                } else {
                                    imgInvited.setVisibility(View.VISIBLE);
                                    imgInvite.setVisibility(View.GONE);
                                    tv_invite.setVisibility(View.GONE);
                                    tv_invited.setVisibility(View.VISIBLE);
                                }

                                imgShortlist.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        int receiverId = shortlistedModel.getUserMemberId();

                                        shortListPresenter.performShortlist("" + memberId, "" + receiverId, "1");

                                        imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        imgShortlist.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                                        tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                    }
                                });

                                imgShortlisted.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        int receiverId = shortlistedModel.getUserMemberId();

                                        shortListPresenter.performShortlist("" + memberId, "" + receiverId, "0");

                                        imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        imgShortlist.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                                        tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                    }
                                });


                                imgInvite.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        int senderId = shortlistedModel.getUserMemberId();

                                        invitePresenter.performInvite("" + memberId, "" + senderId, "1");

                                        imgInvite.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        imgInvited.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                                        tv_invite.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        tv_invited.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                    }
                                });

                                imgInvited.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        int senderId = shortlistedModel.getUserMemberId();

                                        invitePresenter.performInvite("" + memberId, "" + senderId, "0");

                                        imgInvite.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        imgInvited.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                                        tv_invite.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                        tv_invited.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                                    }
                                });


                                try {
                                    int year = Calendar.getInstance().get(Calendar.YEAR);

                                    String dob = shortlistedModel.getDateOfBirth();

                                    String[] dob1 = dob.split("/");

                                    String dateOfBirth = dob1[2];

                                    int age = (year - Integer.parseInt(dateOfBirth));

                                    shortlistedModel.setUserAge("" + age);
                                } catch (ArrayIndexOutOfBoundsException e) {

                                    e.printStackTrace();
                                }

                                tv_memberId.setText(shortlistedModel.getUniqueId());
                                String age_height_other = shortlistedModel.getUserAge() + ", " + shortlistedModel.getUserHeight() + ", " + shortlistedModel.getUserCity() +
                                        ", " + shortlistedModel.getUserState() + ", " + shortlistedModel.getUserCountry();
                                tv_otherDetails.setText(age_height_other);

                                String profileCreatedBy = shortlistedModel.getProfileCreatedBy();

                                if (profileCreatedBy.equals("C")) {

                                    tv_profile_created_by.setText(candidate);
                                    tv_profileView_profile_created_by.setText("Self");

                                } else {

                                    tv_profile_created_by.setText(parents);
                                    tv_profileView_profile_created_by.setText("Parent");
                                }


                                username = shortlistedModel.getFirstName() + " " + shortlistedModel.getLastName();
                                tv_profileView_name.setText(username);
                                mCoordinatorLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
                                mCoordinatorLayout.setTitle(username);
                                tv_profileView_gender.setText(shortlistedModel.getGender());
                                tv_profileView_age.setText(shortlistedModel.getUserAge());
                                tv_profileView_height.setText(shortlistedModel.getUserHeight());
                                tv_profileView_married_status.setText(shortlistedModel.getMarriedStatus());
                                tv_profileView_mother_tongue.setText(shortlistedModel.getMotherTongue());
                                tv_profileView_country.setText(shortlistedModel.getUserCountry());
                                tv_profileView_state.setText(shortlistedModel.getUserState());
                                tv_profileView_city.setText(shortlistedModel.getUserCity());
                                tv_profileView_birth_date.setText(shortlistedModel.getDateOfBirth());
                                tv_profileView_annual_income.setText(shortlistedModel.getSalary_details());
                                tv_profileView_family_type.setText(shortlistedModel.getFamily_type());
                                tv_profileView_father_status.setText(shortlistedModel.getFather_status());
                                tv_profileView_mother_status.setText(shortlistedModel.getMother_status());
                                tv_profileView_no_of_brother.setText(shortlistedModel.getNoOfBrothers());
                                tv_profileView_no_of_brother_married.setText(shortlistedModel.getNoOfBrotherMarried());
                                tv_profileView_no_of_sister.setText(shortlistedModel.getNoOfSisters());
                                tv_profileView_no_of_sister_married.setText(shortlistedModel.getNoOfSistersMarried());
                                tv_profileView_birth_time.setText(shortlistedModel.getBirthTime());
                                tv_profileView_birth_place.setText(shortlistedModel.getBirthPlae());
                                tv_profileView_gotra.setText(shortlistedModel.getGotra());
                                tv_profileView_rashi.setText(shortlistedModel.getRashi());
                                tv_profileView_nakshtra.setText(shortlistedModel.getNakshatra());
                                tv_profileView_charan.setText(shortlistedModel.getCharan());
                                tv_profileView_naadi.setText(shortlistedModel.getNaadi());
                                tv_profileView_gan.setText(shortlistedModel.getGan());
                                tv_profileView_body_type.setText(shortlistedModel.getBodyType());


                                if (shortlistedModel.getUserProfileUrl().isEmpty()) {

                                    Picasso.get()
                                            .load(R.drawable.img_preview)
                                            .placeholder(R.drawable.img_preview)
                                            .error(R.drawable.img_preview)
                                            .into(profileUrl);
                                } else {

                                    Picasso.get()
                                            .load(shortlistedModel.getUserProfileUrl())
                                            .placeholder(R.drawable.img_preview)
                                            .into(profileUrl);
                                }

                                if (shortlistedModel.getKundli_photo().length() > 42) {

                                    tv_profileView_kundli.setVisibility(View.VISIBLE);
                                    kundli_photo.setVisibility(View.VISIBLE);
                                    view_kundli_view.setVisibility(View.VISIBLE);

                                    Picasso.get()
                                            .load(shortlistedModel.getKundli_photo())
                                            .placeholder(R.drawable.img_preview)
                                            .error(R.drawable.img_preview)
                                            .into(kundli_photo);
                                } else {

                                    tv_profileView_kundli.setVisibility(View.GONE);
                                    kundli_photo.setVisibility(View.GONE);
                                    view_kundli_view.setVisibility(View.GONE);
                                }

                            } else {

                                Toast.makeText(GuestSearchDisplayActivity.this, "Invalid id...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "search error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                param.put(UNIQUE_ID, uniqueId);
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void showShortlisted(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showInvited(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
