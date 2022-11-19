package com.sensor.model.app.Interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Clase abstracta que contiene la implemetacion de Select, Delete, Update, Insert.
 * @see Select
 * @see Delete
 * @see Update
 * @see Insert
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
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
    public ArrayList<Object> selectDate(LocalDate firstDate, LocalDate lastDate){
        return null;
    }
    
    @Override
    public Integer getAverage(LocalDate day, LocalTime firstHour, LocalTime lastHour){
        return null;
    }

    @Override
    public int delete(Object primaryKey){
        return 0;
    }
}
