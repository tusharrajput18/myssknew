package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import in.co.vsys.myssksamaj.R;

/**
 * Created by Jack on 09/12/2017.
 */

public class GridDashboardAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private Context mContext;

    private String[] names = {

            "News",
            "Advertise",
            "Matrimony",
            "Business",
            "Achievement",
            "Donation"

    };

    private Integer[] mThumbnails = {

            R.drawable.icon_nes,
            R.drawable.icon_advt,
            R.drawable.icon_matrimony,
            R.drawable.icon_business,
            R.drawable.icon_achievment,
            R.drawable.icon_donation

    };

    public GridDashboardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mThumbnails.length;
    }

    @Override
    public Object getItem(int i) {
        return mThumbnails[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_gridview, null);

        ImageView img = convertView.findViewById(R.id.img_gridRow);
        img.setImageResource(mThumbnails[i]);

        AppCompatTextView tvHeading = convertView.findViewById(R.id.tv_row_heading);
        tvHeading.setText(names[i]);

        return convertView;
    }
}
