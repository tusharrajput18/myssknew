package in.co.vsys.myssksamaj.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.PagerModel;
import in.co.vsys.myssksamaj.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jack on 02/01/2018.
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<PagerModel> list;
    private LayoutInflater inflater;

    public ImageViewPagerAdapter(Context mContext, List<PagerModel> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = ((Activity) mContext).getLayoutInflater();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.pager_layout, container, false);

        PhotoView imageView = (PhotoView) view.findViewById(R.id.imagePagerView);

        view.setTag(position);

        ((ViewPager) container).addView(view);

        PagerModel pagerModel = list.get(position);

        if (pagerModel.getImageUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(imageView);
        } else {

            Picasso.get()
                    .load(pagerModel.getImageUrl())
                    .placeholder(R.drawable.img_preview)
                    .into(imageView);
        }

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView((View) object);
    }
}
