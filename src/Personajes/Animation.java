
package Personajes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation  {
  
    int cont=0,aux=-1,CantImg,inicio;

    private ArrayList< BufferedImage > Sprite;
    private int currFrameIndex;
    private ArrayList<Integer> cuadros;
    private long tiempoTranscurrido;
    private long tiempoFotograma;
    private int paso;
    
    public Animation(ArrayList<BufferedImage> sprite,int inicio,int CantImg) {
        this.Sprite = sprite;
        this.inicio = inicio;
        this.CantImg = CantImg;
        cont = inicio;
         
    }
    public Animation(BufferedImage imagen,ArrayList<BufferedImage> sprite,int inicio,int CantImg) {
        cuadros = new ArrayList<>();
        this.Sprite = new ArrayList<>();
        this.Sprite.add(imagen);
        this.Sprite.addAll(sprite);
        this.inicio = inicio;
        this.CantImg = CantImg;
        cont = inicio;
    }
    
    public Animation(ArrayList<BufferedImage> sprite){
        cuadros = new ArrayList<>();
        this.Sprite = sprite;
        this.CantImg = sprite.size();
    }

    public Animation(String frames, long tiempoFotograma) {
        this.tiempoFotograma = tiempoFotograma;
        cuadros = new ArrayList<>();
        String[] cuadrosTmp = frames.split(",");
        for(String frame : cuadrosTmp){
            cuadros.add(Integer.parseInt(frame));
        }
        cuadros.add(-1);
    }
    
    public void MovimientoSprite(){
        if(cont==(inicio)||cont==(inicio+CantImg-1))
            aux*=-1;
        cont+=aux;
    }
    
     public void resetCont(){
         cont=1;
     }
     
    public BufferedImage getImageSprite(){
        return Sprite.get(cont);
    }
    public ArrayList<BufferedImage> getSprite(){
        return Sprite;
    }
    
    private void CargarSprite(ArrayList<BufferedImage> sprite) {
        for(int i=inicio;i<CantImg-1;i++){
            this.Sprite.add(sprite.get(i));
        }
        
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public int getCont() {
        return cont;
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