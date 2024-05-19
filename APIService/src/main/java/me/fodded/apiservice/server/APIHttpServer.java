package me.fodded.apiservice.server;

import com.sun.net.httpserver.HttpServer;
import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class APIHttpServer {

    private final HttpServer httpServer;
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public APIHttpServer(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void startServer() {
        httpServer.createContext("/", new APIServerHandler());
        httpServer.setExecutor(executorService);
        httpServer.start();
    }
}
