package com;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField login;

    @FXML
    private Button buttonRegistration;

    @FXML
    private Button closeButton;
    @FXML
    private Button buttonHide;
    @FXML
    private Button authoButton;

    @FXML
    private PasswordField password;
    @FXML
    private Label labelRegistration;

    @FXML
    void buttonHide(MouseEvent event) {
        Stage stage = (Stage) buttonHide.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void closeButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void buttonRegistration(MouseEvent event) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("registr.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void authorization(MouseEvent event) {
        if (login.getText().equals("") || password.getText().equals("")) {
            labelRegistration.setText("Поля не заполнены");
            return;
        }
        try {
            Client.oos.writeObject(2);
            Client.oos.writeObject(login.getText());
            Client.oos.writeObject(password.getText());
            Client.oos.flush();
            String entry = (String) Client.ois.readObject();
            if (entry.equals("admin")) {
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("adminMenu.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                Stage stageMainMenu = (Stage) buttonHide.getScene().getWindow();
                stageMainMenu.close();
                stage.show();

            } else if (entry.equals("Вошло!")) {
                labelRegistration.setText(entry);
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("userMenu.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                Stage stageMainMenu = (Stage) buttonHide.getScene().getWindow();
                stageMainMenu.close();
                UserMenuController.stage = stage;
                UserMenuController.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                });
               UserMenuController.show();

            } else labelRegistration.setText(entry);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {


    }

}
