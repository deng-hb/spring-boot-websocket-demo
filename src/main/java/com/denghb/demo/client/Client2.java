package com.denghb.demo.client;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

public class Client2 {

    private static String uri = "ws://localhost:8080/mywebsocket";

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            new TestThread(i).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static class TestThread extends Thread {

        private int i;

        public TestThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                mockConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void mockConnection() throws IOException, DeploymentException {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI r = URI.create(uri);
            Session session = container.connectToServer(Handler.class, r);
            session.getBasicRemote().sendText("hi" + i);

            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                session.getBasicRemote().sendText("hi" + i);
            }
        }
    }


}
