package com.sensor.app.models.Threads;

import com.sensor.app.models.CRUDS.CRUDHumedad;
import com.sensor.app.models.CRUDS.CRUDTemperatura;
import com.sensor.app.models.ReadArduino;
import com.sensor.graphics.models.Humidity;
import com.sensor.graphics.models.Temperature;
import java.time.LocalDateTime;

public class HiloConnectArduinoDB extends Thread {

    private final ReadArduino readArduino;
    private final CRUDTemperatura crudT;
    private final CRUDHumedad crudH;

    private Temperature t;
    private float varT;
    private float temperatura;
    private Humidity h;
    private float varH;
    private float humedad;

    public HiloConnectArduinoDB() {
        readArduino = new ReadArduino();
        crudT = CRUDTemperatura.getInstance();
        crudH = CRUDHumedad.getInstance();
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {

            try {
                try {
                    t = (Temperature) crudT.selectLast();
                    varT = t.getCelsius();
                    h = (Humidity) crudH.selectLast();
                    varH = h.getPercentage().get();
                } catch (NullPointerException e) {
                    varT = 0;
                    varH = 0;
                }

                temperatura = (float) readArduino.selectAll().get(0);
                humedad = (float) readArduino.selectAll().get(1);

                System.out.println("\n-----ARDUINO-----");
                System.out.println("Temperatura -> " + temperatura);
                System.out.println("Humedad -> " + humedad);
                System.out.println("");

                if (varT != temperatura) {
                    crudT.insert(new Temperature(temperatura, LocalDateTime.now()));
                }
                if (varH != humedad) {
                    crudH.insert(new Humidity((int) humedad, LocalDateTime.now()));
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
