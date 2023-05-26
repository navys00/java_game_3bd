package com.example.java_game2;

import com.example.java_game2.Server.Model;
import com.example.java_game2.Server.ModelBuilder;
import com.example.java_game2.Server.ServerResponse;
import com.example.java_game2.Server.SocketWriter;
import com.google.gson.Gson;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class AuthorizePage {
    Socket socket;
    final int port = 3124;
    InetAddress ip = null;

    SocketWriter socketwriter;
    @FXML
    TextField nameField;

    final Model m = ModelBuilder.build();
    public void onConnect(MouseEvent mouseEvent) {
        try {
            ip = InetAddress.getLocalHost();
            socket = new Socket(ip, port);
            socketwriter = new SocketWriter(socket);
            socketwriter.sendMessage(nameField.getText().trim());
            String response = socketwriter.getMessage();
            if (response.equals("ACCEPT")) {
                new Thread(
                        ()->
                        {
                            try {
                                while (true) {
                                    String s = socketwriter.getData();
                                    System.out.println("Res: " + s);
                                    Gson gson = new Gson();
                                    ServerResponse ra = gson.fromJson(s, ServerResponse.class);
                                    m.setTargetArrayList(ra.circleArrayList);
                                    m.setClientArrayList(ra.clientArrayList);
                                    m.setArrowArrayList(ra.targetArrayList);
                                    m.setWinner(ra.theWinnerIs);
                                    m.setEntitiesList(ra.playersEntities);
                                    m.update();
                                }

                            } catch (IOException ignored) {

                            }

                        }
                ).start();
                openGamePage(mouseEvent);
            } else {
                double x = ((Node)(mouseEvent.getSource())).getScene().getWindow().getX();
                double y = ((Node)(mouseEvent.getSource())).getScene().getWindow().getY();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setX(x);
                alert.setY(y);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText(response);

                alert.showAndWait();
                nameField.setText("");
            }

        } catch (IOException ignored) {

        }

    }



    private void openGamePage(Event event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientFrame.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root1, 750, 520);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setResizable(true);
            stage.setTitle("Java Shooter Game.");
            stage.setScene(scene);
            stage.show();

            ((Node)(event.getSource())).getScene().getWindow().hide();

            ClientPage clientFrame = fxmlLoader.getController();
            clientFrame.dataInit(socketwriter, nameField.getText().trim());
            m.update();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
