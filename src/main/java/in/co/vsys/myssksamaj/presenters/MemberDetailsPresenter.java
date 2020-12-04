package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.MemberDetailsContract;
import in.co.vsys.myssksamaj.model.responses.MemberDetailsResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class MemberDetailsPresenter implements MemberDetailsContract.MemberDetailsOps {
    private MemberDetailsContract.MemberDetailsAPI memberDetailsAPI;
    private MemberDetailsContract.MemberDetailsView memberDetailsView;

    public MemberDetailsPresenter(MemberDetailsContract.MemberDetailsView memberDetailsView) {
        this.memberDetailsView = memberDetailsView;
        memberDetailsAPI = RestAdapterContainer.getInstance().create(MemberDetailsContract.MemberDetailsAPI.class);
    }

    @Override
    public void getMemberDetails(String UniqueId) {
        memberDetailsView.showLoading();
        memberDetailsAPI.getMemberDetails(UniqueId).enqueue(new Callback<MemberDetailsResponse>() {
            @Override
            public void onResponse(Call<MemberDetailsResponse> call, Response<MemberDetailsResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<MemberDetailsResponse> call, Throwable t) {
                memberDetailsView.hideLoading();
                memberDetailsView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });

    }

    @Override
    public void onDataReceived(MemberDetailsResponse memberDetailsResponse) {
        try {
            memberDetailsView.hideLoading();
            if(memberDetailsResponse == null || Utilities.isListEmpty(memberDetailsResponse.getData())){
                memberDetailsView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
                return;
            }
            memberDetailsView.showMemberDetails(memberDetailsResponse.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
