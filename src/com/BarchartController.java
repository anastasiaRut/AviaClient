package com;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.*;

public class BarchartController {
    @FXML
    private BarChart chart;

    @FXML
    void initialize() {

        try {
            Client.oos.writeObject(17);
            Client.oos.flush();
            List<String> classes = (ArrayList<String>) Client.ois.readObject();
            Set<String> hashSet = new HashSet<>();
            hashSet.addAll(classes);
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (String str : hashSet) {
                int i = 0;
                for (String c : classes) {
                    if (str.equals(c)) {
                        i++;
                    }
                }
                map.put(str, i);
            }

            ObservableList<BarChart.Series> barData = FXCollections.observableArrayList();
            XYChart.Series data = new XYChart.Series();
            for(Map.Entry<String, Integer> entry: map.entrySet())
            {
                data.getData().add(new XYChart.Data(entry.getKey(),entry.getValue()));
            }
            chart.getData().add(data);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

