package com.cims.common.util;

import com.cims.framework.exception.AppException;
import org.springframework.core.io.ClassPathResource;
import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author baidu
 * @date 2019/4/14 下午1:26
 * @description RSA 解密
 **/
public abstract class RSAUtils {

    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     * @param privateStr
     * @return
     * @throws Exception
     */
    public static PrivateKey string2PrivateKey(String privateStr) throws Exception{
        byte[] keyBytes = base642Byte(privateStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 转Base64编码字节数组
     * @param base64Key
     * @return
     * @throws IOException
     */
    public static byte[] base642Byte(String base64Key) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Key);
    }

    /**
     * 私钥解密
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }



    /**
     * 还原密文
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static String getDecrypted(String encrypted) {
        byte[] privateDecrypt;

        try {
            String privateKeyStr = getFileString();
            PrivateKey privateKey = string2PrivateKey(privateKeyStr);
            byte[] base642Byte = base642Byte(encrypted);
            privateDecrypt = privateDecrypt(base642Byte, privateKey);
        } catch (Exception e) {
            throw new AppException("密文解析异常",e);
        }

        return new String(privateDecrypt);
    }

    /**
     * 文件读取
     * @return
     * @throws IOException
     */
    private static String getFileString() throws IOException {
//        File file = ResourceUtils.getFile("classpath:private_key.txt");
        ClassPathResource resource = new ClassPathResource("private_key.txt");

        InputStream inputStream = resource.getInputStream();
        StringBuilder builder = new StringBuilder();
        int length;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            builder.append(new String(bytes, 0, length));
        }
        inputStream.close();

        return builder.toString();
    }
}
