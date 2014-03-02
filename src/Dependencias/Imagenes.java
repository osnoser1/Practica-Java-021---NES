/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import core.java.JavaImage;
import lenguaje.utils.ManejadorDeArchivos;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import motor.utils.Image;

/**
 *
 * @author estudiante
 */
public class Imagenes {

    public static Image LOGO;
    public static Image APUNTADOR;
    public static Image FONDO;
    public static Image PISO;
    public static Image ACERO;
    public static Image BOMBA;
    public static Image FUEGO;
    public static Image BOMBERMAN;
    public static Image BALLOOM;
    public static Image DOLL;
    public static Image KONDORIA;
    public static Image MINVO;
    public static Image ONEAL;
    public static Image PASS;
    public static Image OVAPI;
    public static Image PONTAN;
    public static Image BLOQUE;
    public static ArrayList<Image> LADRILLO_ESPECIAL;

    public Imagenes() {
        initComponents();
    }

    public final void initComponents() {
        LADRILLO_ESPECIAL = new ArrayList<>();
    }

    public static void cargarImagenes() {
        Ubicacion ubicacion = new Ubicacion();
        ManejadorDeArchivos manejador = ManejadorDeArchivos.getInstance();
        LOGO = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.LOGO));
        BLOQUE = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.BLOQUE));
        APUNTADOR = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.APUNTADOR));
        PISO = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.PISO));
        ACERO = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.ACERO));
        BOMBA = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.BOMBA));
        FUEGO = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.FUEGO));
        BOMBERMAN = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.BOMBERMAN));
//        FONDO = manejador.loadBufferedImageJAR(ubicacion.FONDO);
        LADRILLO_ESPECIAL = getAmbiente("Ladrillo Especial", 9);
        BALLOOM = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.BALLOOM));
        DOLL = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.DOLL));
        KONDORIA = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.KONDORIA));
        MINVO = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.MINVO));
        ONEAL = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.ONEAL));
        OVAPI = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.OVAPI));
        PASS = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.PASS));
        PONTAN = JavaImage.get(manejador.loadBufferedImageJAR(ubicacion.PONTAN));
    }

    public static ArrayList<Image> getAmbiente(String Nombre, int a) {
        ArrayList<Image> Ambiente = new ArrayList<>();
        try {
            for (int i = 1; i <= a; i++) {
                Ambiente.add(JavaImage.get(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/Ambiente/" + Nombre + "/" + Nombre + i + ".png")));
            }
        } catch (Exception e) {
        }
        return Ambiente;
    }

}
