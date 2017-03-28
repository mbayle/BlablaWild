package fr.wcs.blablawild;

import java.util.Date;

/**
 * Created by wilder on 22/03/17.
 */

public class ItineraryModel {
    private int userID = 0;
    private String driverLastName = "Doctor";
    private String driverFirstName = "Who";
    private String departureDate;
    private int price;
    private String departure;
    private String destination;

    private ItineraryModel(){}

    public ItineraryModel(String departureDate, int price, String departure, String destination){
        this.departureDate = departureDate;
        this.price = price;
        this.departure = departure;
        this.destination = destination;
    }

    public int getUserID() {
        return userID;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public int getPrice() {
        return price;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

}
