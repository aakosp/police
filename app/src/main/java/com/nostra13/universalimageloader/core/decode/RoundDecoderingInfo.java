package com.nostra13.universalimageloader.core.decode;

import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

public class RoundDecoderingInfo extends ImageDecodingInfo {

    private int viewWidth;
    private int viewHeight;
    private int round;
    private ScaleType scaleType;

    public RoundDecoderingInfo(String imageKey, String imageUri,
                               ImageSize targetSize, int viewWidth, int viewHeight, int round, ScaleType scaleType, ViewScaleType viewScaleType,
                               ImageDownloader downloader, DisplayImageOptions displayOptions) {
        super(imageKey, imageUri, targetSize, viewScaleType, downloader, displayOptions);

        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.round = round;
        this.scaleType = scaleType;
    }

    public int getViewWidth() {
        return this.viewWidth;
    }

    public int getViewHeight() {
        return this.viewHeight;
    }

    public int getRound() {
        return this.round;
    }

    public ScaleType getScaleType() {
        return this.scaleType;
    }
}
