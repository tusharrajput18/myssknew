package in.co.vsys.myssksamaj.adapterNews;

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
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.interfaces.CallableNewstypeChange1;
import in.co.vsys.myssksamaj.newsmodels.AllNewsTypeModel;
import retrofit2.Callback;


public class NewsType_Adapter extends RecyclerView.Adapter<NewsType_Adapter.NewsTypeViewHolder> {
    ArrayList<AllNewsTypeModel.AllNewsType> newsTypeArrayList;
    Context context;
    private CallableNewstypeChange1 change2;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;

    public NewsType_Adapter(Context context, ArrayList<AllNewsTypeModel.AllNewsType> newsTypeArrayList,  CallableNewstypeChange1 change2) {

        this.context = context;
        this.newsTypeArrayList=newsTypeArrayList;
        this.change2=change2;
    }


    @NonNull
    @Override
    public NewsTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_news_type_row, viewGroup, false);

        return new NewsTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsTypeViewHolder newsTypeViewHolder, final int i) {

       // sizeListViewHolder.changeToSelect(selectedPos == i ? Color.parseColor("#ca3854") : Color.BLACK);
        AllNewsTypeModel.AllNewsType model = newsTypeArrayList.get(i);
        newsTypeViewHolder.tv_size.setText(model.getNewsType());
        final int sdk = android.os.Build.VERSION.SDK_INT;

        if (checkedPosition == -1) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                newsTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white) );
                newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
            } else {
                newsTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white));
                newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
            }
            //sizeListViewHolder.tv_size.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            if (checkedPosition == i) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    newsTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1) );
                    newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    newsTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1));
                    newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                }
            } else {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    newsTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white) );
                    newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
                } else {
                    newsTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white));
                    newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#000000"));
                }
            }
        }


        newsTypeViewHolder.tv_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    newsTypeViewHolder.tv_size.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1) );
                    newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    newsTypeViewHolder.tv_size.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_oval_white1));
                    newsTypeViewHolder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
                }

                if (checkedPosition != i) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = i;
                }
                AllNewsTypeModel.AllNewsType model = newsTypeArrayList.get(checkedPosition);
                change2.getNewsType(model.getNewsTypeId(),model.getNewsType());

            }
        });


    }

    @Override
    public int getItemCount() {
        return newsTypeArrayList.size();
    }



    public class NewsTypeViewHolder extends RecyclerView.ViewHolder {

        TextView tv_size;
        LinearLayout background;

        public NewsTypeViewHolder(@NonNull final View itemView) {
            super(itemView);

            tv_size = (TextView) itemView.findViewById(R.id.tv_size);


        }



    }


}

