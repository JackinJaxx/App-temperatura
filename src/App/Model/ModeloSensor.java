package App.Model;

import java.time.LocalDateTime;

public class ModeloSensor {
    private float temperatura;
    private float humedad;
    private String fecha;
    private String hora;

    public ModeloSensor(float temperatura, float humedad, String fecha, String hora) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.fecha = fecha;
        this.hora = hora;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
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
}
