package in.co.vsys.myssksamaj.adapter;

/**
 * Created by abhijeet on 3/10/15.
 */
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CustomHorizontalListAdapter extends RecyclerView.Adapter<CustomHorizontalListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "EntitiesAdapter";
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

    public CustomHorizontalListAdapter(Context context, RecyclerView recyclerView, List<?> models, int layoutId,
                                       boolean addDecoration, ItemClickedListener itemClickedListener) {
        try {
            this.models = models;
            this.layoutId = layoutId;
            this.itemClickedListener = itemClickedListener;
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            if (addDecoration) {

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, models, itemClickedListener);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        itemClickedListener.onViewBound(holder.itemView, models.get(position), position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface ItemClickedListener {
        void onItemClicked(View view, Object object, int position);

        void onViewBound(View view, Object object, int position);
    }
}