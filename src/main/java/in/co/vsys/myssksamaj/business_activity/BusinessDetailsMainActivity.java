package in.co.vsys.myssksamaj.business_activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businessmodels.BusinessModelList;
import in.co.vsys.myssksamaj.matrimonyutils.ChatContract;

public class BusinessDetailsMainActivity extends AppCompatActivity {

    BusinessModelList model;
    AppCompatImageView imageView;
    AppCompatTextView tv_busiDisplay_title_main, tv_busiDisplay_city_main, tv_busiDisplay_call_main, tv_busiDisplay_direction_main, tv_busiDisplay_mail_main;

    AppCompatTextView tv_busiDisplay_contactPerson_main, tv_busiDisplay_website_main, tv_busiDisplay_contactNumber_main, tv_busiDisplay_mobileNumber_main, tv_busiDisplay_address_main, tv_businessDisplay_mail_main;

    AppCompatTextView tv_busiDisplay_services_main, tv_busiDisplay_aboutUs_main, business_share_main;

    ArrayList<String> sliderarray;
    private HashMap<String, String> Hash_file_maps1;

    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details_main);
        model = new BusinessModelList();
        model = (BusinessModelList) getIntent().getSerializableExtra("model");
        //
        // Toast.makeText(this, "" + model.getBusinessname(), Toast.LENGTH_SHORT).show();

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        sliderarray = new ArrayList<>();
        Hash_file_maps1 = new HashMap<>();
        v_flipper=(ViewFlipper)findViewById(R.id.v_flipper);



        imageView = (AppCompatImageView) findViewById(R.id.img_busiDisplay_image_main);
        tv_busiDisplay_title_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_title_main);
        tv_busiDisplay_title_main.setText(model.getBusinessname());

        tv_busiDisplay_city_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_city_main);
        tv_busiDisplay_city_main.setText(model.getCity());

        tv_busiDisplay_call_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_call_main);
        tv_busiDisplay_direction_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_direction_main);
        tv_busiDisplay_mail_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_mail_main);
        business_share_main = (AppCompatTextView) findViewById(R.id.business_share_main);

        tv_busiDisplay_contactPerson_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_contactPerson_main);
        tv_busiDisplay_contactPerson_main.setText(model.getContactpersonname());

        tv_busiDisplay_website_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_website_main);
        tv_busiDisplay_website_main.setText(model.getWebsite());

        tv_busiDisplay_contactNumber_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_contactNumber_main);
        tv_busiDisplay_contactNumber_main.setText(model.getPhone1());

        tv_busiDisplay_mobileNumber_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_mobileNumber_main);
        tv_busiDisplay_mobileNumber_main.setText(model.getPhone2());

        tv_busiDisplay_address_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_address_main);
        tv_busiDisplay_address_main.setText(model.getAddress());

        tv_businessDisplay_mail_main = (AppCompatTextView) findViewById(R.id.tv_businessDisplay_mail_main);
        tv_businessDisplay_mail_main.setText(model.getEmailId());

        tv_busiDisplay_services_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_services_main);
        tv_busiDisplay_services_main.setText(model.getServices());

        tv_busiDisplay_aboutUs_main = (AppCompatTextView) findViewById(R.id.tv_busiDisplay_aboutUs_main);
        tv_busiDisplay_aboutUs_main.setText(model.getAboutus());


        if (model.getImage1().equals("http://app.myssksamaj.com/uploads/business/")) {
            imageView.setVisibility(View.VISIBLE);

        } else {
            v_flipper.setVisibility(View.GONE);
            sliderarray.add(model.getImage1());
        }

        if (model.getImage2().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage2());
        }
        if (model.getImage3().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage3());
        }

        if (model.getImage4().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage4());
        }

     /*   if (model.getImage5().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage5());
        }

        if (model.getImage6().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage6());
        }

        if (model.getImage7().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage7());
        }

        if (model.getImage8().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage8());
        }

        if (model.getImage9().equals("http://app.myssksamaj.com/uploads/business/")) {

        } else {
            sliderarray.add(model.getImage9());
        }*/

     /*   for(int i=0;i<sliderarray.size();i++){
            flipperImages(sliderarray(i));
        }*/

     if(sliderarray.size()>=1){
         imageView.setVisibility(View.GONE);
         v_flipper.setVisibility(View.VISIBLE);
         for(String str:sliderarray){
             flipperImages(str);
         }
     }else {
         v_flipper.setVisibility(View.GONE);
         imageView.setVisibility(View.VISIBLE);
     }




        /*for (String sliderModelList : sliderarray) {
            Hash_file_maps1.put(sliderModelList, sliderModelList);
            DefaultSliderView textSliderView = new DefaultSliderView(getApplicationContext());
            textSliderView.image(sliderModelList)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", sliderModelList);
            detailsImageSlider.addSlider(textSliderView);

            detailsImageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            //detailsImageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            detailsImageSlider.getPagerIndicator().setVisibility(View.INVISIBLE);

            detailsImageSlider.setCustomAnimation(new DescriptionAnimation());
            detailsImageSlider.setDuration(6000);
            detailsImageSlider.startAutoCycle();
            detailsImageSlider.addOnPageChangeListener(this);
        }*/


        /*if (model.getImage1().length() > 0) {
            Picasso.get()
                    .load(model.getImage1())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        }*/


        tv_busiDisplay_call_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = model.getPhone1();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + model.getPhone1()));
                startActivity(intent);

               /* Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + mobile));
                if (ActivityCompat.checkSelfPermission(BusinessDetailsMainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                BusinessDetailsMainActivity.this.startActivity(intent);*/

            }
        });

        business_share_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApplication();
            }
        });

        tv_busiDisplay_mail_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("sms:" + model.getPhone1()));
                startActivity(smsIntent);
               /* String to = model.getEmailId();
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {to};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_CC, to);
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));*/
            }
        });

        tv_busiDisplay_direction_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.getLatitude().isEmpty()) {
                    Toast.makeText(BusinessDetailsMainActivity.this, "No Location", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BusinessDetailsMainActivity.this, BusinessMapsDirectionActivity.class);
                    intent.putExtra("lat", model.getLatitude());
                    intent.putExtra("long", model.getLongitude());
                    intent.putExtra("business_namme", model.getBusinessname());
                    startActivity(intent);
                }

            }
        });
    }

    public void flipperImages(String str){
        ImageView imageView=new ImageView(this);

        Picasso.get()
                .load(str)
                .placeholder(R.drawable.imageview_default_image)
                .error(R.drawable.imageview_default_image)
                .into(imageView);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }


    private void shareApplication() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "My SSK\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=in.co.vsys.myssksamaj";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

}
