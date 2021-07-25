/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Comparator;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ListItemsTest {

    ListItems listItems = new ListItems();
    //opens file containing the item data to get items to store to 'ListItems' class
    public void openFile() {

        try {
            File file = new File("files/test.txt");
            Scanner scan = new Scanner(file);

            scan.nextLine();
            //read line to get the name, serial number, and value
            while (scan.hasNext()) {
                //read text
                String text = scan.nextLine();

                //use split to separate text whenever there is "\t"
                //create array to store item contents from the text file
                String[] item = text.split("\t");

                //create a new Item object containing the separated item contents
                //add item to listItems
                listItems.addItem(new Item(item[0], item[1], item[2]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addItem() {
        openFile();//initialize listItem.getItems

        //create new item
        Item item = new Item ("67.00", "1234567890", "Person");

        //assert equals for the item after adding to list
        listItems.addItem(item);

        assertEquals(item, listItems.getItems().get(listItems.count-1));
    }

    @Test
    void removeItem() {
        openFile();//initialize listItem.getItems

        //create new item
        Item item = new Item("77.00", "0987654321", "Name");

        //add item to list
        listItems.addItem(item);

        //assert Equals for count before and after removing an item
        int count = listItems.count;

        listItems.removeItem(item);//remove item

        int newCount = listItems.count;

        assertEquals(newCount, count-1);
    }

    @Test
    void editName() {
        openFile();//initialize listItem.getItems
        //extract item object at index 0
        Item item = listItems.getItems().get(0);
        //edit name in item
        listItems.editName(item, "newName");

        assertEquals("newName", listItems.getItems().get(0).getName());
    }

    @Test
    void editSerialNumber() {
        openFile();//initialize listItem.getItems
        //extract item object at index 0
        Item item = listItems.getItems().get(0);
        //edit name in item
        listItems.editSerialNumber(item, "1234567890");

        assertEquals("1234567890", listItems.getItems().get(0).getSerialNumber());

    }

    @Test
    void editValue() {
        openFile();//initialize listItem.getItems
        //extract item object at index 0
        Item item = listItems.getItems().get(0);
        //edit name in item
        listItems.editValue(item, "33.00");

        assertEquals("33.00", listItems.getItems().get(0).getValue());
    }

    @Test
    void sortNameList() {
        openFile();//initialize listItem.getItems

        //create new observable list to store list
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());
        //use sort() method to sort the names in the list
        ob.sort(Comparator.comparing(Item::getName));

        //add the observable list to the listItem
        listItems.sortNameList(ob);

        // assert equals to check if the names are sorted
        assertEquals("89o", listItems.getItems().get(0).getName());
    }

    @Test
    void sortValueList() {
        openFile();//initialize listItem.getItems

        //create new observable list to store list
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());
        //use sort() method to sort the values in the list
        ob.sort(Comparator.comparing(Item::getValue));

        //add the observable list to the listItem
        listItems.sortValueList(ob);

        // assert equals to check if the values are sorted
        assertEquals("$54.00", listItems.getItems().get(0).getValue());
    }

    @Test
    void sortSRNumberList() {
        openFile();//initialize listItem.getItems

        //create new observable list to store list of items
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());

        //use sort() method to sort the serial numbers in the list
        ob.sort(Comparator.comparing(Item::getSerialNumber));

        //add the observable list to the listItem
        listItems.sortSRNumberList(ob);

        // assert equals to check if the serial numbers are sorted
        assertEquals("5645Y57455", listItems.getItems().get(0).getSerialNumber());
    }

    @Test
    void checkSRNumber() {
        openFile();

        boolean value = listItems.checkSRNumber(listItems.getItems().get(0).getSerialNumber());
        assertEquals(true, value);
    }

    private MainWindowController controller = new MainWindowController();

    @Test
    void searchFilterTest() {
        openFile();//initialize listItem.getItems()

        //create an observable list to save all items
        ObservableList<Item> observableList = FXCollections.observableArrayList();

        for (Item item : listItems.getItems()) {
            observableList.add(item);
        }

        String newValue = "67FUY";//create a string variable that matches one of the names in the item list

        //Wrap the observable list in a FilteredList
        FilteredList<Item> filteredData = new FilteredList<>(observableList, b -> true);

        //set the filter predicate to either true or false
        filteredData.setPredicate(item -> {
            //Compare the name in every item in the filter list
            return controller.findNewValue(newValue, item);
        });

        //Wrap the FilteredList in a SortedList
        SortedList<Item> sortedData = new SortedList<>(filteredData);


        //assert equals for item in the listItem arraylist and sortedData list
        assertEquals(listItems.getItems().get(1), sortedData.get(0));
    }
}