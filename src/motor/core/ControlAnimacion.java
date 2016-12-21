package motor.core;

import java.util.ArrayList;

public final class ControlAnimacion {

    private final ArrayList<Integer> cuadros;
    private long tiempoTranscurrido;
    private final long tiempoFotograma;
    private int paso;

    public ControlAnimacion(String frames, long tiempoFotograma) {
        this.tiempoFotograma = tiempoFotograma;
        final String[] cuadrosTmp = frames.split(",");
        cuadros = new ArrayList<>(cuadrosTmp.length + 1);
        for (String frame : cuadrosTmp)
            cuadros.add(Integer.parseInt(frame));
        cuadros.add(-1);
    }

    /**
     *
     * @param totalTiempo Tiempo total transcurrido en milisegundos
     * @return
     */
    public boolean actualizar(final long totalTiempo) {
        //Actualizar el tiempo transcurrido
        tiempoTranscurrido += totalTiempo;
        // Si el tiempo transcurrido es mayor que el plazo de tiempo
        // Tenemos que cambiar los fotogramas
        if (tiempoTranscurrido > tiempoFotograma) {
            tiempoTranscurrido = 0;
            if (cuadros.get(++paso) == -1) {
                paso = 0;
                return true;
            }
        }
        return false;
    }

    public void reiniciar() {
        tiempoTranscurrido = paso = 0;
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
