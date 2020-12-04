package in.co.vsys.myssksamaj.fragmentsBusines;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.businessAdapter.BusinessCityAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessCountryAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessStateAdapter;
import in.co.vsys.myssksamaj.business_activity.BusinessMapsActivity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.businessmodels.SellectAllCity;
import in.co.vsys.myssksamaj.businessmodels.SellectAllCountry;
import in.co.vsys.myssksamaj.businessmodels.SellectAllState;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertBusinessFragment3 extends Fragment {

    private Context mContext;
    private static final String TAG = InsertBusinessFragment3.class.getSimpleName();
    View view;
    ProgressBar con_progressbar;
    ArrayList<SellectAllCountry.AllCountryList> arrayList;
    ArrayList<SellectAllState.AllStateList> stateLists_arraylist;
    ArrayList<SellectAllCity.AllCityList> allCityLists_arraylist;
    BusinessCountryAdapter businessCountryAdapter;
    BusinessStateAdapter businessStateAdapter;
    BusinessCityAdapter businessCityAdapter;
    TextView tv__business_country, tv_business_stateid, tv_business_city;
    TextView et_businesslocation;
    String countryId, stateid, cityid;

    public static String oklat="", oklong="";
    TextView tv_continue;
    ArrayList<String> images = new ArrayList<>();
    TextInputEditText et_business_address, et_business_taluka;

    private String IMAGE_1 = "";
    private String IMAGE_2 = "";
    private String IMAGE_3 = "";
    private String IMAGE_4 = "";
    private String IMAGE_5 = "";
    private String IMAGE_6 = "";
    private String IMAGE_7 = "";
    private String IMAGE_8 = "";
    private String IMAGE_9 = "";

    String business_name, mobile_no, whatsapp_no, email_id, contact_personName;
    String categoryid, subcatid, servicesid, abutcompany = "", discription = "";
    String userid;

    private SharedPreferences mPreference;


    public InsertBusinessFragment3() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InsertBusinessFragment3(ArrayList<String> images) {
        this.images = images;

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

        ((BusinessDashboardActivity) getActivity()).setActionBarTitle("Insert Business");

        view = inflater.inflate(R.layout.fragment_insert_business_next3, container, false);

        mPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid=mPreference.getString("App_common_userId","");
        business_name = getArguments().getString("business_Name");
        mobile_no = getArguments().getString("mobile_No");
        whatsapp_no = getArguments().getString("whatsapp_No");
        email_id = getArguments().getString("business_Email");
        contact_personName = getArguments().getString("contactPerson_name");
        categoryid = getArguments().getString("catid");
        subcatid = getArguments().getString("subcatids");
        servicesid = getArguments().getString("products");
        abutcompany = getArguments().getString("abutcompany");
        discription = getArguments().getString("discription");

        Log.d(TAG, "onCreateView: " + business_name + "\n" + mobile_no + "\n" + whatsapp_no + "\n" + email_id + "\n" + contact_personName + "\n" +
                categoryid + "\n" + subcatid + "\n" + servicesid + "\n" + abutcompany + "\n" + discription);


        arrayList = new ArrayList<>();
        stateLists_arraylist = new ArrayList<>();
        allCityLists_arraylist = new ArrayList<>();

        con_progressbar = (ProgressBar) view.findViewById(R.id.con_progressbar);

        Log.d(TAG, "onCreateView: " + images.size());
        getImages();
        if (ConnectionHelper.networkConnectivity(mContext)) {

            //loadBusinessType();
        } else {

            Toast.makeText(getActivity(), "No internet connection ", Toast.LENGTH_SHORT).show();
        }

        init(view);


        return view;
    }

    public void getImages() {

        if (images.size() == 0) {
            Toast.makeText(mContext, "No Images", Toast.LENGTH_SHORT).show();
        }

        if (images.size() == 1) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (images.size() == 2) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (images.size() == 3) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (images.size() == 4) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));
            Uri path4 = Uri.fromFile(new File(images.get(3)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path4);
                IMAGE_4 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (images.size() == 5) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));
            Uri path4 = Uri.fromFile(new File(images.get(3)));
            Uri path5 = Uri.fromFile(new File(images.get(4)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path4);
                IMAGE_4 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path5);
                IMAGE_5 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (images.size() == 6) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));
            Uri path4 = Uri.fromFile(new File(images.get(3)));
            Uri path5 = Uri.fromFile(new File(images.get(4)));
            Uri path6 = Uri.fromFile(new File(images.get(5)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path4);
                IMAGE_4 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path5);
                IMAGE_5 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path6);
                IMAGE_6 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if (images.size() == 7) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));
            Uri path4 = Uri.fromFile(new File(images.get(3)));
            Uri path5 = Uri.fromFile(new File(images.get(4)));
            Uri path6 = Uri.fromFile(new File(images.get(5)));
            Uri path7 = Uri.fromFile(new File(images.get(6)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path4);
                IMAGE_4 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path5);
                IMAGE_5 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path6);
                IMAGE_6 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path7);
                IMAGE_7 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if (images.size() == 8) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));
            Uri path4 = Uri.fromFile(new File(images.get(3)));
            Uri path5 = Uri.fromFile(new File(images.get(4)));
            Uri path6 = Uri.fromFile(new File(images.get(5)));
            Uri path7 = Uri.fromFile(new File(images.get(6)));
            Uri path8 = Uri.fromFile(new File(images.get(7)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path4);
                IMAGE_4 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path5);
                IMAGE_5 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path6);
                IMAGE_6 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path7);
                IMAGE_7 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path8);
                IMAGE_8 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if (images.size() == 9) {
            Uri path1 = Uri.fromFile(new File(images.get(0)));
            Uri path2 = Uri.fromFile(new File(images.get(1)));
            Uri path3 = Uri.fromFile(new File(images.get(2)));
            Uri path4 = Uri.fromFile(new File(images.get(3)));
            Uri path5 = Uri.fromFile(new File(images.get(4)));
            Uri path6 = Uri.fromFile(new File(images.get(5)));
            Uri path7 = Uri.fromFile(new File(images.get(6)));
            Uri path8 = Uri.fromFile(new File(images.get(7)));
            Uri path9 = Uri.fromFile(new File(images.get(8)));
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                IMAGE_1 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                IMAGE_2 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path3);
                IMAGE_3 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path4);
                IMAGE_4 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path5);
                IMAGE_5 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path6);
                IMAGE_6 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path7);
                IMAGE_7 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path8);
                IMAGE_8 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path9);
                IMAGE_9 = getStringImage(bitmap);
                // Log.d(TAG, "onClick: "+getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getAddress(oklat,oklong);
    }

    public void getAddress(String latitude, String longitude){
        if(latitude!="" && longitude!=""){
            Geocoder geocoder;
            List<Address> addresses=new ArrayList<>();
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(latitude),Double.parseDouble(longitude) , 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                et_businesslocation.setText(knownName+" "+city);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    private void init(View view) {
        tv_continue = (TextView) view.findViewById(R.id.tv_continue3);
        et_business_address = (TextInputEditText) view.findViewById(R.id.et_business_address);
        et_business_taluka = (TextInputEditText) view.findViewById(R.id.et_business_address);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = et_business_address.getText().toString();
                String taluka = et_business_taluka.getText().toString();


                if (validateData()) {
                    Log.d(TAG, "onClick: tushar"+oklat+" "+oklong);
                    insertBusinessData(userid,business_name, mobile_no, whatsapp_no, email_id, contact_personName, categoryid, subcatid, servicesid, abutcompany, discription, countryId, stateid, cityid, taluka, address, oklat, oklong);
                }

               /* Toast.makeText(getActivity(), ""+images.size(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), ""+oklat+"\n"+oklong, Toast.LENGTH_SHORT).show();*/
            }
        });

        // frameLayout3.addView(con_progressbar);

        tv__business_country = (TextView) view.findViewById(R.id.tv__business_country);
        tv_business_stateid = (TextView) view.findViewById(R.id.tv_business_stateid);
        tv_business_city = (TextView) view.findViewById(R.id.tv_business_city);

        tv__business_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadBusinescountry();
            }
        });

        tv_business_stateid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadBusinesState();
            }
        });

        tv_business_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadBusinesCity(stateid);
            }
        });


        et_businesslocation = (TextView) view.findViewById(R.id.et_businesslocation);

        et_businesslocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BusinessMapsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void insertBusinessData(String userid,String business_name, String mobile_no, String whatsapp_no, String email_id, String contact_personName, String categoryid, String subcatid, String servicesid, String abutcompany, String discription, String countryId, String stateid, String cityid, String taluka, String address, String oklat, String oklong) {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertBusiness(userid, categoryid, subcatid, business_name, address, mobile_no, whatsapp_no, "", servicesid, abutcompany,oklong , oklat, contact_personName, IMAGE_1, "jpg", IMAGE_2, "jpg", IMAGE_3, "jpg", IMAGE_4, "jpg", IMAGE_5, "jpg", IMAGE_6, "jpg", IMAGE_7, "jpg", IMAGE_8, "jpg", IMAGE_9, "jpg",
                discription, email_id, countryId, stateid, cityid, taluka, "", "", "", "", "", "", "");
        Log.d(TAG, "insertBusinessData: " + categoryid + "\n" + subcatid + "\n" + business_name + "\n" + address + "\n" + mobile_no + "\n" + whatsapp_no + "\n" + servicesid + "\n" + abutcompany + "\n" + oklat + "\n" + oklong + "\n" + contact_personName + "\n" + IMAGE_1);
        Log.d(TAG, "insertBusinessData: " + discription + "\n" + email_id + "\n" + countryId + "\n" + stateid + "\n" + cityid + "\n" + taluka);
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(getActivity(),BusinessDashboardActivity.class);


                    Intent i = new Intent(getActivity(), BusinessDashboardActivity.class);
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0, 0);

                } else {
                    Toast.makeText(getActivity(), "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validateData() {
        if (countryId.isEmpty()) {
            Toast.makeText(mContext, "Invalid Country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stateid.isEmpty()) {
            Toast.makeText(mContext, "Invalid State", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cityid.isEmpty()) {
            Toast.makeText(mContext, "Invalid City", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_business_address.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Invalid Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (oklat.isEmpty()) {
            Toast.makeText(mContext, "Invalid Location", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void loadBusinescountry() {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllCountry> call = service.getAllCountry();
        call.enqueue(new Callback<SellectAllCountry>() {
            @Override
            public void onResponse(Call<SellectAllCountry> call, Response<SellectAllCountry> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    arrayList = response.body().getAllCountryLists();
                    if (arrayList.size() > 0) {
                        businessCountryAdapter = new BusinessCountryAdapter(getActivity(), arrayList);
                        displayCountryAlert();

                    } else {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllCountry> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayCountryAlert() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);
        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessCountryAdapter);
        businessCountryAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllCountry.AllCountryList countryModel = (SellectAllCountry.AllCountryList) parent.getItemAtPosition(position);
                countryId = countryModel.getId();
                tv__business_country.setText(countryModel.getCountryname());
                dialog.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessCountryAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void loadBusinesState() {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllState> call = service.getAllState();
        call.enqueue(new Callback<SellectAllState>() {
            @Override
            public void onResponse(Call<SellectAllState> call, Response<SellectAllState> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    stateLists_arraylist = response.body().getAllStateLists();
                    if (stateLists_arraylist.size() > 0) {

                        businessStateAdapter = new BusinessStateAdapter(getActivity(), stateLists_arraylist);
                        displayStateAlert();
                    } else {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllState> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayStateAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessStateAdapter);
        businessStateAdapter.notifyDataSetChanged();

        final AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllState.AllStateList stateModel = (SellectAllState.AllStateList) parent.getItemAtPosition(position);
                stateid = stateModel.getId();
                tv_business_stateid.setText(stateModel.getStatename());
                dialog.dismiss();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessStateAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void loadBusinesCity(String stateid) {
        con_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllCity> call = service.getallCity(stateid);
        call.enqueue(new Callback<SellectAllCity>() {
            @Override
            public void onResponse(Call<SellectAllCity> call, Response<SellectAllCity> response) {
                con_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    allCityLists_arraylist = response.body().getAllCityLists();
                    if (allCityLists_arraylist.size() > 0) {

                        businessCityAdapter = new BusinessCityAdapter(getActivity(), allCityLists_arraylist);
                        displayCityAlert();
                    } else {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllCity> call, Throwable t) {
                con_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayCityAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub=alertView.findViewById(R.id.btn_ok_sub);
        btn_ok_sub.setVisibility(View.GONE);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        mListView.setAdapter(businessCityAdapter);
        businessCityAdapter.notifyDataSetChanged();
        final AlertDialog dialog = builder.create();
        dialog.show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllCity.AllCityList cityModel = (SellectAllCity.AllCityList) parent.getItemAtPosition(position);

                cityid = cityModel.getId();
                tv_business_city.setText(cityModel.getCityname());

                dialog.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessCityAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
