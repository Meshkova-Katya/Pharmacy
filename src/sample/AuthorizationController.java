package sample;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthorizationController {


    @FXML
    private TextField txLogin;


    @FXML
    private TextField txPassword;

    @FXML
    private Button authorization;

    @FXML
    private Button registration;

    @FXML
    void initialize() {
        authorization.setOnAction(event -> {
            String login = txLogin.getText().trim();
            String password = txPassword.getText().trim();
            if (!login.equals("") && !password.equals("")) {
                loginUser(login, password);
            } else {
                String str = "Поле с логином или паролем пустое";
                errorInLoginOrPass(str);
            }
        });
        registration.setOnAction(event -> StageHolder.getRegistrationStage().showAndWait());
    }

    private void loginUser(String login, String password) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = dbHandler.authorization(login, password);

        if (user == null) {
            String str = "Такого пользователя не существует!";
            errorInLoginOrPass(str);
        } else {
            dialogInfo();

            authorization.getScene().getWindow().hide(); // закрытие текущего окна

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Medicine.fxml"));

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

    private void errorInLoginOrPass(String string) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(string);
        alert.showAndWait();
    }
    private void dialogInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информационный диалог");
        alert.setHeaderText("Вы успешно вошли в систему!");
        alert.showAndWait();
    }
}
