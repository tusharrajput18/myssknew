package in.co.vsys.myssksamaj.adapterAdvertisement;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.interfaces.CallableAdvertisetypeChange1;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisemtnTypeList;


public class AdvertisemtnTypeAdapter1 extends RecyclerView.Adapter<AdvertisemtnTypeAdapter1.AdvertisementTypeViewHolder> {
    ArrayList<AdvertisemtnTypeList> advertisemtnTypeLists;
    Context context;
    private CallableAdvertisetypeChange1 change2;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;


    public AdvertisemtnTypeAdapter1(Context context, ArrayList<AdvertisemtnTypeList> advertisemtnTypeLists, CallableAdvertisetypeChange1 change1) {
        this.context = context;
        this.advertisemtnTypeLists=advertisemtnTypeLists;
        this.change2=change1;
    }


    @NonNull
    @Override
    public AdvertisementTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_news_type_row, viewGroup, false);

        return new AdvertisementTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdvertisementTypeViewHolder advertisementTypeViewHolder, final int i) {

       // sizeListViewHolder.changeToSelect(selectedPos == i ? Color.parseColor("#ca3854") : Color.BLACK);
        AdvertisemtnTypeList model = advertisemtnTypeLists.get(i);
        advertisementTypeViewHolder.tv_size.setText(model.getAdvertisementType());
        final int sdk = android.os.Build.VERSION.SDK_INT;

        if (checkedPosition == -1) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                advertisementTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white) );
                advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
            } else {
                advertisementTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white));
                advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
            }
            //sizeListViewHolder.tv_size.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            if (checkedPosition == i) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    advertisementTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1) );
                    advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    advertisementTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1));
                    advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                }
            } else {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    advertisementTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white) );
                    advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
                } else {
                    advertisementTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white));
                    advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
                }
            }
        }


        advertisementTypeViewHolder.tv_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    advertisementTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1) );
                    advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    advertisementTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1));
                    advertisementTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                }

                if (checkedPosition != i) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = i;
                }
                AdvertisemtnTypeList model = advertisemtnTypeLists.get(checkedPosition);
                change2.getAdvertismentType(model.getAdvertisementTypeId(),model.getAdvertisementType());

            }
        });


    }

    @Override
    public int getItemCount() {
        return advertisemtnTypeLists.size();
    }



    public class AdvertisementTypeViewHolder extends RecyclerView.ViewHolder {

        TextView tv_size;
        LinearLayout background;

        public AdvertisementTypeViewHolder(@NonNull final View itemView) {
            super(itemView);

            tv_size = (TextView) itemView.findViewById(R.id.tv_size);


        }



    }


}

