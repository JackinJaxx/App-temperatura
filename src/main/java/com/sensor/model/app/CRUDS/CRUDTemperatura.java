package com.sensor.model.app.CRUDS;

import com.sensor.model.app.Connections.ConnectDB;
import com.sensor.model.app.Interfaces.Insert;
import com.sensor.model.app.Interfaces.Select;
import com.sensor.model.app.ModelTemperature;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase que se encarga de hacer las operaciones de la tabla Temperatura
 * @version 2.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public class CRUDTemperatura implements Insert, Select {

    private static CRUDTemperatura instance;
    private final ConnectDB connectDB;
    private final Connection connection;

    private Statement st;
    private PreparedStatement pt;
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(listTemperatura);
    }

    @Override
    public ArrayList<Object> selectDate(LocalDate firstDate, LocalDate lastDate) {
        ArrayList <ModelTemperature> list = new ArrayList <ModelTemperature> ();
        String sql = "SELECT * FROM temperatura WHERE fecha >= ? AND fecha <= ? ORDER BY fecha, hora";
        
        try{
            pt = connection.prepareStatement(sql);
            pt.setDate(1, java.sql.Date.valueOf(firstDate));
            pt.setDate(2, java.sql.Date.valueOf(lastDate));
            
            result = pt.executeQuery();
            
            while (result.next()) {
                list.add(new ModelTemperature(
                        result.getFloat(3),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2))));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return new ArrayList <> (list);
    }

    @Override
    public Integer getAverage(LocalDate day, LocalTime firstHour, LocalTime lastHour) {
        String sql = "SELECT TO_CHAR(AVG(temperatura), '99D99') AS prom FROM temperatura WHERE hora >= ? AND hora <= ? AND fecha = ?";
        
        try{
            pt = connection.prepareStatement(sql);
            pt.setTime(1, java.sql.Time.valueOf(firstHour));
            pt.setTime(2, java.sql.Time.valueOf(lastHour));
            pt.setDate(3, java.sql.Date.valueOf(day));
            
            result = pt.executeQuery();
            
            while (result.next()) {
                return Math.round(result.getInt("prom"));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        
        return null;
    }
}
