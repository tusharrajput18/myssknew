package in.co.vsys.myssksamaj.interfaces;

import android.view.View;

/**
 * @author abhijeetjadhav
 */
public interface FilterListener {
    void onViewBound(View view, Object object, int position);

    void onItemClicked(View view, Object object, int position);
}