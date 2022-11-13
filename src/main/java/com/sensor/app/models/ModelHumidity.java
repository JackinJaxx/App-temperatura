/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.app.models;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Esta clase se encarga de guardar los datos de la humedad
 * @author KevinCyndaqui, JackinJaxx, Wuicho24
 * @version 1.0
 */
public class ModelHumidity {

    private final AtomicInteger percentage;
    private LocalDateTime date;

    public static final int MAX = 90;
    public static final int MIN = 20;
    public static final int RANGE = MAX - MIN;

    public Integer x;
    public Integer y;

    /**
     * Constructor de la clase
     * @param percentage porcentaje de humedad
     * @param date incluye la fecha y hora
     */
    public ModelHumidity(int percentage, LocalDateTime date) {
        this.percentage = new AtomicInteger(percentage);
        this.date = date;
    }

    public AtomicInteger getPercentage() {
        return percentage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setPercentage(int percentage) {
        this.percentage.compareAndSet(percentage, percentage);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
