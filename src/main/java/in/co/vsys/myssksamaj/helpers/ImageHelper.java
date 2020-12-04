package in.co.vsys.myssksamaj.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

/**
 * @author abhijeetjadhav
 */
public class ImageHelper {

    public static String getStringFromBitmap(Context context, Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static String getStringImageFromFile(Context context, String filePath) {

        String imageStr = null;

        try {
            Bitmap bitmap = getBitmapFromFile(context, filePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            imageStr = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }

    public static String getStringImageFromUri(Context context, Uri uri) {

        String imageStr = null;

        try {
            Bitmap bitmap = getBitmapFromUri(context, uri);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            imageStr = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }

    public static Bitmap getBitmapFromFile(Context context, String filename) {
        Bitmap bitmap;
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        if (isThumbnail) {
//            bitmap =
//                    ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filename),
//                            metrics.widthPixels,
//                            (int) context.getResources().getDimension(R.dimen.gallery_image_height));
//        } else {
        bitmap = BitmapFactory.decodeFile(filename);
//        }
//        preserveOrientation(filename, bitmap);

        return bitmap;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String savePic(Bitmap bitmap, String filename) {
        FileOutputStream fos;
        String fileWrote = "";
        try {
            FileOperationsHelper.createIfNotExists(Constants.IMAGES_FOLDER);
            FileOperationsHelper.createFile(Constants.IMAGES_FOLDER, filename);
            if (filename.contains("/"))
                filename = filename.substring(filename.lastIndexOf("/") + 1);
            fileWrote = Constants.IMAGES_FOLDER + filename;
            fos = new FileOutputStream(fileWrote);
            if (null != fos) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.IMAGE_QUALITY, fos);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileWrote;
    }

    public static void getBitmapFromUrl(final Context context, final String path, final String imageName) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

        final String extension = path.substring(path.length() - 4);

        ImageLoader.getInstance().loadImage(path, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Toast.makeText(context, "Image downloaded", Toast.LENGTH_SHORT).show();
                savePic(loadedImage, imageName + extension);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }

    public static void showImage(ImageView imageView, String imageUrl, int defaultImage) {
        if(imageView == null)
            return;

        if(Utilities.isEmpty(imageUrl))
            return;

        Picasso.get()
                .load(imageUrl)
                .placeholder(defaultImage)
                .into(imageView);
    }
}