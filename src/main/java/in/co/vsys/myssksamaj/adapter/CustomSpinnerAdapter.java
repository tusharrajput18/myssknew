package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import in.co.vsys.myssksamaj.utils.Utilities;

import java.util.List;

/**
 * @author Abhijeet
 */

public class CustomSpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    private Context mContext;
    private List<?> models;
    private int layoutId;
    private ItemClickedListener mItemClickedListener;

    public CustomSpinnerAdapter(Context context, List<?> models, int layoutId, ItemClickedListener itemClickedListener) {
        mContext = context;
        this.models = models;
        this.layoutId = layoutId;
        mItemClickedListener = itemClickedListener;
    }

    @Override
    public int getCount() {

        if(Utilities.isListEmpty(models))
            return 0;
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        if(Utilities.isListEmpty(models))
            return null;

        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View spinView;
        if (convertView == null) {
            spinView = LayoutInflater.from(mContext).inflate(layoutId, null);
        } else {
            spinView = convertView;
        }
        mItemClickedListener.onViewBound(spinView, models.get(position), position);
        return spinView;
    }

    public interface ItemClickedListener {
        void onViewBound(View view, Object object, int position);
    }
}
