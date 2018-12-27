package com;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AirportsController {
    private boolean editAirportsFlag = false;
    private boolean emptyFlag = false;
    public static Stage stage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableColumn<Airport, String> idColumn;
    @FXML
    private TableColumn<Airport, String> nameColumn;
    @FXML
    private TableColumn<Airport, String> cityColumn;
    @FXML
    private TableView<Airport> tableAirports;
    @FXML
    private Label label;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button back;

    @FXML
    void initialize() {
        try {
            Client.oos.writeObject(5);
            Client.oos.flush();
            ObservableList<Airport> AirportsInit = FXCollections.observableArrayList();
            ArrayList<Airport> list = (ArrayList<Airport>) Client.ois.readObject();
            AirportsInit = FXCollections.observableList(list);
            AirportsInit.add(new Airport("", "", ""));
            idColumn.setCellValueFactory(new PropertyValueFactory<>("idAirport"));
            idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            tableAirports.setItems(AirportsInit);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void show() {
        stage.show();
    }

    @FXML
    void saveAction(MouseEvent event) {
        List<Airport> Airports = new ArrayList<>();
        Airport Airport = tableAirports.getItems().get(tableAirports.getItems().size() - 1);
        if (!emptyFlag) {
            if (Airport.getIdAirport().equals("") && Airport.getName().equals("") && Airport.getCity().equals(""))
                tableAirports.getItems().remove(tableAirports.getItems().size() - 1);
            else if (Airport.getIdAirport().equals("") || Airport.getName().equals("") || Airport.getCity().equals("")) {
                label.setText("Не заполнены поля!");
                emptyFlag = false;
                editAirportsFlag = false;
                return;
            }
        }
        if (!emptyFlag) {
            label.setText("");
            if (editAirportsFlag) {
                Airports.addAll(tableAirports.getItems());
                List<String> idAirports = new ArrayList<>();
                for (Airport d : Airports)
                    idAirports.add(d.getIdAirport());
                Set<String> setIdAirports = new HashSet<>();
                setIdAirports.addAll(idAirports);
                if (setIdAirports.size() < idAirports.size()) {
                    label.setText("id должны быть уникальны ");
                    return;
                }
                label.setText("");
                try {
                    Client.oos.writeObject(6);
                    Client.oos.flush();
                    Client.oos.writeObject(Airports);
                    Client.oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tableAirports.getItems().add((new Airport("", "", "")));
        } else {
            label.setText("Не заполнены поля!");
            emptyFlag = false;
            editAirportsFlag = false;
            return;
        }
        emptyFlag = false;
        initialize();

    }

    @FXML
    void deleteAction(MouseEvent event) {
        int row = tableAirports.getSelectionModel().getSelectedIndex();
        tableAirports.getItems().remove(row);
        editAirportsFlag = true;
    }

    @FXML
    void editCommitAirportId(TableColumn.CellEditEvent<Airport, String> cellEditEvent) {
        Airport airport = tableAirports.getSelectionModel().getSelectedItem();
        String oldId = airport.getIdAirport();
        airport.setIdAirport(cellEditEvent.getNewValue());
        if (airport.getIdAirport().equals(""))
            emptyFlag = true;
        else {
            try {
                Client.oos.writeObject(11);
                Client.oos.flush();
                Client.oos.writeObject(oldId);
                Client.oos.flush();
                Client.oos.writeObject(airport.getIdAirport());
                Client.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        editAirportsFlag = true;
        tableAirports.refresh();
    }

    @FXML
    void editCommitAirportName(TableColumn.CellEditEvent<Airport, String> cellEditEvent) {
        Airport Airport = tableAirports.getSelectionModel().getSelectedItem();
        Airport.setName(cellEditEvent.getNewValue());
        if (Airport.getName().equals(""))
            emptyFlag = true;
        editAirportsFlag = true;
        tableAirports.refresh();
    }

    @FXML
    void editCommitAirportCity(TableColumn.CellEditEvent<Airport, String> cellEditEvent) {
        Airport Airport = tableAirports.getSelectionModel().getSelectedItem();
        Airport.setCity(cellEditEvent.getNewValue());
        if (Airport.getCity().equals(""))
            emptyFlag = true;
        editAirportsFlag = true;
        tableAirports.refresh();
    }

    @FXML
    void back(MouseEvent event) {
        Stage stage = (Stage) tableAirports.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("adminMenu.fxml"));

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
