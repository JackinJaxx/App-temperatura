package com.sensor.app.models.Interfaces;

import java.util.ArrayList;

public abstract class CRUDAdapter implements Select, Delete, Update, Insert {
    @Override
    public String insert(Object model){
        return null;
    }

    @Override
    public int update(Object model){
        return 0;
    }

    @Override
    public ArrayList<Object> selectAll() {
        return null;
    }

    @Override
    public Object selectLast() {
        return null;
    }

    @Override
    public int delete(Object primaryKey){
        return 0;
    }
}
