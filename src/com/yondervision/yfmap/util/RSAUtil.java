package com.yondervision.yfmap.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class RSAUtil {
    /** 指定加密算法为DESede */
    private static String ALGORITHM = "RSA";
    /** 指定key的大小 */
    private static int KEYSIZE = 1024;
    /** 指定公钥存放文件 */
    private static String PUBLIC_KEY_FILE = "D:/workspace/gjjyecx/WebRoot/tmpPublicKey.dat";
    /** 指定私钥存放文件 */
    private static String PRIVATE_KEY_FILE = "D:/workspace/gjjyecx/WebRoot/tmpPrivateKey.dat";
    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final String OS = System.getProperty("os.name");

    private static final String LC = OS.toLowerCase().startsWith("win") ? "\r\n": "\n";

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对
     */
    private static void generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        System.out.println("公钥 :" + kp.getPublic().toString());
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        /** 得到私钥 */
        System.out.println("公钥 :" + kp.getPrivate().toString());
        Key privateKey = kp.getPrivate();
        /** 用对象流将生成的密钥写入文件 */
        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
        oos1.writeObject(publicKey);
        oos2.writeObject(privateKey);
        /** 清空缓存，关闭文件输出流 */
        oos1.close();
        oos2.close();
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source, String keyfile)
            throws Exception {
        // generateKeyPair();
        /** 将文件中的公钥对象读出 */
        PUBLIC_KEY_FILE = keyfile;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        Key key = (Key) ois.readObject();
        ois.close();
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes("UTF-8");
        int inputLen = b.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(b, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        BASE64Encoder encoder = new BASE64Encoder();
        String ens = encoder.encode(encryptedData);
        return ens.replaceAll(LC, "");
    }

    /**
     * 解密算法 cryptograph:密文
     */
    public static String decrypt(String cryptograph, String keyfile)
            throws Exception {
        /** 将文件中的私钥对象读出 */
        PRIVATE_KEY_FILE = keyfile;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
        Key key = (Key) ois.readObject();
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);

        int inputLen = b1.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(b1, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(b1, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "UTF-8").replaceAll(LC, "");
    }

    public static void main(String[] args) throws Exception {
        String para ="{\"ZJHM\":\"330205199109183323\"}";//要加密的字符串
        String encryptFile = "C:\\Users\\Admin\\Desktop\\nbPublicKey.dat";
        String miwen = encrypt(para.toString(), encryptFile);
        String decryptFile = "C:\\Users\\Admin\\Desktop\\nbPrivateKey.dat";
        miwen="X1I/q5SbtZJNS2p9wq/9UeEusgNGox1HefAi3ccQR/4Fcg6a6M6dZ7Acr0XvLQCBQtxd3W2z6uHB5/uPfIwpK5nW22NlkpAiCDbcHosxKUcJFAp6ATR170Q9vy9dplCSZbzRAv5vJULLY1cd8zlnrnE+zxBdq+U3lFelzkkjH+hyoO9HM95/LcwXadSD+c8DpWgocwpixvz50RrWNbbuU4R6C5Ndgu1x//7cjGA+MtNBXwLZ0JQiRsgM35MBFwo6dzK/Q/tNM7uY4vo77+mQnD67SzEIDMLkWN7uWZk2vSQhVCFGNQVmZSMfo7y/aujPoeBe+9TNO630nLo6fXph3ze1hLD9PpYKGZtf7SrB+W+Onmle9mOFGMHUPwTG62wI4n6KIN7rJfvqXePX8xCRGl3fmwnHPFaMTMu5j/2tAYTXcbLUHQmzNFBelssaqSayEgEhOc0DVnjmM0hGK8galzPNjFq93Id1S3m4JH4lA1PNBtCaJskszfuS6ox3OXj/RRlgYA8BYKgvsWbq0JycjeceDCBdzCdJRQ9Un12J3qL/957ibdUhizkJdrHOi0osA4whwqoz3tlVsikAmuBbcZF1v8d+mRUPr53VMVRfvBSW4gQ4cmmA+L0MXRbddmp6XlHIYLs1d/pyaq8HY7LOM4QkKVFiFBDpBrMPfPBm6v5/1Te69hucBSc4+3mbc5LXUoaI94zVDVSReq0xO4FwVWb8rfTt0vkmz0ol80Yeck4WizhE7ZqBiFtnpMHJ3XyUYszNOY27zj5W4hOXaWTsvLjF9z1U5Jt+ou9HchHFIR3lyegJDXhL7Vd3L0Wm3TFGeR5wjJulUtjEFATPfOzdgT2DKRRcF0ivb5qHOQXtEQ4gyfUc5spGsXXQH3m4DlltFGq/EfKUfmSTTapo7BnPIXsr4ICuUWxRZqksoSypVPXGdvLMYt4i1y7rgHZyt7Kf34hfpN8WDnY/JrGAQbuDdSSkdLxx0fH82sv1NUz/3uT9E95eVMlFNkLaOkiPGsZMM+jRnu0TQwnwefqV9RK3sf7sBspexiXXbmVwknhXeq6Vr8wFKypSFbVy1h5skYRIMAhF/+1xlkPHjBbroe2uX2x1PyhQ5p7+40Vb3a01rHTxppL4/zFZVg8qSfsoBsZm78Be4zresRnKKu8VX6lq35SB6PGL5Psok3Donfsur85FEYjM5ZXROJgl6rjW3U7GFNioxEJmcnisf3nRaFO+KsYxr3KSh4WLxaaZdvM9M7M1Bmyr4zx/KI1DY1OKWWa5AL9bDxwpwtE63nlqNkdaXcTB/fGjZmnmslDWSll5yiwWe73pjcIXxkSvRAQTfz+cfh8C0sk1Bd29hkOHCw/0CQ==";
        String target = decrypt(miwen,decryptFile);// 解密密文
        //密文
        System.out.println(miwen);
        //明文
        System.out.println(target);



    }
}