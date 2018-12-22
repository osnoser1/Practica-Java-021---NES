/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import engine.core.game.Game;
import engine.core.java.gui.JFramePrincipal;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Alfonso Andrés
 */
public class JavaPractice021 {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
//        final String os = System.getProperty("os.name");
//        if (os.contains("Windows"))
//            System.setProperty("sun.java2d.d3d", "True");
//        else
//        System.setProperty("sun.java2d.trace", "timestamp,log,count");
        System.setProperty("sun.java2d.trace", "count");
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.noddraw", "true");
//        System.setProperty("sun.java2d.translaccel", "true");
        java.awt.EventQueue.invokeLater(JavaPractice021::run);
    }

    private static void run() {
        try {
            var reflection = new Reflections();
            var game = reflection.getSubTypesOf(Game.class).stream().findFirst().get().getDeclaredConstructor().newInstance();
            new JFramePrincipal(game);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
