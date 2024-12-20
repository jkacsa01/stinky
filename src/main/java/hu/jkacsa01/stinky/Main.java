package hu.jkacsa01.stinky;

import hu.jkacsa01.stinky.network.WebsocketEndpoint;
import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws DeploymentException, IOException, InterruptedException {
        Server server = new Server(WebsocketEndpoint.class);
        server.start();
        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            server.stop();
        }
    }
}