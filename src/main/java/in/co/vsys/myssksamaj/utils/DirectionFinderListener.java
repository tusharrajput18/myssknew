package in.co.vsys.myssksamaj.utils;

import java.util.List;

/**
 * Created by Jack on 10/11/2017.
 */

public interface DirectionFinderListener {

    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
