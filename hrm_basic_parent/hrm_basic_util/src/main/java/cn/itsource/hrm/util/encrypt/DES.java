package cn.itsource.hrm.util.encrypt;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
    /** 加密、解密key. */
    private static final String PASSWORD_CRYPT_KEY = "XUIKOO01sampso0286666666@$^*&%#!";
    private static final String APP_PASSWORD_CRYPT_KEY = "C670$^871cd#!kj028@66Y*&QKJ6Dyq%";
    private final static String ALGORITHM = "DES";
    public static void main(String[] args) throws Exception {
        String text = "nixianhua";
        String str = DES.encrypt(text);
        System.out.println("enstr: " + str);
        str = DES.decrypt(str);
        System.out.println("destr: " + str);
    }
    
    /**
     * 对数据进行DES解密.
     * @param data 待进行DES加密的数据
     * @return 返回经过DES解密后的数据
     * @throws Exception
     */
    public final static String decrypt(String data) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()),
                PASSWORD_CRYPT_KEY.getBytes()));
    }
    /**
     * 对客户端数据进行DES解密.
     * @param data 待进行DES加密的数据
     * @return 返回经过DES解密后的数据
     * @throws Exception
     */
    public final static String decryptClient(String data) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()),
        		APP_PASSWORD_CRYPT_KEY.getBytes()));
    }
    /**
     * 对客户端进行DES加密.
     * @param data DES加密数据
     * @return 返回解密后的数据
     * @throws Exception
     */
    public final static String encryptClient(String data) throws Exception  {
        return byte2hex(encrypt(data.getBytes(), APP_PASSWORD_CRYPT_KEY
                .getBytes()));
    }
    
    /**
     * 对用DES加密过的数据进行解密.
     * @param data DES加密数据
     * @return 返回解密后的数据
     * @throws Exception
     */
    public final static String encrypt(String data) throws Exception  {
        return byte2hex(encrypt(data.getBytes(), PASSWORD_CRYPT_KEY
                .getBytes()));
    }
    
    /**
     * 用指定的key对数据进行DES加密.
     * @param data 待加密的数据
     * @param key DES加密的key
     * @return 返回DES加密后的数据
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(data);
    }
    /**
     * 用指定的key对数据进行DES解密.
     * @param data 待解密的数据
     * @param key DES解密的key
     * @return 返回DES解密后的数据
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(data);
    }
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
}