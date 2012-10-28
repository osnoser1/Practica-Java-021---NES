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
    public static BufferedImage BOMBA;
    public static BufferedImage FUEGO;
    public static BufferedImage BOMBERMAN;
    public static BufferedImage BALLOOM;
    public static BufferedImage DOLL;
    public static BufferedImage KONDORIA;
    public static BufferedImage MINVO;
    public static BufferedImage ONEAL;
    public static BufferedImage PASS;
    public static BufferedImage OVAPI;
    public static BufferedImage PONTAN;
    public static BufferedImage BLOQUE;
    public static ArrayList<BufferedImage> LADRILLO_ESPECIAL;
    
    public Imagenes(){
        initComponents();   
    }
    
    public final void initComponents(){
        LADRILLO_ESPECIAL = new ArrayList<>();
    }
    
    public static void cargarImagenes(){
        Ubicacion ubicacion = new Ubicacion();
        ManejadorDeArchivos manejador = ManejadorDeArchivos.getInstance();
        LOGO = manejador.loadBufferedImageJAR(ubicacion.LOGO);
        BLOQUE = manejador.loadBufferedImageJAR(ubicacion.BLOQUE);
        APUNTADOR = manejador.loadBufferedImageJAR(ubicacion.APUNTADOR);
        PISO = manejador.loadBufferedImageJAR(ubicacion.PISO);
        ACERO = manejador.loadBufferedImageJAR(ubicacion.ACERO);
        BOMBA = manejador.loadBufferedImageJAR(ubicacion.BOMBA);
        FUEGO = manejador.loadBufferedImageJAR(ubicacion.FUEGO);
        BOMBERMAN = manejador.loadBufferedImageJAR(ubicacion.BOMBERMAN);
//        FONDO = manejador.loadBufferedImageJAR(ubicacion.FONDO);
        LADRILLO_ESPECIAL = getAmbiente("Ladrillo Especial",9);        
        BALLOOM = manejador.loadBufferedImageJAR(ubicacion.BALLOOM);
        DOLL = manejador.loadBufferedImageJAR(ubicacion.DOLL);
        KONDORIA = manejador.loadBufferedImageJAR(ubicacion.KONDORIA);
        MINVO = manejador.loadBufferedImageJAR(ubicacion.MINVO);
        ONEAL = manejador.loadBufferedImageJAR(ubicacion.ONEAL);
        OVAPI = manejador.loadBufferedImageJAR(ubicacion.OVAPI);
        PASS = manejador.loadBufferedImageJAR(ubicacion.PASS);
        PONTAN = manejador.loadBufferedImageJAR(ubicacion.PONTAN);        
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
