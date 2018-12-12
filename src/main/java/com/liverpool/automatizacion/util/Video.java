/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

/**
 *
 * @author aperezg03
 */
public class Video {
    
    ScreenRecorder screenRecorder;
    
    public Video(){
        try {
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();
            
            screenRecorder = new ScreenRecorder(gc, gc.getBounds(),
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f,
                            KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null);
//                    new File(targetFolder));
        } catch (IOException | AWTException ex) {
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarVideo(){
        try {
            screenRecorder.start();
        } catch (IOException ex) {
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detenerVideo(){
        try {
            screenRecorder.stop();
        } catch (IOException ex) {
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
