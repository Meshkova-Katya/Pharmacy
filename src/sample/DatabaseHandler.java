package sample;

import javafx.scene.control.Alert;
import org.junit.experimental.results.PrintableResult;

import java.sql.*;

import static sample.ConstUser.*;


public class DatabaseHandler extends Configs {
    static Connection dbConnection;
    public static Medicine MEDICINE;

    static {
        try {
            dbConnection = getDbConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost/pharmacy?serverTimezone=Europe/Moscow&useSSL=false";
        String userName = dbUser;
        String password = dbPass;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, userName, password);
        return dbConnection;
    }

    public void registration(User user) throws SQLException {

        String insert = "INSERT INTO " + USER_TABLE + " ( " + USER_LOGIN + ", " + USER_PASSWORD + ") " + "VALUES (?, ?)";


        try {

            try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {


                prSt.setString(1, user.getLogin());
                prSt.setString(2, user.getPassword());

                // Добавляет в бд
                prSt.executeUpdate();


                dialogInfo();

            }
        } catch (SQLException | ClassNotFoundException e) {

            error();
        }
    }

    public User authorization(String login, String password) {
        User user = null;
        String select = "SELECT * FROM " + USER_TABLE + " WHERE " +
                USER_LOGIN + "=? AND " + USER_PASSWORD + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {

            prSt.setString(1, login);
            prSt.setString(2, password);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                user = new User();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void add(Medicine medicine) throws SQLException {

        String insert = "INSERT INTO MEDICINE (NAME, QUANTITY) VALUES (?, ?)";

        MEDICINE = medicine;
        try {

            try (PreparedStatement prSt = dbConnection.prepareStatement(insert)) {


                prSt.setString(1, medicine.getName());
                prSt.setInt(2, medicine.getQuantity());
                System.out.println(prSt);
                System.out.println(insert);
                // Добавляет в бд

                prSt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void del(String name) throws SQLException {

        String insert = "DELETE FROM medicine WHERE name = ? ";

        try {

            try (PreparedStatement prSt = dbConnection.prepareStatement(insert)) {

                prSt.setString(1, name);

                System.out.println(prSt);
                // Добавляет в бд
                prSt.executeUpdate();


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izm(Medicine medicine) throws SQLException {
        int count = medicine.getQuantity();
        String insert = "UPDATE medicine SET quantity = " + count + " WHERE name = ? ";
        try {

            try (PreparedStatement prSt = dbConnection.prepareStatement(insert)) {

                prSt.setString(1, medicine.getName());

                System.out.println(prSt);
                // Добавляет в бд
                prSt.executeUpdate();


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Medicine getMedByName(String name) {
        PreparedStatement stmt = null;
        Medicine medicine = new Medicine();
        try {
            stmt = dbConnection.prepareStatement("select * from medicine where name = ? ");
            stmt.setString(1, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            medicine.setId(rs.getInt("id"));
            medicine.setName(rs.getString("name"));
            medicine.setQuantity(rs.getInt("quantity"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicine;
    }



    private void dialogInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информационный диалог");
        alert.setHeaderText("Новый пользователь зарегистрирован!");
        alert.showAndWait();
    }

    private void error() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Пользователь с таким логином уже создан!");
        alert.showAndWait();
    }

}
