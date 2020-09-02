package com.zj.zhijue.helper;

import android.net.Uri;
import android.os.Environment;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class TakePhotoCustomHelper {
    private boolean rgCrop = false;//是否裁剪
    private boolean rgCompress = true;//是否压缩
    private boolean rgCompressTool = true;//是否使用第三方压缩工具,
    private boolean rgCropSourceSize = true;//是否原图片的宽和高
    private boolean rgFrom = true;//默认从相册
    private boolean takePictureFromCamera = false;//是否拍照
    private boolean rgPickTool = false;//使用TakePhoto自带相册
    private boolean rgRawFile = true;//拍照压缩后是否保存原图
    private boolean rgCorrectTool = true;//纠正拍照的照片旋转角度
    private boolean rgShowProgressBar = false;//显示压缩进度条
    private boolean rgCropTool = true;//裁切工具 第三方false ,TakePhoto自带 true;
    private int etCropHeight = 800;
    private int etCropWidth = 800;
    private int etLimit = 1;
    private int etSize = 1024 * 1024 * 2;//大小不超过 B
    private int etHeightPx = 1920;
    private int etWidthPx = 1080;


    public static TakePhotoCustomHelper of() {
        return new TakePhotoCustomHelper();
    }

    private TakePhotoCustomHelper() {

    }

    public void initConfig(TakePhoto takePhoto, boolean camera) {
        takePictureFromCamera = camera;
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        if (etLimit > 1) {
            if (rgCrop) {
                takePhoto.onPickMultipleWithCrop(etLimit, getCropOptions());
            } else {
                takePhoto.onPickMultiple(etLimit);
            }
        }

        if (takePictureFromCamera) {
            /**
             * 拍照选择图片
             */
            if (rgCrop) {
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(imageUri);
            }
        } else {
            /**
             * 从相册或者文档中选择图片
             */
            if (rgFrom) {//是否从相册选择文件

                if (rgCrop) {//是否裁剪
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }

            } else {
                /*从文档中选择图片
                 */
                if (rgCrop) {//是否裁剪
                    takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromDocuments();
                }

            }
        }

    }


    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        if (rgPickTool) {
            /**
             * 是否使用TakePhoto自带相册
             */
            builder.setWithOwnGallery(true);
        }

        if(rgCorrectTool) {
            /**
             * 是否支持旋转
             */
            builder.setCorrectImage(true);
        }

        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config = null;
        if (!rgCompress) {
            /**
             * 是否压缩
             */
            return;
        }

        if (rgCompressTool) {
            config = new CompressConfig.Builder().setMaxSize(etSize)
                    .setMaxPixel(etWidthPx >= etHeightPx ? etWidthPx : etHeightPx)
                    .enableReserveRaw(rgRawFile)
                    .create();
        } else {
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(etHeightPx).setMaxWidth(etWidthPx).setMaxSize(etSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(rgRawFile);
        }
        takePhoto.onEnableCompress(config, rgShowProgressBar);
    }

    private CropOptions getCropOptions() {
        if (!rgCrop) {
            return null;
        }

        CropOptions.Builder builder = new CropOptions.Builder();

        if (rgCropSourceSize) {
            builder.setAspectX(etCropWidth).setAspectY(etCropHeight);
        } else {
            builder.setOutputX(etCropWidth).setOutputY(etCropHeight);
        }
        builder.setWithOwnCrop(rgCropTool);
        return builder.create();
    }

}
