/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.java.resources;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lenguaje.utils.ManejadorDeArchivos;
import motor.core.Resource;

/**
 *
 * @author AlfonsoAndrés
 */
public class SoundResource implements Resource<Clip> {

    public static final int OGG = 0, WAV = 1;

    @Override
    public Clip load(String s, int type) {
        if (type == WAV)
            return ManejadorDeArchivos.getInstance().cargarClipJAR(s);
        Clip clip = null;
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(getClass().getResource(s));
            AudioInputStream din;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    false,            
                    false);
            // Get AudioInputStream that will be decoded by underlying VorbisSPI
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            // Play now !
//            rawplay(decodedFormat, din);
//            in.close();

            clip = AudioSystem.getClip();
            clip.open(din);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println(ex.getMessage());
        }
        return clip;
    }

}