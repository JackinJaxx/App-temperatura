package App.Model;

import java.util.ArrayList;

public class ReadArduino implements CRUD{
    private ConnectArduino connectArduino;
    public ReadArduino() {
        connectArduino = new ConnectArduino();
        System.out.println(connectArduino.connect());
    }
    @Override
    public ArrayList<Object> select() {
        ArrayList<Object> list = new ArrayList<>();
        //int temperatura = Math.round(Float.parseFloat((viewMenu.jTTemperatura.getText())));
        float temperatura = connectArduino.getTemperatura();
        list.add(temperatura);
        float humedad = connectArduino.getHumedad();
        list.add(humedad);
        return list;
    }
    @Override
    public String insert(Object model) {
        return null;
    }

    @Override
    public int update(Object model) {
        return 0;
    }

    @Override
    public int delete(Object primaryKey) {
        return 0;
    }


}
