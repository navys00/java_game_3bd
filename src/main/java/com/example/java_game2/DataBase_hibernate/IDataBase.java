package com.example.java_game2.DataBase_hibernate;

import java.util.ArrayList;

public interface IDataBase {
    void addPlayer(PlayerEntity entity);
    PlayerEntity getPlayerWins(String name);
    void setPlayerWins(PlayerEntity entity);
    void incrementPlayerWins(PlayerEntity entity);
    ArrayList<PlayerEntity> getAllPlayers();
}
