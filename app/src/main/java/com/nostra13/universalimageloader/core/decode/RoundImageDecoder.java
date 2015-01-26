package com.nostra13.universalimageloader.core.decode;

import java.io.IOException;
import java.io.InputStream;

import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

public class RoundImageDecoder extends BaseImageDecoder {

    public RoundImageDecoder(boolean loggingEnabled) {
        super(loggingEnabled);
    }

    @Override
    public Bitmap decode(ImageDecodingInfo decodingInfo) throws IOException {
        Bitmap decodedBitmap;
        InputStream imageStream = getImageStream(decodingInfo);
        ImageFileInfo imageInfo = defineImageSizeAndRotation(imageStream,
                decodingInfo.getImageUri());
        Options decodingOptions = prepareDecodingOptions(imageInfo.imageSize,
                decodingInfo);
        imageStream = getImageStream(decodingInfo);
        decodedBitmap = decodeStream(imageStream, decodingOptions);
        if (decodedBitmap == null) {
            L.e(ERROR_CANT_DECODE_IMAGE, decodingInfo.getImageKey());
        } else {
            decodedBitmap = considerExactScaleAndOrientaiton(decodedBitmap,
                    decodingInfo, imageInfo.exif.rotation,
                    imageInfo.exif.flipHorizontal);
        }
        if (decodingInfo instanceof RoundDecoderingInfo) {
            RoundDecoderingInfo roundinfo = (RoundDecoderingInfo) decodingInfo;
            if (roundinfo.getRound() > 0) {
                decodedBitmap = RoundedBitmapDisplayer.roundCorners(decodedBitmap,
                        roundinfo.getViewWidth(), roundinfo.getViewHeight(),
                        roundinfo.getScaleType(), roundinfo.getRound());
            }
        }
        return decodedBitmap;
    }

}
