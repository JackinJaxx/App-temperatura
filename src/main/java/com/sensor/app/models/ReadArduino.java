package com.sensor.app.models;

<<<<<<< HEAD:src/main/java/com/sensor/app/models/ReadArduino.java
import com.sensor.app.connections.ConnectArduino;
import com.sensor.app.models.abstracs.CRUD;
=======
import App.Model.Connections.ConnectArduino;
import App.Model.Interfaces.CRUDAdapter;

>>>>>>> eea6d66 (backup):src/App/Model/ReadArduino.java
import java.util.ArrayList;

public class ReadArduino extends CRUDAdapter {
    private ConnectArduino connectArduino;
    public ReadArduino() {
        connectArduino = new ConnectArduino();
        connectArduino.connect();
    }

    @Override
    public ArrayList<Object> selectAll() {
        ArrayList<Object> list = new ArrayList<>(); //posicion 0 la temperatura, posicion 1 la humedad
        //int temperatura = Math.round(Float.parseFloat((viewMenu.jTTemperatura.getText())));;
        list.add(connectArduino.getTemperatura());
        list.add(connectArduino.getHumedad());

        return list;
    }

}
