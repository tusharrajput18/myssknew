package in.co.vsys.myssksamaj.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

import in.co.vsys.myssksamaj.interfaces.LocationUpdateListener;
import in.co.vsys.myssksamaj.model.data_models.AddressModel;

/**
 * @author Abhijeet
 */

public class LocationHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static String mLocationId = "34";
    private String TAG = LocationHelper.class.getSimpleName();
    private static Context mContext;
    private static Activity mActivity;
    private LocationManager mLocationManager;
    private Location mLocation;
    private LocationUpdateListener mLocationUpdateListener;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static LocationHelper mLocationHelper;
    private static final int ACCESS_LOCATION_PERMISSION = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private LocationHelper() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public static LocationHelper getInstance(Activity activity) {
        mContext = activity;
        mActivity = activity;
        if (mLocationHelper == null)
            mLocationHelper = new LocationHelper();

        return mLocationHelper;
    }

    public void displayLocationSettingsRequest(Context context) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(mActivity, 101);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    public LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != 0
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != 0) {
                return;
            }
            mLocationManager.removeUpdates(mLocationListener);
            locationResolved(location);
        }

        public void onProviderDisabled(String string2) {
        }

        public void onProviderEnabled(String string2) {
        }

        public void onStatusChanged(String string2, int n, Bundle bundle) {
        }
    };

    private void locationResolved(Location location) {
        mLocation = location;
        mLocationUpdateListener.onLocationUpdated(location, getAddressFromLocation(location));
    }

    private com.google.android.gms.location.LocationListener mGMSLocationListener = new com.google.android.gms.location.LocationListener() {

        public void onLocationChanged(Location location) {
            try {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (location != null) {
                    mLocationManager.removeUpdates(mLocationListener);
                    locationResolved(location);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void getCurrentLocation(Context context) {
        if (mLocation != null) {

            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mGMSLocationListener);
            return;
        }

        Location location = GPSTracker.getInstance(mContext).getLocation();
        if (location != null) {
            mLocation = location;
            locationResolved(mLocation);
            return;
        }

        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // TODO: if google client is null, initialize it and then request for location updates on connected
        if (mGoogleApiClient == null) {
            displayLocationSettingsRequest(context);
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            locationResolved(mLocation);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                    mGMSLocationListener);
        }
    }

    public void getCurrentLocation(Context context, LocationUpdateListener locationUpdateListener) {
        mLocationUpdateListener = locationUpdateListener;

        Location location = GPSTracker.getInstance(mContext).getLocation();
        if (location != null) {
            mLocation = location;
            locationResolved(mLocation);
            return;
        }

        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // TODO: if google client is null, initialize it and then request for location updates on connected
        if (mGoogleApiClient == null) {
            displayLocationSettingsRequest(context);
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            locationResolved(mLocation);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                    mGMSLocationListener);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation(mContext);
        } else {
            verifyStoragePermissions(mActivity);
        }
    }

    public void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, ACCESS_LOCATION_PERMISSION);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public AddressModel getAddressFromLocation(Location location) {
        AddressModel addressModel = new AddressModel();
        Geocoder geocoder = new Geocoder(mActivity);
        try {
            List<Address> matches = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),
                    1);
            Address bestMatch = null;
            if (matches != null && matches.size() > 0) {
                bestMatch = (matches.isEmpty() ? null : matches.get(0));
            }

            addressModel.setAddress(bestMatch.getAddressLine(0) + ", " + bestMatch.getAddressLine(1));
            addressModel.setLatitude("" + location.getLatitude());
            addressModel.setLongitude("" + location.getLongitude());
            addressModel.setLocality(bestMatch.getSubLocality());
            addressModel.setCity(bestMatch.getLocality());
            addressModel.setCountry(bestMatch.getCountryName());
            addressModel.setState(bestMatch.getAdminArea());
            addressModel.setPincode(bestMatch.getPostalCode());

        } catch (Exception e) {
            e.printStackTrace();

            addressModel.setLatitude("" + location.getLatitude());
            addressModel.setLongitude("" + location.getLongitude());
        }
        return addressModel;
    }

    public AddressModel getAddressFromLocation() {
        AddressModel addressModel = null;
        Geocoder geocoder = new Geocoder(mActivity);
        try {
            List<Address> matches = geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(),
                    1);
            Address bestMatch = null;
            if (matches != null && matches.size() > 0) {
                bestMatch = (matches.isEmpty() ? null : matches.get(0));
            }

            addressModel = new AddressModel();
            addressModel.setAddress(bestMatch.getAddressLine(0) + ", " + bestMatch.getAddressLine(1));
            addressModel.setLatitude("" + mLocation.getLatitude());
            addressModel.setLongitude("" + mLocation.getLongitude());
            addressModel.setLocality(bestMatch.getSubLocality());
            addressModel.setCity(bestMatch.getLocality());
            addressModel.setCountry(bestMatch.getCountryName());
            addressModel.setState(bestMatch.getAdminArea());
            addressModel.setPincode(bestMatch.getPostalCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressModel;
    }
}