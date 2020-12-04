package in.co.vsys.myssksamaj.businesservices;

import in.co.vsys.myssksamaj.businessmodels.BusinessModel1;
import in.co.vsys.myssksamaj.businessmodels.CommonUserModel;
import in.co.vsys.myssksamaj.businessmodels.JsonResult;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.businessmodels.SellectAllBusinessType;
import in.co.vsys.myssksamaj.businessmodels.SellectAllCity;
import in.co.vsys.myssksamaj.businessmodels.SellectAllCountry;
import in.co.vsys.myssksamaj.businessmodels.SellectAllState;
import in.co.vsys.myssksamaj.businessmodels.SellectAllSubCategoryType;
import in.co.vsys.myssksamaj.mainMobileModel.OtpModel;
import in.co.vsys.myssksamaj.mainMobileModel.RegistrationModel;
import in.co.vsys.myssksamaj.model.AppUpdateModel;
import in.co.vsys.myssksamaj.model.SliderModel;
import in.co.vsys.myssksamaj.modelAdvertisement.AdvertisementByType;
import in.co.vsys.myssksamaj.modelAdvertisement.ModelAdvertisementType;
import in.co.vsys.myssksamaj.modelAdvertisement.SelectCummentsAdvertisementId;
import in.co.vsys.myssksamaj.newsmodels.AllNewsTypeModel;
import in.co.vsys.myssksamaj.newsmodels.SelectCummentsNewsId;
import in.co.vsys.myssksamaj.newsmodels.SelectNewsUsingNewsId;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BusinessAPIServices {



    @GET("SelectAllBusinessType")
    Call<SellectAllBusinessType> getAllBusinessImages();

    @GET("SelectAllCountry")
    Call<SellectAllCountry> getAllCountry();

    @GET("SelectAllState")
    Call<SellectAllState> getAllState();

    @FormUrlEncoded()
    @POST("SelectAllCityMasterUsingStateId")
    Call<SellectAllCity> getallCity(@Field("StateId")String s);

    @FormUrlEncoded()
    @POST("SelectSubCategoryUsingCategoryId")
    Call<SellectAllSubCategoryType> getAllSubCategory(@Field("id")String s);

    @FormUrlEncoded()
    @POST("SelectServicesUsingSubCategoryId")
    Call<SelectServicesUsingSubCategoryId> getAllServiceUsingSubCategoryIds(@Field("id")String s);

    @FormUrlEncoded()
    @POST("Business_Insert")
    Call<JsonResult> insertBusiness(@Field("AppLoginId")String AppLoginId,@Field("BusinessTypeId")String BusinessTypeId,@Field("BusinessSubTypeId")String BusinessSubTypeId,@Field("BusinessName")String BusinessName,@Field("Address")String Address,@Field("Phone1")String Phone1,@Field("Phone2")String Phone2,@Field("Webiste")String Webiste,@Field("Services")String Services,@Field("Aboutus")String Aboutus,
                                    @Field("Longitude")String Longitude,@Field("Latitude")String Latitude,@Field("ContactPersonName")String ContactPersonName,@Field("Image1")String Image1,@Field("Image1Extension")String Image1Extension,@Field("Image2")String Image2,@Field("Image2Extension")String Image2Extension,@Field("Image3")String Image3,@Field("Image3Extension")String Image3Extension,@Field("Image4")String Image4,
                                    @Field("Image4Extension")String Image4Extension,@Field("Image5")String Image5,@Field("Image5Extension")String Image5Extension,@Field("Image6")String Image6,@Field("Image6Extension")String Image6Extension,@Field("Image7")String Image7,@Field("Image7Extension")String Image7Extension,@Field("Image8")String Image8,@Field("Image8Extension")String Image8Extension,@Field("Image9")String Image9,
                                    @Field("Image9Extension")String Image9Extension,@Field("description")String description,@Field("EmailId")String EmailId,@Field("Country")String Country,@Field("State")String State,@Field("City")String City,@Field("taluka")String taluka,@Field("Monday")String Monday,@Field("Tuesday")String Tuesday,@Field("Wednesday")String Wednesday,@Field("Thursday")String Thursday,
                                    @Field("Friday")String Friday,@Field("Saturday")String Saturday,@Field("Sunday")String Sunday);

    @FormUrlEncoded()
    @POST("/matrimonyapp.asmx/MainApp_Registration")
    Call<RegistrationModel> insertCommonRegister(@Field("FirstName")String fname, @Field("MiddleName") String mname, @Field("LastName") String lname, @Field("Mobile") String mobile, @Field("Email") String emailid, @Field("Password") String password, @Field("IdProofPhoto") String image_1, @Field("IdProofPhotoExtension") String jpg, @Field("AadharImage") String image_2, @Field("AadharImageExtension") String jpg1, @Field("Address") String address, @Field("Country") String contryid, @Field("State") String stateid, @Field("City") String cityid);

    @FormUrlEncoded()
    @POST("/matrimonyapp.asmx/MainAppUserLogin")
    Call<CommonUserModel> loginUser(@Field("mobile")String mobile,@Field("password") String password);

    @FormUrlEncoded()
    @POST("SelectBusinessUsingServiceId")
    Call<BusinessModel1> getAllBusinessUsngIds(@Field("id")String s);

    @GET("SelectAllNewsType")
    Call<AllNewsTypeModel> getAllNewstype();

    @FormUrlEncoded()
    @POST("News_Insert")
    Call<JsonResult> insertNews(@Field("AppLoginId")String s,@Field("NewsTypeId") String newstypeid,@Field("Shortdescription") String shortdisc,@Field("Longdescription") String longdisc,@Field("Location") String s1,@Field("Image1") String image_1,@Field("Image1Extension") String jpg,@Field("Image2") String image_2,@Field("Image2Extension") String jpg1);

    @FormUrlEncoded()
    @POST("SelectNewsUsingNewsType")
    Call<SelectNewsUsingNewsId> getAllNewsbyTypeId(@Field("NewsTypeId")String s,@Field("userid")String userid);

    @GET("SelectAlladvertisementType")
    Call<ModelAdvertisementType> getAllAdvertisementType();

    @FormUrlEncoded()
    @POST("advertisement_Insert")
    Call<JsonResult> insertAdvertisement(@Field("AppLoginId")String s,@Field("advertisementTypeId") String newstypeid,@Field("Shortdescription") String shortdisc,@Field("Longdescription") String longdisc,@Field("Location") String s1,@Field("Image1") String image_1,@Field("Image1Extension") String jpg,@Field("Image2") String image_2,@Field("Image2Extension") String jpg1);

    @FormUrlEncoded()
    @POST("SelectadvertisementUsingadvertisementType")
    Call<AdvertisementByType> getallAdvertisementByType(@Field("advertisementTypeId")String str,@Field("userid")String userid);

    @FormUrlEncoded()
    @POST("SearchNews")
    Call<SelectNewsUsingNewsId> getAllNews(@Field("NewsTypeId")String s,@Field("Date") String s1,@Field("page") String page,@Field("userid") String userid);

    @FormUrlEncoded()
    @POST("Searchadvertisement")
    Call<AdvertisementByType> getallAdvertisement(@Field("advertisementTypeId")String s,@Field("Date") String s1,@Field("page")String page,@Field("userid")String userid);

    @GET("/matrimonyapp.asmx/SelectAllSlider")
    Call<SliderModel> getAllSliderImages();

    @FormUrlEncoded()
    @POST("/matrimonyapp.asmx/loginUserCount_insert")
    Call<JsonResult> insertLoginusercount(@Field("uid")String uid,@Field("IMEIno") String IMEIno);

    @FormUrlEncoded()
    @POST("SelectBusinessUsingBusinessNameKeyWord")
    Call<BusinessModel1> getAllBusinessUsngKeyWord(@Field("BusinessSearchKeyword")String str);

    @GET("/matrimonyapp.asmx/getAppVersionNumber")
    Call<AppUpdateModel> getAllversionNo();

    @FormUrlEncoded()
    @POST("NewsLike_Insert")
    Call<JsonResult> insertNewsLike(@Field("AppLoginId")String AppLoginId,@Field("NewsId") String NewsId,@Field("likevalue") String likevalue);

    @FormUrlEncoded()
    @POST("AdLike_Insert")
    Call<JsonResult> insertAdvertisementLike(@Field("AppLoginId")String userid,@Field("AdvertisementId") String AdvertisementId,@Field("likevalue") String aTrue);

    @FormUrlEncoded()
    @POST("AdComment_Insert")
    Call<JsonResult> insertAdvertisementCumments(@Field("AppLoginId")String registereduserid,@Field("AdvertisementId") String advertisementid,@Field("comment") String cumments);

    @FormUrlEncoded()
    @POST("NewsComment_Insert")
    Call<JsonResult> insertNewsCumments(@Field("AppLoginId")String registereduserid,@Field("NewsId") String newsid,@Field("comment") String cumments);

    @FormUrlEncoded()
    @POST("SelectCommentsUsingNewsId")
    Call<SelectCummentsNewsId> getNewsCummentsByNewsid(@Field("NewsId")String s);

    @FormUrlEncoded()
    @POST("SelectCommentsUsingadvertisementId")
    Call<SelectCummentsAdvertisementId> getAdvertisementCummentsByNewsid(@Field("advertisementId")String s);

    @FormUrlEncoded()
    @POST("/matrimonyapp.asmx/getOTPNo")
    Call<OtpModel> logingetOtp(@Field("mobileno")String mobil);

    @FormUrlEncoded()
    @POST("/matrimonyapp.asmx/UpdateMainAppDevicedId")
    Call<JsonResult> insertDeviceId(@Field("MemberId")String user_id,@Field("DevicedId") String dev_id);
}
