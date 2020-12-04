package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.CasteContract;
import in.co.vsys.myssksamaj.model.responses.CasteResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class CastePresenter implements CasteContract.CasteOps {

    private CasteContract.CasteAPI casteAPI;
    private CasteContract.CasteView casteView;

    public CastePresenter(CasteContract.CasteView casteView) {
        this.casteView = casteView;
        casteAPI = RestAdapterContainer.getInstance().create(CasteContract.CasteAPI.class);
    }

    @Override
    public void getAllCastes() {
        casteView.showLoading();
        casteAPI.getAllCastes().enqueue(new Callback<CasteResponse>() {
            @Override
            public void onResponse(Call<CasteResponse> call, Response<CasteResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<CasteResponse> call, Throwable t) {
                casteView.hideLoading();
                casteView.showError("Response not found");
            }
        });
    }

    @Override
    public void onDataReceived(CasteResponse casteResponse) {
        casteView.hideLoading();
        casteView.showCasteList(casteResponse.getData());
    }
}