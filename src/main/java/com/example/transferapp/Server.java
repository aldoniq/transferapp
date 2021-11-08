package com.example.transferapp;


import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class Server extends WebSocketServer {

    public Server(int port) {
        super(new InetSocketAddress(port));

    }


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake
                .getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }
    String filename = "";

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        broadcast(message);
        filename = message;
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        BufferedWriter writer;
        String s = StandardCharsets.UTF_8.decode(message).toString();
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(conn + ": " + s);
    }

    public static void startServer() {
        try{
            int port = 7777; // 843 flash policy port
////        try {
////            port = Integer.parseInt(args[0]);
////        } catch (Exception ex) {
////        }
            Server s = new Server(port);
            s.start();
            s.setReuseAddr(true);
            System.out.println("Server started on port: " + s.getPort());

            new BufferedReader(new InputStreamReader(System.in));
//            while (true) {
//                String in = sysin.readLine();
//                s.broadcast(in);
//                if (in.equals("exit")) {
//                    s.stop(1000);
//                    break;
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public static void main(String[] args) throws InterruptedException, IOException {
//        int port = 5555; // 843 flash policy port
//        try {
//            port = Integer.parseInt(args[0]);
//        } catch (Exception ex) {
//        }
//        Server s = new Server(port);
//        s.start();
//        System.out.println("Server started on port: " + s.getPort());
//
//        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            String in = sysin.readLine();
//            s.broadcast(in);
//            if (in.equals("exit")) {
//                s.stop(1000);
//                break;
//            }
//        }
//    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        // some errors like port binding failed may not be assignable to a specific websocket
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

}