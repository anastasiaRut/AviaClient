package com;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String flightNumber;
    private String airport;
    private String date;
    private String classOfFlight;
    private String seatsLeft;
    private String flightTime;

    public Ticket(String flightNumber, String airport, String date, String classOfFlight, String seatsLeft, String flightTime) {
        this.flightNumber = flightNumber;
        this.airport = airport;
        this.date = date;
        this.classOfFlight = classOfFlight;
        this.seatsLeft = seatsLeft;
        this.flightTime = flightTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getClassOfFlight() {
        return classOfFlight;
    }

    public void setClassOfFlight(String classOfFlight) {
        this.classOfFlight = classOfFlight;
    }

    public String getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(String seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }
}
