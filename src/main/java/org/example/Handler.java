package org.example;

import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
public class Handler {
    public static class handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            OutputStream outputStream = exchange.getResponseBody();
            String message = "";

            if ("GET".equals(exchange.getRequestMethod())) {
                //DI SINI GET!
            } else if ("POST".equals(exchange.getRequestMethod())){
                //DI SINI POST!
            } else if ("PUT".equals(exchange.getRequestMethod())){
                //DI SINI PUT!
            } else if ("DELETE".equals(exchange.getRequestMethod())){
                //DI SINI DELETE!
            } else {
                message = "Method not found.";
                exchange.sendResponseHeaders(404, message.length());
                outputStream.write(message.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}

class Users{
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;
}
class Products{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;
}
