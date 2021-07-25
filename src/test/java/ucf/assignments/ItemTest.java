/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item item = new Item( "66.00", "1234567890", "Person");

    @Test
    void getName() {
        //returns true for assetEquals if the name matches
        assertEquals("Person", item.getName());
    }

    @Test
    void setName() {
        //set new description
        item.setName("XBox");
        assertEquals("XBox", item.getName());
    }

    @Test
    void getSerialNumber() {
        //returns true for assertEquals if the serial number matches
        assertEquals("1234567890", item.getSerialNumber());
    }

    @Test
    void setSerialNumber() {
        item.setSerialNumber("0987654321");
        assertEquals("0987654321", item.getSerialNumber());
    }

    @Test
    void getValue() {
        //returns true for assertEquals if the value matches
        assertEquals("66.00", item.getValue());
    }

    @Test
    void setValue() {
        item.setSerialNumber("99");
        assertEquals("99", item.getSerialNumber());
    }
}