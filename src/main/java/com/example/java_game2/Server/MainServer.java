package com.example.java_game2.Server;

import com.example.java_game2.DataBase_JDBC.DataBase;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

public class MainServer {
    final int port = 3124;
    InetAddress ip = null;
    final ExecutorService service = Executors.newFixedThreadPool(4);
    final ArrayList<Client> clientArrayList = new ArrayList<>();

    final Model model = ModelBuilder.build();

    public void bcast(){
        clientArrayList.forEach(Client::sendInfoToClient);
    }
    public void serverStart(){
        ServerSocket ss;
        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 2, ip);
            System.out.append("Server start\n");
            DataBase db = new DataBase();
            model.init(db);
            model.setMc(this);

            while(true)
            {
                Socket cs;
                cs = ss.accept();
                SocketWriter socketwriter = new SocketWriter(cs);
                String respName = socketwriter.getMessage();

                if (tryAddClient(socketwriter, respName)) {
                    System.out.println(respName + " Connected");
                } else {
                    cs.close();
                }
            }

        } catch (IOException ignored) {}
    }

    private boolean tryAddClient(SocketWriter socketwriter, String name) {
         if (clientArrayList.size() >= 4) {
             socketwriter.sendMessage("Превышено максимальное число подключений");
             return false;
         }
         if (clientArrayList.isEmpty() ||
                 clientArrayList.stream()
                .filter(client -> client.getPlayerName().equals(name))
                .findFirst()
                .orElse(null) == null) {
             socketwriter.sendMessage("ACCEPT");
             Client c = new Client(socketwriter, this, name);
             clientArrayList.add(c);
             service.submit(c);
             System.out.println("RESPONSE ACCEPT");
             return true;
         }
        socketwriter.sendMessage("Уже имеется игрок с таким именем");
        System.out.println("RESPONSE DECLINE");
        return false;
    }



    public static void main(String[] args) {
        new MainServer().serverStart();
    }

}
