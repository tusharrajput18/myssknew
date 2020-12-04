package in.co.vsys.myssksamaj.businessAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.business_activity.BusinessListActivity;
import in.co.vsys.myssksamaj.business_activity.BusinessServicesActivity;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.businessmodels.SellectAllSubCategoryType;


public class BusinessServiceAdapter1 extends RecyclerView.Adapter<BusinessServiceAdapter1.BusinessViewHolder> {

    ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> sellectAllServicesArrayList;
    Context context;
    public BusinessServiceAdapter1(BusinessServicesActivity context, ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> sellectAllServicesArrayList) {
        this.context = context;
        this.sellectAllServicesArrayList=sellectAllServicesArrayList;
    }


    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_sub_cat_list, viewGroup, false);

        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder customViewHolder, int i) {
        final SelectServicesUsingSubCategoryId.SellectAllServices model = sellectAllServicesArrayList.get(i);
        customViewHolder.subcat_textview.setText(""+model.getName());
        customViewHolder.linear_sub_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BusinessListActivity.class);
                intent.putExtra("serviceid",model.getId());
                intent.putExtra("fromactivityname","serviceactivity");
                context.startActivity(intent);
            }
        });


        /*if(model.getBusiness_image().length()>0){
            Picasso.get()
                    .load(model.getBusiness_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(customViewHolder.buisness_title_imageView);
        }else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(customViewHolder.buisness_title_imageView);
        }


        customViewHolder.buisness_title_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,BusinessSubActivity.class);
                intent.putExtra("mainBusinessid",model.getBusinessTypeId());
                context.startActivity(intent);

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return sellectAllServicesArrayList.size();
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {
        TextView subcat_textview;
        CardView linear_sub_cat;


        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            subcat_textview=(TextView)itemView.findViewById(R.id.subcat_textview);
            linear_sub_cat=(CardView) itemView.findViewById(R.id.linear_sub_cat);


        }



    }
}
