/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class OpenSaveFileTest {

    ListItems listItems = new ListItems();
    //opens file containing the item data to get items to store to 'ListItems' class
    public void openFile() {

        try {
            File file = new File("files/Item.txt");
            Scanner scan = new Scanner(file);

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

    OpenSaveFile io = new OpenSaveFile();

    @Test
    void saveTSVFileTest() {
        //create new file with .txt extension
        File file = new File("files/outputFiles/TSVFile.txt");
        //save the file
        io.saveTSVFile(file);

        //initialize the listItem.getItems()
        openFile();

        //openTSVFile() returns list of items from the saved .txt file
        ObservableList<Item> ob = io.openTSVFile(file);

        //assert equals for getName() at index 0 for list from the saved TSV file with listItems.getItems()
        assertEquals(listItems.getItems().get(0).getName(), ob.get(0).getName());

    }

    @Test
    void saveHTMLFileTest() {
        //create new file with .html extension
        File file = new File("files/outputFiles/HTMLFile.html");
        //save the file
        io.saveHTMLFile(file);

        //initialize the listItem.getItems()
        openFile();

        //openHTMLFile() returns list of items from the saved .html file
        ObservableList<Item> ob = io.openHTMLFile(file);

        //assert equals for getName() at index 0 for list from the saved HTML file with listItems.getItems()
        assertEquals(listItems.getItems().get(0).getName(), ob.get(0).getName());

    }

    @Test
    void saveJSONFileTest() {
        //create new file with .json extension
        File file = new File("files/outputFiles/JSONFile.txt");
        //save the file
        io.saveJSONFile(file);

        //initialize the listItem.getItems()
        openFile();

        //openJSONFile() returns list of items from the saved .json file
        ObservableList<Item> ob = io.openJSONFile(file);

        //assert equals for getName() at index 0 for list from the saved JSON file with listItems.getItems()
        assertEquals(listItems.getItems().get(0).getName(), ob.get(0).getName());

    }

    @Test
    void openTSVFileTest() {
        //open test.txt file from files folder in the root directory
        File file = new File("files/test.txt");

        //openTSVFile() returns list of items from the .txt file
        ObservableList<Item> ob = io.openTSVFile(file);

        //assert equals for getName() at index 0 for list from the TSV file with listItems.getItems()
        assertEquals("gug", ob.get(0).getName());
    }

    @Test
    void openHTMLFile() {
        //open HTMLTestFile.html file from files folder in the root directory
        File file = new File("files/HTMLTestFile.html");

        //openHTMLFile() returns list of items from the .html file
        ObservableList<Item> ob = io.openHTMLFile(file);

        //assert equals for getName() at index 0 for list from the HTML file with listItems.getItems()
        assertEquals("gug", ob.get(0).getName());
    }

    @Test
    void openJSONFile() {
        //create new file with .json extension
        File file = new File("files/JSONTestFile.json");

        //openJSONFile() returns list of items from the .json file
        ObservableList<Item> ob = io.openJSONFile(file);

        //assert equals for getName() at index 0 for list from the JSON file with listItems.getItems()
        assertEquals("gug", ob.get(0).getName());
    }
}