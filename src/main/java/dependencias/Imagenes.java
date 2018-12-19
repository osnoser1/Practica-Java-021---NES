/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dependencias;

import java.awt.Image;
import motor.core.java.resources.ImageResource;
import static motor.core.java.resources.ImageResource.*;

/**
 *
 * @author estudiante
 */
public class Imagenes {

    public static final Image LOGO;
    public static final Image APUNTADOR;
    public static Image FONDO;
    public static final Image PISO;
    public static final Image ACERO;
    public static final Image BOMBA;
    public static final Image FUEGO;
    public static final Image BOMBERMAN;
    public static final Image BALLOOM;
    public static final Image DOLL;
    public static final Image KONDORIA;
    public static final Image MINVO;
    public static final Image ONEAL;
    public static final Image PASS;
    public static final Image OVAPI;
    public static final Image PONTAN;
    public static final Image BLOQUE;
    public static final Image LADRILLO_ESPECIAL;

    static {
        final var ubicacion = new Ubicacion();
        final var ir = new ImageResource();
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
