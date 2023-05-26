package com.example.java_game2.Server;

import java.util.ArrayList;
import com.example.java_game2.Constructor_objects.Circles;

public class ServerResponse {
    public ArrayList<ClientInfo> clientArrayList;
    public ArrayList<Circles> circleArrayList;
    public ArrayList<Circles> targetArrayList;
    public ArrayList<ClientInfo> playersEntities;
    public String theWinnerIs;
}
