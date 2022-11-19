package com.sensor.model.app.Interfaces;
/**
 * Interfaz que se encarga de insertar datos en la base de datos
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public interface Insert {
    /**
     * Metodo que se encarga de insertar datos en la base de datos
     * @param model Objeto que se va a insertar en la base de datos
     * @return String que contendra si se actualizo o no
     */
    String insert(Object model);
}
