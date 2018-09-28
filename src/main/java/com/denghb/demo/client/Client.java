package com.denghb.demo.client;


/*

only

<dependency>
      <groupId>org.apache.tomcat.embed</groupId>
      <artifactId>tomcat-embed-websocket</artifactId>
      <version>9.0.12</version>
    </dependency>

 */

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class Client {
    private static String uri = "ws://localhost:8080/mywebsocket";
    private static Session session;

    private void start() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI r = URI.create(uri);
            session = container.connectToServer(Handler.class, r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        onInput(client);
    }

    private static void onInput(Client client) throws InterruptedException {
        client.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            do {
                input = br.readLine();
                if (!input.equals("exit"))
                    client.session.getBasicRemote().sendText(input);
            } while (!input.equals("exit"));
        } catch (Exception e) {
            e.printStackTrace();

            // 重新链接
            Thread.sleep(1000);
            onInput(client);
        }
    }
}
