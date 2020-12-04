package in.co.vsys.myssksamaj.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easebuzz.payment.kit.PWECouponsActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.RecentlyProfileVisitorModel;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodels.StaticDataModel;

public class ProfileVisitedListActivity extends AppCompatActivity implements ListClickListener,
        TransactionDataContract.TransactionView {

    private Context mContext;
    private static final String MEMBER_ID = "MemberId";
    private CustomListAdapter profileVisitedAdapter;
    private RecyclerView mRecyclerView;
    private int memberId;
    private List<RecentlyProfileVisitorModel> profileVisitorModelList;
    private static final String TAG = ProfileVisitedListActivity.class.getSimpleName();
    private ProgressBar mProgressBar;

    private AlertDialog alertDialog;
    private Dialog dialog;
    private String mTransactionId = "", mPackageId = "1";
    private TransactionDataContract.TransactionOps transactionDataPresenter;
    private int mMemberId;
    private SharedPreferences mPreferences;

    private int profileVisitorsPastVisibleItems, profileVisitorsVisibleItemCount, profileVisitorsTotalItemCount,
            profileVisitorsPage = 0;
    private boolean profileVisitorsLoading = false;
    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_visited_list);
        mContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.profileVisitedList);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_visitedList);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_profileVisited);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Profile Visited List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        profileVisitorModelList = new ArrayList<>();

        SharedPreferences mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);
        memberId = mPreference.getInt("memberId", 0);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    profileVisitorsVisibleItemCount = mRecyclerView.getLayoutManager().getChildCount();
                    profileVisitorsTotalItemCount = mRecyclerView.getLayoutManager().getItemCount();
                    profileVisitorsPastVisibleItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!profileVisitorsLoading) {
                        if ((profileVisitorsVisibleItemCount + profileVisitorsPastVisibleItems) >= profileVisitorsTotalItemCount) {
                            profileVisitorsLoading = true;
                            profileVisitorsPage++;
                            loadProfileVisitorData( memberId,  + profileVisitorsPage);
                        }
                    }
                }
            }
        });

        loadProfileVisitorData( memberId, profileVisitorsPage);


        transactionDataPresenter = new TransactionPresenter(this);
    }

    private void loadProfileVisitorData(final int mID, final int pageCount) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest recentlYProfileVisitors = new StringRequest(Request.Method.POST, Config.recentlyProfileViewUrl_paging,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("recentlyVisitors", response);
                        mProgressBar.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray;
                            JSONObject rJsonObject = new JSONObject(response);

                            int success = rJsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = rJsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    RecentlyProfileVisitorModel model = new RecentlyProfileVisitorModel();

                                    int memberId = jsonObject1.getInt("MemberId");
                                    String uniqueId = jsonObject1.getString("UniqueId");
                                    String userAge = jsonObject1.getString("DOB");
                                    String userHeight = jsonObject1.getString("MemberHeight");
                                    String motherTongue = jsonObject1.getString("MotherTongue");
                                    String userProfileUrl = jsonObject1.getString("MainProfilePhoto");
                                    String userCity = jsonObject1.getString("MemberCity");
                                    String userCountry = jsonObject1.getString("MemberCountry");
                                    String userEductionIN = jsonObject1.getString("EducationInName");
                                    String userIncome = jsonObject1.getString("MemberInCome");
                                    String marriedStatus = jsonObject1.getString("MarriedStatus");
                                    String shortlist = jsonObject1.getString("Shorted");
                                    String invite = jsonObject1.getString("Invited");
                                    String firstName = jsonObject1.getString("FirstName");
                                    String lastName = jsonObject1.getString("LastName");
                                    String chatInviteFlag = jsonObject1.getString("IsChat");
                                    String inviteReceivedFlag = jsonObject1.getString("ReceiverFlag");
                                    String onlinestatus = jsonObject1.getString("onlinestatus");

                                    String SenderBlocked = jsonObject1.getString("SenderBlocked");
                                    String ispremium = jsonObject1.getString("ispremium");

                                    model.setInviteReceivedFlag(inviteReceivedFlag);
                                    model.setInviteChatFlag(chatInviteFlag);
                                    model.setFirstName(firstName);
                                    model.setLastName(lastName);
                                    model.setUserMemberId(memberId);
                                    model.setUniqueId(uniqueId);
                                    model.setDOB(userAge);
                                    model.setUserHeight(userHeight);
                                    model.setMotherTongue(motherTongue);
                                    model.setUserProfileUrl(userProfileUrl);
                                    model.setUserCity(userCity);
                                    model.setUserCountry(userCountry);
                                    model.setEductionIn(userEductionIN);
                                    model.setUserIncome(userIncome);
                                    model.setMarriedStatus(marriedStatus);
                                    model.setShortlistedFlag(shortlist);
                                    model.setInvitedFlag(invite);
                                    model.setOnlinestatus(onlinestatus);
                                    model.setOnlieTime(jsonObject1.getString("Online Time"));
                                    model.setOfflineTime(jsonObject1.getString("Offline Time"));
                                    model.setProfileCreatedBy("" + jsonObject1.getString("AccountCreatedBy"));
                                    model.setTokenId("" + jsonObject1.getString("DeviceId"));
                                    model.setIspremium(ispremium);
                                    model.setSenderBlocked(SenderBlocked);

                                    if(model.getSenderBlocked().equalsIgnoreCase("0")){
                                        profileVisitorModelList.add(model);
                                    }



                                    Log.d(TAG, "showRecentlyProfileViewed: "+profileVisitorModelList.size());
                                }
                                profileVisitorsLoading = false;
                                if (profileVisitedAdapter == null) {
                                profileVisitedAdapter = new CustomListAdapter(mContext, mRecyclerView, profileVisitorModelList,
                                        R.layout.recyclerview_row, new CustomListAdapter.ItemClickedListener() {
                                    @Override
                                    public void onViewBound(View itemView, Object object, int position) {
                                        showProfileVisitedListItemData(itemView, (RecentlyProfileVisitorModel) object,
                                                position);
                                    }

                                    @Override
                                    public void onItemClicked(View view, Object object, int position) {

                                    }
                                });
                                mRecyclerView.setAdapter(profileVisitedAdapter);
                                } else
                                    profileVisitedAdapter.notifyDataSetChanged();
                            } else {

                                Toast.makeText(ProfileVisitedListActivity.this, "list not available", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("recentlyVisitors error ", error.toString());
                        mProgressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(MEMBER_ID, String.valueOf(mID));
                params.put("page", String.valueOf(pageCount));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(recentlYProfileVisitors);
    }

    private void showProfileVisitedListItemData(View itemView, final RecentlyProfileVisitorModel model,
                                                final int position) {

        ImageView img_scroll_profileImage, img_accept;
        final TextView tv_scroll_memberId, tv_scroll_profile_otherDetails, tv_scroll_profile_languageDetails,
                tv_scroll_profile_ProfessionDetails, tv_scroll_profile_saleryDetails,
                tv_scroll_profile_marriedStatus1, tv_scroll_profile_AddressDetails, tv_invite,
                tv_invited, tv_shortlist, tv_shortlisted;
        final ImageView img_scroll_invite, img_invited, img_scroll_shortlist, img_scroll_shortlisted, img_scroll_chat;
        TextView tv_online, tv_offline, tv_accept;

        ImageView img_premium;

        img_premium=(ImageView)itemView.findViewById(R.id.img_premium);

        img_scroll_profileImage = itemView.findViewById(R.id.img_scroll_profileImage);
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

        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        memberId = mPreferences.getInt("memberId", 0);

        if(model.getIspremium().equalsIgnoreCase("0")){

            img_premium.setVisibility(View.GONE);

        }else {
            img_premium.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(model.getShortlistedFlag()) || model.getShortlistedFlag().contains("0")) {

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

        if (model.getInviteReceivedFlag().equals(String.valueOf(model.getUserMemberId()))) {

            img_scroll_invite.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);
            img_invited.setVisibility(View.GONE);
            tv_invited.setVisibility(View.GONE);
            img_accept.setVisibility(View.VISIBLE);
            tv_accept.setVisibility(View.VISIBLE);

        } else {
            if (TextUtils.isEmpty(model.getInvitedFlag()) || model.getInvitedFlag().contains("0")) {
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

        if (model.getUserProfileUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        } else {
            Picasso.get()
                    .load(model.getUserProfileUrl()).placeholder(R.drawable.img_preview).into(img_scroll_profileImage);
        }

        tv_scroll_memberId.setText(model.getUniqueId());

        if (model.getProfileCreatedBy().equals("P")) {

            tv_scroll_profile_otherDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_otherDetails.setText("Profile managed by Parent");
            tv_scroll_profile_saleryDetails.setVisibility(View.GONE);
            tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);
            tv_scroll_profile_languageDetails.setVisibility(View.GONE);
            tv_scroll_profile_marriedStatus1.setVisibility(View.GONE);

        } else if (model.getProfileCreatedBy().equals("C")) {

            try {

                int year = Calendar.getInstance().get(Calendar.YEAR);
                String dob = model.getDOB();
                String[] dob1 = dob.split("/");
                String dateOfBirth = dob1[2];
                int age = (year - Integer.parseInt(dateOfBirth));
                model.setUserAge("" + age);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String age = "" + model.getUserAge();
            String height = "" + model.getUserHeight();
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

            if (model.getUserIncome().equals("") || model.getUserIncome().equalsIgnoreCase("")) {

                tv_scroll_profile_saleryDetails.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_saleryDetails.setVisibility(View.VISIBLE);
                tv_scroll_profile_saleryDetails.setText(model.getUserIncome());
            }

            if (model.getEductionIn().equals("") || model.getEductionIn().equalsIgnoreCase("")) {

                tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);

            } else {

                tv_scroll_profile_ProfessionDetails.setVisibility(View.VISIBLE);
                tv_scroll_profile_ProfessionDetails.setText(model.getEductionIn());
            }
        }

        String city = "" + model.getUserCity();
        String country = "" + model.getUserCountry();
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

        img_scroll_profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    onListItemClicked(model);
                    return;
                }


                int actualPositionInList=getActualPosition(model.getUserMemberId());
                Intent intent = new Intent(mContext, ProfileVisitedViewActivity.class);

                String name=model.getFirstName()+" "+model.getLastName();
                intent.putExtra("name",name);
                intent.putExtra("page", getPage(actualPositionInList));
                intent.putExtra("PVisitedPosition", getPosition(actualPositionInList));
                intent.putExtra("PVisitedFlag", 2);
                intent.putExtra("memberid",model.getUserMemberId());
                intent.putExtra("memberName", model.getFirstName() + " " + model.getLastName());

                mContext.startActivity(intent);
            }
        });

        img_scroll_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    onListItemClicked(model);
                    return;
                }

                int senderId = profileVisitorModelList.get(position).getUserMemberId();
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
                    onListItemClicked(model);
                    return;
                }

                int senderId = profileVisitorModelList.get(position).getUserMemberId();
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
                    onListItemClicked(model);
                    return;
                }

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    onListItemClicked(model);
                    return;
                }

                int senderId = profileVisitorModelList.get(position).getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                String name = mPreferences.getString("nav_memberName", "");
                String imageUrl = mPreferences.getString("nav_profileUrl", "");

                String message = "Invitation Received from " + name;

                MatrimonyUtils.sendInvitationNotification(model.getTokenId(), message, imageUrl,
                        String.valueOf(model.getUserMemberId()), String.valueOf(memberId), name);


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
                    onListItemClicked(model);
                    return;
                }

                int senderId = profileVisitorModelList.get(position).getUserMemberId();
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

                if(mUserType==1) {
                    if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                        onListItemClicked(model);
                        return;
                    }

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = profileVisitorModelList.get(position).getFirstName() + " " + profileVisitorModelList.get(position).getLastName();
                    String imageUrl = profileVisitorModelList.get(position).getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", profileVisitorModelList.get(position).getUserMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", profileVisitorModelList.get(position).getTokenId()).apply();
                }else {
                    final Dialog dialogChoice = new Dialog(ProfileVisitedListActivity.this);
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
                            Intent intent=new Intent(ProfileVisitedListActivity.this,DonationActivity.class);
                            startActivity(intent);

                        }
                    });
                }

            }


        });

        String onlineTime = model.getOnlieTime();
        String offlineTime = model.getOfflineTime();

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
        } else {

            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline");
            tv_online.setVisibility(View.GONE);
        }
    }

    private int getPosition(int actualPositionInList) {
        if(profileVisitorsPage>0){
            return mod(actualPositionInList,10);
        }else {
            return actualPositionInList;
        }

    }

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }

    private int getActualPosition(int userMemberId) {
        int pos=0;
        for (int i=0;i<profileVisitorModelList.size();i++){
            if(userMemberId==profileVisitorModelList.get(i).getUserMemberId()){
                pos=i;
                break;
            }
        }
        return pos;
    }

    private int getPage(int actualPositionInList) {
        if(profileVisitorsPage>0){
            return actualPositionInList/10;
        }else {
           return 0;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(ProfileVisitedListActivity.this, HomeActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        startActivity(new Intent(ProfileVisitedListActivity.this, HomeActivity.class));
//    }

    @Override
    public void onListItemClicked(Object object) {
        android.app.AlertDialog.Builder db = new android.app.AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
//        db.setView(dialog_layout);
        db.setTitle("Update subscription");
        db.setMessage(PaymentHelper.GET_PREMIUM);
        db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                transactionDataPresenter.getTransactionData("" + mMemberId, mPackageId);
            }
        });

        db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = db.show();
    }


    @Override
    public void showLoading() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog == null) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    //View view = getLayoutInflater().inflate(R.layout.progress);
                    builder.setView(R.layout.progress);
                    dialog = builder.create();
                }
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * NOTE: Payment SDK implementation and methods from here onwards:
     */

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


    // When payment is done through easebuzz then invoke this method.

    // This will set SharedPreferences and update the drawer view with appropriate "Paid/Premium member"
    private void successfulTransactionCompletion() {
        try {
            mPreferences.edit().putInt("userType", 1).apply();
            MainPresenter.getInstance().setUserType(mContext,1);
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionFailure() {
        try {
            mPreferences.edit().putInt("userType", 0).apply();
            MainPresenter.getInstance().setUserType(mContext,0);
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTransactionUpdate(String message) {
//        mDrawerLayout.closeDrawer(GravityCompat.START);

//        displayNavText(navigationView);
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
//        result = EasebuzzConstants.PAYMENT_SUCCESSFUL;

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

        transactionDataPresenter.updateTransactionData("" + mMemberId, "" + mPackageId,
                mTransactionId, status);
    }
}


