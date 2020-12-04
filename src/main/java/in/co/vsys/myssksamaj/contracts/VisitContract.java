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
public class VisitContract {

    public interface VisitAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/Member_Insert_ProfileVisited")
        Call<Entity> performVisit(
                @Field("MemberId") String MemberId,
                @Field("ProfileViewMemberId") String ProfileViewMemberId
        );
    }

    public interface VisitOps {
        void performVisit(String MemberId, String ProfileViewMemberId);

        void onDataReceived(Entity entity);
    }

    public interface VisitView extends MVPView {
        void showVisitPerformed(String message);
    }
}