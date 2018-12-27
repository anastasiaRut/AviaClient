package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Client  client = new Client();
        if (!client.getConnectToServer()) {
            ErrorWindow window = new ErrorWindow();
        }else {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 700, 400));
            primaryStage.show();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
