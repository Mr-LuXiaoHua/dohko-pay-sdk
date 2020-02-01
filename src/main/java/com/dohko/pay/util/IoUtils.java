package com.dohko.pay.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author
 * @desc io操作
 */
public class IoUtils {

    /**
     * 将文件转成base64字符串
     * @param filePath 文件绝对路径
     * @return
     */
    public static String file2Base64String(String filePath) {
        String base64String = null;
        File file = new File(filePath);
        byte[] originalBytes;
        try {
            originalBytes = FileUtils.readFileToByteArray(file);
            base64String = Base64.encodeBase64String(originalBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
