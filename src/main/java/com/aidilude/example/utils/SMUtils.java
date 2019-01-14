package com.aidilude.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SMUtils {

    //###########################################配置项###########################################

    private static final String encode = "GBK";

    private static final String mobile = "13688405192";

//    private static final String username = "jimeihd";   // 短信
    private static final String username = "jimeiyy";   // 语音

//    private static final String password_md5 = "A1C0E236B4D85A29E3B6370523F613F8";  // 短信
    private static final String password_md5 = "6572bdaff799084b973320f43f09b363";  // 语音

//    private static final String apiKey = "2cba9167486a883aa2ea28002c22ddbb";   // 短信
    private static final String apiKey = "4db2ec91834309cce68b312d9fd74d64";   // 语音

    private static final String msgPrefix = "【】欢迎光临***，您的验证码是：";

    private static final String msgSeparate = "。";

    private static final String msgSuffix = "验证码有效时间为3分钟，请勿泄露。";

    //###########################################功能函数###########################################

    public static String createSM(String code){
        return msgPrefix + code + msgSeparate + msgSuffix;
    }

    public static boolean sendSM(String msg, String phone) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); //连接超时：30秒
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");    //读取超时：30秒
        StringBuffer buffer = new StringBuffer();
        try {
            String contentUrlEncode = URLEncoder.encode(msg, encode);
            buffer.append("http://m.5c.com.cn/api/send/index.php?username=" + username + "&password_md5=" + password_md5 + "&mobile=" + phone + "&apikey=" + apiKey + "&content=" + contentUrlEncode + "&encode=" + encode);
            URL url = new URL(buffer.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String result = reader.readLine();
            return result.indexOf("success") >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}