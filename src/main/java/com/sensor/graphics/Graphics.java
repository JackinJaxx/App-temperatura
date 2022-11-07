/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.sensor.graphics;

import com.sensor.graphics.controllers.ControllerGraphics;

/**
 *
 * @author KevinCyndaquil
 */
public class Graphics {

    public static void main(String[] args) {
        ControllerGraphics graphics = new ControllerGraphics();
        graphics.throwJFrame();
        graphics.throwTemperature(); 
    }
}
