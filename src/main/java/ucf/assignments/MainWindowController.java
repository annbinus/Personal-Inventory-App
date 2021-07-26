/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

public class MainWindowController implements Initializable {

    private static ListItems listItems;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> name;

    @FXML
    private TableColumn<Item, String> serialNumber;

    @FXML
    private TableColumn<Item, String> value;

    @FXML
    private TextField searchText;

    @FXML
    private Button addButton;

    @FXML
    public void editNameColumn(TableColumn.CellEditEvent<Item, String> cell) {
        //get changes made by user from the table
        Item itemSelected = tableView.getSelectionModel().getSelectedItem();
        //set the changes to item's name
        listItems.editName(itemSelected, cell.getNewValue());
    }

    @FXML
    public void editSerialNumberColumn(TableColumn.CellEditEvent<Item, String> cell) {
        //get changes made by user from the table
        Item itemSelected = tableView.getSelectionModel().getSelectedItem();

        if (cell.getNewValue().matches("[a-zA-Z0-9]*") && !(cell.getNewValue() == null || cell.getNewValue() == "" || cell.getNewValue().length() != 10)) {
            if (listItems.checkSRNumber(cell.getNewValue()) == false) {
                //set the changes to item's serial number
                listItems.editSerialNumber(itemSelected, cell.getNewValue());
            } else {
                JOptionPane.showMessageDialog(null, "Serial Number already taken.", "Error", JOptionPane.ERROR_MESSAGE);
                tableView.refresh();// refresh table after editing
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Serial Number! Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
            tableView.refresh();// refresh table after editing
            return;
        }

    }

    @FXML
    public void editValueColumn(TableColumn.CellEditEvent<Item, String> cell) {

        if (cell.getNewValue().matches("[0-9.]*")) {
            //convert String Value to double
            Double convertVal = Double.parseDouble(cell.getNewValue());

            //round the value to decimal places
            convertVal = Math.round(convertVal * 100.0) / 100.0;

            //use number formatter to format the value into currency format
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String newVal = formatter.format(convertVal);

            //get changes made by user from the table
            Item itemSelected = tableView.getSelectionModel().getSelectedItem();
            //set the changes to item's value
            listItems.editValue(itemSelected, newVal);
            tableView.refresh();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Value! Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
            tableView.refresh();
            return;
        }
    }

    //return items in listItems
    public static ArrayList<Item> getList() {
        return listItems.getItems();
    }



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

    public void setTableCellEditable() {

        //columns are set to be editable
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        value.setCellFactory(TextFieldTableCell.forTableColumn());
    }


    @FXML
    public void handleAddButton(ActionEvent event) {

        //load the Item.fxml to show the Add Task window
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Item.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setResizable(false);

        //close window when the Add button is pushed
        Stage prevStage = (Stage) addButton.getScene().getWindow();
        prevStage.close();
        stage.show();
    }

    @FXML
    public void handleFileOpen(Event event) {

        //create OpenSaveFile object to open files
        OpenSaveFile open = new OpenSaveFile();

        FileChooser fileChooser = new FileChooser();

        //title for the file chooser dialog window
        fileChooser.setTitle("Open Resource File");

        // the dialog window opens and the user-selected file is assigned to the File class
        File file = fileChooser.showOpenDialog(null);


        if (file == null) {

            //show the warning dialog window
            JOptionPane.showMessageDialog(null, "Choose a valid file");

        } else if (file.toString().endsWith(".txt")) {

            //clear items list
            listItems.clearAllItems();

            //set the tableView with the new data from the TSV file
            //openTSVFile returns the list of items on the file
            tableView.setItems(open.openTSVFile(file));

            //store items from the TSV file into the arraylist
            for (Item item : open.openTSVFile(file)) {
                listItems.addItem(item);
            }


        } else if (file.toString().endsWith(".html")) {

            //clear items list
            listItems.clearAllItems();

            //set the tableView with the new data from the HTML file
            //openHTMLFile returns the list of items on the file
            tableView.setItems(open.openHTMLFile(file));

            //store items from the HTML file into the arraylist
            for (Item item : open.openHTMLFile(file)) {
                listItems.addItem(item);
            }


        } else if (file.toString().endsWith(".json")) {

            //clear item list
            listItems.clearAllItems();

            //set the tableView with the new data from JSON file
            //openJSONFile returns the list of items on the file
            tableView.setItems(open.openJSONFile(file));

            //store items from the JSON file into the arraylist
            for (Item item : open.openJSONFile(file)) {
                listItems.addItem(item);
            }
        }
    }


    @FXML
    public void handleFileDownload(Event event) {

        FileChooser fileChooser = new FileChooser();

        //title for the directory chooser dialog window
        fileChooser.setTitle("Choose a folder to save file");

        //create extensions for files
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSV file", "*.txt"),
                new FileChooser.ExtensionFilter("HTML file", "*.html"),
                new FileChooser.ExtensionFilter("JSON file", "*.json"));

        // the dialog window opens and the user-selected directory is assigned to the File class
        File file = fileChooser.showSaveDialog(null);
        fileChooser.setInitialDirectory(file.getParentFile());

        //create object to access methods in OpenSaveFile
        OpenSaveFile save = new OpenSaveFile();

        if (file.toString().endsWith(".txt")) {
            //save file ending with .txt
            //this method creates new txt file with the data in the user chosen directory
            save.saveTSVFile(file);
        }

        if (file.toString().endsWith(".html")) {
            //save file ending with .html
            //this method creates new HTML file with the data in the user chosen directory
            save.saveHTMLFile(file);
        }

        if (file.toString().endsWith(".json")) {
            //save file ending with .json
            //this method creates new JSON file with the data in the user chosen directory
            save.saveJSONFile(file);
        }
    }

    public void sortName() {
        //create new observable list to store list
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());
        //use sort() method to sort the names in the list
        ob.sort(Comparator.comparing(Item::getName));
        //set list to the tableView
        tableView.setItems(ob);
        //add list to the listItems
        listItems.sortNameList(ob);

    }

    public void sortSerialNumber() {
        //create new observable list to store list
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());
        //use sort() method to sort the serial numbers in the list
        ob.sort(Comparator.comparing(Item::getSerialNumber));
        //set list to the tableView
        tableView.setItems(ob);
        //add list to the listItems
        listItems.sortSRNumberList(ob);

    }

    public void sortValue() {
        //create new observable list to store list
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());
        //use sort() method to sort the values in the list
        ob.sort(Comparator.comparing(Item::getValue));
        //set list to the tableView
        tableView.setItems(ob);
        //add list to the listItems
        listItems.sortValueList(ob);
    }


    public boolean findNewValue(String newValue, Item item){
        //If filter text is empty, display all items.
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();

        // Compare the name, value, and the serial number of every item with filter text.
        if (item.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
            return true;
        } else if (item.getSerialNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
            return true;
        } else if (item.getValue().indexOf(lowerCaseFilter) != -1)
            return true;
        else
            return false;
    }

    public void setSearchText(){
        //create new observable list to store list of items
        ObservableList<Item> observableList = FXCollections.observableArrayList();

        for (Item item : listItems.getItems()) {
            observableList.add(item);
        }

        //Wrap the observable list in a FilteredList
        FilteredList<Item> filteredData = new FilteredList<>(observableList, b -> true);

        //add listener to the searchText text field
        //set the filter predicate to either true or false
        searchText.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(item -> {

            //check if the value is valid
            return findNewValue(newValue, item);

        }));

        //Wrap the FilteredList in a SortedList
        SortedList<Item> sortedData = new SortedList<>(filteredData);

        //Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        //Add sorted (and filtered) data to the table
        tableView.setItems(sortedData);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //associate the Item data with the table columns
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        //initialize the listItems
        listItems = new ListItems();


        //open file to read items from the text file
        openFile();

        //items from the listItems is added to the tableView
        tableView.getItems().setAll(listItems.getItems());

        tableView.setEditable(true);

        //set the table editable so the user can change values
        setTableCellEditable();

        //set table with the searched value in the search bar
        setSearchText();

    }

    @FXML
    public void handleRemoveButton(ActionEvent actionEvent) {
        //get the selected item and remove from tableView
        Item item = tableView.getSelectionModel().getSelectedItem();

        // remove item from the List
        listItems.removeItem(item);

        //Wrap teh arraylist into an observable list
        ObservableList<Item> ob = FXCollections.observableArrayList(listItems.getItems());

        //set items to the tableview
        tableView.setItems(ob);
    }
}
