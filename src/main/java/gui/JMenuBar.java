/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controladores.CJMenuItem;
import controladores.JMenuController;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Alfonso Andrés
 */
public class JMenuBar extends javax.swing.JMenuBar{

    private ArrayList<JMenu> jMenu;
    private ArrayList<JMenuItem> jMenuItem;
    private final int TOP_MENU = 5;
    private static JMenuBar instance;
    private CJMenuItem cJMenuItem;
    
    private JMenuBar() {
        super();
        initComponents();
    }

    public static JMenuBar getInstance() {
        return instance == null ? (instance = new JMenuBar()) : instance;
    }
    
    private void initComponents() {
        jMenu = new ArrayList<>();
        jMenuItem = new ArrayList<>();
        cJMenuItem = new CJMenuItem();
        initJMenu();
        initJMenuItem();
    }

    private void initJMenu() {
        var controller=new JMenuController();
        for(var i = 0; i<this.TOP_MENU; i++){
            jMenu.add(new javax.swing.JMenu(getTitleJMenu(i)));
            jMenu.get(i).addActionListener(controller);
            add(jMenu.get(i));
        }
    }
    private String getTitleJMenu(int i){
        if(i==0) return "Archivo";
        if(i==1) return "Editar";
        if(i==2) return "Opciones";
        if(i==3) return "Herramientas";
        return "Ayuda";
    }

    private void initJMenuItem() {
        initJMenuItemFile();
        initJMenuItemEdit();
        initJMenuItemOptions();
        initJMenuItemTools();
        initJMenuItemHelp();
    }

    private void initJMenuItemFile() {
        var get = jMenu.get(0);
        for(int size=5,i=0;i<size;i++){
            var jMenuItem = new JMenuItem(getTextFile(i));
            jMenuItem.setActionCommand(getTextFile(i));
            jMenuItem.setAccelerator(getAcceleratorFile(i));
            get.add(jMenuItem);
        }
    }

    private String getTextFile(int i){
        var option=0;
        if(i==option++) return "Juego en red";
        else if(i==option++) return "Desconectar";
        else if(i==option++) return "Conversar";
        else if(i==option++) return "Informacion del juego";
        return "Salir";
    }
    
    private void initJMenuItemEdit() {
        var get = jMenu.get(1);
        for(int size=5,i=0;i<size;i++){
            var jMenuItem = new JMenuItem(getTextEdition(i));
            jMenuItem.setActionCommand(getTextEdition(i));
            jMenuItem.setAccelerator(getAcceleratorEdition(i));
            get.add(jMenuItem);
        }
    }

    private void initJMenuItemOptions() {
        var get = jMenu.get(2);
        for(int size=8,i=0;i<size;i++){
            var jMenuItem = new JMenuItem(getTextOptions(i));
            jMenuItem.setActionCommand(getTextOptions(i));
            jMenuItem.setAccelerator(getAcceleratorOptions(i));
            jMenuItem.addActionListener(cJMenuItem);
            get.add(jMenuItem);
        }
    }

    private void initJMenuItemTools() {
        var get = jMenu.get(3);
        for(int size=3,i=0;i<size;i++){
            var jMenuItem = new JMenuItem(getTextTools(i));
            jMenuItem.setActionCommand(getTextTools(i));
            jMenuItem.setAccelerator(getAcceleratorTools(i));
            get.add(jMenuItem);
        }
    }

    private void initJMenuItemHelp() {
        var get = jMenu.get(4);
        for(int size=1,i=0;i<size;i++){
            var jMenuItem = new JMenuItem(getTextHelp(i));
            jMenuItem.setActionCommand(getTextHelp(i));
            jMenuItem.setAccelerator(getAcceleratorHelp(i));
            get.add(jMenuItem);
        }
    }

    private KeyStroke getAcceleratorFile(int i) {
        int option=0,keyCode;
        if(option++==i) keyCode=KeyEvent.VK_N;
        else if(option++==i) keyCode=KeyEvent.VK_D;
        else if(option++==i) keyCode=KeyEvent.VK_A;
        else if(option++==i) keyCode=KeyEvent.VK_I;
        else keyCode=KeyEvent.VK_X;
        return KeyStroke.getKeyStroke(keyCode,KeyEvent.CTRL_DOWN_MASK);
    }

    private String getTextEdition(int i) {
        var option=0;
        if(i==option++) return "Reiniciar";
        else if(i==option++) return "Pausa";
        else if(i==option++) return "Cargar estado";
        else if(i==option++) return "Guardar estado";
        return "Captura de Pantalla";
    }

    private KeyStroke getAcceleratorEdition(int i) {
        int option=0,keyCode;
        if(option++==i) keyCode=KeyEvent.VK_F1;
        else if(option++==i) keyCode=KeyEvent.VK_P;
        else if(option++==i) keyCode=KeyEvent.VK_L;
        else if(option++==i) keyCode=KeyEvent.VK_S;
        else keyCode=KeyEvent.VK_P;
        if(i<option)
            return KeyStroke.getKeyStroke(keyCode,0);
        else
            return KeyStroke.getKeyStroke(keyCode,KeyEvent.CTRL_DOWN_MASK);
    }

    private String getTextOptions(int i) {
        var options=0;
        if(i==options++) return "Juego...";
        if(i==options++) return "Graficos...";
        if(i==options++) return "Sounds...";
        if(i==options++) return "Controles...";
        if(i==options++) return "Atajos del teclado...";
        if(i==options++) return "Directorios...";
        if(i==options++) return "Pantalla completa";
        return "Mostrar FPS";
    }

    private KeyStroke getAcceleratorOptions(int i) {
        if(6==i) return KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK);
        else if(7==i) return KeyStroke.getKeyStroke(KeyEvent.VK_F11,0);
        return null;
    }

    private String getTextTools(int i) {
        var option=0;
        if(i==option++) return "Codigos de trampa...";
        else if(i==option++) return "Activar trampas";
        return "Desactivar trampas";
    }

    private KeyStroke getAcceleratorTools(int i) {
        int options=0,keyCode;
        if(options++==i) keyCode=KeyEvent.VK_END;
        else if(options++==i) keyCode=KeyEvent.VK_INSERT;
        else keyCode=KeyEvent.VK_DELETE;
        if(i==0)
            return KeyStroke.getKeyStroke(keyCode,KeyEvent.CTRL_DOWN_MASK);
        else
            return KeyStroke.getKeyStroke(keyCode,0);
    }

    private String getTextHelp(int i) {
        return "Acerca de...";
    }

    private KeyStroke getAcceleratorHelp(int i) {
        return null;
    }
    
}
