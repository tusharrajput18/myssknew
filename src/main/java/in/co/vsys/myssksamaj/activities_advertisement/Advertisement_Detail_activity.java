package in.co.vsys.myssksamaj.activities_advertisement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_news.News_Detail_activity;
import in.co.vsys.myssksamaj.adapterAdvertisement.AdvertisementAllComments_Adapter;
import in.co.vsys.myssksamaj.adapterNews.NewsAllComments_Adapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisementList;
import in.co.vsys.myssksamaj.modelAdvertisement.AllAdvertisementCumments;
import in.co.vsys.myssksamaj.modelAdvertisement.SelectCummentsAdvertisementId;
import in.co.vsys.myssksamaj.network1.AdvertisementAPIClient;
import in.co.vsys.myssksamaj.network1.NewsAPIClient;
import in.co.vsys.myssksamaj.newsmodels.NewsUsingTypeId;
import in.co.vsys.myssksamaj.newsmodels.SelectCummentsNewsId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Advertisement_Detail_activity extends AppCompatActivity {

    AdvertisementList model;
    Toolbar toolbar_addsdetils;
    private SharedPreferences mPreference;

    ImageView img_adds_detail_1,imageview_adds_title_logo;
    TextView tv_adds_detail_page_title,tv_adds_short_discription_detail,tv_adds_long_discription_detail;
    AppCompatImageButton btn_send_adds_comment;
    EditText et_send_comment_adds_details;
    RecyclerView recycler_adds_detail_comments;
    ProgressBar advertisement_details_progressbar;
    String registereduserid="";
    ArrayList<AllAdvertisementCumments> allAdvertisementCummentsArrayList;
    AdvertisementAllComments_Adapter advertisementAllComments_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement__detail_activity);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        registereduserid = mPreference.getString("App_common_userId", "0");
        toolbar_addsdetils=(Toolbar)findViewById(R.id.toolbar_addsdetils);
        setSupportActionBar(toolbar_addsdetils);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Advertisement Details ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        model = (AdvertisementList) getIntent().getSerializableExtra("model");

        init();
    }

    private void init() {

        allAdvertisementCummentsArrayList=new ArrayList<>();

        Log.d("tushar", "init: \n"+model.getUsername()+" \n"+model.getShortdescription()+" \n"+model.getLongdescription()+" \n"+model.getImage());

        tv_adds_detail_page_title = (TextView) findViewById(R.id.tv_adds_detail_page_title);
        tv_adds_short_discription_detail = (TextView) findViewById(R.id.tv_adds_short_discription_detail);
        tv_adds_long_discription_detail = (TextView) findViewById(R.id.tv_adds_long_discription_detail);

        img_adds_detail_1 = (ImageView) findViewById(R.id.img_adds_detail_1);
        imageview_adds_title_logo = (ImageView) findViewById(R.id.imageview_adds_title_logo);

        btn_send_adds_comment = (AppCompatImageButton) findViewById(R.id.btn_send_adds_comment);
        et_send_comment_adds_details = (EditText) findViewById(R.id.et_send_comment_adds_details);


        advertisement_details_progressbar=(ProgressBar)findViewById(R.id.advertisement_details_progressbar);


        recycler_adds_detail_comments = (RecyclerView) findViewById(R.id.recycler_adds_detail_comments);
        recycler_adds_detail_comments.setHasFixedSize(true);
        recycler_adds_detail_comments.setLayoutManager(new LinearLayoutManager(this));


        if (model.getProfileimage().length() > 0) {
            Picasso.get()
                    .load(model.getProfileimage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageview_adds_title_logo);
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageview_adds_title_logo);
        }

          //  String image1=model.getImage();

            if (model.getImage2().length() > 0) {
                Picasso.get()
                        .load(model.getImage2())
                        .placeholder(R.drawable.imageview_default_image)
                        .error(R.drawable.imageview_default_image)
                        .into(img_adds_detail_1);
            } else {
                Picasso.get()
                        .load(R.drawable.imageview_default_image)
                        .placeholder(R.drawable.imageview_default_image)
                        .error(R.drawable.imageview_default_image)
                        .into(img_adds_detail_1);
            }

        tv_adds_detail_page_title.setText(""+model.getUsername());
        tv_adds_short_discription_detail.setText(""+model.getShortdescription());
        tv_adds_long_discription_detail.setText(""+model.getLongdescription());


        btn_send_adds_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAdvertisementComments(registereduserid,model.getAdvertisementid(),et_send_comment_adds_details.getText().toString());
            }
        });

        et_send_comment_adds_details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0){
                    btn_send_adds_comment.setVisibility(View.VISIBLE);
                }else if(et_send_comment_adds_details.getText().toString().isEmpty()) {
                    btn_send_adds_comment.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        loadAllNewsCumments(model.getAdvertisementid());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*  onBackPressed();*/
                Intent intent = new Intent(Advertisement_Detail_activity.this, AdvertisementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Advertisement_Detail_activity.this, AdvertisementActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void loadAllNewsCumments(String page) {
        advertisement_details_progressbar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SelectCummentsAdvertisementId> call = service.getAdvertisementCummentsByNewsid(""+page);
        call.enqueue(new Callback<SelectCummentsAdvertisementId>() {
            @Override
            public void onResponse(Call<SelectCummentsAdvertisementId> call, Response<SelectCummentsAdvertisementId> response) {
                advertisement_details_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {
                    allAdvertisementCummentsArrayList = response.body().getAllAdvertisementCummentsArrayList();
                    if (allAdvertisementCummentsArrayList.size() > 0) {
                        Collections.reverse(allAdvertisementCummentsArrayList);
                        advertisementAllComments_adapter = new AdvertisementAllComments_Adapter(Advertisement_Detail_activity.this, allAdvertisementCummentsArrayList);
                        Log.d("mytag", "onResponse:  allNewsCummentsArrayList" + allAdvertisementCummentsArrayList.size());
                        recycler_adds_detail_comments.setAdapter(advertisementAllComments_adapter);
                        advertisementAllComments_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Advertisement_Detail_activity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Advertisement_Detail_activity.this, "Response Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SelectCummentsAdvertisementId> call, Throwable t) {
                advertisement_details_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                //Toast.makeText(NewsDashboardActivity.this, "Error.......", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void insertAdvertisementComments(String registereduserid, String advertisementid, String cumments) {
        advertisement_details_progressbar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertAdvertisementCumments(registereduserid,advertisementid,cumments);
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                advertisement_details_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()==1){
                        Toast.makeText(Advertisement_Detail_activity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        et_send_comment_adds_details.setText("");
                        loadAllNewsCumments(model.getAdvertisementid());
                    }else {
                        advertisement_details_progressbar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(Advertisement_Detail_activity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    advertisement_details_progressbar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(Advertisement_Detail_activity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                advertisement_details_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(Advertisement_Detail_activity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
