/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.graphics.models;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author KevinCyndaquil
 */
public class Temperature {

    private Integer id;
    private final AtomicInteger C;
    private LocalDateTime date;

    public Integer x = 0;
    public Integer y = 0;

    public static final int MAX = 40;
    public static final int MIN = -5;
    public static final int RANGE = MAX - MIN;

    public Temperature(Integer id, int celsius, LocalDateTime fecha) {
        this.id = id;
        this.C = new AtomicInteger(celsius);
        this.date = fecha;
    }

    public Integer getId() {
        return id;
    }

    public AtomicInteger getCelsius() {
        return C;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCelsius(int celsius) {
        this.C.addAndGet(celsius);
    }

    public void setDate(LocalDateTime fecha) {
        this.date = fecha;
    }
}
