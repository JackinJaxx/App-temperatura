package com.sensor.model.app.Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de conectarse a la base de datos
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public class ConnectDB {

    private final String url = "jdbc:postgresql://localhost:5432/termometro"; //aca se pone el nombre de la base de datos
    private final String user = "postgres";
    private final String password = "alejandro1807"; //contraseña de postgres
    private Connection connection = null;

    /**
     * Metodo que se encarga de conectarse a la base de datos
     * @return Boolean que indica si se conecto o no
     */
    public Boolean connectDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + e);
            return false;
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Error al conectar a la base de datos " + url + ": " + e);
        }
        return false;
    }

    /**
     * Metodo getter de la conexion
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }
}
