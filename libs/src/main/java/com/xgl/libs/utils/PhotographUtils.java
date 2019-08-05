package com.xgl.libs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/18.
 */

public final class PhotographUtils {
    public static final int RESULT_PHOTO = 0;
    public static final int RESULT_FILE = 1;
    private Context mContext;
    private File photoFile;


    public void openCamera(Activity activity) {
        mContext = activity;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
        createParent(photoFile);
        Uri uri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, RESULT_PHOTO);
    }

    public void openCamera(Fragment fragment) {
        mContext = fragment.getActivity();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
        createParent(photoFile);
        Uri uri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(intent, RESULT_PHOTO);
    }

    public void openPick(Activity activity) {
        mContext = activity;
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent1, RESULT_FILE);
    }

    public void openPick(android.support.v4.app.Fragment fragment) {
        mContext = fragment.getActivity();
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        fragment.startActivityForResult(intent1, RESULT_FILE);
    }

    public File onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_PHOTO) {
                File newFile = correctPhotoFile();
                photoFile.delete();
                return newFile;
            } else if (requestCode == RESULT_FILE) {
                String path = data.getData().getPath();
                if (TextUtils.isEmpty(path)) {
                    path = getPhotoPathByLocalUri(mContext, data);
                }
                if (!TextUtils.isEmpty(path)) {
                    Bitmap bitmap = compressBitmap(path);
                    return bitmapToFile(bitmap);
                }
            }
        }
        return null;
    }

    private File correctPhotoFile() {
        int readPicture = readPictureDegree(photoFile.getPath());
        Bitmap bitmap = compressBitmap(photoFile.getPath());
        if (readPicture > 0) {
            bitmap = rotaingImageView(readPicture, bitmap);
        }
        return bitmapToFile(bitmap);
    }

    private File bitmapToFile(Bitmap bitmap) {
        File result = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
        createParent(result);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(result));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getPhotoPathByLocalUri(Context context, Intent data) {
        String picturePath;
        try {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return picturePath;
    }


    private int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 图片旋转
     *
     * @param angle
     * @param bitmap
     * @return
     */
    private Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    private void createParent(File file) {
        File parent = new File(file.getParent());
        if (!parent.exists()) {
            parent.mkdir();
        }
    }

    //将图片进行质量压缩
    private Bitmap compressBitmap(String path) {
        //先解析图片边框的大小
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeFile(path, ops);
        ops.inSampleSize = 1;
        int oHeight = ops.outHeight;
        int oWidth = ops.outWidth;
        //控制压缩比
        int contentHeight = 500;
        int contentWidth = 500;
        if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
            ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
        } else {
            ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
        }
        ops.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, ops);
        return bm;
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return mContext.getPackageName() + "/" + dateFormat.format(date) + ".jpg";
    }
}
