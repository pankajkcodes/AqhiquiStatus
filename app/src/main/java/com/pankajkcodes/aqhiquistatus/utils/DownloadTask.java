package com.pankajkcodes.aqhiquistatus.utils;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
public class DownloadTask  {

    public static void download(String downloadPathUri, String destPath, Context context,
                                String fileName){

        Uri uri = Uri.parse(downloadPathUri) ;
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES,
                destPath+fileName);
        ((DownloadManager)context.getSystemService(DOWNLOAD_SERVICE)).enqueue(request);




    }

}

