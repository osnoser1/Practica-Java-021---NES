/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Alfonso Andrés
 */
public class JBarraMenu extends javax.swing.JMenuBar{

    private ArrayList<JMenu> jmenu;
    private ArrayList<JMenuItem> jMenuItem;
    private final int TOPE_JMENU = 5, TOPE_JMENU_ITEM = 10; 
    private static JBarraMenu instance;
    
    private JBarraMenu() {
        super();
        initComponents();
    }

    public static JBarraMenu getInstance() {
        return instance == null ? (instance = new JBarraMenu()) : instance;
    }
    
    private void initComponents() {
        jmenu = new ArrayList<>();
        jMenuItem = new ArrayList<>();
        initJMenu();
        initJMenuItem();
    }

    private void initJMenu() {
        Controladores.ControladorJMenu controlador=new Controladores.ControladorJMenu();
        for(int i=0;i<this.TOPE_JMENU;i++){
            jmenu.add(new javax.swing.JMenu(getTitleJMenu(i)));
            jmenu.get(i).addActionListener(controlador);
            add(jmenu.get(i));
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
        initJMenuItemArchivo();
        initJMenuItemEdicion();
        initJMenuItemOpciones();
        initJMenuItemHerramientas();
        initJMenuItemAyuda();
    }

    private void initJMenuItemArchivo() {
        JMenu get = jmenu.get(0);
        for(int size=5,i=0;i<size;i++){
            JMenuItem jMenuItem = new JMenuItem(getTextArchivo(i));
            jMenuItem.setActionCommand(getTextArchivo(i));
            jMenuItem.setAccelerator(getAcceleratorArchivo(i));
            get.add(jMenuItem);
        }
    }

    private String getTextArchivo(int i){
        int opcion=0;
        if(i==opcion++) return "Juego en red";
        else if(i==opcion++) return "Desconectar";
        else if(i==opcion++) return "Conversar";
        else if(i==opcion++) return "Informacion del juego";
        return "Salir";
    }
    
    private void initJMenuItemEdicion() {
        JMenu get = jmenu.get(1);
        for(int size=5,i=0;i<size;i++){
            JMenuItem jMenuItem = new JMenuItem(getTextEdicion(i));
            jMenuItem.setActionCommand(getTextEdicion(i));
            jMenuItem.setAccelerator(getAcceleratorEdicion(i));
            get.add(jMenuItem);
        }
    }

    private void initJMenuItemOpciones() {
        JMenu get = jmenu.get(2);
        for(int size=8,i=0;i<size;i++){
            JMenuItem jMenuItem = new JMenuItem(getTextOpciones(i));
            jMenuItem.setActionCommand(getTextOpciones(i));
            jMenuItem.setAccelerator(getAcceleratorOpciones(i));
            get.add(jMenuItem);
        }
    }

    private void initJMenuItemHerramientas() {
        JMenu get = jmenu.get(3);
        for(int size=3,i=0;i<size;i++){
            JMenuItem jMenuItem = new JMenuItem(getTextHerramientas(i));
            jMenuItem.setActionCommand(getTextHerramientas(i));
            jMenuItem.setAccelerator(getAcceleratorHerramientas(i));
            get.add(jMenuItem);
        }
    }

    private void initJMenuItemAyuda() {
        JMenu get = jmenu.get(4);
        for(int size=1,i=0;i<size;i++){
            JMenuItem jMenuItem = new JMenuItem(getTextAyuda(i));
            jMenuItem.setActionCommand(getTextAyuda(i));
            jMenuItem.setAccelerator(getAcceleratorAyuda(i));
            get.add(jMenuItem);
        }
    }

    private KeyStroke getAcceleratorArchivo(int i) {
        int opcion=0,keyCode;
        if(opcion++==i) keyCode=KeyEvent.VK_N;
        else if(opcion++==i) keyCode=KeyEvent.VK_D;
        else if(opcion++==i) keyCode=KeyEvent.VK_A;
        else if(opcion++==i) keyCode=KeyEvent.VK_I;
        else keyCode=KeyEvent.VK_X;
        return KeyStroke.getKeyStroke(keyCode,KeyEvent.CTRL_MASK);
    }

    private String getTextEdicion(int i) {
        int opcion=0;
        if(i==opcion++) return "Reiniciar";
        else if(i==opcion++) return "Pausa";
        else if(i==opcion++) return "Cargar estado";
        else if(i==opcion++) return "Guardar estado";
        return "Captura de Pantalla";
    }

    private KeyStroke getAcceleratorEdicion(int i) {
        int opcion=0,keyCode;
        if(opcion++==i) keyCode=KeyEvent.VK_F1;
        else if(opcion++==i) keyCode=KeyEvent.VK_P;
        else if(opcion++==i) keyCode=KeyEvent.VK_L;
        else if(opcion++==i) keyCode=KeyEvent.VK_S;
        else keyCode=KeyEvent.VK_P;
        if(i<opcion)
            return KeyStroke.getKeyStroke(keyCode,0);
        else
            return KeyStroke.getKeyStroke(keyCode,KeyEvent.CTRL_MASK);
    }

    private String getTextOpciones(int i) {
        int opcion=0;
        if(i==opcion++) return "Juego...";
        if(i==opcion++) return "Graficos...";
        if(i==opcion++) return "Sonidos...";
        if(i==opcion++) return "Controles...";
        if(i==opcion++) return "Atajos del teclado...";
        if(i==opcion++) return "Directorios...";
        if(i==opcion++) return "Pantalla completa";
        return "Mostrar FPS";
    }

    private KeyStroke getAcceleratorOpciones(int i) {
        if(6==i) return KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_MASK);
        else if(7==i) return KeyStroke.getKeyStroke(KeyEvent.VK_F11,0);
        return null;
    }

    private String getTextHerramientas(int i) {
        int opcion=0;
        if(i==opcion++) return "Codigos de trampa...";
        else if(i==opcion++) return "Activar trampas";
        return "Desactivar trampas";
    }

    private KeyStroke getAcceleratorHerramientas(int i) {
        int opcion=0,keyCode;
        if(opcion++==i) keyCode=KeyEvent.VK_END;
        else if(opcion++==i) keyCode=KeyEvent.VK_INSERT;
        else keyCode=KeyEvent.VK_DELETE;
        if(i==0)
            return KeyStroke.getKeyStroke(keyCode,KeyEvent.CTRL_MASK);
        else
            return KeyStroke.getKeyStroke(keyCode,0);
    }

    private String getTextAyuda(int i) {
        return "Acerca de...";
    }

    private KeyStroke getAcceleratorAyuda(int i) {
        return null;
    }
    
}
