package in.co.vsys.myssksamaj.presenters;

import android.util.Log;

import in.co.vsys.myssksamaj.contracts.SearchContract;
import in.co.vsys.myssksamaj.model.responses.SearchByNameResponse;
import in.co.vsys.myssksamaj.model.responses.SearchByUniqueIdResponse;
import in.co.vsys.myssksamaj.model.responses.SearchResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class SearchPresenter implements SearchContract.SearchOps {

    private SearchContract.SearchAPI searchAPI;
    private SearchContract.SearchView searchView;

    public SearchPresenter(SearchContract.SearchView searchView) {
        this.searchView = searchView;
        searchAPI = RestAdapterContainer.getInstance().create(SearchContract.SearchAPI.class);
    }

    @Override
    public void searchMember(String Gender, String AgeFrom, String AgeTo, String HeightFrom,
                             String HeightTo, String MotherTongueId, String MarriedStatus,
                             String CountryId, String StateId, String CityId, String isOnline, String income, String occupation, String physicallyChallenged,String manglik,String userid) {

        String st = "";
        if (!isOnline.equals("-1")) {
            st = isOnline;
        }
        searchView.showLoading();
        searchAPI.searchMember(Gender, AgeFrom, AgeTo, HeightFrom, HeightTo, MotherTongueId, MarriedStatus,
                CountryId, StateId, CityId, st, income, occupation, physicallyChallenged,manglik,userid).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                searchView.hideLoading();
                searchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void searchMemberByUniqueId(String uniqueId) {
        searchAPI.searchMemberByUniqueId(uniqueId).enqueue(new Callback<SearchByUniqueIdResponse>() {
            @Override
            public void onResponse(Call<SearchByUniqueIdResponse> call, Response<SearchByUniqueIdResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<SearchByUniqueIdResponse> call, Throwable t) {
                searchView.hideLoading();
                searchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void searchMemberByName(String name,String userid) {
        searchAPI.searchMemberByName(name,userid).enqueue(new Callback<SearchByNameResponse>() {
            @Override
            public void onResponse(Call<SearchByNameResponse> call, Response<SearchByNameResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<SearchByNameResponse> call, Throwable t) {
                searchView.hideLoading();
                searchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(SearchResponse searchResponse) {
        searchView.hideLoading();
        if (searchResponse == null) {
            searchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        searchView.showSearchResults(searchResponse.getData());
    }

    @Override
    public void onDataReceived(SearchByUniqueIdResponse searchByUniqueIdResponse) {
        searchView.hideLoading();
        if (searchByUniqueIdResponse == null){
            searchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        searchView.showSearchedResults(searchByUniqueIdResponse.getData());
    }

    @Override
    public void onDataReceived(SearchByNameResponse searchByNameResponse) {
        searchView.hideLoading();
        Log.e("search", searchByNameResponse.getData().size() + "");
        if (searchByNameResponse == null){
//            || Utilities.isListEmpty(searchByNameResponse.getData())) {
            searchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        searchView.showSearchedResults(searchByNameResponse.getData());
    }
}