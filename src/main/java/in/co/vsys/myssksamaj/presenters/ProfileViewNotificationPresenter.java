package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.ProfileViewNotificationContract;
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
public class ProfileViewNotificationPresenter implements ProfileViewNotificationContract.ProfileViewNotificationOps {

    private ProfileViewNotificationContract.ProfileViewNotificationAPI profileViewNotificationAPI;
    private ProfileViewNotificationContract.ProfileViewNotificationView profileViewNotificationView;

    public ProfileViewNotificationPresenter(ProfileViewNotificationContract.ProfileViewNotificationView profileViewNotificationView) {
        this.profileViewNotificationView = profileViewNotificationView;
        profileViewNotificationAPI = RestAdapterContainer.getInstance().create(ProfileViewNotificationContract.ProfileViewNotificationAPI.class);
    }

    @Override
    public void sendViewProfileNotification(String DeviceId, String Message, String AppName, String ImageURL,
                                            String UserId, String MemberId, String MemberName) {
        profileViewNotificationView.showLoading();
        profileViewNotificationAPI.sendViewProfileNotification(DeviceId, Message, AppName, ImageURL,
                UserId, MemberId, MemberName).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                profileViewNotificationView.hideLoading();
                profileViewNotificationView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        profileViewNotificationView.hideLoading();
        if(entity == null || Utilities.isEmpty(entity.getMessage())) {
            profileViewNotificationView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        profileViewNotificationView.showNotificationSent();
    }
}