package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MedicineController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tx1;

    @FXML
    private Button add;

    @FXML
    private Button quantity;

    @FXML
    private Button sell;

    @FXML
    private Label quantity2;

    @FXML
    private Button statistics;


    private void addMedicine() {

        DatabaseHandler dbHandler = new DatabaseHandler();


        String name = tx1.getText().trim();

        if (!name.equals("")) {
            Medicine med = dbHandler.getMedByName(name);

            if (med != null) {

                try {

                    med.setQuantity(med.getQuantity() + 1);
                    dbHandler.izm(med);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {

                Medicine medicine = new Medicine();
                medicine.setName(name);
                medicine.setQuantity(1);
                try {
                    dbHandler.add(medicine);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else {
            errorHandling();
        }
    }


    private void delMedicine() {


        DatabaseHandler dbHandler = new DatabaseHandler();


        String market = tx1.getText().trim();

        if (!market.equals("")) {
            Medicine medicine = dbHandler.getMedByName(market);
            if (medicine == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Такого препарата нет в БД");
                alert.showAndWait();
            } else if (medicine.getQuantity() == 1) {
                try {
                    dbHandler.del(medicine.getName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                medicine.setQuantity(medicine.getQuantity() - 1);
                try {
                    dbHandler.izm(medicine);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else {
            errorHandling();
        }


    }

    @FXML
    void initialize() {
        add.setOnAction(event -> addMedicine());
        sell.setOnAction(event -> delMedicine());
        quantity.setOnAction(event -> findQuantity());
        statistics.setOnAction(event -> showWindow());

    }

    private void findQuantity() {
        String name = tx1.getText().trim();
        if (!name.equals("")) {
            DatabaseHandler dbHandler = new DatabaseHandler();

            Medicine med = dbHandler.getMedByName(name);

            quantity2.setVisible(true);
            if (med == null) {
                quantity2.setText("Кол-во лекарств на складе: " + 0);
            } else {
                quantity2.setText("Кол-во лекарств на складе: " + med.getQuantity());
            }

            System.out.println(name);


        } else {
            errorHandling();
        }
    }

    private void errorHandling() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Заполните все поля!");
        alert.showAndWait();

    }

    private void showWindow() {
        statistics.getScene().getWindow().hide(); // закрытие текущего окна

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SalesStatistics.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 600));
        stage.showAndWait(); // чтобы подождал

    }
}