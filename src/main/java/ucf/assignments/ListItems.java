/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ListItems {

    //create an arraylist to store items
    private ArrayList<Item> itemList;

    //use count to keep track of the storage
    int count;

    public ListItems() {
        //initialize arraylist to Item objects
        itemList = new ArrayList<>(100);
        // use count to track the number of items in the list
        count = 0;
    }

    public void addItem(Item item) {
        itemList.add(item);
        //increment count after adding item
        count++;
    }

    public void clearAllItems(){
        //reset the Item ArrayList
        itemList = new ArrayList<>();
        //reset counter to 0
        count = 0;
    }

    public void removeItem(Item item) {
        //decrement count after removing item
        itemList.remove(item);
        count--;
    }

    //return items
    public ArrayList<Item> getItems() {
        return itemList;
    }

    public void editName(Item item, String name) {
        int flag = 0; //use flag to track the index of the item in the list

        //find item from the list that matches with the item given
        for (int i = 0; i < count; i++) {

            if (item.equals(getItems().get(i))) {
                flag = i;
                break;
            }
        }

        //set name of the item of the found item with new value
        getItems().get(flag).setName(name);
    }

    public void editSerialNumber(Item item, String srNumber) {
        int flag = 0; //use flag to track the index of the item in the list

        //find item from the list that matches with the item given
        for (int i = 0; i < count; i++) {

            if (item.equals(getItems().get(i))) {
                flag = i;
                break; //break if the item is found
            }
        }

        //set serial number of the found item with new value
        getItems().get(flag).setSerialNumber(srNumber);
    }

    public void editValue(Item item, String value) {
        int flag = 0; //use flag to track the index of the item in the list

        //find item from the list that matches with the item given
        for (int i = 0; i < count; i++) {

            if (item.equals(getItems().get(i))) {
                flag = i;
                break; //break if the item is found
            }
        }

        //set serial number of the found item with new value
        getItems().get(flag).setValue(value);
    }

    public void sortNameList(ObservableList<Item> ob){
        clearAllItems();
        for(Item item: ob){
            itemList.add(item);
        }
    }

    public void sortValueList(ObservableList<Item> ob){
        clearAllItems();
        for(Item item: ob){
            itemList.add(item);
        }
    }
    public void sortSRNumberList(ObservableList<Item> ob){
        clearAllItems();
        for(Item item: ob){
            itemList.add(item);
        }
    }

    public boolean checkSRNumber(String srNumber) {

        boolean flag = false;
        for (Item item : itemList) {
            if (item.getSerialNumber().equals(srNumber.toUpperCase())) {
                flag = true;
            }
        }
        return flag;
    }
}
