/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Guest;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.modelo.Ticket;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.modelo.Promocion;
=======
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.paginas.Checkout_P0;
import com.liverpool.automatizacion.paginas.Checkout_P1;
import com.liverpool.automatizacion.paginas.Checkout_P2;
import com.liverpool.automatizacion.paginas.Checkout_P3;
import com.liverpool.automatizacion.paginas.Checkout_P4;
import com.liverpool.automatizacion.paginas.LivHome;
import com.liverpool.automatizacion.paginas.LivPDP;
import com.liverpool.automatizacion.paginas.MesaRegalos;
import com.liverpool.automatizacion.paginas.PaypalSite;
import com.liverpool.automatizacion.paginas.ThreeDSecure;
import com.liverpool.automatizacion.principal.Navegador;
import com.liverpool.automatizacion.util.Excel;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import javax.json.JsonObject;
import javax.swing.JOptionPane;
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
    MesaRegaloFL mesaRegalo;
    Navegador browser;

















    Tienda tienda;
    Tarjeta tarjeta;
    Direccion direccionTar;
    Login login;
    Login loginPaypal;
    Guest guest;
    Sku sku;
    Promocion promocion;
    ArrayList<ArrayList<String>> casos = new ArrayList<>();
    ArrayList<String> escenario = new ArrayList<>();
    public String usuario, metodoPago, compra, cupones;
    boolean excel;
    String [] sku_cantidad;
    String [] fechaTarjeta;
    String [] promo;
    
    ArrayList<String> skuss = new ArrayList<>();
    String [] SKU;

















    ArrayList<String> skuss = new ArrayList<>();
    String [] SKU;

    private final Interfaz interfaz;

    public MesaDeRegalosFueraLista(Interfaz interfaz, Navegador browser, boolean excel) {
<<<<<<< Upstream, based on origin/master
       
        this.login = login;
        this.tlog = tlog;
        this.browser = browser;
        this.interfaz = interfaz;
        
        if(!excel){
            skus = new ArrayList<Sku>(){{
    //            add(new Sku("67966758", "5"));
=======

        this.login = login;
        this.tlog = tlog;
        this.browser = browser;
        this.interfaz = interfaz;
        
        if(!excel){
            skus = new ArrayList<Sku>(){{
    //            add(new Sku("67966758", "5"));
>>>>>>> 83a00d8 
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
//        login = new Login("iisancheze@liverpool.com.mx", "liverpool");
//      Login para WST 
//        login = new Login("vane.velasco1407@gmail.com", "12345678");
        Log.write("Login********   " + login.getUser());

*/    }
        
    public void datosEscenarioExcel(int i){
//        String [] tiendaE;
//        String [] tarjetaFecha;
//        String [] SKU;
//        ArrayList<String> skuss = new ArrayList<>();

        escenario = casos.get(i);
        
        switch(usuario){
            case "Login-Dentro de lista":
                loginDentroDeLista();
                break;
            case "Guest-Dentro de lista":
                guestDentroDeLista();
                break;
            case "Login-Fuera de lista":
                loginFueraDeLista();
                break;
            case "Guest-Fuera de lista":
                guestFueraDeLista();
                break;
            case "Festejado-Fuera de lista":
                festejadoFueraDeLista();
                break;
        }
    }
    
    public void loginDentroDeLista(){
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        mesaRegalo = new MesaRegaloFL(escenario.get(3), escenario.get(4), escenario.get(5));
        
        skuss.addAll(Arrays.asList(escenario.get(6).split("\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size(); j++){
            SKU = skuss.get(j).split(", ");
            System.out.println(SKU[0]+" "+SKU[1]);
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        
        cupones = escenario.get(7);
        promo = escenario.get(8).split(", ");
        if(promo.length>1)
            promocion = new Promocion(promo[0], promo[1]);
        else 
            promocion = new Promocion(promo[1]);
        
        metodoPago = escenario.get(9);
        fechaTarjeta = escenario.get(12).split("/");
        if(fechaTarjeta.length>1)
            tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), fechaTarjeta[0], fechaTarjeta[1]);
        loginPaypal = new Login(escenario.get(13), escenario.get(14));
    }
    
    public void guestDentroDeLista(){
        compra = escenario.get(0);
        guest = new Guest(escenario.get(1),escenario.get(2),escenario.get(3),
                escenario.get(4),escenario.get(5),escenario.get(6));
        mesaRegalo = new MesaRegaloFL(escenario.get(7),escenario.get(8),escenario.get(9));
        
        skuss.addAll(Arrays.asList(escenario.get(10).split("\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size(); j++){
            SKU = skuss.get(j).split(", ");
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        
        cupones = escenario.get(11);
        promo = escenario.get(12).split(", ");
        if(promo.length>1)
            promocion = new Promocion(promo[0], promo[1]);
        else 
            promocion = new Promocion(promo[1]);
        
        direccionTar = new Direccion(escenario.get(13),escenario.get(14),escenario.get(15),escenario.get(16),
                escenario.get(17),escenario.get(18),escenario.get(19),escenario.get(20),escenario.get(21),
                escenario.get(22),escenario.get(23));
        metodoPago = escenario.get(24);
        fechaTarjeta = escenario.get(28).split("/");
        if(fechaTarjeta.length>1)
            tarjeta = new Tarjeta(escenario.get(25), escenario.get(26), escenario.get(27),
                    fechaTarjeta[0], fechaTarjeta[1], escenario.get(1), escenario.get(2), escenario.get(3));
        loginPaypal = new Login(escenario.get(29), escenario.get(30));
    }
    
    public void loginFueraDeLista(){
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        mesaRegalo = new MesaRegaloFL(escenario.get(3), escenario.get(4), escenario.get(5));
        
        skuss.addAll(Arrays.asList(escenario.get(6).split("\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size(); j++){
            SKU = skuss.get(j).split(", ");
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        
        cupones = escenario.get(7);
        promo = escenario.get(8).split(", ");
        if(promo.length>1)
            promocion = new Promocion(promo[0], promo[1]);
        else 
            promocion = new Promocion(promo[1]);
        
        metodoPago = escenario.get(9);
        fechaTarjeta = escenario.get(12).split("/");
        if(fechaTarjeta.length>1)
            tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), fechaTarjeta[0], fechaTarjeta[1]);
        loginPaypal = new Login(escenario.get(13), escenario.get(14));
        Log.write("Numero de evento dentro login... " + mesaRegalo.getNumEvento());
    }
    
    public void guestFueraDeLista(){
        compra = escenario.get(0);
        guest = new Guest(escenario.get(1),escenario.get(2),escenario.get(3),
                escenario.get(4),escenario.get(5),escenario.get(6));
        mesaRegalo = new MesaRegaloFL(escenario.get(7),escenario.get(8),escenario.get(9));
        
        skuss.addAll(Arrays.asList(escenario.get(10).split("\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size(); j++){
            SKU = skuss.get(j).split(", ");
            System.out.println(SKU[0]+" "+SKU[1]);
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        
        cupones = escenario.get(11);
        promo = escenario.get(12).split(", ");
        if(promo.length>1)
            promocion = new Promocion(promo[0], promo[1]);
        else 
            promocion = new Promocion(promo[1]);
        
        direccionTar = new Direccion(escenario.get(13),escenario.get(14),escenario.get(15),escenario.get(16),
                escenario.get(17),escenario.get(18),escenario.get(19),escenario.get(20),escenario.get(21),
                escenario.get(22),escenario.get(23));
        metodoPago = escenario.get(24);
        fechaTarjeta = escenario.get(28).split("/");
        if(fechaTarjeta.length>1)
            tarjeta = new Tarjeta(escenario.get(25), escenario.get(26), escenario.get(27),
                    fechaTarjeta[0], fechaTarjeta[1], escenario.get(1), escenario.get(2), escenario.get(3));
        loginPaypal = new Login(escenario.get(29), escenario.get(30));
        
        Log.write("Numero de evento dentro guest + ------------------------" + mesaRegalo.getNumEvento());
        
    }
    
    public void festejadoFueraDeLista(){
        String [] tiendaCC;
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        mesaRegalo = new MesaRegaloFL(escenario.get(3));
        
        skuss.addAll(Arrays.asList(escenario.get(4).split("\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size(); j++){
            SKU = skuss.get(j).split(", ");
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        
        cupones = escenario.get(5);
        promo = escenario.get(6).split(", ");
        if(promo.length>1)
            promocion = new Promocion(promo[0], promo[1]);
        else 
            promocion = new Promocion(promo[1]);
        
        tiendaCC = escenario.get(7).split(",");
        if(tiendaCC.length>1)
            tienda = new Tienda(tiendaCC[1], tiendaCC[0]);
        direccionTar = new Direccion(escenario.get(8));
        metodoPago = escenario.get(9);
        fechaTarjeta = escenario.get(12).split("/");
        if(fechaTarjeta.length>1)
            tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), fechaTarjeta[0], fechaTarjeta[0]);
        loginPaypal = new Login(escenario.get(13), escenario.get(14));
    }

    public void mesaRegalos() {

        boolean skuEncontrado;
        String numEvEncontrado;
//        mesaRegalo = new MesaRegaloFL();
        
        for(int e=1; e<casos.size(); e++){
            datosEscenarioExcel(e);

            driver = browser.iniciarNavegador();

            LivHome home = new LivHome(interfaz, driver, login);

            Log.write("Despues de LiveHome");
            Utils.sleep(2000);
            LivPDP pdp = new LivPDP(interfaz, driver);
            Checkout_P0 paso0 = new Checkout_P0(interfaz, driver);
            MesaRegalos mesa = new MesaRegalos(interfaz, driver);


            //        WQA:
//            numEv.setNumEvento("36773699");
            //        Productivo
//            numEv.setNumEvento("50009146");
//            WST:
//            numEv.setId("50010408");

            if (usuario.equals("Login-Fuera de lista")) {
                home.incioSesion();
                for (int i = 0; i < skus.size(); i++) {
                    skuEncontrado = home.buscarSKU(skus.get(i));
                    if (skuEncontrado) {
                        pdp.cantidadSKU(skus.get(i));
                        pdp.agregaraBolsa();
                    }
                }
                pdp.irPaso0();
                Log.write("Numero de evento ------------------------" + mesaRegalo.getNumEvento());
                numEvEncontrado = paso0.buscarNumeroEventoMRFL(mesaRegalo);
                Log.write("Numero encontrado ------------------------" + numEvEncontrado);

            } else if (usuario.equals("Guest-Fuera de lista")) {
                for (int i = 0; i < skus.size(); i++) {
                    skuEncontrado = home.buscarSKU(skus.get(i));
                    if (skuEncontrado) {
                        pdp.cantidadSKU(skus.get(i));
                        pdp.agregaraBolsa();
                    }
                }
                pdp.irPaso0();
                Log.write("Numero de evento ------------------------" + mesaRegalo.getNumEvento());
                numEvEncontrado = paso0.buscarNumeroEventoMRFL(mesaRegalo);
                Log.write("Numero encontrado ------------------------" + numEvEncontrado);

            }else if (usuario.equals("Login-Dentro de lista")){
                home.incioSesion();
                Log.write("Usuario ------------------------" + usuario);
                Log.write("N E ------------------------" + mesaRegalo);
                numEvEncontrado = mesa.compraPersonalDentroDeLista(mesaRegalo,login);
                Log.write("Antes de entrar a SKU  ------------------------" + numEvEncontrado);
                Log.write("Antes de entrar a SKU  ------------------------" + skus.get(0));
    //            mesa.seleccionaSKU(skus,numEv);
                for (int i = 0; i < skus.size(); i++) {
                    Log.write("entra for SKU  ------------------------" + skus.get(i));
                   Utils.sleep(2000);
                   skuEncontrado = mesa.seleccionaSKU(skus.get(i));
                   Log.write("Antes de entrar a SKU  ------------------------" + skuEncontrado);
                   if (skuEncontrado) {
    //                   mesa.cantidad(skus.get(i));
                       mesa.agregaBolsa(mesaRegalo);
    //                   mesa.addGiftToCart(numEv);
                   }
                }

            }else if (usuario.equals("Guest-Dentro de lista")){

    //            numEvEncontrado = mesa.compraGuestDentroDeLista(mesaRegalo);
                numEvEncontrado = mesa.compraPersonalDentroDeLista(mesaRegalo, login);
    //            mesa.seleccionaSKU(skus,numEv);

            }

        }

*/    }
        
    public void datosEscenarioExcel(int i){
//        String [] tiendaE;
//        String [] tarjetaFecha;
//        String [] SKU;
//        ArrayList<String> skuss = new ArrayList<>();
        
        escenario = casos.get(i);
        
        switch(usuario){
            case "Login-Dentro de lista":
                loginDentroDeLista();
                break;
            case "Guest-Dentro de lista":
                guestDentroDeLista();
                break;
            case "Login-Fuera de lista":
                loginFueraDeLista();
                break;
            case "Guest-Fuera de lista":
                guestFueraDeLista();
                break;
            case "Festejado-Fuera de lista":
                festejadoFueraDeLista();
                break;
        }
    }
    
    public void inicioSesionMDRFL() {

        Log.write("InicioSesion");
        boolean skuEncontrado;
        String numEvEncontrado;
        
        for(int e=1; e<casos.size(); e++){
            datosEscenarioExcel(e);
            
            driver = browser.iniciarNavegador();

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
//            mesaRegalo = new MesaRegaloFL();
//            System.out.println("hola");
//          WQA:
//            numEv.setNumEvento("36773699");
//          Productivo
//          numEv.setId("50009146");
//          WST:
//          numEv.setId("50010408");
        
            Log.write("Numero de evento ------------------------//" + mesaRegalo.getNumEvento());
            numEvEncontrado = paso0.buscarNumeroEventoMRFL(mesaRegalo);
            Log.write("Numero encontrado ------------------------" + numEvEncontrado);
//        
//          paso0.pasoCeroFin();
        }
    }

    public void guestMDRFL() {
        boolean skuEncontrado;
        String numEvEncontrado;
        
        for(int e=1; e<casos.size(); e++){
            datosEscenarioExcel(e);
        
        
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
//          numEv = new MesaRegaloFL();
//          WQA:
//          numEv.setNumEvento("36773699");
//          Productivo
//          numEv.setNumEvento("50009146");
//          WST:
//          numEv.setId("50010408");

            Log.write("Numero de evento ------------------------" );
            Log.write("Numero de evento ------------------------" + mesaRegalo.getNumEvento());
            numEvEncontrado = paso0.buscarNumeroEventoMRFL(mesaRegalo);
            Log.write("Numero encontrado ------------------------" + numEvEncontrado);
        }
        
    }

    private void pasoCero() {
        // Validar si el login es null
//        if(login != null){
//            // Iniciar sesion            
//        }

        // Validar si el login pudo iniciar sesion
    }

    @Override
    public void execute() {
//             inicioSesionMDRFL();
        mesaRegalos();
//             guestMDRFL();
//                pasoCero();
    }
}
