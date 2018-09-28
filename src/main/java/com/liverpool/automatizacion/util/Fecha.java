/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author isancheza
 */
public class Fecha {
    
    public static String getSDate(Date date){
        DateFormat df = new SimpleDateFormat("ddMMyy");
        return df.format(date);
    }
    
    public static String getSDate(String date) throws ParseException{
        DateFormat df = new SimpleDateFormat("ddMMyy");
        Date d = df.parse(date);
        return df.format(d);
    }
    
    public static Date getDate(String date) throws ParseException{
        DateFormat df = new SimpleDateFormat("ddMMyy");
        Date d = df.parse(date);
        return d;
    }
    
    public static Date getBirthday(String birthday){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return df.parse(birthday);
        } catch (ParseException ex) {
            Log.write("Ocurrio una excepcion al parsear la fecha de cumpleaños del cliente:");
            Log.write(ex.toString());
        }
        return null;
    }
    
    public static Date expirationDate() throws ParseException{
        DateFormat df = new SimpleDateFormat("ddMMyy");
        return df.parse(expirationSDate());
    }
    
    public static String expirationSDate(){
        DateFormat df = new SimpleDateFormat("ddMMyy");
        Date dt = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_YEAR,90);
        return df.format(calendar.getTime());
    }
    
    public static Date today() throws ParseException{
        DateFormat df = new SimpleDateFormat("ddMMyy");
        return df.parse(sToday());
    }
    
    public static String sToday(){
        DateFormat df = new SimpleDateFormat("ddMMyy");
        Date dt = Calendar.getInstance().getTime();
        return df.format(dt);
    }
    
    public static String getFecha(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = Calendar.getInstance().getTime();
        return df.format(dt);
    }
    
    public static String getHora(){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dt = Calendar.getInstance().getTime();
        return df.format(dt);
    }
    
    public static boolean changePass(String expireDate){
        boolean success = false;
        try {
            Date today = today();
            Date expire = getDate(expireDate);
            // Validar si hay que cambiar el password
            success = today.compareTo(expire) >=0;
        } catch (ParseException ex) {
            Log.write(ex.toString());
            success = false;
        }
        return success;
    }
    
    // Validar un rango de 30 dias entre fechas
    public static boolean inRange(String initDate, String finDate) throws ParseException{
        Date init = getDate(initDate);
        Date fin = getDate(finDate);
        Date max = null; // limite superior (o inferior) segun sea el caso
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(init);
        
        // Validar si fin es mayor a init
        if(init.compareTo(fin) < 0){
            calendar.add(Calendar.DAY_OF_YEAR,30);
            max = calendar.getTime(); // Que tan mayor puede ser fin
            // Validar que fin sea menor o igual a max
            return fin.compareTo(max) <= 0;
        }
        else if(init.compareTo(fin) > 0){ // fin es menor a init
            calendar.add(Calendar.DAY_OF_YEAR,-30);
            max = calendar.getTime(); // Que tan menor puede ser fin
            // Validar que fin sea mayor o igual a max
            return fin.compareTo(max) >= 0;
        }
        // Son iguales
        return true;
    }
    
    public static Date initDate(String dfecha) throws ParseException{
        Date d = new Date();
        if(!dfecha.equals("")){
            DateFormat df = new SimpleDateFormat("MMdd"); 
            d = df.parse(dfecha); // Parsear dfecha
        } else {
            d = Calendar.getInstance().getTime(); // Tomar la fecha de hoy
        }
        Calendar c1 = Calendar.getInstance(); // Obtenemos el instante
        Calendar c2 = (Calendar)c1.clone(); // Clonamos para editar
        c2.setTime(d);
        c2.set(Calendar.YEAR, c1.get(Calendar.YEAR));
        c2.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY));
        c2.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
        c2.set(Calendar.SECOND, c1.get(Calendar.SECOND));
        return c2.getTime();
    }
    
    public static Date endDate(Date init, String endDate, String endTime) throws ParseException{
        Date finDate = null;
        if(!endDate.equals("")){
            DateFormat df = new SimpleDateFormat("ddMMyyHH:mm:ss");
            Date d = df.parse(endDate+endTime);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            finDate = c.getTime();
        } else {
            // Aumentar 1 dia a la fecha de init
            Calendar c = Calendar.getInstance();
            c.setTime(init);
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d = df.parse(endTime);
            Calendar c2 = (Calendar)c.clone();
            c2.setTime(d);
            c2.set(Calendar.YEAR, c.get(Calendar.YEAR));
            c2.set(Calendar.MONTH, c.get(Calendar.MONTH));
            c2.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR));
            c2.add(Calendar.DAY_OF_YEAR,1);
            finDate = c2.getTime();
        }
        return finDate;
    }
    
    public static String fileDate(Date initDate){
        DateFormat df = new SimpleDateFormat("MMdd00");
        return df.format(initDate);
    }
    
    public static String date() {
        DateFormat df = new SimpleDateFormat("ddMMyy");
        return df.format(Calendar.getInstance().getTime());
    }
    
    public static String bDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(Calendar.getInstance().getTime());
    }
    
    public static String yesteday(){
        DateFormat df = new SimpleDateFormat("ddMMyy");
        Calendar calendar = Calendar.getInstance();
        Date dt = calendar.getTime();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        return df.format(calendar.getTime());
    }
    
    public static String tomorrow(){
        DateFormat df = new SimpleDateFormat("ddMMyy");
        Calendar calendar = Calendar.getInstance();
        Date dt = calendar.getTime();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        return df.format(calendar.getTime());
    }
    
    public static String getPastDcol(int pastDays){
        return getPast(new SimpleDateFormat("MMdd00"), pastDays);
    }
    
    public static String getPastDate(int pastDays){
        return getPast(new SimpleDateFormat("ddMMyy"), pastDays);
    }
    
    public static String getPast(DateFormat df, int pastDays){
        Calendar calendar = Calendar.getInstance();
        Date dt = calendar.getTime();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_YEAR, -pastDays);
        dt = calendar.getTime();
        return df.format(dt);
    }
    
    public static String dateTimeFile(){
        DateFormat df = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");
        Date dt = Calendar.getInstance().getTime();
        return df.format(dt);
    }  
}
