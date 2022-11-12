package com.sensor.app.models;

import com.sensor.app.models.Connections.ConnectArduino;
import com.sensor.app.models.Interfaces.CRUDAdapter;
import java.util.ArrayList;

public class ReadArduino extends CRUDAdapter {

    private final ConnectArduino connectArduino;

    public ReadArduino() {
        connectArduino = new ConnectArduino();
        connectArduino.connect();
    }

    @Override
    public ArrayList<Object> selectAll() {
        ArrayList<Object> list = new ArrayList<>(); //posicion 0 la temperatura, posicion 1 la humedad
        list.add(connectArduino.getTemperatura());
        list.add(connectArduino.getHumedad());

        return list;
    }

}
