/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Alfonso Andrés
 */
public class Sonido {
    
    private Clip clip;
    private boolean loop=false;
    private boolean paused=false;

    public Sonido(Clip clip) {
        this.clip = clip;
    }

    public boolean isLoop() {
        return loop;
    }
    
    public boolean play(){
        if(clip==null)
            return false;
        if(!clip.isOpen())
            return false;
        if(clip.getFramePosition()==clip.getFrameLength())
            clip.setFramePosition(0);
        if(loop)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            clip.start();
        return true;
    }
    
    public boolean stop(){
        if(clip==null)
            return false;
        if(!clip.isOpen())
            return false;
        clip.stop();
        clip.setFramePosition(0);
        return true;
    }
    
    public boolean pause(){
        if(clip==null)
            return false;
        if(!clip.isOpen())
            return false;
        paused=true;
        clip.stop();
        return true;
    }
    
    public boolean loop(){
        if(clip==null)
            return false;
        if(!clip.isOpen())
            return false;
        if(loop){
            clip.start();
            loop=false;
        }else{
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            loop=true;
        }
        return true;
    }

    public boolean isPlaying() {
        return clip.isRunning();
    }

    public void setVolume(int vol){
        if(clip==null)
            return;
        if(vol<0||vol>100)
            vol=100;
        double val = vol / 100.0;
        try {
            FloatControl volControl =
              (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float)(Math.log(val == 0.0 ? 0.0001 : val) / Math.log(10.0) * 20.0);
            volControl.setValue(dB);
        } catch (Exception ex) {
            System.out.println("No se puede ajustar el volumen");
        }
    }
    
    public Sonido clonar() {
        Clip clip1=clip;
        Sonido sonido = new Sonido(clip);
        sonido.stop();
        return sonido;
    }
    
    public boolean isAlive(){
        return paused;
    }
    
    
    public int getFrameLength(){
        return clip.getFrameLength();
    }
    
    public int getFramePosition(){
        return clip.getFramePosition();
    }
}
