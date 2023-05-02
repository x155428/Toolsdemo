package src.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dealString {
    //原数据
    public String originaldata="";
    //原数据去除payload
    public String originalDelPayload="";
    //payload
    public String payload="";
    //头部信息
    public String head="";
    public LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    public LinkedHashMap<String,String> resultmap=new LinkedHashMap<String,String>();


    //hashmap存放中文解释
    public dealString(String originaldata){
        this.originaldata=originaldata;
        //初始化map
        map.put("Protocol","协议：");
        map.put("Application","应用/服务：");
        map.put("SrcIPAddr","源IP地址：");
        map.put("SrcPort","源端口：");
        map.put("DstIPAddr","目的IP地址：");
        map.put("DstPort","目的端口：");
        map.put("RcvVPNInstance","源VPN地址：");
        map.put("SrcZoneName","源安全域：");
        map.put("DstZoneName","目的安全域：");
        map.put("UserName","用户名：");
        map.put("PolicyName","匹配策略名：");
        map.put("AttackName","攻击名称：");
        map.put("AttackID","匹配特征ID：");
        map.put("Category","攻击类型：");
        map.put("Protection","防护类型：");
        map.put("SubProtection","防护子类型：");
        map.put("Severity","严重程度：");
        map.put("Action","动作：");
        map.put("CVE","CVE编号：");
        map.put("BID","BID编号：");
        map.put("MSB","MSB编号：");
        map.put("HitDirection","命中报文方向：");
        map.put("RealSrcIP","真实源IP：");
        map.put("SubCategory","攻击子类型：");
        map.put("LoginUserName","登录用户名：");
        map.put("LoginPwd","登录密码：");
        map.put("CapturePktName","捕获报文包名：");
        map.put("HttpHost","HTTP主机：");
        map.put("HttpFirstLine","HTTP头：");
        map.put("SrcMacAddr","源MAC地址：");
        map.put("DstMacAddr","目的MAC地址：");
        map.put("RealSrcMacAddr","真实源MAC地址：");
        map.put("RealDstMacAddr","真实目的MAC地址：");
        map.put("PayLoad","攻击载荷：");
        //匹配到payload字符串，放到resultmap中
        Pattern pattern = Pattern.compile("PayLoad.*?$");
        Matcher matcher = pattern.matcher(this.originaldata);
        if (matcher.find()) {
            payload = matcher.group();
        }
        //payload字符串变成空字符串
        originalDelPayload=this.originaldata.replace(payload,"");
        //用分号分隔字符串
        String result[]=this.originalDelPayload.split(";");
        head=result[0];
        result[0]="";
        result=removeEmptyStrings(result);
        /*for (String tt:result
             ) {System.out.println(tt);

        }*/
        resultmap=resultINmap(result,resultmap,map);
        resultmap.put(map.get("PayLoad"),payload);
       /*for (String key : resultmap.keySet()) {
            String value = resultmap.get(key);
            System.out.println("Key = " + key + ", Value = " + value);
        }*/

    }

    //去除数组中空字符串
    public static String[] removeEmptyStrings(String[] arr) {
        List<String> list = new ArrayList<>();
        for (String s : arr) {
            if (!s.trim().isEmpty()) {
                list.add(s);
            }
        }
        return list.toArray(new String[0]);
    }

    public static void showarr(String arr[]){
        for (String s:arr) {
            System.out.println(s);
        }
    }

    //除去payload和头部后放入map，对应中文
    public static LinkedHashMap<String, String> resultINmap(String s[], LinkedHashMap<String,String> resultmap, LinkedHashMap<String,String> map){
        for (String tmp:s) {
            Pattern pattern = Pattern.compile("^(.*?)\\(");
            Matcher matcher = pattern.matcher(tmp);
            if (matcher.find()) {
                String result = matcher.group(1);
                //匹配到的result做key到map中找到中文，将中文和字符串tmp一起放入resultmap
                //System.out.println("匹配到的值："+result+"####"+"建："+map.get(result.trim())+"值："+tmp);
                resultmap.put(map.get(result.trim()),tmp);
                //System.out.println(result);  // 输出
            }

        }
        return resultmap;
    }

    //处理payload,删除\00等，解码ascii
    public static String decodeStr(String input){

        System.out.println("Original string: " + input);

        Pattern pattern = Pattern.compile("\\\\([0-9a-fA-F]{2})");
        Matcher matcher = pattern.matcher(input);

        StringBuilder outputBuilder = new StringBuilder();
        while (matcher.find()) {
            String hexString = matcher.group(1);
            int asciiCode = Integer.parseInt(hexString, 16);
            char asciiChar = (char) asciiCode;
            if (asciiChar >= 0 && asciiChar <= 126) {
                matcher.appendReplacement(outputBuilder, String.valueOf(asciiChar));
            }
            else{
                matcher.appendReplacement(outputBuilder, "");
            }
        }
        matcher.appendTail(outputBuilder);

        String output = outputBuilder.toString();
        return output;
    }

}
