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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FlightsController {
    private boolean editFlightsFlag = false;
    private boolean emptyFlag = false;
    public static Stage stage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableView<Flight> tableFlights;
    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> destinationCodeColumn;
    @FXML
    private TableColumn<Flight, String> dayColumn;
    @FXML
    private TableColumn<Flight, String> monthColumn;
    @FXML
    private TableColumn<Flight, String> yearColumn;
    @FXML
    private TableColumn<Flight, String> classOfFlightColumn;
    @FXML
    private TableColumn<Flight, String> seatsLeftColumn;
    @FXML
    private TableColumn<Flight, String> flightTimeColumn;

    @FXML
    private Label label;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button back;

    public static void show() {
        stage.show();
    }

    @FXML
    void initialize() {
        try {
            Client.oos.writeObject(9);
            Client.oos.flush();
            ObservableList<String> dayValues = FXCollections.observableArrayList();
            for (int i = 1; i <= 31; i++)
                dayValues.add(Integer.toString(i));

            ObservableList<String> yearValues = FXCollections.observableArrayList("2018", "2019");
            ObservableList<String> monthValues = FXCollections.observableArrayList();
            for (int i = 1; i <= 12; i++)
                monthValues.add(Integer.toString(i));

            ObservableList<Flight> flightObservableList = FXCollections.observableArrayList();
            ArrayList<Flight> list = (ArrayList<Flight>) Client.ois.readObject();
            flightObservableList = FXCollections.observableList(list);
            flightObservableList.add(new Flight("", "", "", "", "", "", "", ""));
            ArrayList<String> destins = (ArrayList<String>) Client.ois.readObject();
            ObservableList<String> destinsObservableList = FXCollections.observableArrayList();
            destinsObservableList = FXCollections.observableList(destins);
            ArrayList<String> classes = (ArrayList<String>) Client.ois.readObject();
            ObservableList<String> classesObservableList = FXCollections.observableArrayList();
            classesObservableList = FXCollections.observableList(classes);


            flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
            flightNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            destinationCodeColumn.setCellValueFactory(new PropertyValueFactory<>("destinationCode"));
            destinationCodeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), destinsObservableList));
            dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
            dayColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), dayValues));
            monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
            monthColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), monthValues));
            yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
            yearColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), yearValues));


            classOfFlightColumn.setCellValueFactory(new PropertyValueFactory<>("classOfFlight"));
            classOfFlightColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), classesObservableList));
            seatsLeftColumn.setCellValueFactory(new PropertyValueFactory<>("seatsLeft"));
            seatsLeftColumn.setCellFactory(TextFieldTableCell.forTableColumn());

            flightTimeColumn.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
            flightTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());


            tableFlights.setItems(flightObservableList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void editCommitDepartureDay(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setDay(cellEditEvent.getNewValue());
        if (flight.getDay().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();

    }

    @FXML
    public void editCommitDestinationCode(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setDestinationCode(cellEditEvent.getNewValue());
        if (flight.getDestinationCode().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();
    }

    @FXML
    public void editCommitFlightNumber(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setFlightNumber(cellEditEvent.getNewValue());
        if (flight.getFlightNumber().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();
    }

    @FXML
    public void editCommitDepartureMonth(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setMonth(cellEditEvent.getNewValue());
        if (flight.getMonth().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();
    }


    @FXML
    void back(MouseEvent event) {
        Stage stage = (Stage) tableFlights.getScene().getWindow();
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

    @FXML
    void deleteAction(MouseEvent event) {
        int row = tableFlights.getSelectionModel().getSelectedIndex();
        tableFlights.getItems().remove(row);
        editFlightsFlag = true;
    }

    @FXML
    public void saveAction(MouseEvent event) {
        List<Flight> Flights = new ArrayList<>();
        Flight flight = tableFlights.getItems().get(tableFlights.getItems().size() - 1);
        if (!emptyFlag) {
            if (flight.getFlightNumber().equals("") && flight.getSeatsLeft().equals("") && flight.getClassOfFlight().equals("") && flight.getYear().equals("") && flight.getMonth().equals("") && flight.getDay().equals("") && flight.getDestinationCode().equals("") && flight.getFlightTime().equals(""))
                tableFlights.getItems().remove(tableFlights.getItems().size() - 1);
            else if (flight.getFlightNumber().equals("") || flight.getSeatsLeft().equals("") || flight.getClassOfFlight().equals("") || flight.getYear().equals("") || flight.getMonth().equals("") && flight.getDay().equals("") || flight.getDestinationCode().equals("") || flight.getFlightTime().equals("")) {
                label.setText("Не заполнены поля!");
                emptyFlag = false;
                editFlightsFlag = false;
                return;
            }
        }
        if (!emptyFlag) {
            label.setText("");
            if (editFlightsFlag) {
                Flights.addAll(tableFlights.getItems());
                List<String> idFlights = new ArrayList<>();
                for (Flight d : Flights)
                    idFlights.add(d.getFlightNumber());
                Set<String> setIdFlights = new HashSet<>();
                setIdFlights.addAll(idFlights);
                if (setIdFlights.size() < idFlights.size()) {
                    label.setText("id должны быть уникальны ");
                    return;
                }
                label.setText("");
                try {
                    Client.oos.writeObject(10);
                    Client.oos.flush();
                    Client.oos.writeObject(Flights);
                    Client.oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tableFlights.getItems().add((new Flight("", "", "", "", "", "", "", "")));
        } else {
            label.setText("Не заполнены поля!");
            emptyFlag = false;
            editFlightsFlag = false;
            return;
        }
        emptyFlag = false;
        initialize();
    }

    @FXML
    public void editCommitDepartureYear(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setYear(cellEditEvent.getNewValue());
        if (flight.getYear().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();

    }

    @FXML
    public void editCommitClassOfFlight(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setClassOfFlight(cellEditEvent.getNewValue());
        if (flight.getClassOfFlight().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();
    }

    @FXML
    public void editCommitSeatsLeft(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        if (!cellEditEvent.getNewValue().matches("[-+]?\\d+")) {
            label.setText("Некорректный ввод числа мест");
            return;
        }
        else if(Integer.parseInt(cellEditEvent.getNewValue())<0)
        { label.setText("Некорректный ввод числа мест");
            return;}
        flight.setSeatsLeft(cellEditEvent.getNewValue());
        if (flight.getSeatsLeft().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();
    }

    @FXML
    public void editCommitFlightTime(TableColumn.CellEditEvent<Flight, String> cellEditEvent) {
        Flight flight = tableFlights.getSelectionModel().getSelectedItem();
        flight.setFlightTime(cellEditEvent.getNewValue());
        if (flight.getFlightTime().equals(""))
            emptyFlag = true;
        editFlightsFlag = true;
        tableFlights.refresh();
    }
}
