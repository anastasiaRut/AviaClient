package com;

import java.io.Serializable;

public class Airport implements Serializable {
    private String idAirport;
    private String name;
    private String city;

    public Airport(String idAirport, String name, String city) {
        this.idAirport = idAirport;
        this.name = name;
        this.city = city;
    }

    public String getIdAirport() {
        return idAirport;
    }

    public void setIdAirport(String idAirport) {
        this.idAirport = idAirport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
