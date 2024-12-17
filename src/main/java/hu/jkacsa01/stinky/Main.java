package hu.jkacsa01.stinky;

import hu.jkacsa01.stinky.network.ClientConnectionHandler;
import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws DeploymentException, IOException, InterruptedException {
        Server server = new Server(ClientConnectionHandler.class);
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