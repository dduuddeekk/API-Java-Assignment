package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public static void server() throws IOException{
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 8056), 0);
        httpServer.createContext("/", new Handler.handler());
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }
}
