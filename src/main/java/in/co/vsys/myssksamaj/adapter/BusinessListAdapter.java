package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDetailsActivity;
import in.co.vsys.myssksamaj.model.data_models.BusinessModel;


/**
 * Created by Vysys on 04/04/2018.
 */

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder> {

    private Context mContext;
    private List<BusinessModel> businessList;

    public BusinessListAdapter(Context mContext, List<BusinessModel> businessList) {
        this.mContext = mContext;
        this.businessList = businessList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_business_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final BusinessModel businessModel = businessList.get(position);

        String url = businessModel.getImageOne();

        if (url != null) {

            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.img_preview)
                    .into(holder.imageView);
        } else {

            Picasso.get()
                    .load(R.drawable.circle_preview)
                    .error(R.drawable.circle_preview)
                    .into(holder.imageView);
        }

        holder.tv_businessName.setText(businessModel.getBusinessName());
        holder.tv_location.setText(businessModel.getBusinessCity());
        holder.tv_office_no.setText(businessModel.getContactNo());
        holder.tv_businessType.setText(businessModel.getBusinessType());
        holder.tv_website.setText(businessModel.getBusinessUrl());

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BusinessDetailsActivity.class);

                int businessId = businessModel.getBusinessId();

                intent.putExtra("businessId", businessId);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;
        AppCompatTextView tv_businessName, tv_office_no, tv_location, tv_website, tv_businessType;
        LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.businessList_rowImage);
            tv_businessName = itemView.findViewById(R.id.businessList_rowName);
            tv_office_no = itemView.findViewById(R.id.businessList_rowNumber);
            tv_location = itemView.findViewById(R.id.businessList_rowCity);
            tv_website = itemView.findViewById(R.id.businessList_rowWebsite);
            tv_businessType = itemView.findViewById(R.id.businessList_rowBType);
            mLinearLayout = itemView.findViewById(R.id.layout_businessList);
        }
    }
}
