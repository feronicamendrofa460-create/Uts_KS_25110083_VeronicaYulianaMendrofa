package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost:3306/db_inventori";
            String user = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(
                url, user, password
            );

            System.out.println("Koneksi Berhasil!");

            return conn;

        } catch (SQLException e) {

            System.out.println("Koneksi Gagal: " + e.getMessage());

            return null;
        }
    }
}