package in.co.vsys.myssksamaj.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.contracts.AllUserInfoContract;
import in.co.vsys.myssksamaj.contracts.GetDataCountContract;
import in.co.vsys.myssksamaj.contracts.ImagesContract;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.ImagesDataModel;
import in.co.vsys.myssksamaj.model.PagerModel;
import in.co.vsys.myssksamaj.model.data_models.MyDataCountModel;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.presenters.AllUserInfoPresenter;
import in.co.vsys.myssksamaj.presenters.GetDataCountPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

public class MyProfileActivity extends AppCompatActivity implements AllUserInfoContract.AllUserInfoView {

    private Context mContext;
    private static final String TAG = MyProfileActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    public List<PagerModel> phList;
    private ProgressBar mProgressBar;
    private CircularImageView myProfileImage;
    private TextView tv_profilePercentage;
    private ProgressBar profilePercentageProgressBar;
    private TextView tv_memberName, tv_memberUniqueId, tv_myProfile_editProfile, tv_myProfile_inviteCount,
            tv_myProfile_shortlistCount, tv_myProfile_ViewCount;
    private ImageView img_basicInfo, img_profilePhoto, img_professionInf0, img_familyInfo, img_astroInfo,
            img_lookingInfo, img_lifeStyle, img_ViewProfile;
    //    private MemberModel memberModel;
    private LinearLayout layout_myProfileProfession, layout_myProfileViewProfile, layout_myProfileViewPhoto,
            layout_myProfileFAL, layout_myProfileLookingFor, layout_invite, layout_shortlist, layout_view;
    //    private String basicProfilePercentage, photoProfilePercentage, familyProfilePercentage,
//            professionProfilePercentage, horoscopeProfilePercentage, introductionProfilePercentage,
//            lookingForProfilePercentage, lifeStyleProfilePercentage;
    private int progress;
    private SharedPreferences mPreferences;
    private int memberId;
    private String accountCreatedBy;
    private AllUserInfoContract.AllUserInfoOps allUserInfoPresenter;
    private GetDataCountContract.GetDataCountOps getDataCountPresenter;
    private UserDetailsModel mUserDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_matri_profile);
        mContext = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.myProfile_toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.myProfile_progressBar);
        profilePercentageProgressBar = (ProgressBar) findViewById(R.id.row_percentProgressBar);
        tv_profilePercentage = (TextView) findViewById(R.id.row_percent_totalProgress);
        myProfileImage = (CircularImageView) findViewById(R.id.myProfile_profile);
        tv_memberName = (TextView) findViewById(R.id.myProfile_Name);
        tv_memberUniqueId = (TextView) findViewById(R.id.myProfile_memberId);
        tv_myProfile_inviteCount = (TextView) findViewById(R.id.tv_myProfile_inviteCount);
        tv_myProfile_shortlistCount = (TextView) findViewById(R.id.tv_myProfile_shortlistCount);
        tv_myProfile_ViewCount = (TextView) findViewById(R.id.tv_myProfile_ViewCount);
        img_basicInfo = (ImageView) findViewById(R.id.img_myProfile_basicInfo);
        img_profilePhoto = (ImageView) findViewById(R.id.img_myProfile_photo);
        img_professionInf0 = (ImageView) findViewById(R.id.img_myProfile_professionInfo);
        img_familyInfo = (ImageView) findViewById(R.id.img_myProfile_familyInfo);
        img_astroInfo = (ImageView) findViewById(R.id.img_myProfile_astroInfo);
        img_lookingInfo = (ImageView) findViewById(R.id.img_myProfile_lookingFor);
        img_lifeStyle = (ImageView) findViewById(R.id.img_myProfile_lifeStyle);
        img_ViewProfile = (ImageView) findViewById(R.id.img_myProfile_viewProfile);
        tv_myProfile_editProfile = (TextView) findViewById(R.id.tv_myProfile_editProfile);
        layout_myProfileFAL = (LinearLayout) findViewById(R.id.layout_myProfileFAL);
        layout_myProfileViewPhoto = (LinearLayout) findViewById(R.id.layout_myProfileViewPhoto);
        layout_myProfileLookingFor = (LinearLayout) findViewById(R.id.layout_myProfileLookingFor);
        layout_myProfileProfession = (LinearLayout) findViewById(R.id.layout_myProfileProfession);
        layout_myProfileViewProfile = (LinearLayout) findViewById(R.id.layout_myProfileViewProfile);
        layout_invite = (LinearLayout) findViewById(R.id.layout_myProfileInvite);
        layout_shortlist = (LinearLayout) findViewById(R.id.layout_myProfileShortlist);
        layout_view = (LinearLayout) findViewById(R.id.layout_myProfileView);


        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("My Profile Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        memberId = mPreferences.getInt("memberId", 0);
        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");
        Log.d(TAG, "memberId" + memberId);

        phList = new ArrayList<>();
//        memberModel = new MemberModel();

        if (accountCreatedBy.equals("P")) {
            layout_myProfileFAL.setVisibility(View.GONE);
            layout_myProfileLookingFor.setVisibility(View.GONE);
            layout_myProfileProfession.setVisibility(View.GONE);
            layout_myProfileViewPhoto.setVisibility(View.GONE);
            tv_profilePercentage.setVisibility(View.GONE);
            profilePercentageProgressBar.setVisibility(View.GONE);
            layout_myProfileViewProfile.setVisibility(View.GONE);
        } else {

            layout_myProfileFAL.setVisibility(View.VISIBLE);
            layout_myProfileLookingFor.setVisibility(View.VISIBLE);
            layout_myProfileProfession.setVisibility(View.VISIBLE);
            layout_myProfileViewPhoto.setVisibility(View.VISIBLE);
            tv_profilePercentage.setVisibility(View.VISIBLE);
            profilePercentageProgressBar.setVisibility(View.VISIBLE);
            layout_myProfileViewProfile.setVisibility(View.VISIBLE);
        }

        img_basicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicInfoPopUP();
            }
        });

        img_profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrimonyUtils.loadImages(mContext, memberId, new ImagesContract.ImagesView() {
                    @Override
                    public void showImages(ImagesDataModel imagesDataModel) {

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
                });
//                phList.clear();
//                loadImages(memberId);
            }
        });

        img_professionInf0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professionInfoPopUP();
            }
        });

        img_familyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familyInfoPopUP();
            }
        });

        img_astroInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                astroInfoPopUP();
            }
        });

        img_lookingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookingForInfoPopUP();
            }
        });

        tv_myProfile_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, EditMyProfileActivity.class));
            }
        });

        img_lifeStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeStyleInfo();
            }
        });

        layout_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accountCreatedBy.equals("P")) {
                    Toast.makeText(mContext, "You can not view Invitation", Toast.LENGTH_SHORT).show();
                } else if (accountCreatedBy.equals("C")) {
                    startActivity(new Intent(mContext, MatriInvitationActivity.class));
                }
            }
        });

        img_ViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ViewMyProfileActivity.class);
                intent.putExtra("uniqueId", mUserDetailsModel.getUniqueId());
                startActivity(intent);

                // startActivity(new Intent(mContext, ViewMyProfileActivity.class));
            }
        });

        layout_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ShortlistedActivity.class));
            }
        });

        layout_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountCreatedBy.equals("P")) {

                } else if (accountCreatedBy.equals("C")) {
                    startActivity(new Intent(mContext, ProfileVisitedListActivity.class));
                }
            }
        });
        allUserInfoPresenter = new AllUserInfoPresenter(this);
        allUserInfoPresenter.getAllUserInfoOps("" + memberId);
    }

    private void setProfileInfo() {
        try {
            if (Utilities.isEmpty(mUserDetailsModel.getMainProfilePhoto())) {
                Picasso.get()
                        .load(R.drawable.circle_preview)
                        .placeholder(R.drawable.circle_preview)
                        .error(R.drawable.circle_preview)
                        .into(myProfileImage);
            } else {
                Picasso.get()
                        .load(mUserDetailsModel.getMainProfilePhoto())
                        .placeholder(R.drawable.circle_preview)
                        .into(myProfileImage);

            }

            tv_memberUniqueId.setText(mUserDetailsModel.getUniqueId());
            String memberName = mUserDetailsModel.getFirstName() + " " + mUserDetailsModel.getLastName();
            tv_memberName.setText(memberName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void showProfileInfo() {
//
//        mProgressBar.setVisibility(View.VISIBLE);
//
//        allUserInfoPresenter.getAllUserInfoOps("" + memberId);

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.allInfoUsingMemeberIdUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        mProgressBar.setVisibility(View.INVISIBLE);
//                        Log.d(TAG, "all member info response" + response);
//
//                        try {
//                            JSONArray jsonArray;
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            int success = jsonObject.getInt("success");
//                            if (success == 1) {
//
//                                jsonArray = jsonObject.getJSONArray("data");
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    int memberId = object.getInt("MemberId");
//                                    String uniqueId = object.getString("UniqueId");
//                                    String accountCreatedBy = object.getString("AccountCreatedBy");
//                                    String firstName = object.getString("FirstName");
//                                    String lastName = object.getString("LastName");
//                                    String gender = object.getString("Gender");
//                                    String DOB = object.getString("DOB");
//                                    String mobileNumber = object.getString("Mobile");
//                                    String emailId = object.getString("EmailId");
//                                    String memeberHeight = object.getString("MemberHeight");
//                                    String memeberCountry = object.getString("MemberCountry");
//                                    String memberState = object.getString("MemberState");
//                                    String memberCity = object.getString("MemberCity");
//                                    String marriedStatus = object.getString("MarriedStatus");
//                                    String motherTongue = object.getString("MotherTongue");
//                                    String eduction = object.getString("MemberEducationName");
//                                    String eductionIn = object.getString("EducationInName");
//                                    String workingWith = object.getString("EducationWithName");
//                                    String workingAs = object.getString("MemberOccupation");
//                                    String memeberIncome = object.getString("MemberInCome");
//                                    String familyType = object.getString("FamilyType");
//                                    String fatherStatus = object.getString("fatherstatus");
//                                    String fatherCompany = object.getString("fathercompany");
//                                    String fatherPost = object.getString("fatherpost");
//                                    String motherStatus = object.getString("motherstatus");
//                                    String motherCompany = object.getString("mothercompany");
//                                    String motherPost = object.getString("motherpost");
//                                    String noOfBrother = object.getString("NoOfBorther");
//                                    String noOfBrotherMarried = object.getString("NoOfBortherMarried");
//                                    String noOfSister = object.getString("NoOfSister");
//                                    String noOfSisterMarried = object.getString("NoOfSisterMarried");
//                                    String birthTime = object.getString("BirthTime");
//                                    String birthPlace = object.getString("BirthPlace");
//                                    String gotra = object.getString("gotraname");
//                                    String manglik = object.getString("manglik");
//                                    String rashi = object.getString("Rashi");
//                                    String nakshtra = object.getString("Nakshtra");
//                                    String charan = object.getString("Charan");
//                                    String naddi = object.getString("Naddi");
//                                    String gan = object.getString("Gan");
//                                    String foodType = object.getString("foodtype");
//                                    String drinkHabit = object.getString("drinkhabit");
//                                    String smokeHabit = object.getString("smokehabit");
//                                    String complexion = object.getString("complexion");
//                                    String bodyType = object.getString("bodytype");
//                                    String physicalChallenge = object.getString("physicalchallenge");
//                                    String kundliPhoto = object.getString("KundaliPhoto");
//                                    String minHeight = object.getString("PartnerMinHeight");
//                                    String maxHeight = object.getString("PartnerMaxHeight");
//                                    String minAge = object.getString("PartnerMinAge");
//                                    String maxAge = object.getString("PartnerMaxAge");
//                                    String partnerMarriedStatus = object.getString("PartnerMarriedStatus");
//                                    String partnerEduction = object.getString("PartnerEducationType");
//                                    String partnerEductionIn = object.getString("PartnerEducationIn");
//                                    String partnerworkingWith = object.getString("PartnerEducationWith");
//                                    String partnerWorkingAs = object.getString("PartnerOccupation");
//                                    String partnerMotherTounge = object.getString("PartnerMotherTongue");
//                                    String partnercountry = object.getString("PartnerCountry");
//                                    String partnerState = object.getString("PartnerState");
//                                    String partnerCity = object.getString("PartnerCity");
//                                    String partnerIncome = object.getString("PartnerIncome");
//                                    String partnerEatingHabbit = object.getString("PartnerFoodType");
//                                    String partnerDringkHabbit = object.getString("PartnerDrinkHabit");
//                                    String partnerSmokingHabbit = object.getString("partnerSmokeHabit");
//                                    String partnerBodyType = object.getString("PartnerBodyType");
//                                    String partnerPhysicalChallenge = object.getString("PartnerPhysicalChallenge");
//                                    String partnerComplexion = object.getString("PartnerComplexion");
//                                    String partnerIntroduction = object.getString("PartnerIntroduction");
//                                    String selfIntroduction = object.getString("SelfIntroduction");
//                                    String introductionVideo = object.getString("IntroductionVideo");
//                                    String memberProfileUrl = object.getString("MainProfilePhoto");
//                                    memberModel.setCasteName(object.getString("Caste"));
//                                    memberModel.setSubCasteName(object.getString("Subcaste"));
//                                    memberModel.setIdentityPhoto(object.getString("Identity_Photo"));
//                                    memberModel.setBasicProfilePercentage("" + object.getString("basicinformationpercentage"));
//                                    memberModel.setPhotoProfilePercetage("" + object.getString("photoinformationpercentage"));
//                                    memberModel.setIntroductionProilePercetage("" + object.getString("candidateonformatiopercentage"));
//                                    memberModel.setFamilyProfilePercetage("" + object.getString("familyinformationpercentage"));
//                                    memberModel.setHigherEduProfilePercetage("" + object.getString("highereducationpercentage"));
//                                    memberModel.setDesiredPartnerPercetage("" + object.getString("desiredpartnerinformationpercentage"));
//                                    memberModel.setHoroscopeProfilePercetage("" + object.getString("horoscopeinformationpercentage"));
//                                    memberModel.setLifeStyleProfilePercetage("" + object.getString("lifeStyleInfoPercentage"));
//                                    memberModel.setUserMemberId(memberId);
//                                    memberModel.setUniqueId(uniqueId);
//                                    memberModel.setProfileCreatedBy(accountCreatedBy);
//                                    memberModel.setFirstName(firstName);
//                                    memberModel.setLastName(lastName);
//                                    memberModel.setGender(gender);
//                                    memberModel.setMobileNumber(mobileNumber);
//                                    memberModel.setEmailId(emailId);
//                                    memberModel.setUserHeight(memeberHeight);
//                                    memberModel.setUserCountry(memeberCountry);
//                                    memberModel.setUserState(memberState);
//                                    memberModel.setUserCity(memberCity);
//                                    memberModel.setMarriedStatus(marriedStatus);
//                                    memberModel.setMotherTongue(motherTongue);
//                                    memberModel.setEduction(eduction);
//                                    memberModel.setEductionIn(eductionIn);
//                                    memberModel.setWorkingWith(workingWith);
//                                    memberModel.setWorkingAs(workingAs);
//                                    memberModel.setUserProfileUrl(memberProfileUrl);
//                                    memberModel.setSalary_details(memeberIncome);
//                                    memberModel.setFamily_type(familyType);
//                                    memberModel.setFather_status(fatherStatus);
//                                    memberModel.setFather_company(fatherCompany);
//                                    memberModel.setFather_post(fatherPost);
//                                    memberModel.setMother_status(motherStatus);
//                                    memberModel.setMother_company(motherCompany);
//                                    memberModel.setMother_post(motherPost);
//                                    memberModel.setNoOfBrothers(noOfBrother);
//                                    memberModel.setNoOfSisters(noOfSister);
//                                    memberModel.setNoOfBrotherMarried(noOfBrotherMarried);
//                                    memberModel.setNoOfSistersMarried(noOfSisterMarried);
//                                    memberModel.setFoodType(foodType);
//                                    memberModel.setSmokingHabit(smokeHabit);
//                                    memberModel.setDrinkingHabit(drinkHabit);
//                                    memberModel.setComplexion(complexion);
//                                    memberModel.setBodyType(bodyType);
//                                    memberModel.setPhysicalChallege(physicalChallenge);
//                                    memberModel.setDateOfBirth(DOB);
//                                    memberModel.setBirthTime(birthTime);
//                                    memberModel.setBirthPlae(birthPlace);
//                                    memberModel.setGotra(gotra);
//                                    memberModel.setRashi(rashi);
//                                    memberModel.setNakshatra(nakshtra);
//                                    memberModel.setNaadi(naddi);
//                                    memberModel.setGan(gan);
//                                    memberModel.setCharan(charan);
//                                    memberModel.setManglik(manglik);
//                                    memberModel.setKundli_photo(kundliPhoto);
//                                    memberModel.setPartner_min_age(minAge);
//                                    memberModel.setPartner_max_age(maxAge);
//                                    memberModel.setPartner_min_height(minHeight);
//                                    memberModel.setPartner_max_height(maxHeight);
//                                    memberModel.setPartner_mother_tounge(partnerMotherTounge);
//                                    memberModel.setPartner_married_status(partnerMarriedStatus);
//                                    memberModel.setPartner_physical_status(partnerPhysicalChallenge);
//                                    memberModel.setPartner_eating_habits(partnerEatingHabbit);
//                                    memberModel.setPartner_smoking_habits(partnerSmokingHabbit);
//                                    memberModel.setPartner_drinking_habits(partnerDringkHabbit);
//                                    memberModel.setPartner_eduction(partnerEduction);
//                                    memberModel.setPartner_eduction_In(partnerEductionIn);
//                                    memberModel.setPartner_working_with(partnerworkingWith);
//                                    memberModel.setPartner_working_as(partnerWorkingAs);
//                                    memberModel.setPartner_income(partnerIncome);
//                                    memberModel.setPartner_country(partnercountry);
//                                    memberModel.setPartner_state(partnerState);
//                                    memberModel.setPartner_city(partnerCity);
//                                    memberModel.setParnter_body_type(partnerBodyType);
//                                    memberModel.setPartner_complexion(partnerComplexion);
//                                    memberModel.setSelectIntroduction(selfIntroduction);
//                                    memberModel.setPartner_introduction(partnerIntroduction);
//
//                                }
//
//                                try {
//
//                                    if (TextUtils.isEmpty(memberModel.getBasicProfilePercentage())) {
//
//                                        memberModel.setBasicProfilePercentage("20");
//                                    }
//
//                                    if (TextUtils.isEmpty(memberModel.getPhotoProfilePercetage())) {
//
//                                        memberModel.setPhotoProfilePercetage("0");
//                                    }
//                                    if (TextUtils.isEmpty(memberModel.getFamilyProfilePercetage())) {
//
//                                        memberModel.setFamilyProfilePercetage("0");
//                                    }
//                                    if (TextUtils.isEmpty(memberModel.getHigherEduProfilePercetage())) {
//
//                                        memberModel.setHigherEduProfilePercetage("0");
//                                    }
//                                    if (TextUtils.isEmpty(memberModel.getHoroscopeProfilePercetage())) {
//
//                                        memberModel.setHoroscopeProfilePercetage("0");
//                                    }
//                                    if (TextUtils.isEmpty(memberModel.getIntroductionProilePercetage())) {
//
//                                        memberModel.setIntroductionProilePercetage("0");
//                                    }
//                                    if (TextUtils.isEmpty(memberModel.getDesiredPartnerPercetage())) {
//
//                                        memberModel.setDesiredPartnerPercetage("0");
//                                    }
//
//                                    if (TextUtils.isEmpty(memberModel.getLifeStyleProfilePercetage())) {
//
//                                        memberModel.setLifeStyleProfilePercetage("0");
//                                    }
//
//                                    progress = Integer.parseInt(memberModel.getBasicProfilePercentage()) + Integer.parseInt(memberModel.getPhotoProfilePercetage()) + Integer.parseInt(memberModel.getFamilyProfilePercetage()) + Integer.parseInt(memberModel.getHigherEduProfilePercetage()) + Integer.parseInt(memberModel.getHoroscopeProfilePercetage()) + Integer.parseInt(memberModel.getIntroductionProilePercetage()) + Integer.parseInt(memberModel.getDesiredPartnerPercetage()) + Integer.parseInt(memberModel.getLifeStyleProfilePercetage());
//
//                                    profilePercentageProgressBar.setProgress(progress);
//
//                                    tv_profilePercentage.setText("Your profile is " + "" + progress + "% completed so far");
//
//                                } catch (NumberFormatException e) {
//                                    e.printStackTrace();
//                                }
//
//                                mPreferences.edit().putString("nav_profileUrl", memberModel.getUserProfileUrl()).apply();
//
//                                setProfileInfo();
//
//                                displayCount();
//
//                            } else {
//
//                                Toast.makeText(mContext, "Details not found...", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "memberInfo error " + error);
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> param = new HashMap<>();
//                param.put(MEMBER_ID, String.valueOf(memberId));
//                return param;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//    }

//    private void loadImages(final int mId) {
//
//        final ProgressDialog mDialog = new ProgressDialog(this);
//        mDialog.setMessage("Please wait...");
//        mDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.IMEAGES_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        mDialog.dismiss();
//
//                        Log.d(TAG, "images url response" + response);
//
//                        try {
//
//                            JSONArray jsonArray;
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            int success = jsonObject.getInt("success");
//
//                            if (success == 1) {
//
//                                jsonArray = jsonObject.getJSONArray("data");
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    String profileUlr = object.getString("MainProfilePhoto");
//                                    String photoOneUrl = object.getString("MemberPhoto1");
//                                    String photoTwoUrl = object.getString("MemberPhoto2");
//                                    String photoThreeUrl = object.getString("MemberPhoto3");
//                                    String photoFourUrl = object.getString("MemberPhoto4");
//                                    String photoFiveUrl = object.getString("MemberPhoto5");
//                                    String photoSixUrl = object.getString("MemberPhoto6");
//                                    String photoSevenUrl = object.getString("MemberPhoto7");
//                                    String photoEightUrl = object.getString("MemberPhoto8");
//                                    String photoNineUrl = object.getString("MemberPhoto9");
//                                    String photoTenUrl = object.getString("MemberPhoto10");
//
//                                    if (!TextUtils.isEmpty(profileUlr) && profileUlr.length() > 49) {
//
//                                        phList.add(new PagerModel(profileUlr));
//                                    }
//
//                                    if (!TextUtils.isEmpty(photoOneUrl) && photoOneUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoOneUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoTwoUrl) && photoTwoUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoTwoUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoThreeUrl) && photoThreeUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoThreeUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoFourUrl) && photoFourUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoFourUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoFiveUrl) && photoFiveUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoFiveUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoSixUrl) && photoSixUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoSixUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoSevenUrl) && photoSevenUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoSevenUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoEightUrl) && photoEightUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoEightUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoNineUrl) && photoNineUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoNineUrl));
//                                    }
//                                    if (!TextUtils.isEmpty(photoTenUrl) && photoTenUrl.length() > 49) {
//
//                                        phList.add(new PagerModel(photoTenUrl));
//                                    }
//                                }
//
//                                showPOpImages();
//
//                            } else {
//
//                                Toast.makeText(mContext, "image List not available", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.d(TAG, "photo url error" + error);
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> param = new HashMap<>();
//                param.put(MEMBER_ID, String.valueOf(mId));
//
//                return param;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//    }

//    private void showPOpImages() {
//
//        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.row_images_scroll);
//
//       /* List<PagerModel> arrList = new ArrayList<>();
//        arrList.add(new PagerModel("1", imageOne));
//        arrList.add(new PagerModel("2", imageTwo));
//        arrList.add(new PagerModel("3", imageThree));
//        arrList.add(new PagerModel("4", imageFour));*/
//
//        // ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(PopUpActivity.this, arrList);
//
//        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(mContext, images, R.layout.pager_layout,
//                new CustomPagerAdapter.ItemClickedListener() {
//                    @Override
//                    public void onItemClicked(View view, Object object, int position) {
//
//                    }
//
//                    @Override
//                    public void onViewBound(View view, Object object, int position) {
//                        String imageUrl = (String) object;
//                        PhotoView imageView = (PhotoView) view.findViewById(R.id.imagePagerView);
//
//                        if (Utilities.isEmpty(imageUrl)) {
//                            Picasso.get()
//                                    .load(R.drawable.img_preview)
//                                    .placeholder(R.drawable.img_preview)
//                                    .error(R.drawable.img_preview)
//                                    .into(imageView);
//                        } else {
//
//                            Picasso.get()
//                                    .load(imageUrl)
//                                    .placeholder(R.drawable.img_preview)
//                                    .into(imageView);
//                        }
//                    }
//                });
//
//
//        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(MyProfileActivity.this, phList);
//        ViewPager viewPager = (ViewPager) dialog.findViewById(R.id.image_viewPager);
//        viewPager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        CircleIndicator circleIndicator = (CircleIndicator) dialog.findViewById(R.id.circleIndictor);
//        circleIndicator.setViewPager(viewPager);
//
//        dialog.show();
//
//    }

    private void basicInfoPopUP() {

        //  LayoutInflater inflater = getLayoutInflater();
        // View basicView = inflater.inflate(R.layout.basic_info_layout, null);

        final Dialog basicView = new Dialog(this);
        basicView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        basicView.setContentView(R.layout.basic_info_layout);
        basicView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        basicView.show();
        basicView.setCancelable(true);

        Window window = basicView.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_memberName = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogName);
        TextView tv_uniqueId = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogUniqueId);
        TextView tv_gender = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogGender);
        TextView tv_DOB = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogDOB);
        TextView tv_age = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogAge);
        TextView tv_motherTounge = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogMTounge);
        TextView tv_marriedStatus = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogMStatus);
        TextView tv_mobileNumber = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogMNo);
        TextView tv_height = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogHeight);
        TextView tv_city = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogAddress);
        TextView tv_EmailId = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogEmailId);
        TextView tvCaste = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogCaste);
        TextView tvSubCaste = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogSubCaste);
        //  TextView tv_countyr = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogCountry);
        TextView tv_introduction = (TextView) basicView.findViewById(R.id.tv_Basic_alertDialogIntroduction);
        ImageView img_identy = (ImageView) basicView.findViewById(R.id.alertDialog_parentIdenty);
        LinearLayout layout_basicGendor = (LinearLayout) basicView.findViewById(R.id.layout_basicGendor);
        LinearLayout layout_basicDOB = (LinearLayout) basicView.findViewById(R.id.layout_basicDOB);
        LinearLayout layout_basicAge = (LinearLayout) basicView.findViewById(R.id.layout_basicAge);
        LinearLayout layout_basicHeight = (LinearLayout) basicView.findViewById(R.id.layout_basicHeight);
        LinearLayout layout_basicMotherTounge = (LinearLayout) basicView.findViewById(R.id.layout_basicMotherTounge);
        LinearLayout layout_basicMarriedStatus = (LinearLayout) basicView.findViewById(R.id.layout_basicMarriedStatus);
        CardView layout_basicIntroduction = (CardView) basicView.findViewById(R.id.layout_basicIntroduction);
        CardView layout_basicIdenty = (CardView) basicView.findViewById(R.id.layout_basicIdenty);

        if (accountCreatedBy.equals("P")) {

            layout_basicAge.setVisibility(View.GONE);
            layout_basicGendor.setVisibility(View.GONE);
            layout_basicHeight.setVisibility(View.GONE);
            layout_basicMotherTounge.setVisibility(View.GONE);
            layout_basicMarriedStatus.setVisibility(View.GONE);
            layout_basicIntroduction.setVisibility(View.GONE);
            layout_basicDOB.setVisibility(View.GONE);
            layout_basicIdenty.setVisibility(View.VISIBLE);

        } else {

            layout_basicAge.setVisibility(View.VISIBLE);
            layout_basicGendor.setVisibility(View.VISIBLE);
            layout_basicHeight.setVisibility(View.VISIBLE);
            layout_basicMotherTounge.setVisibility(View.VISIBLE);
            layout_basicMarriedStatus.setVisibility(View.VISIBLE);
            layout_basicIntroduction.setVisibility(View.VISIBLE);
            layout_basicDOB.setVisibility(View.VISIBLE);
            layout_basicIdenty.setVisibility(View.VISIBLE);
        }

        if (mUserDetailsModel != null) {

            if (accountCreatedBy.equals("C")) {

                mUserDetailsModel.setAge(MatrimonyUtils.getAge(mUserDetailsModel.getDOB()));

                tv_age.setText(mUserDetailsModel.getAge());

                tv_gender.setText(mUserDetailsModel.getGender());
                tv_DOB.setText(mUserDetailsModel.getDOB());

                tv_motherTounge.setText(mUserDetailsModel.getMotherTongue());
                tv_marriedStatus.setText(mUserDetailsModel.getMarriedStatus());

                tv_height.setText(mUserDetailsModel.getMemberHeight());

                tv_city.setText(mUserDetailsModel.getMemberCity() + ", " + mUserDetailsModel.getMemberState() + ", " + mUserDetailsModel.getMemberCountry());

                String introduction = mUserDetailsModel.getSelfIntroduction();

                if (!TextUtils.isEmpty(introduction)) {

                    tv_introduction.setText(introduction);
                } else {

                    tv_introduction.setText(getResources().getString(R.string.no_intro));
                    tv_introduction.setTextColor(getResources().getColor(R.color.colorHind));
                }
            }

//            tvCaste.setText("" + mUserDetailsModel.getCasteName());
            tvCaste.setVisibility(View.GONE);
//            tvSubCaste.setText("" + mUserDetailsModel.getSubCasteName());
            tvSubCaste.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(mUserDetailsModel.getIdentity_Photo()) && mUserDetailsModel.getIdentity_Photo().length() > 5) {

                Picasso.get()
                        .load(mUserDetailsModel.getIdentity_Photo())
                        .placeholder(R.drawable.id_preview)
                        .into(img_identy);
            } else {

                img_identy.setVisibility(View.GONE);
            }

            String memberName = mUserDetailsModel.getFirstName() + " " + mUserDetailsModel.getLastName();
            tv_memberName.setText(memberName);

            tv_uniqueId.setText(mUserDetailsModel.getUniqueId());
//            String email = mUserDetailsModel.getEmailId();
//            if (MainPresenter.getInstance().isPremiumMember(mContext))
//                email = Utilities.partiallyHideEmail(email);
//            tv_EmailId.setText(email);

            Utilities.setDataToTextView(tv_mobileNumber, mUserDetailsModel.getMobile());
            Utilities.setDataToTextView(tv_EmailId, mUserDetailsModel.getEmailId());

//            Utilities.showContactNumber(mContext, tv_mobileNumber, mUserDetailsModel.getMobile());
//            Utilities.showEmail(mContext, tv_EmailId, mUserDetailsModel.getEmailId());

//            tv_mobileNumber.setText(mUserDetailsModel.getMobile());
            tv_city.setText(mUserDetailsModel.getMemberHeight() + ", " + mUserDetailsModel.getMemberState() + ", " + mUserDetailsModel.getMemberCountry());
        }
    }

    private void professionInfoPopUP() {

        //  LayoutInflater inflater = getLayoutInflater();
        //   View professionView = inflater.inflate(R.layout.profession_info_layout, null);

        final Dialog dialogChoice = new Dialog(this);
        dialogChoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChoice.setContentView(R.layout.profession_info_layout);
        dialogChoice.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogChoice.show();
        dialogChoice.setCancelable(true);

        Window window = dialogChoice.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        TextView tv_eduction = (TextView) dialogChoice.findViewById(R.id.tv_Profession_alertDialogEduction);
        TextView tv_eductionIn = (TextView) dialogChoice.findViewById(R.id.tv_Profession_alertDialogEductionIn);
        TextView tv_workingWith = (TextView) dialogChoice.findViewById(R.id.tv_Profession_alertDialogWWith);
        TextView tv_workingAs = (TextView) dialogChoice.findViewById(R.id.tv_Profession_alertDialogWAs);
        TextView tv_annualIncome = (TextView) dialogChoice.findViewById(R.id.tv_Profession_alertDialogAIncome);

        if (mUserDetailsModel != null) {

            tv_eduction.setText(mUserDetailsModel.getEducationWithName());
            tv_eductionIn.setText(mUserDetailsModel.getEducationInName());
            tv_workingWith.setText(mUserDetailsModel.getMemberOccupation());
            tv_workingAs.setText(mUserDetailsModel.getMemberOccupation());
            tv_annualIncome.setText(mUserDetailsModel.getMemberInCome());
        }

        //  final AlertDialog.Builder aBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //  aBuilder.setTitle("Profession Info");
        //  aBuilder.setView(professionView);
        //  aBuilder.setCancelable(true);

        /*aBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });*/


        //    AlertDialog dialog = aBuilder.create();

       /* WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position*/

        //  dialog.show();

    }

    private void familyInfoPopUP() {

        //  LayoutInflater inflater = getLayoutInflater();
        //  View familyView = inflater.inflate(R.layout.family_info_layout, null);

        final Dialog familyView = new Dialog(this);
        familyView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        familyView.setContentView(R.layout.family_info_layout);
        familyView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        familyView.show();
        familyView.setCancelable(true);

        Window window = familyView.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_fatherStatus = (TextView) familyView.findViewById(R.id.tv_family_alertDialogFStatus);
        TextView tv_fatherCompany = (TextView) familyView.findViewById(R.id.tv_family_alertDialogFCompany);
        TextView tv_fatherPost = (TextView) familyView.findViewById(R.id.tv_family_alertDialogFPOst);
        TextView tv_motherStatus = (TextView) familyView.findViewById(R.id.tv_family_alertDialogMStatus);
        TextView tv_motherCompany = (TextView) familyView.findViewById(R.id.tv_family_alertDialogMCompany);
        TextView tv_motherPost = (TextView) familyView.findViewById(R.id.tv_family_alertDialogMPost);
        TextView tv_noOfBrothers = (TextView) familyView.findViewById(R.id.tv_family_alertDialogNoBrothers);
        TextView tv_noOfBMarried = (TextView) familyView.findViewById(R.id.tv_family_alertDialogNoBMarried);
        TextView tv_noOfSisters = (TextView) familyView.findViewById(R.id.tv_family_alertDialogNoSisters);
        TextView tv_noOfSMarried = (TextView) familyView.findViewById(R.id.tv_family_alertDialogNoSMarried);
        LinearLayout layout_father_company = (LinearLayout) familyView.findViewById(R.id.layout_fatherCompany);
        LinearLayout layout_father_post = (LinearLayout) familyView.findViewById(R.id.father_postLayout);
        LinearLayout layout_mother_company = (LinearLayout) familyView.findViewById(R.id.layout_motherCompany);
        LinearLayout layout_mother_post = (LinearLayout) familyView.findViewById(R.id.layout_motherPost);
        LinearLayout layout_noOfBrother_married = (LinearLayout) familyView.findViewById(R.id.layout_brother_married);
        LinearLayout layout_noOfSister_married = (LinearLayout) familyView.findViewById(R.id.layout_sister_married);


        if (mUserDetailsModel != null) {

            String father_status = mUserDetailsModel.getFatherstatus();
            tv_fatherStatus.setText(father_status);

            if ((father_status.equals("Employed")) || (father_status.equals("Business")) || (father_status.equals("Retired"))) {

                layout_father_company.setVisibility(View.VISIBLE);
                layout_father_post.setVisibility(View.VISIBLE);

                tv_fatherCompany.setText(mUserDetailsModel.getFathercompany());
                tv_fatherPost.setText(mUserDetailsModel.getFatherpost());

            }

            String mother_status = mUserDetailsModel.getMotherstatus();
            tv_motherStatus.setText(mother_status);
            if ((mother_status.equals("Employed")) || (mother_status.equals("Business")) || (mother_status.equals("Retired"))) {

                layout_mother_company.setVisibility(View.VISIBLE);
                layout_mother_post.setVisibility(View.VISIBLE);

                tv_motherCompany.setText(mUserDetailsModel.getMothercompany());
                tv_motherPost.setText(mUserDetailsModel.getMotherpost());
            }

            String noOfBrothers = mUserDetailsModel.getNoOfBorther();
            tv_noOfBrothers.setText(noOfBrothers);

            int nB = 0;

            try {

                nB = Integer.parseInt(noOfBrothers);

                if (nB > 0) {

                    layout_noOfBrother_married.setVisibility(View.VISIBLE);
                    tv_noOfBMarried.setText(mUserDetailsModel.getNoOfBortherMarried());
                }

            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            String noOfSisters = mUserDetailsModel.getNoOfSister();
            tv_noOfSisters.setText(noOfSisters);

            int nS = 0;
            try {
                nS = Integer.parseInt(noOfSisters);
                if (nS > 0) {
                    layout_noOfSister_married.setVisibility(View.VISIBLE);
                    tv_noOfSMarried.setText(mUserDetailsModel.getNoOfSisterMarried());
                }
            } catch (Exception nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }
        /*final AlertDialog.Builder aBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //  aBuilder.setTitle("Family Info");
        aBuilder.setView(familyView);
        aBuilder.setCancelable(true);*/

        /*aBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });*/


        // AlertDialog dialog = aBuilder.create();

       /* WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 100;  */ //y position

        //   dialog.show();

    }

    private void astroInfoPopUP() {

        //  LayoutInflater inflater = getLayoutInflater();
        // View astroView = inflater.inflate(R.layout.astro_info_layout, null);

        final Dialog astroView = new Dialog(this);
        astroView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        astroView.setContentView(R.layout.astro_info_layout);
        astroView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        astroView.show();
        astroView.setCancelable(true);

        Window window = astroView.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_DOB = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogDOB);
        TextView tv_BTime = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogBTime);
        TextView tv_BPlace = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogBPlace);
        TextView tv_gotra = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogGotra);
        TextView tv_rashi = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogRashi);
        TextView tv_nakshtra = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogNakstra);
        TextView tv_charan = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogCharan);
        TextView tv_naadi = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogNaadi);
        TextView tv_manglik = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogManglik);
        TextView tv_gan = (TextView) astroView.findViewById(R.id.tv_astro_alertDialogGan);
        ImageView img_kundli_image = (ImageView) astroView.findViewById(R.id.img_astro_alertDialogKImage);
        CardView cardView = astroView.findViewById(R.id.cardView_astroAlertKundli);


        if (mUserDetailsModel != null) {

            tv_DOB.setText(mUserDetailsModel.getDOB());
            tv_BTime.setText(mUserDetailsModel.getBirthTime());
            tv_BPlace.setText(mUserDetailsModel.getBirthPlace());
            tv_gotra.setText(mUserDetailsModel.getGotra());
            tv_rashi.setText(mUserDetailsModel.getRashi());
            tv_nakshtra.setText(mUserDetailsModel.getNakshtra());
            tv_charan.setText(mUserDetailsModel.getCharan());
            tv_naadi.setText(mUserDetailsModel.getNaddi());
            tv_gan.setText(mUserDetailsModel.getGan());
            tv_manglik.setText(mUserDetailsModel.getManglik());

            String kundli_url = mUserDetailsModel.getKundaliPhoto();


            if (kundli_url == null || TextUtils.isEmpty(kundli_url) || kundli_url.length() < 6) {

                cardView.setVisibility(View.GONE);
                Picasso.get()
                        .load(R.drawable.kundli_preview)
                        .placeholder(R.drawable.kundli_preview)
                        .into(img_kundli_image);
                // MatriNearByActivity.userProfileUrl = "" + mUserDetailsModel.getKundli_photo();
            } else {

                cardView.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(kundli_url)
                        .placeholder(R.drawable.kundli_preview)
                        .into(img_kundli_image);
                //  mPreferences.edit().putString("img_url", kundli_url).apply();

            }


        }

      /*  final AlertDialog.Builder aBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //  aBuilder.setTitle("Astro Info");
        aBuilder.setView(astroView);
        aBuilder.setCancelable(true);*/

        /*aBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });*/


        /*AlertDialog dialog = aBuilder.create();

         *//*WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position*//*

        dialog.show();*/

    }

    private void lookingForInfoPopUP() {

        /*LayoutInflater inflater = getLayoutInflater();
        View lookingView = inflater.inflate(R.layout.looking_for_layout, null);*/

        final Dialog lookingView = new Dialog(this);
        lookingView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lookingView.setContentView(R.layout.looking_for_layout);
        lookingView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lookingView.show();
        lookingView.setCancelable(true);

        Window window = lookingView.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_age = lookingView.findViewById(R.id.tv_looking_maxAge);
        TextView tv_height = lookingView.findViewById(R.id.tv_looking_Height);
        TextView tv_marriedStatus = lookingView.findViewById(R.id.tv_looking_married);
        TextView tv_motherToungue = lookingView.findViewById(R.id.tv_looking_motherT);
        TextView tv_country = lookingView.findViewById(R.id.tv_looking_country);
        TextView tv_state = lookingView.findViewById(R.id.tv_looking_state);
        TextView tv_city = lookingView.findViewById(R.id.tv_looking_city);
        TextView tv_eduction = lookingView.findViewById(R.id.tv_looking_eduction);
        TextView tv_eduction_in = lookingView.findViewById(R.id.tv_looking_eductionId);
        TextView tv_working_with = lookingView.findViewById(R.id.tv_looking_workingWith);
        TextView tv_working_as = lookingView.findViewById(R.id.tv_looking_workingAs);
        TextView tv_annual_income = lookingView.findViewById(R.id.tv_looking_annualIncome);
        TextView tv_eatingHabit = lookingView.findViewById(R.id.tv_looking_eatingHabit);
        TextView tv_smokingHabit = lookingView.findViewById(R.id.tv_looking_smoking);
        TextView tv_drinkingHabit = lookingView.findViewById(R.id.tv_looking_drinking);
        TextView tv_bodyType = lookingView.findViewById(R.id.tv_looking_bodyType);
        TextView tv_introduction = lookingView.findViewById(R.id.tv_looking_introduction);
        TextView tv_complexion = lookingView.findViewById(R.id.tv_looking_complexion);
        TextView tv_physicalChallenged = lookingView.findViewById(R.id.tv_looking_physicalChallenged);

        if (mUserDetailsModel != null) {

            String minHeight = mUserDetailsModel.getPartnerMinHeight();
            String maxHeight = mUserDetailsModel.getPartnerMaxHeight();

            if (!TextUtils.isEmpty(minHeight) && minHeight.length() > 0) {

                minHeight = minHeight.replace(" Ft", "");
            }

            if (!TextUtils.isEmpty(maxHeight) && maxHeight.length() > 0) {

                maxHeight = maxHeight.replace(" Ft", "");
            }

            tv_height.setText("" + minHeight + " to " + maxHeight);
            tv_age.setText("" + mUserDetailsModel.getPartnerMinAge() + " to " + mUserDetailsModel.getPartnerMaxAge());
            tv_marriedStatus.setText("" + mUserDetailsModel.getPartnerMarriedStatus());
            tv_motherToungue.setText("" + mUserDetailsModel.getPartnerMotherTongue());
            tv_country.setText("" + mUserDetailsModel.getPartnerCountry());
            tv_state.setText("" + mUserDetailsModel.getPartnerState());
            tv_city.setText("" + mUserDetailsModel.getPartnerCity());
            tv_eduction.setText("" + mUserDetailsModel.getPartnerEducationType());
            tv_eduction_in.setText("" + mUserDetailsModel.getPartnerEducationIn());
            tv_working_with.setText("" + mUserDetailsModel.getPartnerEducationWith());
            tv_working_as.setText("" + mUserDetailsModel.getPartnerOccupation());
            tv_annual_income.setText("" + mUserDetailsModel.getPartnerIncome());
            tv_eatingHabit.setText("" + mUserDetailsModel.getPartnerFoodType());
            tv_smokingHabit.setText("" + mUserDetailsModel.getPartnerSmokeHabit());
            tv_drinkingHabit.setText("" + mUserDetailsModel.getPartnerDrinkHabit());
            tv_bodyType.setText("" + mUserDetailsModel.getPartnerBodyType());
            tv_complexion.setText("" + mUserDetailsModel.getPartnerComplexion());
            tv_physicalChallenged.setText("" + mUserDetailsModel.getPartnerPhysicalChallenge());
            tv_introduction.setText("" + mUserDetailsModel.getPartnerIntroduction());
        }
    }

    private void lifeStyleInfo() {

        // LayoutInflater inflater = getLayoutInflater();
        // View basicView = inflater.inflate(R.layout.life_style_info, null);

        final Dialog basicView = new Dialog(this);
        basicView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        basicView.setContentView(R.layout.life_style_info);
        basicView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        basicView.show();
        basicView.setCancelable(true);

        Window window = basicView.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_foodType = basicView.findViewById(R.id.tv_lifeStyle_alertDialogFoodType);
        TextView tv_drinking = basicView.findViewById(R.id.tv_lifeStyle_alertDialogDrinking);
        TextView tv_smoking = basicView.findViewById(R.id.tv_lifeStyle_alertDialogSmoking);
        TextView tv_complexion = basicView.findViewById(R.id.tv_lifeStyle_alertDialogComplexion);
        TextView tv_bodyType = basicView.findViewById(R.id.tv_lifeStyle_alertDialogBodyType);
        TextView tv_physicalChallege = basicView.findViewById(R.id.tv_lifeStyle_alertDialogPhysical);


        if (mUserDetailsModel != null) {

            tv_foodType.setText(mUserDetailsModel.getFoodtype());
            tv_drinking.setText(mUserDetailsModel.getDrinkhabit());
            tv_smoking.setText(mUserDetailsModel.getSmokehabit());
            tv_complexion.setText(mUserDetailsModel.getComplexion());
            tv_bodyType.setText(mUserDetailsModel.getBodytype());
            tv_physicalChallege.setText(mUserDetailsModel.getPhysicalchallenge());
        }


        /*final AlertDialog.Builder aBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        aBuilder.setView(basicView);
        aBuilder.setCancelable(true);

        *//*aBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });*//*


        AlertDialog dialog = aBuilder.create();
        dialog.show();*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void displayCount() {

        getDataCountPresenter = new GetDataCountPresenter(new GetDataCountContract.GetDataCountView() {
            @Override
            public void showDataCount(MyDataCountModel myDataCountModel) {
                try {
//                    if (TextUtils.isEmpty(mUserDetailsModel.getBasicinformationpercentage())) {
//                        mUserDetailsModel.setBasicinformationpercentage("20");
//                    }
//
//                    if (TextUtils.isEmpty(mUserDetailsModel.getPhotoinformationpercentage())) {
//
//                        mUserDetailsModel.setPhotoinformationpercentage("0");
//                    }
//                    if (TextUtils.isEmpty(mUserDetailsModel.getFamilyinformationpercentage())) {
//
//                        mUserDetailsModel.setFamilyinformationpercentage("0");
//                    }
//                    if (TextUtils.isEmpty(mUserDetailsModel.getHighereducationpercentage())) {
//
//                        mUserDetailsModel.setHighereducationpercentage("0");
//                    }
//                    if (TextUtils.isEmpty(mUserDetailsModel.getHoroscopeinformationpercentage())) {
//
//                        mUserDetailsModel.setHoroscopeinformationpercentage("0");
//                    }
//                    if (TextUtils.isEmpty(mUserDetailsModel.getCandidateonformatiopercentage())) {
//
//                        mUserDetailsModel.setCandidateonformatiopercentage("0");
//                    }
//                    if (TextUtils.isEmpty(mUserDetailsModel.getDesiredpartnerinformationpercentage())) {
//
//                        mUserDetailsModel.setDesiredpartnerinformationpercentage("0");
//                    }
//
//                    if (TextUtils.isEmpty(mUserDetailsModel.getLifeStyleInfoPercentage())) {
//
//                        mUserDetailsModel.setLifeStyleInfoPercentage("0");
//                    }
//
//                    progress = Integer.parseInt(mUserDetailsModel.getBasicinformationpercentage())
//                            + Integer.parseInt(mUserDetailsModel.getMainProfilePhoto())
//                            + Integer.parseInt(mUserDetailsModel.getFamilyinformationpercentage())
//                            + Integer.parseInt(mUserDetailsModel.getHighereducationpercentage())
//                            + Integer.parseInt(mUserDetailsModel.getHoroscopeinformationpercentage())
//                            + Integer.parseInt(mUserDetailsModel.getCandidateonformatiopercentage())
//                            + Integer.parseInt(mUserDetailsModel.getDesiredpartnerinformationpercentage())
//                            + Integer.parseInt(mUserDetailsModel.getLifeStyleInfoPercentage());

                    if (accountCreatedBy.equals("P")) {
                        tv_myProfile_inviteCount.setText("0");
                        tv_myProfile_shortlistCount.setText("" + myDataCountModel.getYour_ShortList_Count());
                        tv_myProfile_ViewCount.setText("0");

                    } else if (accountCreatedBy.equals("C")) {

                        tv_myProfile_inviteCount.setText("" + myDataCountModel.getYou_Accept_SendRequest_Count());
                        tv_myProfile_shortlistCount.setText("" + myDataCountModel.getYour_ShortList_Count());
                        tv_myProfile_ViewCount.setText("" + myDataCountModel.getYou_Visted_Profile_Count());
                    }

                    profilePercentageProgressBar.setProgress(progress);

                    tv_profilePercentage.setText("Your profile is " + "" + progress + "% completed so far");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mPreferences.edit().putString("nav_profileUrl", mUserDetailsModel.getMainProfilePhoto()).apply();
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
        });
        getDataCountPresenter.getDataCountResponse("" + memberId);
    }

    @Override
    public void showAllUserInfo(UserDetailsModel userDetailsModel) {
        try {
            mUserDetailsModel = userDetailsModel;
            if (mUserDetailsModel == null)
                return;

//            if (!Utilities.isEmpty(mUserDetailsModel.getBasicinformationpercentage())) {
//                userDetailsModel.setBasicinformationpercentage("20");
//            }
//
//            if (!Utilities.isEmpty(mUserDetailsModel.getPhotoinformationpercentage())) {
//                userDetailsModel.setPhotoinformationpercentage("0");
//            }
//            if (!Utilities.isEmpty(mUserDetailsModel.getFamilyinformationpercentage())) {
//                userDetailsModel.setFamilyinformationpercentage("0");
//            }
//            if (!Utilities.isEmpty(mUserDetailsModel.getHighereducationpercentage())) {
//                userDetailsModel.setHighereducationpercentage("0");
//            }
//            if (!Utilities.isEmpty(mUserDetailsModel.getHoroscopeinformationpercentage())) {
//                userDetailsModel.setHoroscopeinformationpercentage("0");
//            }
//            if (!Utilities.isEmpty(mUserDetailsModel.getCandidateonformatiopercentage())) {
//                userDetailsModel.setCandidateonformatiopercentage("0");
//            }
//            if (!Utilities.isEmpty(mUserDetailsModel.getDesiredpartnerinformationpercentage())) {
//                userDetailsModel.setDesiredpartnerinformationpercentage("0");
//            }
//            if (!Utilities.isEmpty(mUserDetailsModel.getLifeStyleInfoPercentage())) {
//                userDetailsModel.setLifeStyleInfoPercentage("0");
//            }
            progress = Utilities.getInt(mUserDetailsModel.getBasicinformationpercentage())
                    + Utilities.getInt(mUserDetailsModel.getPhotoinformationpercentage())
                    + Utilities.getInt(mUserDetailsModel.getFamilyinformationpercentage())
                    + Utilities.getInt(mUserDetailsModel.getHighereducationpercentage())
                    + Utilities.getInt(mUserDetailsModel.getHoroscopeinformationpercentage())
                    + Utilities.getInt(mUserDetailsModel.getCandidateonformatiopercentage())
                    + Utilities.getInt(mUserDetailsModel.getDesiredpartnerinformationpercentage())
                    + Utilities.getInt(mUserDetailsModel.getLifeStyleInfoPercentage());

            profilePercentageProgressBar.setProgress(progress);
            tv_profilePercentage.setText("Your profile is " + "" + progress + "% completed so far");

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPreferences.edit().putString("nav_profileUrl", mUserDetailsModel.getMainProfilePhoto()).apply();
        setProfileInfo();
        displayCount();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}