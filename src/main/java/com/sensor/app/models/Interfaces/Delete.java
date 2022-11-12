package com.sensor.app.models.Interfaces;
/**
 * Interfaz que se encarga de definir los metodos de eliminacion que se van a utilizar en las clases que la implementen
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */
public interface Delete {
    /**
     * Metodo que se encarga de eliminar los datos de la base de datos
     * @param primaryKey llave primaria del registro que se va a eliminar
     * @return int que indica si se elimino o no
     */
    int delete(Object primaryKey);
}
