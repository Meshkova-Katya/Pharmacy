package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SalesStatisticsController {

    @FXML
    private Label statistics;

    @FXML
    void initialize() {
        String str = DatabaseHandler.findOutStatistics();
        statistics.setText(str);
    }
}
