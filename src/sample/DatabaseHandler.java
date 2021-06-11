package sample;

import java.sql.*;


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
            if (!rs.next()){
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

}
