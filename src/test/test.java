package src.test;

public class test{
    public static boolean match(String str) {
        String pattern = ".*\\(\\d{4}\\)=$";
        String pattern1="^.*\\(\\d{4}\\)=--$";
        if(str.matches(pattern)|str.matches(pattern1)){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        String t="LoginPwd(1178)=-12--";
        System.out.println(match(t));
    }
}
