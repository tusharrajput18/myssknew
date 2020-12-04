package in.co.vsys.myssksamaj.helpers;

import java.io.File;

import in.co.vsys.myssksamaj.utils.Constants;

/**
 * @author abhijeet.j
 */
public class FileOperationsHelper {


    public static void createFile(String folder, String filename) {
        try {
            File directory = new File(folder);
            if (!directory.exists())
                directory.mkdirs();
            new File(directory, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createIfNotExists(String filename) {
        File folder = new File(filename);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
//        if (success) {
//            // Do something on success
//        } else {
//            // Do something else on failure
//        }
    }

    /**
     * Delete images onCreate()
     */
    public static void deleteFolder() {
        try {
            File directory = new File(Constants.IMAGES_FOLDER);
            if (directory.isDirectory()) {
                String[] children = directory.list();
                for (int i = 0; i < children.length; i++) {
                    new File(directory, children[i]).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
