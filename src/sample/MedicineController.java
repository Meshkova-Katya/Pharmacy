package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class MedicineController {
    public static int temp = 1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tx1;

    @FXML
    private Button add;

    @FXML
    private TextField tx3;

    @FXML
    private TextField tx2;

    @FXML
    private Button quantity;

    @FXML
    private Button sell;

    @FXML
    private Label quantity2;

    private void addMedicine() {

        DatabaseHandler dbHandler = new DatabaseHandler();


        String add_medicine = tx1.getText().trim();


        if (!add_medicine.equals("")) {
            Medicine medicine = new Medicine(add_medicine, temp);
            try {
                dbHandler.add(medicine);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            String st = DatabaseHandler.MEDICINE.getAdd_medicine();
            if (add_medicine == st) {
                DatabaseHandler.MEDICINE.setQuantity(temp++);

            } else {
                String str = "Заполните все поля!";
                System.out.println(str);
            }


        }
    }

    private void delMedicine() {

        DatabaseHandler dbHandler = new DatabaseHandler();

        int temp = DatabaseHandler.MEDICINE.getQuantity();

        String market = tx2.getText().trim();

        if (!market.equals("")) {

            Medicine medicine = new Medicine(market, temp--);


            try {

                dbHandler.del(medicine);

                medicine.setQuantity(DatabaseHandler.MEDICINE.getQuantity());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            String str = "Заполните все поля!";
            System.out.println(str);
        }


    }

    @FXML
    void initialize() {
        add.setOnAction(event -> addMedicine());
        sell.setOnAction(event -> delMedicine());
    }

}