package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijeet on 3/10/15.
 */
public class CustomPagerAdapter extends PagerAdapter {

    private Context context;
    private List mModels = new ArrayList();
    private ItemClickedListener itemClickedListener;
    private int mLayoutId;

    public CustomPagerAdapter(Context context, List<?> models, int layoutId,
                              ItemClickedListener itemClickedListener) {
        this.context = context;
        this.mModels.addAll(models);
        this.mLayoutId = layoutId;
        this.itemClickedListener = itemClickedListener;
    }

    public List getModels() {
        return mModels;
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;
        try {
            view = LayoutInflater.from(container.getContext())
                    .inflate(mLayoutId, container, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickedListener.onItemClicked(view, mModels.get(position), position);
                }
            });

            itemClickedListener.onViewBound(view, mModels.get(position), position);

            container.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void updateList(List<?> models){
        mModels.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
//        container.removeView((RelativeLayout) object);
        // container.removeView((View) object);
    }

    public interface ItemClickedListener {
        void onItemClicked(View view, Object object, int position);

        void onViewBound(View view, Object object, int position);
    }
}