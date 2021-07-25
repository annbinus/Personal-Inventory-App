/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;


public class ItemController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField srNumber;

    @FXML
    private TextField value;

    @FXML
    private Button enterButton;

    @FXML
    private Label nameReq;

    @FXML
    private Label numReq1;

    @FXML
    private Label numReq2;

    @FXML
    private Label valueReq;

    @FXML
    public void handleEnterButton() {
        //initialize variables by getting text from the testField

        String Name = name.getText();
        String serialNumber = srNumber.getText();
        String Value = value.getText();


        MainWindowController controller = new MainWindowController();
        ListItems listItems = new ListItems();

        //check if these variables meet the requirements
        if (Name.length() >= 2 && Name.length() <= 256 && serialNumber.matches("[a-zA-Z0-9]*") && Value.matches("[0-9.]*") && !listItems.checkSRNumber(serialNumber)) {

                //convert String Value to double
                Double convertVal = Double.parseDouble(Value);

                //round the value to decimal places
                convertVal = Math.round(convertVal * 100.0) / 100.0;

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String newVal = formatter.format(convertVal);

                //create Item object
                Item newTask = new Item( newVal, serialNumber, Name);


                // add Item object to ListItems
                controller.getList().add(newTask);

                //after adding new item, write on the text file
                try {
                    //use the Item.txt file to write data
                    FileWriter fwrite = new FileWriter("files/Item.txt");

                    //get each Item from ListItems and write it on the text file
                    for (Item item : MainWindowController.getList()) {
                        fwrite.write(item.toString() + "\n");
                    }

                    //close file
                    fwrite.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // open the main window after adding task
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                //close window when the Add Task button is pushed
                Stage stagePrevious = (Stage) enterButton.getScene().getWindow();
                stagePrevious.close();
                stage.show();
            }

        else if(listItems.checkSRNumber(serialNumber)){
                JOptionPane.showMessageDialog(null, "Serial Number already taken.","Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Invalid name or serial number or value. \nPlease enter again!","Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //add listener to the name textField
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            //check if the textField is empty
            if (newValue == null || newValue.equals("")) {
                //if empty or doesn't meet the requirement, set the requirement label color to red
                nameReq.setTextFill(Color.web("#cd0808"));
            } else if (newValue.length() >= 2 && newValue.length() <= 256) {
                nameReq.setTextFill(Color.web("#0abc15"));
            } else {
                //if not empty and meets the requirement, set the requirement label to green
                nameReq.setTextFill(Color.web("#cd0808"));
            }
        });


        //add listener to the serial number textField
        srNumber.textProperty().addListener((observable, oldValue, newValue) -> {

            //check if textField is empty
            if (newValue == null || newValue.equals("") || newValue.length() != 10) {
                numReq1.setTextFill(Color.web("#cd0808"));//set requirement label color to red
                numReq2.setTextFill(Color.web("#cd0808"));
            } else {
                numReq2.setTextFill(Color.web("#0abc15"));//if length = 10, set label color to green
            }

            //check if the serial number has any characters other than letters and digits

            if (srNumber.getText().matches("[a-zA-Z0-9]*")) {
                numReq1.setTextFill(Color.web("#0abc15"));//if true, set label color to red
            } else {
                numReq1.setTextFill(Color.web("#cd0808"));// if false, set label color to green
            }
        });

        //add listener to the value textField
        value.textProperty().addListener((observable, oldValue, newValue) -> {

            //check if the value has any characters other than digits

            if(value.getText().matches("[0-9.]*")){
                valueReq.setTextFill(Color.web("#0abc15"));// if true, set label color to green
            }
            else{
                valueReq.setTextFill(Color.web("#cd0808"));// if false, set label color to red
            }
        });
    }

}
