package com.sensor.app.connections;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;


public class ConnectArduino {
    private PanamaHitek_Arduino ino;
    private PanamaHitek_MultiMessage multi;
    private SerialPortEventListener listener;

    private String port = "COM4"; //aca se pondra el puerto al que esta conectada tu computadora

    private float temperatura;
    private float humedad;


    public ConnectArduino() {
        ino = new PanamaHitek_Arduino();
        multi = new PanamaHitek_MultiMessage(2, ino);
        listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                try {
                    if (multi.dataReceptionCompleted()) {
                        temperatura = Float.parseFloat(multi.getMessage(0));
                        humedad = Float.parseFloat(multi.getMessage(1));
                        System.out.println("temperatura en arduino: " + multi.getMessage(0));
                        System.out.println("humedad en arduino: " + multi.getMessage(1));
                        multi.flushBuffer();
                    }
                } catch (ArduinoException | SerialPortException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    public String connect() {
        try {
            ino.arduinoRX(port, 9600, listener);
        } catch (Exception e) {
            return "Error al conectar Arduino: " + e;
        }
        return "Puerto "+port+ " conectado";
    }

    public float getTemperatura() {
        return temperatura;
    }

    public float getHumedad() {
        return humedad;
    }
}
