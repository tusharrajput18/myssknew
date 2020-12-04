package in.co.vsys.myssksamaj.adapterNews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.activities_news.News_Detail_activity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.interfaces.CallableNewstypeChange1;
import in.co.vsys.myssksamaj.network1.NewsAPIClient;
import in.co.vsys.myssksamaj.newsmodels.AllNewsTypeModel;
import in.co.vsys.myssksamaj.newsmodels.NewsUsingTypeId;
import in.co.vsys.myssksamaj.newsmodels.SelectNewsUsingNewsId;
import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsByTypeId_Adapter extends RecyclerView.Adapter<NewsByTypeId_Adapter.NewsViewHolder> {
    ArrayList<NewsUsingTypeId> newsUsingTypeIdArrayList;
    Context context;
    private CallableNewstypeChange1 change2;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;
    private SharedPreferences mPreference;
    String registereduserid = "";
    private ImageView content;


    public NewsByTypeId_Adapter(Context context, ArrayList<NewsUsingTypeId> newsUsingTypeIdArrayList) {

        this.context = context;
        this.newsUsingTypeIdArrayList = newsUsingTypeIdArrayList;
        mPreference = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_news, viewGroup, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int i) {
        registereduserid = mPreference.getString("App_common_userId", "0");
        final NewsUsingTypeId model = newsUsingTypeIdArrayList.get(i);
        holder.news_short_discription.setText(model.getShortdescription());
        holder.news_long_discription.setText(model.getLongdescription());

        holder.tv_writer_name.setText(model.getUsername());


        if (model.getLiked().equals("0")) {
            holder.imageview_news_like.setColorFilter(null);
        } else {
            holder.imageview_news_like.setColorFilter(Color.argb(255, 204, 0, 204)); // White Tint
        }

        holder.linear_news_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getLiked().equals("0")) {
                    model.setLiked("1");

                    String likecount=String.valueOf(Integer.parseInt(model.getLikedcount())+1);
                    model.setLikedcount(likecount);

                    holder.setLiketrue(registereduserid, model.getNewsid(), "true");

                } else if (model.getLiked().equals("1")) {
                    model.setLiked("0");

                    String likecount=String.valueOf(Integer.parseInt(model.getLikedcount())-1);
                    model.setLikedcount(likecount);

                    holder.setLiketrue(registereduserid, model.getNewsid(), "false");

                }
            }

        });

        holder.linear_news_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,News_Detail_activity.class);
                intent.putExtra("model",model);
                context.startActivity(intent);

            }
        });


        if (model.getProfileimage().length() > 0) {
            Picasso.get()
                    .load(model.getProfileimage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.news_small_imageview);
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.news_small_imageview);
        }

        if (model.getImage2().length() > 0) {
            Picasso.get()
                    .load(model.getImage2())
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(holder.news_large_imageview);
        } else {
            Picasso.get()
                    .load(R.drawable.imageview_default_image)
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(holder.news_large_imageview);
        }

        holder.tv_news_row_like_count.setText(""+model.getLikedcount());
        holder.tv_news_row_comment_count.setText(""+model.getCommentcount());

        // sizeListViewHolder.changeToSelect(selectedPos == i ? Color.parseColor("#ca3854") : Color.BLACK);

        holder.linear_news_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(context, News_Detail_activity.class);
                intent.putExtra("model",model);
                context.startActivity(intent);*/
            }
        });

        holder.linear_news_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  content =  holder.news_large_imageview;
                content.setDrawingCacheEnabled(true);;
                Bitmap bitmap = Bitmap.createBitmap(content.getDrawingCache());*/
                String title=model.getUsername();
                String descripation=model.getShortdescription();
                String long_descripation=model.getLongdescription();

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    String sAux = "My SSk\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=in.co.vsys.myssksamaj \n\n"+title+"\n\n"+descripation+"\n\n"+long_descripation;
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    context.startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("mytaag", "getItemCount: " + newsUsingTypeIdArrayList.size());
        return newsUsingTypeIdArrayList.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView news_short_discription, news_long_discription, tv_writer_name;
        ImageView news_large_imageview, news_small_imageview;
        LinearLayout linear_news_row;
        LinearLayout linear_news_like,linear_news_comments;
        ImageView imageview_news_like;
        ProgressBar custom_news_progressbar;
        LinearLayout linear_news_share;

        TextView tv_news_row_like_count,tv_news_row_comment_count;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);

            news_short_discription = (TextView) itemView.findViewById(R.id.news_short_discription);
            news_long_discription = (TextView) itemView.findViewById(R.id.news_long_discription);


            news_large_imageview = (ImageView) itemView.findViewById(R.id.news_large_imageview);
            news_small_imageview = (ImageView) itemView.findViewById(R.id.news_small_imageview);

            tv_writer_name = (TextView) itemView.findViewById(R.id.tv_writer_name);

            linear_news_row = (LinearLayout) itemView.findViewById(R.id.linear_news_row);

            linear_news_comments = (LinearLayout) itemView.findViewById(R.id.linear_news_comments);
            linear_news_share = (LinearLayout) itemView.findViewById(R.id.linear_news_share);

            linear_news_like = (LinearLayout) itemView.findViewById(R.id.linear_news_like);
            imageview_news_like = (ImageView) itemView.findViewById(R.id.imageview_news_like);
            custom_news_progressbar = (ProgressBar) itemView.findViewById(R.id.custom_news_progressbar);

            tv_news_row_like_count=(TextView)itemView.findViewById(R.id.tv_news_row_like_count);
            tv_news_row_comment_count=(TextView)itemView.findViewById(R.id.tv_news_row_comment_count);

        }

        public void setLiketrue(String userid, String newsid, final String aTrue) {

            ((NewsDashboardActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    custom_news_progressbar.setVisibility(View.VISIBLE);
                }
            });

            BusinessAPIServices service = NewsAPIClient.getInstance().create(BusinessAPIServices.class);
            Call<JsonResult> call = service.insertNewsLike(userid, newsid, aTrue);
            call.enqueue(new Callback<JsonResult>() {
                @Override
                public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {

                    ((NewsDashboardActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            custom_news_progressbar.setVisibility(View.GONE);
                        }
                    });

                    if (response.isSuccessful()) {
                        if(aTrue.equals("true")) {
                            Toast.makeText(context, "Successfully liked..", Toast.LENGTH_SHORT).show();

                        }else if(aTrue.equals("false")){
                            Toast.makeText(context, "Successfully disliked..", Toast.LENGTH_SHORT).show();
                        }
                        notifyDataSetChanged();
                    } else {
                        custom_news_progressbar.setVisibility(View.GONE);
                        Toast.makeText(context, "Response Error...", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonResult> call, Throwable t) {
                    custom_news_progressbar.setVisibility(View.GONE);
                    Log.d("mytag", "onFailure: " + t.getMessage());
                }
            });

        }
    }


}

