package com.sensor.model.app.Interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Interfaz que se encarga de definir los metodos de seleccion que se van a
 * utilizar en las clases que la implementen
 *
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public interface Select {

    /**
     * Metodo que se encarga de seleccionar el ultimo registro de la base de
     * datos
     *
     * @return Object con el ultimo registro de la base de datos
     */
    Object selectLast();

    /**
     * Metodo que se encarga de seleccionar todos los registros de la base de
     * datos
     *
     * @return ArrayList<>() con los datos de la tabla
     */
    ArrayList<Object> selectAll();

    /**
     * Método que se encarga de seleccionar un rango de fechas de todos los
     * registros de la base de datos
     *
     * @param firstDate la primera fecha
     * @param lastDate la última fecha
     * @return ArrayList<>() con el rango de fechas seleccionado
     */
    ArrayList<Object> selectDate(LocalDate firstDate, LocalDate lastDate);

    /**
     * Método que retorna el promedio de un rango de fechas
     *
     * @param day
     * @param firstHour
     * @param lastHour
     * @return
     */
    Integer getAverage(LocalDate day, LocalTime firstHour, LocalTime lastHour);
}
