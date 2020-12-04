package in.co.vsys.myssksamaj.helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import in.co.vsys.myssksamaj.interfaces.DownloadFileListener;
import in.co.vsys.myssksamaj.utils.Constants;

/**
 * @author abhijeetjadhav
 */
public class DownloadHelper {
    public static void downloadFile(final String url, final String outputFileName, final DownloadFileListener downloadFileListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadFileListener.showDownloadStart();
                    URL u = new URL(url);
                    URLConnection conn = u.openConnection();
                    int contentLength = conn.getContentLength();

                    DataInputStream stream = new DataInputStream(u.openStream());

                    byte[] buffer = new byte[contentLength];
                    stream.readFully(buffer);
                    stream.close();

                    FileOperationsHelper.createIfNotExists(Constants.DOCUMENT_FOLDER);
                    FileOperationsHelper.createFile(Constants.DOCUMENT_FOLDER, outputFileName);

                    DataOutputStream fos = new DataOutputStream(new FileOutputStream(Constants.DOCUMENT_FOLDER + outputFileName));
                    fos.write(buffer);
                    fos.flush();
                    fos.close();

                    downloadFileListener.showDownloadCompleted(Constants.DOCUMENT_FOLDER + outputFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}