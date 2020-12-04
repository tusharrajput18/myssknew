package in.co.vsys.myssksamaj.fragmentsBusines;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.businessAdapter.BusinessMainCategoryAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessServiceAdapter;
import in.co.vsys.myssksamaj.businessAdapter.BusinessSubCategoryAdapter;
import in.co.vsys.myssksamaj.businessAdapter.ImagesAdapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;
import in.co.vsys.myssksamaj.businessmodels.SellectAllSubCategoryType;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertBusinessFragment2 extends Fragment {

    private Context mContext;
    public static final String TAG = InsertBusinessFragment2.class.getSimpleName();
    LinearLayout ll_selectimage;
    View view;
    ArrayList<String> images = new ArrayList<String>();
    private final int ACTIVITY_CAMERA = 0;
    private final int ACTIVITY_CHOOSE_FILE = 1;
    ImagesAdapter imagesAdapter;
    private String imgPath;
    RecyclerView rvData;
    TextView tv_continue;



    BusinessMainCategoryAdapter businessMainCategoryAdapter;
    BusinessSubCategoryAdapter businessSubCategoryAdapter;
    BusinessServiceAdapter businessServiceAdapter;
    TextView tv_business_category, tv_business_subcategory, tv_business_product;
    ProgressBar progressbar_business_frag2;
    ArrayList<SellectAllBusinessType.BusinessModules> maincat_arraylist;
    ArrayList<SellectAllSubCategoryType.SubCateList> subCateLists_arraylist;
    ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> sellectAllServicesArrayList;

    TextInputEditText et_aboutcompany,et_aboutproductservice;
    String maincategoryid;
    String subcategoryid;
    String serviceses_id;

    String business_name, mobile_no, whatsapp_no, email_id, contact_personName;
    String abutcompany="",discription="";


    public InsertBusinessFragment2() {
        // Required empty public constructortv_business_category
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((BusinessDashboardActivity) getActivity()).setActionBarTitle("Insert Business");

        view = inflater.inflate(R.layout.fragment_insert_business_next2, container, false);
        business_name = getArguments().getString("business_Name");
        mobile_no = getArguments().getString("mobile_No");
        whatsapp_no = getArguments().getString("whatsapp_No");
        email_id = getArguments().getString("business_Email");
        contact_personName = getArguments().getString("contactPerson_name");
        progressbar_business_frag2 = (ProgressBar) view.findViewById(R.id.progressbar_business_frag2);

        if (ConnectionHelper.networkConnectivity(mContext)) {

           // loadMaincategory();

            //loadBusinessType();
        } else {

            Toast.makeText(getActivity(), "No internet connection ", Toast.LENGTH_SHORT).show();
        }


        init();


        return view;
    }

    private void init() {
        maincat_arraylist = new ArrayList<>();
        subCateLists_arraylist = new ArrayList<>();
        sellectAllServicesArrayList = new ArrayList<>();


        ll_selectimage = (LinearLayout) view.findViewById(R.id.ll_selectimage);
        rvData = (RecyclerView) view.findViewById(R.id.rvData);

        et_aboutcompany=(TextInputEditText)view.findViewById(R.id.et_aboutcompany);
        et_aboutproductservice=(TextInputEditText)view.findViewById(R.id.et_aboutproductservice);

        tv_continue = (TextView) view.findViewById(R.id.tv_continue);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abutcompany=et_aboutcompany.getText().toString();
                discription=et_aboutproductservice.getText().toString();

                Toast.makeText(mContext, ""+business_name+"\n"+mobile_no+"\n"+whatsapp_no+"\n"+email_id+"\n"+contact_personName, Toast.LENGTH_SHORT).show();
                if(validateData()){
                    InsertBusinessFragment3 fragment3 = new InsertBusinessFragment3(images);
                    Bundle args = new Bundle();
                    args.putString("business_Name", business_name);
                    args.putString("mobile_No", mobile_no);
                    args.putString("whatsapp_No", whatsapp_no);
                    args.putString("business_Email", email_id);
                    args.putString("contactPerson_name", contact_personName);
                    args.putString("catid", maincategoryid);
                    args.putString("subcatids", subcategoryid);
                    args.putString("products", serviceses_id);
                    args.putString("abutcompany", abutcompany);
                    args.putString("discription", discription);
                    fragment3.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_business_layout, fragment3);
                    fragmentTransaction.commit();
                }
            }

        });

        ll_selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });

        tv_business_category = (TextView) view.findViewById(R.id.tv_business_category);
        tv_business_subcategory = (TextView) view.findViewById(R.id.tv_business_subcategory);
        tv_business_product = (TextView) view.findViewById(R.id.tv_business_product);

        tv_business_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loadMaincategory();
            }
        });

        tv_business_subcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubcategoryUsingIds(maincategoryid);

            }
        });

        tv_business_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loadServicesUsingSubCatId(subcategoryid);
            }
        });


    }

    private boolean validateData() {
        if(maincategoryid.isEmpty()){
            Toast.makeText(mContext, "Invalid Information", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(subcategoryid.isEmpty()){
            Toast.makeText(mContext, "Invalid Information", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(serviceses_id.isEmpty()){
            Toast.makeText(mContext, "Invalid Information", Toast.LENGTH_SHORT).show();
            return false;
        }
      /*  if (et_aboutcompany.getText().toString().isEmpty()) {
            et_aboutcompany.setError("Invalid Information");
            Toast.makeText(getActivity(), "Invalid Information", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_aboutproductservice.getText().toString().length()<10) {
            et_aboutproductservice.setError("Invalid Information");
            Toast.makeText(getActivity(), "Invalid Information", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }


    private void loadMaincategory() {
       // progressbar_business_frag2 = new ProgressBar(getActivity());
        progressbar_business_frag2.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllBusinessType> call = service.getAllBusinessImages();
        call.enqueue(new Callback<SellectAllBusinessType>() {
            @Override
            public void onResponse(Call<SellectAllBusinessType> call, Response<SellectAllBusinessType> response) {
                progressbar_business_frag2.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    maincat_arraylist = response.body().getBusinessModulesList();
                    if (maincat_arraylist.size() > 0) {
                        businessMainCategoryAdapter = new BusinessMainCategoryAdapter(getActivity(), maincat_arraylist);
                        displayMainCategoryAlert();
                    } else {

                    }
                } else {
                    Toast.makeText(getActivity(), "Response Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllBusinessType> call, Throwable t) {
               // progressbar_business_frag2.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void displayMainCategoryAlert() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        mSearchView.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();




        mListView.setAdapter(businessMainCategoryAdapter);
        businessMainCategoryAdapter.notifyDataSetChanged();

        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllBusinessType.BusinessModules model = (SellectAllBusinessType.BusinessModules) parent.getItemAtPosition(position);
                maincategoryid = model.getBusinessTypeId();
                tv_business_category.setText(model.getBusinessType());
                dialog.dismiss();
                // loadBusinesCity(stateid);

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessMainCategoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void loadSubcategoryUsingIds(String maincategoryid) {
        Log.d(TAG, "loadSubcategoryUsingIds: " + maincategoryid);
       // progressbar_business_frag2 = new ProgressBar(getActivity());
        progressbar_business_frag2.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllSubCategoryType> call = service.getAllSubCategory(maincategoryid);
        call.enqueue(new Callback<SellectAllSubCategoryType>() {
            @Override
            public void onResponse(Call<SellectAllSubCategoryType> call, Response<SellectAllSubCategoryType> response) {
                progressbar_business_frag2.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    subCateLists_arraylist = response.body().getSubCateLists();
                    if (subCateLists_arraylist.size() > 0) {

                        businessSubCategoryAdapter = new BusinessSubCategoryAdapter(getActivity(), subCateLists_arraylist);
                        displaySubCategoryAlert();
                    } else {

                        Toast.makeText(getActivity(), "No Data Found...", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Response Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllSubCategoryType> call, Throwable t) {
                progressbar_business_frag2.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void displaySubCategoryAlert() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub = alertView.findViewById(R.id.btn_ok_sub);
        mSearchView.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessSubCategoryAdapter);
        businessSubCategoryAdapter.notifyDataSetChanged();

        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
/*
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                businessSubCategoryAdapter.setCheckBox(i);
            }
        });*/

        btn_ok_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = businessSubCategoryAdapter.getSelectedIds();
                if (selectedRows.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder stringBuilder2 = new StringBuilder();
                   // JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < selectedRows.size(); i++) {
                        if (selectedRows.valueAt(i)) {
                           /* JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("studentId", subCateLists_arraylist.get(selectedRows.keyAt(i)).getId());
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            String selectedRowLabel = subCateLists_arraylist.get(selectedRows.keyAt(i)).getName();
                            String selectedRowLabe2 = subCateLists_arraylist.get(selectedRows.keyAt(i)).getId();
                            stringBuilder.append(selectedRowLabel + "\n");
                            stringBuilder2.append(selectedRowLabe2 + ",");
                        }
                    }

                    subcategoryid=stringBuilder2.toString();
                    //loadServicesUsingSubCatId(stringBuilder2.toString());
                    //Toast.makeText(getActivity(), "Selected Rows\n" + jsonArray.toString(), Toast.LENGTH_SHORT).show();
                    tv_business_subcategory.setText(stringBuilder.toString());
                    dialog.dismiss();
                }
            }
        });

       /* final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();*/

       /* ArrayList<SellectAllSubCategoryType.SubCateList> currentSelectedItems = new ArrayList<>();



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllSubCategoryType.SubCateList model = (SellectAllSubCategoryType.SubCateList) parent.getItemAtPosition(position);
                subcategoryid = model.getBusinesscategoryid();
                tv_business_subcategory.setText(model.getName());
                dialog.dismiss();
                // loadBusinesCity(stateid);

            }
        });*/


       /* SellectAllSubCategoryType.SubCateList model = (SellectAllSubCategoryType.SubCateList) parent.getItemAtPosition(position);
        subcategoryid = model.getBusinesscategoryid();
        tv_business_subcategory.setText(model.getName());
        dialog.dismiss();*/

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessSubCategoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void loadServicesUsingSubCatId(String string) {
      //  progressbar_business_frag2 = new ProgressBar(getActivity());
        progressbar_business_frag2.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SelectServicesUsingSubCategoryId> call = service.getAllServiceUsingSubCategoryIds(string);
        call.enqueue(new Callback<SelectServicesUsingSubCategoryId>() {
            @Override
            public void onResponse(Call<SelectServicesUsingSubCategoryId> call, Response<SelectServicesUsingSubCategoryId> response) {
                progressbar_business_frag2.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    sellectAllServicesArrayList = response.body().getAllServicesArrayList();
                    if (sellectAllServicesArrayList.size() > 0) {
                        businessServiceAdapter = new BusinessServiceAdapter(getActivity(), sellectAllServicesArrayList);

                        displayServicesusingSubCategoryAlert();
                    } else {
                        Toast.makeText(getActivity(), "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Response Error...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SelectServicesUsingSubCategoryId> call, Throwable t) {
                progressbar_business_frag2.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Error.......", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void displayServicesusingSubCategoryAlert() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.row_spinner_popup, null);
        builder.setView(alertView);

        final ListView mListView = alertView.findViewById(R.id.AlertSearchList);
        SearchView mSearchView = alertView.findViewById(R.id.searchView);
        Button btn_ok_sub = alertView.findViewById(R.id.btn_ok_sub);
        mSearchView.setVisibility(View.GONE);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        mListView.setAdapter(businessServiceAdapter);
        //  businessServiceAdapter.notifyDataSetChanged();

        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
/*
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                businessSubCategoryAdapter.setCheckBox(i);
            }
        });*/

        btn_ok_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = businessServiceAdapter.getSelectedIds1();
                if (selectedRows.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder stringBuilder1 = new StringBuilder();
                    for (int i = 0; i < selectedRows.size(); i++) {
                        if (selectedRows.valueAt(i)) {
                            String selectedRowLabel = sellectAllServicesArrayList.get(selectedRows.keyAt(i)).getName();
                            String selectedRowLabel2 = sellectAllServicesArrayList.get(selectedRows.keyAt(i)).getId();
                            stringBuilder.append(selectedRowLabel + ",");
                            stringBuilder1.append(selectedRowLabel2 + ",");
                        }
                    }
                    serviceses_id=stringBuilder1.toString();
                    tv_business_product.setText(stringBuilder.toString());
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });



       /* ArrayList<SellectAllSubCategoryType.SubCateList> currentSelectedItems = new ArrayList<>();



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SellectAllSubCategoryType.SubCateList model = (SellectAllSubCategoryType.SubCateList) parent.getItemAtPosition(position);
                subcategoryid = model.getBusinesscategoryid();
                tv_business_subcategory.setText(model.getName());
                dialog.dismiss();
                // loadBusinesCity(stateid);

            }
        });*/


       /* SellectAllSubCategoryType.SubCateList model = (SellectAllSubCategoryType.SubCateList) parent.getItemAtPosition(position);
        subcategoryid = model.getBusinesscategoryid();
        tv_business_subcategory.setText(model.getName());
        dialog.dismiss();*/

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessSubCategoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void selectimage() {
        if (images.size() >= 9) {
            Toast.makeText(mContext, "You can upload maximum 9 photos", Toast.LENGTH_SHORT).show();

        } else {
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(mContext);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {"Camera", "Gallery"};
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    //First checking if the app is already having the permission
                                    if (isReadStorageAllowed()) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (mContext.checkSelfPermission(Manifest.permission.CAMERA)
                                                    != PackageManager.PERMISSION_GRANTED) {

                                                requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                        ACTIVITY_CAMERA);
                                            } else {
                                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                startActivityForResult(intent, ACTIVITY_CAMERA);
                                            }
                                        } else {
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(intent, ACTIVITY_CAMERA);
                                        }
                                    } else {
                                        requestStoragePermission(ACTIVITY_CAMERA);
                                    }
                                    break;
                                case 1:
                                    //First checking if the app is already having the permission
                                    if (isReadStorageAllowed()) {
                                        //Existing the method with return
                                        Intent chooseFile;
                                        Intent galleryIntent;
                                        chooseFile = new Intent(Intent.ACTION_PICK,
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        chooseFile.setType("image/*");
                                        galleryIntent = Intent.createChooser(chooseFile, "Choose a file");
                                        startActivityForResult(galleryIntent, ACTIVITY_CHOOSE_FILE);
                                        return;
                                    } else {
                                        //If the app has not the permission then asking for the permission
                                        requestStoragePermission(ACTIVITY_CHOOSE_FILE);
                                    }
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        }

    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        else {
            //If permission is not granted returning false
            return false;
        }
    }

    //Requesting permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission(int requestCode) {
        if (shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, requestCode);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Checking the request code of our request
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(mContext, "Oops you just denied the permission",
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == ACTIVITY_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, ACTIVITY_CAMERA);
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(mContext, "Oops you just denied the permission",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Don't forget to check requestCode before continuing your job
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    imgPath = getRealPathFromURI(contentURI);
                    previewUploadFile(imgPath);
//                    filePath = new File(imgPath);
//                    ivProfile.setImageBitmap(bitmap);
//                    images.add(imgPath);
//                    imagepostAdAdapter = new PostAdAdapter(mContext, images, PostAdFragment.this);
//                    rvData.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//                    rvData.setAdapter(imagepostAdAdapter);
                    //addEvent(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == ACTIVITY_CAMERA) {
            if (data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgPath = saveImage(thumbnail);
                previewUploadFile(imgPath);
//            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void previewUploadFile(String filename) {
        try {
            String strPath = filename;
            if (!images.contains(strPath)) {

//                Log.d(TAG, "previewUploadFile: path " + strPath);
                if (images.size() > 9) {
                    Toast.makeText(mContext, "You can upload maximum 9 photos", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d(TAG, "previewUploadFile: "+strPath);
                    images.add(strPath);
                }
                imagesAdapter = new ImagesAdapter(mContext, images);
                rvData.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                rvData.setAdapter(imagesAdapter);
            } else
                Toast.makeText(mContext, "File already selected!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String thePath = "no-path-found";
        Cursor cursor;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        cursor = mContext.getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return thePath;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File directory
                = new File(Environment.getExternalStorageDirectory()
                + "/" + getResources().getString(R.string.app_name));
        // have the object build the directory structure, if needed.
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            File f = new File(directory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(mContext,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d(String.valueOf(mContext), "saveImage: path " + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }




   /* private void loadBusinessType() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.business_type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "business type response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = null;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int bTypeId = object.getInt("businessTypeId");
                                    String bTypeName = object.getString("businessType");

                                    BusinessTypeModel typeModel = new BusinessTypeModel(bTypeId, bTypeName);

                                    businessTypeList.add(typeModel);

                                }

                                BusinessTypeAdapter adapter = new BusinessTypeAdapter(getActivity(), businessTypeList);
                                spinner_business_type.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "business type error" + error);
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }*/
}
