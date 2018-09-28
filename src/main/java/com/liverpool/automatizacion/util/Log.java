/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isancheza
 */
public class Log {
    public static String nameFile = "log_"+Fecha.date();
    
    public static void write(String msg){
        File fl = null;
        FileWriter fw = null;
        
        try {
            fl = new File(nameFile);
            fw = new FileWriter(fl, true);
            fw.write(Fecha.dateTimeFile() + " - " + msg + "\r\n");
            fw.flush();
        } catch (Exception e) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            if(fw != null){
                try{
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
