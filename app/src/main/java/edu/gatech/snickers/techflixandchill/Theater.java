package edu.gatech.snickers.techflixandchill;

/**
 * Theater object that will be displayed by the application when users request to find movie
 * theaters nearby.
 *
 * @version 1.1
 * @author Snickers
 */
public class Theater {
    private String name;
    private String address;
    private String locality;
    private String telNumber;
    private String distance;

    public Theater() {
        //empty constructor
    }


    public Theater(String name, String address, String locality, String telNumber, String distance) {
        this.name = name;
        this.address = address;
        this.locality = locality;
        this.telNumber = telNumber;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
    
    public String getDistance() {
        return distance;
    }
    
    public void setDistance(String distance) {
        this.distance = distance;
    }

}
