package in.co.vsys.myssksamaj.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.ChatActivity;
import in.co.vsys.myssksamaj.activities.DonationActivity;
import in.co.vsys.myssksamaj.activities.HomeActivity;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.InvitationModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InvitationPAcceptAdapter extends PagerAdapter {

    String candidate = "In my own words";
    String parents = "a few words about my daughter";
    private Context mContext;
    private List<InvitationModel> invitationModelList;
    private LayoutInflater inflater;
    private int memberId;
    private SharedPreferences mPreferences;
    private int mUserType;

    public InvitationPAcceptAdapter(Context mContext, List<InvitationModel> invitationModelList) {
        this.mContext = mContext;
        this.invitationModelList = invitationModelList;
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public int getCount() {
        return invitationModelList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_profile, null);

        ImageView profileUrl = view.findViewById(R.id.img_profileView_image);
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
//        TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
//        TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);

        final InvitationModel invitationModel = invitationModelList.get(position);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        MatrimonyUtils.sendMemberIdTask(memberId, invitationModel.getMemberId());

        String name = mPreferences.getString("nav_memberName", "");
        String imageUrl = mPreferences.getString("nav_profileUrl", "");

        String message = "" + name + " " + "viewed your profile";

        MatrimonyUtils.sendProfileViewNotification(invitationModel.getTokenId(), message, imageUrl,
                String.valueOf(invitationModel.getMemberId()), String.valueOf(memberId), name);

        memberId = mPreferences.getInt("memberId", 0);

        String onlineTime = invitationModel.getOnlieTime();
        String offlineTime = invitationModel.getOfflineTime();

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

                invitationModel.setOnline(false);

            } else {

                invitationModel.setOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (invitationModel.getOnlinestatus().equalsIgnoreCase("online")) {

            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);

        } else {

            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline");
            tv_online.setVisibility(View.GONE);
        }


        if (invitationModel.getProfileCreatedBy().equals("P")) {

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

            String age_height_other = invitationModel.getUserCity() +
                    ", " + invitationModel.getUserState() + ", " + invitationModel.getUserCountry();
            tv_otherDetails.setText(age_height_other);

        } else if (invitationModel.getProfileCreatedBy().equals("C")) {

            try {

                int year = Calendar.getInstance().get(Calendar.YEAR);

                String dob = invitationModel.getDateOfBirth();

                String[] dob1 = dob.split("/");

                String dateOfBirth = dob1[2];

                int age = (year - Integer.parseInt(dateOfBirth));

                invitationModel.setUserAge("" + age);


            } catch (ArrayIndexOutOfBoundsException e) {

                e.printStackTrace();
            }

            String age_height_other = invitationModel.getUserAge() + ", " + invitationModel.getUserHeight() + ", " + invitationModel.getUserCity() +
                    ", " + invitationModel.getUserState() + ", " + invitationModel.getUserCountry();
            tv_otherDetails.setText(age_height_other);

            tv_profileView_age.setText(invitationModel.getUserAge());
            tv_profileView_height.setText(invitationModel.getUserHeight());
            tv_profileView_married_status.setText(invitationModel.getMarriedStatus());
            tv_profileView_mother_tongue.setText(invitationModel.getMotherTounge());

            tv_profileView_birth_date.setText(invitationModel.getDateOfBirth());
            tv_profileView_introduction.setText(invitationModel.getSelfIntroduction());
            tv_profileView_eduction.setText(invitationModel.getEduction());
            tv_profileView_eduction_in.setText(invitationModel.getEductionIn());
            tv_profileView_working_with.setText(invitationModel.getWorkingWith());
            tv_profileView_working_as.setText(invitationModel.getWorkingAs());
            tv_profileView_annual_income.setText(invitationModel.getSalary_details());
            tv_profileView_family_type.setText(invitationModel.getFamily_type());
            tv_profileView_father_status.setText(invitationModel.getFather_status());
            tv_profileView_mother_status.setText(invitationModel.getMother_status());
            tv_profileView_no_of_brother.setText(invitationModel.getNoOfBrothers());
            tv_profileView_no_of_brother_married.setText(invitationModel.getNoOfBrotherMarried());
            tv_profileView_no_of_sister.setText(invitationModel.getNoOfSisters());
            tv_profileView_no_of_sister_married.setText(invitationModel.getNoOfSistersMarried());
            tv_profileView_birth_place.setText(invitationModel.getBirthPlae());
            tv_profileView_birth_time.setText(invitationModel.getBirthTime());
            tv_profileView_gotra.setText(invitationModel.getGotra());
            tv_profileView_rashi.setText(invitationModel.getRashi());
            tv_profileView_nakshtra.setText(invitationModel.getNakshatra());
            tv_profileView_charan.setText(invitationModel.getCharan());
            tv_profileView_naadi.setText(invitationModel.getNaadi());
            tv_profileView_gan.setText(invitationModel.getGan());
            tv_profileView_physical_status.setText(invitationModel.getPhysicalChallege());
            tv_profileView_drinking_habit.setText(invitationModel.getDrinkingHabit());
            tv_profileView_smoking_habit.setText(invitationModel.getSmokingHabit());
            tv_profileView_eating_habit.setText(invitationModel.getFoodType());
            tv_profileView_manglik.setText(invitationModel.getManglik());
            tv_profileView_body_type.setText(invitationModel.getBodyType());
            tv_profileView_complexion.setText(invitationModel.getComplexion());

            AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
            TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);

            if (invitationModel.getKundli_photo().equals("") || invitationModel.getKundli_photo().equalsIgnoreCase("")) {

                img_kundli.setVisibility(View.GONE);
                tv_kundli.setVisibility(View.GONE);
            } else {

                img_kundli.setVisibility(View.VISIBLE);
                tv_kundli.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(invitationModel.getKundli_photo())
                        .placeholder(R.drawable.circle_preview)
                        .into(img_kundli);
            }
        }

        //  tvCaste.setText("" + invitationModel.getCaste());
        // tvSubCaste.setText("" + invitationModel.getSubCaste());

        tv_memberId.setText(invitationModel.getUniqueId());
        tv_profileView_country.setText(invitationModel.getUserCountry());
        tv_profileView_state.setText(invitationModel.getUserState());
        tv_profileView_city.setText(invitationModel.getUserCity());

        String profileCreatedBy = invitationModel.getProfileCreatedBy();
        String profileManagedBy = invitationModel.getProfileManagedBy();

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

        String username = invitationModel.getFirstName() + " " + invitationModel.getLastName();
        tv_profileView_name.setText(username);
        tv_profileView_gender.setText(invitationModel.getGender());

        if (TextUtils.isEmpty(invitationModel.getShortlistedFlag()) || invitationModel.getShortlistedFlag().contains("0")) {

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

        if (TextUtils.isEmpty(invitationModel.getInvitedFlag()) || invitationModel.getInvitedFlag().contains("0")) {

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

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUserType==1) {

                    String name = invitationModelList.get(position).getFirstName() + " " + invitationModelList.get(position).getLastName();
                    String imageUrl = invitationModelList.get(position).getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", invitationModelList.get(position).getMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", invitationModelList.get(position).getTokenId()).apply();

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));
                }else {
                    final Dialog dialogChoice = new Dialog(mContext);
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
                            Intent intent=new Intent(mContext, DonationActivity.class);
                            mContext.startActivity(intent);

                        }
                    });
                }
            }
        });

        imgShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int receiverId = invitationModel.getMemberId();

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

                int receiverId = invitationModel.getMemberId();
                MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "0");

                imgShortlisted.setVisibility(View.GONE);
                imgShortlist.setVisibility(View.VISIBLE);

                tv_shortlist.setVisibility(View.VISIBLE);
                tv_shortlisted.setVisibility(View.GONE);
            }
        });


        /*imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = invitationModelList.get(position).getMemberId();
                matrimonyUtils.inviteTask(memberId, senderId, "1");


                imgInvited.setVisibility(View.VISIBLE);
                imgInvite.setVisibility(View.GONE);

                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.VISIBLE);

            }
        });

        imgInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = invitationModelList.get(position).getMemberId();
                matrimonyUtils.inviteTask(memberId, senderId, "0");


                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);


            }
        });*/


        if (invitationModel.getUserProfileUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {

            Picasso.get()
                    .load(invitationModel.getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mId = invitationModel.getMemberId();
                //  matrimonyUtils.phList.clear();
                MatrimonyUtils.loadImages(mContext, mId, null);
            }
        });


        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
