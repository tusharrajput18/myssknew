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
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;
import in.co.vsys.myssksamaj.businessmodels.SellectAllSubCategoryType;
import in.co.vsys.myssksamaj.interfaces.CallableSubcCategoryChange;

public class BusinessSubCategoryAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<SellectAllSubCategoryType.SubCateList> subCateLists_arraylist;
    private ArrayList<SellectAllSubCategoryType.SubCateList> filterList;
    private LayoutInflater inflater = null;
    private boolean[] itemChecked;

    private SparseBooleanArray mSelectedItemsIds;


    public BusinessSubCategoryAdapter(Context activity, ArrayList<SellectAllSubCategoryType.SubCateList> subCateLists_arraylist) {

        this.mContext = activity;
        this.subCateLists_arraylist = subCateLists_arraylist;
        this.filterList = subCateLists_arraylist;
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

        final SellectAllSubCategoryType.SubCateList item = this.filterList.get(position);

        itemHolder.tv_title.setText(item.getName());

        if (item.isCheck()) {
            itemHolder.cusotm_subcat_check.setChecked(true);
        } else {
            itemHolder.cusotm_subcat_check.setChecked(false);
        }


        itemHolder.cusotm_subcat_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkCheckBox(position, !mSelectedItemsIds.get(position), filterList);
            }
        });


        itemHolder.cusotm_subcat_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


              /*  item.setCheck(b);
                notifyDataSetChanged();*/


                // change.GetData(position,String.valueOf(item.getId()),b);

            }
        });

        return view;
    }

    public void checkCheckBox(int position, boolean value, ArrayList<SellectAllSubCategoryType.SubCateList> filterList) {
        if (value) {
            mSelectedItemsIds.put(position, true);
            filterList.get(position).setCheck(true);
            notifyDataSetChanged();
        } else {
            mSelectedItemsIds.delete(position);
            filterList.get(position).setCheck(false);
            notifyDataSetChanged();

        }


        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public ArrayList<SellectAllSubCategoryType.SubCateList> getAllData() {
        return filterList;
    }
   /* public void setCheckBox(int position){
        //Update status of checkbox
        SellectAllSubCategoryType.SubCateList items = filterList.get(position);
        items.setCheck(!items.isCheck());
        notifyDataSetChanged();
    }*/

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

                    ArrayList<SellectAllSubCategoryType.SubCateList> filteredList = new ArrayList<>();

                    for (SellectAllSubCategoryType.SubCateList item : subCateLists_arraylist) {

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

                filterList = (ArrayList<SellectAllSubCategoryType.SubCateList>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}
