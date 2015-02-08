package com.upyun.api;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.psb.ui.util.ImageUtil;
import com.upyun.api.utils.UpYunException;
import com.upyun.api.utils.UpYunUtils;

/**
 * 上传任务<br>
 * execute(String file)<br>
 * execute(String file, String folder)
 *
 * @author 蚂蚁
 */
public class ImageUploadTask extends AsyncTask<String, Void, String> {
    private static final String API_KEY = "R84a6m2qOBUYlN+mWoFwFd8P6ZE=";
    private static final String BUCKET = "cihiheadphoto";
    private static final long EXPIRATION = System.currentTimeMillis() / 1000
            + 1000 * 5 * 10;
    private static final String DOMAIN = "http://cihiheadphoto.b0.upaiyun.com";
    private OnImageUploadListener mListener;

    public void setOnUploadListener(OnImageUploadListener listener) {
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String string = null;

        try {
            if (params.length > 2) {
                Bitmap bitmap = null;
                if ("uri".equals(params[2])) {
                    bitmap = ImageUtil.getBitmapFromUri(Uri.parse(params[0]));
                } else if ("path".equals(params[2])) {
                    bitmap = ImageUtil.getBitmapFromFile(params[0]);
                }

                if (null == bitmap) {
                    return null;
                }
                Bitmap newbm = null;
//				if(params.length == 4){
//					if("blur".equals(params[3])){
//						newbm = ImageUtil.drawableToBitmap(ImageUtil.BoxBlurFilter(bitmap));
//					}
//				}
//				else{
                newbm = ImageUtil.scalingBitmap(bitmap, 640, 960);
//				}


                String targetFolder = File.separator;
                if (params.length > 1 && params[1] != null) {
                    targetFolder = params[1];
                    if (!targetFolder.startsWith(File.separator))
                        targetFolder = File.separator + targetFolder;
                    if (!targetFolder.endsWith(File.separator))
                        targetFolder = targetFolder + File.separator;
                }
                String saveKey = targetFolder
                        + UUID.randomUUID().toString()
                        .toUpperCase(Locale.ENGLISH) + ".jpg";
                // 取得base64编码后的policy
                String policy = UpYunUtils.makePolicy(saveKey, EXPIRATION,
                        BUCKET);

                // 根据表单api签名密钥对policy进行签名
                // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                String signature = UpYunUtils.signature(policy + "&" + API_KEY);

                // 上传文件到对应的bucket中去。
                string = Uploader.upload(policy, signature, BUCKET, newbm);
                newbm.recycle();
                string = DOMAIN + string;
            } else {
                String srcFile = params[0];
                String ext = ".jpg";
                if ((srcFile != null) && (srcFile.length() > 0)) {
                    int dot = srcFile.lastIndexOf('.');
                    if ((dot > -1) && (dot < (srcFile.length() - 1))) {
                        ext = srcFile.substring(dot);
                    }
                }
                String targetFolder = File.separator;
                if (params.length > 1 && params[1] != null) {
                    targetFolder = params[1];
                    if (!targetFolder.startsWith(File.separator))
                        targetFolder = File.separator + targetFolder;
                    if (!targetFolder.endsWith(File.separator))
                        targetFolder = targetFolder + File.separator;
                }
                String saveKey = targetFolder
                        + UUID.randomUUID().toString()
                        .toUpperCase(Locale.ENGLISH) + ext;

                // 取得base64编码后的policy
                String policy = UpYunUtils.makePolicy(saveKey, EXPIRATION,
                        BUCKET);

                // 根据表单api签名密钥对policy进行签名
                // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                String signature = UpYunUtils.signature(policy + "&" + API_KEY);

                // 上传文件到对应的bucket中去。
                string = Uploader.upload(policy, signature, BUCKET, srcFile);
                string = DOMAIN + string;
            }

        } catch (UpYunException e) {
            //e.printStackTrace();
        }

        return string;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            if (this.mListener != null) {
                this.mListener.onUploadComplete(result);
            }
        } else {
            if (this.mListener != null)
                this.mListener.onUploadFailed();
        }
    }

    public interface OnImageUploadListener {
        void onUploadComplete(String path);

        void onUploadFailed();
    }
}
