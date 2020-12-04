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
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businessmodels.SellectAllState;
import in.co.vsys.myssksamaj.model.StateModel;

public class BusinessStateAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<SellectAllState.AllStateList> stateLists_arraylist;
    private ArrayList<SellectAllState.AllStateList> filterList;
    private LayoutInflater inflater = null;

    public BusinessStateAdapter(Context activity, ArrayList<SellectAllState.AllStateList> stateLists_arraylist) {

        this.mContext = activity;
        this.stateLists_arraylist = stateLists_arraylist;
        this.filterList = stateLists_arraylist;

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

        SellectAllState.AllStateList item = this.filterList.get(position);

        itemHolder.tv_title.setText(item.getStatename());

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

                    filterList = stateLists_arraylist;
                } else {

                    ArrayList<SellectAllState.AllStateList> filteredList = new ArrayList<>();

                    for (SellectAllState.AllStateList item : stateLists_arraylist) {

                        if (item.getStatename().toLowerCase().contains(charString)) {

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

                filterList = (ArrayList<SellectAllState.AllStateList>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}
