/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sensor.graphics.models.abstracs;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author KevinCyndaquil
 */
public interface CatalogGraphics extends ActionListener{
    public Integer throwJPanel(int witdh, int height);
    public Integer throwJPanel(int witdh, int height, ArrayList <Object> function);
    public Integer setSecondCoordinates(Object object);
    public Integer setRangeCoordinates(Object object);
    public Integer setHoursCoordinates(ArrayList <Object> function);
    public Integer paintLine(Graphics g, Object object1, Object object2);
    public Integer paintHelpLine(Graphics g, Object object);
    public Integer paintCartesian(Graphics g);
    public Integer paintGraphic(Graphics g);
    public Integer paintGraphic(Graphics g, ArrayList <Object> function);
    public Integer start();
    public Integer stop();
}
