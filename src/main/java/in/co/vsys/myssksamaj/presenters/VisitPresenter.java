package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.VisitContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class VisitPresenter implements VisitContract.VisitOps {

    private VisitContract.VisitAPI visitAPI;
    private VisitContract.VisitView visitView;

    public VisitPresenter(VisitContract.VisitView visitView) {
        this.visitView = visitView;
        visitAPI = RestAdapterContainer.getInstance().create(VisitContract.VisitAPI.class);
    }

    @Override
    public void performVisit(String MemberId, String ProfileViewMemberId) {
        visitView.showLoading();
        visitAPI.performVisit(MemberId, ProfileViewMemberId).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                visitView.hideLoading();
                visitView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        visitView.hideLoading();
        if (entity == null || Utilities.isEmpty(entity.getMessage())) {
            visitView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        visitView.showVisitPerformed(entity.getMessage());
    }
}
