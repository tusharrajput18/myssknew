package in.co.vsys.myssksamaj.adapterAdvertisement;

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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_advertisement.Advertisement_Detail_activity;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.activities_news.News_Detail_activity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.interfaces.CallableNewstypeChange1;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisementList;
import in.co.vsys.myssksamaj.network1.AdvertisementAPIClient;
import in.co.vsys.myssksamaj.network1.NewsAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Advertisement_Adapter extends RecyclerView.Adapter<Advertisement_Adapter.AdvertisementViewHolder> {
    ArrayList<AdvertisementList> advertisementLists;
    Context context;
    private CallableNewstypeChange1 change2;
    private SharedPreferences mPreference;
    String registereduserid = "";
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;


    public Advertisement_Adapter(Context context, ArrayList<AdvertisementList> advertisementLists) {
        this.context=context;
        this.advertisementLists=advertisementLists;
        mPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @Override
    public AdvertisementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_advertisement, viewGroup, false);

        return new AdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdvertisementViewHolder holder, int i) {
        registereduserid = mPreference.getString("App_common_userId", "0");
        final AdvertisementList model=advertisementLists.get(i);
       /* if(model.getShortdescription().length()>0){
            holder.news_short_discription.setText(""+model.getShortdescription());
        }else {
            holder.news_short_discription.setText("");
        }*/

        holder.news_short_discription.setText(model.getShortdescription());

        holder.news_long_discription.setText(""+model.getLongdescription());

        holder.tv_advertiser_name.setText(model.getUsername());


        if(model.getProfileimage().length()>0){
            Picasso.get()
                    .load(model.getProfileimage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.news_small_imageview);
        }else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.news_small_imageview);
        }

        if(model.getImage().length()>0){
            Picasso.get()
                    .load(model.getImage())
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(holder.news_large_imageview);
        }else {
            Picasso.get()
                    .load(R.drawable.imageview_default_image)
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(holder.news_large_imageview);
        }

        if (model.getLiked().equals("0")) {
            holder.imageview_advertisement_like.setColorFilter(null);
        } else {
            holder.imageview_advertisement_like.setColorFilter(Color.argb(255, 204, 0, 204)); // White Tint
        }

        holder.linear_advertisement_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getLiked().equals("0")) {
                    model.setLiked("1");
                    String likecount=String.valueOf(Integer.parseInt(model.getLikedcount())+1);
                    model.setLikedcount(likecount);
                    holder.setLiketrue(registereduserid, model.getAdvertisementid(), "true");
                } else if (model.getLiked().equals("1")) {
                    model.setLiked("0");
                    String likecount=String.valueOf(Integer.parseInt(model.getLikedcount())-1);
                    model.setLikedcount(likecount);
                    holder.setLiketrue(registereduserid, model.getAdvertisementid(), "false");
                }
            }
        });

        holder.linear_advertisement_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Advertisement_Detail_activity.class);
                intent.putExtra("model",model);
                context.startActivity(intent);
            }
        });

        holder.tv_row_advertise_like_count.setText(""+model.getLikedcount());
        holder.tv_row_advertise_comment_count.setText(""+model.getCommentcount());
       // sizeListViewHolder.changeToSelect(selectedPos == i ? Color.parseColor("#ca3854") : Color.BLACK);

        holder.linear_advertisement_share.setOnClickListener(new View.OnClickListener() {
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
        Log.d("mytaag", "getItemCount: "+advertisementLists.size());
        return advertisementLists.size();
    }



    public class AdvertisementViewHolder extends RecyclerView.ViewHolder {


        TextView news_short_discription,news_long_discription,tv_advertiser_name;
        ImageView news_large_imageview,news_small_imageview;
        LinearLayout linear_advertisement_like,linear_advertisement_comments;
        ImageView imageview_advertisement_like;
        TextView tv_row_advertise_like_count,tv_row_advertise_comment_count;
        LinearLayout linear_advertisement_share;

        public AdvertisementViewHolder(@NonNull final View itemView) {
            super(itemView);

            tv_advertiser_name=(TextView)itemView.findViewById(R.id.tv_advertiser_name);

            news_short_discription=(TextView)itemView.findViewById(R.id.news_short_discription);
            news_long_discription=(TextView)itemView.findViewById(R.id.news_long_discription);

            news_large_imageview=(ImageView) itemView.findViewById(R.id.news_large_imageview);
            news_small_imageview=(ImageView) itemView.findViewById(R.id.news_small_imageview);

            imageview_advertisement_like=(ImageView)itemView.findViewById(R.id.imageview_advertisement_like);

            linear_advertisement_like=(LinearLayout) itemView.findViewById(R.id.linear_advertisement_like);
            linear_advertisement_comments=(LinearLayout)itemView.findViewById(R.id.linear_advertisement_comments);

            tv_row_advertise_like_count=(TextView) itemView.findViewById(R.id.tv_row_advertise_like_count);
            tv_row_advertise_comment_count=(TextView) itemView.findViewById(R.id.tv_row_advertise_comment_count);

            linear_advertisement_share=(LinearLayout) itemView.findViewById(R.id.linear_advertisement_share);

        }


        public void setLiketrue(String userid, String advertisementid, final String aTrue) {
            BusinessAPIServices service = AdvertisementAPIClient.getInstance().create(BusinessAPIServices.class);
            Call<JsonResult> call = service.insertAdvertisementLike(userid, advertisementid, aTrue);
            call.enqueue(new Callback<JsonResult>() {
                @Override
                public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                    if (response.isSuccessful()) {
                        if(aTrue.equals("true")) {
                            Toast.makeText(context, "Successfully liked..", Toast.LENGTH_SHORT).show();
                        }else if(aTrue.equals("false")){
                            Toast.makeText(context, "Successfully disliked..", Toast.LENGTH_SHORT).show();
                        }
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Response Error...", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonResult> call, Throwable t) {
                    Log.d("mytag", "onFailure: " + t.getMessage());
                }
            });

        }



    }


}

