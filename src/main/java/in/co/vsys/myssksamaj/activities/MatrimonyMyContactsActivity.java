package in.co.vsys.myssksamaj.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.List;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.ContactAdapter;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.model.ContactsModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class MatrimonyMyContactsActivity extends AppCompatActivity {

    Toolbar toolbar_contacts;
    RecyclerView contacts_recyclerview;
    ProgressBar contact_progressbar;

    List<ContactsModel> contactsModelList;
    private SharedPreferences mPreferences;;
    int myMemberId;
    TextView tv_no_contacts, tv_total_remaning_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_my_contacts);

        toolbar_contacts = (Toolbar) findViewById(R.id.toolbar_contacts);

        setSupportActionBar(toolbar_contacts);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("My Contacts");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myMemberId = SharedPrefsHelper.getInstance(this).getIntVal("memberId");

        tv_no_contacts=(TextView)findViewById(R.id.tv_no_contacts);
        tv_total_remaning_contacts=(TextView)findViewById(R.id.tv_total_remaning_contacts);

        contacts_recyclerview = (RecyclerView) findViewById(R.id.contacts_recyclerview);
        contacts_recyclerview.setHasFixedSize(true);
        contacts_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        contact_progressbar = (ProgressBar) findViewById(R.id.contact_progressbar);


        getContacts(myMemberId);
        getRemaingProfilecount(myMemberId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                startActivity(new Intent(MatrimonyNotificationActivity.this, HomeActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getRemaingProfilecount(final int mMemberId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getContactCredits,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("mytag", "logout response====: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");
                            int data = jsonObject.getInt("data");

                            if (success == 1) {
                                tv_total_remaning_contacts.setText("Total Remaning Contacts : " + data);
                                Log.d("mytag", "onResponse: " + data);
                                //  Toast.makeText(mContext, "Log out time send", Toast.LENGTH_SHORT).show();
                            } else {

                                //  Toast.makeText(mContext, "Log out time not send...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("mytag", "logout error===========: ", error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put("userId", String.valueOf(mMemberId));

                return param;
            }
        };

        VolleySingleton.getInstance(MatrimonyMyContactsActivity.this).addToRequestQueue(stringRequest);
    }


    private void getContacts(final int othermemberid) {
        contactsModelList = new ArrayList<>();
        if (contactsModelList != null) {
            contactsModelList.clear();
        }
        contact_progressbar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getContactViewedDetails,
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
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    ContactsModel contactsModel = new ContactsModel();

                                    contactsModel.setMemberId(jsonObject1.getString("MemberId"));
                                    contactsModel.setFirstName(jsonObject1.getString("FirstName"));
                                    contactsModel.setMiddleName(jsonObject1.getString("MiddleName"));
                                    contactsModel.setLastName(jsonObject1.getString("LastName"));
                                    contactsModel.setMobile(jsonObject1.getString("mobile"));
                                    contactsModelList.add(contactsModel);
                                }

                                if(contactsModelList.size()>0){
                                    tv_no_contacts.setVisibility(View.GONE);
                                    contacts_recyclerview.setVisibility(View.VISIBLE);
                                    ContactAdapter contactAdapter=new ContactAdapter(MatrimonyMyContactsActivity.this,contactsModelList);
                                    contacts_recyclerview.setAdapter(contactAdapter);

                                }else {
                                    contacts_recyclerview.setVisibility(View.GONE);
                                    tv_no_contacts.setVisibility(View.VISIBLE);
                                }




                                contact_progressbar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        contact_progressbar.setVisibility(View.GONE);
                        //   Log.d(TAG, "recently error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MemberId", String.valueOf(othermemberid));

                return params;


            }
        };

        VolleySingleton.getInstance(MatrimonyMyContactsActivity.this).addToRequestQueue(stringRequest);


    }
}
