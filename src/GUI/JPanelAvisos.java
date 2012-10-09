/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Fuentes.Fuentes;
import Sonidos.Sonidos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelAvisos extends javax.swing.JPanel{

    private short nivel=1,MAX_NIVEL=50;
    private BufferedImage imagen;
    private Fuentes fuentes;
    
    public JPanelAvisos() {
        super();
        initComponents();
    }

    private void initComponents() {
        this.setBackground(Color.BLACK);
        this.setVisible(false);
        this.setRequestFocusEnabled(true);
        this.requestFocusInWindow();
        this.setFocusable(true);
        fuentes=new Fuentes();
        imagen=new BufferedImage(640,560,BufferedImage.TYPE_INT_RGB);
    }


    public void setMAX_NIVEL(short MAX_NIVEL) {
        this.MAX_NIVEL=(short)(MAX_NIVEL>50?50:
                MAX_NIVEL<1?1:MAX_NIVEL);
    }

    public void setNivel(short nivel) {
        this.nivel=(short)(nivel>50?50:
                nivel<1?1:nivel);
    }

    public void aumentarNivel(){
        if(nivel!=MAX_NIVEL)
            nivel++;
    }
    
    public boolean finDeJuego(){
        return nivel==MAX_NIVEL;
    }
    
    public short getNivel() {
        return nivel;
    }

    public short getMAX_NIVEL() {
        return MAX_NIVEL;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imagen!=null)
            g.drawImage(imagen,0,0,this.getWidth(),this.getHeight(),this);
    }
    
    
    public void iniciarJPanelStage(){
        drawString("STAGE  "+this.nivel);
        this.setVisible(true);
        Sonidos.getInstance().getSonido(Sonidos.LEVEL_START).play();
    }
    
    public void iniciarJPanelGameOver(){
        drawString("GAME OVER");
        this.setVisible(true);
        Sonidos.getInstance().getSonido(Sonidos.GAME_OVER).play();
    }
    
    public void iniciarBonusStage(){
        drawString("BONUS STAGE");
        this.setVisible(true);
    }
    
    private void drawString(String string){
        imagen=new BufferedImage(640,560,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=imagen.createGraphics();
        g2.setColor(new Color(127,127,127));
        g2.setFont(fuentes.getJoystixMonospacce(24));
        g2.drawString(string,199,298);
        g2.setColor(Color.WHITE);
        g2.drawString(string,197,296);
    }
    
}
