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
public class NumberIfExistContract {
    public interface NumberifExistApi {
        @FormUrlEncoded
        @POST("matrimonyapp.asmx/CheckIfNumberExists")
        Call<Entity> numberexit(
                @Field("Mobile") String Mobile
        );
    }

    public interface NumberifExistOps{
        void numberifexist(String Mobile);

        void onNumberDataReceived(Entity entity);
    }

    public interface NumberIfExistview extends MVPView{
        void showNumbermsg(String message);
    }
}