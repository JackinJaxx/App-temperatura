package com.sensor.controller.app;

import com.sensor.model.app.CRUDS.CRUDHumedad;
import com.sensor.model.app.CRUDS.CRUDTemperatura;
import com.sensor.model.app.ModelHumidity;
import com.sensor.model.app.CRUDS.ReadArduino;
import com.sensor.model.app.ModelTemperature;

import java.time.LocalDateTime;

/**
 * Hilo que se encarga de leer los datos del arduino y guardarlos en la base de datos
 *
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 * @version 1.0
 * @see Thread
 */
public class HiloConnectArduinoDB extends Thread {
    private final ReadArduino readArduino;
    private final CRUDTemperatura crudT;
    private final CRUDHumedad crudH;
    private ModelTemperature objectT;
    private ModelHumidity objectH;

    //Contendra el ultimo valor de temperatura en la base de datos
    private float lastTemperatura;

    // Contendra el valor de temperatura actual del arduino
    private float temperatura;

    //contendra el ultimo valor de humedad en la base de datos
    private float lastHumedad;

    // Contendra el valor de humedad actual del arduino
    private float humedad;

    /**
     * Constructor de la clase
     * @see ReadArduino
     * @see CRUDTemperatura
     * @see CRUDHumedad
     */
    public HiloConnectArduinoDB() {
        readArduino = ReadArduino.getInstance();
        crudT = CRUDTemperatura.getInstance();
        crudH = CRUDHumedad.getInstance();
    }

    /**
     * Metodo que se ejecuta al iniciar el hilo
     */
    @Override
    public void run() {
        try {
            Thread.sleep(2000); /*Se espera 1 segundos para que se conecte el arduino y el sensor recopile los datos
                                        ya que el sensor no recopila al instante*/
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                try {
                    objectT = (ModelTemperature) crudT.selectLast();// Pide ala base de datos la consulta del ultimo registro de temperatura
                    lastTemperatura = objectT.getCelsius(); //Se guarda el ultimo valor de temperatura de la base de datos
                    objectH = (ModelHumidity) crudH.selectLast(); //Pide ala base de datos la consulta del ultimo registro de humedad
                    lastHumedad = objectH.getPercentage().get(); //Se guarda el ultimo valor de humedad de la base de datos
                } catch (
                        NullPointerException e) { //si entra aca esque mando un null y no hay ningun registro aun, entonces se inicializan en 0
                    lastTemperatura = 0;
                    lastHumedad = 0;
                }

                try {
                    temperatura = (float) readArduino.selectAll().get(0); //pide el valor actual de temperatura al arduino
                    humedad = (float) readArduino.selectAll().get(1); //pide el valor actual de humedad al arduino

                    System.out.println("\n-----ARDUINO-----");
                    System.out.println("Temperatura -> " + temperatura);
                    System.out.println("Humedad -> " + humedad);
                    System.out.println();

                    //Si el valor actual es diferente al ultimo valor en la base de datos  inserta en la base de datos
                    if (lastTemperatura != temperatura) {
                        crudT.insert(new ModelTemperature(temperatura, LocalDateTime.now()));
                    }
                    if (lastHumedad != humedad) {
                        crudH.insert(new ModelHumidity((int) humedad, LocalDateTime.now()));
                    }
                }catch (NullPointerException e){
                    System.out.println("No se pudo leer el arduino");
                }
                Thread.sleep(1000);// cada dos segundos esta generando este while
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
