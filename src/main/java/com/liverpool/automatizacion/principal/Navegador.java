/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.principal;

import com.liverpool.automatizacion.util.Log;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author APEREZG03
 */
public class Navegador {
    
    String navegador;
    WebDriver driver;
    Properties entorno;
    Properties p;
    
    public Navegador(String navegador, Properties p, Properties entorno){
        this.navegador = navegador;
        this.p = p;
        this.entorno = entorno;
    }
    
    public WebDriver iniciarNavegador(){
        switch(navegador){
            case "Chrome":
                System.setProperty(p.getProperty(Const.CHROME_DRIVER),p.getProperty(Const.CHROME_PATH));
                driver = new ChromeDriver(); // Abre el navegador
                break;
            case "Mozilla Firefox":

                break;
            case "Internet Explorer":

                break;
            case "No Aplica":

                break;
        }
        configuracionTiempos();
        iniciarPagina();
        return driver;
    }
    
    public void configuracionTiempos(){
        driver.manage().timeouts().pageLoadTimeout(Long.parseLong(p.getProperty(Const.DRIVER_PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(Long.parseLong(p.getProperty(Const.DRIVER_SET_SCRIPT_TIMEOUT)), TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Long.parseLong(p.getProperty(Const.DRIVER_IMPLICITLY_WAIT)), TimeUnit.SECONDS);
    }
    
    public void seleccionCookies(){
        Cookie cookie = new Cookie(p.getProperty(Const.APP_COOKIE), p.getProperty(Const.APP_SITIO));
        driver.manage().deleteAllCookies();
        try{
            driver.manage().addCookie(cookie);
        } catch(Exception ex){
            Log.write("Ocurrio una excepcion al agregar la sig cookie:");
            Log.write("Cookie = {"+cookie.getName()+","+cookie.getValue()+"}");
            Log.write(ex.toString());
        }
    }
    
    public void iniciarPagina(){
        String ambience = entorno.getProperty(Entorno.URL);
        driver.manage().window().maximize();
        driver.get(ambience); // Abre la pagina en la pestaña actual
    }
}
