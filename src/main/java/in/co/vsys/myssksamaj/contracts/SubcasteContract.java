package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.SubcasteModel;
import in.co.vsys.myssksamaj.model.responses.SubcasteResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class SubcasteContract {

    public interface SubcasteAPI {
        @GET("/matrimonyapp.asmx/SelectAllSubCasteUsingCasteId" )
        Call<SubcasteResponse> getAllSubCasteByCaste(@Query("CasteId" ) String casteId);
    }

    public interface SubcasteOps {
        void getAllSubCasteByCaste(String casteId);

        void onDataReceived(SubcasteResponse subcasteResponse);
    }

    public interface SubcasteView extends MVPView {
        void showSubcasteList(List<SubcasteModel> subcasteModels);
    }
}