package com.sensor.app.models.CRUDS;

import com.sensor.app.models.Connections.ConnectDB;
import com.sensor.app.models.Interfaces.CRUDAdapter;
import com.sensor.graphics.models.Humidity;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase que se encarga de hacer las operaciones de la tabla Humedad
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public class CRUDHumedad extends CRUDAdapter {

    public static CRUDHumedad instance;
    private final ConnectDB connectDB;
    private final Connection connection;

    private Statement st;
    private ResultSet result;

    /**
     * Constructor de la clase
     * @see ConnectDB
     */
    private CRUDHumedad() {
        connectDB = new ConnectDB();
        connectDB.connectDatabase();
        connection = connectDB.getConnection();
    }

    /**
     * Metodo que se encarga de crear una instancia de la clase
     * @return CRUDHumedad
     */
    static public CRUDHumedad getInstance() {
        if (instance == null) {
            instance = new CRUDHumedad();
        }
        return instance;
    }

    @Override
    public String insert(Object model) {
        Humidity modelo = (Humidity) model;

        String fecha = modelo.getDate().getYear() + "/" + modelo.getDate().getMonthValue() + "/" + modelo.getDate().getDayOfMonth();
        String hora = modelo.getDate().getHour() + ":" + modelo.getDate().getMinute() + ":" + modelo.getDate().getSecond();
        System.out.println(fecha + "x" + hora);
        String sql = "INSERT INTO humedad VALUES ('"
                + fecha + "','"
                + hora + "',"
                + modelo.getPercentage().get() + ")";
        try {
            st = connection.createStatement();
            st.executeQuery(sql);
        } catch (PSQLException ew) {
            return ew.getMessage();
        } catch (SQLException e) {
            Logger.getLogger(Humidity.class.getName()).log(Level.SEVERE, null, e);
        }
        return "\n**HUMEDAD CREADA**\n";
    }

    @Override
    public int update(Object model) {
        return 0;
    }

    @Override
    public int delete(Object primaryKey) {
        return 0;
    }

    @Override
    public Object selectLast() {
        Humidity modelo = null;
        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM humedad ORDER BY fecha,hora DESC LIMIT 1");
            while (result.next()) {
                modelo = new Humidity(
                        Integer.parseInt(result.getString(3)),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return modelo;
    }

    @Override
    public ArrayList<Object> selectAll() {
        ArrayList<Humidity> listHumedad = new ArrayList<>();

        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM humedad");
            while (result.next()) {
                listHumedad.add(new Humidity(
                        Integer.parseInt(result.getString(3)),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(listHumedad);
    }
}
