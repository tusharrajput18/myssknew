package in.co.vsys.myssksamaj.presenters;

import android.content.Intent;
import android.widget.Toast;

import in.co.vsys.myssksamaj.contracts.BasicRegistrationContract;
import in.co.vsys.myssksamaj.model.responses.BasicRegistrationResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class BasicRegistrationPresenter implements BasicRegistrationContract.BasicRegistrationOps {

    private BasicRegistrationContract.BasicRegistrationAPI basicRegistrationAPI;
    private BasicRegistrationContract.BasicRegistrationView basicRegistrationView;

    public BasicRegistrationPresenter(BasicRegistrationContract.BasicRegistrationView basicRegistrationView) {
        this.basicRegistrationView = basicRegistrationView;
        basicRegistrationAPI = RestAdapterContainer.getInstance().create(BasicRegistrationContract.BasicRegistrationAPI.class);
    }

    @Override
    public void registerBasicInfo(String AppLoginId, String FirstName, String MiddleName, String LastName,
                                  String Gender, String DOB, String Mobile, String OtherMobileNo,
                                  String EmailId, String MotherTongue, String MarriedStatus, String Height,
                                  String CountryId, String StateId, String CityId,
                                  String AccountCreatedBy, String DeviceId, String Password, String IdentityPhoto,
                                  String IdentityPhotoExtension, String ParentCountryId, String ParentStateId,
                                  String ParentCityId, String AccountMangeBy, String BasicInformationPercentage) {
//                                  String Caste, String SubCaste) {

        basicRegistrationAPI.registerBasicInfo(AppLoginId, FirstName, MiddleName, LastName, Gender,
                DOB, Mobile, OtherMobileNo, EmailId, MotherTongue, MarriedStatus, Height,
                CountryId, StateId, CityId, AccountCreatedBy, DeviceId, Password, IdentityPhoto,
                IdentityPhotoExtension, ParentCountryId, ParentStateId, ParentCityId, AccountMangeBy,
                BasicInformationPercentage)
//                , Caste, SubCaste)
                .enqueue(new Callback<BasicRegistrationResponse>() {
                    @Override
                    public void onResponse(Call<BasicRegistrationResponse> call, Response<BasicRegistrationResponse> response) {

                        onDataReceived(response.body());
                    }

                    @Override
                    public void onFailure(Call<BasicRegistrationResponse> call, Throwable t) {
                        basicRegistrationView.hideLoading();
                        basicRegistrationView.showError("This Mobile Number Already Use In Matrimony Registration");
                    }
                });
    }

    @Override
    public void onDataReceived(BasicRegistrationResponse basicRegistrationResponse) {
        try {
            basicRegistrationView.hideLoading();
            if (basicRegistrationResponse == null ||Utilities.isListEmpty(basicRegistrationResponse.getData())) {

              //  basicRegistrationView.showError("Registration error");
            }
            basicRegistrationView.showBasicInfoRegistered(basicRegistrationResponse.getMessage(), basicRegistrationResponse.getData().get(0).getMemberId());




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}