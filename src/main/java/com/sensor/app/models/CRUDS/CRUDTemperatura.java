package com.sensor.app.models.CRUDS;

import com.sensor.app.models.Connections.ConnectDB;
import com.sensor.app.models.Interfaces.CRUDAdapter;
import com.sensor.app.models.ModelTemperature;
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
 * Clase que se encarga de hacer las operaciones de la tabla Temperatura
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public class CRUDTemperatura extends CRUDAdapter {

    private static CRUDTemperatura instance;
    private final ConnectDB connectDB;
    private final Connection connection;

    private Statement st;
    private ResultSet result;

    /**
     * Constructor de la clase
     * @see ConnectDB
     */
    private CRUDTemperatura() {
        connectDB = new ConnectDB();
        connectDB.connectDatabase();
        connection = connectDB.getConnection();
    }

    /**
     * Metodo que se encarga de crear una instancia de la clase
     * @return CRUDTemperatura
     */
    static public CRUDTemperatura getInstance() {
        if (instance == null) {
            instance = new CRUDTemperatura();
        }
        return instance;
    }

    @Override
    public String insert(Object model) {
        ModelTemperature modelo = (ModelTemperature) model;

        String fecha = modelo.getDate().getYear() + "/" + modelo.getDate().getMonthValue() + "/" + modelo.getDate().getDayOfMonth();
        String hora = modelo.getDate().getHour() + ":" + modelo.getDate().getMinute() + ":" + modelo.getDate().getSecond();
        String sql = "INSERT INTO temperatura VALUES ('" +
                fecha + "','" +
                hora + "'," +
                modelo.getCelsius() + ")";

        try {
            st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (PSQLException ew) {
            return ew.getMessage();
        } catch (SQLException e) {
            Logger.getLogger(ModelTemperature.class.getName()).log(Level.SEVERE, null, e);
        }
        return "\n**TEMPERATURA CREADA**\n";
    }

    @Override
    public Object selectLast() {
        ModelTemperature modelo = null;
        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM temperatura ORDER BY fecha,hora DESC LIMIT 1");
            while (result.next()) {
                modelo = new ModelTemperature(
                        result.getFloat(3),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelo;
    }

    @Override
    public ArrayList<Object> selectAll() {
        ArrayList<ModelTemperature> listTemperatura = new ArrayList<>();
        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM temperatura");
            while (result.next()) {
                listTemperatura.add(new ModelTemperature(
                        result.getFloat(3),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2))));
            }
            st.close();
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(listTemperatura);
    }

    @Override
    public int update(Object model) {
        return 0;
    }

    @Override
    public int delete(Object primaryKey) {
        return 0;
    }


}
