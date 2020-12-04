package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.ImagesDataModel;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.responses.ImagesDataResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class ImagesContract {

    public interface LoadImageAPI {
        String MEMBER_ID = "MemberId";

        @GET("/matrimonyapp.asmx/Matrimony_Member_AllPhotoList")
        Call<ImagesDataResponse> getAllImages(@Query(MEMBER_ID) String memberId);
    }

    public interface ImageOps {
        void getAllImages(String memberId);

        void uploadImages(String MEMBER_ID,
                          String PROFILE_PHOTO,
                          String PROFILE_EXTENSION,
                          String mainphoto_showTo,
                          String mainphoto_status,
                          String PHOTO_ONE,
                          String PHOTO_ONE_EXTENSION,
                          String photo_showTo1,
                          String photo_status1,

                          String PHOTO_TWO,
                          String PHOTO_TWO_EXTENSION,
                          String photo_showTo2,
                          String photo_status2,

                          String PHOTO_THREE,
                          String PHOTO_THREE_EXTENSION,
                          String photo_showTo3,
                          String photo_status3,

                          String PHOTO_FOUR,
                          String PHOTO_FOUR_EXTENSION,
                          String photo_showTo4,
                          String photo_status4,

                          String PHOTO_FIVE,
                          String PHOTO_FIVE_EXTENSION,
                          String photo_showTo5,
                          String photo_status5,

                          String PHOTO_SIX,
                          String PHOTO_SIX_EXTENSION,
                          String photo_showTo6,
                          String photo_status6,

                          String PHOTO_SEVEN,
                          String PHOTO_SEVEN_EXTENSION,
                          String photo_showTo7,
                          String photo_status7,

                          String PHOTO_EIGHT,
                          String PHOTO_EIGHT_EXTENSION,
                          String photo_showTo8,
                          String photo_status8,

                          String PHOTO_NINE,
                          String PHOTO_NINE_EXTENSION,
                          String photo_showTo9,
                          String photo_status9,

                          String PHOTO_TEN,
                          String PHOTO_TEN_EXTENSION,
                          String photo_showTo10,
                          String photo_status10,

                          int PHOTO_PROFILE_PERCENTAGE);

        void onDataReceived(ImagesDataResponse imagesDataResponse);

        void onDataReceived(String uploaded);
    }

    public interface UploadImageAPI {

        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/MatrimonyRegistration_ProfilePictureAndOtherPhoto")
        Call<Entity> uploadImages(@Field("MemberId") String MEMBER_ID,
                                  @Field("MainProfilePhoto") String profileImage,
                                  @Field("MainProfilePhotoExtension") String PROFILE_EXTENSION,
                                  @Field("mainphoto_showTo") String mainphoto_showTo,
                                  @Field("mainphoto_status") String mainphoto_status,


                                  @Field("Photo1") String PHOTO_ONE,
                                  @Field("Photo1Extension") String PHOTO_ONE_EXTENSION,
                                  @Field("photo_showTo1") String photo_showTo1,
                                  @Field("photo_status1") String photo_status1,


                                  @Field("Photo2") String PHOTO_TWO,
                                  @Field("Photo2Extension") String PHOTO_TWO_EXTENSION,
                                  @Field("photo_showTo2") String photo_showTo2,
                                  @Field("photo_status2") String photo_status2,


                                  @Field("Photo3") String PHOTO_THREE,
                                  @Field("Photo3Extension") String PHOTO_THREE_EXTENSION,
                                  @Field("photo_showTo3") String photo_showTo3,
                                  @Field("photo_status3") String photo_status3,

                                  @Field("Photo4") String PHOTO_FOUR,
                                  @Field("Photo4Extension") String PHOTO_FOUR_EXTENSION,
                                  @Field("photo_showTo4") String photo_showTo4,
                                  @Field("photo_status4") String photo_status4,

                                  @Field("Photo5") String PHOTO_FIVE,
                                  @Field("Photo5Extension") String PHOTO_FIVE_EXTENSION,
                                  @Field("photo_showTo5") String photo_showTo5,
                                  @Field("photo_status5") String photo_status5,

                                  @Field("Photo6") String PHOTO_SIX,
                                  @Field("Photo6Extension") String PHOTO_SIX_EXTENSION,
                                  @Field("photo_showTo6") String photo_showTo6,
                                  @Field("photo_status6") String photo_status6,

                                  @Field("Photo7") String PHOTO_SEVEN,
                                  @Field("Photo7Extension") String PHOTO_SEVEN_EXTENSION,
                                  @Field("photo_showTo7") String photo_showTo7,
                                  @Field("photo_status7") String photo_status7,

                                  @Field("Photo8") String PHOTO_EIGHT,
                                  @Field("Photo8Extension") String PHOTO_EIGHT_EXTENSION,
                                  @Field("photo_showTo8") String photo_showTo8,
                                  @Field("photo_status8") String photo_status8,

                                  @Field("Photo9") String PHOTO_NINE,
                                  @Field("Photo9Extension") String PHOTO_NINE_EXTENSION,
                                  @Field("photo_showTo9") String photo_showTo9,
                                  @Field("photo_status9") String photo_status9,

                                  @Field("Photo10") String PHOTO_TEN,
                                  @Field("Photo10Extension") String PHOTO_TEN_EXTENSION,
                                  @Field("photo_showTo10") String photo_showTo10,
                                  @Field("photo_status10") String photo_status10,

                                  @Field("PhotoInformationPercentage") int PHOTO_PROFILE_PERCENTAGE);
    }

    public interface ImagesView extends MVPView {
        void showImages(ImagesDataModel imagesDataModel);
    }

    public interface UploadImagesView extends MVPView {
        void showImageUploaded();

        void showImageUploadFailed();
    }
}