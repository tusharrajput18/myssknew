package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.ChatActivity;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.RecentlyJointModel;

/**
 * Created by Jack on 08/12/2017.
 */
public class ViewProfileAdapter extends PagerAdapter {

    String candidate = "In my own words";
    String parents = "a few words about my daughter";
    private String accountCreatedBy;

    private Context mContext;
    private List<RecentlyJointModel> list;
    private LayoutInflater inflater;
    private int memberId;
    private SharedPreferences mPreferences;

    public ViewProfileAdapter(Context mContext, List<RecentlyJointModel> list) {
        this.mContext = mContext;
        this.list = list;

        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.row_profile, null);

        ImageView profileUrl = view.findViewById(R.id.img_profileView_image);
        TextView tv_memberId = view.findViewById(R.id.tv_profileView_memberId);
        TextView tv_otherDetails = view.findViewById(R.id.tv_profile_otherDetails);
        final ImageView imgInvite = view.findViewById(R.id.img_profileView_invite);
        final ImageView imgInvited = view.findViewById(R.id.img_profileView_invited);
        final ImageView imgAccept = view.findViewById(R.id.img_profileView_accept);
        final ImageView imgShortlist = view.findViewById(R.id.img_profileView_shortlist);
        final ImageView imgShortlisted = view.findViewById(R.id.img_profileView_shortlisted);
        final TextView tv_shortlist = view.findViewById(R.id.tv_shortlist);
        final TextView tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
        final TextView tv_invite = view.findViewById(R.id.tv_profileView_invite);
        final TextView tv_invited = view.findViewById(R.id.tv_profileView_invited);
        final TextView tv_accept = view.findViewById(R.id.tv_profileView_accept);
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
        TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
        TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);


        final RecentlyJointModel recentlyJointModel = list.get(position);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        memberId = mPreferences.getInt("memberId", 0);
        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");
        //    Log.d("memberId", String.valueOf(memberId));

        int userId = recentlyJointModel.getUserMemberId();

        MatrimonyUtils.sendMemberIdTask(memberId, userId);


        String name = mPreferences.getString("nav_memberName", "");
        String imageUrl = mPreferences.getString("nav_profileUrl", "");

        String message = "" + name + " " + "viewed your profile";

        MatrimonyUtils.sendProfileViewNotification(recentlyJointModel.getTokenId(), message, imageUrl,
                String.valueOf(recentlyJointModel.getUserMemberId()), String.valueOf(memberId), name);

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

            if (recentlyJointModel.getInviteReceivedFlag().equals(String.valueOf(recentlyJointModel.getUserMemberId()))) {
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

        if (TextUtils.isEmpty(recentlyJointModel.getInvitedFlag()) || recentlyJointModel.getInvitedFlag().contains("0")) {
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

        if (TextUtils.isEmpty(recentlyJointModel.getShortlistedFlag()) || recentlyJointModel.getShortlistedFlag().contains("0")) {
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
                int receiverId = recentlyJointModel.getUserMemberId();
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
                int receiverId = recentlyJointModel.getUserMemberId();
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
                    Toast.makeText(mContext, "You can not invite Candidate profile ", Toast.LENGTH_LONG).show();
                } else {

                    int senderId = list.get(position).getUserMemberId();
                    MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                    String name = mPreferences.getString("nav_memberName", "");
                    String imageUrl = mPreferences.getString("nav_profileUrl", "");

                    String message = "Invitation Received from " + name;

                    MatrimonyUtils.sendInvitationNotification(recentlyJointModel.getTokenId(), message,
                            imageUrl, String.valueOf(recentlyJointModel.getUserMemberId()), String.valueOf(memberId),
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
                int senderId = list.get(position).getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        if (recentlyJointModel.getProfileCreatedBy().equals("P")) {

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

            String age_height_other = recentlyJointModel.getUserCity() +
                    ", " + recentlyJointModel.getUserState() + ", " + recentlyJointModel.getUserCountry();
            tv_otherDetails.setText(age_height_other);

        } else if (recentlyJointModel.getProfileCreatedBy().equals("C")) {

            try {
                recentlyJointModel.setUserAge(MatrimonyUtils.getAge(recentlyJointModel.getDOB()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String age_height_other = recentlyJointModel.getUserAge() + ", " + recentlyJointModel.getUserHeight() + ", " + recentlyJointModel.getUserCity() +
                    ", " + recentlyJointModel.getUserState() + ", " + recentlyJointModel.getUserCountry();
            tv_otherDetails.setText(age_height_other);

            tv_profileView_age.setText(recentlyJointModel.getUserAge());
            tv_profileView_height.setText(recentlyJointModel.getUserHeight());
            tv_profileView_married_status.setText(recentlyJointModel.getMarriedStatus());
            tv_profileView_mother_tongue.setText(recentlyJointModel.getMotherTongue());

            tv_profileView_birth_date.setText(recentlyJointModel.getDOB());
            tv_profileView_introduction.setText(recentlyJointModel.getSelfIntroduction());
            tv_profileView_eduction.setText(recentlyJointModel.getEduction());
            tv_profileView_eduction_in.setText(recentlyJointModel.getEductionIn());
            tv_profileView_working_with.setText(recentlyJointModel.getWorkWith());
            tv_profileView_working_as.setText(recentlyJointModel.getWorkAs());
            tv_profileView_annual_income.setText(recentlyJointModel.getUserIncome());
            tv_profileView_family_type.setText(recentlyJointModel.getFamilyType());
            tv_profileView_father_status.setText(recentlyJointModel.getFatherStatus());
            tv_profileView_mother_status.setText(recentlyJointModel.getMotherstatus());
            tv_profileView_no_of_brother.setText(recentlyJointModel.getNoOfBrothers());
            tv_profileView_no_of_brother_married.setText(recentlyJointModel.getNoOfBrotherMarried());
            tv_profileView_no_of_sister.setText(recentlyJointModel.getNoOfSisters());
            tv_profileView_no_of_sister_married.setText(recentlyJointModel.getNoOfSistersMarried());
            tv_profileView_birth_place.setText(recentlyJointModel.getBirthPlace());
            tv_profileView_birth_time.setText(recentlyJointModel.getBirthTime());
            tv_profileView_gotra.setText(recentlyJointModel.getGotra());
            tv_profileView_rashi.setText(recentlyJointModel.getRashi());
            tv_profileView_nakshtra.setText(recentlyJointModel.getNakstra());
            tv_profileView_charan.setText(recentlyJointModel.getCharan());
            tv_profileView_naadi.setText(recentlyJointModel.getNaaddi());
            tv_profileView_gan.setText(recentlyJointModel.getGan());
            tv_profileView_physical_status.setText(recentlyJointModel.getPhysicalChallege());
            tv_profileView_drinking_habit.setText(recentlyJointModel.getDrinkingHabit());
            tv_profileView_smoking_habit.setText(recentlyJointModel.getSmokingHabit());
            tv_profileView_eating_habit.setText(recentlyJointModel.getFoodType());
            tv_profileView_manglik.setText(recentlyJointModel.getManglik());
            tv_profileView_body_type.setText(recentlyJointModel.getBodyType());
            tv_profileView_complexion.setText(recentlyJointModel.getComplexion());

            AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
            TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);

            if (recentlyJointModel.getKundliPhoto().equals("") || recentlyJointModel.getKundliPhoto().equalsIgnoreCase("")) {

                img_kundli.setVisibility(View.GONE);
                tv_kundli.setVisibility(View.GONE);
            } else {

                img_kundli.setVisibility(View.VISIBLE);
                tv_kundli.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(recentlyJointModel.getKundliPhoto())
                        .placeholder(R.drawable.circle_preview)
                        .into(img_kundli);
            }
        }

//        tvCaste.setText("" + recentlyJointModel.getCaste());
//        tvSubCaste.setText("" + recentlyJointModel.getSubCaste());
        tv_memberId.setText(recentlyJointModel.getUniqueId());

        String profileCreatedBy = recentlyJointModel.getProfileCreatedBy();
        String profileManagedBy = recentlyJointModel.getProfileManagedBy();

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

        String username = recentlyJointModel.getFirstName() + " " + recentlyJointModel.getLastName();
        tv_profileView_name.setText(username);
        tv_profileView_gender.setText(recentlyJointModel.getGender());

        tv_profileView_country.setText(recentlyJointModel.getUserCountry());
        tv_profileView_state.setText(recentlyJointModel.getUserState());
        tv_profileView_city.setText(recentlyJointModel.getUserCity());

        if (list.get(position).getUserProfileUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {
            Picasso.get()
                    .load(list.get(position).getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mId = recentlyJointModel.getUserMemberId();
                MatrimonyUtils.loadImages(mContext, mId, null);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ChatActivity.class));

                String name = list.get(position).getFirstName() + " " + list.get(position).getLastName();
                String imageUrl = list.get(position).getUserProfileUrl();

                mPreferences.edit().putInt("messageReceiverId", list.get(position).getUserMemberId()).apply();
                mPreferences.edit().putString("messageReceiverName", name).apply();
                mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                mPreferences.edit().putString("receiverTokenId", list.get(position).getTokenId()).apply();
            }
        });

        String onlineTime = recentlyJointModel.getOnlieTime();
        String offlineTime = recentlyJointModel.getOfflineTime();

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
                recentlyJointModel.setOnline(false);

            } else {
                recentlyJointModel.setOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (recentlyJointModel.isOnline()) {
            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);
        } else {
            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline " + recentlyJointModel.getOfflineTime());
            tv_online.setVisibility(View.GONE);
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}