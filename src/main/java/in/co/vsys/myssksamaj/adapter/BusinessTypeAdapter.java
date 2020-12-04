package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.data_models.BusinessTypeModel;

/**
 * Created by Vysys on 29/03/2018.
 */

public class BusinessTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<BusinessTypeModel> businessTypeLIst;
    private LayoutInflater inflater;

    public BusinessTypeAdapter(Context mContext, List<BusinessTypeModel> businessTypeLIst) {
        this.mContext = mContext;
        this.businessTypeLIst = businessTypeLIst;
    }

    private class BusinessViewHolder {

        AppCompatTextView tvHeading;
    }

    @Override
    public int getCount() {
        return businessTypeLIst.size();
    }

    @Override
    public Object getItem(int position) {
        return businessTypeLIst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BusinessViewHolder viewHolder;

        if (convertView == null) {

            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_row, parent, false);

            viewHolder = new BusinessViewHolder();

            viewHolder.tvHeading = convertView.findViewById(R.id.tv_spinner_heading);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (BusinessViewHolder) convertView.getTag();
        }

        BusinessTypeModel typeModel = businessTypeLIst.get(position);

        viewHolder.tvHeading.setText(typeModel.getBusinessName());


        return convertView;
    }
}
