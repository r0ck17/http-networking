package by.javaguru.server;

import util.PropertiesUtil;

public class HttpServerRunner {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(PropertiesUtil.getInt("port"));
        httpServer.run();
    }
}
