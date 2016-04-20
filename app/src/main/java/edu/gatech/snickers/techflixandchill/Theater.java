package edu.gatech.snickers.techflixandchill;

/**
 * Created by Scottie on 3/13/16.
 */
public class Theater {
    private String name;
    private String address;
    private String locality;
    private String telNumber;

    public Theater() {
        //empty constructor
    }


    public Theater(String name, String address, String locality, String telNumber) {
        this.name = name;
        this.address = address;
        this.locality = locality;
        this.telNumber = telNumber;
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

}
