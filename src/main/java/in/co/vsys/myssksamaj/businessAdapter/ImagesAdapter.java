package in.co.vsys.myssksamaj.businessAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ItemViewHolder> {
    Context mContext;

    ArrayList<String> images;
    public static final String TAG="TAG";
    List<String> mPictureList;
    private String imgPath;

    public ImagesAdapter(Context mContext, ArrayList<String> images) {
        this.mContext = mContext;
        this.images = images;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_imageupload, parent, false);
        return new ItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, final int position) {
        if (images.size() > 0) {
            String image = images.get(position).toString();
            if (!image.equals("")) {


                Picasso.get()
                        .load(new File(image))
                        .placeholder(R.drawable.preview)
                        .error(R.drawable.preview)
                        .into(itemViewHolder.ivImage);
            }
            itemViewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    images.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return images.size() > 9 ? 9: images.size();
//        return images.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView ivClose;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ivImage=(ImageView)itemView.findViewById(R.id.iv_image);
            ivClose=(ImageView)itemView.findViewById(R.id.iv_close);
        }
    }
}