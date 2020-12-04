package in.co.vsys.myssksamaj.activities_advertisement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.CommonLoginActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.adapterAdvertisement.Advertisement_Adapter;
import in.co.vsys.myssksamaj.adapterAdvertisement.AdvertisemtnTypeAdapter1;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.interfaces.CallableAdvertisetypeChange1;
import in.co.vsys.myssksamaj.maindirectory.LoginActivity;
import in.co.vsys.myssksamaj.maindirectory.MainMobileNumberActivity;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisementByType;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisementList;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisemtnTypeList;
import in.co.vsys.myssksamaj.modelAdvertisement.ModelAdvertisementType;
import in.co.vsys.myssksamaj.network1.AdvertisementAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementActivity extends AppCompatActivity implements CallableAdvertisetypeChange1 {

    Toolbar toolbar_AdvertisementHome;
    RecyclerView recycler_advertisement_type,recycler_advertisement_by_type,recycler_All_advertisement;
    ProgressBar advertisement_dash_progressbar;
    AdvertisemtnTypeAdapter1 advertisemtnTypeAdapter1;
    CallableAdvertisetypeChange1 change1;

    Advertisement_Adapter advertisement_adapter;

    ArrayList<AdvertisemtnTypeList> advertisemtnTypeLists;
    ArrayList<AdvertisementList> advertisementLists;

    ArrayList<AdvertisementList> all_advertisementLists;

    TextView tv_advertise_login;

    private SharedPreferences mPreference;

    private int PastVisibleItems, VisibleItemCount, TotalItemCount,
            JoinedPage = 0;
    private boolean recentlyJoinedLoading = false;

    String registereduserid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        toolbar_AdvertisementHome=(Toolbar)findViewById(R.id.toolbar_AdvertisementHome);
        setSupportActionBar(toolbar_AdvertisementHome);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Advertisement");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(mPreference.contains("App_common_userId")){
            registereduserid= mPreference.getString("App_common_userId","0");
        }



        FloatingActionButton fab_advertisement=(FloatingActionButton)findViewById(R.id.fab_advertisement);
        fab_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPreference.contains("App_common_userId")) {

                    Intent intent = new Intent(AdvertisementActivity.this, InsertAdvertisementActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AdvertisementActivity.this, "Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        change1=this;

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AdvertisementActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void init() {

        tv_advertise_login=(TextView)findViewById(R.id.tv_advertise_login);

        if(mPreference.contains("App_common_userId")){
          String fname=  mPreference.getString("common_fname","");
            String lname=mPreference.getString("common_lname","");
            tv_advertise_login.setText(fname+" "+lname);
            tv_advertise_login.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_logout,0);
        }else {
            tv_advertise_login.setText("Login");
        }


        advertisemtnTypeLists=new ArrayList<>();
        advertisementLists=new ArrayList<>();
        all_advertisementLists=new ArrayList<>();

        recycler_advertisement_type=(RecyclerView)findViewById(R.id.recycler_advertisement_type);
        recycler_advertisement_type.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager1 = new LinearLayoutManager(this);
        MyLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_advertisement_type.setLayoutManager(MyLayoutManager1);

        recycler_advertisement_by_type=(RecyclerView)findViewById(R.id.recycler_advertisement_by_type);
        recycler_advertisement_by_type.setHasFixedSize(true);
        recycler_advertisement_by_type.setLayoutManager(new LinearLayoutManager(this));
        advertisement_dash_progressbar=(ProgressBar)findViewById(R.id.advertisement_dash_progressbar);


        recycler_All_advertisement=(RecyclerView)findViewById(R.id.recycler_All_advertisement);
        recycler_All_advertisement.setHasFixedSize(true);
        recycler_All_advertisement.setLayoutManager(new LinearLayoutManager(this));

        recycler_All_advertisement.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    VisibleItemCount = recycler_All_advertisement.getLayoutManager().getChildCount();
                    TotalItemCount = recycler_All_advertisement.getLayoutManager().getItemCount();
                    PastVisibleItems = ((LinearLayoutManager) recycler_All_advertisement.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!recentlyJoinedLoading) {
                        if ((VisibleItemCount + PastVisibleItems) >= TotalItemCount) {
                            recentlyJoinedLoading = true;
                            JoinedPage++;
                            Log.d("mytag", "onScrolled: "+JoinedPage);
                            loadAllAdvertiseMnet(String.valueOf(JoinedPage));

                        }
                    }
                }
            }
        });




        tv_advertise_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPreference.contains("App_common_userId")){
                    logoutAlertDialog();
                }else {


                    startActivity(new Intent(AdvertisementActivity.this, CommonLoginActivity.class).putExtra("Login","adds"));
                }

            }
        });
        loadAddsType();
        loadAllAdvertiseMnet(String.valueOf(JoinedPage));

    }
    private void logoutAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.logout_popup, null);
        builder.setView(view);

        AppCompatButton btn_self = view.findViewById(R.id.btn_rowSelf);
        AppCompatButton btn_parent = view.findViewById(R.id.btn_rowParent);
        TextView tv_login = view.findViewById(R.id.tv_rowLogin_login);
        TextView tv_register = view.findViewById(R.id.tv_rowLogin_register);

        tv_login.setVisibility(View.VISIBLE);
        tv_register.setVisibility(View.GONE);

        final AlertDialog alertDialog = builder.create();


        btn_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdvertisementActivity.this, MainMobileNumberActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                mPreference.edit().remove("common_fname").commit();
                mPreference.edit().remove("common_mname").commit();
                mPreference.edit().remove("common_lname").commit();
                mPreference.edit().remove("common_mobileNo").commit();
                mPreference.edit().remove("App_common_userId").commit();
                mPreference.edit().remove("otp").commit();

                alertDialog.dismiss();
            }
        });

        btn_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(mContext, LoginActivity.class));
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void loadAddsType() {
        advertisement_dash_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<ModelAdvertisementType> call = service.getAllAdvertisementType();
        call.enqueue(new Callback<ModelAdvertisementType>() {
            @Override
            public void onResponse(Call<ModelAdvertisementType> call, Response<ModelAdvertisementType> response) {
                advertisement_dash_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    advertisemtnTypeLists = response.body().getAdvertisemtnTypeLists();
                    if (advertisemtnTypeLists.size() > 0) {
                        advertisemtnTypeAdapter1 = new AdvertisemtnTypeAdapter1(AdvertisementActivity.this, advertisemtnTypeLists,change1);
                        recycler_advertisement_type.setAdapter(advertisemtnTypeAdapter1);
                    } else {
                        Toast.makeText(AdvertisementActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdvertisementActivity.this, "Response Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelAdvertisementType> call, Throwable t) {
                advertisement_dash_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(AdvertisementActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void getAdvertismentType(String str, String str1) {

        //Toast.makeText(this, ""+str+"\n"+str1, Toast.LENGTH_SHORT).show();

        if(str1.equals("All")){
            all_advertisementLists.clear();
            loadAllAdvertiseMnet("0");
            recycler_All_advertisement.setVisibility(View.VISIBLE);
            recycler_advertisement_by_type.setVisibility(View.GONE);
        }else {
            advertisementLists.clear();
            loadAdvertisementByTypeId(str);
            recycler_All_advertisement.setVisibility(View.GONE);
            recycler_advertisement_by_type.setVisibility(View.VISIBLE);

        }

    }

    private void loadAdvertisementByTypeId(String str) {
        recycler_advertisement_by_type.setVisibility(View.VISIBLE);
        advertisement_dash_progressbar.setVisibility(View.VISIBLE);
        if(advertisementLists!=null){
            advertisementLists.clear();
        }
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<AdvertisementByType> call = service.getallAdvertisementByType(str,registereduserid);
        call.enqueue(new Callback<AdvertisementByType>() {
            @Override
            public void onResponse(Call<AdvertisementByType> call, Response<AdvertisementByType> response) {
                advertisement_dash_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    advertisementLists = response.body().getAdvertisementLists();
                    if (advertisementLists.size() > 0) {
                        advertisement_adapter = new Advertisement_Adapter(AdvertisementActivity.this, advertisementLists);
                        Log.d("mytag", "onResponse: "+advertisementLists.size());
                        recycler_advertisement_by_type.setAdapter(advertisement_adapter);
                        advertisement_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AdvertisementActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdvertisementActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AdvertisementByType> call, Throwable t) {
                advertisement_dash_progressbar.setVisibility(View.GONE);
                advertisement_adapter.notifyDataSetChanged();
                Log.d("mytag", "onFailure: " + t.getMessage());
                //Toast.makeText(AdvertisementActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void loadAllAdvertiseMnet(String page) {
        recycler_All_advertisement.setVisibility(View.VISIBLE);
        advertisement_dash_progressbar.setVisibility(View.VISIBLE);
        if(all_advertisementLists!=null){
            all_advertisementLists.clear();
        }
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<AdvertisementByType> call = service.getallAdvertisement("","",page,registereduserid);
        call.enqueue(new Callback<AdvertisementByType>() {
            @Override
            public void onResponse(Call<AdvertisementByType> call, Response<AdvertisementByType> response) {
                advertisement_dash_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    all_advertisementLists = response.body().getAdvertisementLists();
                    if (all_advertisementLists.size() > 0) {
                        advertisement_adapter = new Advertisement_Adapter(AdvertisementActivity.this, all_advertisementLists);
                        Log.d("mytag", "onResponse: "+all_advertisementLists.size());
                        recycler_All_advertisement.setAdapter(advertisement_adapter);
                        advertisement_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AdvertisementActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdvertisementActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AdvertisementByType> call, Throwable t) {
                advertisement_dash_progressbar.setVisibility(View.GONE);
                //advertisement_adapter.notifyDataSetChanged();
                Log.d("mytag", "onFailure: " + t.getMessage());
                //Toast.makeText(AdvertisementActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
