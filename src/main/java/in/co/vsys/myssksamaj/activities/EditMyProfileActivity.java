package in.co.vsys.myssksamaj.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.MemberModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditMyProfileActivity extends AppCompatActivity {

    private static final String TAG = EditMyProfileActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    private MemberModel memberModel;
    private ImageView img_edit_basicInfo, img_edit_photo, img_edit_professionInfo, img_edit_familyInfo, img_edit_astroInfo, img_edit_lookingFor, img_edit_lifeStyle, img_edit_introduction;
    private CircleImageView my_edit_profile;
    private TextView my_edit_memberName, my_edit_memberId;
    private ProgressBar mProgressBar;
    private SharedPreferences mPreferences;
    private int memberId;
    private String accountCreatedBy;
    private LinearLayout layout_myProfileProfession, layout_myProfileViewPhoto, layout_myProfileFAL, layout_lifeStyle;
    private boolean isPaused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_edit_profile);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.myeditProfile_toolbar);

        img_edit_basicInfo = (ImageView) findViewById(R.id.img_edit_basicInfo);
        img_edit_photo = (ImageView) findViewById(R.id.img_edit_photo);
        img_edit_professionInfo = (ImageView) findViewById(R.id.img_edit_professionInfo);
        img_edit_familyInfo = (ImageView) findViewById(R.id.img_edit_familyInfo);
        img_edit_astroInfo = (ImageView) findViewById(R.id.img_edit_astroInfo);
        img_edit_lookingFor = (ImageView) findViewById(R.id.img_edit_lookingFor);
        img_edit_lifeStyle = (ImageView) findViewById(R.id.img_edit_lifeStyle);
        img_edit_introduction = (ImageView) findViewById(R.id.img_edit_introduction);
        my_edit_profile = (CircleImageView) findViewById(R.id.my_edit_profile);
        my_edit_memberName = (TextView) findViewById(R.id.my_edit_memberName);
        my_edit_memberId = (TextView) findViewById(R.id.my_edit_memberId);
        mProgressBar = (ProgressBar) findViewById(R.id.my_edit_progressBar);
        layout_myProfileFAL = (LinearLayout) findViewById(R.id.layout_myProfileFAL);
        layout_myProfileViewPhoto = (LinearLayout) findViewById(R.id.layout_myProfileViewPhoto);
        layout_myProfileProfession = (LinearLayout) findViewById(R.id.layout_myProfileProfession);
        layout_lifeStyle = (LinearLayout) findViewById(R.id.layout_lifeStyle);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Edit Profile Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        memberId = mPreferences.getInt("memberId", 0);

        showProfileInfo();

        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");

        if (accountCreatedBy.equals("P")) {

            layout_myProfileFAL.setVisibility(View.GONE);
            layout_lifeStyle.setVisibility(View.GONE);
            layout_myProfileProfession.setVisibility(View.GONE);

        } else {

            layout_myProfileFAL.setVisibility(View.VISIBLE);
            layout_lifeStyle.setVisibility(View.VISIBLE);
            layout_myProfileProfession.setVisibility(View.VISIBLE);
            layout_myProfileViewPhoto.setVisibility(View.VISIBLE);
        }

        img_edit_basicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (memberModel == null || memberModel.getGender() == null)
                        return;

                    String gender = memberModel.getGender();
                    String fistName = memberModel.getFirstName();
                    String middleName=memberModel.getFatherName();
                    String lastName = memberModel.getLastName();
                    String mobileNumber = memberModel.getMobileNumber();
                    String emailId = memberModel.getEmailId();
                    String dob = memberModel.getDateOfBirth();
                    String country = memberModel.getUserCountry();
                    String state = memberModel.getUserState();
                    String city = memberModel.getUserCity();
                    String mTounge = memberModel.getMotherTongue();
                    String height = memberModel.getUserHeight();
                    String marriedStatus = memberModel.getMarriedStatus();

                    Intent intent = new Intent(EditMyProfileActivity.this, EditBasicInfoActivity.class);

                    if (fistName != null) {

                        intent.putExtra("edit_firstName", fistName);
                    }
                    if (middleName != null) {

                        intent.putExtra("edit_middleName", middleName);
                    }

                    if (gender != null) {

                        intent.putExtra("edit_gender", gender);
                    }
                    if (lastName != null) {

                        intent.putExtra("edit_lastName", lastName);
                    }
                    if (mobileNumber != null) {

                        intent.putExtra("edit_mobileNumber", mobileNumber);
                    }
                    if (emailId != null) {

                        intent.putExtra("edit_emailId", emailId);
                    }

                    if (dob != null) {

                        intent.putExtra("edit_dob", dob);
                    }

                    if (country != null) {

                        intent.putExtra("edit_country", country);
                    }

                    if (state != null) {

                        intent.putExtra("edit_state", state);
                    }

                    if (city != null) {

                        intent.putExtra("edit_city", city);
                    }

                    if (mTounge != null) {

                        intent.putExtra("edit_mTongue", mTounge);
                    }

                    if (height != null) {

                        intent.putExtra("edit_height", height);
                    }

                    if (marriedStatus != null) {

                        intent.putExtra("edit_marriedStatus", marriedStatus);
                    }

                    if (memberModel.getMemberCountryId() != null) {

                        intent.putExtra("edit_memberCountryId", memberModel.getMemberCountryId());
                    }

                    if (memberModel.getMemberStateId() != null) {

                        intent.putExtra("edit_memberStateId", memberModel.getMemberStateId());
                    }

                    if (memberModel.getMemberCityId() != null) {

                        intent.putExtra("edit_memberCityId", memberModel.getMemberCityId());
                    }

                    if (memberModel.getMemberMotherTongueId() != null) {

                        intent.putExtra("edit_memberMotherTongue", memberModel.getMemberMotherTongueId());
                    }

                    if (memberModel.getProfileCreatedBy() != null) {

                        intent.putExtra("edit_memberAccountCreatedBy", memberModel.getProfileCreatedBy());
                    }

                    if (memberModel.getProfileManagedBy() != null) {

                        intent.putExtra("edit_memberAccountManagedBy", memberModel.getProfileManagedBy());
                    }

                    if (memberModel.getIdentityPhoto() != null) {

                        intent.putExtra("edit_memberIdentityImage", memberModel.getIdentityPhoto());
                    }

//                    if (memberModel.getCasteName() != null) {
//
//                        intent.putExtra("edit_memberCaste", memberModel.getCasteName());
//                    }
//
//                    if (memberModel.getSubCasteName() != null) {
//
//                        intent.putExtra("edit_memberSubCaste", memberModel.getSubCasteName());
//                    }

                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        img_edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditMyProfileActivity.this, UploadImagesActivity.class));
            }
        });

        img_edit_professionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eduction = memberModel.getEduction();
                String eductionIn = memberModel.getEductionIn();
                String workWith = memberModel.getWorkingWith();
                String workAs = memberModel.getWorkingAs();
                String annualIncome = memberModel.getSalary_details();

                Intent intent = new Intent(EditMyProfileActivity.this, EditProfessionInfoActivity.class);

                if (eduction != null) {

                    intent.putExtra("edit_eduction", eduction);
                }

                if (eductionIn != null) {

                    intent.putExtra("edit_eductionIn", eductionIn);
                }
                if (workWith != null) {

                    intent.putExtra("edit_workWith", workWith);
                }
                if (workAs != null) {

                    intent.putExtra("edit_workAs", workAs);
                }
                if (annualIncome != null) {

                    intent.putExtra("edit_annualIncome", annualIncome);
                }


                startActivity(intent);
            }
        });

        img_edit_familyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditMyProfileActivity.this, EditFamilyInfoActivity.class);

                if (memberModel.getFamily_type() != null) {

                    intent.putExtra("edit_familyType", memberModel.getFamily_type());
                }

                if (memberModel.getFather_status() != null) {

                    intent.putExtra("edit_fatherStatus", memberModel.getFather_status());
                }
                if (memberModel.getFather_post() != null) {

                    intent.putExtra("edit_fatherPost", memberModel.getFather_post());
                }
                if (memberModel.getFather_company() != null) {

                    intent.putExtra("edit_fatherCompany", memberModel.getFather_company());
                }

                if (memberModel.getMother_status() != null) {

                    intent.putExtra("edit_motherStatus", memberModel.getMother_status());
                }

                if (memberModel.getMother_post() != null) {

                    intent.putExtra("edit_motherPost", memberModel.getMother_post());
                }

                if (memberModel.getMother_company() != null) {

                    intent.putExtra("edit_motherCompany", memberModel.getMother_company());
                }

                if (memberModel.getNoOfBrothers() != null) {

                    intent.putExtra("edit_noOfBrothers", memberModel.getNoOfBrothers());
                }

                if (memberModel.getNoOfBrotherMarried() != null) {

                    intent.putExtra("edit_noOfBrotherMarried", memberModel.getNoOfBrotherMarried());
                }

                if (memberModel.getNoOfSisters() != null) {

                    intent.putExtra("edit_noOfSisters", memberModel.getNoOfSisters());
                }

                if (memberModel.getNoOfSistersMarried() != null) {

                    intent.putExtra("edit_noOfSisterMarried", memberModel.getNoOfSistersMarried());
                }


                startActivity(intent);
            }
        });

        img_edit_astroInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditMyProfileActivity.this, EditAstroInfoActivity.class);

                if (memberModel.getBirthTime() != null) {

                    intent.putExtra("edit_birthTime", memberModel.getBirthTime());
                }

                if (memberModel.getBirthPlae() != null) {

                    intent.putExtra("edit_birthPlace", memberModel.getBirthPlae());
                }

                if (memberModel.getGotra() != null) {

                    intent.putExtra("edit_gotra", memberModel.getGotra());
                }

                if (memberModel.getRashi() != null) {

                    intent.putExtra("edit_rashi", memberModel.getRashi());
                }

                if (memberModel.getNakshatra() != null) {

                    intent.putExtra("edit_nakshtra", memberModel.getNakshatra());
                }

                if (memberModel.getCharan() != null) {

                    intent.putExtra("edit_charan", memberModel.getCharan());
                }

                if (memberModel.getNaadi() != null) {

                    intent.putExtra("edit_naadi", memberModel.getNaadi());
                }
                if (memberModel.getGan() != null) {

                    intent.putExtra("edit_gan", memberModel.getGan());
                }

                if (memberModel.getKundli_photo() != null) {

                    intent.putExtra("edit_kundliPhoto", memberModel.getKundli_photo());
                }

                if (memberModel.getManglik() != null) {

                    intent.putExtra("edit_manglik", memberModel.getManglik());
                }
                if(memberModel.getMemberOccupationID()!=null){
                    intent.putExtra("edit_occupation_id",memberModel.getMemberOccupationID());
                }
                if(memberModel.getPhysicalChallege()!=null){
                    intent.putExtra("edit_physicalchallenge",memberModel.getPhysicalChallege());
                }
                if(memberModel.getMemberInComeID()!=null){
                    intent.putExtra("edit_income_id",memberModel.getMemberInComeID());
                }

                startActivity(intent);
            }
        });

        img_edit_lookingFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditMyProfileActivity.this, EditLookingForActivity.class);

                intent.putExtra("edit_pMinAge", memberModel.getPartner_min_age());
                intent.putExtra("edit_pMaxAge", memberModel.getPartner_max_age());
                intent.putExtra("edit_pMinHeight", memberModel.getPartner_min_height());
                intent.putExtra("edit_pMaxHeight", memberModel.getPartner_max_height());
                intent.putExtra("edit_pMarriedStatus", memberModel.getPartner_married_status());
                intent.putExtra("edit_pMotherTounge", memberModel.getPartner_mother_tounge());
                intent.putExtra("edit_pCountry", memberModel.getPartner_country());
                intent.putExtra("edit_pState", memberModel.getPartner_state());
                intent.putExtra("edit_pCity", memberModel.getPartner_city());
                intent.putExtra("edit_pDietaryHabit", memberModel.getPartner_eating_habits());
                intent.putExtra("edit_pDrinkingHabit", memberModel.getPartner_drinking_habits());
                intent.putExtra("edit_pSmokingHabit", memberModel.getPartner_smoking_habits());
                intent.putExtra("edit_pBodyType", memberModel.getParnter_body_type());
                intent.putExtra("edit_partner_introduction", memberModel.getPartner_introduction());
                intent.putExtra("edit_parnter_eduction", memberModel.getPartner_eduction());
                intent.putExtra("edit_partner_eductionIn", memberModel.getPartner_eduction_In());
                intent.putExtra("edit_partner_workWith", memberModel.getPartner_working_with());
                intent.putExtra("edit_partner_workAs", memberModel.getPartner_working_as());
                intent.putExtra("edit_partner_income", memberModel.getPartner_income());
                intent.putExtra("edit_partner_motherTongueId", memberModel.getMotherToungeSelectedId());
                intent.putExtra("edit_partner_eductionId", memberModel.getEductionSelectedId());
                intent.putExtra("edit_partner_countryId", memberModel.getPartnerSelectedCountryId());
                intent.putExtra("edit_partner_stateId", memberModel.getPartnerSelectedStateId());
                intent.putExtra("edit_partner_cityId", memberModel.getPartnerSelectedCityId());
                intent.putExtra("edit_partner_eductionInId", memberModel.getPartnerEductionInId());
                intent.putExtra("edit_partner_workingWithId", memberModel.getPartnerWorkingWithId());
                intent.putExtra("edit_partner_workingAsId", memberModel.getPartnerWorkingAsId());
                intent.putExtra("edit_partner_complexion", memberModel.getPartner_complexion());
                intent.putExtra("edit_partner_physicalChallenged", memberModel.getPartner_physical_status());


                startActivity(intent);
            }
        });

        img_edit_lifeStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditMyProfileActivity.this, EditLifeStyleActivity.class);

                intent.putExtra("edit_lifeStyle_eating", memberModel.getFoodType());
                intent.putExtra("edit_lifeStyle_drinking", memberModel.getDrinkingHabit());
                intent.putExtra("edit_lifeStyle_smoking", memberModel.getSmokingHabit());
                intent.putExtra("edit_lifeStyle_bodyType", memberModel.getBodyType());
                intent.putExtra("edit_lifeStyle_complexion", memberModel.getComplexion());
                intent.putExtra("edit_lifeStyle_physical", memberModel.getPhysicalChallege());

                startActivity(intent);
            }
        });

        img_edit_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMyProfileActivity.this, EditIntroductionActivity.class);
                if (!Utilities.isEmpty(memberModel.getSelectIntroduction())) {
                    intent.putExtra("edit_introduction", memberModel.getSelectIntroduction());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(EditMyProfileActivity.this, MyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditMyProfileActivity.this, MyProfileActivity.class));
    }*/

    private void showProfileInfo() {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.allInfoUsingMemeberIdUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, "all member info response" + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");
                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int memberId = object.getInt("MemberId");
                                    String uniqueId = object.getString("UniqueId");
                                    String accountCreatedBy = object.getString("AccountCreatedBy");
                                    String accountManagedBy = object.getString("Account Manage By");
                                    String firstName = object.getString("FirstName");
                                    String middleName = object.getString("MiddleName");
                                    String lastName = object.getString("LastName");
                                    String gender = object.getString("Gender");
                                    String DOB = object.getString("DOB");
                                    String mobileNumber = object.getString("Mobile");
                                    String emailId = object.getString("EmailId");
                                    String memeberHeight = object.getString("MemberHeight");
                                    String memeberCountry = object.getString("MemberCountry");
                                    String memberState = object.getString("MemberState");
                                    String memberCity = object.getString("MemberCity");
                                    String marriedStatus = object.getString("MarriedStatus");
                                    String motherTongue = object.getString("MotherTongue");
                                    String eduction = object.getString("MemberEducationName");
                                    String eductionIn = object.getString("EducationInName");
                                    String workingWith = object.getString("EducationWithName");
                                    String workingAs = object.getString("MemberOccupation");
                                    String memeberIncome = object.getString("MemberInCome");
                                    String familyType = object.getString("FamilyType");
                                    String fatherStatus = object.getString("fatherstatus");
                                    String fatherCompany = object.getString("fathercompany");
                                    String fatherPost = object.getString("fatherpost");
                                    String motherStatus = object.getString("motherstatus");
                                    String motherCompany = object.getString("mothercompany");
                                    String motherPost = object.getString("motherpost");
                                    String noOfBrother = object.getString("NoOfBorther");
                                    String noOfBrotherMarried = object.getString("NoOfBortherMarried");
                                    String noOfSister = object.getString("NoOfSister");
                                    String noOfSisterMarried = object.getString("NoOfSisterMarried");
                                    String birthTime = object.getString("BirthTime");
                                    String birthPlace = object.getString("BirthPlace");
                                    String gotra = object.getString("gotraname");
                                    String manglik = object.getString("manglik");
                                    String rashi = object.getString("Rashi");
                                    String nakshtra = object.getString("Nakshtra");
                                    String charan = object.getString("Charan");
                                    String naddi = object.getString("Naddi");
                                    String gan = object.getString("Gan");
                                    String foodType = object.getString("foodtype");
                                    String drinkHabit = object.getString("drinkhabit");
                                    String smokeHabit = object.getString("smokehabit");
                                    String complexion = object.getString("complexion");
                                    String bodyType = object.getString("bodytype");
                                    String physicalChallenge = object.getString("physicalchallenge");
                                    String kundliPhoto = object.getString("KundaliPhoto");
                                    String minHeight = object.getString("PartnerMinHeight");
                                    String maxHeight = object.getString("PartnerMaxHeight");
                                    String minAge = object.getString("PartnerMinAge");
                                    String maxAge = object.getString("PartnerMaxAge");
                                    String partnerMarriedStatus = object.getString("PartnerMarriedStatus");
                                    String partnerEduction = object.getString("PartnerEducationType");
                                    String partnerEductionIn = object.getString("PartnerEducationIn");
                                    String partnerworkingWith = object.getString("PartnerEducationWith");
                                    String partnerWorkingAs = object.getString("PartnerOccupation");
                                    String partnerMotherTounge = object.getString("PartnerMotherTongue");
                                    String partnercountry = object.getString("PartnerCountry");
                                    String partnerState = object.getString("PartnerState");
                                    String partnerCity = object.getString("PartnerCity");
                                    String partnerIncome = object.getString("PartnerIncome");
                                    String partnerEatingHabbit = object.getString("PartnerFoodType");
                                    String partnerDringkHabbit = object.getString("PartnerDrinkHabit");
                                    String partnerSmokingHabbit = object.getString("partnerSmokeHabit");
                                    String partnerBodyType = object.getString("PartnerBodyType");
                                    String partnerComplexion = object.getString("PartnerComplexion");
                                    String partnerPhysicalChallenge = object.getString("PartnerPhysicalChallenge");
                                    String partnerIntroduction = object.getString("PartnerIntroduction");
                                    String selfIntroduction = object.getString("SelfIntroduction");
                                    String introductionVideo = object.getString("IntroductionVideo");
                                    String memberProfileUrl = object.getString("MainProfilePhoto");
                                    String identityImageUrl = object.getString("Identity_Photo");

                                    String MemberInComeID=object.getString("MemberInComeID");
                                    String MemberOccupationID=object.getString("MemberOccupationID");

                                    memberModel = new MemberModel();

                                    memberModel.setIdentityPhoto(identityImageUrl);
                                    memberModel.setProfileManagedBy(accountManagedBy);
//                                    memberModel.setCasteName("" + object.getString("Caste"));
//                                    memberModel.setSubCasteName("" + object.getString("Subcaste"));
                                    memberModel.setMemberMotherTongueId(object.getString("MotherTongueID"));
                                    memberModel.setMemberCountryId(object.getString("MemberCountryID"));
                                    memberModel.setMemberStateId(object.getString("MemberStateID"));
                                    memberModel.setMemberCityId(object.getString("MemberCityID"));
                                    memberModel.setPartnerSelectedStateId(object.getString("PartnerStateID"));
                                    memberModel.setPartnerSelectedCityId(object.getString("PartnerCityID"));
                                    memberModel.setPartnerSelectedCountryId(object.getString("PartnerCountryID"));
                                    memberModel.setMotherToungeSelectedId(object.getString("PartnerMotherTongueID"));
                                    memberModel.setEductionSelectedId(object.getString("PartnerEducationTypeID"));
                                    memberModel.setPartnerEductionInId(object.getString("PartnerEducationInID"));
                                    memberModel.setPartnerWorkingWithId(object.getString("PartnerEducationWithID"));
                                    memberModel.setPartnerWorkingAsId(object.getString("PartnerOccupationID"));
                                    memberModel.setUserMemberId(memberId);
                                    memberModel.setUniqueId(uniqueId);
                                    memberModel.setProfileCreatedBy(accountCreatedBy);
                                    memberModel.setFirstName(firstName);
                                    memberModel.setFatherName(middleName);
                                    memberModel.setLastName(lastName);
                                    memberModel.setGender(gender);
                                    memberModel.setMobileNumber(mobileNumber);
                                    memberModel.setEmailId(emailId);
                                    memberModel.setUserHeight(memeberHeight);
                                    memberModel.setUserCountry(memeberCountry);
                                    memberModel.setUserState(memberState);
                                    memberModel.setUserCity(memberCity);
                                    memberModel.setMarriedStatus(marriedStatus);
                                    memberModel.setMotherTongue(motherTongue);
                                    memberModel.setEduction(eduction);
                                    memberModel.setEductionIn(eductionIn);
                                    memberModel.setWorkingWith(workingWith);
                                    memberModel.setWorkingAs(workingAs);
                                    memberModel.setUserProfileUrl(memberProfileUrl);
                                    memberModel.setSalary_details(memeberIncome);
                                    memberModel.setFamily_type(familyType);
                                    memberModel.setFather_status(fatherStatus);
                                    memberModel.setFather_company(fatherCompany);
                                    memberModel.setFather_post(fatherPost);
                                    memberModel.setMother_status(motherStatus);
                                    memberModel.setMother_company(motherCompany);
                                    memberModel.setMother_post(motherPost);
                                    memberModel.setNoOfBrothers(noOfBrother);
                                    memberModel.setNoOfSisters(noOfSister);
                                    memberModel.setNoOfBrotherMarried(noOfBrotherMarried);
                                    memberModel.setNoOfSistersMarried(noOfSisterMarried);

                                    memberModel.setFoodType(foodType);
                                    memberModel.setSmokingHabit(smokeHabit);
                                    memberModel.setDrinkingHabit(drinkHabit);
                                    memberModel.setComplexion(complexion);
                                    memberModel.setBodyType(bodyType);
                                    memberModel.setPhysicalChallege(physicalChallenge);
                                    memberModel.setDateOfBirth(DOB);
                                    memberModel.setBirthTime(birthTime);
                                    memberModel.setBirthPlae(birthPlace);
                                    memberModel.setGotra(gotra);
                                    memberModel.setRashi(rashi);
                                    memberModel.setNakshatra(nakshtra);
                                    memberModel.setNaadi(naddi);
                                    memberModel.setGan(gan);
                                    memberModel.setCharan(charan);
                                    memberModel.setManglik(manglik);
                                    memberModel.setKundli_photo(kundliPhoto);
                                    memberModel.setPartner_min_age(minAge);
                                    memberModel.setPartner_max_age(maxAge);
                                    memberModel.setPartner_min_height(minHeight);
                                    memberModel.setPartner_max_height(maxHeight);
                                    memberModel.setPartner_mother_tounge(partnerMotherTounge);
                                    memberModel.setPartner_married_status(partnerMarriedStatus);
                                    memberModel.setPartner_physical_status(partnerPhysicalChallenge);
                                    memberModel.setPartner_eating_habits(partnerEatingHabbit);
                                    memberModel.setPartner_smoking_habits(partnerSmokingHabbit);
                                    memberModel.setPartner_drinking_habits(partnerDringkHabbit);
                                    memberModel.setPartner_eduction(partnerEduction);
                                    memberModel.setPartner_eduction_In(partnerEductionIn);
                                    memberModel.setPartner_working_with(partnerworkingWith);
                                    memberModel.setPartner_working_as(partnerWorkingAs);
                                    memberModel.setPartner_income(partnerIncome);
                                    memberModel.setPartner_country(partnercountry);
                                    memberModel.setPartner_state(partnerState);
                                    memberModel.setPartner_city(partnerCity);
                                    memberModel.setParnter_body_type(partnerBodyType);
                                    memberModel.setSelectIntroduction(selfIntroduction);
                                    memberModel.setPartner_complexion(partnerComplexion);
                                    memberModel.setPartner_introduction(partnerIntroduction);
                                    memberModel.setMemberInComeID(MemberInComeID);
                                    memberModel.setMemberOccupationID(MemberOccupationID);
                                }
                                setDataToFields();
                            } else {
                                Toast.makeText(EditMyProfileActivity.this, "Details not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "memberInfo error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put(MEMBER_ID, String.valueOf(memberId));

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setDataToFields() {

        if (Utilities.isEmpty(memberModel.getUserProfileUrl())) {

            Picasso.get()
                    .load(R.drawable.circle_preview)
                    .placeholder(R.drawable.circle_preview)
                    .error(R.drawable.circle_preview)
                    .into(my_edit_profile);
        } else {

            Picasso.get()
                    .load(memberModel.getUserProfileUrl())
                    .placeholder(R.drawable.circle_preview)
                    .into(my_edit_profile);
        }

        my_edit_memberId.setText(memberModel.getUniqueId());
        String memberName = memberModel.getFirstName() + " " + memberModel.getLastName();
        my_edit_memberName.setText(memberName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPaused){
            showProfileInfo();
        }
    }
}