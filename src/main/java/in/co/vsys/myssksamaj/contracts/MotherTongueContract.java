package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.MotherTongueModel;
import in.co.vsys.myssksamaj.model.responses.MotherTongueResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author abhijeetjadhav
 */
public class MotherTongueContract {

    public interface MotherTongueAPI {
        @GET("/matrimonyapp.asmx/SelectAllMotherTongueMaster")
        Call<MotherTongueResponse> getAllMotherTongue();
    }

    public interface MotherTongueOps {
        void getAllMotherTongue();

        void onDataReceived(MotherTongueResponse motherTongueResponse);
    }

    public interface MotherTongueView extends MVPView {
        void showMotherTongues(List<MotherTongueModel> motherTongueModels);
    }
}
