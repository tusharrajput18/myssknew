package in.co.vsys.myssksamaj.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomPagerAdapter;
import in.co.vsys.myssksamaj.contracts.DownloadProfileContract;
import in.co.vsys.myssksamaj.contracts.GetPdfUrlContract;
import in.co.vsys.myssksamaj.contracts.UserMatchContract;
import in.co.vsys.myssksamaj.fragments.ImageDisplayFragment;
import in.co.vsys.myssksamaj.fragments.RecentMatchFragment;
import in.co.vsys.myssksamaj.fragments.ShowContactFragment;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.InitiatePaymentListener;
import in.co.vsys.myssksamaj.interfaces.PageSelectionListener;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserMatchModel;
import in.co.vsys.myssksamaj.presenters.DownloadProfilePresenter;
import in.co.vsys.myssksamaj.presenters.GetPdfUrlPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.UserMatchPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class RecentMatchesPagerActivity extends AppCompatActivity implements UserMatchContract.UserMatchView, PageSelectionListener, InitiatePaymentListener, DownloadProfileContract.DownloadProfileView,
        GetPdfUrlContract.GetPdfUrlView {

    private Context mContext;
    private ViewPager viewPager;
    private int memberId;
    private UserMatchContract.UserMatchOps userMatchPresenter;
    private int profileMatchesPastVisibleItems, profileMatchesVisibleItemCount, profileMatchesTotalItemCount,
            profileMatchesPage = 0;
    private boolean profileMatchesLoading = false;
    private List<UserMatchModel> userMatchModels = new ArrayList<>();
    private CustomPagerAdapter customPagerAdapter;
    private TextView titleTxt;
    private DownloadProfileContract.DownloadProfileOps downloadProfilePresenter;
    private GetPdfUrlContract.GetPdfUrlOps getPdfUrlPresenter;
    private String fileName;
    public static String userName = "";
    private int pageCount;
    private int position1,userMemeberId,homeActivityFlag;
    private UserMatchModel currentProfileSelected;
    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_matches_pager);
        mContext = this;
        viewPager = findViewById(R.id.pager);
        titleTxt = findViewById(R.id.title);
        Log.d("mytag", "onCreate:onCreate onCreate onCreate onCreate onCreate onCreate ");


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

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userMatchPresenter = new UserMatchPresenter(this);

//        intent.putExtra("uniqueId", uniqueId);
//        intent.putExtra("flagMatches", 1);
//        init();

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        userMatchPresenter.getUserMatch("" + memberId, "" + pageCount);
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        MainPresenter.getInstance().setPageSelectionListener(this);
        getPdfUrlPresenter = new GetPdfUrlPresenter(this);
        downloadProfilePresenter = new DownloadProfilePresenter(this);
    }

    @Override
    public void showUserMatch(final List<UserMatchModel> data) {
        titleTxt.setText(data.get(position1).getFirstName() + " " + data.get(position1).getLastName());
        userMatchModels.addAll(data);

        currentProfileSelected=userMatchModels.get(position1);

        FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                //Toast.makeText(mContext, ""+userMatchModels.get(position), Toast.LENGTH_SHORT).show();

                RecentMatchFragment recentMatchFragment = new RecentMatchFragment();
                recentMatchFragment.setInitiatePaymentListener(RecentMatchesPagerActivity.this);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ShareableIntents.USER_DETAILS, userMatchModels.get(position));
                recentMatchFragment.setArguments(bundle);
                return recentMatchFragment;
            }

            @Override
            public int getCount() {
                return userMatchModels.size();
            }
        };
        viewPager.setAdapter(fragmentStatePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == userMatchModels.size() - 1) {
                    profileMatchesPage++;
                    userMatchPresenter.getUserMatch("" + memberId, "" + profileMatchesPage);
                }
                titleTxt.setText(userMatchModels.get(position).getFirstName() + " " + userMatchModels.get(position).getLastName());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(position1);

//        userMatchModels.addAll(data);
//        if (customPagerAdapter == null) {
//            customPagerAdapter = new CustomPagerAdapter(mContext, userMatchModels,
//                    R.layout.fragment_recent_matches, new CustomPagerAdapter.ItemClickedListener() {
//                @Override
//                public void onItemClicked(View view, Object object, int position) {
//
//                }
//
//                @Override
//                public void onViewBound(View view, Object object, int position) {
//                    init(view, (UserDetailsModel) object);
//                }
//            });
//            viewPager.setAdapter(customPagerAdapter);
//        } else {
//            customPagerAdapter.notifyDataSetChanged();
//        }

    }

    private void init(View view, final UserDetailsModel mUserDetailsModel) {

        try {
            AppCompatImageView img_kundli;
            String memberImageUrl = "";

            LinearLayout basicDetailsContainer;

            String receivedInviteFlag;
            int minHeight = 0, minAge = 0, marriedStatus = 0, foodType = 0, smokeType = 0, drinkType = 0;
            int uniqueId = 0, homeActivityFlag = 0, packageId = 1;

            ImageView imgInvite, imgInvited, imgShortlist, imgShortlisted, imgChat, profileUrl, imgAccept;
            TextView tvInvite, tvInvited, tvShortlist, tvShortlisted;
            ImageView img_match_age, img_match_height, img_match_drink, img_match_food, img_match_smoke, img_match_married;
            CircularImageView img_profileViewverPic, img_memberProfile;
            TextView tv_memberId, tv_otherDetails, tv_profile_created_by, tv_match_details_introduction, tv_profileView_gender, tv_profileView_age, tv_profileView_height,
                    tv_profileView_married_status, tv_profileView_mother_tongue, tv_profileView_physical_status, tv_profileView_body_type, tv_profileView_profile_created_by, tv_profileView_eating_habit,
                    tv_profileView_drinking_habit, tv_profileView_smoking_habit, tv_profileView_eduction, tv_profileView_eduction_in, tv_profileView_working_with, tv_profileView_working_as, tv_profileView_annual_income,
                    tv_profileView_country, tv_profileView_state, tv_match_details_city, tv_profileView_family_type, tv_profileView_father_status, tv_profileView_mother_status, tv_profileView_no_of_brother,
                    tv_profileView_no_of_brother_married, tv_profileView_no_of_sister, tv_profileView_no_of_sister_married, tv_profileView_birth_date, tv_profileView_birth_time, tv_profileView_birth_place,
                    tv_profileView_gotra, tv_profileView_rashi, tv_profileView_nakshtra, tv_profileView_charan, tv_profileView_naadi, tv_profileView_gan, tv_profileView_name, tv_manglik, tv_profileView_complexion;
            TextView tv_online, tv_offline, tv_profile_managed_by, tv_accept, tvContact, tvEmail;


            basicDetailsContainer = view.findViewById(R.id.basic_info_container);
            tv_profile_managed_by = view.findViewById(R.id.tv_match_details_managedBy);
            img_kundli = (AppCompatImageView) view.findViewById(R.id.img_match_details_kundli);
            profileUrl = (ImageView) view.findViewById(R.id.img_match_details_image);
            tv_memberId = (TextView) view.findViewById(R.id.tv_match_details_memberId);
            tv_otherDetails = (TextView) view.findViewById(R.id.tv_match_details_otherDetails);
            imgInvite = (ImageView) view.findViewById(R.id.img_match_details_invite);
            imgInvited = (ImageView) view.findViewById(R.id.img_match_details_invited);
            tvInvited = (TextView) view.findViewById(R.id.tv_match_details_invited);
            tvInvite = (TextView) view.findViewById(R.id.tv_match_details_invite);
            tvShortlist = (TextView) view.findViewById(R.id.tv_match_details_shortlist);
            tvShortlisted = (TextView) view.findViewById(R.id.tv_match_details_shortlisted);
            imgInvite = (ImageView) view.findViewById(R.id.img_match_details_invite);
            imgShortlist = (ImageView) view.findViewById(R.id.img_match_details_shortlist);
            imgShortlisted = (ImageView) view.findViewById(R.id.img_match_details_shortlisted);
            imgChat = (ImageView) view.findViewById(R.id.img_match_details__chat);
            tv_profile_created_by = (TextView) view.findViewById(R.id.tv_match_details_created_by);
            tv_match_details_introduction = (TextView) view.findViewById(R.id.tv_match_details_introduction);
            tv_profileView_name = (TextView) view.findViewById(R.id.tv_match_details__name);
            tv_profileView_gender = (TextView) view.findViewById(R.id.tv_match_details_gender);
            tv_profileView_age = (TextView) view.findViewById(R.id.tv_match_details_age);
            tv_profileView_height = (TextView) view.findViewById(R.id.tv_match_details_height);
            tv_profileView_married_status = (TextView) view.findViewById(R.id.tv_match_details_married_status);
            tv_profileView_mother_tongue = (TextView) view.findViewById(R.id.tv_match_details_mother_tongue);
            tv_profileView_physical_status = (TextView) view.findViewById(R.id.tv_match_details_physical_status);
            tv_profileView_body_type = (TextView) view.findViewById(R.id.tv_match_details_body_type);
            tv_profileView_complexion = (TextView) view.findViewById(R.id.tv_match_details_complexion);
            tv_profileView_profile_created_by = (TextView) view.findViewById(R.id.tv_match_details_profile_created_by);
            tv_profileView_eating_habit = (TextView) view.findViewById(R.id.tv_match_details_eating_habit);
            tv_profileView_drinking_habit = (TextView) view.findViewById(R.id.tv_match_details_drinking_habit);
            tv_profileView_smoking_habit = (TextView) view.findViewById(R.id.tv_match_details_smoking_habit);
            tv_profileView_eduction = (TextView) view.findViewById(R.id.tv_match_details_eduction);
            tv_profileView_eduction_in = (TextView) view.findViewById(R.id.tv_match_details_eduction_in);
            tv_profileView_working_with = (TextView) view.findViewById(R.id.tv_match_details_working_with);
            tv_profileView_working_as = (TextView) view.findViewById(R.id.tv_match_details_working_as);
            tv_profileView_annual_income = (TextView) view.findViewById(R.id.tv_match_details_annual_income);
            tv_profileView_country = (TextView) view.findViewById(R.id.tv_match_details_country);
            tv_profileView_state = (TextView) view.findViewById(R.id.tv_match_details_state);
            tv_match_details_city = (TextView) view.findViewById(R.id.tv_match_details_city);
            tv_profileView_family_type = (TextView) view.findViewById(R.id.tv_match_details_family_type);
            tv_profileView_father_status = (TextView) view.findViewById(R.id.tv_match_details_father_status);
            tv_profileView_mother_status = (TextView) view.findViewById(R.id.tv_match_details_mother_status);
            tv_profileView_no_of_brother = (TextView) view.findViewById(R.id.tv_match_details_no_of_brother);
            tv_profileView_no_of_brother_married = (TextView) view.findViewById(R.id.tv_match_details_no_of_brother_married);
            tv_profileView_no_of_sister = (TextView) view.findViewById(R.id.tv_match_details_no_of_sister);
            tv_profileView_no_of_sister_married = (TextView) view.findViewById(R.id.tv_match_details_no_of_sister_married);
            tv_profileView_birth_date = (TextView) view.findViewById(R.id.tv_match_details_birth_date);
            tv_profileView_birth_time = (TextView) view.findViewById(R.id.tv_match_details_birth_time);
            tv_profileView_birth_place = (TextView) view.findViewById(R.id.tv_match_details_birth_place);
            tv_profileView_gotra = (TextView) view.findViewById(R.id.tv_match_details_gotra);
            tv_profileView_rashi = (TextView) view.findViewById(R.id.tv_match_details_rashi);
            tv_profileView_nakshtra = (TextView) view.findViewById(R.id.tv_match_details_nakshtra);
            tv_profileView_charan = (TextView) view.findViewById(R.id.tv_match_details_charan);
            tv_profileView_naadi = (TextView) view.findViewById(R.id.tv_match_details_naadi);
            tv_profileView_gan = (TextView) view.findViewById(R.id.tv_match_details_gan);
            tv_manglik = (TextView) view.findViewById(R.id.tv_match_details_manglik);
            img_profileViewverPic = (CircularImageView) view.findViewById(R.id.img_match_detailsviwerPic);
            img_memberProfile = (CircularImageView) view.findViewById(R.id.img_match_details_user);
            img_match_age = (ImageView) view.findViewById(R.id.img_match_details_matchAge);
            img_match_height = (ImageView) view.findViewById(R.id.img_match_details_matchHeight);
            img_match_smoke = (ImageView) view.findViewById(R.id.img_match_details_matchSHabit);
            img_match_food = (ImageView) view.findViewById(R.id.img_match_details_matchEHabit);
            img_match_drink = (ImageView) view.findViewById(R.id.img_match_details_matchDHabit);
            img_match_married = (ImageView) view.findViewById(R.id.img_match_details_matchMarriedStatus);
            tv_online = view.findViewById(R.id.tv_match_details_online);
            tv_offline = view.findViewById(R.id.tv_match_details_Offline);
            imgAccept = view.findViewById(R.id.img_match_details_accept);
            tv_accept = view.findViewById(R.id.tv_match_details_accept);
            tvContact = view.findViewById(R.id.tv_contact);
            tvEmail = view.findViewById(R.id.tv_email);

            ImageView download = view.findViewById(R.id.download);

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fileName = mUserDetailsModel.getFirstName() + "_" + mUserDetailsModel.getLastName();

                    Toast.makeText(RecentMatchesPagerActivity.this, ""+fileName+mUserDetailsModel.getMemberId(), Toast.LENGTH_SHORT).show();

                   /* getPdfUrlPresenter.getProfilePdf(mUserDetailsModel.getMemberId());*/

                }
            });


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
//
//        if (mUserDetailsModel.getMainProfilePhoto().isEmpty()) {
//
//            Picasso.get()
//                    .load(R.drawable.img_preview)
//                    .placeholder(R.drawable.img_preview)
//                    .error(R.drawable.img_preview)
//                    .into(img_profileViewverPic);
//        } else {
//
//            Picasso.get()
//                    .load(matchesModel.getUserProfileUrl())
//                    .placeholder(R.drawable.img_preview)
//                    .into(img_profileViewverPic);
//        }

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

//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        String dob = mUserDetailsModel.getDOB();
//        String[] dob1 = dob.split("/");
//        String dateOfBirth = dob1[2];
//        int age = (year - Integer.parseInt(dateOfBirth));

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

            if (mUserDetailsModel.isOnline()) {

                tv_online.setVisibility(View.VISIBLE);
                tv_online.setText("Online");
                tv_offline.setVisibility(View.GONE);

            } else {

                tv_offline.setVisibility(View.VISIBLE);
                tv_offline.setText("Offline " + mUserDetailsModel.getOfflineTime());
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

            String candidate = "In my own words";

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
                        final Dialog dialogChoice = new Dialog(RecentMatchesPagerActivity.this);
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
                                Intent intent=new Intent(RecentMatchesPagerActivity.this,DonationActivity.class);
                                startActivity(intent);

                            }
                        });
                    }






                }
            });
            showBasicDetails(mUserDetailsModel, basicDetailsContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        tvContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
//                }
//            }
//        });
    }

//    private void showBasicDetails(UserDetailsModel mUserDetailsModel, LinearLayout basicDetailsContainer) {
////        basicDetailsContainer.addView(addData("Name",
////                mUserDetailsModel.getFirstName() + " " + mUserDetailsModel.getLastName(), null));
////
////        if (Utilities.isEmpty(mUserDetailsModel.getEmailId()) && Utilities.isEmpty(mUserDetailsModel.getMobile())) {
////            return;
////        }
////
////        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
////            @Override
////            public void onViewClick(View view) {
////                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////                    PaymentActivity.startPaymentActivity(UserDetailsActivity.this, PaymentActivity.REQUEST_CODE, memberId, packageId);
////                    return;
////                }
////
////                ShowContactFragment showContactFragment = new ShowContactFragment();
////                Bundle bundle = new Bundle();
////                bundle.putString(Constants.CONTACT_NO, mUserDetailsModel.getMobile());
////                bundle.putString(Constants.EMAIL, mUserDetailsModel.getEmailId());
////                showContactFragment.setArguments(bundle);
////
////                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
////            }
////        }));
//
////        String contact = mUserDetailsModel.getMobile();
////        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////            contact = Utilities.partiallyHideContact(contact);
////        }
////        basicDetailsContainer.addView(addData("Contact", contact, new ViewClickListener() {
////            @Override
////            public void onViewClick(View view) {
////                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////                    transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
////                }
////            }
////        }));
////
////        String emailId = mUserDetailsModel.getEmailId();
////        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////            emailId = Utilities.partiallyHideEmail(emailId);
////        }
////        basicDetailsContainer.addView(addData("Email", emailId, new ViewClickListener() {
////            @Override
////            public void onViewClick(View view) {
////                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////                    transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
////                }
////            }
////        }));
//
//        showBasicDetails(mUserDetailsModel, basicDetailsContainer);
//    }

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

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                ShowContactFragment showContactFragment = new ShowContactFragment();
                showContactFragment.setInitiatePaymentListener(RecentMatchesPagerActivity.this);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MEMBER_ID, userDetailsModel.getMemberId());
                bundle.putString(Constants.CONTACT_NO, userDetailsModel.getMobile());
                bundle.putString(Constants.EMAIL, userDetailsModel.getEmailId());
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
    public void onPageSelected(String title) {
        Utilities.setText(titleTxt, title);
    }

    @Override
    public void initiatePayment() {
        Intent intent = new Intent(mContext, PackagesActivity.class);
        startActivityForResult(intent, 111);
    }

    @Override
    public void showFileDownloaded(String message) {

    }

    @Override
    public void showPdfUrl(String url) {
        Toast.makeText(mContext, "" + fileName + " " + url, Toast.LENGTH_SHORT).show();
        //downloadProfilePresenter.downloadFile(fileName, url);
    }
}