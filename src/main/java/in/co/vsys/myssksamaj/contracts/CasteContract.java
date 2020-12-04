package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.CasteModel;
import in.co.vsys.myssksamaj.model.responses.CasteResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author abhijeetjadhav
 */
public class CasteContract {

    public interface CasteAPI{
        @GET("matrimonyapp.asmx/SelectAllCaste")
        Call<CasteResponse> getAllCastes();
    }

    public interface CasteOps{
        void getAllCastes();

        void onDataReceived(CasteResponse casteResponse);
    }

    public interface CasteView extends MVPView {
        void showCasteList(List<CasteModel> casteModels);
    }
}