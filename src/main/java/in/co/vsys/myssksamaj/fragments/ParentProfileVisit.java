package in.co.vsys.myssksamaj.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.ChatActivity;
import in.co.vsys.myssksamaj.activities.DonationActivity;
import in.co.vsys.myssksamaj.activities.ListActivity;
import in.co.vsys.myssksamaj.activities.PaymentActivity;
import in.co.vsys.myssksamaj.activities.ProfileVisitorViewActivity;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.BlockContract;
import in.co.vsys.myssksamaj.contracts.GetVisitorsByRoleContract;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.BlockPresenter;
import in.co.vsys.myssksamaj.presenters.GetVisitorsByRolePresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentProfileVisit extends Fragment implements GetVisitorsByRoleContract.GetVisitorView, BlockContract.BlockView {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<UserProfileModel> profileVisitorModelList = new ArrayList<>();
    private GetVisitorsByRoleContract.GetVisitorsOps getVisitorsPresenter;
    private CustomListAdapter profileVisitedAdapter;
    private int memberId = 0, packageId = 1;
    private BlockContract.BlockOps blockPresenter;

    private boolean isBlocked = false;

    private static final String TAG = ParentProfileVisit.class.getSimpleName();
    private int mUserType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_profile_visit, container, false);
        mContext = getActivity();

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);
        initUI(view);
        getVisitorsPresenter = new GetVisitorsByRolePresenter(this);
        getVisitorsPresenter.getVisitorsByRole("" + memberId, "P");
        blockPresenter = new BlockPresenter(this);
        Log.d("mytag", "" + TAG);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.profileVisitedList);
    }

    private void showProfileVisitedData(View itemView, final UserProfileModel model, final int position) {
        final ImageView img_scroll_profileImage, img_accept;
        final TextView tv_scroll_memberId, tv_scroll_profile_otherDetails, tv_scroll_profile_languageDetails, tv_scroll_profile_ProfessionDetails, tv_scroll_profile_saleryDetails, tv_scroll_profile_marriedStatus1, tv_scroll_profile_AddressDetails, tv_invite, tv_invited, tv_shortlist, tv_shortlisted;
        final ImageView img_scroll_invite, img_invited, img_scroll_shortlist, img_scroll_shortlisted, img_scroll_chat;
        final TextView tv_online, tv_offline, tv_accept;
        LinearLayout offlineLayout;
        final ImageView blocimageview;
        final LinearLayout block_overlay;
        Button unblock;

        img_scroll_profileImage = itemView.findViewById(R.id.img_scroll_profileImage);
        blocimageview = itemView.findViewById(R.id.blocimageview);
        tv_scroll_memberId = itemView.findViewById(R.id.tv_scroll_memberId);
        img_accept = itemView.findViewById(R.id.img_scroll_accept);
        tv_accept = itemView.findViewById(R.id.tv_scroll_accept);
        tv_scroll_profile_otherDetails = itemView.findViewById(R.id.tv_scroll_profile_otherDetails);
        tv_scroll_profile_languageDetails = itemView.findViewById(R.id.tv_scroll_profile_languageDetails);
        tv_scroll_profile_ProfessionDetails = itemView.findViewById(R.id.tv_scroll_profile_ProfessionDetails);
        tv_scroll_profile_saleryDetails = itemView.findViewById(R.id.tv_scroll_profile_saleryDetails);
        tv_scroll_profile_marriedStatus1 = itemView.findViewById(R.id.tv_scroll_profile_marriedStatus1);
        tv_scroll_profile_AddressDetails = itemView.findViewById(R.id.tv_scroll_profile_AddressDetails);
        img_scroll_invite = itemView.findViewById(R.id.img_scroll_invite);
        img_invited = itemView.findViewById(R.id.img_scroll_invited);
        img_scroll_shortlist = itemView.findViewById(R.id.img_scroll_shortlist);
        img_scroll_shortlisted = itemView.findViewById(R.id.img_scroll_shortlisted);
        img_scroll_chat = itemView.findViewById(R.id.img_scroll_chat);
        tv_shortlist = itemView.findViewById(R.id.tv_scroll_shortlist);
        tv_shortlisted = itemView.findViewById(R.id.tv_scroll_shortlisted);
        tv_invite = itemView.findViewById(R.id.tv_scroll_invite);
        tv_invited = itemView.findViewById(R.id.tv_scroll_invited);
        tv_online = itemView.findViewById(R.id.tv_profileView_online);
        tv_offline = itemView.findViewById(R.id.tv_profileView_Offline);
        block_overlay = itemView.findViewById(R.id.block_overlay);
        unblock = itemView.findViewById(R.id.unblock);

        if (model.getAccountCreatedBy().equalsIgnoreCase("P")) {
            Log.d(TAG, "showProfileVisitedData: " + model.getAccountCreatedBy());
            blocimageview.setVisibility(View.VISIBLE);
            if (model.getBlocked().equalsIgnoreCase("1")) {
                blocimageview.setVisibility(View.GONE);
                block_overlay.setVisibility(View.VISIBLE);

                img_scroll_profileImage.setEnabled(false);
                img_scroll_shortlist.setEnabled(false);
                img_scroll_shortlisted.setEnabled(false);
                img_scroll_invite.setEnabled(false);
                img_invited.setEnabled(false);
                img_scroll_chat.setEnabled(false);
            } else {

                img_scroll_profileImage.setEnabled(true);
                img_scroll_shortlist.setEnabled(true);
                img_scroll_shortlisted.setEnabled(true);
                img_scroll_invite.setEnabled(true);
                img_invited.setEnabled(true);
                img_scroll_chat.setEnabled(true);

                blocimageview.setVisibility(View.VISIBLE);
                block_overlay.setVisibility(View.GONE);
            }

            //
        } else {
            blocimageview.setVisibility(View.GONE);
            block_overlay.setVisibility(View.GONE);
        }
      /*  Log.d(TAG, "showProfileVisitedData: "+TAG);

        if(TAG.equalsIgnoreCase("ParentProfileVisit")){
            blocimageview.setVisibility(View.VISIBLE);
        }else {
            blocimageview.setVisibility(View.GONE);
        }*/
        //  offlineLayout = itemView.findViewById(R.id.layout_scroll_offline);

        Log.d(TAG, "showProfileVisitedData: " + model.getAccountCreatedBy());


        if (TextUtils.isEmpty(model.getShorted()) || model.getShorted().contains("0")) {

            img_scroll_shortlist.setVisibility(View.VISIBLE);
            img_scroll_shortlisted.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.VISIBLE);
            tv_shortlisted.setVisibility(View.GONE);

        } else {

            img_scroll_shortlisted.setVisibility(View.VISIBLE);
            img_scroll_shortlist.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlisted.setVisibility(View.VISIBLE);

        }

        img_accept.setVisibility(View.GONE);
        tv_accept.setVisibility(View.GONE);
        img_scroll_invite.setVisibility(View.GONE);
        tv_invite.setVisibility(View.GONE);

        if (model.getInvited().equals(String.valueOf(model.getMemberId()))) {

            img_scroll_invite.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);
            img_invited.setVisibility(View.GONE);
            tv_invited.setVisibility(View.GONE);
            img_accept.setVisibility(View.VISIBLE);
            tv_accept.setVisibility(View.VISIBLE);

        } else {

            if (TextUtils.isEmpty(model.getInvited()) || model.getInvited().contains("0")) {

                img_scroll_invite.setVisibility(View.VISIBLE);
                img_invited.setVisibility(View.GONE);
                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);

            } else {

                img_invited.setVisibility(View.VISIBLE);
                img_scroll_invite.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.VISIBLE);
            }
        }

        if (model.getMainProfilePhoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        } else {

            Picasso.get()
                    .load(model.getMainProfilePhoto())
                    .placeholder(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        }

        tv_scroll_memberId.setText(model.getUniqueId());

        //String age_height = model.getUserAge() + "," + model.getUserHeight();

        if (model.getAccountCreatedBy().equals("P")) {

            tv_scroll_profile_otherDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_otherDetails.setText("Profile Created by Samaj Bandhav");
            tv_scroll_profile_saleryDetails.setVisibility(View.GONE);
            tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);
            tv_scroll_profile_languageDetails.setVisibility(View.GONE);
            tv_scroll_profile_marriedStatus1.setVisibility(View.GONE);

        } else if (model.getAccountCreatedBy().equals("C")) {
            try {
                model.setAge("" + model.getDOB());
            } catch (ArrayIndexOutOfBoundsException e) {

                e.printStackTrace();
            }

            String age = "" + model.getAge();
            String height = "" + model.getMemberHeight();
            String age_height;

            if (age.equals("") || age.equalsIgnoreCase("")) {

                age_height = "" + height;
            } else {

                age_height = age + " , " + height;
            }

            if (age_height.equals("") || age_height.equalsIgnoreCase("")) {

                tv_scroll_profile_otherDetails.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_otherDetails.setVisibility(View.VISIBLE);
                tv_scroll_profile_otherDetails.setText(age_height);
            }

            if (model.getMotherTongue().equals("") || model.getMotherTongue().equalsIgnoreCase("")) {

                tv_scroll_profile_languageDetails.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_languageDetails.setVisibility(View.VISIBLE);
                tv_scroll_profile_languageDetails.setText(model.getMotherTongue());
            }

            if (model.getMarriedStatus().equals("") || model.getMarriedStatus().equalsIgnoreCase("")) {

                tv_scroll_profile_marriedStatus1.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_marriedStatus1.setVisibility(View.VISIBLE);
                tv_scroll_profile_marriedStatus1.setText(model.getMarriedStatus());
            }

            if (model.getMemberInCome().equals("") || model.getMemberInCome().equalsIgnoreCase("")) {

                tv_scroll_profile_saleryDetails.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_saleryDetails.setVisibility(View.VISIBLE);
                tv_scroll_profile_saleryDetails.setText(model.getMemberInCome());
            }

            if (model.getEducationInName().equals("") || model.getEducationInName().equalsIgnoreCase("")) {

                tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_ProfessionDetails.setVisibility(View.VISIBLE);
                tv_scroll_profile_ProfessionDetails.setText(model.getEducationInName());
            }
        }

        String city = "" + model.getMemberCity();
        String country = "" + model.getMemberCountry();
        String city_country;

        if (city.equals("") || city.equalsIgnoreCase("")) {

            city_country = "" + country;
        } else {

            city_country = city + "," + country;
        }

        if (city_country.equals("") || city_country.equalsIgnoreCase("")) {

            tv_scroll_profile_AddressDetails.setVisibility(View.GONE);
        } else {

            tv_scroll_profile_AddressDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_AddressDetails.setText(city_country);
        }

        String onlineTime = model.getOnlineTime();
        String offlineTime = model.getOffline_Time();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

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

                model.setOnline(false);

            } else {

                model.setOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (model.getOnlinestatus().equalsIgnoreCase("online")) {

            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);
            // offlineLayout.setVisibility(View.GONE);
        } else {

            // offlineLayout.setVisibility(View.VISIBLE);
            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline");
            tv_online.setVisibility(View.GONE);
        }

        blocimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(mContext, "" + model.getMemberId(), Toast.LENGTH_SHORT).show();
                blockDialog();

                img_scroll_profileImage.setEnabled(false);
                img_scroll_shortlist.setEnabled(false);
                img_scroll_shortlisted.setEnabled(false);
                img_scroll_invite.setEnabled(false);
                img_invited.setEnabled(false);
                img_scroll_chat.setEnabled(false);
            }

            private void blockDialog() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
                alertDialog.setTitle(R.string.app_name);
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setMessage("Do you want to block " + model.getFirstName() + "?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isBlocked = true;
                        model.setBlocked("1");
                        blockPresenter.updateBlockStatus(memberId, Integer.parseInt(model.getMemberId()), 1);
                        block_overlay.setVisibility(View.VISIBLE);
                        blocimageview.setVisibility(View.GONE);

                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlocked = false;

                model.setBlocked("0");
                block_overlay.setVisibility(View.GONE);
                blockPresenter.updateBlockStatus(memberId, Integer.parseInt(model.getMemberId()), 0);
                blocimageview.setVisibility(View.VISIBLE);

                img_scroll_profileImage.setEnabled(true);
                img_scroll_shortlist.setEnabled(true);
                img_scroll_shortlisted.setEnabled(true);
                img_scroll_invite.setEnabled(true);
                img_invited.setEnabled(true);
                img_scroll_chat.setEnabled(true);
            }
        });

        img_scroll_profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }

                Intent intent = new Intent(mContext, ProfileVisitorViewActivity.class);
                intent.putExtra("PVisitedPosition", position);
                intent.putExtra("Role", "P");
                ProfileVisitorViewActivity.memberName = model.getFirstName() + " " + model.getLastName();
                mContext.startActivity(intent);
            }
        });

        img_scroll_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    PaymentActivity.startPaymentActivity(getActivity(), PaymentActivity.REQUEST_CODE, memberId, packageId);
                    return;
                }

                int senderId = Utilities.getInt(model.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, senderId, "1");

                img_scroll_shortlisted.setVisibility(View.VISIBLE);
                img_scroll_shortlist.setVisibility(View.GONE);

                tv_shortlist.setVisibility(View.GONE);
                tv_shortlisted.setVisibility(View.VISIBLE);
            }
        });

        img_scroll_shortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    PaymentActivity.startPaymentActivity(getActivity(), PaymentActivity.REQUEST_CODE, memberId, packageId);
                    return;
                }

                int senderId = Utilities.getInt(model.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, senderId, "0");

                img_scroll_shortlist.setVisibility(View.VISIBLE);
                img_scroll_shortlisted.setVisibility(View.GONE);

                tv_shortlist.setVisibility(View.VISIBLE);
                tv_shortlisted.setVisibility(View.GONE);
            }
        });

        img_scroll_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    PaymentActivity.startPaymentActivity(getActivity(), PaymentActivity.REQUEST_CODE, memberId, packageId);
                    return;
                }

                int senderId = Utilities.getInt(model.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");


                String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                String message = "Invitation Received from " + name;

                MatrimonyUtils.sendInvitationNotification(model.getDeviceId(), message, imageUrl,
                        model.getMemberId(), String.valueOf(memberId), name);

                img_invited.setVisibility(View.VISIBLE);
                img_scroll_invite.setVisibility(View.GONE);

                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.VISIBLE);
            }
        });

        img_invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    PaymentActivity.startPaymentActivity(getActivity(), PaymentActivity.REQUEST_CODE, memberId, packageId);
                    return;
                }

                int senderId = Utilities.getInt(model.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                img_scroll_invite.setVisibility(View.VISIBLE);
                img_invited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        /*img_scroll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recentlyProfileVisitorModelList.get(position).getInviteChatFlag().equals("1")) {

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = recentlyProfileVisitorModelList.get(position).getFirstName() + " " + recentlyProfileVisitorModelList.get(position).getLastName();
                    String imageUrl = recentlyProfileVisitorModelList.get(position).getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", recentlyProfileVisitorModelList.get(position).getUserMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", recentlyProfileVisitorModelList.get(position).getTokenId()).apply();

                } else {

                    Toast.makeText(mContext, "Please accept the invitation", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        img_scroll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }

                if(mUserType==1) {


                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = model.getFirstName() + " " + model.getLastName();
                    String imageUrl = model.getMainProfilePhoto();

                    SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                            Utilities.getInt(model.getMemberId()));

                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                            name);

                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                            imageUrl);

                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                            model.getDeviceId());
                }else {
                    final Dialog dialogChoice = new Dialog(getActivity());
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
                            Intent intent=new Intent(getActivity(), DonationActivity.class);
                            startActivity(intent);

                        }
                    });
                }


            }
        });


    }

    @Override
    public void showUsers(List<UserProfileModel> userDetailsModels) {
        profileVisitorModelList.clear();

        int j = 0;
       /* while (j < arrlist.size()) {
            Log.d("mytag", "onClick: " + arrlist.get(j).getChatId());
            chatPresenter.deleteChat(Integer.parseInt(arrlist.get(j).getChatId()));
            chatList.remove(arrlist.get(j));
            mChatRecyclerAdapter.notifyDataSetChanged();
            j++;
        }*/

       /* for(int i=0;i<=userDetailsModels.size();i++){
            if(userDetailsModels.get(i).getBlocked().equalsIgnoreCase("0")){
                profileVisitorModelList.add(userDetailsModels.get(i));
            }
        }*/

       /* for (UserProfileModel userProfileModel : userDetailsModels) {

        }*/

       while (j<userDetailsModels.size()){
           if(userDetailsModels.get(j).getBlocked().equalsIgnoreCase("0")||userDetailsModels.get(j).getBlocked().equalsIgnoreCase("")){

                   profileVisitorModelList.add(userDetailsModels.get(j));


           }
           j++;
       }
        profileVisitedAdapter = new CustomListAdapter(mContext, mRecyclerView,
                profileVisitorModelList, R.layout.recyclerview_row, new CustomListAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                showProfileVisitedData(view, (UserProfileModel) object, position);
            }

            @Override
            public void onItemClicked(View view, Object object, int position) {


            }
        });
        mRecyclerView.setAdapter(profileVisitedAdapter);
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
    public void showBlockedStatus(String message) {

    }
}