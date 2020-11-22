package com.josystems.mycollageapp.qrcode;

import android.content.Context;
import android.graphics.Bitmap;

import com.crashlytics.android.Crashlytics;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class QrCodeHelper {
    private static QrCodeHelper qrCodeGenerator = null;
    private ErrorCorrectionLevel errorCorrectionLevel;
    private int margin;
    private String mContent;
    private int mWidth, mHeight;

    public QrCodeHelper(Context context) {
        mHeight = (int) (context.getResources().getDisplayMetrics().heightPixels / 2.4);
        mWidth = (int) (context.getResources().getDisplayMetrics().widthPixels / 1.3);
    }

    public static QrCodeHelper getInstance(Context context) {
        if (qrCodeGenerator == null)
            qrCodeGenerator = new QrCodeHelper(context);
        return qrCodeGenerator;
    }

    public Bitmap getQRCode() {
        return generate();
    }

    public QrCodeHelper setErrorCorrectionLevel(ErrorCorrectionLevel level) {
        errorCorrectionLevel = level;
        return this;
    }

    public QrCodeHelper setContent(String content) {
        mContent = content;
        return this;
    }

    public QrCodeHelper setSize(int w, int h) {
        mWidth = w;
        mHeight = h;
        return this;
    }

    public QrCodeHelper setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    private Bitmap generate() {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hintMap.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        hintMap.put(EncodeHintType.MARGIN, margin);
        BitMatrix bitMatrix;
        try {
            bitMatrix = new QRCodeWriter().encode(mContent, BarcodeFormat.QR_CODE, mWidth, mHeight, hintMap);
            int[] pixels = new int[mWidth * mHeight];
            for (int i = 0; i < mHeight; i++) {
                for (int j = 0; j < mWidth; j++) {
                    if (bitMatrix.get(j, i))
                        pixels[i * mWidth + j] = 0xFA000000;
                    else
                        pixels[i * mWidth + j] = 0x282946;
                }
            }
            return Bitmap.createBitmap(pixels, mWidth, mHeight, Bitmap.Config.ARGB_8888);
        } catch (WriterException e) {
            Crashlytics.logException(e);
        }
    return null;
    }
}
