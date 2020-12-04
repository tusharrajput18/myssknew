package in.co.vsys.myssksamaj.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomPagerAdapter;
import in.co.vsys.myssksamaj.contracts.AllUserInfoContract;
import in.co.vsys.myssksamaj.contracts.DownloadProfileContract;
import in.co.vsys.myssksamaj.contracts.GetPdfUrlContract;
import in.co.vsys.myssksamaj.contracts.RecentlyJointContractPaging;
import in.co.vsys.myssksamaj.fragments.ImageDisplayFragment;
import in.co.vsys.myssksamaj.fragments.ShowContactFragment;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.InitiatePaymentListener;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.AllUserInfoPresenter;
import in.co.vsys.myssksamaj.presenters.DownloadProfilePresenter;
import in.co.vsys.myssksamaj.presenters.GetPdfUrlPresenter;
import in.co.vsys.myssksamaj.presenters.RecentlyJointPagingPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class MatrimonyProfileViewActivity extends AppCompatActivity implements DownloadProfileContract.DownloadProfileView,
        GetPdfUrlContract.GetPdfUrlView, InitiatePaymentListener, AllUserInfoContract.AllUserInfoView
//        implements TransactionDataContract.TransactionView
{

    private Context mContext;
    private String candidate = "In my own words";
    private static final String TAG = MatrimonyProfileViewActivity.class.getSimpleName();
    private int memberId, userMemeberId;
    private SharedPreferences mPreference;
    private ProgressDialog progressDialog;
    private ViewPager viewPager;
    private int position1;
    private Toolbar mToolbar;
    private int viewPosition, homeActivityFlag, listFlagActivity;
    public static String userName = "";
    private ProgressDialog mDialog;
    private CustomPagerAdapter customPagerAdapter;
    private int pageCount;
    private String mTransactionId = "", mPackageId = "1";
    private boolean isSwiped = false;
    private List<UserProfileModel> mUserProfileModelList = new ArrayList<>();
    private DownloadProfileContract.DownloadProfileOps downloadProfilePresenter;
    private GetPdfUrlContract.GetPdfUrlOps getPdfUrlPresenter;
    private UserProfileModel currentProfileSelected;
    private String fileName;
    private AllUserInfoContract.AllUserInfoOps allUserInfoPresenter;
    private int mMemberId, mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_profile_view);
        mContext = this;

        viewPager = findViewById(R.id.viewpager);
        mToolbar = findViewById(R.id.toolbar_profileView);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle("" + userName);
        }

        Intent p = getIntent();

        if (p != null) {
            if (p.getExtras().containsKey("position")) {
                position1 = p.getExtras().getInt("position", 0);
            }
            if (p.getExtras().containsKey("pageCount")) {
                pageCount = p.getExtras().getInt("pageCount", 0);
            }
            userMemeberId = p.getExtras().getInt("memberId", 0);

            homeActivityFlag = p.getExtras().getInt("flagHomeActivity", 0);

        }

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreference.getInt("memberId", 0);
        allUserInfoPresenter = new AllUserInfoPresenter(this);

        mMemberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);

        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        Log.e("intent", position1 + "::" + pageCount);


        if (position1 == 0) {
            mToolbar.setTitle("" + userName);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadRecentlyData();
            }
        }).start();

        getPdfUrlPresenter = new GetPdfUrlPresenter(this);
        downloadProfilePresenter = new DownloadProfilePresenter(this);
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
                onBackPressed();
                return true;

            case R.id.nav_share:
                Toast.makeText(this, "Share Click", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadRecentlyData() {

        allUserInfoPresenter.getAllUserInfoOps("" + userMemeberId);


      /*  RecentlyJointContractPaging.RecentlyJointPagingOps recentlyJointPagingPresenter =
                new RecentlyJointPagingPresenter(new RecentlyJointContractPaging.RecentlyJointPagingView() {
                    @Override
                    public void showRecentlyJoint(List<UserProfileModel> userProfileModels) {

                        int j = 0;


                        while (j<userProfileModels.size()){
                            if(userProfileModels.get(j).getSenderBlocked().equalsIgnoreCase("0")){
                                mUserProfileModelList.add(userProfileModels.get(j));
                            }

                            j++;
                        }

                        //mUserProfileModelList.addAll(userProfileModels);
                        Log.e("size", "" + mUserProfileModelList.size());
                        showRecentlyJointItemModel();
                    }

                    @Override
                    public void showLoading() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mDialog == null) {
                                    mDialog = new ProgressDialog(mContext);
                                    mDialog.setMessage(getString(R.string.please_wait));
                                }
                                mDialog.show();
                            }
                        });
                    }

                    @Override
                    public void hideLoading() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void showError(String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
        recentlyJointPagingPresenter.getRecentlyJoint("" + memberId, "" + pageCount);
        Log.d("mytag", "loadRecentlyData: "+memberId+" "+pageCount);*/
    }

    private void showRecentlyJointItemModel(final UserDetailsModel userProfileModel) {

       // final UserProfileModel userProfileModel = mUserProfileModelList.get(position1);

        // currentProfileSelected = userProfileModel;

        try {
            LinearLayout basicDetailsContainer = findViewById(R.id.basic_info_container);
            LinearLayout linear_basic = findViewById(R.id.linear_basic);
            // linear_basic.setVisibility(View.GONE);

            ImageView img_det_premium = findViewById(R.id.img_det_premium);
            ImageView profileUrl = findViewById(R.id.img_profileView_image);
            TextView tv_memberId = findViewById(R.id.tv_profileView_memberId);
            TextView tv_otherDetails = findViewById(R.id.tv_profile_otherDetails);
            final ImageView imgInvite = findViewById(R.id.img_profileView_invite);
            final ImageView imgInvited = findViewById(R.id.img_profileView_invited);
            final ImageView imgAccept = findViewById(R.id.img_profileView_accept);
            final ImageView imgShortlist = findViewById(R.id.img_profileView_shortlist);
            final ImageView imgShortlisted =findViewById(R.id.img_profileView_shortlisted);
            final TextView tv_shortlist = findViewById(R.id.tv_shortlist);
            final TextView tv_shortlisted = findViewById(R.id.tv_shortlisted);
            final TextView tv_invite = findViewById(R.id.tv_profileView_invite);
            final TextView tv_invited = findViewById(R.id.tv_profileView_invited);
            final TextView tv_accept = findViewById(R.id.tv_profileView_accept);
            ImageView imgChat = findViewById(R.id.img_profileView_chat);
            TextView tv_profile_created_by = findViewById(R.id.tv_profile_created_by);
            TextView tv_profile_managed_by = findViewById(R.id.tv_profile_managedBy);
            TextView tv_profileView_introduction = findViewById(R.id.tv_profileView_introduction);
            TextView tv_profileView_name = findViewById(R.id.tv_profileView_name);
            TextView tv_profileView_gender = findViewById(R.id.tv_profileView_gender);
            TextView tv_profileView_age = findViewById(R.id.tv_profileView_age);
            TextView tv_profileView_height = findViewById(R.id.tv_profileView_height);
            TextView tv_profileView_married_status = findViewById(R.id.tv_profileView_married_status);
            TextView tv_profileView_mother_tongue = findViewById(R.id.tv_profileView_mother_tongue);
            TextView tv_profileView_physical_status = findViewById(R.id.tv_profileView_physical_status);
            TextView tv_profileView_body_type = findViewById(R.id.tv_profileView_body_type);
            TextView tv_profileView_complexion = findViewById(R.id.tv_profileView_complexion);
            TextView tv_profileView_profile_created_by = findViewById(R.id.tv_profileView_profile_created_by);
            TextView tv_profileView_eating_habit = findViewById(R.id.tv_profileView_eating_habit);
            TextView tv_profileView_drinking_habit = findViewById(R.id.tv_profileView_drinking_habit);
            TextView tv_profileView_smoking_habit = findViewById(R.id.tv_profileView_smoking_habit);
            TextView tv_profileView_eduction = findViewById(R.id.tv_profileView_eduction);
            TextView tv_profileView_eduction_in = findViewById(R.id.tv_profileView_eduction_in);
            TextView tv_profileView_working_with = findViewById(R.id.tv_profileView_working_with);
            TextView tv_profileView_working_as = findViewById(R.id.tv_profileView_working_as);
            TextView tv_profileView_annual_income = findViewById(R.id.tv_profileView_annual_income);
            TextView tv_profileView_country = findViewById(R.id.tv_profileView_country);
            TextView tv_profileView_state = findViewById(R.id.tv_profileView_state);
            TextView tv_profileView_city = findViewById(R.id.tv_profileView_city);
            TextView tv_profileView_family_type = findViewById(R.id.tv_profileView_family_type);
            TextView tv_profileView_father_status = findViewById(R.id.tv_profileView_father_status);
            TextView tv_profileView_mother_status = findViewById(R.id.tv_profileView_mother_status);
            TextView tv_profileView_no_of_brother = findViewById(R.id.tv_profileView_no_of_brother);
            TextView tv_profileView_no_of_brother_married = findViewById(R.id.tv_profileView_no_of_brother_married);
            TextView tv_profileView_no_of_sister = findViewById(R.id.tv_profileView_no_of_sister);
            TextView tv_profileView_no_of_sister_married = findViewById(R.id.tv_profileView_no_of_sister_married);
            TextView tv_profileView_birth_date = findViewById(R.id.tv_profileView_birth_date);
            TextView tv_profileView_birth_time = findViewById(R.id.tv_profileView_birth_time);
            TextView tv_profileView_birth_place = findViewById(R.id.tv_profileView_birth_place);
            TextView tv_profileView_gotra = findViewById(R.id.tv_profileView_gotra);
            TextView tv_profileView_rashi = findViewById(R.id.tv_profileView_rashi);
            TextView tv_profileView_nakshtra = findViewById(R.id.tv_profileView_nakshtra);
            TextView tv_profileView_charan = findViewById(R.id.tv_profileView_charan);
            TextView tv_profileView_naadi = findViewById(R.id.tv_profileView_naadi);
            TextView tv_profileView_gan = findViewById(R.id.tv_profileView_gan);
            TextView tv_profileView_manglik = findViewById(R.id.tv_profileView_manglik);
            TextView tv_online = findViewById(R.id.tv_profileView_online);
            TextView tv_offline = findViewById(R.id.tv_profileView_Offline);
            LinearLayout layout_profession = findViewById(R.id.layout_profileViewProfession);
            LinearLayout layout_location = findViewById(R.id.layout_tv_profileViewLocation);
            LinearLayout layout_familyDetails =findViewById(R.id.layout_profileViewFamilyDetails);
            LinearLayout layout_Astro = findViewById(R.id.layout_profileViewAstroDetails);
            View view_profession = findViewById(R.id.view_profileViewProfession);
            View view_location = findViewById(R.id.view_profileViewLocation);
            View view_family = findViewById(R.id.view_profileViewFamilyDetails);
            View view_astro = findViewById(R.id.view_profileViewAstroDetails);
            TextView tv_professionHeading = findViewById(R.id.tv_profileViewProfession);
            TextView tv_locationHeading = findViewById(R.id.tv_profileViewLocation);
            TextView tv_familyHeading = findViewById(R.id.tv_profileViewFamilyDetails);
            TextView tv_astroHeading = findViewById(R.id.tv_profileViewAstroDetails);
            TextView tv_age_heading = findViewById(R.id.tv_age);
            TextView tv_height_heading = findViewById(R.id.tv_height);
            TextView tv_marriedStatus_heading = findViewById(R.id.tv_married_status);
            TextView tv_motherTounge_heading = findViewById(R.id.tv_mother_tongue);
            TextView tv_physicalStatus_heading = findViewById(R.id.tv_physical);
            TextView tv_bodyType_heading = findViewById(R.id.tv_body_type);
            TextView tv_complexion_heading = findViewById(R.id.tv_body_complexion);
            TextView tv_eating_heading = findViewById(R.id.tv_Eating_Habits);
            TextView tv_drinking_heading = findViewById(R.id.tv_drinking_habit);
            TextView tv_smoking_heading = findViewById(R.id.tv_smoking_habits);
            TextView tv_age_heading_dot = findViewById(R.id.tv_age_dot);
            TextView tv_height_heading_dot = findViewById(R.id.tv_height_dot);
            TextView tv_marriedStatus_heading_dot = findViewById(R.id.tv_married_statusDot);
            TextView tv_motherTounge_heading_dot = findViewById(R.id.tv_mother_tongueDot);
            TextView tv_physicalStatus_heading_dot = findViewById(R.id.tv_physicalDot);
            TextView tv_bodyType_heading_dot = findViewById(R.id.tv_body_typeDot);
            TextView tv_complexion_heading_dot = findViewById(R.id.tv_body_complexionDot);
            TextView tv_eating_heading_dot = findViewById(R.id.tv_Eating_HabitsDot);
            TextView tv_drinking_heading_dot = findViewById(R.id.tv_drinking_habitDot);
            TextView tv_smoking_heading_dot = findViewById(R.id.tv_smoking_habitsDot);
//                        TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
//                        TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);
            TextView tvContact = findViewById(R.id.tv_contact);
            TextView tvEmail = findViewById(R.id.tv_email);

            ImageView download = findViewById(R.id.download);
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                                   /* String memberId =
//                                        "3";
                                            currentProfileSelected.getMemberId();*/

                    fileName=userProfileModel.getFirstName()+"_"+userProfileModel.getLastName();
                    getPdfUrlPresenter.getProfilePdf(userProfileModel.getMemberId());

                    //Toast.makeText(mContext, ""+userProfileModel.getMemberId(), Toast.LENGTH_SHORT).show();
                }
            });

            memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
            final String accountCreatedBy = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.ACCOUNT_CREATED_BY);
            int userId = Integer.parseInt(userProfileModel.getMemberId());
            MatrimonyUtils.sendMemberIdTask(memberId, userId);
            String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
            String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

            String message = "" + name + " " + "viewed your profile";

            MatrimonyUtils.sendProfileViewNotification(userProfileModel.getDeviceId(), message, imageUrl,
                    String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId), name);

            tv_invite.setEnabled(true);

            imgInvite.setVisibility(View.GONE);
            imgInvited.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);
            tv_invited.setVisibility(View.GONE);
            imgAccept.setVisibility(View.GONE);
            tv_accept.setVisibility(View.GONE);

            if (accountCreatedBy.equals("P")) {
                //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                //                imgInvite.setImageAlpha(64);
                //            }
            } else if (accountCreatedBy.equals("C")) {

                if (userProfileModel.getInvited().equals(String.valueOf(userProfileModel.getMemberId()))) {
                    imgAccept.setVisibility(View.VISIBLE);
                    tv_accept.setVisibility(View.VISIBLE);
                    imgInvited.setVisibility(View.GONE);
                    imgInvite.setVisibility(View.GONE);
                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.GONE);
                } else {
                    //                if (TextUtils.isEmpty(recentlyJointModel.getInvitedFlag()) || recentlyJointModel.getInvitedFlag().contains("0")) {
                    //
                    //                    imgInvite.setVisibility(View.VISIBLE);
                    //                    imgInvited.setVisibility(View.GONE);
                    //                    tv_invite.setVisibility(View.VISIBLE);
                    //                    tv_invited.setVisibility(View.GONE);
                    //                    imgAccept.setVisibility(View.GONE);
                    //                } else {
                    //                    imgInvited.setVisibility(View.VISIBLE);
                    //                    imgInvite.setVisibility(View.GONE);
                    //                    tv_invite.setVisibility(View.GONE);
                    //                    tv_invited.setVisibility(View.VISIBLE);
                    //                    imgAccept.setVisibility(View.GONE);
                    //                }
                }
            }

            if (TextUtils.isEmpty(userProfileModel.getInvited()) || userProfileModel.getInvited().contains("0")) {
                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);
                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
                imgAccept.setVisibility(View.GONE);
            } else {
                imgInvited.setVisibility(View.VISIBLE);
                imgInvite.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.VISIBLE);
                imgAccept.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(userProfileModel.getShorted()) || userProfileModel.getShorted().contains("0")) {
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

            imgShortlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "1");

                    imgShortlisted.setVisibility(View.VISIBLE);
                    imgShortlist.setVisibility(View.GONE);

                    tv_shortlist.setVisibility(View.GONE);
                    tv_shortlisted.setVisibility(View.VISIBLE);
                }
            });

            imgShortlisted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "0");

                    imgShortlisted.setVisibility(View.GONE);
                    imgShortlist.setVisibility(View.VISIBLE);

                    tv_shortlist.setVisibility(View.VISIBLE);
                    tv_shortlisted.setVisibility(View.GONE);
                }
            });

            imgInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (accountCreatedBy.equals("P")) {
                        Toast.makeText(mContext, "You cannot invite Candidate profile ", Toast.LENGTH_LONG).show();
                    } else {

                        int senderId = Integer.parseInt(userProfileModel.getMemberId());
                        MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                        String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                        String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                        String message = "Invitation Received from " + name;

                        MatrimonyUtils.sendInvitationNotification(userProfileModel.getDeviceId(), message,
                                imageUrl, String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId),
                                name);

                        imgInvited.setVisibility(View.VISIBLE);
                        imgInvite.setVisibility(View.GONE);

                        tv_invite.setVisibility(View.GONE);
                        tv_invited.setVisibility(View.VISIBLE);
                    }
                }
            });

            imgInvited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int senderId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                    imgInvite.setVisibility(View.VISIBLE);
                    imgInvited.setVisibility(View.GONE);

                    tv_invite.setVisibility(View.VISIBLE);
                    tv_invited.setVisibility(View.GONE);
                }
            });

            if (userProfileModel.getAccountCreatedBy().equals("P")) {

                tv_age_heading.setVisibility(View.GONE);
                tv_age_heading_dot.setVisibility(View.GONE);
                tv_height_heading.setVisibility(View.GONE);
                tv_height_heading_dot.setVisibility(View.GONE);
                tv_marriedStatus_heading.setVisibility(View.GONE);
                tv_marriedStatus_heading_dot.setVisibility(View.GONE);
                tv_motherTounge_heading.setVisibility(View.GONE);
                tv_motherTounge_heading_dot.setVisibility(View.GONE);
                tv_physicalStatus_heading.setVisibility(View.GONE);
                tv_physicalStatus_heading_dot.setVisibility(View.GONE);
                tv_bodyType_heading.setVisibility(View.GONE);
                tv_bodyType_heading_dot.setVisibility(View.GONE);
                tv_complexion_heading.setVisibility(View.GONE);
                tv_complexion_heading_dot.setVisibility(View.GONE);
                tv_eating_heading.setVisibility(View.GONE);
                tv_eating_heading_dot.setVisibility(View.GONE);
                tv_drinking_heading.setVisibility(View.GONE);
                tv_drinking_heading_dot.setVisibility(View.GONE);
                tv_smoking_heading.setVisibility(View.GONE);
                tv_smoking_heading_dot.setVisibility(View.GONE);
                tv_profileView_introduction.setVisibility(View.GONE);
                tv_profileView_age.setVisibility(View.GONE);
                tv_profileView_height.setVisibility(View.GONE);
                tv_profileView_married_status.setVisibility(View.GONE);
                tv_profileView_mother_tongue.setVisibility(View.GONE);
                tv_profileView_physical_status.setVisibility(View.GONE);
                tv_profileView_body_type.setVisibility(View.GONE);
                tv_profileView_complexion.setVisibility(View.GONE);
                tv_profileView_drinking_habit.setVisibility(View.GONE);
                tv_profileView_smoking_habit.setVisibility(View.GONE);
                tv_profileView_eating_habit.setVisibility(View.GONE);

                layout_profession.setVisibility(View.GONE);
                layout_familyDetails.setVisibility(View.GONE);
                layout_location.setVisibility(View.GONE);
                layout_Astro.setVisibility(View.GONE);
                view_profession.setVisibility(View.GONE);
                view_family.setVisibility(View.GONE);
                view_astro.setVisibility(View.GONE);
                view_location.setVisibility(View.GONE);
                tv_professionHeading.setVisibility(View.GONE);
                tv_familyHeading.setVisibility(View.GONE);
                tv_locationHeading.setVisibility(View.GONE);
                tv_astroHeading.setVisibility(View.GONE);

                String age_height_other = userProfileModel.getMemberCity() +
                        ", " + userProfileModel.getMemberState() + ", " + userProfileModel.getMemberCountry();
                tv_otherDetails.setText(age_height_other);

            } else if (userProfileModel.getAccountCreatedBy().equals("C")) {

                try {
                    userProfileModel.setAge(MatrimonyUtils.getAge(userProfileModel.getDOB()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String age_height_other = userProfileModel.getAge()
                        + ", " + userProfileModel.getMemberHeight()
                        + ", " + userProfileModel.getMemberCity()
                        + ", " + userProfileModel.getMemberState()
                        + ", " + userProfileModel.getMemberCountry();

                tv_otherDetails.setText(age_height_other);

                tv_profileView_age.setText(userProfileModel.getAge());
                tv_profileView_height.setText(userProfileModel.getMemberHeight());
                tv_profileView_married_status.setText(userProfileModel.getMarriedStatus());
                tv_profileView_mother_tongue.setText(userProfileModel.getMotherTongue());

                tv_profileView_birth_date.setText(userProfileModel.getDOB());
                tv_profileView_introduction.setText(userProfileModel.getSelfIntroduction());
                tv_profileView_eduction.setText(userProfileModel.getMemberEducationName());
                tv_profileView_eduction_in.setText(userProfileModel.getEducationInName());
                tv_profileView_working_with.setText(userProfileModel.getEducationWithName());
                tv_profileView_working_as.setText(userProfileModel.getMemberOccupation());
                tv_profileView_annual_income.setText(userProfileModel.getMemberInCome());
                tv_profileView_family_type.setText(userProfileModel.getFamilyType());
                tv_profileView_father_status.setText(userProfileModel.getFatherstatus());
                tv_profileView_mother_status.setText(userProfileModel.getMotherstatus());
                tv_profileView_no_of_brother.setText(userProfileModel.getNoOfBorther());
                tv_profileView_no_of_brother_married.setText(userProfileModel.getNoOfBortherMarried());
                tv_profileView_no_of_sister.setText(userProfileModel.getNoOfSister());
                tv_profileView_no_of_sister_married.setText(userProfileModel.getNoOfSisterMarried());
                tv_profileView_birth_place.setText(userProfileModel.getBirthPlace());
                tv_profileView_birth_time.setText(userProfileModel.getBirthTime());
                tv_profileView_gotra.setText(userProfileModel.getGotra());
                tv_profileView_rashi.setText(userProfileModel.getRashi());
                tv_profileView_nakshtra.setText(userProfileModel.getNakshtra());
                tv_profileView_charan.setText(userProfileModel.getCharan());
                tv_profileView_naadi.setText(userProfileModel.getNaddi());
                tv_profileView_gan.setText(userProfileModel.getGan());
                tv_profileView_physical_status.setText(userProfileModel.getPhysicalchallenge());
                tv_profileView_drinking_habit.setText(userProfileModel.getDrinkhabit());
                tv_profileView_smoking_habit.setText(userProfileModel.getSmokehabit());
                tv_profileView_eating_habit.setText(userProfileModel.getFoodtype());
                tv_profileView_manglik.setText(userProfileModel.getManglik());
                tv_profileView_body_type.setText(userProfileModel.getBodytype());
                tv_profileView_complexion.setText(userProfileModel.getComplexion());

                AppCompatImageView img_kundli = findViewById(R.id.img_profileView_kundli);
                TextView tv_kundli = findViewById(R.id.tv_profileView_kundli);

                if (userProfileModel.getKundaliPhoto().equals("") || userProfileModel.getKundaliPhoto().equalsIgnoreCase("")) {

                    img_kundli.setVisibility(View.GONE);
                    tv_kundli.setVisibility(View.GONE);
                } else {

                    img_kundli.setVisibility(View.VISIBLE);
                    tv_kundli.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(userProfileModel.getKundaliPhoto())
                            .placeholder(R.drawable.circle_preview)
                            .into(img_kundli);

                    img_kundli.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("kundali", userProfileModel.getKundaliPhoto());
                            bundle.putString("memberName", userProfileModel.getFirstName() + "_" + userProfileModel.getLastName());
                            imageDisplayFragment.setArguments(bundle);
                            imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                        }
                    });
                }
            }

            tv_memberId.setText(userProfileModel.getUniqueId());

            String profileCreatedBy = userProfileModel.getAccountCreatedBy();
            String profileManagedBy = userProfileModel.getAccountManageBy();

            switch (profileManagedBy) {
                case "Self":

                    tv_profile_managed_by.setText("Profile Created by Self");
                    break;
                case "Parent":

                    tv_profile_managed_by.setText("Profile Created by Parent");
                    break;
                case "Sibling":

                    tv_profile_managed_by.setText("Profile Created by Sibling");
                    break;
            }

            if (profileCreatedBy.equals("C")) {
                tv_profile_created_by.setText(candidate);
                tv_profileView_profile_created_by.setText("Self");
            } else {
                tv_profile_created_by.setVisibility(View.GONE);
                tv_profileView_profile_created_by.setText("Parent");
                tv_profile_managed_by.setText("Profile Created by Parent");
            }

            String username = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
            tv_profileView_name.setText(username);
            tv_profileView_gender.setText(userProfileModel.getGender());

            tv_profileView_country.setText(userProfileModel.getMemberCountry());
            tv_profileView_state.setText(userProfileModel.getMemberState());
            tv_profileView_city.setText(userProfileModel.getMemberCity());

            if (userProfileModel.getMainProfilePhoto().isEmpty()) {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .fit()
                        .error(R.drawable.img_preview)
                        .into(profileUrl);
            } else {
                Picasso.get()
                        .load(userProfileModel.getMainProfilePhoto())
                        .placeholder(R.drawable.img_preview)
                        .fit()
                        .into(profileUrl);
            }

            profileUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.loadImages(mContext, mId, null);
                }
            });

            imgChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mUserType==1){
                        mContext.startActivity(new Intent(mContext, ChatActivity.class));
                        String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                        String imageUrl = userProfileModel.getMainProfilePhoto();

                        SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                                Integer.parseInt(userProfileModel.getMemberId()));
                        SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                                name);
                        SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                                imageUrl);
                        SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                                userProfileModel.getDeviceId());

                    }else {

                        final Dialog dialogChoice = new Dialog(MatrimonyProfileViewActivity.this);
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
                                Intent intent=new Intent(MatrimonyProfileViewActivity.this,DonationActivity.class);
                                startActivity(intent);

                            }
                        });

                    }


                }
            });

            if(userProfileModel.getIspremium().equalsIgnoreCase("0")){
                img_det_premium.setVisibility(View.GONE);
            }else {
                img_det_premium.setVisibility(View.VISIBLE);
            }



            String onlineTime = userProfileModel.getOnlineTime();
            String offlineTime = userProfileModel.getOfflineTime();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            Date onlineDate = null, offlineDate = null;

            try {
                if (!TextUtils.isEmpty(onlineTime) && onlineTime.length() > 5) {
                    onlineDate = formatter.parse(onlineTime);
                }
                if (!TextUtils.isEmpty(offlineTime) && offlineTime.length() > 5) {
                    offlineDate = formatter.parse(offlineTime);
                }

                assert onlineDate != null;
                assert offlineDate != null;

                if (onlineDate != null && offlineDate != null) {
                    if (onlineDate.compareTo(offlineDate) < 0) {
                        userProfileModel.setOnline(false);
                    } else {
                        userProfileModel.setOnline(true);
                    }
                } else {
                    userProfileModel.setOnline(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (userProfileModel.getOnlinestatus().equalsIgnoreCase("online")) {
                tv_online.setVisibility(View.VISIBLE);
                tv_online.setText("Online");
                tv_offline.setVisibility(View.GONE);
            } else {
                tv_offline.setVisibility(View.VISIBLE);
                tv_offline.setText("Offline ");
                tv_online.setVisibility(View.GONE);
            }

            showBasicDetails(userProfileModel,basicDetailsContainer);

//                Utilities.showContactNumber(mContext, tvContact, Utilities.getString(userProfileModel.getMobile()));
//                Utilities.showEmail(mContext, tvEmail, Utilities.getString(userProfileModel.getEmailid()));


        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  try {
            currentProfileSelected=mUserProfileModelList.get(position1);
            if (customPagerAdapter == null) {
                customPagerAdapter = new CustomPagerAdapter(mContext, mUserProfileModelList,
                        R.layout.row_profile, new CustomPagerAdapter.ItemClickedListener() {
                    @Override
                    public void onItemClicked(View view, Object object, final int position) {

                    }

                    @Override
                    public void onViewBound(View view, Object object, int position) {


                    }
                });

                viewPager.setAdapter(customPagerAdapter);

                final int lastPageIndex=viewPager.getAdapter().getCount()-1;

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        try {
                            String toolbarFName = mUserProfileModelList.get(position).getFirstName();
                            toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();
                            String toolbarLName = mUserProfileModelList.get(position).getLastName();
                            toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();
                            mToolbar.setTitle(toolbarFName + " " + toolbarLName);

                            if (isSwiped) {
                                Log.d("mytag", "onPageSelected:123 "+customPagerAdapter.getCount()+" "+position);
                                if ((customPagerAdapter.getCount() - 1) == position+1) {
                                    pageCount++;
                                    isSwiped = false;
                                    loadRecentlyData();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(position==mUserProfileModelList.size()){
                            viewPager.setCurrentItem(0);
                        }
                    }


                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state == ViewPager.SCROLL_STATE_SETTLING) {
                            isSwiped = true;
                        }

                        *//*viewPager.getCurrentItem()

                        if(position==0){
                            viewPager.setCurrentItem(lastPageIndex - 1, false);
                        }*//*
                       *//* if(position==lastPageIndex)
                            viewPager.setCurrentItem(1, false);*//*

                      *//*  if(state==ViewPager.SCROLL_STATE_IDLE){
                            viewPager.setCurrentItem(1);
                        }*//*



                    }
                });


                if (mUserProfileModelList.size() == 10) {
                    viewPager.setCurrentItem(position1);
                   *//* viewPager.setHorizontalScrollBarEnabled(false);*//*
                }
            } else {
                customPagerAdapter.updateList(mUserProfileModelList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


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
//
//        /**
//         * Note:
//         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
//         * for other constants check the documentation of easebuzz.in here:
//         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
//         */
//        if (requestCode == StaticDataModel.PWE_REQUEST_CODE) {
//            if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
//                status = "1";
//                successfulTransactionCompletion();
//            } else {
//                status = "0";
//                transactionFailure();
//            }
//            transactionDataPresenter.updateTransactionData("" + memberId, "" + mPackageId,
//                    mTransactionId, status);
//        }
//    }
//
//    private void successfulTransactionCompletion() {
//        try {
//            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.USERTYPE, 1);
//            MainPresenter.getInstance().setUserType(mContext, 1);
//            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
//            customPagerAdapter.notifyDataSetChanged();
//
////            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
////            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void transactionFailure() {
//        try {
//            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.USERTYPE, 0);
//            MainPresenter.getInstance().setUserType(mContext, 0);
//            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
//            customPagerAdapter.notifyDataSetChanged();
////            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
////            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void showTransactionUpdate(String message) {
//
//    }

    /*private void showBasicDetails1(){
        showBasicDetails(userProfileModel, basicDetailsContainer);
    }*/

    private void showBasicDetails(final UserDetailsModel userProfileModel, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", userProfileModel.getFirstName() + " " + userProfileModel.getLastName(),
                null));

        Log.d("mytag", "showBasicDetails container..................: ");

       /* if (Utilities.isEmpty(userProfileModel.getEmailid()) && Utilities.isEmpty(userProfileModel.getMobile())) {
            return;
        }*/

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                Log.d("mytag", "basicDetailsContainer:........................ ");
                ShowContactFragment showContactFragment = new ShowContactFragment();
                showContactFragment.setInitiatePaymentListener(MatrimonyProfileViewActivity.this);
                Bundle bundle = new Bundle();

                bundle.putString(Constants.MEMBER_ID, userProfileModel.getMemberId());
                bundle.putString(Constants.CONTACT_NO, userProfileModel.getMobile());
                bundle.putString(Constants.EMAIL, userProfileModel.getEmailId());
                showContactFragment.setArguments(bundle);
                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
            }
        }));
    }

    private View addData(String title, String text, ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
        TextView titleTxt = view.findViewById(R.id.title);
        TextView value = view.findViewById(R.id.value);

        Utilities.setText(titleTxt, title);
        Utilities.setText(value, text, viewClickListener);
        return view;
    }

    private View addViewContact(final ViewClickListener viewClickListener) {
        Log.d("mytag", "addData:11111 22222222222222222222");
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_view_action_layout, null);
        Button view_contact = view.findViewById(R.id.view_contact);
        view_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag", "addData: 22222222222222222222");
                viewClickListener.onViewClick(v);
            }
        });
        return view;
    }

   /* private void showBasicDetails(final UserProfileModel userProfileModel, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", userProfileModel.getFirstName() + " " + userProfileModel.getLastName(),
                null));

        Log.d("mytag", "showBasicDetails container..................: ");

        if (Utilities.isEmpty(userProfileModel.getEmailid()) && Utilities.isEmpty(userProfileModel.getMobile())) {
            return;
        }

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                ShowContactFragment showContactFragment = new ShowContactFragment();
                showContactFragment.setInitiatePaymentListener(MatrimonyProfileViewActivity.this);
                Bundle bundle = new Bundle();

                bundle.putString(Constants.MEMBER_ID, userProfileModel.getMemberId());
                bundle.putString(Constants.CONTACT_NO, userProfileModel.getMobile());
                bundle.putString(Constants.EMAIL, userProfileModel.getEmailid());
                showContactFragment.setArguments(bundle);
                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
            }
        }));
    }

    private View addData(String title, String text, ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
        TextView titleTxt = view.findViewById(R.id.title);
        TextView value = view.findViewById(R.id.value);

        Utilities.setText(titleTxt, title);
        Utilities.setText(value, text, viewClickListener);
        return view;
    }

    private View addViewContact(final ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_view_action_layout, null);
        Button view_contact = view.findViewById(R.id.view_contact);
        view_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClickListener.onViewClick(v);
            }
        });
        return view;
    }*/

    @Override
    public void showFileDownloaded(String message) {

    }

    @Override
    public void showLoading() {
        try {
            if (progressDialog.isShowing())
                return;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Loading");
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void hideLoading() {
        try {
            if (!progressDialog.isShowing())
                return;
            progressDialog.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showPdfUrl(String url) {
       // Toast.makeText(mContext, ""+fileName+" "+url, Toast.LENGTH_SHORT).show();
        downloadProfilePresenter.downloadFile(fileName,url);
    }

    @Override
    public void initiatePayment() {
        Intent intent = new Intent(mContext, PackagesActivity.class);
        startActivityForResult(intent, 111);
    }

    @Override
    public void showAllUserInfo(UserDetailsModel userDetailsModel) {
        showRecentlyJointItemModel(userDetailsModel);
    }

//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void showError(String message) {
//
//    }
}