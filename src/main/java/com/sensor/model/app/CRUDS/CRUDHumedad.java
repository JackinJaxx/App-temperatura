package com.sensor.model.app.CRUDS;

import com.sensor.model.app.Connections.ConnectDB;
import com.sensor.model.app.Interfaces.Insert;
import com.sensor.model.app.Interfaces.Select;
import com.sensor.model.app.ModelHumidity;
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
 * Clase que se encarga de hacer las operaciones de la tabla Humedad
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public class CRUDHumedad implements Select, Insert {

    private static CRUDHumedad instance;
    private final ConnectDB connectDB;
    private final Connection connection;

    private Statement st;
    private PreparedStatement pt;
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
    public static  CRUDHumedad getInstance() {
        if (instance == null) {
            instance = new CRUDHumedad();
        }
        return instance;
    }

    @Override
    public String insert(Object model) {
        ModelHumidity modelo = (ModelHumidity) model;

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
            Logger.getLogger(ModelHumidity.class.getName()).log(Level.SEVERE, null, e);
        }
        return "\n**HUMEDAD CREADA**\n";
    }

    @Override
    public Object selectLast() {
        ModelHumidity modelo = null;
        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM humedad ORDER BY fecha,hora DESC LIMIT 1");
            while (result.next()) {
                modelo = new ModelHumidity(
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
        ArrayList<ModelHumidity> listHumedad = new ArrayList<>();

        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM humedad");
            while (result.next()) {
                listHumedad.add(new ModelHumidity(
                        Integer.parseInt(result.getString(3)),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(listHumedad);
    }

    @Override
    public ArrayList<Object> selectDate(LocalDate firstDate, LocalDate lastDate) {
        ArrayList <ModelHumidity> list = new ArrayList <ModelHumidity> ();
        String sql = "SELECT * FROM humedad WHERE fecha >= ? AND fecha <= ? ORDER BY fecha, hora";
        
        try{
            pt = connection.prepareStatement(sql);
            pt.setDate(1, java.sql.Date.valueOf(firstDate));
            pt.setDate(2, java.sql.Date.valueOf(lastDate));
            
            result = pt.executeQuery();
            
            while (result.next()) {
                list.add(new ModelHumidity(
                        Integer.parseInt(result.getString(3)),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2))));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return new ArrayList <> (list);
    }

    @Override
    public Integer getAverage(LocalDate day, LocalTime firstHour, LocalTime lastHour) {
        String sql = "SELECT TO_CHAR(AVG(humedad), '99D99') AS prom FROM humedad WHERE hora >= ? AND hora <= ? AND fecha = ?";
        
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
