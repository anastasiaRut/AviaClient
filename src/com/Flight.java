package com;

import java.io.Serializable;

public class Flight implements Serializable {
    private String flightNumber;
    private String destinationCode;
    private String day;
    private String month;
    private String year;
    private String classOfFlight;
    private String seatsLeft;
    private String flightTime;

    public Flight(String flightNumber, String destinationCode, String day, String month, String year, String classOfFlight, String seatsLeft, String flightTime) {
        this.flightNumber = flightNumber;
        this.destinationCode = destinationCode;
        this.day = day;
        this.month = month;
        this.year = year;
        this.classOfFlight = classOfFlight;
        this.seatsLeft = seatsLeft;
        this.flightTime = flightTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
