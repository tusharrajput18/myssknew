package in.co.vsys.myssksamaj.fragmentsBusines;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.adapter.BusinessSliderAdapter;
import in.co.vsys.myssksamaj.businessAdapter.SellectAllBusinessAdapter;
import in.co.vsys.myssksamaj.business_activity.BusinessListActivity;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;
import in.co.vsys.myssksamaj.fragments.BusinessSearchFragment;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessHomeFragment extends Fragment {

    public static final String TAG = BusinessHomeFragment.class.getSimpleName();
    private ViewPager mViewPager, tabViewPager;
    private TabLayout mTabLayout;
    private static int currentPage = 0;
    private static int numOfPages = 0;
    private static final Integer[] IMAGES = {R.drawable.slider};
    private ArrayList<Integer> imagesArray = new ArrayList<>();

    private RecyclerView recycler_businessmain;
    private ProgressBar businessHome_progressBar;
    private ArrayList<SellectAllBusinessType.BusinessModules> arrayList;
    private Toolbar business_main_toolbar;
    EditText text_search_business;
    Button btn_search;


   /* private GestureDetector gestureDetector;
    private ClickListener clickListener;*/

    public BusinessHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_home, container, false);
       /* business_main_toolbar=(Toolbar)view.findViewById(R.id.business_main_toolbar);*/

      /*  setHasOptionsMenu(true);
        ((BusinessDashboardActivity) getActivity()).setSupportActionBar(business_main_toolbar);*/
        ((BusinessDashboardActivity) getActivity()).getSupportActionBar().setTitle("Business Directory");

        businessHome_progressBar=(ProgressBar) view.findViewById(R.id.businessHome_progressBar);

        text_search_business =(EditText)view.findViewById(R.id.text_search_business);

        btn_search=(Button)view.findViewById(R.id.btn_search);

        text_search_business.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0){
                    btn_search.setVisibility(View.VISIBLE);
                }else if(text_search_business.getText().toString().isEmpty()) {
                    btn_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidate()){
                    Intent intent = new Intent(getActivity(), BusinessListActivity.class);
                    intent.putExtra("prodname", text_search_business.getText().toString());
                    intent.putExtra("fromactivityname","dashboard");
                    startActivity(intent);
                    text_search_business.setText("");
                }


             /*   FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                BusinessSearchFragment businessSearchFragment=new BusinessSearchFragment();
                transaction.replace(R.id.business_home_frag,businessSearchFragment);
                //transaction.replace(R.id.nav_business_layout, select);
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("tag");
                transaction.commit();*/
            }
        });

        recycler_businessmain=(RecyclerView)view.findViewById(R.id.recycler_businessmain);
        recycler_businessmain.setHasFixedSize(true);
        recycler_businessmain.setLayoutManager(new GridLayoutManager(getActivity(),3));

        arrayList=new ArrayList<>();

        loadBusinesMainDirectory();

      /*  recycler_businessmain.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                    clickListener.onClick(child, rv.getChildAdapterPosition(child));

                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            public interface ClickListener {

                void onClick(View view, int position);

                void onLongClick(View view, int position);
            }


        });*/


       /* mViewPager = view.findViewById(R.id.business_pager);
        mTabLayout = view.findViewById(R.id.tabs_businessDash);
        tabViewPager = view.findViewById(R.id.viewPager_businessTab);

        tabViewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(tabViewPager);

        loadImages();*/
        //mGridView.setAdapter(new ());

        return view;
    }

    private boolean isValidate() {
        if (text_search_business.getText().toString().length() < 1) {
            Toast.makeText(getActivity(), "Invalid search..", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void loadBusinesMainDirectory() {
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




    }

    private void loadImages() {

        for (int i = 0; i < IMAGES.length; i++)
            imagesArray.add(IMAGES[i]);


       // mViewPager.setAdapter(new BusinessSliderAdapter(getActivity(), imagesArray));
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

    }

}
