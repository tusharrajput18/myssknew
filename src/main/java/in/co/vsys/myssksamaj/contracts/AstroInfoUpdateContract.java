package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class AstroInfoUpdateContract {

    public interface AstroInfoUpdateAPI{

        @FormUrlEncoded
        @POST("matrimonyapp.asmx/MatrimonyRegistration_Update_HoroScopeInformation")
        Call<Entity> updateAstroInfo( @Field("MemberId")String memberId,
                                      @Field("BirthTime")String birthTime,
                                      @Field("BirthCountry")String birthCountry,
                                      @Field("BirthState")String birthState,
                                      @Field("Gotra")String gotra,
                                      @Field("Rashi")String rashi,
                                      @Field("Nakshtra")String nakshtra,
                                      @Field("Charan")String charan,
                                      @Field("Naadi")String naadi,
                                      @Field("Gan")String gan,
                                      @Field("BirthPlace")String birthPlace,
                                      @Field("KundaliPhoto")String kundaliPhoto,
                                      @Field("KundaliPhotoExtension")String kundaliPhotoExtension,
                                      @Field("HoroScopeInformationPercentage")String horoScopeInformationPercentage,
                                      @Field("isManglik")String isManglik,
                                      @Field("occupation")String occupation,
                                      @Field("income")String income,
                                      @Field("physicalchallenge")String physicalchallenge);
    }

    public interface AstroInfoUserInfoOps {

        void getRequestUpdate(String memberId,
                              String BirthTime,
                              String BirthCountry,
                              String BirthState,
                              String Gotra,
                              String Rashi,
                              String Nakshtra,
                              String Charan,
                              String Naadi,
                              String Gan,
                              String BirthPlace,
                              String KundaliPhoto,
                              String KundaliPhotoExtension,
                              String HoroScopeInformationPercentage,
                              String isManglik,
                              String occupation,
                              String income,
                              String physicalchallenge);

        void onDataReceived(Entity entity);
    }

    public interface AstroInfoView extends MVPView {
        void showAllUserInfo(Entity entity);
    }
}
