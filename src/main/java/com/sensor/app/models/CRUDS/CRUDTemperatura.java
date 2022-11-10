package App.Model.CRUDS;

import App.Model.Connections.ConnectDB;
import App.Model.Interfaces.CRUDAdapter;
import App.Model.ModelTemperatura;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDTemperatura extends CRUDAdapter {
    static CRUDTemperatura instance;
    private CRUDTemperatura() {
    }
    static public CRUDTemperatura getInstance(){
        if(instance == null){
            instance = new CRUDTemperatura();
        }
        return instance;
    }
    @Override
    public String insert(Object model) {
        ModelTemperatura modelo = (ModelTemperatura) model;
        ConnectDB connectDB = new ConnectDB();
        connectDB.connectDatabase();
        Connection connection = connectDB.getConnection();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO temperatura VALUES ('"+modelo.getFecha()+"','"+modelo.getHora()+"','"+modelo.getTemperatura()+"')");
            //st.executeUpdate("INSERT INTO humedad VALUES ('"+sensor.getFecha()+"','"+sensor.getHora()+"','"+sensor.getHumedad()+"')");
            st.close();
            connection.close();
        } catch (PSQLException ew) {
            return ew.getMessage();
        } catch (SQLException e) {
            Logger.getLogger(ModelTemperatura.class.getName()).log(Level.SEVERE, null, e);
        }
        return "Los datos se han insertado correctamente!!";
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
    public ArrayList<Object> selectLast() {
        ResultSet result;
        ModelTemperatura objTemperatura;
        ArrayList<Object> listTemperatura = new ArrayList<>();
        ConnectDB connectDB = new ConnectDB();
        connectDB.connectDatabase();
        Connection connection = connectDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM temperatura ORDER BY fecha,hora DESC LIMIT 1");
            while (result.next()) {
                objTemperatura = new ModelTemperatura(
                        result.getString(1),
                        result.getString(2),
                        Float.parseFloat(result.getString(3)));
                listTemperatura.add(objTemperatura);
            }
            statement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTemperatura;
    }

    @Override
    public ArrayList<Object> selectAll() {
        ResultSet result;
        ModelTemperatura objTemperatura;
        ArrayList<Object> listTemperatura = new ArrayList<>();
        ConnectDB connectDB = new ConnectDB();
        connectDB.connectDatabase();
        Connection connection = connectDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM temperatura");
            while (result.next()) {
                objTemperatura = new ModelTemperatura(
                        result.getString(1),
                        result.getString(2),
                        Float.parseFloat(result.getString(3)));
                listTemperatura.add(objTemperatura);
            }
            statement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTemperatura;
    }
}
