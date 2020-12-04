package in.co.vsys.myssksamaj.presenters;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.co.vsys.myssksamaj.model.rest_api.RawRestAdapterContainer;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ViewContactPresenter {

    public interface AllPackagesAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/contactViewed")
        Call<String> callApi(
                @Field("fromId") String fromId,
                @Field("toId") String toId
        );
    }

    public ViewContactPresenter(String fromId, String toId, final RestView restView) {
        RawRestAdapterContainer.getInstance().create(AllPackagesAPI.class)
                .callApi(fromId, toId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
                    Log.d("mytag", "onResponse: 123456");
                    String message = jsonObject.get("message").toString()/*.replace("\"", "").trim()*/;
                    restView.showMessage(message);
                    Log.d("mytag123456", "onResponse: "+message);
                  /*  if (message.equalsIgnoreCase("You Must Subscribe To Contact This Member")) {
                        restView.showError(message);
                    }

                    if (message.equalsIgnoreCase("You Are Free To Contact This Member")) {
                        restView.showMessage(message);
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                restView.showError(t.getLocalizedMessage());
            }
        });
    }

    public interface RestView extends MVPView {
        Context getContext();

        void showMessage(String message);
    }
}