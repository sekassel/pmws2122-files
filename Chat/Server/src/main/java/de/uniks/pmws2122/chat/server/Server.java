package de.uniks.pmws2122.chat.server;

public class Server {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.init();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stopServer));
    }
}
