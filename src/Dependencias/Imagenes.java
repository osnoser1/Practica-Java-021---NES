/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author estudiante
 */
public class Imagenes{
    
    public static BufferedImage LOGO;
    public static BufferedImage APUNTADOR;
    public static BufferedImage FONDO;
    public static BufferedImage PISO;
    public static BufferedImage ACERO;
    public static BufferedImage BOMBERMAN;
    public static ArrayList<BufferedImage> BOMB;
    public static ArrayList<BufferedImage> FIRE;
    public static ArrayList<BufferedImage> LADRILLO;
    public static ArrayList<BufferedImage> BOMBERMAN_1;
    public static ArrayList<BufferedImage> BALLOOM_1;
    public static ArrayList<BufferedImage> ONEAL_1;
    public static ArrayList<BufferedImage> DOLL_1;
    public static ArrayList<BufferedImage> MINVO_1;
    public static ArrayList<BufferedImage> KONDORIA_1;
    public static ArrayList<BufferedImage> OVAPI_1;
    public static ArrayList<BufferedImage> PASS_1;
    public static ArrayList<BufferedImage> PONTAN_0;
    public static ArrayList<BufferedImage> MUERTE_ENEMIGOS;
    public static ArrayList<BufferedImage> MUERTE_BOMBERMAN;
    public static ArrayList<BufferedImage> LADRILLO_ESPECIAL;
    
    public Imagenes(){
        initComponents();   
    }
    
    public final void initComponents(){
        BOMB = new ArrayList<>();
        FIRE = new ArrayList<>();
        LADRILLO = new ArrayList<>();
        BALLOOM_1 = new ArrayList<>();
        ONEAL_1 = new ArrayList<>();
        DOLL_1 = new ArrayList<>();
        MINVO_1 = new ArrayList<>();
        KONDORIA_1 = new ArrayList<>();
        OVAPI_1 = new ArrayList<>();
        PASS_1 = new ArrayList<>();
        PONTAN_0 = new ArrayList<>();
        MUERTE_ENEMIGOS = new ArrayList<>();
        BOMBERMAN_1 = new ArrayList<>();
        LADRILLO_ESPECIAL = new ArrayList<>();
    }
    
    public static void cargarImagenes(){
        LOGO = ManejadorDeArchivos.getInstance().loadBufferedImageJAR(Ubicacion.LOGO);
        APUNTADOR = ManejadorDeArchivos.getInstance().loadBufferedImageJAR(Ubicacion.APUNTADOR);
        PISO = ManejadorDeArchivos.getInstance().loadBufferedImageJAR(Ubicacion.PISO);
        ACERO = ManejadorDeArchivos.getInstance().loadBufferedImageJAR(Ubicacion.ACERO);
        BOMBERMAN = ManejadorDeArchivos.getInstance().loadBufferedImageJAR(Ubicacion.BOMBERMAN);
//        FONDO = ManejadorDeArchivos.getInstance().loadBufferedImageJAR(Ubicacion.FONDO);
        LADRILLO_ESPECIAL = getAmbiente("Ladrillo Especial",9);
        LADRILLO = getAmbiente("Bloque",7);
        BOMB = getAmbiente("Bomba",3);
        FIRE = getAmbiente("Fire",28);
        
        BALLOOM_1 = getEnemigo("Balloom");
        DOLL_1 = getEnemigo("Doll");
        KONDORIA_1 = getEnemigo("Kondoria");
        MINVO_1 = getEnemigo("Minvo");
        ONEAL_1 = getEnemigo("Oneal");
        OVAPI_1 = getEnemigo("Ovapi");
        PASS_1 = getEnemigo("Pass");
        PONTAN_0 = getEnemigo("Pontan",6);
        MUERTE_ENEMIGOS = getEnemigo("Muerte",4);
        BOMBERMAN_1 = getJugador("Bomberman",18);
        
    }
    public static ArrayList<BufferedImage> getEnemigo(String Nombre){
        ArrayList<BufferedImage> Enemigo = new ArrayList<>();
        try{
            for(int i = 1;i<= 7;i++) {
                Enemigo.add(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/Enemigos/"+Nombre+"/"+Nombre+i+".png"));
            }
        }catch(Exception e){}
        return Enemigo;
    }
    
    public static ArrayList<BufferedImage> getEnemigo(String Nombre,int a){
        ArrayList<BufferedImage> Enemigo = new ArrayList<>();
        try{
            for(int i = 1;i<= a;i++) {
                Enemigo.add(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/Enemigos/"+Nombre+"/"+Nombre+i+".png"));
            }
        }catch(Exception e){}
        return Enemigo;
    }
    public static ArrayList<BufferedImage> getJugador(String Nombre,int a){
        ArrayList<BufferedImage> Jugador = new ArrayList<>();
        try{
            for(int i = 1;i<= a;i++) {
                Jugador.add(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/Jugadores/"+Nombre+"/"+Nombre+i+".png"));
            }
        }catch(Exception e){}
        return Jugador;
    }
    public static ArrayList<BufferedImage> getAmbiente(String Nombre,int a){
        ArrayList<BufferedImage> Ambiente = new ArrayList<>();
        try{
            for(int i = 1;i<= a;i++) {
                Ambiente.add(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/Ambiente/"+Nombre+"/"+Nombre+i+".png"));
            }
        }catch(Exception e){}
        return Ambiente;
    }
}
