/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static Connection koneksi;

    public static Connection getKoneksi() {
    try {
        if (koneksi == null || koneksi.isClosed()) {

            String url = "jdbc:mysql://localhost:3306/db_absensi";
            String user = "root";
            String password = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            koneksi = DriverManager.getConnection(url, user, password);

            System.out.println("Koneksi Database Berhasil!");
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return koneksi;
}

    
}