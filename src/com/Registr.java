package com;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Registr {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;
    @FXML
    private Button closeButton;
    @FXML
    private Button registrButton;
    @FXML
    private Label label;

    @FXML
    void closeButton(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void registrButton(MouseEvent event) {

        if (login.getText().equals("") || password.getText().equals("")) {
            label.setText("Поля не заполнены");
            return;
        }
        try {
            Client.oos.writeObject(1);
            Client.oos.writeObject(login.getText());
            Client.oos.writeObject(password.getText());
            Client.oos.flush();
            label.setText((String) Client.ois.readObject());
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
