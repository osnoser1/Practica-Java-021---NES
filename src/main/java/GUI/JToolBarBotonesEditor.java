package gui;

import Controladores.CPanelEspecial;
import Dependencias.Imagenes;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JToolBar;
import juego.constantes.Objetos;

public class JToolBarBotonesEditor extends JToolBar {

    private ArrayList<BotonEspecial> botones;
    private final int cantidadBotones = 11;
    private static JToolBarBotonesEditor instance;

    private JToolBarBotonesEditor() {
        super(JToolBar.VERTICAL);
        initComponents();
    }

    public static JToolBarBotonesEditor getInstance() {
        return instance == null ? (instance = new JToolBarBotonesEditor()) : instance;
    }

    private void initComponents() {
        this.setLayout(new java.awt.GridLayout(6, 2, 5, 5));
        this.setPreferredSize(new java.awt.Dimension(150, 560));
        this.setBackground(Color.white);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.gray));
        botones = new ArrayList<>();
        initBotonesEspeciales();
    }

    private void initBotonesEspeciales() {
        var objetos = Objetos.values();
        for (var i = 0; i < this.cantidadBotones; i++) {
            if (i > 1) {
                botones.add(new BotonEspecial(Objetos.getInstance(objetos[i].getValue())));
            } else {
                botones.add(new BotonEspecial(getImage(i)));
            }
            var boton = botones.get(i);
            boton.addActionListener(new CPanelEspecial(boton));
            boton.setName(objetos[i].name());
            this.add(botones.get(i));
        }
    }

    private Image getImage(int i) {
//        int opcion=0;
//        if(i == opcion++) return new Ladrillo(0, 0, 0).getImagen().getSubimage(0, 0);
        return Imagenes.PISO;
    }

}
