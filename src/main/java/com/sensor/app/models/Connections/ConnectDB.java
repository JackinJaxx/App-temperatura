<<<<<<<< HEAD:src/main/java/com/sensor/app/connections/ConnectDB.java
package com.sensor.app.connections;
========
package App.Model.Connections;
>>>>>>>> eea6d66 (backup):src/main/java/com/sensor/app/models/Connections/ConnectDB.java


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    //connectDatabase postgresql
    private final String url = "jdbc:postgresql://localhost:5432/termometro"; //aca se pone el nombre de la base de datos
    private final String user = "postgres";
    private final String password = "qw6xdg7sB!"; //contraseña de postgres
    private static ConnectDB instance;
    private Connection connection = null;

    public ConnectDB() {
    }
    /*public static ConnectDB getInstance(){
        if(instance == null){
            instance = new ConnectDB();
        }
        return instance;
    }
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
    public Connection getConnection() {
        return connection;
    }
}
