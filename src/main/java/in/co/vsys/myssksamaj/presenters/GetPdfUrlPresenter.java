package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetPdfUrlContract;
import in.co.vsys.myssksamaj.model.responses.PdfResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class GetPdfUrlPresenter implements GetPdfUrlContract.GetPdfUrlOps {
    private GetPdfUrlContract.GetPdfUrlAPI getPdfUrlAPI;
    private GetPdfUrlContract.GetPdfUrlView getPdfUrlView;

    public GetPdfUrlPresenter(GetPdfUrlContract.GetPdfUrlView getPdfUrlView) {
        this.getPdfUrlView = getPdfUrlView;
        getPdfUrlAPI = RestAdapterContainer.getInstance().create(GetPdfUrlContract.GetPdfUrlAPI.class);
    }

    @Override
    public void getProfilePdf(String memberId) {
        getPdfUrlView.showLoading();
        getPdfUrlAPI.getProfilePdf(memberId).enqueue(new Callback<PdfResponse>() {
            @Override
            public void onResponse(Call<PdfResponse> call, Response<PdfResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<PdfResponse> call, Throwable t) {
                getPdfUrlView.hideLoading();
                getPdfUrlView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(PdfResponse pdfResponse) {
        getPdfUrlView.hideLoading();
        if (pdfResponse == null || Utilities.isListEmpty(pdfResponse.getData())) {
            getPdfUrlView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        getPdfUrlView.showPdfUrl(pdfResponse.getData().get(0).getPDFPath());
    }
}