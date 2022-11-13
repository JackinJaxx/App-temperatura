/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.app.models;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Esta clase se encarga de guardar los datos de la temperatura
 * @author KevinCyndaqui, JackinJaxx, Wuicho24
 * @version 1.0
 */
public class ModelTemperature {

    private final AtomicInteger C;
    private final AtomicInteger decimal;
    private LocalDateTime date;

    public Integer x = 0;
    public Integer y = 0;

    public static final int MAX = 40;
    public static final int MIN = -5;
    public static final int RANGE = MAX - MIN;

    /**
     * Constructor de la clase
     * @param celsius grados centigrados
     * @param date incluye la date y hora
     */
    public ModelTemperature(float celsius, LocalDateTime date) {
        this.C = new AtomicInteger(Math.round(celsius));
        this.decimal = new AtomicInteger(Math.round((celsius - Math.round(celsius)) * 100));
        this.date = date;
    }
    
    public float getCelsius(){
        return (float)(C.get() + ((float)(decimal.get()) / 100f));
    }
    
    public AtomicInteger getACelsius() {
        return C;
    }

    public AtomicInteger getDecimal() {
        return decimal;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public void setCelsius(int celsius) {
        this.C.addAndGet(celsius);
    }
    
    public void setDecimal(int decimal) {
        
    }
    
    public void setDate(LocalDateTime fecha) {
        this.date = fecha;
    }
}
