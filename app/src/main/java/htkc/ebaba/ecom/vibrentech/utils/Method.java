package htkc.ebaba.ecom.vibrentech.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import htkc.ebaba.ecom.vibrentech.R;

import static android.content.Context.DOWNLOAD_SERVICE;


public class Method {

    public static Activity activity;
    public static boolean share = false, onBackPress = false, allowPermitionExternalStorage = false,slider=false,loginBack=false;
    public static Bitmap mbitmap;
    private static File file;
    private String filename;

    public static Typeface scriptable;

    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private final String myPreference = "login";
    public String pref_login = "pref_login";
    private String firstTime = "firstTime";
    public String profileId = "profileId";
    public String userEmail = "userEmail";
    public String userPassword = "userPassword";
    public String userName = "userName";



    //network check
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) activity
                .getSystemService( Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

     /*<---------------------- share & downlode and methodes ---------------------->*/

    public static void share_save(String image, String bookName, String bookAuthor, String description, String bookLink) {
        new DownloadImageTask().execute(image, bookName, bookAuthor, description, bookLink);
    }

    public static class DownloadImageTask extends AsyncTask<String, String, String> {

        String bookName, bookAuthor, bookDescription, bookLink;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                bookName = params[1];
                bookAuthor = params[2];
                bookDescription = params[3];
                bookLink = params[4];
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mbitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                // Log exception
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (share) {
                ShareImage(mbitmap, bookName, bookAuthor, bookDescription, bookLink);
                share = false;
            }
            super.onPostExecute(s);
        }
    }

    private static void ShareImage(Bitmap finalBitmap, String bookName, String bookAuthor, String description, String bookLink) {

        String root = activity.getExternalCacheDir().getAbsolutePath();
        String fname = "Image_share" + ".jpg";
        file = new File(root, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress( Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri contentUri = Uri.fromFile(file);
        Log.d("url", String.valueOf(contentUri));

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction( Intent.ACTION_SEND);
            shareIntent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setData(contentUri);
            shareIntent.setType("image/*");
            shareIntent.putExtra( Intent.EXTRA_STREAM, contentUri);
            shareIntent.putExtra( Intent.EXTRA_TEXT, Html.fromHtml(bookName)
                    + "\n" + "\n" + Html.fromHtml(bookAuthor)
                    + "\n" + "\n" + Html.fromHtml(description)
                    + "\n" + "\n" + Html.fromHtml(bookLink));
            activity.startActivity( Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    public void download(String id, String bookName, String bookImage, String bookAuthor, String bookUrl, String type) {

        File root = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/AndroidEBook/");
        if (!root.exists()) {
            root.mkdirs();
        }
        DownloadManager.Request request = new DownloadManager.Request( Uri.parse(bookUrl));
        request.setDescription(activity.getResources().getString( R.string.downloading) + bookName);
        request.setTitle(activity.getResources().getString(R.string.app_name));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE);
        filename = "filename-" + id;
        if (type.equals("epub")){
            request.setDestinationInExternalPublicDir("/AndroidEBook/", filename + ".epub");
        }else {
            request.setDestinationInExternalPublicDir("/AndroidEBook/", filename + ".pdf");
        }

        Log.d("bookNmae", bookName);
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);

        new DownloadImage().execute(bookImage, id, bookName, bookAuthor,type);

    }

    public class DownloadImage extends AsyncTask<String, String, String> {

        private String id, bookName, bookAuthor,type;
        Bitmap bitmapDownload;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                id = params[1];
                bookName = params[2];
                bookAuthor = params[3];
                type = params[4];
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmapDownload = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                // Log exception
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            downloadImage(bitmapDownload, id, bookName, bookAuthor,type);

            super.onPostExecute(s);
        }

    }

    public void downloadImage(Bitmap bitmap, String id, String bookName, String bookAuthor, String type) {

        String filePath = null;

        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/AndroidEBook/";
        File sdIconStorageDir = new File(iconsStoragePath);

        //create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();

        try {
            String fname = "Image-" + id;
            filePath = sdIconStorageDir.toString() + "/" + fname + ".jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            //choose another format if PNG doesn't suit you
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        }
    }

}
