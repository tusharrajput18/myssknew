package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.SearchByNameResponse;
import in.co.vsys.myssksamaj.model.responses.SearchByUniqueIdResponse;
import in.co.vsys.myssksamaj.model.responses.SearchResponse;
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
public class SearchContract {
    public interface SearchAPI {
        //Matrimony_Search_Online_StatusNew
        @FormUrlEncoded
        @POST("matrimonyapp.asmx/Matrimony_Search_Online_StatusNew")
        Call<SearchResponse> searchMember(
                @Field("Gender") String Gender,
                @Field("AgeFrom") String AgeFrom,
                @Field("AgeTo") String AgeTo,
                @Field("HeightFrom") String HeightFrom,
                @Field("HeightTo") String HeightTo,
                @Field("MotherTongueId") String MotherTongueId,
                @Field("MarriedStatus") String MarriedStatus,
                @Field("CountryId") String CountryId,
                @Field("StateId") String StateId,
                @Field("CityId") String CityId,
                @Field("isOnline") String IsOnline,
                @Field("incomeId") String incomeId,
                @Field("occupationId") String occupationId,
                @Field("physicallyChallenged") String physicallyChallenged,
                @Field("isManglik") String isManglik,
                @Field("MemberId") String MemberId
        );

        @GET("matrimonyapp.asmx/Matrimony_Search2")
        Call<SearchByUniqueIdResponse> searchMemberByUniqueId(
                @Query("UniqueId") String uniqueId
        );



        @GET("matrimonyapp.asmx/Matrimony_Search_By_Name")
        Call<SearchByNameResponse> searchMemberByName(
                @Query("name") String name,
                @Query("MemberId") String MemberId
        );
    }

    public interface SearchOps {
        void searchMember(String Gender, String AgeFrom, String AgeTo, String HeightFrom, String HeightTo,
                          String MotherTongueId, String MarriedStatus, String CountryId, String StateId,
                          String CityId, String isOnline, String incomeId, String occupationId, String physicallyChallenged,String manglik,String userid);

        void searchMemberByUniqueId(String uniqueId);

        void searchMemberByName(String name,String userid);

        void onDataReceived(SearchResponse searchResponse);

        void onDataReceived(SearchByUniqueIdResponse searchByUniqueIdResponse);

        void onDataReceived(SearchByNameResponse searchByNameResponse);
    }

    public interface SearchView extends MVPView {
        void showSearchResults(List<UserProfileModel> userProfileModels);

        void showSearchedResults(List<UserProfileModel> userProfileModels);
    }
}