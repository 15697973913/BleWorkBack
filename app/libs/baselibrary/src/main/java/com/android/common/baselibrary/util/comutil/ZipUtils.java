package com.android.common.baselibrary.util.comutil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;
import java.util.ArrayList;

public class ZipUtils {
    public static String zipFiles(String srcZipFolderPath, String dstZipFilePath) throws ZipException {
        ZipFile zipFile = new ZipFile(dstZipFilePath);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(8);
        parameters.setCompressionLevel(5);
        zipFile.addFolder(srcZipFolderPath, parameters);
        return dstZipFilePath;
    }

    public static String zipFiles(ArrayList<File> zipFiles, String dstZipFilePath) throws ZipException {
        ZipFile zipFile = new ZipFile(dstZipFilePath);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(8);
        parameters.setCompressionLevel(5);
        zipFile.addFiles(zipFiles, parameters);
        return dstZipFilePath;
    }
}
