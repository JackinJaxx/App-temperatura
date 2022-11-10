package com.sensor.app.models;

public class ModelTemperatura {

    private String fecha;
    private String hora;
    private float temperatura;

    public ModelTemperatura(String fecha, String hora, float temperatura) {
        this.fecha = fecha;
        this.hora = hora;
        this.temperatura = temperatura;
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

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }
}
