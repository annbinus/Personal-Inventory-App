/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class OpenSaveFile {

    //create ListItems variable;
    private static ListItems listItems;


    //opens file containing the previous item data to store to the new list
    public void openFile() {

        try {
            listItems = new ListItems();//initialize new List

            File file = new File("files/Item.txt");//open Item.txt
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

    public void saveTSVFile(File file) {

        //initialize listItems.getItems by opening the file with the previous data
        openFile();

        try {

            //all the data from the present ListItems is written on a text file
            FileWriter write = new FileWriter(file.toString());

            write.write("Value\tSerialNumber\tName\n");// table column heading

            //write the ListItems items on the file
            for (Item item : listItems.getItems()) {
                write.write(item.toString() + "\n");
            }

            write.close();

            //show user that listItems is saved
            JOptionPane.showMessageDialog(null, "ListItems saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveHTMLFile(File file) {

        //initialize listItems.getItems by opening the file with the previous data
        openFile();

        //table column heading
        String html = "<table border = '1'>" +
                "<tr>" +
                "<td>Value</td>" +
                "<td>Serial Number</td>" +
                "<td>Name</td>" +
                "</tr>";

        try {
            //write item data on file provided by user
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(html);

            //loop through listItem.getItems() to print each Item data
            for (Item item : listItems.getItems()) {
                bw.write("<tr><td>" +
                        item.getValue() +
                        "</td><td>" +
                        item.getSerialNumber() +
                        "</td><td>" +
                        item.getName() +
                        "</td></tr>");
            }

            bw.close();

            //show user that listItems is saved
            JOptionPane.showMessageDialog(null, "ListItems saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveJSONFile(File file) {

        //initialize listItems.getItems by opening the file with the previous data
        openFile();

        //Creating a JSONObject object to store each Item
        JSONObject itemObj = null;

        //create a JSONArray to store array of Items
        JSONArray itemList = new JSONArray();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            //loop through listItem.getItems() to get store each Item in new JSONObject
            for (Item item : listItems.getItems()) {

                itemObj = new JSONObject();

                //Inserting key-value pairs into the json object
                itemObj.put("Name", item.getName());
                itemObj.put("Serial Number", item.getSerialNumber());
                itemObj.put("Value", item.getValue());

                //put each object in the array
                itemList.put(itemObj);

            }

            bw.write(itemList.toString(4));//print array on JSON file with proper indentation
            //show user that listItems is saved
            JOptionPane.showMessageDialog(null, "ListItems saved");

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Item> openTSVFile(File file){
        //initialize listItems.getItems by opening the file with the previous data
        openFile();

        //clear all item for a new table
        listItems.clearAllItems();

        Scanner scan = null;
        try {
            // create a new scanner for the file
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scan.nextLine();//skip the first line

        //create observable list to store each Item
        ObservableList<Item> ob = FXCollections.observableArrayList();

        //loop through until the hasNext() = null
        while (scan.hasNext()) {

            //copy text to line
            String line = scan.nextLine();

            //use split to separate text whenever there is "\t"
            //create array to store item contents from the text file
            String[] item = line.split("\t");

            //create a new Item containing data read from file
            Item newItem = new Item(item[0], item[1], item[2]);

            // add new item to observable list
            ob.add(newItem);

            //add new item to ListItems arraylist
            listItems.getItems().add(newItem);
        }
        return ob;
    }

    public ObservableList<Item> openHTMLFile(File file){
        //initialize listItems.getItems by opening the file with the previous data
        openFile();

        //create new table
        listItems.clearAllItems();

        //create observable list to store each Item
        ObservableList<Item> ob = FXCollections.observableArrayList();

        try {
            //create document to parse file using Jsoup
            Document doc = Jsoup.parse(file, "UTF-8");

            //create Element to extract the table
            Element table = doc.select("table").get(0);

            //create Element to extract the rows
            Elements rows = table.select("tr");

            //loop through each row to extract and store each column to the observable list
            for (int i = 1; i < rows.size(); i++) {

                //get row
                Element row = rows.get(i);
                //extract columns in row
                Elements cols = row.select("td");

                //add new item with data to observable list
                ob.add(new Item(cols.get(0).text(), cols.get(1).text(), cols.get(2).text()));

                //add new item to ListItems arraylist
                listItems.getItems().add(new Item(cols.get(0).text(), cols.get(1).text(), cols.get(2).text()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ob;
    }

    public ObservableList<Item> openJSONFile(File file){

        //initialize listItems.getItems by opening the file with the previous data
        openFile();

        //clear table to show new file
        listItems.clearAllItems();

        //create a JSON parser object
        JSONParser parser = new JSONParser();

        //create a observable list to store each Item
        ObservableList<Item> ob = FXCollections.observableArrayList();

        try {
            //parse the array on the JSON file
            org.json.simple.JSONArray arr = (org.json.simple.JSONArray) parser.parse(new FileReader(file));

            //loop through the array to get each item object
            for (Object obj : arr) {
                org.json.simple.JSONObject item = (org.json.simple.JSONObject) obj;

                //get each Item data from the Item object
                //create new Item containing data and add it to observable list
                ob.add(new Item((String) item.get("Value"), (String) item.get("Serial Number"), (String) item.get("Name")));

                //add new item to ListItems arraylist
                listItems.getItems().add(new Item((String) item.get("Value"), (String) item.get("Serial Number"), (String) item.get("Name")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ob;
    }
}

