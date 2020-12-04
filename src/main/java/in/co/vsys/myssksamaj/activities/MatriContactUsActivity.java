package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import in.co.vsys.myssksamaj.R;

public class MatriContactUsActivity extends AppCompatActivity {

    ImageView imageView_contactus;
    LinearLayout linear_contactus_matri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_contact_us);

        imageView_contactus=(ImageView)findViewById(R.id.imageView_contactus);
        linear_contactus_matri=(LinearLayout) findViewById(R.id.linear_contactus_matri);

        Picasso.get()
                .load("http://app.myssksamaj.com/uploads/SliderImg/Webp.net-compress-image%20(2)637390734174066825.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView_contactus);

        linear_contactus_matri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup_callOption();

            }
        });
    }


    private void popup_callOption() {
        final Dialog dialogChoice = new Dialog(this);
        dialogChoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChoice.setContentView(R.layout.dialog_contactus_option);
        dialogChoice.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogChoice.show();
        dialogChoice.setCancelable(true);

        ImageView tv_dilog_call = (ImageView) dialogChoice.findViewById(R.id.tv_dilog_call);
        tv_dilog_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChoice.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9923449493"));
                startActivity(intent);

            }
        });
        ImageView tv_dilog_wathsapp = (ImageView) dialogChoice.findViewById(R.id.tv_dilog_wathsapp);
        tv_dilog_wathsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChoice.dismiss();
                String number = "+919923449493";
                try {
                    number = number.replace(" ", "").replace("+", "");

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Log.e("ERROR_OPEN_MESSANGER", e.toString());
                }
            }
        });
    }
}