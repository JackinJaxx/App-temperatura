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
public class Humidity {

    private final AtomicInteger percentage;
    private LocalDateTime date;

    public static final int MAX = 90;
    public static final int MIN = 20;
    public static final int RANGE = MAX - MIN;

    public Integer x;
    public Integer y;

    public Humidity(int percentage, LocalDateTime date) {
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
