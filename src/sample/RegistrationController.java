package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txLogin;

    @FXML
    private Label quantity2;

    @FXML
    private TextField txPassword;

    @FXML
    private Button registration;

    @FXML
    void initialize() {
      registration.setOnAction(event -> {

            signUpNewUser();
          StageHolder.getRegistrationStage().close();
        });
    }
    private void signUpNewUser() {

        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = txLogin.getText().trim();
        String password = txPassword.getText().trim();

        if (!login.equals("") && !password.equals("")) {


            User user = new User(login, password);


            try {

                dbHandler.registration(user);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            String str = "Заполните все поля!";

        }

    }

}
