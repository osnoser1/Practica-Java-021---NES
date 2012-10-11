package GUI;

import Controladores.ControladorPanelEspecial;
import Dependencias.Imagenes;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JToolBar;

public class JToolBarBotonesEditor extends JToolBar {

    private ArrayList<BotonEspecial> botones;
    private final int cantidadBotones = 11;
    private static JToolBarBotonesEditor instance;
    
    private JToolBarBotonesEditor() {
    	super(JToolBar.VERTICAL);
    	initComponents();
    }

    public static JToolBarBotonesEditor getInstance(){
        return instance == null ? (instance = new JToolBarBotonesEditor()) : instance;
    }
    
    private void initComponents() {
        this.setLayout(new java.awt.GridLayout(6,2,5,5));
        this.setPreferredSize(new java.awt.Dimension(150,560));
    	this.setBackground(Color.white);
    	this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.gray));
        botones = new ArrayList<>();
    	initBotonesEspeciales();
    }
    
    private void initBotonesEspeciales() {
        for(int i = 0; i < this.cantidadBotones; i++){
            if(i > 1)
                botones.add(new BotonEspecial(getPersonaje(i)));
            else
                botones.add(new BotonEspecial(getBufferedImage(i)));
            BotonEspecial boton = botones.get(i);
            boton.addActionListener(new ControladorPanelEspecial(boton));
            boton.setName(getNameBotonEspecial(i));
            this.add(botones.get(i));
        }
    }

    private BufferedImage getBufferedImage(int i) {
        int opcion=0;
        if(i == opcion++) return Imagenes.LADRILLO.get(0);
        return Imagenes.PISO;
    }
    
    private Personajes.Personaje getPersonaje(int i) {
        int opcion = 2;
        if(i == opcion++) return new Personajes.Bomberman(0, 0);
        if(i == opcion++) return new Personajes.Balloom(0, 0);
        if(i == opcion++) return new Personajes.Oneal(0, 0);
        if(i == opcion++) return new Personajes.Doll(0, 0);
        if(i == opcion++) return new Personajes.Minvo(0, 0);
        if(i == opcion++) return new Personajes.Kondoria(0, 0);
        if(i == opcion++) return new Personajes.Ovapi(0, 0);
        if(i == opcion++) return new Personajes.Pass(0, 0);
        return new Personajes.Pontan(0, 0);
    }
    
    private String getNameBotonEspecial(int i) {
        int opcion=0;
        if(opcion++ == i) return "Ladrillo";
        if(opcion++ == i) return "Piso";
        if(opcion++ == i) return "Bomberman";
        if(opcion++ == i) return "Ballom";
        if(opcion++ == i) return "Oneal";
        if(opcion++ == i) return "Doll";
        if(opcion++ == i) return "Minvo";
        if(opcion++ == i) return "Kondoria";
        if(opcion++ == i) return "Ovapi";
        if(opcion++ == i) return "Pass";
        return "Pontan";

    }
    
}