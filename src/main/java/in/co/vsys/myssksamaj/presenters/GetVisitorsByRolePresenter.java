package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetVisitorsByRoleContract;
import in.co.vsys.myssksamaj.model.responses.VisitorInfoResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class GetVisitorsByRolePresenter implements GetVisitorsByRoleContract.GetVisitorsOps {

    private GetVisitorsByRoleContract.GetVisitorsAPI getVisitorsAPI;
    private GetVisitorsByRoleContract.GetVisitorView getVisitorsView;

    public GetVisitorsByRolePresenter(GetVisitorsByRoleContract.GetVisitorView getVisitorsView) {
        this.getVisitorsView = getVisitorsView;
        getVisitorsAPI = RestAdapterContainer.getInstance().create(GetVisitorsByRoleContract.GetVisitorsAPI.class);
    }

    @Override
    public void getVisitorsByRole(String MemberId, String role) {
        getVisitorsView.showLoading();
        getVisitorsAPI.getVisitorsByRole(MemberId, role).enqueue(new Callback<VisitorInfoResponse>() {
            @Override
            public void onResponse(Call<VisitorInfoResponse> call, Response<VisitorInfoResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<VisitorInfoResponse> call, Throwable t) {
                getVisitorsView.hideLoading();
                getVisitorsView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(VisitorInfoResponse visitorInfoResponse) {
        getVisitorsView.hideLoading();
        if (visitorInfoResponse == null || Utilities.isListEmpty(visitorInfoResponse.getData())) {
            getVisitorsView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        getVisitorsView.showUsers(visitorInfoResponse.getData());
    }
}
