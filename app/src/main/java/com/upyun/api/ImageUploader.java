package com.upyun.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.psb.ui.util.ImageUtil;
import com.upyun.api.utils.UpYunException;
import com.upyun.api.utils.UpYunUtils;
import com.util.StringUtils;

public class ImageUploader {
//	private static final String API_KEY = "DqB4c/W3Da7Gsy6VqO++e/6bdys=";
//	private static final String BUCKET = "xiaomayi";
//	private static final String DOMAIN = "http://xiaomayi.b0.upaiyun.com";

    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 960;
    private static final String AVATAR_API_KEY = "c1neDXEliQqxZ4Jr4MDST+eL7fc=";
    private static final String AVATAR_BUCKET = "phonehead";
    private static final String AVATAR_DOMAIN = "http://phonehead.b0.upaiyun.com";
    private static final String APPEAL_BUCKET = "airphoto";
    private static final String APPEAL_API_KEY = "Z00phPZZtT4gDt/WwPKjrD2ED50=";
    private static final String APPEAL_DOMAIN = "http://airphoto.b0.upaiyun.com";
    private static final String PHOTO_API_KEY = "R84a6m2qOBUYlN+mWoFwFd8P6ZE=";
    private static final String PHOTO_BUCKET = "cihiheadphoto";
    private static final String PHOTO_DOMAIN = "http://cihiheadphoto.b0.upaiyun.com";
    private static final long EXPIRATION = System.currentTimeMillis() / 1000
            + 1000 * 5 * 10;
    private OnImageUploadListener mListener;

    public ImageUploader() {
    }

    private static String checkFolder(String folder) {
        if (StringUtils.isEmpty(folder))
            return File.separator;

        String ret = folder;
        if (!folder.startsWith(File.separator))
            ret = File.separator + ret;
        if (!folder.endsWith(File.separator))
            ret = ret + File.separator;

        return ret;
    }

    // 根据路径获得图片并压缩，返回bitmap
    public static InputStream getSmallStream(String filePath, int reqWidth, int reqHeight) {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        try {
            bmp = ImageUtil.getBitmapFromFile(filePath);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            if (height > reqHeight || width > reqWidth) {
                float heightRatio = (float) height / (float) reqHeight;
                float widthRatio = (float) width / (float) reqWidth;
                float ratio = heightRatio < widthRatio ? widthRatio : heightRatio;

                int newWidth = (int) (width / ratio);
                int newHeight = (int) (height / ratio);
                bmp = ThumbnailUtils.extractThumbnail(bmp, newWidth, newHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            }
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            bais = new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bmp != null) {
                    bmp.recycle();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }

        return bais;
    }

    // 根据路径获得图片并压缩，返回bitmap
    public static InputStream getSmallStream(Uri fileUri, int reqWidth, int reqHeight) {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        try {
            bmp = ImageUtil.getBitmapFromUri(fileUri);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            if (height > reqHeight || width > reqWidth) {
                float heightRatio = (float) height / (float) reqHeight;
                float widthRatio = (float) width / (float) reqWidth;
                float ratio = heightRatio < widthRatio ? widthRatio : heightRatio;

                int newWidth = (int) (width / ratio);
                int newHeight = (int) (height / ratio);
                bmp = ThumbnailUtils.extractThumbnail(bmp, newWidth, newHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            }
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            bais = new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bmp != null) {
                    bmp.recycle();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }

        return bais;
    }

    private void finish(String result, Bundle data) {
        if (this.mListener != null) {
            if (result != null) {
                this.mListener.onUploadComplete(result, data);
            } else {
                this.mListener.onUploadFailed(data);
            }
        }
    }

    /**
     * 上传
     *
     * @param fileUri
     *            本地文件Uri
     * @param folder
     *            目标目录
     * @param extras
     *            附加数据，用于返回
     */
//	public void upload(Uri fileUri, String folder, Bundle extras) {
//		UploadInfo info = new UploadInfo(fileUri, folder, 0, 0);
//		ImageUploadTask task = new ImageUploadTask(extras);
//		task.execute(info);
//	}

    /**
     * 上传
     *
     * @param fileUri 本地文件Uri
     * @param folder  目标目录
     * @param extras  附加数据，用于返回
     */
    public void uploadAvatar(Uri fileUri, String folder, Bundle extras) {
        UploadInfo info = new UploadInfo(fileUri, folder, 0, 0, 1);
        ImageUploadTask task = new ImageUploadTask(extras);
        task.execute(info);
    }

    //上传图片诉求
    public void uploadAppeal(Uri fileUri, String folder, Bundle extras) {
        UploadInfo info = new UploadInfo(fileUri, folder, 1280, 720, 2);
        ImageUploadTask task = new ImageUploadTask(extras);
        task.execute(info);
    }

    /**
     * 上传
     *
     * @param fileUri   本地文件Uri
     * @param folder    目标目录
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param extras    附加数据，用于返回
     */
    public void upload(Uri fileUri, String folder, int maxWidth, int maxHeight,
                       Bundle extras) {
        UploadInfo info = new UploadInfo(fileUri, folder, maxWidth, maxHeight);
        ImageUploadTask task = new ImageUploadTask(extras);
        task.execute(info);
    }

    public void setOnUploadListener(OnImageUploadListener listener) {
        this.mListener = listener;
    }

    public interface OnImageUploadListener {
        void onUploadComplete(String path, Bundle data);

        void onUploadFailed(Bundle data);
    }

    private class UploadInfo {
        private final Uri mImageUri;
        private final String mFolder;
        private final int mMaxWidth;
        private final int mMaxHeight;
        private final int mType; // 0:相册；1：头像

        public UploadInfo(Uri fileUri, String folder, int maxWidth,
                          int maxHeight) {
            mImageUri = fileUri;
            mFolder = checkFolder(folder);
            mMaxWidth = maxWidth;
            mMaxHeight = maxHeight;
            mType = 0;
        }

        public UploadInfo(Uri fileUri, String folder, int maxWidth,
                          int maxHeight, int type) {
            mImageUri = fileUri;
            mFolder = checkFolder(folder);
            mMaxWidth = maxWidth;
            mMaxHeight = maxHeight;
            mType = type;
        }

        public Uri getImageUri() {
            return mImageUri;
        }

        public String getFolder() {
            return mFolder;
        }

        public int getMaxWidth() {
            return this.mMaxWidth;
        }

        public int getMaxHeight() {
            return this.mMaxHeight;
        }

        public int getType() {
            return this.mType;
        }
    }

    private class ImageUploadTask extends AsyncTask<UploadInfo, Void, String> {
        private final Bundle mData;

        public ImageUploadTask(Bundle data) {
            mData = data;
        }

        @Override
        protected String doInBackground(UploadInfo... params) {
            String retUrl = null;
            try {
                UploadInfo info = params[0];
                Uri fileUri = info.getImageUri();
                String folder = info.getFolder();
                int maxWidth = info.getMaxWidth();
                int maxHeight = info.getMaxHeight();

                String ext = ".jpg";
                String filePath = fileUri.getPath();
                if ((filePath != null) && (filePath.length() > 0)) {
                    int dot = filePath.lastIndexOf('.');
                    if ((dot > -1) && (dot < (filePath.length() - 1))) {
                        ext = filePath.substring(dot);
                    }
                }

                String key = PHOTO_API_KEY;
                String bucket = PHOTO_BUCKET;
                String domain = PHOTO_DOMAIN;
                if (info.getType() == 1) {
                    key = AVATAR_API_KEY;
                    bucket = AVATAR_BUCKET;
                    domain = AVATAR_DOMAIN;
                }
                if (info.getType() == 2) {    //带图片的诉求
                    key = APPEAL_API_KEY;
                    bucket = APPEAL_BUCKET;
                    domain = APPEAL_DOMAIN;
                }

                String saveKey = folder
                        + UUID.randomUUID().toString()
                        .toUpperCase(Locale.ENGLISH) + ext;
                // 取得base64编码后的policy
                String policy = UpYunUtils.makePolicy(saveKey, EXPIRATION,
                        bucket);

                // 根据表单api签名密钥对policy进行签名
                // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                String signature = UpYunUtils.signature(policy + "&" + key);

                if (maxWidth > 0 && maxHeight > 0) {
                    InputStream stream = getSmallStream(fileUri, maxWidth, maxHeight);
                    if (stream == null) return null;
                    retUrl = Uploader.upload(policy, signature, bucket, stream);
                } else {
//					retUrl = Uploader.upload(policy, signature, bucket,
//							fileUri.getPath());
                    //修改为从URI获取
                    Bitmap tmpBitmap = ImageUtil.getBitmapFromUri(fileUri);
                    retUrl = Uploader.upload(policy, signature, bucket, tmpBitmap);
                    tmpBitmap.recycle();
                }

                if (!StringUtils.isEmpty(retUrl)) {
                    retUrl = domain + retUrl;
                }

            } catch (UpYunException e) {
                e.printStackTrace();
            }
            return retUrl;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            finish(result, mData);
            // ImageUploader uploader = mUploader.get();
            // if (uploader != null) {
            // uploader.
            // }
        }
    }
}
