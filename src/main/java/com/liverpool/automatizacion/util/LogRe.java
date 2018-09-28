/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isancheza
 */
public class LogRe {            
                                     // PrefijoDDMMAA                         
    private static final String re = "^([\\w]*)([0-3][\\d][0-1][\\d][\\d]{2})$";
    
    // Obtener el prefijo del nombre del log
    public static String getPrefijo(String nameLog){
        Pattern pat = Pattern.compile(re);
        Matcher mat = pat.matcher(nameLog);
        if(mat.matches()){
            return mat.group(1);
        }
        return null;
    }
    
    // Obtener el prefijo del nombre del log
    public static String getDate(String nameLog){
        Pattern pat = Pattern.compile(re);
        Matcher mat = pat.matcher(nameLog);
        if(mat.matches()){
            return mat.group(2);
        }
        return null;
    }
    
    public static boolean isAValidFile(File f, String prefijo){
        Pattern pat = Pattern.compile("^" + prefijo + "[0-3][\\d][0-1][\\d][\\d]{2}$");
        Matcher mat = pat.matcher(f.getName());
        return mat.matches();
    }
}
