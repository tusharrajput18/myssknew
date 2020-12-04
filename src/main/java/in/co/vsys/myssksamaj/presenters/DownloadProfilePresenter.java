package in.co.vsys.myssksamaj.presenters;

import android.os.Environment;

import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;

import in.co.vsys.myssksamaj.contracts.DownloadProfileContract;
import in.co.vsys.myssksamaj.helpers.DownloadHelper;
import in.co.vsys.myssksamaj.interfaces.DownloadFileListener;
import in.co.vsys.myssksamaj.model.RecentlyProfileVisitorModel;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class DownloadProfilePresenter implements DownloadProfileContract.DownloadProfileOps {

    private DownloadProfileContract.DownloadProfileAPI downloadProfileAPI;
    private DownloadProfileContract.DownloadProfileView downloadProfileView;

    public DownloadProfilePresenter(DownloadProfileContract.DownloadProfileView downloadProfileView) {
        this.downloadProfileView = downloadProfileView;
        downloadProfileAPI = RestAdapterContainer.getInstance().create(DownloadProfileContract.DownloadProfileAPI.class);
    }

    @Override
    public void downloadFile(RecentlyProfileVisitorModel recentlyProfileVisitorModel, String fileUrl) {
        try {
            final String extension = fileUrl.substring(fileUrl.length() - 4);
            final String filename = recentlyProfileVisitorModel.getFirstName() + "_" + recentlyProfileVisitorModel.getLastName() + extension;
            downloadProfileView.showLoading();
            DownloadHelper.downloadFile(fileUrl, filename, new DownloadFileListener() {
                @Override
                public void showDownloadStart() {
                    downloadProfileView.showError("Download started");
                }

                @Override
                public void showDownloadCompleted(String filePath) {
                    downloadProfileView.showError("Download complete. Location:\n" + filePath);
                }
            });

            downloadProfileAPI.download(fileUrl).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        File path = Environment.getExternalStorageDirectory();
                        File file = new File(path, filename);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(response.body().bytes());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFile(/*final UserProfileModel userProfileModel,*/final String fileName, String fileUrl) {
        try {
            final String extension = fileUrl.substring(fileUrl.length() - 4);
           final String filename = fileName + extension;
            downloadProfileView.showLoading();
            DownloadHelper.downloadFile(fileUrl, filename, new DownloadFileListener() {
                @Override
                public void showDownloadStart() {
                    downloadProfileView.showError("Download started");
                }

                @Override
                public void showDownloadCompleted(String filePath) {
                    downloadProfileView.showError("Download complete. Location:\n" + filePath);
                }
            });

            downloadProfileAPI.download(fileUrl).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        File path = Environment.getExternalStorageDirectory();
                        File file = new File(path, filename);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(response.body().bytes());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFile(UserDetailsModel userDetailsModel, String fileUrl) {
        try {
            final String extension = fileUrl.substring(fileUrl.length() - 4);
            final String filename = userDetailsModel.getFirstName() + "_" + userDetailsModel.getLastName() + extension;
            downloadProfileView.showLoading();
            downloadProfileAPI.download(fileUrl).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        File path = Environment.getExternalStorageDirectory();
                        File file = new File(path, filename);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(response.body().bytes());

                        onDataReceived();
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    downloadProfileView.hideLoading();
                    downloadProfileView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceived() {
        downloadProfileView.hideLoading();
        downloadProfileView.showFileDownloaded("Profile downloaded successfully");
    }
}
