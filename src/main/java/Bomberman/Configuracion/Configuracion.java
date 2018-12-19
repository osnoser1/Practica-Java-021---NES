/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bomberman.Configuracion;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author AlfonsoAndrés
 */
public class Configuracion {

    private static Configuracion instance;
    private final Dimension tamañoVentana = new Dimension(656, 600);
    private final Dimension internalResolution = new Dimension(640, 560);
    private final Point2D.Double escala = new Point2D.Double(1, 1);
    private final static String ARCHIVO_CONFIGURACION = "Settings.properties";
    private final static String ARCHIVO_CONFIGURACION_POR_DEFECTO = "/Juego/ConfiguacionPorDefecto.properties";
    private Properties configuacionPorDefecto;
    private final HashMap<String, List<ISettingsListener>> listenersByKey;
    private final List<SettingsListenerInfo> allListeners;

    private Configuracion() {
        listenersByKey = new HashMap<>();
        allListeners = new LinkedList<>();
//        configuacionPorDefecto = new Properties();
//        patchSettings = new Properties();
//        InputStream defaultSettingsStream = null;
//        InputStream loadedSettingsStream = null;
//        try {
//            defaultSettingsStream = getClass().getResourceAsStream(ARCHIVO_CONFIGURACION_POR_DEFECTO);
//            configuacionPorDefecto.load(defaultSettingsStream);
//            loadedSettings = new SortedProperties(defaultSettings);
//            File settingsFile = new File(ARCHIVO_CONFIGURACION);
//            settingsFile.createNewFile();
//            loadedSettingsStream = new BufferedInputStream(new FileInputStream(settingsFile));
//            loadedSettings.load(loadedSettingsStream);
//        } catch (Exception e) {
//            
//        }
    }

    public static Configuracion getInstance() {
        return instance == null ? (instance = new Configuracion()) : instance;
    }

    public void setTamañoVentana(int width, int height) {
        tamañoVentana.setSize(width, height);
        escala.setLocation(
                tamañoVentana.getWidth() / internalResolution.getWidth(),
                tamañoVentana.getHeight() / internalResolution.getHeight()
        );
    }

    public Dimension getTamañoVentana() {
        return tamañoVentana;
    }

    public double getEscalaX() {
        return escala.x;
    }
    
    public double getEscalaY() {
        return escala.y;
    }
    
}
