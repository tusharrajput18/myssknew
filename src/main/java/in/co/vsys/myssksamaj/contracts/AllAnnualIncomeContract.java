package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.AnnualIncomeModel;
import in.co.vsys.myssksamaj.model.data_models.AllAnnualIncomeModel;
import in.co.vsys.myssksamaj.model.responses.AllAnnualIncomeResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;

public class AllAnnualIncomeContract {

    public interface AllAnnualIncomeAPI {
        @GET("/matrimonyapp.asmx/SelectAllAnnualIncome")
        Call<AllAnnualIncomeResponse> allAnnualIncome();
    }

    public interface AllAnnualIncomeOPS {
        void allAnnualIncome();

        void onDataRecieved(AllAnnualIncomeResponse response);
    }

    public interface AllAnnualIncomeView extends MVPView {
        void showAnnualIncome(List<AllAnnualIncomeModel> annualIncomeList);
    }

}
