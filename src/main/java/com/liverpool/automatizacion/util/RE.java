/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isancheza
 */
public class RE {
    
    public static boolean isADate(String birthday){
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(birthday);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static boolean isAMail(String mail){
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(mail);
        return mat.matches();
    }
    
    public static boolean isAPhoneNumber(String num, int min, int max) {
        Pattern pat = Pattern.compile("^[\\d]{"+min+","+max+"}");
        Matcher mat = pat.matcher(num);
        return mat.matches();
    }
    
    public static boolean isAnAmount(String monto){
        Pattern pat = Pattern.compile("^[\\d]+(\\.[\\d]{2})*");
        Matcher mat = pat.matcher(monto);
        return mat.matches();
    }
    
    public static boolean isAName(String name){
        Pattern pat = Pattern.compile("^[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]{2,}");
        Matcher mat = pat.matcher(name);
        return mat.matches();
    }
    
    public static boolean isANum(String num){
        Pattern pat = Pattern.compile("^[\\d]+");
        Matcher mat = pat.matcher(num);
        return mat.matches();
    }
    
    // Validar cadena vacio o cadena de espacios
    public static boolean isEmpty(String text){
        Pattern pat = Pattern.compile("^$|[\\s]+");
        Matcher mat = pat.matcher(text);
        return mat.matches();
    }

    public static boolean isAPsw(String psw) {
        Matcher pass = Pattern.compile("^[\\w]{8}").matcher(psw);
        Matcher mins = Pattern.compile("^([A-Z0-9]*[a-z]+[A-Z0-9]*)*$").matcher(psw);
        Matcher mays = Pattern.compile("^([a-z0-9]*[A-Z]+[a-z0-9]*)*$").matcher(psw);
        Matcher nums = Pattern.compile("^([a-zA-Z]*[0-9]+[a-zA-Z]*)*$").matcher(psw);
        return pass.matches() && mins.matches() && mays.matches() && nums.matches();
    }
    
    public static boolean validNum(String num, int min, int max){
        Pattern pat = Pattern.compile("^[\\d]{"+min+","+max+"}");
        Matcher mat = pat.matcher(num);
        return mat.matches();
    }
}
