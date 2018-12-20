/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman.configuracion;

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
public class Configuration {

    private static Configuration instance;
    private final Dimension windowSize = new Dimension(656, 600);
    private final Dimension internalResolution = new Dimension(640, 560);
    private final Point2D.Double scale = new Point2D.Double(1, 1);
    private final HashMap<String, List<ISettingsListener>> listenersByKey;
    private final List<SettingsListenerInfo> allListeners;

    private Configuration() {
        listenersByKey = new HashMap<>();
        allListeners = new LinkedList<>();
//        defaultSettings = new Properties();
//        patchSettings = new Properties();
//        InputStream defaultSettingsStream = null;
//        InputStream loadedSettingsStream = null;
//        try {
//            defaultSettingsStream = getClass().getResourceAsStream(DEFAULT_SETTINGS_FILE);
//            defaultSettings.load(defaultSettingsStream);
//            loadedSettings = new SortedProperties(defaultSettings);
//            File settingsFile = new File(SETTINGS_FILE);
//            settingsFile.createNewFile();
//            loadedSettingsStream = new BufferedInputStream(new FileInputStream(settingsFile));
//            loadedSettings.load(loadedSettingsStream);
//        } catch (Exception e) {
//            
//        }
    }

    public static Configuration getInstance() {
        return instance == null ? (instance = new Configuration()) : instance;
    }

    public void setWindowSize(int width, int height) {
        windowSize.setSize(width, height);
        scale.setLocation(
                windowSize.getWidth() / internalResolution.getWidth(),
                windowSize.getHeight() / internalResolution.getHeight()
        );
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

    public double getScaleX() {
        return scale.x;
    }
    
    public double getScaleY() {
        return scale.y;
    }
    
}
