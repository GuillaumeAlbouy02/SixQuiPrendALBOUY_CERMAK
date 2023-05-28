package com.example.sixquiprendalbouy_cermak.models;

import com.example.sixquiprendalbouy_cermak.views.Display;
import javafx.stage.Stage;
import lombok.Getter;

public class Game {
    private @Getter Stage stage;
    private @Getter Display ds;

    public Game(Stage stage) {
        this.stage = stage;
        this.ds = new Display(this, stage);
    }

    public void startGame(){
        ds.dsCreatePlayers();
    }
}
