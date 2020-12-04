package in.co.vsys.myssksamaj.presenters;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.PackageModel;
import in.co.vsys.myssksamaj.model.rest_api.RawRestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * @author abhijeetjadhav
 */
public class AllPackagesPresenter {

    private AllPackagesView allPackagesView;

    public interface AllPackagesAPI {
        @GET("/matrimonyapp.asmx/SelectAllPackages")
        Call<String> selectAllPackages();
    }

    public AllPackagesPresenter(final AllPackagesView allPackagesView) {
        this.allPackagesView = allPackagesView;
        RawRestAdapterContainer.getInstance().create(AllPackagesAPI.class)
                .selectAllPackages().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
                    List<PackageModel> packageModels = new Gson().fromJson(jsonObject.get("data"), new TypeToken<List<PackageModel>>(){}.getType());

                    allPackagesView.showPackages(packageModels);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                allPackagesView.showError(t.getLocalizedMessage());
            }
        });
    }

    public interface AllPackagesView extends MVPView {
        Context getContext();

        void showPackages(List<PackageModel> packageModels);
    }
}