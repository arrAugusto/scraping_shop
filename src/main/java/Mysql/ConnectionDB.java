package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author agr12
 */
public class ConnectionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/scraping_shop"; // URL de la base de datos
    private static final String USER = "root"; // Nombre de usuario
    private static final String PASSWORD = ""; // Contraseña

    // Método para obtener una conexi�n a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
