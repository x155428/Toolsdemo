package src.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dealString {
    //ԭ����
    public String originaldata="";
    //ԭ����ȥ��payload
    public String originalDelPayload="";
    //payload
    public String payload="";
    //ͷ����Ϣ
    public String head="";
    public LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    public LinkedHashMap<String,String> resultmap=new LinkedHashMap<String,String>();


    //hashmap������Ľ���
    public dealString(String originaldata){
        this.originaldata=originaldata;
        //��ʼ��map
        map.put("Protocol","Э�飺");
        map.put("Application","Ӧ��/����");
        map.put("SrcIPAddr","ԴIP��ַ��");
        map.put("SrcPort","Դ�˿ڣ�");
        map.put("DstIPAddr","Ŀ��IP��ַ��");
        map.put("DstPort","Ŀ�Ķ˿ڣ�");
        map.put("RcvVPNInstance","ԴVPN��ַ��");
        map.put("SrcZoneName","Դ��ȫ��");
        map.put("DstZoneName","Ŀ�İ�ȫ��");
        map.put("UserName","�û�����");
        map.put("PolicyName","ƥ���������");
        map.put("AttackName","�������ƣ�");
        map.put("AttackID","ƥ������ID��");
        map.put("Category","�������ͣ�");
        map.put("Protection","�������ͣ�");
        map.put("SubProtection","���������ͣ�");
        map.put("Severity","���س̶ȣ�");
        map.put("Action","������");
        map.put("CVE","CVE��ţ�");
        map.put("BID","BID��ţ�");
        map.put("MSB","MSB��ţ�");
        map.put("HitDirection","���б��ķ���");
        map.put("RealSrcIP","��ʵԴIP��");
        map.put("SubCategory","���������ͣ�");
        map.put("LoginUserName","��¼�û�����");
        map.put("LoginPwd","��¼���룺");
        map.put("CapturePktName","�����İ�����");
        map.put("HttpHost","HTTP������");
        map.put("HttpFirstLine","HTTPͷ��");
        map.put("SrcMacAddr","ԴMAC��ַ��");
        map.put("DstMacAddr","Ŀ��MAC��ַ��");
        map.put("RealSrcMacAddr","��ʵԴMAC��ַ��");
        map.put("RealDstMacAddr","��ʵĿ��MAC��ַ��");
        map.put("PayLoad","�����غɣ�");
        //ƥ�䵽payload�ַ������ŵ�resultmap��
        Pattern pattern = Pattern.compile("PayLoad.*?$");
        Matcher matcher = pattern.matcher(this.originaldata);
        if (matcher.find()) {
            payload = matcher.group();
        }
        //payload�ַ�����ɿ��ַ���
        originalDelPayload=this.originaldata.replace(payload,"");
        //�÷ֺŷָ��ַ���
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

    //ȥ�������п��ַ���
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

    //��ȥpayload��ͷ�������map����Ӧ����
    public static LinkedHashMap<String, String> resultINmap(String s[], LinkedHashMap<String,String> resultmap, LinkedHashMap<String,String> map){
        for (String tmp:s) {
            Pattern pattern = Pattern.compile("^(.*?)\\(");
            Matcher matcher = pattern.matcher(tmp);
            if (matcher.find()) {
                String result = matcher.group(1);
                //ƥ�䵽��result��key��map���ҵ����ģ������ĺ��ַ���tmpһ�����resultmap
                //System.out.println("ƥ�䵽��ֵ��"+result+"####"+"����"+map.get(result.trim())+"ֵ��"+tmp);
                resultmap.put(map.get(result.trim()),tmp);
                //System.out.println(result);  // ���
            }

        }
        return resultmap;
    }

    //����payload,ɾ��\00�ȣ�����ascii
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
