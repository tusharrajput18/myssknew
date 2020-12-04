package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.ChatActivity;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.interfaces.PagerInteractionListener;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.RecentlyProfileVisitorModel;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 06/01/2018.
 */
public class PVisitedVPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<RecentlyProfileVisitorModel> list;
    private LayoutInflater inflater;
    private int memberId;
    private SharedPreferences mPreferences;
    private String candidate = "In my own words";

    private ListClickListener mListClickListener;
    private PagerInteractionListener mPageInteractionListener;

    public PVisitedVPagerAdapter(Context mContext, List<RecentlyProfileVisitorModel> list, PagerInteractionListener
                                 pagerInteractionListener) {
        this.mContext = mContext;
        this.list = list;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mPageInteractionListener = pagerInteractionListener;
    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.mListClickListener = listClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
        TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);
        LinearLayout basicDetailsContainer =(LinearLayout) view.findViewById(R.id.basic_info_container);

        final RecentlyProfileVisitorModel recentlyProfileVisitorModel = list.get(position);


        //String memberName = recentlyJointModel.getFirstName() + "" + recentlyJointModel.getLastName();

        // mPreferences.edit().putString("memberName",memberName).apply();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        memberId = mPreferences.getInt("memberId", 0);

        if (recentlyProfileVisitorModel.getProfileCreatedBy().equals("P")) {

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

            String age_height_other = recentlyProfileVisitorModel.getUserCity() +
                    ", " + recentlyProfileVisitorModel.getUserState() + ", " + recentlyProfileVisitorModel.getUserCountry();
            tv_otherDetails.setText(age_height_other);

        } else if (recentlyProfileVisitorModel.getProfileCreatedBy().equals("C")) {

            try {
                recentlyProfileVisitorModel.setUserAge(MatrimonyUtils.getAge(recentlyProfileVisitorModel.getDOB()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String age_height_other = recentlyProfileVisitorModel.getUserAge() + ", " + recentlyProfileVisitorModel.getUserHeight() + ", " + recentlyProfileVisitorModel.getUserCity() +
                    ", " + recentlyProfileVisitorModel.getUserState() + ", " + recentlyProfileVisitorModel.getUserCountry();
            tv_otherDetails.setText(age_height_other);

            tv_profileView_age.setText(recentlyProfileVisitorModel.getUserAge());
            tv_profileView_height.setText(recentlyProfileVisitorModel.getUserHeight());
            tv_profileView_married_status.setText(recentlyProfileVisitorModel.getMarriedStatus());
            tv_profileView_mother_tongue.setText(recentlyProfileVisitorModel.getMotherTongue());

            tv_profileView_birth_date.setText(recentlyProfileVisitorModel.getDOB());
            tv_profileView_introduction.setText(recentlyProfileVisitorModel.getSelfIntroduction());
            tv_profileView_eduction.setText(recentlyProfileVisitorModel.getEduction());
            tv_profileView_eduction_in.setText(recentlyProfileVisitorModel.getEductionIn());
            tv_profileView_working_with.setText(recentlyProfileVisitorModel.getWorkWith());
            tv_profileView_working_as.setText(recentlyProfileVisitorModel.getWorkAs());
            tv_profileView_annual_income.setText(recentlyProfileVisitorModel.getUserIncome());
            tv_profileView_family_type.setText(recentlyProfileVisitorModel.getFamilyType());
            tv_profileView_father_status.setText(recentlyProfileVisitorModel.getFatherStatus());
            tv_profileView_mother_status.setText(recentlyProfileVisitorModel.getMotherstatus());
            tv_profileView_no_of_brother.setText(recentlyProfileVisitorModel.getNoOfBrothers());
            tv_profileView_no_of_brother_married.setText(recentlyProfileVisitorModel.getNoOfBrotherMarried());
            tv_profileView_no_of_sister.setText(recentlyProfileVisitorModel.getNoOfSisters());
            tv_profileView_no_of_sister_married.setText(recentlyProfileVisitorModel.getNoOfSistersMarried());
            tv_profileView_birth_place.setText(recentlyProfileVisitorModel.getBirthPlace());
            tv_profileView_birth_time.setText(recentlyProfileVisitorModel.getBirthTime());
            tv_profileView_gotra.setText(recentlyProfileVisitorModel.getGotra());
            tv_profileView_rashi.setText(recentlyProfileVisitorModel.getRashi());
            tv_profileView_nakshtra.setText(recentlyProfileVisitorModel.getNakstra());
            tv_profileView_charan.setText(recentlyProfileVisitorModel.getCharan());
            tv_profileView_naadi.setText(recentlyProfileVisitorModel.getNaaddi());
            tv_profileView_gan.setText(recentlyProfileVisitorModel.getGan());
            tv_profileView_physical_status.setText(recentlyProfileVisitorModel.getPhysicalChallege());
            tv_profileView_drinking_habit.setText(recentlyProfileVisitorModel.getDrinkingHabit());
            tv_profileView_smoking_habit.setText(recentlyProfileVisitorModel.getSmokingHabit());
            tv_profileView_eating_habit.setText(recentlyProfileVisitorModel.getFoodType());
            tv_profileView_manglik.setText(recentlyProfileVisitorModel.getManglik());
            tv_profileView_body_type.setText(recentlyProfileVisitorModel.getBodyType());
            tv_profileView_complexion.setText(recentlyProfileVisitorModel.getComplexion());

            AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
            img_kundli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);

            if (recentlyProfileVisitorModel.getKundliPhoto().equals("") || recentlyProfileVisitorModel.getKundliPhoto().equalsIgnoreCase("")) {

                img_kundli.setVisibility(View.GONE);
                tv_kundli.setVisibility(View.GONE);
            } else {

                img_kundli.setVisibility(View.VISIBLE);
                tv_kundli.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(recentlyProfileVisitorModel.getKundliPhoto())
                        .placeholder(R.drawable.circle_preview)
                        .into(img_kundli);
            }
        }

        String onlineTime = recentlyProfileVisitorModel.getOnlieTime();
        String offlineTime = recentlyProfileVisitorModel.getOfflineTime();

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

                recentlyProfileVisitorModel.setOnline(false);

            } else {

                recentlyProfileVisitorModel.setOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        tvCaste.setText("" + recentlyProfileVisitorModel.getCaste());
//        tvSubCaste.setText("" + recentlyProfileVisitorModel.getSubCaste());

        if (recentlyProfileVisitorModel.isOnline()) {

            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);

        } else {

            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline " + recentlyProfileVisitorModel.getOfflineTime());
            tv_online.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(recentlyProfileVisitorModel.getShortlistedFlag()) || recentlyProfileVisitorModel.getShortlistedFlag().contains("0")) {

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

        if (TextUtils.isEmpty(recentlyProfileVisitorModel.getInvitedFlag()) || recentlyProfileVisitorModel.getInvitedFlag().contains("0")) {

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

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    mListClickListener.onListItemClicked(recentlyProfileVisitorModel);
                    return;
                }

                int receiverId = recentlyProfileVisitorModel.getUserMemberId();

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

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    mListClickListener.onListItemClicked(recentlyProfileVisitorModel);
                    return;
                }

                int receiverId = recentlyProfileVisitorModel.getUserMemberId();
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

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    mListClickListener.onListItemClicked(recentlyProfileVisitorModel);
                    return;
                }

                int senderId = list.get(position).getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                String name = mPreferences.getString("nav_memberName", "");
                String message = "Invitation Received from " + name;
                String imageUrl = mPreferences.getString("nav_profileUrl", "");

                MatrimonyUtils.sendInvitationNotification(recentlyProfileVisitorModel.getTokenId(),
                        message, imageUrl, String.valueOf(recentlyProfileVisitorModel.getUserMemberId()),
                        String.valueOf(memberId), name);

                imgInvited.setVisibility(View.VISIBLE);
                imgInvite.setVisibility(View.GONE);

                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.VISIBLE);
            }
        });

        imgInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    mListClickListener.onListItemClicked(recentlyProfileVisitorModel);
                    return;
                }

                int senderId = list.get(position).getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    mListClickListener.onListItemClicked(recentlyProfileVisitorModel);
                    return;
                }

                mContext.startActivity(new Intent(mContext, ChatActivity.class));

                String name = list.get(position).getFirstName() + " " + list.get(position).getLastName();
                String imageUrl = list.get(position).getUserProfileUrl();

                mPreferences.edit().putInt("messageReceiverId", list.get(position).getUserMemberId()).apply();
                mPreferences.edit().putString("messageReceiverName", name).apply();
                mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                mPreferences.edit().putString("receiverTokenId", list.get(position).getTokenId()).apply();
            }
        });

        tv_memberId.setText(recentlyProfileVisitorModel.getUniqueId());

        String profileCreatedBy = recentlyProfileVisitorModel.getProfileCreatedBy();
        String profileManagedBy = recentlyProfileVisitorModel.getProfileManagedBy();

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

        String username = recentlyProfileVisitorModel.getFirstName() + " " + recentlyProfileVisitorModel.getLastName();
        tv_profileView_name.setText(username);
        tv_profileView_gender.setText(recentlyProfileVisitorModel.getGender());

        tv_profileView_country.setText(recentlyProfileVisitorModel.getUserCountry());
        tv_profileView_state.setText(recentlyProfileVisitorModel.getUserState());
        tv_profileView_city.setText(recentlyProfileVisitorModel.getUserCity());

        if (recentlyProfileVisitorModel.getUserProfileUrl().isEmpty()) {
            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {
            Picasso.get()
                    .load(recentlyProfileVisitorModel.getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mId = recentlyProfileVisitorModel.getUserMemberId();
                //  matrimonyUtils.phList.clear();
                MatrimonyUtils.loadImages(mContext, mId, null);
            }
        });

        showBasicDetails(recentlyProfileVisitorModel, basicDetailsContainer);
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

    private void showBasicDetails(final RecentlyProfileVisitorModel recentlyProfileVisitorModel, LinearLayout basicDetailsContainer) {
       try {
           basicDetailsContainer.addView(addData("Name", recentlyProfileVisitorModel.getFirstName() + " " + recentlyProfileVisitorModel.getLastName(),
                   null));
       }catch (Exception e){
           e.printStackTrace();
       }
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
}