package com.yondervision.yfmap.common.security;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;



public class KeySignature {
	 // 类型证书X509   
    public static final String CERT_TYPE = "X.509";   
    
    /**  
     * 由Certificate获得公钥  
     * @param certificatePath证书路径  
     * @return PublicKey 公钥  
     * @throws Exception  
     */  
    private static PublicKey getPublicKeyByCertificate(String certificatePath)   
            throws Exception {   
        // 获得证书   
        Certificate certificate = getCertificate(certificatePath);   
        // 获得公钥   
        return certificate.getPublicKey();   
    }   
  
    /**  
     * 获得Certificate  
     * @param certificatePath 证书路径  
     * @return Certificate 证书   
     * @throws Exception  
     */  
    private static Certificate getCertificate(String certificatePath)   
            throws Exception {   
        // 实例化证书工厂   
        CertificateFactory certificateFactory = CertificateFactory   
                .getInstance(CERT_TYPE);   
        // 取得证书文件流   
        FileInputStream in = new FileInputStream(certificatePath);   
        // 生成证书   
        Certificate certificate = certificateFactory.generateCertificate(in);   
        // 关闭证书文件流   
        in.close();   
        return certificate;   
    }  
  
    /**  
     * 公钥解密  
     * @param data 待解密数据  
     * @param certificatePath 证书路径  
     * @return byte[] 解密数据  
     * @throws Exception  
     */  
    public static byte[] decryptByPublicKey(byte[] data, String certificatePath)   
            throws Exception {   
        // 取得公钥   
        PublicKey publicKey = getPublicKeyByCertificate(certificatePath);   
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, publicKey);   
        return cipher.doFinal(data);   
    }   
  
    /**  
     * 验证签名  
     * @param data 数据  
     * @param sign 签名  
     * @param certificatePath 证书路径  
     * @return boolean 验证通过为真  
     * @throws Exception  
     * */  
    public static boolean verify(byte[] data, byte[] sign,   
            String certificatePath) throws Exception {   
        // 获得证书   
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);   
        // 由证书构建签名   
        Signature signature = Signature.getInstance(x509Certificate   
                .getSigAlgName());   
        // 由证书初始化签名，实际上是使用了证书中的公钥   
        signature.initVerify(x509Certificate);   
        signature.update(data);   
        return signature.verify(sign);   
    }   
}
