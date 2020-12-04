package in.co.vsys.myssksamaj.adapterAdvertisement;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.interfaces.CallableNewstypeChange1;
import in.co.vsys.myssksamaj.modelAdvertisement.AllAdvertisementCumments;
import in.co.vsys.myssksamaj.newsmodels.AllNewsCumments;


public class AdvertisementAllComments_Adapter extends RecyclerView.Adapter<AdvertisementAllComments_Adapter.NewsViewHolder> {
    ArrayList<AllAdvertisementCumments> allAdvertisementCummentsArrayList;
    Context context;
    private CallableNewstypeChange1 change2;
    private SharedPreferences mPreference;
    String registereduserid = "";


    public AdvertisementAllComments_Adapter(Context context, ArrayList<AllAdvertisementCumments> allAdvertisementCummentsArrayList) {

        this.context = context;
        this.allAdvertisementCummentsArrayList = allAdvertisementCummentsArrayList;
        mPreference = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_advertisement_cumments, viewGroup, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int i) {
        registereduserid = mPreference.getString("App_common_userId", "0");
        final AllAdvertisementCumments model = allAdvertisementCummentsArrayList.get(i);

       holder.tv_add_comment_time.setText(""+model.getCreateddate());
        holder.tv_adds_row_commenter_profilename.setText("" + model.getFname() + " " + model.getLname());
        holder.tv_adds_row_comment.setText("" + model.getCommenttext());
        if (model.getProfileimage().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(holder.imgview_circular_row_adds_commenter_profile);
        } else {

            Picasso.get()
                    .load(model.getProfileimage())
                    .placeholder(R.drawable.img_preview)
                    .into(holder.imgview_circular_row_adds_commenter_profile);
        }




    }

    @Override
    public int getItemCount() {
        Log.d("mytaag", "getItemCount: " + allAdvertisementCummentsArrayList.size());
        return allAdvertisementCummentsArrayList.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_adds_row_commenter_profilename, tv_adds_row_comment,tv_add_comment_time;

        CircleImageView imgview_circular_row_adds_commenter_profile;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgview_circular_row_adds_commenter_profile=(CircleImageView)itemView.findViewById(R.id.imgview_circular_row_adds_commenter_profile);
            tv_adds_row_commenter_profilename=(TextView)itemView.findViewById(R.id.tv_adds_row_commenter_profilename);
            tv_adds_row_comment=(TextView)itemView.findViewById(R.id.tv_adds_row_comment);
            tv_add_comment_time=(TextView)itemView.findViewById(R.id.tv_add_comment_time);



        }

    }


}

