package in.co.vsys.myssksamaj.adapter;

/**
 * Created by abhijeet on 3/10/15.
 */
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "CustomListAdapter";
    private List<?> models;
    private ItemClickedListener itemClickedListener;
    private int layoutId;
    private LinearLayoutManager layoutManager;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        public DataObjectHolder(final View itemView, final List<?> models, final ItemClickedListener itemClickedListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemClickedListener.onItemClicked(v, models.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public CustomListAdapter(Context context, RecyclerView recyclerView, List<?> models, int layoutId,
                             ItemClickedListener itemClickedListener) {
        this.models = models;
        this.layoutId = layoutId;
        this.itemClickedListener = itemClickedListener;
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        try {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, models, itemClickedListener);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        itemClickedListener.onViewBound(holder.itemView, models.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (models == null || models.size() == 0)
            return 0;
        return models.size();
    }

    public void updateList(List newModels) {
        models = new ArrayList<>();

        models.addAll(newModels);
        notifyDataSetChanged();
    }

    public interface ItemClickedListener {
        void onViewBound(View view, Object object, int position);

        void onItemClicked(View view, Object object, int position);
    }
}