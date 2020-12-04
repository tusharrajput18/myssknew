package in.co.vsys.myssksamaj.fragment_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsHomeFragment extends Fragment {

    private static final String TAG = NewsHomeFragment.class.getSimpleName();
    private RecyclerView recycler_news_main;
    private ProgressBar news_Home_progressBar;


    public NewsHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_home, container, false);

        ((NewsDashboardActivity) getActivity()).getSupportActionBar().setTitle("News Directory");



    /*    news_Home_progressBar=(ProgressBar) view.findViewById(R.id.news_Home_progressBar);

        recycler_news_main=(RecyclerView)view.findViewById(R.id.recycler_news_main);
        recycler_news_main.setHasFixedSize(true);
        recycler_news_main.setLayoutManager(new GridLayoutManager(getActivity(),3));*/

        return view;
    }

   /* private void loadBusinesMainDirectory() {
        businessHome_progressBar.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllBusinessType> call = service.getAllBusinessImages();
        call.enqueue(new Callback<SellectAllBusinessType>() {
            @Override
            public void onResponse(Call<SellectAllBusinessType> call, Response<SellectAllBusinessType> response) {
                businessHome_progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    arrayList=response.body().getBusinessModulesList();
                    if (arrayList.size()>0){
                        recycler_businessmain.setVisibility(View.VISIBLE);
                        SellectAllBusinessAdapter sellectAllBusinessAdapter=new SellectAllBusinessAdapter(getActivity(),arrayList);
                        recycler_businessmain.setAdapter(sellectAllBusinessAdapter);

                    }
                    else {
                        recycler_businessmain.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(getActivity(), "Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllBusinessType> call, Throwable t) {
                businessHome_progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });




    }*/

    /*private void loadImages() {

        for (int i = 0; i < IMAGES.length; i++)
            imagesArray.add(IMAGES[i]);


        mViewPager.setAdapter(new BusinessSliderAdapter(getActivity(), imagesArray));
        //  mIndicator.setRadius(5 * density);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == imagesArray.size()) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

    }*/

}
