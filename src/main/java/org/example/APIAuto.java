////package org.example;
////
////import java.io.File;
////import java.util.Scanner;
////
////import com.sun.net.httpserver.Headers;
////import com.sun.net.httpserver.HttpExchange;
////import java.io.FileNotFoundException;
////
////public class APIAuto {
////    public static Boolean apiAuthorization(HttpExchange exchange) throws FileNotFoundException {
////        Headers requestHeaders = exchange.getRequestHeaders();
////        String headersKey = "main-api-key:"+requestHeaders.getFirst("main-api-key");
////        File file = new File(".env");
////        String api_key = null;
////        Scanner scanner = new Scanner(file);
////        while (scanner.hasNextLine()) {
////            String line = scanner.nextLine();
////            if (line.contains("main-api-key")) {
////                api_key = line;
////            } else{
////                api_key = "NULL";
////            }
////        }
////        scanner.close();
////        System.out.println(headersKey);
////        System.out.println(api_key);
////        if(headersKey.equals(api_key)){
////            return true;
////        }
////        return false;
////    }
////}
//package org.example;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//
//import com.sun.net.httpserver.Headers;
//import com.sun.net.httpserver.HttpExchange;
//
//public class APIAuto {
//    public static Boolean apiAuthorization(HttpExchange exchange) throws FileNotFoundException {
//        Headers requestHeaders = exchange.getRequestHeaders();
//        String headersKey = "main-api-key:" + requestHeaders.getFirst("main-api-key");
//        File file = new File(".env");
//        String api_key = null;
//        Scanner scanner = new Scanner(file);
//
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//
//            if (line.contains("main-api-key")) {
//                api_key = line;
//                break; // Exit the loop after finding the desired line
//            }
//        }
//
//        scanner.close();
//        System.out.println(headersKey);
//        System.out.println(api_key);
//
//        if (headersKey.equals(api_key)) {
//            return true;
//        }
//
//        return false;
//    }
//}
//
