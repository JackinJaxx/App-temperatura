package App.Model;

import java.util.ArrayList;

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
    public int insert(Object model) {
        return 0;
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
