package me.ewriter.bangumitv.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Zubin on 2016/7/26.
 */
public class Md5Util {

    /**
     * Md5加密
     * @param seed 加密种子字符串
     * @return 机密后的十六进制字符串
     */
    public static String md5(String seed) {
        try {
            //TLog.i(Constants.LogTag, "### md5 encrypt:" + seed);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(seed.getBytes());
            byte messageDigest[] = digest.digest();

            return HexUtil.bytes2HexStr(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }


}
