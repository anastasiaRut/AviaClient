package com;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.util.List;

public class UserMenuController {
    public static Stage stage;

    public static void show() {
        stage.show();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem diagram;

    @FXML
    private TableView<Ticket> tableTickets;

    @FXML
    private TableColumn<Ticket, String> TicketNumberColumn;

    @FXML
    private TableColumn<Ticket, String> airportColumn;

    @FXML
    private TableColumn<Ticket, String> dateColumn;

    @FXML
    private TableColumn<Ticket, String> classColumn;

    @FXML
    private TableColumn<Ticket, String> seatsLeftColumn;

    @FXML
    private TableColumn<Ticket, String> TicketTimeColumn;

    @FXML
    private ComboBox<String> cityBox;

    @FXML
    private ComboBox<String> dayBox;

    @FXML
    private ComboBox<String> monthBox;

    @FXML
    private ComboBox<String> yearBox;

    @FXML
    private ComboBox<String> classBox;

    @FXML
    private Button searchButton;

    @FXML
    private Label label;

    @FXML
    private Button sellButton;

    @FXML
    void showDiagram(ActionEvent event) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("diagram.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void initialize() {
        try {
            Client.oos.writeObject(13);
            Client.oos.flush();
            ObservableList<String> dayValues = FXCollections.observableArrayList();
            for (int i = 1; i <= 31; i++)
                dayValues.add(Integer.toString(i));
            dayBox.setItems(dayValues);

            ObservableList<String> yearValues = FXCollections.observableArrayList("2018", "2019");
            yearBox.setItems(yearValues);
            ObservableList<String> monthValues = FXCollections.observableArrayList();
            for (int i = 1; i <= 12; i++)
                monthValues.add(Integer.toString(i));
            monthBox.setItems(monthValues);
//        ObservableList<Ticket> ticketObservableList = FXCollections.observableArrayList();
//        ArrayList<Ticket> list = null;
//
//            list = (ArrayList<Ticket>) Client.ois.readObject();
//
//        ticketObservableList = FXCollections.observableList(list);

            List<String> cities = (ArrayList<String>) Client.ois.readObject();
            Set<String> citiesSet = new HashSet<>();
            citiesSet.addAll(cities);
            cities.clear();
            cities.addAll(citiesSet);
            ObservableList<String> citiesObservableList = FXCollections.observableArrayList();
            citiesObservableList = FXCollections.observableList(cities);
            cityBox.setItems(citiesObservableList);

            List<String> classes = (ArrayList<String>) Client.ois.readObject();
            ObservableList<String> classesObservableList = FXCollections.observableArrayList();
            classesObservableList = FXCollections.observableList(classes);
            classBox.setItems(classesObservableList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchEvent(MouseEvent mouseEvent) {
        if (cityBox.getValue() == null || dayBox.getValue() == null || monthBox.getValue() == null || yearBox.getValue() == null || classBox.getValue() == null) {
            label.setText("Выбраны не все параметры поиска");
        } else {
            label.setText("");
            try {
                Client.oos.writeObject(14);
                Client.oos.flush();
                Client.oos.writeObject(cityBox.getValue());
                Client.oos.flush();
                Client.oos.writeObject(dayBox.getValue());
                Client.oos.flush();
                Client.oos.writeObject(monthBox.getValue());
                Client.oos.flush();
                Client.oos.writeObject(yearBox.getValue());
                Client.oos.flush();
                Client.oos.writeObject(classBox.getValue());
                Client.oos.flush();

                ObservableList<Ticket> ticketObservableList = FXCollections.observableArrayList();
                List<Ticket> list = (ArrayList<Ticket>) Client.ois.readObject();
                if (list.isEmpty()) {
                    label.setText("Ничего не найдено");
                    tableTickets.setItems(null);
                    return;
                }
                cityBox.setValue(null);
                dayBox.setValue(null);
                monthBox.setValue(null);
                yearBox.setValue(null);
                classBox.setValue(null);
                ticketObservableList = FXCollections.observableList(list);
                TicketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
                airportColumn.setCellValueFactory(new PropertyValueFactory<>("airport"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
                classColumn.setCellValueFactory(new PropertyValueFactory<>("classOfFlight"));
                seatsLeftColumn.setCellValueFactory(new PropertyValueFactory<>("seatsLeft"));
                TicketTimeColumn.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
                tableTickets.setItems(ticketObservableList);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void sellAction(ActionEvent actionEvent) {
        try {
            if (tableTickets.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            if (tableTickets.getSelectionModel().getSelectedItem().getSeatsLeft().equals("0")) {
                label.setText("Места на рейс распроданы");
                return;
            }
            Client.oos.writeObject(15);
            Client.oos.flush();
            String ticketNumber = tableTickets.getSelectionModel().getSelectedItem().getFlightNumber();
            String seats = tableTickets.getSelectionModel().getSelectedItem().getSeatsLeft();
            Client.oos.writeObject(ticketNumber);
            Client.oos.flush();
            Client.oos.writeObject(seats);
            Client.oos.flush();
            int row = tableTickets.getSelectionModel().getFocusedIndex();
            ObservableList<Ticket> items = tableTickets.getItems();
            Ticket ticket = items.get(row);
            File f = new File("ticket.txt");
            PrintWriter bw = new PrintWriter(new FileOutputStream(f));
            bw.println("=====ЧЕК=====\n");
            bw.println("Продан билет\n");
            bw.println("Рейс: " + ticket.getFlightNumber() + "\n");
            bw.println("Место прибытия: " + ticket.getAirport() + "\n");
            bw.println("Дата отлёта: " + ticket.getDate() + "\n");
            bw.println("Класс обслуживания: " + ticket.getClassOfFlight() + "\n");
            bw.println("Часов полёта: " + ticket.getFlightTime() + "\n");
            bw.close();
            label.setText("Билет продан");
            Desktop desktop = Desktop.getDesktop();
            desktop.open(f);

            items.get(row).setSeatsLeft(Integer.toString(Integer.parseInt(seats) - 1));
            tableTickets.setItems(items);
            seatsLeftColumn.setVisible(false);
            seatsLeftColumn.setVisible(true);
            tableTickets.refresh();
            tableTickets.getProperties().put(TableViewSkinBase.RECREATE, Boolean.TRUE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showBarChart(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("barchart.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
    }
}
