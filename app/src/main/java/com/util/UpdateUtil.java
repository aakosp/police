package com.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aako on 2015/8/10.
 */
public class UpdateUtil {

    //public static final String UPDATE_VERJSON = "ver.txt";
    public static final String UPDATE_SAVENAME = "updateapk.apk";
    private static final int UPDATE_CHECKCOMPLETED = 1;
    private static final int UPDATE_DOWNLOADING = 2;
    private static final int UPDATE_DOWNLOAD_ERROR = 3;
    private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
    private static final int UPDATE_DOWNLOAD_CANCELED = 5;
    private UpdateCallback callback;
    private Context ctx;
    private int progress;
    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case UPDATE_CHECKCOMPLETED:
//                    callback.checkUpdateCompleted(hasNewVersion, newVersion);
                    break;
                case UPDATE_DOWNLOADING:
                    callback.downloadProgressChanged(progress);
                    break;
                case UPDATE_DOWNLOAD_ERROR:
                    callback.downloadCompleted(false, msg.obj.toString());
                    break;
                case UPDATE_DOWNLOAD_COMPLETED:
                    callback.downloadCompleted(true, "");
                    break;
                case UPDATE_DOWNLOAD_CANCELED:
                    callback.downloadCanceled();
                default:
                    break;
            }
        }
    };
    private Boolean canceled;
    private String updateUrl = "";
    private String savefolder = Environment.getExternalStorageDirectory() + "/police/";

//    public String getNewVersionName()
//    {
//        return newVersion;
//    }
//
//    public String getUpdateInfo()
//    {
//        return updateInfo;
//    }
//
//    private void getCurVersion() {
//        try {
//            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
//                    ctx.getPackageName(), 0);
//            curVersion = pInfo.versionName;
//            curVersionCode = pInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            curVersion = "1.1.1000";
//            curVersionCode = 1;
//        }
//    }

    public UpdateUtil(Context context, UpdateCallback updateCallback) {
        ctx = context;
        callback = updateCallback;
        canceled = false;
//        getCurVersion();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(
                Uri.fromFile(new File(savefolder, UPDATE_SAVENAME)),
                "application/vnd.android.package-archive");
        ctx.startActivity(intent);
    }

    public void downloadPackage(String url) {
        updateUrl = url;
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(updateUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();

                    File folder = new File(savefolder);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    File apkFile = new File(savefolder, UPDATE_SAVENAME);
                    if (apkFile.exists()) {
                        apkFile.delete();
                    }

                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    byte buf[] = new byte[512];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);
                        updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING));
                        if (numread <= 0) {
                            updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!canceled);
                    if (canceled) {
                        updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
                    }
                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR, e.getMessage()));
                } catch (IOException e) {
                    e.printStackTrace();
                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR, e.getMessage()));
                }

            }
        }.start();
    }

    public void cancelDownload() {
        canceled = true;
    }

    public interface UpdateCallback {
        public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo);

        public void downloadProgressChanged(int progress);

        public void downloadCanceled();

        public void downloadCompleted(Boolean sucess, CharSequence errorMsg);
    }


    /**********************************************/
    //NetHelper
//    public class NetHelper {
//
//        public static String httpStringGet(String url) throws Exception {
//            return httpStringGet(url, "utf-8");
//        }
//
//        public static Drawable loadImage(String url) {
//            try {
//                return Drawable.createFromStream(
//                        (InputStream) new URL(url).getContent(), "test");
//            } catch (MalformedURLException e) {
//                Log.e("exception", e.getMessage());
//            } catch (IOException e) {
//                Log.e("exception", e.getMessage());
//            }
//            return null;
//        }
//
//        public static String httpStringGet(String url, String enc) throws Exception {
//            // This method for HttpConnection
//            String page = "";
//            BufferedReader bufferedReader = null;
//            try {
//                HttpClient client = new DefaultHttpClient();
//                client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
//                        "android");
//
//                HttpParams httpParams = client.getParams();
//                HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
//                HttpConnectionParams.setSoTimeout(httpParams, 5000);
//
//                HttpGet request = new HttpGet();
//                request.setHeader("Content-Type", "text/plain; charset=utf-8");
//                request.setURI(new URI(url));
//                HttpResponse response = client.execute(request);
//                bufferedReader = new BufferedReader(new InputStreamReader(response
//                        .getEntity().getContent(), enc));
//
//                StringBuffer stringBuffer = new StringBuffer("");
//                String line = "";
//
//                String NL = System.getProperty("line.separator");
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(line + NL);
//                }
//                bufferedReader.close();
//                page = stringBuffer.toString();
//                Log.i("page", page);
//                System.out.println(page + "page");
//                return page;
//            } finally {
//                if (bufferedReader != null) {
//                    try {
//                        bufferedReader.close();
//                    } catch (IOException e) {
//                        Log.d("BBB", e.toString());
//                    }
//                }
//            }
//        }
//
//        public static boolean checkNetWorkStatus(Context context) {
//            boolean result;
//            ConnectivityManager cm = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netinfo = cm.getActiveNetworkInfo();
//            if (netinfo != null && netinfo.isConnected()) {
//                result = true;
//                Log.i("NetStatus", "The net was connected");
//            } else {
//                result = false;
//                Log.i("NetStatus", "The net was bad!");
//            }
//            return result;
//        }
//    }


}
