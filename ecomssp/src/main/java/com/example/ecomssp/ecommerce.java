package com.example.ecomssp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ecommerce extends Application {

    userinterface ui=new userinterface();
    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(ui.createcontent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}