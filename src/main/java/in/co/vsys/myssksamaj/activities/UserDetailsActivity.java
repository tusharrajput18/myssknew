package in.co.vsys.myssksamaj.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.contracts.DownloadProfileContract;
import in.co.vsys.myssksamaj.contracts.GetPdfUrlContract;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.contracts.UserContract;
import in.co.vsys.myssksamaj.fragments.ImageDisplayFragment;
import in.co.vsys.myssksamaj.fragments.ShowContactFragment;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.InitiatePaymentListener;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.presenters.DownloadProfilePresenter;
import in.co.vsys.myssksamaj.presenters.GetPdfUrlPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.presenters.UserPresenter;
import in.co.vsys.myssksamaj.presenters.ViewContactPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class UserDetailsActivity extends AppCompatActivity implements UserContract.UserDetailsView,
        TransactionDataContract.TransactionView, DownloadProfileContract.DownloadProfileView,
        GetPdfUrlContract.GetPdfUrlView, InitiatePaymentListener {

    private Context mContext;
    private static final String TAG = ShortlistedProfileViewActivity.class.getSimpleName();
    private String candidate = "In my own words";
    //    private MatchesModel matchesModel;
    private AppCompatImageView img_kundli;
    private static final String MEMBER_ID = "MemberId";
    private String memberImageUrl = "";

    private String fileName;

    String cname;

    private LinearLayout basicDetailsContainer;

    private  ImageView img_det_premium;

    public static String receivedInviteFlag;
    public static int minHeight, minAge, marriedStatus, foodType, smokeType, drinkType;
    private ImageView imgInvite, imgInvited, imgShortlist, imgShortlisted, imgChat, profileUrl, imgAccept;
    private TextView tvInvite, tvInvited, tvShortlist, tvShortlisted;
    private ImageView img_match_age, img_match_height, img_match_drink, img_match_food, img_match_smoke, img_match_married;
    private CircularImageView img_profileViewverPic, img_memberProfile;
    private TextView tv_memberId, tv_otherDetails, tv_profile_created_by, tv_match_details_introduction, tv_profileView_gender, tv_profileView_age, tv_profileView_height,
            tv_profileView_married_status, tv_profileView_mother_tongue, tv_profileView_physical_status, tv_profileView_body_type, tv_profileView_profile_created_by, tv_profileView_eating_habit,
            tv_profileView_drinking_habit, tv_profileView_smoking_habit, tv_profileView_eduction, tv_profileView_eduction_in, tv_profileView_working_with, tv_profileView_working_as, tv_profileView_annual_income,
            tv_profileView_country, tv_profileView_state, tv_match_details_city, tv_profileView_family_type, tv_profileView_father_status, tv_profileView_mother_status, tv_profileView_no_of_brother,
            tv_profileView_no_of_brother_married, tv_profileView_no_of_sister, tv_profileView_no_of_sister_married, tv_profileView_birth_date, tv_profileView_birth_time, tv_profileView_birth_place,
            tv_profileView_gotra, tv_profileView_rashi, tv_profileView_nakshtra, tv_profileView_charan, tv_profileView_naadi, tv_profileView_gan, tv_profileView_name, tv_manglik, tv_profileView_complexion;
    private TextView tv_online, tv_offline, tv_profile_managed_by, tv_accept, tvContact, tvEmail;

    private UserDetailsModel mUserDetailsModel;
    private int uniqueId, memberId, homeActivityFlag, packageId = 1;
    private UserContract.UserDetailsOps userDetailsPresenter;
    private ProgressDialog mDialog;
    private TransactionDataContract.TransactionOps transactionDataPresenter;

    private String mTransactionId = "", mPackageId = "1";
    private DownloadProfileContract.DownloadProfileOps downloadProfilePresenter;
    private GetPdfUrlContract.GetPdfUrlOps getPdfUrlPresenter;

    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_details);
        mContext = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.matches_profile_toolbar);
        init();
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
           // getSupportActionBar().setTitle("Matches Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        memberImageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.USER_IMAGE_URL);
        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        Intent p = getIntent();
        if (p != null) {
            uniqueId = p.getExtras().getInt("uniqueId", 0);
            homeActivityFlag = p.getExtras().getInt("flagMatches", 0);
            cname = p.getExtras().getString("name", "");
            setTitle(cname);
        }

        imgShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sId = Integer.parseInt(mUserDetailsModel.getMemberId());

                MatrimonyUtils.addShortlistTask(mContext, memberId, sId, "1");

                imgShortlisted.setVisibility(View.VISIBLE);
                imgShortlist.setVisibility(View.GONE);

                tvShortlist.setVisibility(View.GONE);
                tvShortlisted.setVisibility(View.VISIBLE);
            }
        });

        imgShortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int receiverId = Integer.parseInt(mUserDetailsModel.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "0");

                imgShortlist.setVisibility(View.VISIBLE);
                imgShortlisted.setVisibility(View.GONE);

                tvShortlist.setVisibility(View.VISIBLE);
                tvShortlisted.setVisibility(View.GONE);
            }
        });

        imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = Integer.parseInt(mUserDetailsModel.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                String message = "Invitation Received from " + name;
                MatrimonyUtils.sendInvitationNotification(mUserDetailsModel.getDeviceId(), message, imageUrl,
                        String.valueOf(mUserDetailsModel.getMemberId()), String.valueOf(memberId), name);

                imgInvited.setVisibility(View.VISIBLE);
                imgInvite.setVisibility(View.GONE);

                tvInvite.setVisibility(View.GONE);
                tvInvited.setVisibility(View.VISIBLE);
            }
        });

        imgInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int senderId = Integer.parseInt(mUserDetailsModel.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tvInvite.setVisibility(View.VISIBLE);
                tvInvited.setVisibility(View.GONE);
            }
        });

        ImageView download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = mUserDetailsModel.getFirstName() + "_" + mUserDetailsModel.getLastName();
                getPdfUrlPresenter.getProfilePdf(mUserDetailsModel.getMemberId());
            }
        });

        userDetailsPresenter = new UserPresenter(this);
        userDetailsPresenter.getUserDetails("" + uniqueId);

        transactionDataPresenter = new TransactionPresenter(this);

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
                //  startActivity(new Intent(mContext, HomeActivity.class));
                // finish();
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

        if (homeActivityFlag == 1) {

            Intent intent = NavUtils.getParentActivityIntent(this);

            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            startActivity(intent);

            finish();
        } else if (homeActivityFlag == 2) {

            Intent listIntent = new Intent(mContext, MatriMatchesActivity.class);
            // listIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            listIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(listIntent);
            finish();
        }
    }

    private void init() {

        img_det_premium=(ImageView)findViewById(R.id.img_det_premium);

        basicDetailsContainer = findViewById(R.id.basic_info_container);
        tv_profile_managed_by = findViewById(R.id.tv_match_details_managedBy);
        img_kundli = (AppCompatImageView) findViewById(R.id.img_match_details_kundli);
        profileUrl = (ImageView) findViewById(R.id.img_match_details_image);
        tv_memberId = (TextView) findViewById(R.id.tv_match_details_memberId);
        tv_otherDetails = (TextView) findViewById(R.id.tv_match_details_otherDetails);
        imgInvite = (ImageView) findViewById(R.id.img_match_details_invite);
        imgInvited = (ImageView) findViewById(R.id.img_match_details_invited);
        tvInvited = (TextView) findViewById(R.id.tv_match_details_invited);
        tvInvite = (TextView) findViewById(R.id.tv_match_details_invite);
        tvShortlist = (TextView) findViewById(R.id.tv_match_details_shortlist);
        tvShortlisted = (TextView) findViewById(R.id.tv_match_details_shortlisted);
        imgInvite = (ImageView) findViewById(R.id.img_match_details_invite);
        imgShortlist = (ImageView) findViewById(R.id.img_match_details_shortlist);
        imgShortlisted = (ImageView) findViewById(R.id.img_match_details_shortlisted);
        imgChat = (ImageView) findViewById(R.id.img_match_details__chat);
        tv_profile_created_by = (TextView) findViewById(R.id.tv_match_details_created_by);
        tv_match_details_introduction = (TextView) findViewById(R.id.tv_match_details_introduction);
        tv_profileView_name = (TextView) findViewById(R.id.tv_match_details__name);
        tv_profileView_gender = (TextView) findViewById(R.id.tv_match_details_gender);
        tv_profileView_age = (TextView) findViewById(R.id.tv_match_details_age);
        tv_profileView_height = (TextView) findViewById(R.id.tv_match_details_height);
        tv_profileView_married_status = (TextView) findViewById(R.id.tv_match_details_married_status);
        tv_profileView_mother_tongue = (TextView) findViewById(R.id.tv_match_details_mother_tongue);
        tv_profileView_physical_status = (TextView) findViewById(R.id.tv_match_details_physical_status);
        tv_profileView_body_type = (TextView) findViewById(R.id.tv_match_details_body_type);
        tv_profileView_complexion = (TextView) findViewById(R.id.tv_match_details_complexion);
        tv_profileView_profile_created_by = (TextView) findViewById(R.id.tv_match_details_profile_created_by);
        tv_profileView_eating_habit = (TextView) findViewById(R.id.tv_match_details_eating_habit);
        tv_profileView_drinking_habit = (TextView) findViewById(R.id.tv_match_details_drinking_habit);
        tv_profileView_smoking_habit = (TextView) findViewById(R.id.tv_match_details_smoking_habit);
        tv_profileView_eduction = (TextView) findViewById(R.id.tv_match_details_eduction);
        tv_profileView_eduction_in = (TextView) findViewById(R.id.tv_match_details_eduction_in);
        tv_profileView_working_with = (TextView) findViewById(R.id.tv_match_details_working_with);
        tv_profileView_working_as = (TextView) findViewById(R.id.tv_match_details_working_as);
        tv_profileView_annual_income = (TextView) findViewById(R.id.tv_match_details_annual_income);
        tv_profileView_country = (TextView) findViewById(R.id.tv_match_details_country);
        tv_profileView_state = (TextView) findViewById(R.id.tv_match_details_state);
        tv_match_details_city = (TextView) findViewById(R.id.tv_match_details_city);
        tv_profileView_family_type = (TextView) findViewById(R.id.tv_match_details_family_type);
        tv_profileView_father_status = (TextView) findViewById(R.id.tv_match_details_father_status);
        tv_profileView_mother_status = (TextView) findViewById(R.id.tv_match_details_mother_status);
        tv_profileView_no_of_brother = (TextView) findViewById(R.id.tv_match_details_no_of_brother);
        tv_profileView_no_of_brother_married = (TextView) findViewById(R.id.tv_match_details_no_of_brother_married);
        tv_profileView_no_of_sister = (TextView) findViewById(R.id.tv_match_details_no_of_sister);
        tv_profileView_no_of_sister_married = (TextView) findViewById(R.id.tv_match_details_no_of_sister_married);
        tv_profileView_birth_date = (TextView) findViewById(R.id.tv_match_details_birth_date);
        tv_profileView_birth_time = (TextView) findViewById(R.id.tv_match_details_birth_time);
        tv_profileView_birth_place = (TextView) findViewById(R.id.tv_match_details_birth_place);
        tv_profileView_gotra = (TextView) findViewById(R.id.tv_match_details_gotra);
        tv_profileView_rashi = (TextView) findViewById(R.id.tv_match_details_rashi);
        tv_profileView_nakshtra = (TextView) findViewById(R.id.tv_match_details_nakshtra);
        tv_profileView_charan = (TextView) findViewById(R.id.tv_match_details_charan);
        tv_profileView_naadi = (TextView) findViewById(R.id.tv_match_details_naadi);
        tv_profileView_gan = (TextView) findViewById(R.id.tv_match_details_gan);
        tv_manglik = (TextView) findViewById(R.id.tv_match_details_manglik);
        img_profileViewverPic = (CircularImageView) findViewById(R.id.img_match_detailsviwerPic);
        img_memberProfile = (CircularImageView) findViewById(R.id.img_match_details_user);
        img_match_age = (ImageView) findViewById(R.id.img_match_details_matchAge);
        img_match_height = (ImageView) findViewById(R.id.img_match_details_matchHeight);
        img_match_smoke = (ImageView) findViewById(R.id.img_match_details_matchSHabit);
        img_match_food = (ImageView) findViewById(R.id.img_match_details_matchEHabit);
        img_match_drink = (ImageView) findViewById(R.id.img_match_details_matchDHabit);
        img_match_married = (ImageView) findViewById(R.id.img_match_details_matchMarriedStatus);
        tv_online = findViewById(R.id.tv_match_details_online);
        tv_offline = findViewById(R.id.tv_match_details_Offline);
        imgAccept = findViewById(R.id.img_match_details_accept);
        tv_accept = findViewById(R.id.tv_match_details_accept);
        tvContact = findViewById(R.id.tv_contact);
        tvEmail = findViewById(R.id.tv_email);

//        tvContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
//                }
//            }
//        });
    }

    @Override
    public void showUserDetails(UserDetailsModel userDetailsModel) {
        try {
            mUserDetailsModel = userDetailsModel;

            MatrimonyUtils.sendMemberIdTask(memberId, Integer.parseInt(mUserDetailsModel.getMemberId()));

            String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
            String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

            String message = "" + name + " " + "viewed your profile";

            MatrimonyUtils.sendProfileViewNotification(mUserDetailsModel.getDeviceId(), message,
                    imageUrl, String.valueOf(mUserDetailsModel.getMemberId()), String.valueOf(memberId),
                    name);


            tv_match_details_introduction.setText(mUserDetailsModel.getPartnerIntroduction());
            tv_profileView_physical_status.setText(mUserDetailsModel.getPhysicalchallenge());
            tv_profileView_eating_habit.setText(mUserDetailsModel.getFoodtype());
            tv_profileView_smoking_habit.setText(mUserDetailsModel.getSmokehabit());
            tv_profileView_drinking_habit.setText(mUserDetailsModel.getDrinkhabit());
            tv_profileView_eduction.setText(mUserDetailsModel.getEducationWithName());
            tv_profileView_eduction_in.setText(mUserDetailsModel.getEducationInName());
            tv_profileView_working_with.setText(mUserDetailsModel.getEducationWithName());
            tv_profileView_working_as.setText(mUserDetailsModel.getMemberOccupation());

            imgInvite.setVisibility(View.GONE);
            imgInvited.setVisibility(View.GONE);
            tvInvite.setVisibility(View.GONE);
            tvInvited.setVisibility(View.GONE);
            imgAccept.setVisibility(View.GONE);
            tv_accept.setVisibility(View.GONE);

            if (TextUtils.isEmpty(mUserDetailsModel.getShorted()) || mUserDetailsModel.getShorted().contains("0")) {

                imgShortlist.setVisibility(View.VISIBLE);
                imgShortlisted.setVisibility(View.GONE);
                tvShortlist.setVisibility(View.VISIBLE);
                tvShortlisted.setVisibility(View.GONE);
            } else {

                imgShortlisted.setVisibility(View.VISIBLE);
                imgShortlist.setVisibility(View.GONE);
                tvShortlist.setVisibility(View.GONE);
                tvShortlisted.setVisibility(View.VISIBLE);
            }

            if (mUserDetailsModel.getInvited().equals(String.valueOf(mUserDetailsModel.getMemberId()))) {

                imgAccept.setVisibility(View.VISIBLE);
                tv_accept.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);
                imgInvite.setVisibility(View.GONE);
                tvInvite.setVisibility(View.GONE);
                tvInvited.setVisibility(View.GONE);

            } else {

                if (TextUtils.isEmpty(mUserDetailsModel.getInvited()) || mUserDetailsModel.getInvited().contains("0")) {
                    imgInvite.setVisibility(View.VISIBLE);
                    imgInvited.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.VISIBLE);
                    tvInvited.setVisibility(View.GONE);
                    imgAccept.setVisibility(View.GONE);

                } else {
                    imgInvited.setVisibility(View.VISIBLE);
                    imgInvite.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    tvInvited.setVisibility(View.VISIBLE);
                    imgAccept.setVisibility(View.GONE);
                }
            }

            if (mUserDetailsModel.getMainProfilePhoto().isEmpty()) {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .error(R.drawable.img_preview)
                        .into(profileUrl);
            } else {

                Picasso.get()
                        .load(mUserDetailsModel.getMainProfilePhoto())
                        .placeholder(R.drawable.img_preview)
                        .into(profileUrl);
            }

            if (memberImageUrl.isEmpty()) {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .error(R.drawable.img_preview)
                        .into(img_memberProfile);
            } else {

                Picasso.get()
                        .load(memberImageUrl)
                        .placeholder(R.drawable.img_preview)
                        .into(img_memberProfile);
            }

            if(mUserDetailsModel.getIspremium().equalsIgnoreCase("0")){
                img_det_premium.setVisibility(View.GONE);
            }else {
                img_det_premium.setVisibility(View.VISIBLE);
            }


            mUserDetailsModel.setAge(MatrimonyUtils.getAge(mUserDetailsModel.getDOB()));
            tv_memberId.setText(mUserDetailsModel.getUniqueId());
            String onlineTime = mUserDetailsModel.getOnlineTime();
            String offlineTime = mUserDetailsModel.getOfflineTime();

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

                if (onlineDate.compareTo(offlineDate) < 0) {
                    mUserDetailsModel.setOnline(false);
                } else {
                    mUserDetailsModel.setOnline(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mUserDetailsModel.getOnlinestatus().equalsIgnoreCase("online")) {

                tv_online.setVisibility(View.VISIBLE);
                tv_online.setText("Online");
                tv_offline.setVisibility(View.GONE);

            } else {

                tv_offline.setVisibility(View.VISIBLE);
                tv_offline.setText("Offline ");
                tv_online.setVisibility(View.GONE);
            }

            String age_height_other = mUserDetailsModel.getAge() + ", " + mUserDetailsModel.getMemberHeight() + ", "
                    + mUserDetailsModel.getMemberCity() +
                    ", " + mUserDetailsModel.getMemberState() + ", " + mUserDetailsModel.getMemberCountry();
            tv_otherDetails.setText(age_height_other);

            String profileCreatedBy = mUserDetailsModel.getAccountCreatedBy();
            String profileManagedBy = mUserDetailsModel.getAccountManageBy();

            if (profileManagedBy.equals("Self")) {
                tv_profile_managed_by.setText("Profile Created by Self");
            } else if (profileManagedBy.equals("Parent")) {
                tv_profile_managed_by.setText("Profile Created by Parent");
            } else if (profileManagedBy.equals("Sibling")) {
                tv_profile_managed_by.setText("Profile Created by Sibling");
            }

            if (profileCreatedBy.equals("C")) {
                tv_profile_created_by.setText(candidate);
                tv_profileView_profile_created_by.setText("Self");
            } else {
                tv_profileView_profile_created_by.setText("Parent");
            }

            String username = mUserDetailsModel.getFirstName() + " " + mUserDetailsModel.getLastName();
            tv_profileView_name.setText(username);
            //  ((MatrimonyProfileViewActivity) mContext).setTitleTask(username);
            tv_profileView_gender.setText(mUserDetailsModel.getGender());
            tv_profileView_age.setText(mUserDetailsModel.getAge());
            tv_profileView_height.setText(mUserDetailsModel.getMemberHeight());
            tv_profileView_married_status.setText(mUserDetailsModel.getMarriedStatus());
            tv_profileView_mother_tongue.setText(mUserDetailsModel.getMotherTongue());
            tv_profileView_country.setText(mUserDetailsModel.getMemberCountry());
            tv_profileView_state.setText(mUserDetailsModel.getMemberState());
            tv_match_details_city.setText(mUserDetailsModel.getMemberCity());
            tv_profileView_birth_date.setText(mUserDetailsModel.getDOB());
            tv_profileView_annual_income.setText(mUserDetailsModel.getMemberInCome());
            tv_profileView_family_type.setText(mUserDetailsModel.getFamilyType());
            tv_profileView_father_status.setText(mUserDetailsModel.getFatherstatus());
            tv_profileView_mother_status.setText(mUserDetailsModel.getMotherstatus());
            tv_profileView_no_of_brother.setText(mUserDetailsModel.getNoOfBorther());
            tv_profileView_no_of_brother_married.setText(mUserDetailsModel.getNoOfBortherMarried());
            tv_profileView_no_of_sister.setText(mUserDetailsModel.getNoOfSister());
            tv_profileView_no_of_sister_married.setText(mUserDetailsModel.getNoOfSisterMarried());
            tv_profileView_birth_time.setText(mUserDetailsModel.getBirthTime());
            tv_profileView_birth_place.setText(mUserDetailsModel.getBirthPlace());
            tv_profileView_gotra.setText(mUserDetailsModel.getGotra());
            tv_profileView_rashi.setText(mUserDetailsModel.getRashi());
            tv_profileView_nakshtra.setText(mUserDetailsModel.getNakshtra());
            tv_profileView_charan.setText(mUserDetailsModel.getCharan());
            tv_profileView_naadi.setText(mUserDetailsModel.getNaddi());
            tv_profileView_gan.setText(mUserDetailsModel.getGan());
            tv_profileView_body_type.setText(mUserDetailsModel.getBodytype());
            tv_manglik.setText(mUserDetailsModel.getManglik());
            tv_profileView_complexion.setText(mUserDetailsModel.getComplexion());

            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));

            if (minHeight == 1) {
                img_match_height.setImageResource(R.drawable.ic_check_);

            } else {
                img_match_height.setImageResource(R.drawable.ic_unchecked);
            }

            if (minAge == 1) {
                img_match_age.setImageResource(R.drawable.ic_check_);

            } else {
                img_match_age.setImageResource(R.drawable.ic_unchecked);
            }

            if (foodType == 1) {

                img_match_food.setImageResource(R.drawable.ic_check_);

            } else {
                img_match_food.setImageResource(R.drawable.ic_unchecked);
            }

            if (smokeType == 1) {
                img_match_smoke.setImageResource(R.drawable.ic_check_);

            } else {
                img_match_smoke.setImageResource(R.drawable.ic_unchecked);
            }

            if (drinkType == 1) {
                img_match_drink.setImageResource(R.drawable.ic_check_);

            } else {
                img_match_drink.setImageResource(R.drawable.ic_unchecked);
            }

            if (marriedStatus == 1) {
                img_match_married.setImageResource(R.drawable.ic_check_);

            } else {

                img_match_married.setImageResource(R.drawable.ic_unchecked);
            }

            if (mUserDetailsModel.getKundaliPhoto().equals("") || mUserDetailsModel.getKundaliPhoto().equalsIgnoreCase("")) {
                img_kundli.setVisibility(View.GONE);
            } else {
                img_kundli.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(mUserDetailsModel.getKundaliPhoto())
                        .placeholder(R.drawable.circle_preview)
                        .into(img_kundli);

                img_kundli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("kundali", mUserDetailsModel.getKundaliPhoto());
                        bundle.putString("memberName", mUserDetailsModel.getFirstName() + "_" + mUserDetailsModel.getLastName());
                        imageDisplayFragment.setArguments(bundle);
                        imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                    }
                });
            }

            profileUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mId = Integer.parseInt(mUserDetailsModel.getMemberId());
                    MatrimonyUtils.loadImages(mContext, mId, null);
                }
            });

            imgChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mUserType==1){

                    startActivity(new Intent(mContext, ChatActivity.class));
                    String name = mUserDetailsModel.getFirstName() + " " + mUserDetailsModel.getLastName();
                    String imageUrl = mUserDetailsModel.getMainProfilePhoto();

                    SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                            Integer.parseInt(mUserDetailsModel.getMemberId()));
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                            name);
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                            imageUrl);
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                            mUserDetailsModel.getDeviceId());

                    }else {
                        final Dialog dialogChoice = new Dialog(UserDetailsActivity.this);
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
                                Intent intent=new Intent(UserDetailsActivity.this,DonationActivity.class);
                                startActivity(intent);

                            }
                        });

                    }
                }
            });



            showBasicDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
      /*  try {
            if (mDialog == null) {
                mDialog = new ProgressDialog(this);
                mDialog.setMessage(getString(R.string.please_wait));
                mDialog.setCancelable(false);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog.show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void hideLoading() {
     /*   try {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void showError(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, ""+message, Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void showTransactionData(String transactionId, String amount) {
        try {
            mTransactionId = transactionId;
            float fAmount = Float.parseFloat(amount);

            String memberId = "" + MainPresenter.getInstance().getMemberId(mContext);
            String memberName = MainPresenter.getInstance().getMemberName(mContext);
            String emailId = MainPresenter.getInstance().getEmailId(mContext);
            String phone = MainPresenter.getInstance().getPhone(mContext);
            String productInfo = "Yearly subscription";

            initiatePayment(mContext, memberId, transactionId, fAmount, productInfo, memberName, emailId, phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String result = data.getStringExtra("result");

        String status;

        /**
         * Note:
         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
         * for other constants check the documentation of easebuzz.in here:
         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
         */
        if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
            status = "1";
            successfulTransactionCompletion();
        } else {
            status = "0";
            transactionFailure();
        }
        transactionDataPresenter.updateTransactionData("" + memberId, "" + mPackageId,
                mTransactionId, status);
    }

    private void successfulTransactionCompletion() {
        try {
            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.USERTYPE, 1);
            MainPresenter.getInstance().setUserType(mContext, 1);
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionFailure() {
        try {
            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.USERTYPE, 0);
            MainPresenter.getInstance().setUserType(mContext, 0);
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTransactionUpdate(String message) {

    }

    private void showBasicDetails() {
//        basicDetailsContainer.addView(addData("Name",
//                mUserDetailsModel.getFirstName() + " " + mUserDetailsModel.getLastName(), null));
//
//        if (Utilities.isEmpty(mUserDetailsModel.getEmailId()) && Utilities.isEmpty(mUserDetailsModel.getMobile())) {
//            return;
//        }
//
//        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
//            @Override
//            public void onViewClick(View view) {
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    PaymentActivity.startPaymentActivity(UserDetailsActivity.this, PaymentActivity.REQUEST_CODE, memberId, packageId);
//                    return;
//                }
//
//                ShowContactFragment showContactFragment = new ShowContactFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.CONTACT_NO, mUserDetailsModel.getMobile());
//                bundle.putString(Constants.EMAIL, mUserDetailsModel.getEmailId());
//                showContactFragment.setArguments(bundle);
//
//                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
//            }
//        }));

//        String contact = mUserDetailsModel.getMobile();
//        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//            contact = Utilities.partiallyHideContact(contact);
//        }
//        basicDetailsContainer.addView(addData("Contact", contact, new ViewClickListener() {
//            @Override
//            public void onViewClick(View view) {
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
//                }
//            }
//        }));
//
//        String emailId = mUserDetailsModel.getEmailId();
//        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//            emailId = Utilities.partiallyHideEmail(emailId);
//        }
//        basicDetailsContainer.addView(addData("Email", emailId, new ViewClickListener() {
//            @Override
//            public void onViewClick(View view) {
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
//                }
//            }
//        }));

        showBasicDetails(mUserDetailsModel, basicDetailsContainer);
    }

//    private View addData(String title, String text, ViewClickListener viewClickListener) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
//        TextView titleTxt = view.findViewById(R.id.title);
//        TextView value = view.findViewById(R.id.value);
//
//        Utilities.setText(titleTxt, title);
//        Utilities.setText(value, text, viewClickListener);
//        return view;
//    }

    private void showBasicDetails(final UserDetailsModel userDetailsModel, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", userDetailsModel.getFirstName() + " " + userDetailsModel.getLastName(),
                null));

      /*  if(Utilities.isEmpty(userDetailsModel.getEmailId()) && Utilities.isEmpty(userDetailsModel.getMobile())){
            return;
        }*/

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {

                int myMemberId = SharedPrefsHelper.getInstance(mContext).getIntVal("memberId");

                new ViewContactPresenter("" + myMemberId, userDetailsModel.getMemberId(), new ViewContactPresenter.RestView() {
                    @Override
                    public Context getContext() {
                        return mContext;
                    }

                    @Override
                    public void showMessage(String message) {

                        ShowContactFragment showContactFragment = new ShowContactFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.MEMBER_ID, userDetailsModel.getMemberId());
                        bundle.putString(Constants.CONTACT_NO, userDetailsModel.getMobile());
                        bundle.putString(Constants.EMAIL, userDetailsModel.getEmailId());
                        showContactFragment.setArguments(bundle);

                        showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");

                    }

                    @Override
                    public void showLoading() {

                    }

                    @Override
                    public void hideLoading() {

                    }

                    @Override
                    public void showError(String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
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
    }

    @Override
    public void showFileDownloaded(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showPdfUrl(String url) {

        downloadProfilePresenter.downloadFile(fileName,url);
        //downloadProfilePresenter.downloadFile(mUserDetailsModel, url);
    }

    @Override
    public void initiatePayment() {
        Intent intent = new Intent(mContext, PackagesActivity.class);
        startActivityForResult(intent, 111);
    }

//    private void showBasicDetails(final UserProfileModel userProfileModel, LinearLayout basicDetailsContainer) {
//        basicDetailsContainer.addView(addData("Name", userProfileModel.getFirstName() + " " + userProfileModel.getLastName(),
//                null));
//
//        if (Utilities.isEmpty(userProfileModel.getEmailid()) && Utilities.isEmpty(userProfileModel.getMobile())) {
//            return;
//        }
//
//        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
//            @Override
//            public void onViewClick(View view) {
//                ShowContactFragment showContactFragment = new ShowContactFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.CONTACT_NO, userProfileModel.getMobile());
//                bundle.putString(Constants.EMAIL, userProfileModel.getEmailid());
//                showContactFragment.setArguments(bundle);
//
//                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
//            }
//        }));
//    }
//
//    private View addData(String title, String text, ViewClickListener viewClickListener) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
//        TextView titleTxt = view.findViewById(R.id.title);
//        TextView value = view.findViewById(R.id.value);
//
//        Utilities.setText(titleTxt, title);
//        Utilities.setText(value, text, viewClickListener);
//        return view;
//    }
//
//    private View addViewContact(final ViewClickListener viewClickListener) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_view_action_layout, null);
//        Button view_contact = view.findViewById(R.id.view_contact);
//        view_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewClickListener.onViewClick(v);
//            }
//        });
//        return view;
//    }
}