package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomSpinnerAdapter;
import in.co.vsys.myssksamaj.contracts.GetDistanceContract;
import in.co.vsys.myssksamaj.model.MarkerDataModel;
import in.co.vsys.myssksamaj.model.data_models.DistanceModel;
import in.co.vsys.myssksamaj.presenters.GetDistancePresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

//https://developers.google.com/maps/documentation/android-sdk/marker#info_windowshttps://developers.google.com/maps/documentation/android-sdk/marker#info_windows

public class MatriNearByActivity extends FragmentActivity implements OnMapReadyCallback, GetDistanceContract.GetDistanceView, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    private String userProfileUrl = "";
    private SharedPreferences mPreferences;
    private ArrayList<MarkerDataModel> markerList = new ArrayList<>();
    private static final String MEMBER_ID = "MemberId";
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";
    private static final String DISTANCE = "distance";
    private double lat, longitude;
    private String selectdistance;
    private int memberId;
    private String memberUniqueId, memberName;
    private static final String TAG = MatriNearByActivity.class.getSimpleName();
    private static final int PERMS_REQUEST_CODE = 124;
    private Hashtable<String, String> markerListWithImage;
    private Marker marker, redMarker;
    private Spinner spnDisplacement;
    private List<DistanceModel> distanceModelList = new ArrayList<>();
    private CustomSpinnerAdapter displacementSpnAdapter;
    private GetDistanceContract.GetDistanceOPS mGetDistancePresenter;
    private ProgressBar progressBar;

    private String gender;

    Marker mymarker;

    private android.location.LocationListener mLocationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {

                lat = location.getLatitude();
                longitude = location.getLongitude();

                getCurrentMemberMarker(lat, longitude);

                mLocationManager.removeUpdates(mLocationListener);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServicesAvailable()) {

            setContentView(R.layout.activity_matri_near_by);

            //Spinner select Displacement
            spnDisplacement = findViewById(R.id.spn_select_displacement);
            progressBar = findViewById(R.id.matri_near_by_me_progressbar);
            mGetDistancePresenter = new GetDistancePresenter(this);
            mGetDistancePresenter.getDistance();

            // Toolbar mToolbar = findViewById(R.id.toolbar_matri_nearBy);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.matri_map);

            mapFragment.getMapAsync(MatriNearByActivity.this);


            // mGoogleMap = mMapView.getMap();
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            spnDisplacement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    DistanceModel model = distanceModelList.get(i);
                    selectdistance = model.getDistance();
                    if (!Utilities.isListEmpty(markerList)) {
                        markerList.clear();
                    }

                    getNearByMember();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            findViewById(R.id.img_refresh_matri).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (hasPermission()) {
                        getNearByMember();
                    } else {
                        requestPermission();
                    }
                }
            });

            findViewById(R.id.btn_matriNearBY_backArrow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            memberId = mPreferences.getInt("memberId", 0);
            memberName = mPreferences.getString("nav_memberName", null);
            memberUniqueId = mPreferences.getString("nav_uniqueId", null);
            userProfileUrl = mPreferences.getString("nav_profileUrl", null);
            gender = mPreferences.getString("gender", null);

            Log.d(TAG, "onCreate: "+gender);

            markerListWithImage = new Hashtable<>();
        } else {

            Toast.makeText(this, "Google play service not available not your device", Toast.LENGTH_LONG).show();
        }

    }

    private void init() {

        //getNearByMember();

        if (mMap != null) {
            mMap.clear();
            //getCurrentMemberMarker(lat, longitude);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.d(TAG, "run: "+markerList.size());
                    int i = 0;
                    while (i < markerList.size()) {
                        if (markerList.get(i).getUserImage().length() > 0) {

                            final int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    createMarker(markerList.get(finalI).getLatitude(), markerList.get(finalI).getLongitude(), markerList.get(finalI).getUniqueId(), markerList.get(finalI).getMarkerDetails(), markerList.get(finalI).getColorIcon(), markerList.get(finalI).getUserImage());
                                }
                            });

                        }
                        i++;
                    }

                }
            });

           /* for (int i = 0; i < markerList.size(); i++) {
                if (markerList.get(i).getUserImage().length() > 0) {
                    createMarker(markerList.get(i).getLatitude(), markerList.get(i).getLongitude(), markerList.get(i).getUniqueId(), markerList.get(i).getMarkerDetails(), markerList.get(i).getColorIcon(), markerList.get(i).getUserImage());
                }

            }*/
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (mMap != null) {

            mMap.setInfoWindowAdapter(new CustomInfoAdapter());

            getCurrentMemberMarker(lat, longitude);
            //displayAlert(userProfileUrl, memberName);
            init();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);




    }

    private void drawMarker(final double latitude, final double longitude, final String title, final String snippet) {

        if (mMap != null) {

            LatLng gps = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(gps).title("" + title).snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }
    }

    private void getCurrentLocation() {

        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled))
            displayGPSONAlert(MatriNearByActivity.this);
        else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {

            lat = location.getLatitude();
            longitude = location.getLongitude();
            getCurrentMemberMarker(lat, longitude);

            sendMemberLatLongToServer();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasPermission()) {

            getCurrentLocation();

        } else {

            requestPermission();
        }
    }




    public void createMarker(final double latitude, final double longitude, final String title, final String snippet, final String color, final String imageUrl) {

        if (mMap != null) {
            if (color.equals("Red")) {

                View marker = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
                CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);

                markerImage.setTag(imageUrl);

                Picasso.get()
                        .load(imageUrl)
                        .resize(50, 50)
                        .centerCrop()
                        .into(markerImage);
                Log.d(TAG, "createMarker: " + imageUrl);


                TextView txt_name = (TextView) marker.findViewById(R.id.name);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                MatriNearByActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
                marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                marker.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                marker.draw(canvas);

                Log.d(TAG, "createMarker: " + bitmap);

                MarkerOptions options = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(snippet)
                        .snippet(title)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

                //BitmapDescriptorFactory.fromBitmap(createCustomMarker(MatriNearByActivity.this, imageUrl))
                //BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)

                redMarker = mMap.addMarker(options);
                redMarker.showInfoWindow();
                redMarker.hideInfoWindow();
                markerListWithImage.put(redMarker.getId(), imageUrl);
            }
        }
    }




    public static Bitmap createCustomMarker(Context context, String url) {

        //https://www.ipragmatech.com/add-custom-image-in-google-maps-marker-android/

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);

        Picasso.get()
                .load(url)
                .into(markerImage);


        TextView txt_name = (TextView) marker.findViewById(R.id.name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);
        return bitmap;
    }

    private void getCurrentMemberMarker(double lat, double longitude) {

        LatLng gps = new LatLng(lat, longitude);


        /*if (mMap != null) {


            marker = mMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .title(memberUniqueId)
                    .snippet(memberName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            marker.showInfoWindow();
            marker.hideInfoWindow();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }*/


        if (mMap != null) {

            if (marker != null) {

                marker.remove();
            }

            MarkerOptions options = new MarkerOptions()
                    .position(gps)
                    .title(memberUniqueId)
                    .snippet(memberName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            marker = mMap.addMarker(options);

            marker.showInfoWindow();
            marker.hideInfoWindow();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));

            getNearByMember();

        }

    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }


    private void displayGPSONAlert(final Activity activity) {

        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(this);

        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;

        String message = "Enable GPS to display your current location and view other member profiles";

        mBuilder.setMessage(message);
        mBuilder.setCancelable(false);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.startActivity(new Intent(action));
                dialog.dismiss();
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        mBuilder.create().show();
    }

    private boolean hasPermission() {

        int res = 0;

        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        for (String perems : permissions) {

            res = checkCallingOrSelfPermission(perems);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {

                return false;
            }
        }

        return true;
    }

    private void requestPermission() {

        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean allowed = true;

        switch (requestCode) {

            case PERMS_REQUEST_CODE:

                for (int res : grantResults) {

                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;

            default:

                allowed = false;
                break;
        }

        if (allowed) {

            // showFileChooser(PICK_IMAGE_REQUEST_PROFILE_PICK);

            getCurrentLocation();


        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if ((shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) && (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))) {

                    Toast.makeText(this, "Phone state permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getNearByMember() {
        Log.d(TAG, "getNearByMember: Tusahr");
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait");
        mDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matri_near_by_update_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            mDialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = null;
                            Log.d("response", response);

                            int success = object.getInt("success");

                            if (success == 1) {

                                jsonArray = object.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MarkerDataModel markerDataModel = new MarkerDataModel();

                                    String firstName = "" + jsonObject.getString("FirstName");
                                    String lastName = "" + jsonObject.getString("LastName");
                                    markerDataModel.setUsername("" + firstName + " " + lastName);
                                    markerDataModel.setUniqueId("" + jsonObject.getString("UniqueId"));
                                    markerDataModel.setUserImage("" + jsonObject.getString("MainProfilePhoto"));
                                    String memberLongitude = jsonObject.getString("Longitude");
                                    String memberLatitude = jsonObject.getString("Latitude");
                                    markerDataModel.setLongitude(Double.parseDouble(memberLongitude));
                                    markerDataModel.setLatitude(Double.parseDouble(memberLatitude));
                                    markerDataModel.setColorIcon("" + jsonObject.getString("Color"));
                                    markerDataModel.setDistance(Double.parseDouble("" + jsonObject.getString("Distance")));
                                    markerDataModel.setMarkerDetails("" + markerDataModel.getUsername() + "\nDistance: " + markerDataModel.getDistance());
                                    String gender1=""+jsonObject.getString("Gender");
                                    String marriedstatus=""+jsonObject.getString("Gender");
                                    markerList.add(markerDataModel);

                                }
                                Log.e(TAG, "onResponse: " + markerList.size());
                                init();

                                Toast.makeText(MatriNearByActivity.this, "Location Updated...", Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(MatriNearByActivity.this, "Location not updated...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", error.toString());
                        mDialog.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.d(TAG, "getParams: " + lat + " " + longitude);

                Map<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(memberId));
                param.put(LATITUDE, String.valueOf(lat));
                param.put(LONGITUDE, String.valueOf(longitude));
                param.put(DISTANCE, String.valueOf(selectdistance));
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void showDistanceList(final List<DistanceModel> distanceList) {
        distanceModelList = distanceList;
        displacementSpnAdapter = new CustomSpinnerAdapter(this, distanceList, R.layout.row_simple_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                DistanceModel model = distanceList.get(position);

                TextView textView = view.findViewById(R.id.row_spin_heading);
                textView.setText(model.getDistance());
            }
        });

        spnDisplacement.setAdapter(displacementSpnAdapter);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    public class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

        private View v;

        public CustomInfoAdapter() {

            v = getLayoutInflater().inflate(R.layout.row_google_info_windows, null);
        }


        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }



        @Override
        public View getInfoContents(final Marker marker) {

            MatriNearByActivity.this.marker = marker;

            String url = null;

            AppCompatTextView tvHeading = v.findViewById(R.id.row_google_userName);
            AppCompatTextView tvDistance = v.findViewById(R.id.row_google_distance);
            CircleImageView imageView = v.findViewById(R.id.row_google_profileImage);
            AppCompatTextView tvUniqueId = v.findViewById(R.id.row_google_uniqueId);
            LinearLayout googler_linera= v.findViewById(R.id.googler_linera);

            if (marker.getId() != null && markerListWithImage != null && markerListWithImage.size() > 0) {

                if (markerListWithImage.get(marker.getId()) != null) {

                    url = markerListWithImage.get(marker.getId());
                }

            }

            if (!TextUtils.isEmpty(url) && url.length() > 10) {

                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.bg_gif_progress)
                        .into(imageView);
            } else {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .into(imageView);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick:123 "+marker.getTitle());
                }
            });

            tvHeading.setText("" + marker.getTitle());
            tvUniqueId.setText("" + marker.getSnippet());


            return v;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(MatriNearByActivity.this, "tushar", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMemberLatLongToServer() {

        Log.d(TAG, "getParams: "+memberId+" "+lat+" "+longitude);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matri_near_by_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            Log.d("response", response);


                            int success = object.getInt("success");

                            if (success == 1) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MatriNearByActivity.this, "Location updated...", Toast.LENGTH_SHORT).show();

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MatriNearByActivity.this, "location not updated...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(memberId));
                param.put(LATITUDE, String.valueOf(lat));
                param.put(LONGITUDE, String.valueOf(longitude));

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void displayAlert(String imgUrl, String memberName) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.row_near_by_alert, null);
        mBuilder.setView(view);
        mBuilder.setCancelable(true);

        TextView tvName = view.findViewById(R.id.tv_memberName);
        CircleImageView imageView = view.findViewById(R.id.img_profileImage);
        ImageView imgRefresh = view.findViewById(R.id.refresh_location);

        tvName.setText("" + memberName);

        if (imgUrl.length() > 10) {

            Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.drawable.img_preview)
                    .into(imageView);
        } else {
            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .into(imageView);
        }

        final android.support.v7.app.AlertDialog dialog = mBuilder.create();
        dialog.show();
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasPermission()) {
                    getNearByMember();
                } else {
                    requestPermission();
                }
                dialog.dismiss();
                getCurrentMemberMarker(lat, longitude);
            }
        });
    }

    public void createMarker1(final double latitude, final double longitude, final String title, final String snippet, final String color, final String imageUrl) {

        Log.d(TAG, "createMarker: " + latitude + " " + longitude);

        Log.d(TAG, "createMarker: " + imageUrl);

        if (mMap != null) {

            if (imageUrl != null) {
                if (color.equals("Red")) {
                    MarkerOptions options = new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title(snippet)
                            .snippet(title)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    //BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED

                    redMarker = mMap.addMarker(options);

               /* Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(snippet)
                        .snippet(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));*/
                    redMarker.showInfoWindow();
                    redMarker.hideInfoWindow();

                    markerListWithImage.put(redMarker.getId(), imageUrl);

                } /*else if (color.equals("Blue")) {

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(snippet)
                        .snippet(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                marker.showInfoWindow();
                marker.hideInfoWindow();

                markerListWithImage.put(marker.getId(), imageUrl);
            }*/


            }


        }


    }


}