package App.Model.CRUDS;

import App.Model.Connections.ConnectDB;
import App.Model.Interfaces.CRUDAdapter;
import App.Model.ModelHumedad;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDHumedad extends CRUDAdapter {
    static CRUDHumedad instance;

    private CRUDHumedad() {
    }

    static public CRUDHumedad getInstance() {
        if (instance == null) {
            instance = new CRUDHumedad();
        }
        return instance;
    }

    @Override
    public String insert(Object model) {
        ModelHumedad modelo = (ModelHumedad) model;
        ConnectDB connectDB = new ConnectDB();
        connectDB.connectDatabase();
        Connection connection = connectDB.getConnection();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO humedad VALUES ('" + modelo.getFecha() + "','" + modelo.getHora() + "','" + modelo.getHumedad() + "')");
            //st.executeUpdate("INSERT INTO humedad VALUES ('"+sensor.getFecha()+"','"+sensor.getHora()+"','"+sensor.getHumedad()+"')");
            st.close();
            connection.close();
        } catch (PSQLException ew) {
            return ew.getMessage();
        } catch (SQLException e) {
            Logger.getLogger(ModelHumedad.class.getName()).log(Level.SEVERE, null, e);
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
        ModelHumedad objHumedad;
        ArrayList<Object> listHumedad = new ArrayList<>();
        ConnectDB connectDB = new ConnectDB();
        connectDB.connectDatabase();
        Connection connection = connectDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM humedad ORDER BY fecha,hora DESC LIMIT 1");
            while (result.next()) {
                objHumedad = new ModelHumedad(
                        result.getString(1),
                        result.getString(2),
                        Float.parseFloat(result.getString(3)));
                listHumedad.add(objHumedad);
            }
            statement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listHumedad;
    }

    @Override
    public ArrayList<Object> selectAll() {
        ResultSet result;
        ModelHumedad objHumedad;
        ArrayList<Object> listHumedad = new ArrayList<>();
        ConnectDB connectDB = new ConnectDB();
        connectDB.connectDatabase();
        Connection connection = connectDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM humedad");
            while (result.next()) {
                objHumedad = new ModelHumedad(
                        result.getString(1),
                        result.getString(2),
                        Float.parseFloat(result.getString(3)));
                listHumedad.add(objHumedad);
            }
            statement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listHumedad;
    }
}
