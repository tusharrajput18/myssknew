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
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.helpers.ImageHelper;
import in.co.vsys.myssksamaj.photoview.PhotoView;

public class ImageDisplayFragment extends DialogFragment {

    private Context mContext;
    private String kundliPhoto;
    private String memberName;

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_image_display, null);
        dialog.setContentView(view, layoutParams);

        kundliPhoto = getArguments().getString("kundali");
        memberName = getArguments().getString("memberName");

        PhotoView photoView = view.findViewById(R.id.image);
        Picasso.get()
                .load(kundliPhoto)
                .placeholder(R.drawable.img_preview)
                .error(R.drawable.img_preview)
                .into(photoView);

        ImageView download = view.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageHelper.getBitmapFromUrl(mContext, kundliPhoto, memberName + "_kundali");
            }
        });

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}