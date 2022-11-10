package App.Model.Threads;

import App.Model.CRUDS.CRUDHumedad;
import App.Model.CRUDS.CRUDTemperatura;
import App.Model.ModelHumedad;
import App.Model.ModelTemperatura;
import App.Model.ReadArduino;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HiloConnectArduinoDB extends Thread {
    ReadArduino readArduino;
    CRUDTemperatura crudT;
    CRUDHumedad crudH;
    float temperatura;
    float humedad;
    ModelTemperatura modelTemperatura;
    float temperaturaVariacion;
    ModelHumedad modelHumedad;
    float humeadadVariacion;

    static Timer timer;
    public HiloConnectArduinoDB(){
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

                    modelTemperatura = (ModelTemperatura) crudT.selectLast().get(0);
                    temperaturaVariacion = modelTemperatura.getTemperatura();
                    modelHumedad = (ModelHumedad) crudH.selectLast().get(0);
                    humeadadVariacion = modelHumedad.getHumedad();
                } catch (IndexOutOfBoundsException e) {
                    temperaturaVariacion = 0;
                    humeadadVariacion = 0;
                }

                temperatura = (float) readArduino.selectAll().get(0);
                System.out.println(temperatura+"aqui toy temperatura");
                humedad = (float) readArduino.selectAll().get(1);
                System.out.println(humedad+"aqui toy humedad");
                if (temperaturaVariacion != temperatura) {
                    crudT.insert(new ModelTemperatura(
                            LocalDate.now().toString(),
                            LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).toString(),
                            temperatura));
                }
                if (humeadadVariacion != humedad) {
                    crudH.insert(new ModelHumedad(
                            LocalDate.now().toString(),
                            LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).toString(),
                            humedad));
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
