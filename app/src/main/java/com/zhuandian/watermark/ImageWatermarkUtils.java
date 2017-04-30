package com.zhuandian.watermark;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 谢栋 on 2017/4/30.
 */
public class ImageWatermarkUtils {

    /**
     * 水印
     * @param src 添加水印的图
     * @param watermark 水印图
     * @param alpha 水印的透明度
     * @return
     */
    public static Bitmap Watermark(Bitmap src, Bitmap watermark, int alpha) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        Paint paint=new Paint();
        paint.setAlpha(alpha);
        paint.setAntiAlias(true);
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);
        cv.drawBitmap(watermark, 0, h/2, paint);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newb;
    }
}
