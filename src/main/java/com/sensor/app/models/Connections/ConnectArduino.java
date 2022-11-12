package com.sensor.app.models.Connections;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ConnectArduino {

    private final PanamaHitek_Arduino ino;
    private final SerialPortEventListener listener;
    private PanamaHitek_MultiMessage multi;

    private final String port = "COM16"; //aca se pondra el puerto al que esta conectada tu computadora
    private float temperatura;
    private float humedad;

    public ConnectArduino() {
        ino = new PanamaHitek_Arduino();
        multi = new PanamaHitek_MultiMessage(2, ino);
        listener = (SerialPortEvent serialPortEvent) -> {
            try {
                if (multi.dataReceptionCompleted()) {
                    temperatura = Float.parseFloat(multi.getMessage(0));
                    humedad = Float.parseFloat(multi.getMessage(1));
                    multi.flushBuffer();
                }
            } catch (ArduinoException | SerialPortException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public String connect() {
        try {
            ino.arduinoRX(port, 9600, listener);
        } catch (ArduinoException | SerialPortException e) {
            return "Error al conectar Arduino: " + e;
        }
        return "Puerto " + port + " conectado";
    }

    public static void main(String[] args) {
        ConnectArduino obj = new ConnectArduino();
        obj.connect();
    }

    public float getTemperatura() {
        return temperatura;
    }

    public float getHumedad() {
        return humedad;
    }
}
