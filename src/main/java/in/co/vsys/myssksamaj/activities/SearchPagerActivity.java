package in.co.vsys.myssksamaj.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.adapter.CustomPagerAdapter;
import in.co.vsys.myssksamaj.contracts.InviteContract;
import in.co.vsys.myssksamaj.contracts.ProfileViewNotificationContract;
import in.co.vsys.myssksamaj.contracts.SearchContract;
import in.co.vsys.myssksamaj.contracts.ShortListContract;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.contracts.VisitContract;
import in.co.vsys.myssksamaj.fragments.ImageDisplayFragment;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.SearchModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.InvitePresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.ProfileViewNotificationPresenter;
import in.co.vsys.myssksamaj.presenters.ShortListPresenter;
import in.co.vsys.myssksamaj.presenters.VisitPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class SearchPagerActivity extends AppCompatActivity implements InviteContract.InviteView, ShortListContract.ShortListView, VisitContract.VisitView, ProfileViewNotificationContract.ProfileViewNotificationView {

    private Context mContext;
    private static final String TAG = SearchPagerActivity.class.getSimpleName();
    private String mTransactionId = "", mPackageId = "1";
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<SearchModel> searchList = new ArrayList<>();
    private CustomListAdapter searchListAdapter;
    private SharedPreferences mPreferences;
    private ViewPager viewPager;
    private int memberId;
    private String minAge, maxAge, minHeight, maxHeight, gender, motherTongue, marriedStatus, countryId,
            stateId, cityId;

    private String candidate = "In my own words";

    private AlertDialog alertDialog;
    private TransactionDataContract.TransactionOps transactionDataPresenter;
    private SearchContract.SearchOps searchPresenter;
    private InviteContract.InviteOps invitePresenter;
    private ShortListContract.ShortListOps shortListPresenter;
    private VisitContract.VisitOps visitPresenter;

    private ProfileViewNotificationContract.ProfileViewNotificationOps profileViewNotificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pager);

        invitePresenter = new InvitePresenter(this);
        shortListPresenter = new ShortListPresenter(this);
        visitPresenter = new VisitPresenter(this);
        profileViewNotificationPresenter = new ProfileViewNotificationPresenter(this);


        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(mContext, searchList, R.layout.row_profile, new CustomPagerAdapter.ItemClickedListener() {
            @Override
            public void onItemClicked(View view, Object object, int position) {

            }

            @Override
            public void onViewBound(View view, Object object, int position) {
                final UserProfileModel userProfileModel = (UserProfileModel) object;

                LinearLayout basicDetailsContainer = findViewById(R.id.basic_info_container);
                final ImageView profileUrl = view.findViewById(R.id.img_profileView_image);
                TextView tv_memberId = view.findViewById(R.id.tv_profileView_memberId);
                TextView tv_otherDetails = view.findViewById(R.id.tv_profile_otherDetails);
                final ImageView imgInvite = view.findViewById(R.id.img_profileView_invite);
                final ImageView imgInvited = view.findViewById(R.id.img_profileView_invited);
                final ImageView imgShortlist = view.findViewById(R.id.img_profileView_shortlist);
                final ImageView imgShortlisted = view.findViewById(R.id.img_profileView_shortlisted);
                final TextView tv_shortlist = view.findViewById(R.id.tv_shortlist);
                final TextView tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
                final TextView tv_invite = view.findViewById(R.id.tv_profileView_invite);
                final TextView tv_invited = view.findViewById(R.id.tv_profileView_invited);
                ImageView imgChat = view.findViewById(R.id.img_profileView_chat);
                TextView tv_profile_created_by = view.findViewById(R.id.tv_profile_created_by);
                TextView tv_profile_managed_by = view.findViewById(R.id.tv_profile_managedBy);
                TextView tv_profileView_introduction = view.findViewById(R.id.tv_profileView_introduction);
                TextView tv_profileView_name = view.findViewById(R.id.tv_profileView_name);
                TextView tv_profileView_gender = view.findViewById(R.id.tv_profileView_gender);
                TextView tv_profileView_age = view.findViewById(R.id.tv_profileView_age);
                TextView tv_profileView_height = view.findViewById(R.id.tv_profileView_height);
                TextView tv_profileView_married_status = view.findViewById(R.id.tv_profileView_married_status);
                TextView tv_profileView_mother_tongue = view.findViewById(R.id.tv_profileView_mother_tongue);
                TextView tv_profileView_physical_status = view.findViewById(R.id.tv_profileView_physical_status);
                TextView tv_profileView_body_type = view.findViewById(R.id.tv_profileView_body_type);
                TextView tv_profileView_complexion = view.findViewById(R.id.tv_profileView_complexion);
                TextView tv_profileView_profile_created_by = view.findViewById(R.id.tv_profileView_profile_created_by);
                TextView tv_profileView_eating_habit = view.findViewById(R.id.tv_profileView_eating_habit);
                TextView tv_profileView_drinking_habit = view.findViewById(R.id.tv_profileView_drinking_habit);
                TextView tv_profileView_smoking_habit = view.findViewById(R.id.tv_profileView_smoking_habit);
                TextView tv_profileView_eduction = view.findViewById(R.id.tv_profileView_eduction);
                TextView tv_profileView_eduction_in = view.findViewById(R.id.tv_profileView_eduction_in);
                TextView tv_profileView_working_with = view.findViewById(R.id.tv_profileView_working_with);
                TextView tv_profileView_working_as = view.findViewById(R.id.tv_profileView_working_as);
                TextView tv_profileView_annual_income = view.findViewById(R.id.tv_profileView_annual_income);
                TextView tv_profileView_country = view.findViewById(R.id.tv_profileView_country);
                TextView tv_profileView_state = view.findViewById(R.id.tv_profileView_state);
                TextView tv_profileView_city = view.findViewById(R.id.tv_profileView_city);
                TextView tv_profileView_family_type = view.findViewById(R.id.tv_profileView_family_type);
                TextView tv_profileView_father_status = view.findViewById(R.id.tv_profileView_father_status);
                TextView tv_profileView_mother_status = view.findViewById(R.id.tv_profileView_mother_status);
                TextView tv_profileView_no_of_brother = view.findViewById(R.id.tv_profileView_no_of_brother);
                TextView tv_profileView_no_of_brother_married = view.findViewById(R.id.tv_profileView_no_of_brother_married);
                TextView tv_profileView_no_of_sister = view.findViewById(R.id.tv_profileView_no_of_sister);
                TextView tv_profileView_no_of_sister_married = view.findViewById(R.id.tv_profileView_no_of_sister_married);
                TextView tv_profileView_birth_date = view.findViewById(R.id.tv_profileView_birth_date);
                TextView tv_profileView_birth_time = view.findViewById(R.id.tv_profileView_birth_time);
                TextView tv_profileView_birth_place = view.findViewById(R.id.tv_profileView_birth_place);
                TextView tv_profileView_gotra = view.findViewById(R.id.tv_profileView_gotra);
                TextView tv_profileView_rashi = view.findViewById(R.id.tv_profileView_rashi);
                TextView tv_profileView_nakshtra = view.findViewById(R.id.tv_profileView_nakshtra);
                TextView tv_profileView_charan = view.findViewById(R.id.tv_profileView_charan);
                TextView tv_profileView_naadi = view.findViewById(R.id.tv_profileView_naadi);
                TextView tv_profileView_gan = view.findViewById(R.id.tv_profileView_gan);
                TextView tv_profileView_manglik = view.findViewById(R.id.tv_profileView_manglik);
                TextView tv_online = view.findViewById(R.id.tv_profileView_online);
                TextView tv_offline = view.findViewById(R.id.tv_profileView_Offline);
                LinearLayout layout_profession = view.findViewById(R.id.layout_profileViewProfession);
                LinearLayout layout_location = view.findViewById(R.id.layout_tv_profileViewLocation);
                LinearLayout layout_familyDetails = view.findViewById(R.id.layout_profileViewFamilyDetails);
                LinearLayout layout_Astro = view.findViewById(R.id.layout_profileViewAstroDetails);
                View view_profession = view.findViewById(R.id.view_profileViewProfession);
                View view_location = view.findViewById(R.id.view_profileViewLocation);
                View view_family = view.findViewById(R.id.view_profileViewFamilyDetails);
                View view_astro = view.findViewById(R.id.view_profileViewAstroDetails);
                TextView tv_professionHeading = view.findViewById(R.id.tv_profileViewProfession);
                TextView tv_locationHeading = view.findViewById(R.id.tv_profileViewLocation);
                TextView tv_familyHeading = view.findViewById(R.id.tv_profileViewFamilyDetails);
                TextView tv_astroHeading = view.findViewById(R.id.tv_profileViewAstroDetails);
                TextView tv_age_heading = view.findViewById(R.id.tv_age);
                TextView tv_height_heading = view.findViewById(R.id.tv_height);
                TextView tv_marriedStatus_heading = view.findViewById(R.id.tv_married_status);
                TextView tv_motherTounge_heading = view.findViewById(R.id.tv_mother_tongue);
                TextView tv_physicalStatus_heading = view.findViewById(R.id.tv_physical);
                TextView tv_bodyType_heading = view.findViewById(R.id.tv_body_type);
                TextView tv_complexion_heading = view.findViewById(R.id.tv_body_complexion);
                TextView tv_eating_heading = view.findViewById(R.id.tv_Eating_Habits);
                TextView tv_drinking_heading = view.findViewById(R.id.tv_drinking_habit);
                TextView tv_smoking_heading = view.findViewById(R.id.tv_smoking_habits);
                TextView tv_age_heading_dot = view.findViewById(R.id.tv_age_dot);
                TextView tv_height_heading_dot = view.findViewById(R.id.tv_height_dot);
                TextView tv_marriedStatus_heading_dot = view.findViewById(R.id.tv_married_statusDot);
                TextView tv_motherTounge_heading_dot = view.findViewById(R.id.tv_mother_tongueDot);
                TextView tv_physicalStatus_heading_dot = view.findViewById(R.id.tv_physicalDot);
                TextView tv_bodyType_heading_dot = view.findViewById(R.id.tv_body_typeDot);
                TextView tv_complexion_heading_dot = view.findViewById(R.id.tv_body_complexionDot);
                TextView tv_eating_heading_dot = view.findViewById(R.id.tv_Eating_HabitsDot);
                TextView tv_drinking_heading_dot = view.findViewById(R.id.tv_drinking_habitDot);
                TextView tv_smoking_heading_dot = view.findViewById(R.id.tv_smoking_habitsDot);
//                TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
//                TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);

                if (userProfileModel.getMainProfilePhoto().isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.img_preview)
                            .placeholder(R.drawable.img_preview)
                            .error(R.drawable.img_preview)
                            .into(profileUrl);
                } else {

                    Picasso.get()
                            .load(userProfileModel.getMainProfilePhoto())
                            .placeholder(R.drawable.img_preview)
                            .into(profileUrl);
                }


//                final InvitationModel invitationModel = invitationModelList.get(position);

                mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//                matrimonyUtils = new MatrimonyUtils(mContext);

                visitPresenter.performVisit("" + memberId, userProfileModel.getMemberId());

                String name = mPreferences.getString("nav_memberName", "");
                String imageUrl = mPreferences.getString("nav_profileUrl", "");

                String message = "" + name + " " + "viewed your profile";

                profileViewNotificationPresenter.sendViewProfileNotification(userProfileModel.getDeviceId(),
                        message, Constants.APP_NAME, imageUrl,
                        "" + userProfileModel.getMemberId(), "" + memberId,
                        name);
//                matrimonyUtils.sendProfileViewNotification(invitationModel.getTokenId(), message, imageUrl, String.valueOf(invitationModel.getMemberId()), String.valueOf(memberId), name);


                memberId = mPreferences.getInt("memberId", 0);

                String onlineTime = userProfileModel.getOnlineTime();
                String offlineTime = userProfileModel.getOffline_Time();

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

                        userProfileModel.setOnline(false);

                    } else {

                        userProfileModel.setOnline(true);
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

                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        String dob = userProfileModel.getDOB();
                        String[] dob1 = dob.split("/");
                        String dateOfBirth = dob1[2];
                        int age = (year - Integer.parseInt(dateOfBirth));

                        userProfileModel.setAge("" + age);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    String age_height_other = userProfileModel.getAge() + ", "
                            + userProfileModel.getMemberHeight() + ", " + userProfileModel.getMemberCity() +
                            ", " + userProfileModel.getMemberState() + ", " + userProfileModel.getMemberCountry();
                    tv_otherDetails.setText(age_height_other);

                    tv_profileView_age.setText(userProfileModel.getAge());
                    tv_profileView_height.setText(userProfileModel.getMemberHeight());
                    tv_profileView_married_status.setText(userProfileModel.getMarriedStatus());
                    tv_profileView_mother_tongue.setText(userProfileModel.getMotherTongue());

                    tv_profileView_birth_date.setText(userProfileModel.getDOB());
                    tv_profileView_introduction.setText(userProfileModel.getSelfIntroduction());
                    tv_profileView_eduction.setText(userProfileModel.getMemberEducationName());
                    tv_profileView_eduction_in.setText(userProfileModel.getEducationInName());
                    tv_profileView_working_with.setText(userProfileModel.getMemberOccupation());
                    //TODO: verify what is this
//                    tv_profileView_working_as.setText(userProfileModel.getWorkingAs());
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

                    AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
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

                    TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);

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
                    }
                }

                // tvCaste.setText("" + userProfileModel.getCaste());
                //  tvSubCaste.setText("" + userProfileModel.getSubCaste());

                tv_memberId.setText(userProfileModel.getUniqueId());
                tv_profileView_country.setText(userProfileModel.getMemberCountry());
                tv_profileView_state.setText(userProfileModel.getMemberState());
                tv_profileView_city.setText(userProfileModel.getMemberCity());

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

                if (TextUtils.isEmpty(userProfileModel.getInvited()) || userProfileModel.getInvited().contains("0")) {

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

                        int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                        shortListPresenter.performShortlist("" + memberId, "" + receiverId, "1");

                        imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        imgShortlist.setVisibility(imgShortlist.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

                        tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    }
                });

                imgShortlisted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int receiverId = Integer.parseInt(userProfileModel.getMemberId());

                        shortListPresenter.performShortlist("" + memberId, "" + receiverId, "0");

                        imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        imgShortlist.setVisibility(imgShortlist.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

                        tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    }
                });

                imgInvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int senderId = Integer.parseInt(userProfileModel.getMemberId());
                        invitePresenter.performInvite("" + memberId, "" + senderId, "1");

                        imgInvite.setVisibility(imgInvite.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        imgInvited.setVisibility(imgInvited.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                        tv_invite.setVisibility(tv_invite.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        tv_invited.setVisibility(tv_invited.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                    }
                });

                imgInvited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int senderId = Integer.parseInt(userProfileModel.getMemberId());
                        invitePresenter.performInvite("" + memberId, "" + senderId, "0");

                        imgInvite.setVisibility(imgInvite.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        imgInvited.setVisibility(imgInvited.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                        tv_invite.setVisibility(tv_invite.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        tv_invited.setVisibility(tv_invited.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    }
                });

                if (userProfileModel.getMainProfilePhoto().isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.img_preview)
                            .placeholder(R.drawable.img_preview)
                            .error(R.drawable.img_preview)
                            .into(profileUrl);
                } else {

                    Picasso.get()
                            .load(userProfileModel.getMainProfilePhoto())
                            .placeholder(R.drawable.img_preview)
                            .into(profileUrl);
                }

                profileUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int mId = Integer.parseInt(userProfileModel.getMemberId());
                        //  matrimonyUtils.phList.clear();
                        MatrimonyUtils.loadImages(mContext, memberId, null);
                    }
                });

                basicDetailsContainer.addView(addData("Name",
                        userProfileModel.getFirstName() + " " + userProfileModel.getLastName(), null));

                String contact = userProfileModel.getMobile();
                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    contact = Utilities.partiallyHideContact(contact);
                }
                basicDetailsContainer.addView(addData("Contact", contact, new ViewClickListener() {
                    @Override
                    public void onViewClick(View view) {
                        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                            transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
                        }
                    }
                }));

                String emailId = userProfileModel.getEmailid();
                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    emailId = Utilities.partiallyHideEmail(emailId);
                }
                basicDetailsContainer.addView(addData("Email", emailId, new ViewClickListener() {
                    @Override
                    public void onViewClick(View view) {
                        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                            transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
                        }
                    }
                }));

                basicDetailsContainer.addView(addData("Gender", userProfileModel.getGender(), null));
            }
        });

        viewPager.setAdapter(customPagerAdapter);
    }

    private View addData(String title, String text, ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
        TextView titleTxt = view.findViewById(R.id.title);
        TextView value = view.findViewById(R.id.value);

        Utilities.setText(titleTxt, title);
        Utilities.setText(value, text, viewClickListener);
        return view;
    }

    @Override
    public void showInvited(String message) {
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
    public void showShortlisted(String message) {

    }

    @Override
    public void showVisitPerformed(String message) {

    }

    @Override
    public void showNotificationSent() {

    }
}
