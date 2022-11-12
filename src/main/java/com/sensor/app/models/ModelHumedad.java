package com.sensor.app.models;

public class ModelHumedad {
    private String fecha;
    private String hora;
    private float humedad;

    public ModelHumedad(String fecha, String hora, float humedad) {
        this.fecha = fecha;
        this.hora = hora;
        this.humedad = humedad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }
}
