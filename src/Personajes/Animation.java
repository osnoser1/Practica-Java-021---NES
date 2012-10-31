package Personajes;

import java.util.ArrayList;

public class Animation  {

    private ArrayList<Integer> cuadros;
    private long tiempoTranscurrido;
    private long tiempoFotograma;
    private int paso;

    public Animation(String frames, long tiempoFotograma) {
        this.tiempoFotograma = tiempoFotograma;
        cuadros = new ArrayList<>();
        String[] cuadrosTmp = frames.split(",");
        for(String frame : cuadrosTmp){
            cuadros.add(Integer.parseInt(frame));
        }
        cuadros.add(-1);
    }

    /**
     *
     * @param totalTiempo Tiempo total transcurrido en milisegundos
     * @return
     */
    public boolean actualizar(long totalTiempo){
        //Actualizar el tiempo transcurrido
        tiempoTranscurrido += totalTiempo;
        // Si el tiempo transcurrido es mayor que el plazo de tiempo
        // Tenemos que cambiar los fotogramas
        if(tiempoTranscurrido > tiempoFotograma) {//frame 
            tiempoTranscurrido = 0;
            if(cuadros.get(++paso) == -1){
                paso = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * @return El indice del fotograma actual
     */
    public int getPaso() {
        return paso;
    }

    /**
     * @param paso El indice de fotograma a establecer
     */
    public void setPaso(int paso) {
        this.paso = paso;
    }
 
    /**
     *
     * @return El indice del frame actual a pintar
     */
    public int getCuadroActual() {
        return cuadros.get(paso);
    }
    
}