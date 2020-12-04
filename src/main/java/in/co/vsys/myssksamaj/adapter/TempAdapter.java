package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.CountryModel;

/**
 * Created by Vysys on 20/03/2018.
 */

public class TempAdapter extends ArrayAdapter<CountryModel> {

    private Context mContext;
    private List<CountryModel> tempList;
    private LayoutInflater inflater;

    public TempAdapter(Context mContext, List<CountryModel> tempList) {
        super(mContext, android.R.layout.simple_spinner_dropdown_item, tempList);
        this.mContext = mContext;
        this.tempList = tempList;
    }

    static class ViewHolder {

        private AppCompatTextView tv_rowTitle;
    }

    @Override
    public int getCount() {
        return tempList.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_simple_spinner, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_rowTitle = convertView.findViewById(R.id.row_spin_heading);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        CountryModel countryModel = tempList.get(position);
        viewHolder.tv_rowTitle.setText(countryModel.getCountryName());

        return convertView;
    }

}
