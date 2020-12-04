package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.EditUserContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class UpdateUserPresenter implements EditUserContract.UpdateUserOps {

    private EditUserContract.EditUserAPI editUserAPI;
    private EditUserContract.UpdateUserView updateUserView;

    public UpdateUserPresenter(EditUserContract.UpdateUserView updateUserView) {
        this.updateUserView = updateUserView;
        editUserAPI = RestAdapterContainer.getInstance().create(EditUserContract.EditUserAPI.class);
    }

    @Override
    public void updateUser(int memberId, String FirstName, String MiddleName, String LastName,
                           String Gender, String DOB, String Mobile, String OtherMobileNo, String EmailId,
                           String MotherTongue, String MarriedStatus, String Height, String Address, int CountryId,
                           int StateId, int CityId, String DeviceId, String AccountMangeBy,String IdentityPhoto,
                           String IdentityPhotoExtension,String Caste, String SubCaste) {

        updateUserView.showLoading();
        editUserAPI.updateUser(memberId, FirstName, MiddleName, LastName, Gender, DOB, Mobile, OtherMobileNo,
                EmailId, MotherTongue, MarriedStatus, Height, Address, CountryId, StateId, CityId, DeviceId, AccountMangeBy,
                IdentityPhoto, IdentityPhotoExtension, Caste, SubCaste).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                updateUserView.hideLoading();
                updateUserView.showError("Could not update");
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        updateUserView.hideLoading();
        if (entity == null || entity.getMessage() == null) {

            updateUserView.showError("Could not update");
            return;
        }
        updateUserView.showUserEditted(entity.getMessage());
    }
}