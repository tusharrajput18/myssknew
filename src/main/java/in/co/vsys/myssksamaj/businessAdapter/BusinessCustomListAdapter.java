package in.co.vsys.myssksamaj.businessAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import in.co.vsys.myssksamaj.business_activity.BusinessDetailsMainActivity;
import in.co.vsys.myssksamaj.businessmodels.BusinessModelList;


public class BusinessCustomListAdapter extends RecyclerView.Adapter<BusinessCustomListAdapter.BusinessViewHolder> {

    ArrayList<BusinessModelList> businessModelLists;
    Context context;


    public BusinessCustomListAdapter(Context context, ArrayList<BusinessModelList> businessModelLists) {
        this.context = context;
        this.businessModelLists=businessModelLists;
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_business_row, viewGroup, false);

        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder customViewHolder, int i) {
        final BusinessModelList model = businessModelLists.get(i);
        customViewHolder.tv_business_name.setText(""+model.getBusinessname());
        customViewHolder.tv_business_discription.setText(""+model.getContactpersonname());
      /*  customViewHolder.linear_sub_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BusinessServicesActivity.class);
                intent.putExtra("serviceid",model.getId());
                context.startActivity(intent);
            }
        });*/

      customViewHolder.tv_custom_row_business_mobile.setText(""+model.getPhone1());
      customViewHolder.tv_custom_row_business_address.setText(""+model.getAddress());
      customViewHolder.tv_custom_row_aboutus.setText(""+model.getAboutus());

        customViewHolder.linear_business_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+model.getPhone1()));
                context.startActivity(intent);


            }
        });

        customViewHolder.tv_business_deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+model.getBusinessname()+" will contact you soon..", Toast.LENGTH_SHORT).show();
            }
        });


      customViewHolder.linearLayout_business_list.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent intent=new Intent(context, BusinessDetailsMainActivity.class);
              intent.putExtra("model",model);
              context.startActivity(intent);

          }
      });


        if(model.getImage1().length()>0){
            Picasso.get()
                    .load(model.getImage1())
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(customViewHolder.img_custom_business);
        }else {
            Picasso.get()
                    .load(R.drawable.imageview_default_image)
                    .placeholder(R.drawable.imageview_default_image)
                    .error(R.drawable.imageview_default_image)
                    .into(customViewHolder.img_custom_business);
        }



    }



    @Override
    public int getItemCount() {
        return businessModelLists.size();
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {
        TextView tv_business_name,tv_business_discription,tv_business_deals;
        LinearLayout linear_business_call;
        ImageView img_custom_business;
        LinearLayout linearLayout_business_list;
        TextView tv_custom_row_business_mobile,tv_custom_row_business_address,tv_custom_row_aboutus;


        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_business_name=(TextView)itemView.findViewById(R.id.tv_business_name);
            tv_business_discription=(TextView)itemView.findViewById(R.id.tv_business_discription);
            tv_business_deals=(TextView)itemView.findViewById(R.id.tv_business_deals);
            linear_business_call=(LinearLayout) itemView.findViewById(R.id.linear_business_call);
            img_custom_business=(ImageView) itemView.findViewById(R.id.img_custom_business);
            linearLayout_business_list=(LinearLayout) itemView.findViewById(R.id.linearLayout_business_list);

            tv_custom_row_business_mobile=(TextView)itemView.findViewById(R.id.tv_custom_row_business_mobile);
            tv_custom_row_business_address=(TextView)itemView.findViewById(R.id.tv_custom_row_business_address);
            tv_custom_row_aboutus=(TextView)itemView.findViewById(R.id.tv_custom_row_aboutus);


        }



    }
}
