package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class DatabaseHandler extends Configs  {
    Connection dbConnection;
    public static Medicine MEDICINE;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost/pharmacy?serverTimezone=Europe/Moscow&useSSL=false";
        String userName = dbUser;
        String password = dbPass;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(url, userName, password);
        return dbConnection;
    }

    public void add(Medicine medicine) throws SQLException {

        String insert = "INSERT INTO MEDICINE (ADD_MEDICINE, QUANTITY) VALUES (?, ?)";

        MEDICINE  = medicine;
        try {

            try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {


                prSt.setString(1, medicine.getAdd_medicine());
                prSt.setInt(2, medicine.setQuantity(MedicineController.temp));
                System.out.println(prSt);
                System.out.println(insert);
                // Добавляет в бд
                prSt.executeUpdate();

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void del(Medicine medicine) throws SQLException {

        String insert = "DELETE FROM" + MEDICINE +  "WHERE name = ?";

        try {

            try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {

                prSt.setInt(1, medicine.getQuantity());

                System.out.println(prSt);
                // Добавляет в бд
                prSt.executeUpdate();


            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
