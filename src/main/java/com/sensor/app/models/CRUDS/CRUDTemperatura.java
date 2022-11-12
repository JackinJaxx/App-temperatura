package com.sensor.app.models.CRUDS;

import com.sensor.app.models.Connections.ConnectDB;
import com.sensor.app.models.Interfaces.CRUDAdapter;
import com.sensor.graphics.models.Temperature;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDTemperatura extends CRUDAdapter {
    
    public static CRUDTemperatura instance;
    private final ConnectDB connectDB;
    private final Connection connection;
    
    private Statement st;
    private ResultSet result;
    
    private CRUDTemperatura() {
        connectDB = new ConnectDB();
        connectDB.connectDatabase();
        connection = connectDB.getConnection();
    }
    
    static public CRUDTemperatura getInstance(){
        if(instance == null){
            instance = new CRUDTemperatura();
        }
        return instance;
    }
    
    @Override
    public String insert(Object model) {
        Temperature modelo = (Temperature) model;
        
        String fecha = modelo.getDate().getYear() + "/" + modelo.getDate().getMonthValue() + "/" + modelo.getDate().getDayOfMonth();
        String hora = modelo.getDate().getHour() + ":" + modelo.getDate().getMinute() + ":" + modelo.getDate().getSecond();
        String sql = "INSERT INTO temperatura VALUES ('" + 
                fecha + "','" +
                hora + "'," +
                modelo.getCelsius() + ")";
        
        try {
            st = connection.createStatement();
            st.executeUpdate(sql);
            //st.close();
            //connection.close();
        } catch (PSQLException ew) {
            return ew.getMessage();
        } catch (SQLException e) {
            Logger.getLogger(Temperature.class.getName()).log(Level.SEVERE, null, e);
        }
        return "\n**TEMPERATURA CREADA**\n";
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
        Temperature modelo = null;
        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM temperatura ORDER BY fecha,hora DESC LIMIT 1");
            while (result.next()) {
                modelo = new Temperature(
                        result.getFloat(3),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2)));
            }
            //st.close();
            //result.close();
            //connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (Object) modelo;
    }

    @Override
    public ArrayList<Object> selectAll() {
        ArrayList<Temperature> listTemperatura = new ArrayList<>();
        
        try {
            st = connection.createStatement();
            result = st.executeQuery("SELECT * FROM temperatura");
            while (result.next()) {
                listTemperatura.add(new Temperature(
                        result.getFloat(3),
                        LocalDateTime.parse(result.getString(1) + "T" + result.getString(2))));
            }
            st.close();
            result.close();
            //connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList <>(listTemperatura);
    }
}
