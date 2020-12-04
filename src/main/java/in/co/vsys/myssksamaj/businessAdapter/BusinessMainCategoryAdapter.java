package in.co.vsys.myssksamaj.businessAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;
import in.co.vsys.myssksamaj.businessmodels.SellectAllState;

public class BusinessMainCategoryAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<SellectAllBusinessType.BusinessModules> maincat_arraylist;
    private ArrayList<SellectAllBusinessType.BusinessModules> filterList;
    private LayoutInflater inflater = null;



    public BusinessMainCategoryAdapter(Context activity, ArrayList<SellectAllBusinessType.BusinessModules> maincat_arraylist) {
        this.mContext = activity;
        this.maincat_arraylist=maincat_arraylist;
        this.filterList = maincat_arraylist;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DrawerItemHolder itemHolder;
        View view = convertView;

        if (view == null) {

            view = inflater.inflate(R.layout.custom_navigation, parent, false);
            itemHolder = new DrawerItemHolder();

            itemHolder.tv_title = view
                    .findViewById(R.id.drawer_itemName);

            view.setTag(itemHolder);
        } else {

            itemHolder = (DrawerItemHolder) view.getTag();
        }

        SellectAllBusinessType.BusinessModules item = this.filterList.get(position);

        itemHolder.tv_title.setText(item.getBusinessType());

        return view;
    }

    private static class DrawerItemHolder {

        TextView tv_title;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {

                    filterList = maincat_arraylist;
                } else {

                    ArrayList<SellectAllBusinessType.BusinessModules> filteredList = new ArrayList<>();

                    for (SellectAllBusinessType.BusinessModules item : maincat_arraylist) {

                        if (item.getBusinessType().toLowerCase().contains(charString)) {

                            filteredList.add(item);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();

                filterResults.values = filterList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filterList = (ArrayList<SellectAllBusinessType.BusinessModules>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}
