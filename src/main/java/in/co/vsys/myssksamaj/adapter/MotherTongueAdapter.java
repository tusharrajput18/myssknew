package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.data_models.MotherTongueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 02/12/2017.
 */
public class MotherTongueAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<MotherTongueModel> list;
    private LayoutInflater inflater = null;
    private List<MotherTongueModel> filterList;

    public MotherTongueAdapter(@NonNull Context context, @NonNull List<MotherTongueModel> objects) {
        this.mContext = context;
        this.list = objects;
        this.filterList = objects;

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

        MotherTongueModel item = this.filterList.get(position);
        itemHolder.tv_title.setText(item.getMothertongue());

        return view;
    }

    private static class DrawerItemHolder {
        CheckedTextView tv_title;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {

                    filterList = list;
                } else {

                    List<MotherTongueModel> filteredList = new ArrayList<>();
                    for (MotherTongueModel item : list) {
                        if (item.getMothertongue().toLowerCase().contains(charString)) {
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

                filterList = (List<MotherTongueModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}