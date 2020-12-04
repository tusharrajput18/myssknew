package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class EditUserContract {

    public interface EditUserAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/MatrimonyRegistration_Update_BasicInformation")
        Call<Entity> updateUser(
                @Field("MemberId") int memberId,
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
                @Field("Address") String Address,
                @Field("CountryId") int CountryId,
                @Field("StateId") int StateId,
                @Field("CityId") int CityId,
                @Field("DeviceId") String DeviceId,
                @Field("AccountMangeBy") String AccountMangeBy,
                @Field("IdentityPhoto") String IdentityPhoto,
                @Field("IdentityPhotoExtension") String IdentityPhotoExtension,
                @Field("Caste") String Caste,
                @Field("SubCaste") String SubCaste
        );
    }

    public interface UpdateUserOps{
        void updateUser(int memberId,
                        String FirstName,
                        String MiddleName,
                        String LastName,
                        String Gender,
                        String DOB,
                        String Mobile,
                        String OtherMobileNo,
                        String EmailId,
                        String MotherTongue,
                        String MarriedStatus,
                        String Height,
                        String Address,
                        int CountryId,
                        int StateId,
                        int CityId,
                        String DeviceId,
                        String AccountMangeBy,
                        String IdentityPhoto,
                        String IdentityPhotoExtension,
                        String Caste,
                        String SubCaste);

        void onDataReceived(Entity entity);
    }

    public interface UpdateUserView extends MVPView{
        void showUserEditted(String message);
    }
}