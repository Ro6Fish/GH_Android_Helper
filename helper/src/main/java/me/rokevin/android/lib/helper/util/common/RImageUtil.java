package me.rokevin.android.lib.helper.util.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.rokevin.android.lib.helper.util.RLog;

/**
 * Created by luokaiwen on 15/8/26.
 * <p/>
 * 图片处理帮助类
 */
public class RImageUtil {

    /**
     * 根据给定图片路径压缩图片到指定图片路径
     *
     * @param sourcePath
     * @param targetPath
     * @param size
     */
    public static void compress(String sourcePath, String targetPath, int size) {


    }

    /**
     * 根据图片路径进行压缩图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getimage(String srcPath, int size) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了,表示只返回宽高
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        //当前图片宽高
        float w = newOpts.outWidth;
        float h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            RLog.e("fileupload", "------原始缩放比例 --------" + (newOpts.outWidth / ww));
            be = (int) (newOpts.outWidth / ww);
            //有时会出现be=3.2或5.2现象，如果不做处理压缩还会失败
            if ((newOpts.outWidth / ww) > be) {

                be += 1;
            }
            //be = Math.round((float) newOpts.outWidth / (float) ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            RLog.e("fileupload", "------原始缩放比例 --------" + (newOpts.outHeight / hh));
            be = (int) (newOpts.outHeight / hh);
            if ((newOpts.outHeight / hh) > be) {

                be += 1;
            }
            //be = Math.round((float) newOpts.outHeight / (float) hh);
        }
        if (be <= 0) {

            be = 1;
        }
        newOpts.inSampleSize = be;//设置缩放比例
        RLog.e("fileupload", "------设置缩放比例 --------" + newOpts.inSampleSize);
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, size);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param size
     * @return
     */
    private static Bitmap compressImage(Bitmap image, int size) {

        if (null == image) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;

        while ((baos.toByteArray().length / 1024) >= size) {  //循环判断如果压缩后图片是否大于等于size,大于等于继续压缩
            RLog.e("fileupload", "------ByteArray--------" + baos.toByteArray().length / 1024);
            baos.reset();//重置baos即清空baos
            options -= 5;//每次都减少5
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            RLog.e("fileupload", "------压缩质量--------" + options);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * Bitmap转byte数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] compressBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;//
        }
        /* 取得相片 */
        Bitmap tempBitmap = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        byte[] byteData = baos.toByteArray();
        return byteData;
    }

    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param outputFile
     * @return
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {

        if (null == b) {
            return null;
        }

        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
