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

public class FlightClassesController {
    private boolean editFlightClassesFlag = false;
    private boolean emptyFlag = false;
    public static Stage stage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableColumn<FlightClass, String> codeColumn;
    @FXML
    private TableColumn<FlightClass, String> nameColumn;
    @FXML
    private TableView<FlightClass> tableFlightClasses;
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
            Client.oos.writeObject(7);
            Client.oos.flush();
            ObservableList<FlightClass> FlightClassesInit = FXCollections.observableArrayList();
            ArrayList<FlightClass> list = (ArrayList<FlightClass>) Client.ois.readObject();
            FlightClassesInit = FXCollections.observableList(list);
            FlightClassesInit.add(new FlightClass("", ""));
            codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
            codeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            tableFlightClasses.setItems(FlightClassesInit);
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
        List<FlightClass> FlightClasss = new ArrayList<>();
        FlightClass FlightClass = tableFlightClasses.getItems().get(tableFlightClasses.getItems().size() - 1);
        if (!emptyFlag) {
            if (FlightClass.getCode().equals("") && FlightClass.getName().equals("") )
                tableFlightClasses.getItems().remove(tableFlightClasses.getItems().size() - 1);
            else if (FlightClass.getCode().equals("") || FlightClass.getName().equals("") ) {
                label.setText("Не заполнены поля!");
                emptyFlag = false;
                editFlightClassesFlag =false;
                return;
            }
        }
        if (!emptyFlag) {
            label.setText("");
            if (editFlightClassesFlag) {
                FlightClasss.addAll(tableFlightClasses.getItems());
                List<String> idFlightClasss = new ArrayList<>();
                for (FlightClass d : FlightClasss)
                    idFlightClasss.add(d.getCode());
                Set<String> setIdFlightClasss = new HashSet<>();
                setIdFlightClasss.addAll(idFlightClasss);
                if (setIdFlightClasss.size() < idFlightClasss.size()) {
                    label.setText("коды должны быть уникальны ");
                    return;
                }
                label.setText("");
                try {
                    Client.oos.writeObject(8);
                    Client.oos.flush();
                    Client.oos.writeObject(FlightClasss);
                    Client.oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tableFlightClasses.getItems().add((new FlightClass("", "")));
        } else {label.setText("Не заполнены поля!"); emptyFlag = false; editFlightClassesFlag =false; return;}
        emptyFlag = false;
        initialize();

    }

    @FXML
    void deleteAction(MouseEvent event) {
        int row = tableFlightClasses.getSelectionModel().getSelectedIndex();
        tableFlightClasses.getItems().remove(row);
        editFlightClassesFlag = true;
    }

    @FXML
    void editCommitFlightClassCode(TableColumn.CellEditEvent<FlightClass, String> cellEditEvent) {
        FlightClass flightClass = tableFlightClasses.getSelectionModel().getSelectedItem();
        String oldCode = flightClass.getCode();
        flightClass.setCode(cellEditEvent.getNewValue());
        if (flightClass.getCode().equals(""))
            emptyFlag = true;
        else {
            try {
                Client.oos.writeObject(12);
                Client.oos.flush();
                Client.oos.writeObject(oldCode);
                Client.oos.flush();
                Client.oos.writeObject(flightClass.getCode());
                Client.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        editFlightClassesFlag = true;
        tableFlightClasses.refresh();
    }

    @FXML
    void editCommitFlightClassName(TableColumn.CellEditEvent<FlightClass, String> cellEditEvent) {
        FlightClass FlightClass = tableFlightClasses.getSelectionModel().getSelectedItem();
        FlightClass.setName(cellEditEvent.getNewValue());
        if (FlightClass.getName().equals(""))
            emptyFlag = true;
        editFlightClassesFlag = true;
        tableFlightClasses.refresh();
    }


    @FXML
    void back(MouseEvent event) {
        Stage stage = (Stage) tableFlightClasses.getScene().getWindow();
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
