package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.ParentRegistrationContract;
import in.co.vsys.myssksamaj.model.responses.BasicRegistrationResponse;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class RegisterParentPresenter implements ParentRegistrationContract.RegisterParentOps {

    private ParentRegistrationContract.ParentRegistrationAPI parentRegistrationAPI;
    private ParentRegistrationContract.RegisterParentView registerParentView;

    public RegisterParentPresenter(ParentRegistrationContract.RegisterParentView registerParentView) {
        this.registerParentView = registerParentView;
        parentRegistrationAPI = RestAdapterContainer.getInstance().create(ParentRegistrationContract.ParentRegistrationAPI.class);
    }

    @Override
    public void registerParent(int MAIN_USER_ID, String FIRST_NAME, String MIDDLE_NAME,
                               String LAST_NAME, String GENDER, String DATE_OF_BIRTH,
                               String MOBILE_NUMBER, String OTHER_MOBILE, String EMAIL_ID,
                               int MOTHER_TONGUE, String MARRIED_STATUS, String HEIGHT,
                               String ADDRESS,
                               int COUNTRY_ID, int STATE_ID, int CITY_ID, String FOR_PROFILE_CREATED, String DEVICE_ID,
                               String PASSWORD, String PHOTO_IDENTITY, String PHOTO_EXTENSION,
                               int PARENT_COUNTRY_ID, int PARENT_STATE_ID, int PARENT_CITY_ID,
                               String ACCOUNT_MANAGED_BY, int BASIC_PROFILE_PERCENTAGE) {

        registerParentView.showLoading();
        parentRegistrationAPI.registerParent(MAIN_USER_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME,
                GENDER, DATE_OF_BIRTH, MOBILE_NUMBER, OTHER_MOBILE, EMAIL_ID, MOTHER_TONGUE,
                MARRIED_STATUS, HEIGHT,
                ADDRESS,
                COUNTRY_ID, STATE_ID, CITY_ID,
                FOR_PROFILE_CREATED, DEVICE_ID, PASSWORD, PHOTO_IDENTITY, PHOTO_EXTENSION,
                PARENT_COUNTRY_ID, PARENT_STATE_ID, PARENT_CITY_ID, ACCOUNT_MANAGED_BY, BASIC_PROFILE_PERCENTAGE).enqueue(new Callback<BasicRegistrationResponse>() {
            @Override
            public void onResponse(Call<BasicRegistrationResponse> call, Response<BasicRegistrationResponse> response) {
                if (response == null || response.body() == null) {
                    registerParentView.showError("Could not register parent");
                    return;
                }

                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<BasicRegistrationResponse> call, Throwable t) {
                registerParentView.hideLoading();
                registerParentView.showError("Could not register parent");
            }
        });
    }

    @Override
    public void onDataReceived(BasicRegistrationResponse basicRegistrationResponse) {
        try {
            registerParentView.hideLoading();
            if (basicRegistrationResponse == null || Utilities.isEmpty(basicRegistrationResponse.getMessage())) {
                registerParentView.showParentRegisteredError();
                return;
            }

            registerParentView.showParentRegistered(basicRegistrationResponse.getMessage(),basicRegistrationResponse.getData().get(0).getMemberId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}