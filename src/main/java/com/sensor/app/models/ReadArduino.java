package com.sensor.app.models;

import com.sensor.app.connections.ConnectArduino;
import com.sensor.app.models.abstracs.CRUD;
import java.util.ArrayList;

public class ReadArduino implements CRUD {
    private ConnectArduino connectArduino;
    public ReadArduino() {
        connectArduino = new ConnectArduino();
        System.out.println(connectArduino.connect());
    }

    @Override
    public ArrayList<Object> select() {
        ArrayList<Object> list = new ArrayList<>(); //posicion 0 la temperatura, posicion 1 la humedad
        //int temperatura = Math.round(Float.parseFloat((viewMenu.jTTemperatura.getText())));;
        list.add(connectArduino.getTemperatura());
        list.add(connectArduino.getHumedad());

        return list;
    }
    @Override
    public String insert(Object model) {
        return null;
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
