package org.example;

public class Parsing {
    public static String[] path(String string){
        String[] result = string.split("/");
        return result;
    }
    public static int filterCheck(String string){
        char[] check = string.toCharArray();
        for(int i = 0; i < check.length; i++){
            if(check[i] == '?'){
                return 1;
            }
        }
        return 0;
   }
   public static String[] filter(String string){
        String[] result = string.split("&");
        return result;
   }
   public static String[] conditionFilter(String string){
        String[] result = string.split("=");
        return result;
   }
}

