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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.MatchesModel;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class MatriMatchesActivity extends AppCompatActivity implements ListClickListener, TransactionDataContract.TransactionView {

    private RecyclerView mMatchesRecyclerView;
    private ProgressBar mProgressBar;
    private List<MatchesModel> matchesModelList;
    private SharedPreferences mPreferences;
    private int memberId;
    private ImageView btnBack;
    private static final String TAG = ShortlistedActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";

    private Context mContext;
    private AlertDialog alertDialog;
    private Dialog dialog;
    private int mMemberId;
    private String mTransactionId = "", mPackageId = "1";
    private TransactionDataContract.TransactionOps transactionDataPresenter;

    private int profileMatchesPastVisibleItems, profileMatchesVisibleItemCount, profileMatchesTotalItemCount,
            profileMatchesPage = 0;
    private boolean profileMatchesLoading = false;
    private CustomListAdapter matchesAdapter;

    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_matri_matches);
            mContext = this;

            mMatchesRecyclerView = (RecyclerView) findViewById(R.id.matches_recyclerView);
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar_matches);

            Toolbar mToolbar = (Toolbar) findViewById(R.id.matchesList_toolbar);

            setSupportActionBar(mToolbar);

            if (getSupportActionBar() != null) {

                getSupportActionBar().setTitle("Matches List");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

            }

            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            memberId = mPreferences.getInt("memberId", 0);
            mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

            matchesModelList = new ArrayList<>();

            mMatchesRecyclerView.setHasFixedSize(true);
            mMatchesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


            mMatchesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    if (dy > 0) {
                        profileMatchesVisibleItemCount = mMatchesRecyclerView.getLayoutManager().getChildCount();
                        profileMatchesTotalItemCount = mMatchesRecyclerView.getLayoutManager().getItemCount();
                        profileMatchesPastVisibleItems = ((LinearLayoutManager) mMatchesRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                        if (!profileMatchesLoading) {
                            if ((profileMatchesVisibleItemCount + profileMatchesPastVisibleItems) >= profileMatchesTotalItemCount) {
                                profileMatchesLoading = true;
                                profileMatchesPage++;
                                loadRecentlyMatchesData("" + memberId, "" + profileMatchesPage);
                            }
                        }
                    }
                }
            });

            loadRecentlyMatchesData("" + memberId, "" + profileMatchesPage);

            mMemberId = MainPresenter.getInstance().getMemberId(mContext);
            transactionDataPresenter = new TransactionPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(MatriMatchesActivity.this, HomeActivity.class));
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
//        startActivity(new Intent(MatriMatchesActivity.this, HomeActivity.class));
//    }

    private void loadRecentlyMatchesData(final String mID, final String pageCount) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest recentlyJoint = new StringRequest(Request.Method.POST, Config.member_matches_url_paging,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d("matches response", response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MatchesModel matchesModel = new MatchesModel();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    matchesModel.setFirstName(object.getString("FirstName"));
                                    matchesModel.setLastName(object.getString("LastName"));
                                    matchesModel.setUserHeight(object.getString("height"));
                                    matchesModel.setUserMemberId(object.getInt("M_ID"));
                                    matchesModel.setUniqueId("" + object.getString("UniqueId"));
                                    matchesModel.setDateOfBirth("" + object.getString("DOB"));
                                    matchesModel.setUserCountry(object.getString("Country"));
                                    matchesModel.setUserCity(object.getString("City"));
                                    matchesModel.setAnnualIncome(object.getString("Income"));
                                    matchesModel.setMotherTongue(object.getString("MotherTongue"));
                                    matchesModel.setEduction(object.getString("Education"));
                                    matchesModel.setMarriedStatus(object.getString("Married Status"));
                                    matchesModel.setUserProfileUrl("" + object.getString("MainProfilePhoto"));
                                    matchesModel.setOnlinestatus("" + object.getString("onlinestatus"));
                                    matchesModel.setIspremium("" + object.getString("ispremium"));
                                    matchesModel.setSenderBlocked("" + object.getString("SenderBlocked"));

                                    matchesModel.setMatchminAge(object.getInt("MIN_Age"));
                                    matchesModel.setMatchMaxAge(object.getInt("MAX_Age"));
                                    matchesModel.setMatchminHeight(object.getInt("MIN_Height"));
                                    matchesModel.setMatchMaxHeight(object.getInt("MAX_Height"));
                                    matchesModel.setMatchMStatus(object.getInt("Married_Status"));
                                    matchesModel.setMatchFoodType(object.getInt("Food_Type"));
                                    matchesModel.setMatchDrinkHabit(object.getInt("Drink_Habit"));
                                    matchesModel.setMatchSmokeHabit(object.getInt("Smoke_Habit"));
                                    matchesModel.setShortlistedFlag("" + object.getString("Shorted"));
                                    matchesModel.setInvitedFlag("" + object.getString("Invited"));
                                    matchesModel.setOnlieTime(object.getString("Online Time"));
                                    matchesModel.setOfflineTime(object.getString("Offline Time"));
                                    matchesModel.setTokenId(object.getString("DeviceId"));
                                    matchesModel.setInviteReceivedFlag("" + object.getString("ReceiverFlag"));
                                    //  matchesModel.setInviteChatFlag(object.getString("IsChat"));

                                    if(matchesModel.getSenderBlocked().equalsIgnoreCase("0")){
                                        matchesModelList.add(matchesModel);
                                    }



                                }

                                profileMatchesLoading = false;
                                if (matchesAdapter == null) {
                                    matchesAdapter = new CustomListAdapter(mContext, mMatchesRecyclerView,
                                            matchesModelList, R.layout.recyclerview_row, new CustomListAdapter.ItemClickedListener() {
                                        @Override
                                        public void onViewBound(View view, Object object, int position) {
                                            showMatchModels(view, (MatchesModel) object, position);
                                        }

                                        @Override
                                        public void onItemClicked(View view, Object object, int position) {
                                            MatchesModel matchesModel=(MatchesModel) object;

                                            int uniqueId = matchesModel.getUserMemberId();
                                            String name=matchesModel.getFirstName()+" "+matchesModel.getLastName();

                                            Intent intent = new Intent(mContext, UserDetailsActivity.class);
                                            intent.putExtra("uniqueId", uniqueId);
                                            intent.putExtra("flagMatches", 2);
                                            intent.putExtra("name",name);

                                            Log.d("Match adapter", "uniqueId: " + uniqueId);
                                            UserDetailsActivity.minHeight = matchesModel.getMatchminHeight();
                                            UserDetailsActivity.minAge = matchesModel.getMatchminAge();
                                            UserDetailsActivity.drinkType = matchesModel.getMatchDrinkHabit();
                                            UserDetailsActivity.foodType = matchesModel.getMatchFoodType();
                                            UserDetailsActivity.smokeType = matchesModel.getMatchSmokeHabit();

                                            mContext.startActivity(intent);

                                        }
                                    });
                                    mMatchesRecyclerView.setAdapter(matchesAdapter);
                                } else
                                    matchesAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(MatriMatchesActivity.this, "Details not found...!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("matches error ", error.toString());
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

        VolleySingleton.getInstance(this).addToRequestQueue(recentlyJoint);
    }

    private void showMatchModels(View itemView, final MatchesModel matchesModel, final int position) {

        ImageView img_scroll_profileImage, img_accept;
        final TextView tv_scroll_memberId, tv_scroll_profile_otherDetails, tv_scroll_profile_languageDetails,
                tv_scroll_profile_ProfessionDetails, tv_scroll_profile_saleryDetails, tv_scroll_profile_marriedStatus1,
                tv_scroll_profile_AddressDetails, tv_invite, tv_invited, tv_shortlist, tv_shortlisted;
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

        img_accept.setVisibility(View.GONE);
        tv_accept.setVisibility(View.GONE);
        img_scroll_invite.setVisibility(View.GONE);
        tv_invite.setVisibility(View.GONE);

        if (matchesModel.getInviteReceivedFlag().equals(String.valueOf(matchesModel.getUserMemberId()))) {

            img_scroll_invite.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);
            img_invited.setVisibility(View.GONE);
            tv_invited.setVisibility(View.GONE);
            img_accept.setVisibility(View.VISIBLE);
            tv_accept.setVisibility(View.VISIBLE);

        } else {

            if (TextUtils.isEmpty(matchesModel.getInvitedFlag()) || matchesModel.getInvitedFlag().contains("0")) {

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

        if(matchesModel.getIspremium().equalsIgnoreCase("0")){

            img_premium.setVisibility(View.GONE);

        }else {
            img_premium.setVisibility(View.VISIBLE);
        }

        matchesModel.setUserAge(MatrimonyUtils.getAge(matchesModel.getDateOfBirth()));

        if (matchesModel.getUserAge().equals("") || matchesModel.getUserAge().equalsIgnoreCase("")
                && matchesModel.getUserHeight().equals("") || matchesModel.getUserHeight().equalsIgnoreCase("")) {

            tv_scroll_profile_otherDetails.setVisibility(View.GONE);

        } else {

            tv_scroll_profile_otherDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_otherDetails.setText("" + matchesModel.getUserAge() + "," + matchesModel.getUserHeight());
        }


        tv_scroll_memberId.setText(matchesModel.getUniqueId());

        if (matchesModel.getMotherTongue().equals("") || matchesModel.getMotherTongue().equalsIgnoreCase("")) {

            tv_scroll_profile_languageDetails.setVisibility(View.GONE);

        } else {

            tv_scroll_profile_languageDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_languageDetails.setText(matchesModel.getMotherTongue());
        }

        if (matchesModel.getMarriedStatus().equals("") || matchesModel.getMarriedStatus().equalsIgnoreCase("")) {

            tv_scroll_profile_marriedStatus1.setVisibility(View.GONE);

        } else {

            tv_scroll_profile_marriedStatus1.setVisibility(View.VISIBLE);
            tv_scroll_profile_marriedStatus1.setText(matchesModel.getMarriedStatus());
        }


        String city = "" + matchesModel.getUserCity();
        String country = "" + matchesModel.getUserCountry();
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


        if (matchesModel.getAnnualIncome().equals("") || matchesModel.getAnnualIncome().equalsIgnoreCase("")) {

            tv_scroll_profile_saleryDetails.setVisibility(View.GONE);

        } else {

            tv_scroll_profile_saleryDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_saleryDetails.setText(matchesModel.getAnnualIncome());
        }

        if (matchesModel.getEduction().equals("") || matchesModel.getEduction().equalsIgnoreCase("")) {

            tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);

        } else {

            tv_scroll_profile_ProfessionDetails.setVisibility(View.VISIBLE);
            tv_scroll_profile_ProfessionDetails.setText(matchesModel.getEduction());
        }


        if (TextUtils.isEmpty(matchesModel.getShortlistedFlag()) || matchesModel.getShortlistedFlag().contains("0")) {

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


        if (matchesModel.getUserProfileUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        } else {

            Picasso.get()
                    .load(matchesModel.getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        }

//
//        img_scroll_profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////                    onListItemClicked(matchesModel);
////                    return;
////                }
//
//                //   mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//
//                // mPreferences.edit().putInt("position", position).apply();
//
//                int uniqueId = matchesModel.getUserMemberId();
//
//                Intent intent = new Intent(mContext, UserDetailsActivity.class);
//                intent.putExtra("uniqueId", uniqueId);
//                intent.putExtra("flagMatches", 2);
//
//                Log.d("Match adapter", "uniqueId: " + uniqueId);
//                UserDetailsActivity.minHeight = matchesModel.getMatchminHeight();
//                UserDetailsActivity.minAge = matchesModel.getMatchminAge();
//                UserDetailsActivity.drinkType = matchesModel.getMatchDrinkHabit();
//                UserDetailsActivity.foodType = matchesModel.getMatchFoodType();
//                UserDetailsActivity.smokeType = matchesModel.getMatchSmokeHabit();
//
//                mContext.startActivity(intent);
//            }
//        });

       /* img_scroll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (matchesModel.getInviteChatFlag().equals("1")) {

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = matchesModel.getFirstName() + " " + matchesModel.getLastName();
                    String imageUrl = matchesModel.getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", matchesModel.getUserMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", matchesModel.getTokenId()).apply();

                } else {

                    Toast.makeText(mContext, "Please accept the invitation", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        img_scroll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserType==1){
                    if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                        onListItemClicked(matchesModel);
                        return;
                    }

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = matchesModel.getFirstName() + " " + matchesModel.getLastName();
                    String imageUrl = matchesModel.getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", matchesModel.getUserMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", matchesModel.getTokenId()).apply();
                }else {
                    final Dialog dialogChoice = new Dialog(MatriMatchesActivity.this);
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
                            Intent intent=new Intent(MatriMatchesActivity.this,DonationActivity.class);
                            startActivity(intent);

                        }
                    });
                }

            }
        });

        img_scroll_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    onListItemClicked(matchesModel);
                    return;
                }

                int senderId = matchesModel.getUserMemberId();
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
                    onListItemClicked(matchesModel);
                    return;
                }

                int senderId = matchesModel.getUserMemberId();
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
                    onListItemClicked(matchesModel);
                    return;
                }

                int senderId = matchesModel.getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

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
                    onListItemClicked(matchesModel);
                    return;
                }

                int senderId = matchesModel.getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                img_scroll_invite.setVisibility(View.VISIBLE);
                img_invited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        String onlineTime = matchesModel.getOnlieTime();
        String offlineTime = matchesModel.getOfflineTime();

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

                matchesModel.setOnline(false);

            } else {

                matchesModel.setOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (matchesModel.getOnlinestatus().equalsIgnoreCase("online")) {

            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);
        } else {

            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline");
            tv_online.setVisibility(View.GONE);
        }
    }

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
            MainPresenter.getInstance().setUserType(mContext, 1);
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionFailure() {
        try {
            mPreferences.edit().putInt("userType", 0).apply();
            MainPresenter.getInstance().setUserType(mContext, 0);
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
