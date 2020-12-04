package in.co.vsys.myssksamaj.activities_news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.CommonLoginActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.activities_advertisement.AdvertisementActivity;
import in.co.vsys.myssksamaj.adapterNews.NewsByTypeId_Adapter;
import in.co.vsys.myssksamaj.adapterNews.NewsType_Adapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessServiceAdapter1;
import in.co.vsys.myssksamaj.business_activity.BusinessServicesActivity;
import in.co.vsys.myssksamaj.business_activity.BusinessSubActivity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.fragment_news.NewsHomeFragment;
import in.co.vsys.myssksamaj.fragment_news.NewsInsertFragment;
import in.co.vsys.myssksamaj.fragmentsBusines.BusinessHomeFragment;
import in.co.vsys.myssksamaj.interfaces.CallableNewstypeChange1;
import in.co.vsys.myssksamaj.maindirectory.MainMobileNumberActivity;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import in.co.vsys.myssksamaj.network1.NewsAPIClient;
import in.co.vsys.myssksamaj.newsmodels.AllNewsTypeModel;
import in.co.vsys.myssksamaj.newsmodels.NewsUsingTypeId;
import in.co.vsys.myssksamaj.newsmodels.SelectNewsUsingNewsId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDashboardActivity extends AppCompatActivity implements CallableNewstypeChange1{

    Toolbar toolbar_newsHome;
    RecyclerView recycler_news_type,recycler_news_by_type,recycler_news_allType;
    ProgressBar news_dash_progressbar;
    NewsType_Adapter newsType_adapter;
    CallableNewstypeChange1 change1;

    NewsByTypeId_Adapter newsByTypeId_adapter;

    ArrayList<AllNewsTypeModel.AllNewsType> newsTypeArrayList;
    ArrayList<NewsUsingTypeId> newsUsingTypeIdArrayList;
    ArrayList<NewsUsingTypeId> allNewsArrayList;
    private SharedPreferences mPreference;
    TextView tv_news_login;

    private int PastVisibleItems, VisibleItemCount, TotalItemCount,
            JoinedPage = 0;
    private boolean recentlyJoinedLoading = false;
    String registereduserid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_dashboard);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        toolbar_newsHome=(Toolbar)findViewById(R.id.toolbar_newsHome);
        setSupportActionBar(toolbar_newsHome);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("News");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        if(mPreference.contains("App_common_userId")){
            registereduserid= mPreference.getString("App_common_userId","0");
        }

        FloatingActionButton fab_news=(FloatingActionButton)findViewById(R.id.fab_news);
        fab_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPreference.contains("App_common_userId")) {
                    Intent intent=new Intent(NewsDashboardActivity.this,InsertNewsActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(NewsDashboardActivity.this, "Login First", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(NewsDashboardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void init() {
        allNewsArrayList=new ArrayList<>();
        newsTypeArrayList=new ArrayList<>();
        newsUsingTypeIdArrayList=new ArrayList<>();
        tv_news_login=(TextView)findViewById(R.id.tv_news_login);
        if(mPreference.contains("App_common_userId")){
            String fname=  mPreference.getString("common_fname","");
            String lname=mPreference.getString("common_lname","");
            tv_news_login.setText(fname+" "+lname);
            tv_news_login.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_logout,0);
        }else {
            tv_news_login.setText("Login");
        }

        tv_news_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPreference.contains("App_common_userId")){
                    logoutAlertDialog();
                }else {


                    Intent intent=new Intent(NewsDashboardActivity.this,CommonLoginActivity.class);
                    intent.putExtra("Login","news");
                    startActivity(intent);
                }

            }
        });



        recycler_news_allType=(RecyclerView)findViewById(R.id.recycler_news_allType);
        recycler_news_allType.setHasFixedSize(true);
        recycler_news_allType.setLayoutManager(new LinearLayoutManager(this));

        recycler_news_type=(RecyclerView)findViewById(R.id.recycler_news_type);
        recycler_news_type.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager1 = new LinearLayoutManager(this);
        MyLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_news_type.setLayoutManager(MyLayoutManager1);

        recycler_news_by_type=(RecyclerView)findViewById(R.id.recycler_news_by_type);
        recycler_news_by_type.setHasFixedSize(true);
        recycler_news_by_type.setLayoutManager(new LinearLayoutManager(this));
        news_dash_progressbar=(ProgressBar)findViewById(R.id.news_dash_progressbar);



        recycler_news_allType.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    VisibleItemCount = recycler_news_allType.getLayoutManager().getChildCount();
                    TotalItemCount = recycler_news_allType.getLayoutManager().getItemCount();
                    PastVisibleItems = ((LinearLayoutManager) recycler_news_allType.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!recentlyJoinedLoading) {
                        if ((VisibleItemCount + PastVisibleItems) >= TotalItemCount) {
                            recentlyJoinedLoading = true;
                            JoinedPage++;
                            Log.d("mytag", "onScrolled: "+JoinedPage);
                            loadAllNews(String.valueOf(JoinedPage));
                            // recentlyJointPresenter.getRecentlyJoint("" + memberId, "" + recentlyJoinedPage);
                        }
                    }
                }
            }
        });

        loadNewsType();
        loadAllNews(String.valueOf(JoinedPage));


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

                startActivity(new Intent(NewsDashboardActivity.this, MainMobileNumberActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

    private void loadAllNews(String page) {
        recycler_news_by_type.setVisibility(View.GONE);
        news_dash_progressbar.setVisibility(View.VISIBLE);
        if(allNewsArrayList!=null){
            allNewsArrayList.clear();
        }
        BusinessAPIServices service = NewsAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SelectNewsUsingNewsId> call = service.getAllNews("","",page,registereduserid);
        call.enqueue(new Callback<SelectNewsUsingNewsId>() {
            @Override
            public void onResponse(Call<SelectNewsUsingNewsId> call, Response<SelectNewsUsingNewsId> response) {
                news_dash_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    allNewsArrayList = response.body().getNewsUsingTypeIdArrayList();
                    if (allNewsArrayList.size() > 0) {
                        newsByTypeId_adapter = new NewsByTypeId_Adapter(NewsDashboardActivity.this, allNewsArrayList);
                        Log.d("mytag", "onResponse: "+allNewsArrayList.size());
                        recycler_news_allType.setAdapter(newsByTypeId_adapter);

                    } else {
                        Toast.makeText(NewsDashboardActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewsDashboardActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SelectNewsUsingNewsId> call, Throwable t) {
                news_dash_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                //newsByTypeId_adapter.notifyDataSetChanged();
                //Toast.makeText(NewsDashboardActivity.this, "Error.......", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadNewsType() {
        news_dash_progressbar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = NewsAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<AllNewsTypeModel> call = service.getAllNewstype();
        call.enqueue(new Callback<AllNewsTypeModel>() {
            @Override
            public void onResponse(Call<AllNewsTypeModel> call, Response<AllNewsTypeModel> response) {
                news_dash_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    newsTypeArrayList = response.body().getAllNewsTypeArrayList();
                    if (newsTypeArrayList.size() > 0) {
                        newsType_adapter = new NewsType_Adapter(NewsDashboardActivity.this, newsTypeArrayList,change1);
                        recycler_news_type.setAdapter(newsType_adapter);
                    } else {
                        Toast.makeText(NewsDashboardActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewsDashboardActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllNewsTypeModel> call, Throwable t) {
                news_dash_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(NewsDashboardActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void getNewsType(String str,String str2) {

       // Toast.makeText(this, ""+str+"\n"+str2, Toast.LENGTH_SHORT).show();
        if(str2.equals("All")){
            allNewsArrayList.clear();
            loadAllNews("0");
            recycler_news_allType.setVisibility(View.VISIBLE);
            recycler_news_by_type.setVisibility(View.GONE);
        }else {
            newsUsingTypeIdArrayList.clear();
            loadNewsUsingNewsd(str);
            recycler_news_allType.setVisibility(View.GONE);
            recycler_news_by_type.setVisibility(View.VISIBLE);

        }

    }


    private void loadNewsUsingNewsd(String str) {
        recycler_news_allType.setVisibility(View.GONE);
        recycler_news_by_type.setVisibility(View.VISIBLE);
        news_dash_progressbar.setVisibility(View.VISIBLE);
        if(newsUsingTypeIdArrayList!=null){
            newsUsingTypeIdArrayList.clear();
        }
        BusinessAPIServices service = NewsAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SelectNewsUsingNewsId> call = service.getAllNewsbyTypeId(str,registereduserid);
        call.enqueue(new Callback<SelectNewsUsingNewsId>() {
            @Override
            public void onResponse(Call<SelectNewsUsingNewsId> call, Response<SelectNewsUsingNewsId> response) {
                news_dash_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()==1) {
                        newsUsingTypeIdArrayList = response.body().getNewsUsingTypeIdArrayList();
                        if (newsUsingTypeIdArrayList.size() > 0) {
                            newsByTypeId_adapter = new NewsByTypeId_Adapter(NewsDashboardActivity.this, newsUsingTypeIdArrayList);
                            Log.d("mytag", "onResponse: " + newsUsingTypeIdArrayList.size());
                            recycler_news_by_type.setAdapter(newsByTypeId_adapter);
                        } else {
                            Toast.makeText(NewsDashboardActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                        }
                    }else if(response.body().getSuccess()==0) {
                        Toast.makeText(NewsDashboardActivity.this, "No Data found...", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(NewsDashboardActivity.this, "No Data found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SelectNewsUsingNewsId> call, Throwable t) {
                news_dash_progressbar.setVisibility(View.GONE);
                newsByTypeId_adapter.notifyDataSetChanged();
                Log.d("mytag", "onFailure: " + t.getMessage());
                //Toast.makeText(NewsDashboardActivity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
