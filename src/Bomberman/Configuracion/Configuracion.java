/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bomberman.Configuracion;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    public Dimension tamañoVentana = new Dimension(656,620);
    private final static String ARCHIVO_CONFIGURACION = "Settings.properties";
    private final static String ARCHIVO_CONFIGURACION_POR_DEFECTO = "/Juego/ConfiguacionPorDefecto.properties";
    private Properties configuacionPorDefecto;
    private HashMap<String, List<ISettingsListener>> listenersByKey;
    private List<SettingsListenerInfo> allListeners;
    
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
    
}
