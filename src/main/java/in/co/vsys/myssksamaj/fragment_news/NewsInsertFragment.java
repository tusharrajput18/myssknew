package in.co.vsys.myssksamaj.fragment_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities_news.NewsDashboardActivity;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsInsertFragment extends Fragment {

    private static final String TAG = NewsInsertFragment.class.getSimpleName();



    public NewsInsertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_insert, container, false);

        ((NewsDashboardActivity) getActivity()).getSupportActionBar().setTitle("Insert News ");






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



}
