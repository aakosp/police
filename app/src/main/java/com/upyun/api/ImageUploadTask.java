package com.upyun.api;

import java.util.Locale;
import java.util.UUID;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.psb.ui.util.ImageUtil;
import com.upyun.api.utils.UpYunException;
import com.upyun.api.utils.UpYunUtils;

public class ImageUploadTask extends AsyncTask<String, Void, String> {
    private static final String API_KEY = "7dCP7KvClPFuIolWU5g0NyKlNds=";
    private static final String BUCKET = "tangyin";
    private static final long EXPIRATION = System.currentTimeMillis() / 1000
            + 1000 * 5 * 10;
    private static final String DOMAIN = "http://tangyin.b0.upaiyun.com/";
    private OnImageUploadListener mListener;

    public void setOnUploadListener(OnImageUploadListener listener) {
        this.mListener = listener;
    }

    private int id;

    public ImageUploadTask(int id) {
        this.id = id;
    }

    @Override
    protected String doInBackground(String... params) {
        String string = null;

        try {
            Bitmap bitmap = ImageUtil.getBitmapFromUri(Uri.parse(params[0]));
            if (null == bitmap) {
                return null;
            }
            Bitmap newbm = null;
            newbm = ImageUtil.scalingBitmap(bitmap, 640, 960);

//            String targetFolder = File.separator;
//            if (params.length > 1 && params[1] != null) {
//                targetFolder = params[1];
//                if (!targetFolder.startsWith(File.separator))
//                    targetFolder = File.separator + targetFolder;
//                if (!targetFolder.endsWith(File.separator))
//                    targetFolder = targetFolder + File.separator;
//            }
//            String saveKey = targetFolder +
            String saveKey = UUID.randomUUID().toString()
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
        } catch (UpYunException e) {
            //e.printStackTrace();
        }

        return string;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("ddd", result);
        if (result != null) {
            if (this.mListener != null) {
                this.mListener.onUploadComplete(id, result);
            }
        } else {
            if (this.mListener != null)
                this.mListener.onUploadFailed(id);
        }
    }

    public interface OnImageUploadListener {
        void onUploadComplete(int id, String path);

        void onUploadFailed(int id);
    }
}
