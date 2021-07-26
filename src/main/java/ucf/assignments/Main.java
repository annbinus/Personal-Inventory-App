/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ann Binus
 */
package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));//load fxml file

        } catch (IOException e) {
            e.printStackTrace();
        }
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Personal Inventory");
            primaryStage.setResizable(false);
            primaryStage.show();

    }

    @Override
    public void stop() {
        //save all the items from 'ListItems' to file
        try {

            FileWriter fwrite = new FileWriter("files/Item.txt");

            //loop through each item and write on file
            for (Item item : MainWindowController.getList()) {
                fwrite.write(item.toString() + "\n");
            }
            System.out.println("Item added");

            fwrite.close();
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }

    }


}
