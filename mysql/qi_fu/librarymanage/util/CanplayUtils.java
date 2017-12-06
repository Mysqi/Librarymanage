package com.mysql.qi_fu.librarymanage.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.base.ApplicationConfig;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peter on 2016/9/11.
 */

public class CanplayUtils {
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    public static float density = 1.0f;
    public static float scaledDensity = 1.0f;

    private static final NumberFormat format;
    private static final String redHtml = "<font color='red'>{0}</font>";

    private static String appSecret;

    // 图片类型
    private static final String DRAWABLE = "drawable";
    // 字符类型
    private static final String STRING = "string";
    // id类型
    private static final String ID = "id";
    // 颜色类型
    private static final String COLOR = "color";

    // 图片类型 MIPMAP
    private static final String MIPMAP = "mipmap";

    static {
        format = DecimalFormat.getCurrencyInstance(Locale.CHINA);
        format.setMaximumFractionDigits(2);
    }

    /**
     * 获取屏幕属性
     *
     * @param context
     */
    public static void getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        // ((BaseActivity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display mDisplay = wm.getDefaultDisplay();
        mDisplay.getMetrics(dm);
//        Log.d("width:" + mDisplay.getWidth());
//        Log.d("width:" + mDisplay.getHeight());

        density = dm.density;
        scaledDensity = dm.scaledDensity;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
//        Log.d("SCREEN_WIDTH:" + SCREEN_WIDTH);
//        Log.d("SCREEN_HEIGHT:" + SCREEN_HEIGHT);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        return (int) (pxValue / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue, Context context) {
        return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue, Context context) {
        return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * 设置textView文本值
     *
     * @param textView
     * @param text
     * @param nullText text为空的显示值
     */
    public static void setTextViewTxt(TextView textView, String text,
                                      String nullText) {
        nullText = nullText == null ? "" : nullText;
        text = text == null ? nullText : text;
        textView.setText(text);
    }

    /**
     * 设置textView文本值
     *
     * @param textView
     * @param text
     */
    public static void setTextViewTxt(TextView textView, String text) {
        setTextViewTxt(textView, text, null);
    }

    /**
     * 设置textView文本值
     *
     * @param textView
     * @param text
     */
    public static void setTextViewTxts(TextView textView, String text,
                                       Object... textValue) {
        if (TextUtils.isEmpty(text)) {
            textView.setText("");
        } else {
            text = MessageFormat.format(text, textValue);
            textView.setText(text);
        }
    }

    /**
     * 设置textView文本值
     *
     * @param textView
     * @param text
     */
    public static void setRedText(TextView textView, String text) {
        text = TextUtils.isEmpty(text) ? "" : text;
        textView.setText(Html.fromHtml(text));
    }

    /**
     * 设置textView文本值
     *
     * @param textView
     * @param text
     */
    public static void setRedText(TextView textView, String text,
                                  Object... textValue) {
        if (TextUtils.isEmpty(text)) {
            textView.setText("");
        } else {
            text = MessageFormat.format(text,
                    MessageFormat.format(redHtml, textValue));
            textView.setText(Html.fromHtml(text));
        }
    }

    /**
     * @param textView
     * @param amt
     */
    public static void setCurrencyText(TextView textView, String text,
                                       double amt) {
        setRedText(textView, text, getCurrencyStr(amt));
    }

    /**
     * @param context
     * @param textView
     * @param textResId
     * @param nullText
     */
    public static void setTextViewTxt(Context context, TextView textView,
                                      int textResId, String nullText) {
        nullText = nullText == null ? "" : nullText;
        String text = context.getString(textResId);
        setTextViewTxt(textView, text, nullText);
    }

    /**
     * @param context
     * @param view
     * @param textResId
     */
    public static void setErrorHtmlTxt(Context context, EditText view,
                                       Integer textResId) {
        if (textResId == null || textResId <= 0) {
            return;
        }
        setErrorHtmlTxt(view, context.getString(textResId));
    }

    /**
     * @param view
     * @param text
     */
    public static void setErrorHtmlTxt(EditText view, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        view.setError(Html.fromHtml(MessageFormat.format(
                "<font color='red'>{0}</font>", text)));
    }

    /**
     * @param context
     * @param view
     * @param textResId
     */
    public static void setError(Context context, EditText view,
                                Integer textResId) {
        if (textResId == null || textResId <= 0) {
            return;
        }
        setError(view, context.getString(textResId));
    }

    /**
     * @param view
     * @param text
     */
    public static void setError(EditText view, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        view.setError(Html.fromHtml(MessageFormat.format(redHtml, text)));
    }

    /**
     * 金额格式化
     *
     * @param currency
     * @return
     */
    public static String getCurrencyStr(double currency) {
        return format.format(currency);
    }

    public static long getFileSizes(File f) {// 取得文件大小
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                s = fis.available();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        } else {
        }
        return s;
    }


    /*
     * 获得发送类型对应文字描述
     */
    public static boolean textCheck(String text) {
        if (text != null) {
            if (!"".equals(text.trim()))
                return true;
        }
        return false;
    }

    /*
     * 日期转换 2015-5-7 20:36:37 转换为 2015年5月7日
     */
    public static String exChangeData(String data) {
        Date today = new Date(System.currentTimeMillis());
        String mToday = DateFormat.format("yyyy-MM-dd", today).toString();
        String[] day = data.split(" ");
        if (day[0].equals(mToday)) {
            // 当天
            return "今日" + day[1];
        }

        String[] targeDate = day[0].split("-");
        String year = targeDate[0];
        String month = targeDate[1];
        String days = targeDate[2];

        return year + "年" + subZero(month) + "月" + subZero(days) + "日";
    }

    /*
     * 截取小于10数字前的0 如：09 返回 9
     */
    public static String subZero(String str) {
        if (10 > Integer.parseInt(str)) {
            return str.substring(1);
        }
        return str;
    }


    // 使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    /*
     * 图片按大小压缩
     */
    public static Bitmap comp(Bitmap image, float width, float height) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = width;// 设置高度
        float ww = height;// 设置宽度
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;
    }

    /**
     * 压缩到指定路径 知指定大小
     *
     * @param srcPath
     * @return
     */
    public static boolean getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return saveBitmap2file(compressImage(bitmap), srcPath);// 压缩好比例大小后再进行质量压缩
    }

    /*
     * 图片按比例大小压缩
     */
    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = SCREEN_HEIGHT;// 设置高度
        float ww = SCREEN_WIDTH;// 设置宽度
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;// 降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    public static boolean saveBitmap2file(Bitmap bmp, String path) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

    /*
     * 质量压缩图片
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static int reckonThumbnail(int oldWidth, int oldHeight,
                                      int newWidth, int newHeight) {
        if ((oldHeight > newHeight && oldWidth > newWidth)
                || (oldHeight <= newHeight && oldWidth > newWidth)) {
            int be = (int) (oldWidth / (float) newWidth);
            if (be <= 1)
                be = 1;
            return be;
        } else if (oldHeight > newHeight && oldWidth <= newWidth) {
            int be = (int) (oldHeight / (float) newHeight);
            if (be <= 1)
                be = 1;
            return be;
        }
        return 1;
    }

    public static Bitmap PicZoom(Bitmap bmp, int width, int height) {
        int bmpWidth = bmp.getWidth();
        int bmpHeght = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);

        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
    }

//    /**
//     * 生成二维码 要转换的地址或字符串,可以是中文
//     *
//     * @param url
//     * @param width
//     * @param height
//     * @return
//     */
//    public static Bitmap createQRImage(Context context, String data,
//                                       final int width, final int height) {
//        Intent intent = new Intent();
//        intent.setAction(Intents.Encode.ACTION);
//        intent.putExtra(Intents.Encode.DATA, data);
//        intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE);
//        intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
//        int smallerDimension = width < height ? width : height;
//        smallerDimension = smallerDimension * 7 / 8;
//        QRCodeEncoder qrCodeEncoder;
//        Bitmap bitmap;
//        try {
//            Logger.d("step1");
//            boolean useVCard = false;
//            qrCodeEncoder = new QRCodeEncoder(context, intent,
//                    smallerDimension, useVCard);
//            bitmap = qrCodeEncoder.encodeAsBitmap();
//            if (bitmap == null) {
//                qrCodeEncoder = null;
//                return null;
//            }
//            Logger.d("step2");
//        } catch (WriterException e) {
//            qrCodeEncoder = null;
//            return null;
//        }
//        return bitmap;
//    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     * Created on: 2011-8-2 Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /*
     * 获取图片旋转角度
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
//            Log.e("getExifOrientation:" + ex.getMessage());
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return resizedBitmap;
    }

    /*
     * 关闭软键盘
	 */
    public static void closeKeyboard(Context context) {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) ((Activity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public static int generateViewId() {
        AtomicInteger sNextGeneratedId = new AtomicInteger(1);
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * 判断手机格式
     *
     * @param mobiles 手机号
     * @return true: 符合 false: 不符合
     */
    public static boolean isMobileNO2Contact(String mobiles) {
        if (mobiles.length() != 11) {
            return false;
        }
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 检测网络是否可用
     *
     * @param context 上下文
     * @return true:可用； false:不可用
     */
    public static boolean isNetworkAccessiable(Context context) {
        try {
            if (context != null) {
                ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (CM != null) {
                    NetworkInfo info = CM.getActiveNetworkInfo();
                    if (info != null) {
                        return info.isConnected();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static long lastClickTime;

    /**
     * @Title: isTooWorryClick
     * @Description: TODO 防止在一定的时间内点击多次
     * @return: boolean
     */
    public synchronized static boolean isTooWorryClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * @param versionServer
     * @param versionLocal
     * @return if versionServer > versionLocal, return 1, if equal, return 0, else return
     * -1
     */
    public static int versionComparison(String versionServer, String versionLocal) {
        String version1 = versionServer;
        String version2 = versionLocal;
        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0)
            throw new IllegalArgumentException("Invalid parameter!");

        int index1 = 0;
        int index2 = 0;
        while (index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            int[] number2 = getValue(version2, index2);

            if (number1[0] < number2[0]) {
                return -1;
            } else if (number1[0] > number2[0]) {
//                Logger.i(" ===== number1[0] ====" + number1[0]);
//                Logger.i(" ===== number2[0] ====" + number2[0]);
                return 1;
            } else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }
        if (index1 == version1.length() && index2 == version2.length())
            return 0;
        if (index1 < version1.length())
            return 1;
        else
            return -1;
    }

    /**
     * @param version
     * @param index   the starting point
     * @return the number between two dots, and the index of the dot
     */
    public static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;
        return value_index;
    }


    public static String getPrice(String str) {
        //将传进数字反转
        String reverseStr = new StringBuilder(str).reverse().toString();
        String strTemp = "";
        String dotLast = "";
        if (reverseStr.contains(".")) {
            dotLast = new StringBuilder(reverseStr.substring(0, reverseStr.indexOf("."))).reverse().toString();
            reverseStr = reverseStr.substring(reverseStr.indexOf(".") + 1, reverseStr.length());
        }
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        //将[789,456,] 中最后一个[,]去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        if (TextUtils.isEmpty(dotLast)) {
            return resultStr;
        } else {
            return resultStr + "." + dotLast;
        }
    }


    /**
     * 根据名称获取id资源的id
     *
     * @param name    资源名称
     * @param context 上下文
     * @return id资源的id
     */
    public static int getIDResId(String name, Context context) {
        return getResId(ID, name, context);
    }

    /**
     * 根据名称获取字符串资源的id
     *
     * @param name    资源名称
     * @param context 上下文
     * @return 字符串资源的id
     */
    public static int getStringResId(String name, Context context) {
        return getResId(STRING, name, context);
    }


    /**
     * 根据资源类型和资源名称动态获取资源的id 例如: R.drawable.value type = "drawable" 传入的类型 上面定义的常量
     * name = "value" 传入的名称
     * <p>
     * R.id.value type = "id" 传入的类型 name = "value" 更多
     */
    private static int getResId(String type, String name, Context context) {
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(name)) {
            return -1;
        }
        return context.getResources().getIdentifier(name, type,
                context.getPackageName());
    }

    /**
     * 根据名称获取图片资源的id
     *
     * @param name    资源名称
     * @param context 上下文
     * @return 图片资源的id
     */
    public static int getDrawableResId(String name, Context context) {
        return getResId(DRAWABLE, name, context);
    }

    /**
     * 根据名称获取图片资源的id
     *
     * @param name    资源名称
     * @param context 上下文
     * @return 图片资源的id
     */
    public static int getMipMapResId(String name, Context context) {
        return getResId(MIPMAP, name, context);
    }


    /**
     * 根据名称获取color资源的id
     *
     * @param name    资源名称
     * @param context 上下文
     * @return color资源id
     */
    public static int getColorResId(String name, Context context) {
        return getResId(COLOR, name, context);
    }

    /**
     * 获取assets下文件的数据
     *
     * @return 字符串
     */
    public static String getAssetsFileContent(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return null;
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    ApplicationConfig.resouce.getAssets().open(fileName));
            if (inputReader != null) {
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = bufReader.readLine()) != null)
                    builder.append(line);
                return builder.toString();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView, Context context) {
        int dp_400 = (int) context.getResources().getDimension(R.dimen.dp_400);
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight >= dp_400 ? dp_400 : totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * uri 转File
     *
     * @param activity
     * @param uri
     * @return
     */
    public static File uri2File(Activity activity, Uri uri) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }

    public static String timeFormate(long times) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(times)).toString();
    }

    public static String timeFormate2(long times) {
        return DateFormat.format("yyyy-MM-dd HH:mm", new Date(times)).toString();
    }

    public static String timeFormateDetial(long times) {
        return DateFormat.format("MM月dd日HH点", new Date(times)).toString();
    }

    public static String timeFormateYear(long times) {
        return DateFormat.format("yyyy-MM-dd", new Date(times)).toString();
    }

    public static String timeFormateYearMoth(long times) {
        return DateFormat.format("yyyy/MM/dd", new Date(times)).toString();
    }

    public static String timeCountdownFormate(long times) {
        return DateFormat.format("mm:ss", new Date(times)).toString();
    }
}
