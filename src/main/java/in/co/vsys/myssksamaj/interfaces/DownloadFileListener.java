package in.co.vsys.myssksamaj.interfaces;

/**
 * @author abhijeetjadhav
 */
public interface DownloadFileListener {
    void showDownloadStart();

    void showDownloadCompleted(String filePath);
}