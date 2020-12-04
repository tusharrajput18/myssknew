package in.co.vsys.myssksamaj.activities_news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Collection;
import java.util.Collections;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.activities_advertisement.Advertisement_Detail_activity;
import in.co.vsys.myssksamaj.adapterNews.NewsAllComments_Adapter;
import in.co.vsys.myssksamaj.adapterNews.NewsByTypeId_Adapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.network1.AdvertisementAPIClient;
import in.co.vsys.myssksamaj.network1.NewsAPIClient;
import in.co.vsys.myssksamaj.newsmodels.AllNewsCumments;
import in.co.vsys.myssksamaj.newsmodels.NewsUsingTypeId;
import in.co.vsys.myssksamaj.newsmodels.SelectCummentsNewsId;
import in.co.vsys.myssksamaj.newsmodels.SelectNewsUsingNewsId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News_Detail_activity extends AppCompatActivity {

    NewsUsingTypeId model;
    Toolbar toolbar_newsdetails;
    private SharedPreferences mPreference;

    ImageView img_news_detail_1, imageview_title_logo;
    TextView tv_news_detail_page_title, tv_news_short_discription_detail, tv_news_long_discription_detail;
    AppCompatImageButton btn_send_news_comment;
    EditText et_send_comment_news_details;
    RecyclerView recycler_news_detail_comments;
    ProgressBar news_details_progressbar;
    String registereduserid = "";

    ArrayList<AllNewsCumments> allNewsCummentsArrayList;

    NewsAllComments_Adapter newsAllComments_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__detail_activity);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        registereduserid = mPreference.getString("App_common_userId", "0");
        toolbar_newsdetails = (Toolbar) findViewById(R.id.toolbar_newsdetails);
        setSupportActionBar(toolbar_newsdetails);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("News Details ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        model = (NewsUsingTypeId) getIntent().getSerializableExtra("model");

        init();
    }

    private void init() {

        allNewsCummentsArrayList=new ArrayList<>();

        Log.d("tushar", "init: \n" + model.getUsername() + " \n" + model.getShortdescription() + " \n" + model.getLongdescription() + " \n" + model.getImage());

        tv_news_detail_page_title = (TextView) findViewById(R.id.tv_news_detail_page_title);
        tv_news_short_discription_detail = (TextView) findViewById(R.id.tv_news_short_discription_detail);
        tv_news_long_discription_detail = (TextView) findViewById(R.id.tv_news_long_discription_detail);

        img_news_detail_1 = (ImageView) findViewById(R.id.img_news_detail_1);
        imageview_title_logo = (ImageView) findViewById(R.id.imageview_title_logo);

        btn_send_news_comment = (AppCompatImageButton) findViewById(R.id.btn_send_news_comment);
        et_send_comment_news_details = (EditText) findViewById(R.id.et_send_comment_news_details);

        news_details_progressbar = (ProgressBar) findViewById(R.id.news_details_progressbar);

        recycler_news_detail_comments = (RecyclerView) findViewById(R.id.recycler_news_detail_comments);
        recycler_news_detail_comments.setHasFixedSize(true);
        recycler_news_detail_comments.setLayoutManager(new LinearLayoutManager(this));


        if (model.getProfileimage().length() > 0) {
            Picasso.get()
                    .load(model.getProfileimage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageview_title_logo);
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageview_title_logo);
        }

        //  String image1=model.getImage();

        if (model.getImage2().length() > 0) {
            Picasso.get()
                    .load(model.getImage2())
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(img_news_detail_1);
        } else {
            Picasso.get()
                    .load(R.drawable.imageview_default_image)
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(img_news_detail_1);
        }

        tv_news_detail_page_title.setText("" + model.getUsername());
        tv_news_short_discription_detail.setText("" + model.getShortdescription());
        tv_news_long_discription_detail.setText("" + model.getLongdescription());

        et_send_comment_news_details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    btn_send_news_comment.setVisibility(View.VISIBLE);
                } else if (et_send_comment_news_details.getText().toString().isEmpty()) {
                    btn_send_news_comment.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_send_news_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAdvertisementComments(registereduserid, model.getNewsid(), et_send_comment_news_details.getText().toString());
            }
        });

        loadAllNewsCumments(model.getNewsid());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               // onBackPressed();
                Intent intent = new Intent(News_Detail_activity.this, NewsDashboardActivity.class);
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
        Intent intent = new Intent(News_Detail_activity.this, NewsDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void loadAllNewsCumments(String page) {
        news_details_progressbar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = NewsAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SelectCummentsNewsId> call = service.getNewsCummentsByNewsid(""+page);
        call.enqueue(new Callback<SelectCummentsNewsId>() {
            @Override
            public void onResponse(Call<SelectCummentsNewsId> call, Response<SelectCummentsNewsId> response) {
                news_details_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {
                    allNewsCummentsArrayList = response.body().getAllNewsCummentsArrayList();
                    if (allNewsCummentsArrayList.size() > 0) {
                        Collections.reverse(allNewsCummentsArrayList);
                        newsAllComments_adapter = new NewsAllComments_Adapter(News_Detail_activity.this, allNewsCummentsArrayList);
                        Log.d("mytag", "onResponse:  allNewsCummentsArrayList" + allNewsCummentsArrayList.size());
                        recycler_news_detail_comments.setAdapter(newsAllComments_adapter);
                        newsAllComments_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(News_Detail_activity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(News_Detail_activity.this, "Response Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SelectCummentsNewsId> call, Throwable t) {
                news_details_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                //Toast.makeText(NewsDashboardActivity.this, "Error.......", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void insertAdvertisementComments(String registereduserid, String newsid, String cumments) {
        news_details_progressbar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        BusinessAPIServices service = NewsAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<JsonResult> call = service.insertNewsCumments(registereduserid, newsid, cumments);
        call.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                news_details_progressbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() == 1) {
                        Toast.makeText(News_Detail_activity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        et_send_comment_news_details.setText("");
                        loadAllNewsCumments(model.getNewsid());
                    } else {
                        news_details_progressbar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(News_Detail_activity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    news_details_progressbar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(News_Detail_activity.this, "Error Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                news_details_progressbar.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(News_Detail_activity.this, "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
