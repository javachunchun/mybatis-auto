package com.lcc.mybatis.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by hoze on 2018/1/15.
 */
public class UUIDUtil {

    public static final Charset _utf8 = Charset.forName("UTF-8");
    //得到32位的uuid4
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    //得到32位的uuid5
    public static String getUUID5(String name){
        return nameUUIDFromBytes(name.getBytes(_utf8)).toString().replace("-", "").toLowerCase();
    }

    public static UUID nameUUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalArgumentException("SHA-1 not supported", nsae);
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6]  &= 0x0f;  /* clear version        */
        md5Bytes[6]  |= 0x50;  /* set to version 5     */
        md5Bytes[8]  &= 0x3f;  /* clear variant        */
        md5Bytes[8]  |= 0x80;  /* set to IETF variant  */
        return uuid(md5Bytes);
    }

    public static UUID uuid(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i=0; i<8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i=8; i<16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        return new UUID(msb, lsb);
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtil.getUUID32());
        System.out.println(UUIDUtil.getUUID32());
        System.out.println(UUIDUtil.getUUID32());
        System.out.println(UUIDUtil.getUUID32());
        System.out.println(UUIDUtil.getUUID32());
        System.out.println(UUIDUtil.getUUID32());
        System.out.println(UUIDUtil.getUUID32());
        /*System.out.println(new BigDecimal(0.0).equals(BigDecimal.ZERO));
        System.out.println(new BigDecimal(0.0).divide(new BigDecimal(3.2)));*/
        /*NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(1);//这个1的意识是保存结果到小数点后几位，但是特别声明：这个结果已经先＊100了。
        System.out.println(nf.format(new BigDecimal(0.12345)));//自动四舍五入。*/
    }

}
