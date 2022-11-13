package com.sensor.app.models.CRUDS;

import com.sensor.app.models.Connections.ConnectArduino;
import com.sensor.app.models.Interfaces.CRUDAdapter;

import java.util.ArrayList;

/**
 * Clase que se encarga de leer los datos del arduino
 *
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 * @version 1.0
 */
public class ReadArduino extends CRUDAdapter {
    private final ConnectArduino connectArduino;
    private static ReadArduino instance;
    /**
     * Constructor de la clase
     */
    private ReadArduino() {
        connectArduino = new ConnectArduino();
        connectArduino.connect();
    }

    public static ReadArduino getInstance() {
        if (instance == null) {
            instance = new ReadArduino();
        }
        return instance;
    }

    /**
     * Metodo que se encarga de leer los datos del arduino
     *
     * @return ArrayList con los datos del arduino posicion 0 = temperatura, posicion 1 = humedad
     */
    @Override
    public ArrayList<Object> selectAll() {
        ArrayList<Object> list = new ArrayList<>(); //posicion 0 la temperatura, posicion 1 la humedad

        list.add(connectArduino.getTemperatura());
        list.add(connectArduino.getHumedad());
        return list;
    }
}
