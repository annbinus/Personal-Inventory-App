/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */

package ucf.assignments;

public class Item {

    private String name;
    private String serialNumber;
    private String value;

    public Item(String value, String serialNumber, String name) {
        //initialize the variables
        this.name = name;
        this.serialNumber = serialNumber.toUpperCase();
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber.toUpperCase();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //return TSV form of the variables in Item to write on file
    public String toString() {
        return this.value + "\t" + this.serialNumber + "\t" + this.name;
    }


}
