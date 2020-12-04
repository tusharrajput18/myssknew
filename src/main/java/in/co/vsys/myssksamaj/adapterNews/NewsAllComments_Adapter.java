package in.co.vsys.myssksamaj.adapterNews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.activities_news.News_Detail_activity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.interfaces.CallableNewstypeChange1;
import in.co.vsys.myssksamaj.network1.NewsAPIClient;
import in.co.vsys.myssksamaj.newsmodels.AllNewsCumments;
import in.co.vsys.myssksamaj.newsmodels.NewsUsingTypeId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsAllComments_Adapter extends RecyclerView.Adapter<NewsAllComments_Adapter.NewsViewHolder> {
    ArrayList<AllNewsCumments> allNewsCummentsArrayList;
    Context context;
    private CallableNewstypeChange1 change2;
    private SharedPreferences mPreference;
    String registereduserid = "";


    public NewsAllComments_Adapter(Context context, ArrayList<AllNewsCumments> allNewsCummentsArrayList) {

        this.context = context;
        this.allNewsCummentsArrayList = allNewsCummentsArrayList;
        mPreference = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_news_cumments, viewGroup, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int i) {
        registereduserid = mPreference.getString("App_common_userId", "0");
        final AllNewsCumments model = allNewsCummentsArrayList.get(i);

       /* if (model.getImage2().length() > 0) {
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
        }*/

       holder.tv_comment_time.setText(""+model.getCreateddate());
        holder.tv_news_row_commenter_profilename.setText("" + model.getFname() + " " + model.getLname());
        holder.tv_new_row_comment.setText("" + model.getCommenttext());
        if (model.getProfileimage().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(holder.circleImageView);
        } else {

            Picasso.get()
                    .load(model.getProfileimage())
                    .placeholder(R.drawable.img_preview)
                    .into(holder.circleImageView);
        }




    }

    @Override
    public int getItemCount() {
        Log.d("mytaag", "getItemCount: " + allNewsCummentsArrayList.size());
        return allNewsCummentsArrayList.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_news_row_commenter_profilename, tv_new_row_comment,tv_comment_time;

        CircleImageView circleImageView;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            circleImageView=(CircleImageView)itemView.findViewById(R.id.imgview_circular_row_news_commenter_profile);
            tv_news_row_commenter_profilename=(TextView)itemView.findViewById(R.id.tv_news_row_commenter_profilename);
            tv_new_row_comment=(TextView)itemView.findViewById(R.id.tv_new_row_comment);
            tv_comment_time=(TextView)itemView.findViewById(R.id.tv_comment_time);



        }

    }


}

