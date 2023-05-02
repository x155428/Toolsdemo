package src.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void decodeStr(String input){

        System.out.println("Original string: " + input);

        Pattern pattern = Pattern.compile("\\\\([0-9a-fA-F]{2})");
        Matcher matcher = pattern.matcher(input);

        StringBuilder outputBuilder = new StringBuilder();
        while (matcher.find()) {
            String hexString = matcher.group(1);
            int asciiCode = Integer.parseInt(hexString, 16);
            char asciiChar = (char) asciiCode;
            System.out.println(asciiChar);
            if (asciiChar >= 0 && asciiChar <= 126) {
                matcher.appendReplacement(outputBuilder, String.valueOf(asciiChar));
            }
            else{
                matcher.appendReplacement(outputBuilder, "");
            }
        }
        matcher.appendTail(outputBuilder);

        String output = outputBuilder.toString();
        System.out.println("Decoded string: " + output);
    }

    public static void main(String[] args) {
        String input = "SSH-2.0-Comware-7.1.064\\0d\\0a\\00\\00\\02d\\0b\\14\\e0\\82,j)\\bcPo\\9e\\d7\\17\\85W\\dd\\80?\\00\\00\\00\\7fecdh-sha2-nistp256,ecdh-sha2-nistp384,diffie-hellman-group-exchange-sha1,diffie-hellman-group14-sha1,diffie-hellman-group1-sha1\\00\\00\\00\\07ssh-rsa\\00\\00\\00iaes128-ctr,aes192-ctr,aes256-ctr,AEAD_AES_128_GCM,AEAD_AES_256_GCM,aes128-cbc,3des-cbc,aes256-cbc,des-cbc\\00\\00\\00iaes128-ctr,aes192-ctr,aes256-ctr,AEAD_AES_128_GCM,AEAD_AES_256_GCM,aes128-cbc,3des-cbc,aes256-cbc,des-cbc\\00\\00\\00Ghmac-sha2-256,hmac-sha2-512,hmac-sha1,hmac-md5,hmac-sha1-96,hmac-md5-96\\00\\00\\00Ghmac-sha2-256,hmac-sha2-512,hmac-sha1,hmac-md5,hmac-sha1-96,hmac-md5-96\\00\\00\\00\\1anone,zlib,zlib@openssh.com\\00\\00\\00\\1anone,zlib,zlib@openssh.com\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00\\00;";
        decodeStr(input);
    }
}
