package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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

    }

    private void findQuantity() {
        String name = tx1.getText().trim();
        if (!name.equals("")) {
            DatabaseHandler dbHandler = new DatabaseHandler();
            Medicine med = dbHandler.getMedByName(name);
            quantity2.setVisible(true);
            if (med != null) {
                med.setQuantity(0);

            }
            quantity2.setText("Кол-во лекарств на складе:" + med.getQuantity());

        } else {
           errorHandling();
        }
    }
    private void errorHandling(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Заполните все поля!");
        alert.showAndWait();

    }
}