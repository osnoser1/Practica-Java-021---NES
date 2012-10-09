
package Personajes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation  {
  
    int cont=0,aux=-1,CantImg,inicio;

    private ArrayList< BufferedImage > Sprite;
    private int currFrameIndex;
    
    public Animation(ArrayList<BufferedImage> sprite,int inicio,int CantImg) {
        this.Sprite=new ArrayList<>();
        this.Sprite=sprite;
        this.inicio=inicio;
        this.CantImg=CantImg;
        cont=inicio;
         
    }
    public Animation(BufferedImage imagen,ArrayList<BufferedImage> sprite,int inicio,int CantImg) {
        this.Sprite=new ArrayList<BufferedImage>();

        this.Sprite.add(imagen);
        this.Sprite.addAll(sprite);
        this.inicio=inicio;
        this.CantImg=CantImg;
        cont=inicio;
     
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
    
        
    }




