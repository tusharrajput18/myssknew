package in.co.vsys.myssksamaj.helpers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.AddressModel;

/**
 * Create this Class from tutorial :
 * http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial
 * <p/>
 * For Geocoder read this : http://stackoverflow.com/questions/472313/android-reverse-geocoding-getfromlocation
 * <p/>
 * Try to get my current location by GPS or Network Provider
 * <p/>
 * Update GPSTracker latitude and longitude
 * <p/>
 * GPSTracker latitude getter and setter
 *
 * @return latitude
 * <p/>
 * GPSTracker longitude getter and setter
 * @return GPSTracker isGPSTrackingEnabled getter.
 * Check GPS/wifi is enabled
 * <p/>
 * Stop using GPS listener
 * Calling this method will stop using GPS in your app
 * <p/>
 * Function to show settings alert dialog
 * <p/>
 * Get list of address by latitude and longitude
 * @return null or List<Address>
 * <p/>
 * Geocoder.getFromLocation - Returns an array of Addresses
 * that are known to describe the area immediately surrounding the given latitude and longitude.
 * <p/>
 * Try to get AddressLine
 * @return null or addressLine
 * <p/>
 * Try to get Locality
 * @return null or locality
 * <p/>
 * Try to get Postal Code
 * @return null or postalCode
 * <p/>
 * Try to get my current location by GPS or Network Provider
 * <p/>
 * Update GPSTracker latitude and longitude
 * <p/>
 * GPSTracker latitude getter and setter
 * @return latitude
 * <p/>
 * GPSTracker longitude getter and setter
 * @return GPSTracker isGPSTrackingEnabled getter.
 * Check GPS/wifi is enabled
 * <p/>
 * Stop using GPS listener
 * Calling this method will stop using GPS in your app
 * <p/>
 * Function to show settings alert dialog
 * <p/>
 * Get list of address by latitude and longitude
 * @return null or List<Address>
 * <p/>
 * Geocoder.getFromLocation - Returns an array of Addresses
 * that are known to describe the area immediately surrounding the given latitude and longitude.
 * <p/>
 * Try to get AddressLine
 * @return null or addressLine
 * <p/>
 * Try to get Locality
 * @return null or locality
 * <p/>
 * Try to get Postal Code
 * @return null or postalCode
 * <p/>
 * Try to get my current location by GPS or Network Provider
 * <p/>
 * Update GPSTracker latitude and longitude
 * <p/>
 * GPSTracker latitude getter and setter
 * @return latitude
 * <p/>
 * GPSTracker longitude getter and setter
 * @return GPSTracker isGPSTrackingEnabled getter.
 * Check GPS/wifi is enabled
 * <p/>
 * Stop using GPS listener
 * Calling this method will stop using GPS in your app
 * <p/>
 * Function to show settings alert dialog
 * <p/>
 * Get list of address by latitude and longitude
 * @return null or List<Address>
 * <p/>
 * Geocoder.getFromLocation - Returns an array of Addresses
 * that are known to describe the area immediately surrounding the given latitude and longitude.
 * <p/>
 * Try to get AddressLine
 * @return null or addressLine
 * <p/>
 * Try to get Locality
 * @return null or locality
 * <p/>
 * Try to get Postal Code
 * @return null or postalCode
 * <p/>
 * Try to get my current location by GPS or Network Provider
 * <p/>
 * Update GPSTracker latitude and longitude
 * <p/>
 * GPSTracker latitude getter and setter
 * @return latitude
 * <p/>
 * GPSTracker longitude getter and setter
 * @return GPSTracker isGPSTrackingEnabled getter.
 * Check GPS/wifi is enabled
 * <p/>
 * Stop using GPS listener
 * Calling this method will stop using GPS in your app
 * <p/>
 * Function to show settings alert dialog
 * <p/>
 * Get list of address by latitude and longitude
 * @return null or List<Address>
 * <p/>
 * Geocoder.getFromLocation - Returns an array of Addresses
 * that are known to describe the area immediately surrounding the given latitude and longitude.
 * <p/>
 * Try to get AddressLine
 * @return null or addressLine
 * <p/>
 * Try to get Locality
 * @return null or locality
 * <p/>
 * Try to get Postal Code
 * @return null or postalCode
 */

/*
public class GPSTracker extends Service implements LocationListener {

    // Get Class Name
    private static String TAG = GPSTracker.class.getName();

    private final Context context;

    // flag for GPS Status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS Tracking is enabled 
    boolean isGPSTrackingEnabled = false;

    Location location;
    public double latitude;
    public double longitude;

    // How many Geocoder should return our GPSTracker
    int geocoderMaxResults = 1;

    // The minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    private String provider_info;

    public GPSTracker(Context context) {
        this.context = context;
        getLocation();
    }

    */
/**
 * Try to get my current location by GPS or Network Provider
 *//*

    @TargetApi(Build.VERSION_CODES.M)
    public void getLocation() {

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            //getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // Try to get location if you GPS Service is enabled
            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use GPS Service");

                */
/*
 * This provider determines location using
 * satellites. Depending on conditions, this provider may take a while to return
 * a location fix.
 *//*


                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use Network State to get GPS coordinates");

                */
/*
 * This provider determines location based on
 * availability of cell tower and WiFi access points. Results are retrieved
 * by means of a network lookup.
 *//*

                provider_info = LocationManager.NETWORK_PROVIDER;

            }

            // Application can use GPS or Network Provider
            if (!provider_info.isEmpty()) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(
                        provider_info,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                );

                if (locationManager != null) {
                    location = getLocationUpdated();
                    updateGPSCoordinates();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e(TAG, "Impossible to connect to LocationManager", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Location getLocationUpdated() {
        try {
            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
//                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return null;
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    */
/**
 * Update GPSTracker latitude and longitude
 *//*

    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    */
/**
 * GPSTracker latitude getter and setter
 *
 * @return latitude
 *//*

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    */
/**
 * GPSTracker longitude getter and setter
 *
 * @return
 *//*

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    */
/**
 * GPSTracker isGPSTrackingEnabled getter.
 * Check GPS/wifi is enabled
 *//*

    public boolean getIsGPSTrackingEnabled() {

        return this.isGPSTrackingEnabled;
    }

    */
/**
 * Stop using GPS listener
 * Calling this method will stop using GPS in your app
 *//*

    @TargetApi(Build.VERSION_CODES.M)
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    */
/**
 * Function to show settings alert dialog
 *//*

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Setting Dialog Title
        alertDialog.setTitle("Title");

        //Setting Dialog Message
        alertDialog.setMessage("GPSAlertDialogMessage");

        //On Pressing Setting button
        alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    */
/**
 * Get list of address by latitude and longitude
 *
 * @return null or List<Address>
 *//*

    public List<Address> getGeocoderAddress(Context context) {
        if (location != null) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                */
/**
 * Geocoder.getFromLocation - Returns an array of Addresses
 * that are known to describe the area immediately surrounding the given latitude and longitude.
 *//*

                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);

                return addresses;
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e(TAG, "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }

    */
/**
 * Try to get AddressLine
 *
 * @return null or addressLine
 *//*

    public String getAddressLine(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        } else {
            return null;
        }
    }

    */
/**
 * Try to get Locality
 *
 * @return null or locality
 *//*

    public String getLocality(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        } else {
            return null;
        }
    }

    */
/**
 * Try to get Postal Code
 *
 * @return null or postalCode
 *//*

    public String getPostalCode(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        } else {
            return null;
        }
    }

    */

/**
 * Try to get CountryName
 *
 * @return null or postalCode
 *//*

    public String getCountryName(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String categoryName = address.getCountryName();

            return categoryName;
        } else {
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}*/

public final class GPSTracker implements LocationListener {

    private Context context;
    private static GPSTracker instance;

    // flag for GPS status
    public boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public static GPSTracker getInstance(Context context) {
        if (instance == null) {
            instance = new GPSTracker(context);
        }
        return instance;
    }

    private GPSTracker(Context context) {
        this.context = context;
        getLocation();
    }

    /**
     * Function to get the user's current location
     *
     * @return
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("isGPSEnabled", "=" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if (isGPSEnabled == false && isNetworkEnabled == false) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    location = null;
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        return null;
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    location = null;
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return null;
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            getGeoLocation();
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    private void getGeoLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
//        Location location = locationManager.

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        Log.d("Network", "Network");
        if (locationManager != null) {
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        if (location == null) {
            Toast.makeText(context, "Location Not found", Toast.LENGTH_LONG).show();
        } else {
            Geocoder geocoder = new Geocoder(context);
            List<Address> matches = null;
            try {
                matches = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),
                        1);
                if (matches != null && matches.size() > 0) {
                    Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                    setDataRespectively(bestMatch);
                } else {
                    getGeoLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDataRespectively(Address fullAddress) {
        try {
            String addressText1 = fullAddress.getAddressLine(0);
            String addressText2 = fullAddress.getAddressLine(1); // Address
            String countryText = fullAddress.getCountryName();
            String stateText = fullAddress.getAdminArea();
            String cityText = fullAddress.getLocality();
            String localityText = fullAddress.getSubLocality();

            AddressModel addressModel = new AddressModel();
            addressModel.setAddress(addressText1 + ", " + addressText2);
            addressModel.setLatitude("" + fullAddress.getLatitude());
            addressModel.setLongitude("" + fullAddress.getLongitude());
            addressModel.setLocality(localityText);
            addressModel.setCity(cityText);
            addressModel.setCountry(countryText);
            addressModel.setState(stateText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}