package com.web.base.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HMAC{  
	  
    private static final String HMAC_SHA1 = "HmacSHA1";  
  
    /** 
     * 生成签名数据 
     *  
     * @param data 待加密的数据 
     * @param key  加密使用的key 
     * @return 生成MD5编码的字符串  
     * @throws InvalidKeyException 
     * @throws NoSuchAlgorithmException 
     */  
    public static byte[] HmacSHA1(byte[] data, byte[] key) throws Exception {  
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);  
        Mac mac = Mac.getInstance(HMAC_SHA1);  
        mac.init(signingKey);  
        byte[] rawHmac = mac.doFinal(data);  
        return rawHmac;  
    }  
    
} 