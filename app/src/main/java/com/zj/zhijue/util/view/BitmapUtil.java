package com.zj.zhijue.util.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BitmapUtil {
    public static long FREE_MEMORY = ((long) ((((int) Runtime.getRuntime().freeMemory()) / 1024) / 1024));

    public static Bitmap doodle(Bitmap src, Bitmap watermark) throws Exception {
        return doodle(src, watermark, (src.getWidth() - watermark.getWidth()) - 12, watermark.getHeight() + 20);
    }

    public static Bitmap doodle(Bitmap src, Bitmap watermark, int left, int top) throws Exception {
        Log.d("YKD", "doodle left:" + left + " top:" + top + " src bitmap width:" + src.getWidth() + " height:" + src.getHeight() + " watermark width:" + watermark.getWidth() + " height:" + watermark.getHeight());
        Bitmap newb = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newb);
        canvas.drawBitmap(src, 0.0f, 0.0f, null);
        canvas.drawBitmap(watermark, (float) left, (float) top, null);
        //canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();
        watermark.recycle();
        return newb;
    }

    public static Bitmap decodeBitmapFromPath(String photo_path, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap scanBitmap = BitmapFactory.decodeFile(photo_path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(photo_path, options);
    }

    public Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    private void setDrawable(Drawable drawable, int color, int width, int height) {
        drawable = tintDrawable(drawable, ColorStateList.valueOf(color));
        drawable.setBounds(0, 0, width, height);
       /* button.setCompoundDrawables(null, drawable, null, null);
        button.setTextColor(color);*/
    }

    public static Bitmap tintBitmap(Bitmap inBitmap , int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap (inBitmap.getWidth(), inBitmap.getHeight() , inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(inBitmap , 0, 0, paint) ;
        return outBitmap ;
    }


    // 根据maxWidth, maxHeight计算最合适的inSampleSize
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }

    public static int calculateInSampleSize2(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        while (((long) ((reqHeight * reqWidth) * 4)) > ((FREE_MEMORY * 1048576) / 4) * 3) {
            reqHeight -= 50;
            reqWidth -= 50;
        }
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }
        if (inSampleSize == 0) {
            return 1;
        }
        Log.e("hongliang", "inSampleSize=" + inSampleSize);
        return inSampleSize;
    }

    //缩略图
    public static Bitmap thumbnail(String path,
                                   int maxWidth, int maxHeight, boolean autoRotate) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高信息到options中, 此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int sampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    //保存bitmap
    public static String save(Bitmap bitmap,
                              Bitmap.CompressFormat format, int quality, File destFile) {
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            if (bitmap.compress(format, quality, out)) {
                out.flush();
                out.close();
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return destFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 保存到sd卡
    public static String save(Bitmap bitmap,
                              Bitmap.CompressFormat format, int quality, Context context) {
        if (!Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File dir = new File(Environment.getExternalStorageDirectory()
                + "/" + context.getPackageName() + "/save/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(dir, UUID.randomUUID().toString());
        return save(bitmap, format, quality, destFile);
    }
}
