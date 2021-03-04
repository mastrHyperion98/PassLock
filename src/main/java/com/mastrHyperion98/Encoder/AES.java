package com.mastrHyperion98.Encoder;

/*
Created by: Steven Smith
Created on: 2020-06-23'
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
AES Encoder is a class used to encrypt and decrypt string using AES 256 encryption standards.
 */

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    // Ideally this should be changed later to fetch a system environment variable
    // This ensure different result on different computers
    private static SecretKeySpec secretKey;
    private static String salt = "321saltyicecreamterminator!!!";
    private static IvParameterSpec ivspec = new IvParameterSpec(new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });

    /**
     *
     * @param strToEncrypt the String the user wants to encrypt.
     * @return returns the encrypted String.
     */
    public static String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static SealedObject encryptObject(Serializable object, SecretKeySpec key){
        Cipher cipher = null;
        SealedObject encryptedObject = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            encryptedObject = new SealedObject(object, cipher);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }finally{
            return encryptedObject;
        }
    }
    public static Object decryptObject(SealedObject object, SecretKeySpec key){
        Cipher cipher = null;
        Object decryptedObject = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
            decryptedObject = object.getObject(cipher);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }finally{
            return decryptedObject;
        }
    }
    /**
     *
     * @param strToDecrypt the string the that will be decrypted
     * @return the decrypted String.
     */
    public static String decrypt(String strToDecrypt) {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }

        return null;
    }

    /** Sets a new secretKey for the AES encoder/decoder.
     *
     * @param _secretKey a String parameter _secretKey that is used to set a new value to the local parameter secretKey.
     */
    public static void setSecretKey(SecretKey _secretKey){
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(Base64.getEncoder().encodeToString(_secretKey.getEncoded()).toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }
}


