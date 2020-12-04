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
public class ParentRegistrationContract {
    public interface ParentRegistrationAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/MatrimonyRegistration_BasicInformation")
        Call<BasicRegistrationResponse> registerParent(
                @Field("AppLoginId") int MAIN_USER_ID,
                @Field("FirstName") String FIRST_NAME,
                @Field("MiddleName") String MIDDLE_NAME,
                @Field("LastName") String LAST_NAME,
                @Field("Gender") String GENDER,
                @Field("DOB") String DATE_OF_BIRTH,
                @Field("Mobile") String MOBILE_NUMBER,
                @Field("OtherMobileNo") String OTHER_MOBILE,
                @Field("EmailId") String EMAIL_ID,
                @Field("MotherTongue") int MOTHER_TONGUE,
                @Field("MarriedStatus") String MARRIED_STATUS,
                @Field("Height") String HEIGHT,
                @Field("Address") String ADDRESS,
                @Field("CountryId") int COUNTRY_ID,
                @Field("StateId") int STATE_ID,
                @Field("CityId") int CITY_ID,
                @Field("AccountCreatedBy") String FOR_PROFILE_CREATED,
                @Field("DeviceId") String DEVICE_ID,
                @Field("Password") String PASSWORD,
                @Field("IdentityPhoto") String PHOTO_IDENTITY,
                @Field("IdentityPhotoExtension") String PHOTO_EXTENSION,
                @Field("ParentCountryId") int PARENT_COUNTRY_ID,
                @Field("ParentStateId") int PARENT_STATE_ID,
                @Field("ParentCityId") int PARENT_CITY_ID,
                @Field("AccountMangeBy") String ACCOUNT_MANAGED_BY,
                @Field("BasicInformationPercentage") int BASIC_PROFILE_PERCENTAGE
        );
    }

    public interface RegisterParentOps {
        void registerParent(
                int MAIN_USER_ID,
                String FIRST_NAME,
                String MIDDLE_NAME,
                String LAST_NAME,
                String GENDER,
                String DATE_OF_BIRTH,
                String MOBILE_NUMBER,
                String OTHER_MOBILE,
                String EMAIL_ID,
                int MOTHER_TONGUE,
                String MARRIED_STATUS,
                String HEIGHT,
                String ADDRESS,
                int COUNTRY_ID,
                int STATE_ID,
                int CITY_ID,
                String FOR_PROFILE_CREATED,
                String DEVICE_ID,
                String PASSWORD,
                String PHOTO_IDENTITY,
                String PHOTO_EXTENSION,
                int PARENT_COUNTRY_ID,
                int PARENT_STATE_ID,
                int PARENT_CITY_ID,
                String ACCOUNT_MANAGED_BY,
                int BASIC_PROFILE_PERCENTAGE
        );

        void onDataReceived(BasicRegistrationResponse entity);
    }

    public interface RegisterParentView extends MVPView{
        void showParentRegistered(String message, String memberId);
        void showParentRegisteredError();
    }
}