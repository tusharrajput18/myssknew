package in.co.vsys.myssksamaj.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import in.co.vsys.myssksamaj.BuildConfig;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.model.AppUpdateModel;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateApp {

    String version_name;
    Context context;
    Dialog dialog;
    TextView descTextView;
    TextView tv_update;




    public UpdateApp(Context context){
        this.context = context;
    }

    public void getAppUpdatedVersionNumber() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        BusinessAPIServices service = RestAdapterContainer.getInstance().create(BusinessAPIServices.class);
        Call<AppUpdateModel> call = service.getAllversionNo();
            call.enqueue(new Callback<AppUpdateModel>() {
                @Override
                public void onResponse(Call<AppUpdateModel> call, Response<AppUpdateModel> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        version_name= response.body().getVersionLists().get(0).getVersionnumber();
                        //Toast.makeText(context, ""+BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
                        if(version_name.equals(BuildConfig.VERSION_NAME)){
                           // CustomProgress.hideprogress();
                        }
                        else {
                            buildDialog1(version_name);
                            //CustomProgress.hideprogress();
                        }
                    } else {
                        progressDialog.dismiss();
                        //Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AppUpdateModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();

                }
            });

    }

    private void buildDialog1(String version_name) {
       // validations = new Validations();
        final String appPackageName = context.getPackageName();
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.app_update_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
        descTextView=dialog.findViewById(R.id.descTextView);
        tv_update=dialog.findViewById(R.id.tv_update);
        descTextView.setText("Update "+version_name+" is available to download. Downloading the latest update you will get the latest features, improvements and bug fixes.");

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (Exception e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    e.printStackTrace();
                }
            }
        });
    }
}
