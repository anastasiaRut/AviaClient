package com;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminMenu {
    private boolean editUsersFlag = false;
    private boolean emptyFlag = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label label;

    @FXML
    private MenuItem userMenu;


    @FXML
    private MenuItem exitMenu;
    @FXML
    private MenuItem doctors;

    @FXML
    private TableView<User> tableUser;
    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;


    @FXML
    void deleteEvent(MouseEvent event) {
        int row = tableUser.getSelectionModel().getSelectedIndex();
        tableUser.getItems().remove(row);
        editUsersFlag = true;
    }

    @FXML
    void saveEvent(MouseEvent event) {
        if (editUsersFlag) {
            if (!emptyFlag) {
                label.setText("");
                List<User> users = new ArrayList<>();
                users.addAll(tableUser.getItems());
                List<String> logins = new ArrayList<>();
                for (User u : users)
                    logins.add(u.getLogin());
                Set<String> setLogins = new HashSet<>();
                setLogins.addAll(logins);
                if (setLogins.size() < users.size()) {
                    label.setText("логины должны быть уникальными ");
                    return;
                }
                editUsersFlag = false;
                try {
                    Client.oos.writeObject(4);
                    Client.oos.flush();
                    Client.oos.writeObject(users);
                    Client.oos.flush();
                    Client.oos.writeObject(3);
                    Client.oos.flush();
                    ObservableList<User> usersFX = FXCollections.observableArrayList();
                    ArrayList<User> list = (ArrayList<User>) Client.ois.readObject();
                    usersFX = FXCollections.observableList(list);
                    loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
                    loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
                    tableUser.setItems(usersFX);
                    tableUser.setVisible(true);
                    deleteButton.setVisible(true);
                    saveButton.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                label.setText("Не заполнены поля!");
                emptyFlag = false;
                editUsersFlag = false;
                return;
            }
        }

    }

    @FXML
    void exitMenu(ActionEvent event) {
        Stage stage = (Stage) tableUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editCommitUserLogin(TableColumn.CellEditEvent<User, String> sellEditEvent) {
        User user = tableUser.getSelectionModel().getSelectedItem();
        user.setLogin(sellEditEvent.getNewValue());
        editUsersFlag = true;
        if (user.getLogin().equals(""))
            emptyFlag = true;
    }

    @FXML
    void initialize() {


    }

    @FXML
    public void showUsers(ActionEvent event) {
        try {
            Client.oos.writeObject(3);
            Client.oos.flush();
            ObservableList<User> users = FXCollections.observableArrayList();
            ArrayList<User> list = (ArrayList<User>) Client.ois.readObject();
            users = FXCollections.observableList(list);

            loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            //  passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            tableUser.setItems(users);
            tableUser.setVisible(true);
            deleteButton.setVisible(true);
            saveButton.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void showAirports(ActionEvent event) {
        Stage stage = (Stage) tableUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("airports.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            AirportsController.stage = primaryStage;
            AirportsController.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showClasses(ActionEvent event) {
        Stage stage = (Stage) tableUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("flightClasses.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            FlightClassesController.stage = primaryStage;
            FlightClassesController.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showFlights(ActionEvent event) {
        Stage stage = (Stage) tableUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("flights.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            FlightClassesController.stage = primaryStage;
            FlightClassesController.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

