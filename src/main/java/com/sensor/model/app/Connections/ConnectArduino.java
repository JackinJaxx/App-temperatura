package com.sensor.model.app.Connections;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 * Clase que se encarga de conectarse al arduino
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public class ConnectArduino {
    private final PanamaHitek_Arduino ino;
    private final SerialPortEventListener listener;
    private PanamaHitek_MultiMessage multi;
    private final String port = "COM4"; //aca se pondra el puerto al que esta conectada tu computadora
    private Float temperatura;
    private Float humedad;

    /**
     * Constructor de la clase
     * @see PanamaHitek_Arduino
     * @see SerialPortEventListener
     * @see PanamaHitek_MultiMessage
     * @see SerialPortEvent
     */
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

    /**
     * Metodo que se encarga de conectarse al arduino mediante el puerto
     * @return String Puerto " + port + " conectado ||   Error al conectar Arduino
     */
    public String connect() {
        try {
            ino.arduinoRX(port, 9600, listener);
        } catch (ArduinoException | SerialPortException e) {
            temperatura = null;
            humedad = null;
            return "Error al conectar Arduino: " + e;
        }
        return "Puerto " + port + " conectado";
    }

    public static void main(String[] args) {
        ConnectArduino obj = new ConnectArduino();
        obj.connect();
    }

    /**
     * Metodo getter de la temperatura
     * @return float temperatura
     */
    public Float getTemperatura() {
        return temperatura;
    }

    /**
     * Metodo getter de la humedad
     * @return float humedad
     */
    public Float getHumedad() {
        return humedad;
    }
}
