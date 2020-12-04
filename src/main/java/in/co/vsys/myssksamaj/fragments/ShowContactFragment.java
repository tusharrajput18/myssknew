package in.co.vsys.myssksamaj.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.DonationActivity;
import in.co.vsys.myssksamaj.activities.NewsListActivity;
import in.co.vsys.myssksamaj.activities.PaymentInfoActivity;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.InitiatePaymentListener;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.model.MatchesModel;
import in.co.vsys.myssksamaj.presenters.ViewContactPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import retrofit2.Call;
import retrofit2.Callback;

public class ShowContactFragment extends DialogFragment {

    private Context mContext;
    private String emailId = "", contactNo = "";
    private TextView goPremium;
    private InitiatePaymentListener initiatePaymentListener;
    int usertype;
    int myMemberId;


    LinearLayout  linearcontact;
    TextView tv_mobile_number,tv_emailid;


    private SharedPreferences mPreferences;;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setInitiatePaymentListener(InitiatePaymentListener initiatePaymentListener) {
        this.initiatePaymentListener = initiatePaymentListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        if (mContext == null)
            mContext = getActivity();

        Dialog dialog = new Dialog(mContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        width -= (width / 8);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_show_contact, null);
        dialog.setContentView(view, layoutParams);

        usertype=mPreferences.getInt("userType",-1);
        Log.d("mytag", "onCreateDialog: "+usertype);

      myMemberId = SharedPrefsHelper.getInstance(mContext).getIntVal("memberId");

       /* if(usertype==1){
            String number=getArguments()
        }*/

        /**
         * TODO: currently showing as Coming soon. When it has to be shown again, remove textview from xml
         *         and uncomment below line
         */
        initUI(view);


        return dialog;
    }

    private void initUI(final View view) {
        final LinearLayout contactContainer = view.findViewById(R.id.contact_container);

        goPremium = view.findViewById(R.id.go_premium);

        linearcontact=view.findViewById(R.id.linearcontact);
        tv_mobile_number=view.findViewById(R.id.tv_mobile_number);
        tv_emailid=view.findViewById(R.id.tv_emailid);


        if (getArguments().containsKey(Constants.MEMBER_ID)) {

            String othersMemberId = getArguments().getString(Constants.MEMBER_ID);

            if(usertype==1){
                getProfileDetails(othersMemberId);
                insertProfileview(myMemberId,othersMemberId);
                linearcontact.setVisibility(View.VISIBLE);
                goPremium.setVisibility(View.GONE);

            }else {
                linearcontact.setVisibility(View.GONE);
                goPremium.setVisibility(View.VISIBLE);

            }


         /*   if (getArguments().containsKey(Constants.EMAIL)) {
                emailId = getArguments().getString(Constants.EMAIL);
            }

            if (getArguments().containsKey(Constants.CONTACT_NO)) {
                contactNo = getArguments().getString(Constants.CONTACT_NO);
            }*/





            Log.d("mytag", "initUI: "+othersMemberId+" "+myMemberId);

            /*new ViewContactPresenter("" + myMemberId,""+ othersMemberId, new ViewContactPresenter.RestView() {
                @Override
                public Context getContext() {
                    return mContext;
                }

                @Override
                public void showMessage(String message) {
                    if (!message.trim().equalsIgnoreCase("You Are Free To Contact This Member"))
                        return;

                    if(Utilities.isEmpty(emailId) && Utilities.isEmpty(contactNo)){
                        return;
                    }
                    goPremium.setVisibility(View.GONE);
*//*
                    if (!Utilities.isEmpty(emailId))
                        contactContainer.addView(addData("Email ID", emailId, null));
                    if (!Utilities.isEmpty(contactNo))
                        contactContainer.addView(addData("Mobile No", contactNo, null));*//*
                }

                @Override
                public void showLoading() {

                }

                @Override
                public void hideLoading() {

                }

                @Override
                public void showError(String message) {
                    view.findViewById(R.id.go_premium).setVisibility(View.VISIBLE);
                }
            });*/


            goPremium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dismiss();
                   // Intent intent=new Intent(getActivity(), NewsListActivity.class);
                    //Intent intent=new Intent(getActivity(), PaymentInfoActivity.class);
                    Intent intent=new Intent(getActivity(), DonationActivity.class);
                    startActivity(intent);

                }
            });
        }
    }


    private void getProfileDetails(final String othermemberid) {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait ......");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.allInfoUsingMemeberIdUrl,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonArray;
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                int success = jsonObject.getInt("success");

                                if (success == 1) {
                                    jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        String number = jsonObject1.getString("Mobile");
                                         String email= jsonObject1.getString("EmailId");
                                        tv_mobile_number.setText(""+number);
                                        tv_emailid.setText(""+email);
                                        Log.d("mytag", "onResponse: "+myMemberId+ " "+othermemberid);

                                        Log.d("mytag", "onResponse: "+number+" "+email);

                                    }
                                    progressDialog.dismiss();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            //   Log.d(TAG, "recently error " + error);
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                   params.put("MemberId", othermemberid);

                    return params;


                }
            };

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



    }

    private void insertProfileview(final int myMemberId, final String othermemberid) {
        Log.d("mytag", "insertProfileview: "+myMemberId +" "+othermemberid);
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait ......");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.contactViewed,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = (Integer)jsonObject.getInt("status");
                            int message = (Integer)jsonObject.getInt("message");
                            int data = (Integer)jsonObject.getInt("data");
                            Log.d("mytag", "onResponse: "+success+"===="+message+"====="+data);

                            if (success == 1) {
                                //Toast.makeText(mContext, "Your Remaing Contact is : "+data, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                       Log.d("mytag", "recently error " + error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromId", String.valueOf(myMemberId));
                params.put("toId", othermemberid);

                return params;


            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



    }

    private View addData(String title, String text, ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
        TextView titleTxt = view.findViewById(R.id.title);
        TextView value = view.findViewById(R.id.value);

        Utilities.setText(titleTxt, title);
        Utilities.setText(value, text, viewClickListener);
        return view;
    }
}