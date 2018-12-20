/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package dependencias;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Alfonso Andrés
 */
public class Sound {
    
    private boolean loop;
    private boolean paused;
    private final Clip clip;
    private boolean playing;

    public Sound(Clip clip) {
        this.clip = clip;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean play() {
        if(clip == null || !clip.isOpen())
            return (playing = false);
        if(clip.getFramePosition() == clip.getFrameLength())
            clip.setFramePosition(0);
        if(loop)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            clip.start();
        return (playing = true);
    }

    /**
     * @return Value indicating if the sound stopped.
     */
    public boolean stop() {
        if(clip == null || !clip.isOpen())
            return false;
        clip.stop();
        clip.setFramePosition(0);
        playing = false;
        return true;
    }

    public boolean pause() {
        if(clip == null || !clip.isOpen())
            return (playing = false);
        paused = true;
        clip.stop();
        playing = false;
        return true;
    }

    public boolean loop() {
        if(clip == null)
            return false;
        if(!clip.isOpen())
            return false;
        if(loop) {
            clip.start();
            loop = false;
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            loop = true;
        }
        return true;
    }

    public boolean isPlaying() {
        return playing && clip.getFramePosition() != clip.getFrameLength();
    }

    public void setVolume(int vol) {
        if(clip == null)
            return;
        if(vol < 0 || vol > 100)
            vol = 100;
        var val = vol / 100.0;
        try {
            var volControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            var dB = (float)(Math.log(val == 0.0 ? 0.0001 : val) / Math.log(10.0) * 20.0);
            volControl.setValue(dB);
        } catch(Exception ex) {
            System.out.println("Unable to adjust the volume.");
        }
    }

    @Override
    public Sound clone() {
        var sound = new Sound(clip);
        sound.stop();
        return sound;
    }

    public boolean isAlive() {
        return paused;
    }

    public int getFrameLength() {
        return clip.getFrameLength();
    }

    public int getFramePosition() {
        return clip.getFramePosition();
    }
    
}