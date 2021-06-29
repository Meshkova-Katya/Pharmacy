package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StageHolder {
    private static Stage authorizationStage;
    private static Stage registrationStage;

    public static void load() {
        try {
            authorizationStage = new Stage();
            Parent root = FXMLLoader.load(StageHolder.class.getResource("Authorization.fxml"));
            authorizationStage.setTitle("Аптека");
            authorizationStage.setScene(new Scene(root, 600, 600));

            registrationStage = new Stage();
            Parent root2 = FXMLLoader.load(StageHolder.class.getResource("Registration.fxml"));
            registrationStage.initOwner(authorizationStage);
            registrationStage.initModality(Modality.APPLICATION_MODAL);
            registrationStage.setScene(new Scene(root2, 600, 600));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Stage getAuthorizationStage() {
        return authorizationStage;
    }

    public static Stage getRegistrationStage() {
        return registrationStage;
    }
}