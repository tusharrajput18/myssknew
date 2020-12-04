package in.co.vsys.myssksamaj.businessAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.businessmodels.SellectAllSubCategoryType;

public class BusinessServiceAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> subCateLists_arraylist;
    private ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> filterList;
    private LayoutInflater inflater = null;
    private SparseBooleanArray mSelectedItemsIds;

    public BusinessServiceAdapter(Context activity, ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> sellectAllServicesArrayList) {

        this.mContext = activity;
        this.subCateLists_arraylist=sellectAllServicesArrayList;
        this.filterList = sellectAllServicesArrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItemsIds = new SparseBooleanArray();

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final DrawerItemHolder itemHolder;
        View view = convertView;

        if (view == null) {

            view = inflater.inflate(R.layout.custom_subcategory_list, parent, false);
            itemHolder = new DrawerItemHolder();

            itemHolder.tv_title = view.findViewById(R.id.tv_custom_subcatName);
            itemHolder.cusotm_subcat_check = view.findViewById(R.id.cusotm_subcat_check);

            view.setTag(itemHolder);
        } else {

            itemHolder = (DrawerItemHolder) view.getTag();
        }

        final SelectServicesUsingSubCategoryId.SellectAllServices item = this.filterList.get(position);

        itemHolder.tv_title.setText(item.getName());

        if (item.isCheck()) {
            itemHolder.cusotm_subcat_check.setChecked(true);
        } else {
            itemHolder.cusotm_subcat_check.setChecked(false);
        }

        itemHolder.cusotm_subcat_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox(position, !mSelectedItemsIds.get(position),filterList);
            }
        });

        return view;
    }

    public void checkCheckBox(int position, boolean value, ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> filterList) {
        if (value) {
            mSelectedItemsIds.put(position, true);
            filterList.get(position).setCheck(true);
        }
        else {
            mSelectedItemsIds.delete(position);
            filterList.get(position).setCheck(false);
        }

        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds1() {
        return mSelectedItemsIds;
    }



    private static class DrawerItemHolder {

        TextView tv_title;
        CheckBox cusotm_subcat_check;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {

                    filterList = subCateLists_arraylist;
                } else {

                    ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> filteredList = new ArrayList<>();

                    for (SelectServicesUsingSubCategoryId.SellectAllServices item : subCateLists_arraylist) {

                        if (item.getName().toLowerCase().contains(charString)) {

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

                filterList = (ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}
