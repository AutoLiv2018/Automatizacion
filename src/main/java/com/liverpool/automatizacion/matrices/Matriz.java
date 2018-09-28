/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Validacion;
import java.util.ArrayList;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author IASANCHEZA
 */
public class Matriz {
    protected WebDriver driver;
    protected ArrayList<Validacion> validaciones;
    protected Properties entorno;
    protected boolean tlog;
    protected Login login;

    public Matriz() {
        this(null, new ArrayList<Validacion>(), new Properties(), new Login(), true);
    }

    public Matriz(WebDriver driver, ArrayList<Validacion> validaciones, Properties entorno, Login login, boolean tlog) {
        this.driver = driver;
        this.validaciones = validaciones;
        this.entorno = entorno;
        this.tlog = tlog;
    }
    
    public void execute(){
    
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public ArrayList<Validacion> getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(ArrayList<Validacion> validaciones) {
        this.validaciones = validaciones;
    }

    public Properties getEntorno() {
        return entorno;
    }

    public void setEntorno(Properties entorno) {
        this.entorno = entorno;
    }

    public boolean isTlog() {
        return tlog;
    }

    public void setTlog(boolean tlog) {
        this.tlog = tlog;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
