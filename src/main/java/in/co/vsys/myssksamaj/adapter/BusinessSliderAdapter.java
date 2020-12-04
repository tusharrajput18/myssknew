package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.MainActivity;
import in.co.vsys.myssksamaj.model.SliderModel;

/**
 * Created by Vysys on 27/03/2018.
 */

public class BusinessSliderAdapter extends PagerAdapter {

    private LayoutInflater inflater = null;
    private ArrayList<Integer> images;
    private Context mContext;
    List<SliderModel.SliderModelList> sliderModelListList;

    /*public BusinessSliderAdapter(Context mContext, ArrayList<Integer> images) {
        this.mContext = mContext;
        this.images = images;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    public BusinessSliderAdapter(MainActivity mContext, List<SliderModel.SliderModelList> sliderModelListList) {
        this.mContext = mContext;
        this.sliderModelListList = sliderModelListList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        //Toast.makeText(mContext, ""+sliderModelListList.get(0).getImage(), Toast.LENGTH_SHORT).show();
        return sliderModelListList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide_image, view, false);

        final SliderModel.SliderModelList modelList=sliderModelListList.get(position);
        AppCompatImageView myImage = (AppCompatImageView) myImageLayout
                .findViewById(R.id.slide_image1);
/*
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, ""+modelList.getImage(), Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(mContext, ""+modelList.getImageName(), Toast.LENGTH_SHORT).show();*/

if(modelList.getImage().isEmpty()){
    Picasso.get()
            .load(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(myImage);
}else {
    Log.d("mytag", "instantiateItem: "+modelList.getImage());

    Picasso.get()
            .load(modelList.getImage())
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(myImage);
}



        //myImage.setImageResource(images.get(position));


        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
}
