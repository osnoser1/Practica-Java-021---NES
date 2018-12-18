/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import java.awt.Image;
import motor.core.java.resources.ImageResource;
import static motor.core.java.resources.ImageResource.*;

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
    public static Image LADRILLO_ESPECIAL;

    static {
        final Ubicacion ubicacion = new Ubicacion();
        final ImageResource ir = new ImageResource();
////        FONDO = manejador.loadVolatileImageJAR(ubicacion.FONDO);
        LOGO = ir.load(ubicacion.LOGO, VOLATILE);
        ACERO = ir.load(ubicacion.ACERO, VOLATILE);
        PISO = ir.load(ubicacion.PISO, VOLATILE);
        LADRILLO_ESPECIAL = ir.load(ubicacion.LADRILLO_ESPECIAL, VOLATILE);
        BLOQUE = ir.load(ubicacion.BLOQUE, VOLATILE);
        APUNTADOR = ir.load(ubicacion.APUNTADOR, VOLATILE);
        BOMBA = ir.load(ubicacion.BOMBA, VOLATILE);
        FUEGO = ir.load(ubicacion.FUEGO, VOLATILE);
        BOMBERMAN = ir.load(ubicacion.BOMBERMAN, VOLATILE);
        BALLOOM = ir.load(ubicacion.BALLOOM, VOLATILE);
        DOLL = ir.load(ubicacion.DOLL, VOLATILE);
        KONDORIA = ir.load(ubicacion.KONDORIA, VOLATILE);
        MINVO = ir.load(ubicacion.MINVO, VOLATILE);
        ONEAL = ir.load(ubicacion.ONEAL, VOLATILE);
        OVAPI = ir.load(ubicacion.OVAPI, VOLATILE);
        PASS = ir.load(ubicacion.PASS, VOLATILE);
        PONTAN = ir.load(ubicacion.PONTAN, VOLATILE);
    }

}
