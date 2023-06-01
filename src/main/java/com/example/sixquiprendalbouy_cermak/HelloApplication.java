package com.example.sixquiprendalbouy_cermak;

import com.example.sixquiprendalbouy_cermak.models.Game;
import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Game game = new Game(stage);
        game.startGame();

    }

    public static void main(String[] args) {
        launch();
    }
}