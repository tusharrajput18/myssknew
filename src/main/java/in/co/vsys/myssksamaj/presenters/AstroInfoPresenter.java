package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.AstroInfoUpdateContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstroInfoPresenter implements AstroInfoUpdateContract.AstroInfoUserInfoOps {

    private AstroInfoUpdateContract.AstroInfoUpdateAPI infoUpdateAPI;
    private AstroInfoUpdateContract.AstroInfoView astroInfoView;

    public AstroInfoPresenter(AstroInfoUpdateContract.AstroInfoView astroInfoView) {
        this.astroInfoView = astroInfoView;
        infoUpdateAPI = RestAdapterContainer.getInstance().create(AstroInfoUpdateContract.AstroInfoUpdateAPI.class);
    }


    @Override
    public void getRequestUpdate(String memberId,String BirthTime,String BirthCountry,String BirthState,String Gotra,String Rashi,String Nakshtra,String Charan,String Naadi,String Gan,String BirthPlace,String KundaliPhoto,String KundaliPhotoExtension,String HoroScopeInformationPercentage,String isManglik,String occupation,String income,String physicalchallenge) {
        astroInfoView.showLoading();
        infoUpdateAPI.updateAstroInfo(memberId,BirthTime,BirthCountry,BirthState,Gotra,Rashi,Nakshtra,Charan,Naadi,Gan,BirthPlace,KundaliPhoto,KundaliPhotoExtension,HoroScopeInformationPercentage,isManglik,occupation,income,physicalchallenge).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());

            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {

                astroInfoView.hideLoading();
                astroInfoView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });

    }

    @Override
    public void onDataReceived(Entity entity) {
        try {
            astroInfoView.showLoading();
            if (entity == null) {
                astroInfoView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
                return;
            }
            astroInfoView.showAllUserInfo(entity);
            astroInfoView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
