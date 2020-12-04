package in.co.vsys.myssksamaj.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * Created by Jack on 10/11/2017.
 */

public class Route {

    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}