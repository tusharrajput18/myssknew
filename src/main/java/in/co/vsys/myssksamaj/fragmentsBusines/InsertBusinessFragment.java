package in.co.vsys.myssksamaj.fragmentsBusines;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsertBusinessFragment extends Fragment {

    public static final String TAG = InsertBusinessFragment.class.getSimpleName();

    private Context mContext;
   // private ProgressBar mProgressBar;
    private TextView btn_next;
    private TextInputEditText et_businessname,et_businessmobileno,et_businesswhatsappno,et_businessemailid,et_business_personname;
    private String BusinessName,Phone1,Phone2,EmailId,ContactPersonName;


    public InsertBusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // ((BusinessDashboardActivity) getActivity()).setActionBarTitle("Insert Business");

        View view = inflater.inflate(R.layout.fragment_insert_business, container, false);

      //  mProgressBar = view.findViewById(R.id.progressBar_insertBusiness);
        btn_next = view.findViewById(R.id.btn_insertFrag_next);
        et_businessname=(TextInputEditText)view.findViewById(R.id.et_businessname);
        et_businessmobileno=(TextInputEditText)view.findViewById(R.id.et_businessmobileno);
        et_businesswhatsappno=(TextInputEditText)view.findViewById(R.id.et_businesswhatsappno);

        et_businessemailid=(TextInputEditText)view.findViewById(R.id.et_businessemailid);
        et_business_personname=(TextInputEditText)view.findViewById(R.id.et_business_personname);


        if (ConnectionHelper.networkConnectivity(mContext)) {

        } else {
            Toast.makeText(getActivity(), "No internet connection ", Toast.LENGTH_SHORT).show();
        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessName=et_businessname.getText().toString();
                Phone1=et_businessmobileno.getText().toString();
                Phone2=et_businesswhatsappno.getText().toString();
                EmailId=et_businessemailid.getText().toString();
                ContactPersonName=et_business_personname.getText().toString();
              if(validateData()) {
                  InsertBusinessFragment2 fragment2 = new InsertBusinessFragment2();
                  Bundle args = new Bundle();
                  args.putString("business_Name", BusinessName);
                  args.putString("mobile_No", Phone1);
                  args.putString("whatsapp_No", Phone2);
                  args.putString("business_Email", EmailId);
                  args.putString("contactPerson_name", ContactPersonName);
                  fragment2.setArguments(args);
                  FragmentManager fragmentManager = getFragmentManager();
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.nav_business_layout, fragment2);
                  fragmentTransaction.commit();
              }
            }
        });

        return view;
    }
    private boolean validateData() {
        if(et_businessname.getText().toString().isEmpty()){
            et_businessname.setError("Invalid Business Name");
            Toast.makeText(mContext, "Invalid Business Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_businessmobileno.getText().toString().isEmpty() ){
            et_businessmobileno.setError("Invalid Number");
            Toast.makeText(mContext, "Invalid Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_businesswhatsappno.getText().toString().isEmpty()){
            et_businesswhatsappno.setError("Invalid Whatsapp Number");
            Toast.makeText(mContext, "Invalid Whatsapp Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_businessemailid.getText().toString().isEmpty()) {
            et_businessemailid.setError("Invalid Mail id");
            Toast.makeText(getActivity(), "Invalid Mail id", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_business_personname.getText().toString().isEmpty()) {
            et_business_personname.setError("Invalid Name");
            Toast.makeText(getActivity(), "Invalid Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean isValidBusinessName(String bName) {

        if (bName != null && bName.length() > 3) {

            return true;
        }

        return false;
    }

    private boolean isValidBusinessAddress(String bAddress) {

        if (bAddress != null && bAddress.length() > 3) {

            return true;
        }

        return false;
    }

    private boolean isValidBusinessMobile(String bMobile) {

        if (bMobile != null && bMobile.length() >= 10 && bMobile.length() < 11) {

            return true;
        }

        return false;
    }

    private boolean isValidBusinessONo(String bOfficeNO) {

        if (bOfficeNO != null && bOfficeNO.length() >= 7 && bOfficeNO.length() < 15) {

            return true;
        }

        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidWebsite(String bWebsite) {

        if (bWebsite != null && bWebsite.length() >= 5) {

            return true;
        }

        return false;
    }

    private boolean isValidServices(String bServices) {

        if (bServices != null && bServices.length() >= 10) {

            return true;
        }

        return false;
    }

    private boolean isValidContactPerson(String contactPerson) {

        if (contactPerson != null && contactPerson.length() > 3) {

            return true;
        }

        return false;
    }

/*    private void loadBusinessType() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.business_type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "business type response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = null;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int bTypeId = object.getInt("businessTypeId");
                                    String bTypeName = object.getString("businessType");

                                    BusinessTypeModel typeModel = new BusinessTypeModel(bTypeId, bTypeName);

                                    businessTypeList.add(typeModel);

                                }

                                BusinessTypeAdapter adapter = new BusinessTypeAdapter(getActivity(), businessTypeList);
                                spinner_business_type.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "business type error" + error);
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }*/
}
