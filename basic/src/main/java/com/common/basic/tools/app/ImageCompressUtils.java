package com.common.basic.tools.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 图片压缩工具类
 * 
 * 1.如果是GIF不压缩
 * 2.如果是长宽比超过3并且短边小于等于640的, 说明是长微博, 直接压缩模式:80%JPEG质量
 * 3.如果是长宽比超过3并且短边大于640的, 可能是长微博也可能是特殊图片, 先把最短边跟640计算长宽压缩比, 再直接压缩模式:80%JPEG质量
 * 4.如果不是以上3种情况, 先把图片宽高设置为小于1280, 然后图片大小循环压缩为200kb以下
 * 5.如果文件本身小于200kb, 直接返回new File
 * 
 * @author Yao
 * 
 */
public class ImageCompressUtils {

    // 上传图片的最大宽高
    final public static int IMAGE_MAX_HEIGHT = 1280;
    final public static int IMAGE_MAX_WIDTH = 1280;
    final public static int IMAGE_MEDIUM_LENGTH = 640;

    // 记录所有压缩的cache图
    public static ArrayList<File> picsToDelete = new ArrayList<File>();

    // 压缩模式
    private static boolean compressModeFlag = true;// true是循环压缩到200k,
                                                   // false为一次性压缩

    /**
     * 压缩图片
     * 
     * @param picPath
     *            图片路径
     * @return FIle
     */
    public static File compressImageFile(String picPath) {
        try {
            // 情况1:如果是gif不压缩
            if (picPath.contains(".gif")) {
                return new File(picPath);
            }

            // 对图片进行压缩
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            // 获取这个图片的宽和高
            Bitmap bitmap = BitmapFactory.decodeFile(picPath, options);// 此时返回bm为空
            options.inJustDecodeBounds = false;

            int originalHeight = options.outHeight;
            int originalWidth = options.outWidth;
            // 获取长边跟短边的比值
            int picRatio = (originalWidth >= originalHeight) ? (int) (originalWidth / (float) originalHeight + 0.5)
                    : (int) (originalHeight / (float) originalWidth + 0.5);
            // 获取短边的长度值
            int shortLength = (originalWidth >= originalHeight) ? originalHeight : originalWidth;
            int be = 1;
            // 情况2.如果是长宽比超过3并且短边小于等于640的, 说明是长微博, 直接压缩模式:80%JPEG质量
            if (picRatio > 3 && shortLength <= IMAGE_MEDIUM_LENGTH) {
                //				Utils.print("长微博:直接压缩");
                compressModeFlag = false;// 直接压缩
            }
            // 3.如果是长宽比超过3并且短边大于640的, 可能是长微博也可能是特殊图片, 先把最短边跟640计算长宽压缩比,
            // 再直接压缩模式:80%JPEG质量
            else if (picRatio > 3 && shortLength > IMAGE_MEDIUM_LENGTH) {
                while (shortLength / be > IMAGE_MEDIUM_LENGTH) {
                    be *= 2;
                }
                //				Utils.print("长微博/特殊图片(短边大于640):直接压缩");
                compressModeFlag = false;// 直接压缩
            }
            // 4.如果不是以上3种情况, 先把图片宽高设置为小于1280, 然后图片大小循环压缩为200kb以下
            else {
                //				Utils.print("常规图片:循环压缩到200K");
                // 计算缩放比 必须为偶数
                while ((originalHeight / be > IMAGE_MAX_HEIGHT) || (originalWidth / be > IMAGE_MAX_WIDTH)) {
                    be *= 2;
                }
                compressModeFlag = true;

                //5.如果文件本身小于200kb, 直接返回new File
                File file = new File(picPath);
                long size = getFileSizes(file);
                if (size != 0 && size < 1024 * 200) {
                    return file;
                }

            }

            //		Utils.print(" options.outWidth:" + options.outWidth
            //				+ "options.outHeight:" + options.outHeight + "---" + "be:" + be
            //				+ "---" + "shortLength:" + shortLength
            //				+ "  shortLength / 1280 :" + (shortLength / (float) 720));

            options.inSampleSize = be;
            // 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
            bitmap = BitmapFactory.decodeFile(picPath, options);

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
//            System.out.println(w + " " + h);
            // myImageView.setImageBitmap(bitmap);

            // 保存入SD卡
            File sdDir = new File(Environment.getExternalStorageDirectory().getPath() + "/66xue/picSelectedCache/");
            if (!sdDir.exists()) {
                sdDir.mkdirs();
            }
            File fileOutput = new File(sdDir, SystemClock.currentThreadTimeMillis() + ".jpg");

            try {
                if (compressModeFlag) {
                    // 模式1: 循环压缩至200kb
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                    int outOptions = 100;
                    //					Utils.print("大小:"+baos.toByteArray().length / 1024+"kb");
                    while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
                        baos.reset();// 重置baos即清空baos
                        bitmap.compress(Bitmap.CompressFormat.JPEG, outOptions, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                        outOptions -= 5;// 每次质量都减少5
                    }

                    FileOutputStream out = new FileOutputStream(fileOutput);
                    baos.writeTo(out);
                } else {
                    // 模式2: 直接按60%压缩
                    FileOutputStream out = new FileOutputStream(fileOutput);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out)) {
                        out.flush();
                        out.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            picsToDelete.add(fileOutput);
            compressModeFlag = true;// 把压缩模式置为初始状态:
            return fileOutput;
        } catch (Exception e) {
            e.printStackTrace();
            return new File(picPath);
        }
    }

    /**
     * Bitmap转换为InputStream方法
     * 
     * @param bm
     * @return
     */
    private static InputStream Bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)) {
            InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
            return sbs;
        }
        return null;
    }

    /**
     * 根据路径删除图片
     * 
     * @param path
     */
    public static void deleteTempFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接删除图片
     * 
     * @param file
     */
    public static void deleteTempFile(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有的缩小图片
     */
    public static void deleteCache() {
        try {
            if (picsToDelete != null && picsToDelete.size() > 0) {
                for (int i = 0; i < picsToDelete.size(); i++) {
                    if (picsToDelete.get(i) != null)
                        deleteTempFile(picsToDelete.get(i));
                }
            }
            picsToDelete = new ArrayList<File>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取File大小
     * 
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) {
        try {
            long s = 0;
            if (f.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(f);
                s = fis.available();
            } else {
                f.createNewFile();
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
