package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.interfaces.DisplaySelectedValue;
import in.co.vsys.myssksamaj.model.DrawerItem;

/**
 * Created by Jack on 03-Feb-18.
 */

public class MTPopAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<DrawerItem> mLIst;
    private ArrayList<DrawerItem> mFilterList;
    private SharedPreferences mPreference;


    public MTPopAdapter(@NonNull Context context, @NonNull ArrayList<DrawerItem> objects, DisplaySelectedValue displaySelectedValue) {

        this.mContext = context;
        this.mLIst = objects;
        this.mFilterList = objects;
        inflater = LayoutInflater.from(context);

        mPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public int getCount() {
        return mFilterList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFilterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_checkbox_list, parent, false);

            viewHolder.textView = convertView.findViewById(R.id.tv_pop_text);
            viewHolder.checkBox = convertView.findViewById(R.id.pop_checkbox);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final DrawerItem item = mFilterList.get(position);

        viewHolder.textView.setText(item.getMotherTongueName());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mFilterList.get(position).setSelected(buttonView.isChecked());
            }
        });

        viewHolder.checkBox.setChecked(mFilterList.get(position).isSelected());

        return convertView;

    }

    private class ViewHolder {

        private AppCompatTextView textView;
        private AppCompatCheckBox checkBox;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {

                    mFilterList = mLIst;

                } else {

                    ArrayList<DrawerItem> filterList = new ArrayList<>();

                    for (DrawerItem drawerItem : mLIst) {

                        if (drawerItem.getMotherTongueName().toLowerCase().contains(charString)) {

                            filterList.add(drawerItem);
                        }
                    }

                    mFilterList = filterList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mFilterList = (ArrayList<DrawerItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public StringBuilder getSelectedName() {

        StringBuilder stringBuilder = new StringBuilder();

        String prefix = "";

        for (DrawerItem model : mFilterList) {

            if (model.isSelected()) {

                //   stringBuilder.append(model.getName());

                stringBuilder.append(prefix);

                prefix = ",";

                //stringBuilder.append(",");

                stringBuilder.append(model.getMotherTongueName());

            }
        }

        return stringBuilder;
    }

    public StringBuilder getSelectedIds() {

        StringBuilder stringBuilder = new StringBuilder();

        String prefix = "";

        for (DrawerItem model : mFilterList) {

            if (model.isSelected()) {

                //   stringBuilder.append(model.getName());

                stringBuilder.append(prefix);

                prefix = ",";

                //stringBuilder.append(",");

                stringBuilder.append(model.getMotherTongueId());

            }
        }

        return stringBuilder;
    }
}
