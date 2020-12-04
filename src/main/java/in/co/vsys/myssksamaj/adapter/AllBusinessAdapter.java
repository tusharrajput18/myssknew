package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessTypeListActivity;
import in.co.vsys.myssksamaj.model.data_models.BusinessTypeModel;

/**
 * Created by Vysys on 10/04/2018.
 */

public class AllBusinessAdapter extends RecyclerView.Adapter<AllBusinessAdapter.ViewHolder> {

    private Context mContext;
    private List<BusinessTypeModel> allBusinessList;

    public AllBusinessAdapter(Context mContext, List<BusinessTypeModel> allBusinessList) {
        this.mContext = mContext;
        this.allBusinessList = allBusinessList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final BusinessTypeModel businessTypeModel = allBusinessList.get(position);

        holder.tvHeading.setText(businessTypeModel.getBusinessName());

        holder.tvHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int businessTypeId = businessTypeModel.getBusinessId();

                Intent intent = new Intent(mContext, BusinessTypeListActivity.class);

                intent.putExtra("busi_type_Id", businessTypeId);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allBusinessList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeading;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHeading = itemView.findViewById(R.id.tv_spinner_heading);
        }
    }
}
