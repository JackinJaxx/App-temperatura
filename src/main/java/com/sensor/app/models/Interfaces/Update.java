package com.sensor.app.models.Interfaces;
/**
 * Interfaz que se encarga de definir los metodos de actualizacion que se van a utilizar en las clases que la implementen
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public interface Update {
    /**
     * Metodo que se encarga de actualizar los datos de la base de datos
     * @param model objeto que se va a actualizar
     * @return int que indica si se actualizo o no
     */
    int update(Object model);
}
