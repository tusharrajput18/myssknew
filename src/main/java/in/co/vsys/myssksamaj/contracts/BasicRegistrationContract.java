package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.BasicRegistrationResponse;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class BasicRegistrationContract {

    public interface BasicRegistrationAPI {
        @FormUrlEncoded
        @POST("matrimonyapp.asmx/MatrimonyRegistration_BasicInformation")
        Call<BasicRegistrationResponse> registerBasicInfo(
                @Field("AppLoginId") String AppLoginId,
                @Field("FirstName") String FirstName,
                @Field("MiddleName") String MiddleName,
                @Field("LastName") String LastName,
                @Field("Gender") String Gender,
                @Field("DOB") String DOB,
                @Field("Mobile") String Mobile,
                @Field("OtherMobileNo") String OtherMobileNo,
                @Field("EmailId") String EmailId,
                @Field("MotherTongue") String MotherTongue,
                @Field("MarriedStatus") String MarriedStatus,
                @Field("Height") String Height,
                @Field("CountryId") String CountryId,
                @Field("StateId") String StateId,
                @Field("CityId") String CityId,
                @Field("AccountCreatedBy") String AccountCreatedBy,
                @Field("DeviceId") String DeviceId,
                @Field("Password") String Password,
                @Field("IdentityPhoto") String IdentityPhoto,
                @Field("IdentityPhotoExtension") String IdentityPhotoExtension,
                @Field("ParentCountryId") String ParentCountryId,
                @Field("ParentStateId") String ParentStateId,
                @Field("ParentCityId") String ParentCityId,
                @Field("AccountMangeBy") String AccountMangeBy,
                @Field("BasicInformationPercentage") String BasicInformationPercentage
//                @Field("Caste" ) String Caste,
//                @Field("SubCaste" ) String SubCaste
        );
    }

    public interface BasicRegistrationOps {
        void registerBasicInfo(String AppLoginId, String FirstName, String MiddleName, String LastName, String Gender,
                               String DOB, String Mobile, String OtherMobileNo, String EmailId,
                               String MotherTongue, String MarriedStatus, String Height, String CountryId,
                               String StateId, String CityId, String AccountCreatedBy,
                               String DeviceId, String Password, String IdentityPhoto, String IdentityPhotoExtension,
                               String ParentCountryId, String ParentStateId, String ParentCityId, String AccountMangeBy,
                               String BasicInformationPercentage);
//        , String Caste, String SubCaste);

        void onDataReceived(BasicRegistrationResponse basicRegistrationResponse);
    }

    public interface BasicRegistrationView extends MVPView {
        void showBasicInfoRegistered(String message, String memberId);
    }
}