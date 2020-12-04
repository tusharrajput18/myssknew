package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.interfaces.FilterListener;

/**
 * @author abhijeetjadhav
 */
public class CustomFilterAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<?> models = new ArrayList<>();
    private List<?> mFilterList;
    private FilterListener filterListener;

    public CustomFilterAdapter(Context mContext, List<?> models, FilterListener filterListener) {
        this.mContext = mContext;
        this.models = models;
        this.filterListener = filterListener;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        filterListener.onViewBound(convertView, mFilterList.get(position), position);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String charString = constraint.toString();
//                if (charString.isEmpty()) {
//                    mFilterList.clear();
//                    mFilterList.addAll(models);
//                } else {
//
//                    ArrayList<DrawerItem> filterList = new ArrayList<>();
//                    for (Object object : mLIst) {
//
//                        if (drawerItem.getMotherTongueName().toLowerCase().contains(charString)) {
//
//                            filterList.add(drawerItem);
//                        }
//                    }
//
//                    mFilterList = filterList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilterList;
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//                mFilterList = (ArrayList<DrawerItem>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
}
