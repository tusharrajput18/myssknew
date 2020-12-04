package in.co.vsys.myssksamaj.utils;

import in.co.vsys.myssksamaj.model.rest_api.APIConstants;

/**
 * Created by Jack on 01/12/2017.
 */

public class Config {

   /* public static final String mainLoginUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MainAppUserLogin";
    public static final String forgotPasswordUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MainApp_ForgetPassword";
    public static final String mainRegistrationUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MainApp_Registration";
    public static final String checkMatromonyUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_MainAppId";
    public static final String matroBasicRegUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_BasicInformation";
    public static final String motherTongueUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllMotherTongueMaster";
    public static final String countryListUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllCountry";
    public static final String stateListUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllStateUsingCountryId";
    public static final String multipleStateCityUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllCityMasterUsingMultipleStateId";
    public static final String cityListUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllCityMasterUsingStateId";
    public static final String eductionMasterUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllEducation";
    public static final String eductionInUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllEducationInMaster";
    public static final String workingWithUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllEducationWithMaster";
    public static final String alloccuptionUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllOccupation";
    public static final String annualIncomeUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllAnnualIncome";
    public static final String sendEductionUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_HigherEducationInformation";
    public static final String sendFamilyInfoUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_FamilyInformation";
    public static final String sendDesiredPartnerUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_DesiredPartnerInformation";
    public static final String sendHorshopeDetailsUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_HoroScopeInformation";
    public static final String uploadImagesUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_ProfilePictureAndOtherPhoto";
    public static final String uploadCandidateIntroUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_CandidateIntroduction";
    public static final String recentlyJointUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_NewlyJointUserList";
    public static final String recentlyProfileViewUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_RecentlyProfileVisitedList";
    public static final String recentlyProfileVisitorUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_Who_Visited_MyProfile";
    public static final String member_matches_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_MatchList";
    public static final String sendMemberIdUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Member_Insert_ProfileVisited";
    public static final String userDataByUniqueIdUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_UniqueId";
    public static final String profileVisitedUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Member_Insert_ProfileVisited";
    public static final String URL_SEND_REQUEST = "http://app.myssksamaj.com/matrimonyapp.asmx/Member_SendRequestUpdateStatus";
    public static final String IMEAGES_URL = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_AllPhotoList";
    public static final String addShortlistUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Member_ShortListUpdateStatus";
    public static final String shortlistedmemberUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_MemberShortListedList";
    public static final String searchByIdUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_UniqueId";
    public static final String searchByFilterUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Search";
    public static final String allInfoUsingMemeberIdUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation";
    public static final String invitationSendByMeUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_SendRequestList";
    public static final String invitationReceivedForMeUrl = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_Who_SendRequest_You_List";
    public static final String member_basic_edit_url = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_Update_BasicInformation";
    public static final String member_profession_edit_url = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_Update_HigherEducationInformation";
    public static final String member_family_edit_url = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_Update_FamilyInformation";
    public static final String member_astro_edit_url = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_Update_HoroScopeInformation";
    public static final String member_looking_edit_url = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_Update_DesiredPartnerInformation";
    public static final String insert_business_url = "http://app.myssksamaj.com/business.asmx/Business_Insert";
    public static final String business_type_url = "http://app.myssksamaj.com/business.asmx/SelectAllBusinessType";
    public static final String business_list_url = "http://app.myssksamaj.com/business.asmx/SelectBusinessUsingMainAppId";
    public static final String business_details_url = "http://app.myssksamaj.com/business.asmx/SelectBusinessUsingBusinessId";
    public static final String business_keyword_search_url = "http://app.myssksamaj.com/business.asmx/SelectBusinessUsingBusinessNameKeyWord";
    public static final String business_list_busiType_url = "http://app.myssksamaj.com/business.asmx/SelectBusinessUsingBusinessType";
    public static final String popular_business_list_url = "http://app.myssksamaj.com/business.asmx/SelectAllBusinessTypePopular";
    public static final String matri_near_by_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Location_Insert";
    public static final String matri_near_by_update_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Member_Location_Update";
    public static final String delete_matrimony_location_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_Location_Delete_Using_MemberId";
    public static final String matrimony_login_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_UserLogin";
    public static final String matrimony_gotra_url = "http://app.myssksamaj.com/matrimonyapp.asmx/SelectAllGotraMaster";
    public static final String matrimony_life_style = "http://app.myssksamaj.com/matrimonyapp.asmx/MatrimonyRegistration_LifeStyleInformation";
    public static final String invite_shortlist_view_count_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Matrimony_AcceptanceAndMatchCount";
    public static final String all_count_view_url = "http://app.myssksamaj.com/matrimonyapp.asmx/Member_SendRequest_ProfileVisited_ShortedListed_Blocked_Ignored_CountList";*/

    public static final String mainLoginUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MainAppUserLogin";
    public static final String forgotPasswordUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MainApp_ForgetPassword";
    public static final String mainRegistrationUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MainApp_Registration";
    public static final String checkMatromonyUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_MainAppId";
    public static final String matroBasicRegUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_BasicInformation";
    public static final String motherTongueUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllMotherTongueMaster";
    public static final String countryListUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllCountry";
    public static final String stateListUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllStateUsingCountryId";
    public static final String multipleStateCityUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllCityMasterUsingMultipleStateId";
    public static final String cityListUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllCityMasterUsingStateId";
    public static final String eductionMasterUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllEducation";
    public static final String eductionInUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllEducationInMaster";
    public static final String workingWithUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllEducationWithMaster";
    public static final String alloccuptionUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllOccupation";
    public static final String annualIncomeUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllAnnualIncome";
    public static final String sendEductionUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_HigherEducationInformation";
    public static final String sendFamilyInfoUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_FamilyInformation";
    public static final String sendDesiredPartnerUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_DesiredPartnerInformation";
    public static final String sendHorshopeDetailsUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_HoroScopeInformation";
    public static final String uploadImagesUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_ProfilePictureAndOtherPhoto";
    public static final String uploadCandidateIntroUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_CandidateIntroduction";
    public static final String recentlyJointUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_NewlyJointUserList";
    public static final String recentlyJointUrl_paging = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_NewlyJointUserList_paging";

    public static final String recentlyProfileViewUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_RecentlyProfileVisitedList";
    public static final String recentlyProfileViewUrl_paging = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_RecentlyProfileVisitedList_paging";
    public static final String recentlyProfileVisitorUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_Who_Visited_MyProfile";
    public static final String getVisitorsByRole = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/getVisitorsByRole";

    public static final String member_matches_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_MatchList";
    public static final String member_matches_url_paging = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_MatchList_paging";

//    public static final String sendMemberIdUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Member_Insert_ProfileVisited";
//    public static final String userDataByUniqueIdUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_UniqueId";
//    public static final String profileVisitedUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Member_Insert_ProfileVisited";
    public static final String URL_SEND_REQUEST = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Member_SendRequestUpdateStatus";
    public static final String IMEAGES_URL = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_AllPhotoList";
    public static final String addShortlistUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Member_ShortListUpdateStatus";
    public static final String shortlistedmemberUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_MemberShortListedList";
    public static final String searchByIdUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_UniqueId";
    public static final String searchByFilterUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Search_Online_Status";
    //public static final String searchByFilterUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Search";
    public static final String allInfoUsingMemeberIdUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation";
    public static final String invitationSendByMeUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_SendRequestList";
    public static final String invitationReceivedForMeUrl = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_Who_SendRequest_You_List";
    public static final String member_basic_edit_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_BasicInformation";
    public static final String member_profession_edit_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_HigherEducationInformation_new";
    public static final String member_family_edit_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_FamilyInformation";
    public static final String member_astro_edit_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_HoroScopeInformation";
    public static final String member_looking_edit_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_Update_DesiredPartnerInformation";
    public static final String insert_business_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/Business_Insert";
    public static final String business_type_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/SelectAllBusinessType";
    public static final String business_list_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/SelectBusinessUsingMainAppId";
    public static final String business_details_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/SelectBusinessUsingBusinessId";
    public static final String business_keyword_search_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/SelectBusinessUsingBusinessNameKeyWord";
    public static final String business_list_busiType_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/SelectBusinessUsingBusinessType";
    public static final String popular_business_list_url = APIConstants.SERVER_ENDPOINT_1 + "/business.asmx/SelectAllBusinessTypePopular";
    public static final String matri_near_by_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Location_Insert";
    public static final String matri_near_by_update_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_NearByDistance";
    public static final String delete_matrimony_location_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Location_Delete_Using_MemberId";
    public static final String matrimony_login_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_UserLogin";
    public static final String matrimony_gotra_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllGotraMaster";
    public static final String matrimony_life_style = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/MatrimonyRegistration_LifeStyleInformation";
    public static final String invite_shortlist_view_count_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_AcceptanceAndMatchCount";
    public static final String all_count_view_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Member_SendRequest_ProfileVisited_ShortedListed_Blocked_Ignored_CountList";
    public static final String matrimony_log_out_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_SetOfflineStatus";
    public static final String matrimony_forgot_password_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_ForgetPassword";
    public static final String matrimony_notifaction_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SendNotification";
    public static final String matrimony_invitation_notifaction_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SendNotificationInvitation";
    public static final String matrimony_profileView_notifaction_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SendNotificationViewProfile";
    public static final String matrimony_accept_invitation_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Member_AcceptRequestUpdateStatus";
    public static final String matrimony_invitation_accepted_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/Matrimony_Member_MemberSendAcceptRequestList";
    public static final String matrimony_caste_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllCaste";
    public static final String matrimony_sub_caste_url = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/SelectAllSubCasteUsingCasteId";
    public static final String ToggleOnlineStatus = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/ToggleOnlineStatus";
    public static final String contactViewed = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/contactViewed";
    public static final String getContactCredits = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/getContactCredits";
    public static final String getContactViewedDetails = APIConstants.SERVER_ENDPOINT_1 + "/matrimonyapp.asmx/getContactViewedDetails";
}
