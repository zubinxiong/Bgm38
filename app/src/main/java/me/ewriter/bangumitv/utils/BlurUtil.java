package me.ewriter.bangumitv.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Zubin on 2016/8/1.
 */
public class BlurUtil {

    private static final float BITMAP_SCALE = 0.5f;
    private static final float BLUR_RADIUS = 20f;

    public static Bitmap blur(Context context, Bitmap image) {

        if (image == null)
            return null;

        RenderScript rs = null;
        try {
            int width = Math.round(image.getWidth() * BITMAP_SCALE);
            int height = Math.round(image.getHeight() * BITMAP_SCALE);

            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

            rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

            theIntrinsic.setRadius(BLUR_RADIUS);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);

            //http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=1014
            // bitmap recycle 后导致了background 为空，出现   java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap
            // 这个问题是我在升级support library 后出现的，总结下就是当前bitmap对象可能还被 View 在屏幕画出的时候，不要回收 Bitmap 对象
//            image.recycle();

            rs.destroy();
            return outputBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            rs.destroy();
            return  null;
        }

    }
}
