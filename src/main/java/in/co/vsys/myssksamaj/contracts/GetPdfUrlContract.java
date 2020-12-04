package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.PdfResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class GetPdfUrlContract {
    public interface GetPdfUrlAPI {
        @GET("matrimonyapp.asmx/ProfilePDFShared_Using_MemeberId")
        Call<PdfResponse> getProfilePdf(@Query("MemberId") String MemberId);
    }

    public interface GetPdfUrlOps {
        void getProfilePdf(String memberId);

        void onDataReceived(PdfResponse pdfResponse);
    }

    public interface GetPdfUrlView extends MVPView {
        void showPdfUrl(String url);
    }
}