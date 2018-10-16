/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.paginas.Checkout_P0;
import com.liverpool.automatizacion.paginas.LivHome;
import com.liverpool.automatizacion.paginas.LivPDP;
import com.liverpool.automatizacion.principal.Navegador;
import com.liverpool.automatizacion.util.Excel;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.vista.Interfaz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author IASANCHEZA
 */
public class MesaDeRegalosFueraLista extends Matriz {

    private Properties cart;
    private Properties shipping;
    private HashMap<String, ArrayList<String>> evento;
    ArrayList<Sku> skus;
    MesaRegaloFL numEv;
    Navegador browser;
    
    Tienda tienda;
    Tarjeta tarjeta;
    Direccion direccionTar;
    Login loginPaypal;
    ArrayList<ArrayList<String>> casos = new ArrayList<>();
    ArrayList<String> escenario = new ArrayList<>();
    public String usuario, metodoPago;
    boolean excel;
    

    private final Interfaz interfaz;

    public MesaDeRegalosFueraLista(Interfaz interfaz, Navegador browser, boolean excel) {
       
        this.login = login;
        this.tlog = tlog;
        this.browser = browser;
        this.interfaz = interfaz;
        
        if(!excel){
            skus = new ArrayList<Sku>(){{
    //            add(new Sku("67966758", "5"));
                add(new Sku("1028042848", "5"));
            }};
            //Datos a leer
            tienda = new Tienda("6","CDMX/ZONA METROPOLITANA");
            login = new Login("mpalfredo1@yahoo.com", "liverpool");
            tarjeta = new Tarjeta("NoesVISA", "123", "10", "2025"); 
    //        tarjeta = new Tarjeta("Master Card", "123", "10", "2025"); //wst
            direccionTar = new Direccion("56600","Chalco","Victoria","46","2","A",
                "Niños Heroes","Plateros","55","56570898","5578894556");
            usuario = "Guest";
    //        metodoPago = "Credito";
            metodoPago = "Paypal";
            metodoPago = "CIE";
            loginPaypal = new Login("compradorus@hotmail.com","Comprador1");
        }
        if(excel){
            usuario = "Guest-Fuera de lista";
            String nombreArchivo = "ComprasMesa.xlsx";
            Excel excelArc = new Excel(nombreArchivo);
            casos=excelArc.getExcel(usuario);
        }
        
        Log.write("Esto es lo que sucede..." + casos.get(0));
        Log.write("Lo que contiene el excel..." + casos.get(1));
        
        Log.write("Antes de SKUs ****************************************");
        skus = new ArrayList<Sku>() {
            {
//                Para WQA
//                add(new Sku("10000016731", "3"));
                add(new Sku("1044123068", "2"));
//                add(new Sku("1027486246", "5"));
//                Para WST:
//                add(new Sku("63933834", "3"));
            }
        };
//        Login para WQA
//        login = new Login("vgvelascod14@gmail.com", "12345678");
        login = new Login("iisancheze@liverpool.com.mx", "liverpool");
//      Login para WST 
//        login = new Login("vane.velasco1407@gmail.com", "12345678");
        Log.write("Login********   " + login.getUser());

    }
    
    public void datosEscenarioExcel(int i){
//        String [] tiendaE;
//        String [] tarjetaFecha;
//        String [] SKU;
//        ArrayList<String> skuss = new ArrayList<>();
        
        escenario = casos.get(i);
        
        switch(usuario){
            case "Login":
                inicioSesionMDRFL();
                break;
            case "Guest":
                guestMDRFL();
                break;
        }
    }
    
    public void inicioSesionMDRFL() {

        Log.write("InicioSesion");
        boolean skuEncontrado;
        String numEvEncontrado;

        LivHome home = new LivHome(interfaz, driver, login);

        Log.write("Despues de LiveHome");
        LivPDP pdp = new LivPDP(interfaz, driver);
        Checkout_P0 paso0 = new Checkout_P0(interfaz, driver);
        home.incioSesion();
        for (int i = 0; i < skus.size(); i++) {
            skuEncontrado = home.buscarSKU(skus.get(i));
            if (skuEncontrado) {
                pdp.cantidadSKU(skus.get(i));
                pdp.agregaraBolsa();
            }
        }
        pdp.irPaso0();
        numEv = new MesaRegaloFL();
        System.out.println("hola");
//        WQA:
        numEv.setNumEvento("36773699");
//        Productivo
//        numEv.setId("50009146");
//        WST:
//        numEv.setId("50010408");
        
        Log.write("Numero de evento ------------------------" + numEv.getNumEvento());
        numEvEncontrado = paso0.buscarNumeroEventoMRFL(numEv);
        Log.write("Numero encontrado ------------------------" + numEvEncontrado);
//        

//        paso0.pasoCeroFin();
    }
    
    
    
    public void guestMDRFL(){
        boolean skuEncontrado;
        String numEvEncontrado;
        
        driver = browser.iniciarNavegador();

        LivHome home = new LivHome(interfaz, driver, login);

        Log.write("Despues de LiveHome");
        LivPDP pdp = new LivPDP(interfaz, driver);
        Checkout_P0 paso0 = new Checkout_P0(interfaz, driver);
        
        for (int i = 0; i < skus.size(); i++) {
            skuEncontrado = home.buscarSKU(skus.get(i));
            if (skuEncontrado) {
                pdp.cantidadSKU(skus.get(i));
                pdp.agregaraBolsa();
            }
        }
        pdp.irPaso0();
        numEv = new MesaRegaloFL();
//        WQA:
        numEv.setNumEvento("36773699");
        //        Productivo
//        numEv.setNumEvento("50009146");
//        WST:
//        numEv.setId("50010408");
        
        Log.write("Numero de evento ------------------------" + numEv.getNumEvento());
        numEvEncontrado = paso0.buscarNumeroEventoMRFL(numEv);
        Log.write("Numero encontrado ------------------------" + numEvEncontrado);
    }
    
    
    
            private void pasoCero(){
                // Validar si el login es null
//        if(login != null){
//            // Iniciar sesion            
//        }

                // Validar si el login pudo iniciar sesion
            }


            @Override
            public void execute() {
//             inicioSesionMDRFL();

             guestMDRFL();
//                pasoCero();
            }
        }
