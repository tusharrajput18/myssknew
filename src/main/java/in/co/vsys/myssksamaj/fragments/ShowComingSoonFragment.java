package in.co.vsys.myssksamaj.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import in.co.vsys.myssksamaj.R;

public class ShowComingSoonFragment extends DialogFragment {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        if (mContext == null)
            mContext = getActivity();

        Dialog dialog = new Dialog(mContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        width -= (width / 8);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_show_contact, null);
        dialog.setContentView(view, layoutParams);

        /**
         * TODO: currently showing as Coming soon. When it has to be shown again, remove textview from xml
         *         and uncomment below line
         */
//        initUI(view);

        return dialog;
    }
}
