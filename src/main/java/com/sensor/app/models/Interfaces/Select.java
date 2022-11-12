package com.sensor.app.models.Interfaces;

import java.util.ArrayList;
/**
 * Interfaz que se encarga de definir los metodos de seleccion que se van a utilizar en las clases que la implementen
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public interface Select {

    /**
     * Metodo que se encarga de seleccionar el ultimo registro de la base de datos
     * @return Object con el ultimo registro de la base de datos
     */
    Object selectLast();

    /**
     * Metodo que se encarga de seleccionar todos los registros de la base de datos
     * @return ArrayList con los datos de la tabla
     */
    ArrayList<Object> selectAll();
}
