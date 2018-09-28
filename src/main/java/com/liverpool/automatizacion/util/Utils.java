/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

/**
 *
 * @author iasancheza
 */
public class Utils {

    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Log.write(ex.toString());
        }
    }
    
}
