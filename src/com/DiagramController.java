package com;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.*;

public class DiagramController {
    @FXML
    private PieChart chart;

    @FXML
    void initialize() {
        try {
            Client.oos.writeObject(16);
            Client.oos.flush();
            List<String> cities = (ArrayList<String>) Client.ois.readObject();
            Set<String> hashSet = new HashSet<>();
            hashSet.addAll(cities);
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (String str : hashSet) {
                int i = 0;
                for (String city : cities) {
                    if (str.equals(city)) {
                        i++;
                    }
                }
                map.put(str, i);
            }

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            for(Map.Entry<String, Integer> entry: map.entrySet())
            pieData.add(new PieChart.Data(entry.getKey(),entry.getValue()));

            pieData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," ",data.pieValueProperty())));
            chart.setData(pieData);
            chart.setLabelsVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
