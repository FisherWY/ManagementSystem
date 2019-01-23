package Utils;

/**
 *
 * @Date: 2018/9/26 7:17 PM
 * @author chf
 * 字符串加密类
 */

/**
 * @author chf
 * 字符串加密类
 */


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5byCHF {

    /**
     * 自定义密码编码
     *
     * @param key     ，解码密码
     * @param sourStr 加密字符串
     * @return 加密或解码后的字符串 String
     */

    public static String customEncrypt(String key, String sourStr) {
        StringBuilder strB = new StringBuilder();

        if (key.length() >= 5 && sourStr.length() > 0) {
            char[] keyChar = key.toCharArray();

            char[] sourStrByte = sourStr.toCharArray();

            for (char x : sourStrByte) {
                x = (char) (keyChar[5] ^ x);
                strB = strB.append((char) x);
            }


        } else {
            strB = strB.append("输入有误 两个参数都不能为空,密码必须超过5个字符");

        }

        return strB.toString();
    }


    public static String encryptMD5(String str)//自定义方法 ，实现MD5加密
    {
        BigInteger bigInterger = null;

        try {
            byte[] bt = str.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");//简单静态工厂方法
            md.update(bt);
            bigInterger = new BigInteger(md.digest());
            str = bigInterger.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return str;

    }
}
