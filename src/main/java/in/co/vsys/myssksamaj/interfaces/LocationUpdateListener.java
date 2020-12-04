package in.co.vsys.myssksamaj.interfaces;

import android.location.Location;

import in.co.vsys.myssksamaj.model.data_models.AddressModel;

/**
 * @author Abhijeet
 */

public interface LocationUpdateListener {
    void onLocationUpdated(Location location, AddressModel addressModel);
}