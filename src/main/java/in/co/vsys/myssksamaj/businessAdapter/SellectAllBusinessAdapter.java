package in.co.vsys.myssksamaj.businessAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.business_activity.BusinessSubActivity;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;
import in.co.vsys.myssksamaj.fragmentsBusines.BusinessHomeFragment;


public class SellectAllBusinessAdapter extends RecyclerView.Adapter<SellectAllBusinessAdapter.BusinessViewHolder> {

    ArrayList<SellectAllBusinessType.BusinessModules> sellectallbusinesslist;
    Context context;

    public SellectAllBusinessAdapter(Context context, ArrayList<SellectAllBusinessType.BusinessModules> arrayList) {
        this.sellectallbusinesslist = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_business_main_layout, viewGroup, false);

        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder customViewHolder, int i) {
        final SellectAllBusinessType.BusinessModules model = sellectallbusinesslist.get(i);

        customViewHolder.tv_buisness_title.setText(""+model.getBusinessType());


        if(model.getBusiness_image().length()>0){
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
        });


    }

    @Override
    public int getItemCount() {
        return sellectallbusinesslist.size();
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {
        TextView tv_buisness_title;
        ImageView buisness_title_imageView;
        ConstraintLayout business_main;

        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_buisness_title=(TextView)itemView.findViewById(R.id.tv_buisness_title);
            buisness_title_imageView=(ImageView) itemView.findViewById(R.id.buisness_title_imageView);
            business_main=(ConstraintLayout) itemView.findViewById(R.id.business_main);

        }



    }
}
