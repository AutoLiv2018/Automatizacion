/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.paginas.Checkout_P0;
import com.liverpool.automatizacion.paginas.LivHome;
import com.liverpool.automatizacion.paginas.LivPDP;
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

    private final Interfaz interfaz;

    public MesaDeRegalosFueraLista(Interfaz interfaz, WebDriver driver, boolean tlog) {
       
        this.login = login;
        this.tlog = tlog;
        this.driver = driver;
        this.interfaz = interfaz;

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
//        WQA:
//        numEv.setId("36773699");
//        Productivo
        numEv.setId("50009146");
//        WST:
//        numEv.setId("50010408");
        
        Log.write("Numero de evento ------------------------" + numEv.getId());
        numEvEncontrado = paso0.buscarNumeroEventoMRFL(numEv);
        Log.write("Numero encontrado ------------------------" + numEvEncontrado);
//        

//        paso0.pasoCeroFin();
    }
    
    
    
    public void guestMDRFL(){
        boolean skuEncontrado;
        String numEvEncontrado;

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
//        numEv.setId("36773699");
        //        Productivo
        numEv.setId("50009146");
//        WST:
//        numEv.setId("50010408");
        
        Log.write("Numero de evento ------------------------" + numEv.getId());
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
