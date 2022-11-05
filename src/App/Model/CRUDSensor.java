package App.Model;

import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDSensor implements CRUD{
    static CRUDSensor instance;
    private CRUDSensor() {
    }
    static public CRUDSensor getInstance(){
        if(instance == null){
            instance = new CRUDSensor();
        }
        return instance;
    }
    @Override
    public String insert(Object model) {
        ModeloSensor sensor = (ModeloSensor) model;
        ConnectDB.getInstance().connectDatabase();
        try {
            Statement st = ConnectDB.getInstance().getConnection().createStatement();
            st.executeUpdate("" +
                    "");
        } catch (PSQLException ew) {
            return ew.getMessage();
        } catch (SQLException e) {
            Logger.getLogger(ModeloSensor.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<Object> select() {
        return null;
    }
}
