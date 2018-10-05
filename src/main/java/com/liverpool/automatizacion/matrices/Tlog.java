/*
 * Paso 0
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.modelo.Ticket;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.modelo.Guest;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.paginas.LivHome;
import com.liverpool.automatizacion.paginas.LivPDP;
import com.liverpool.automatizacion.paginas.Checkout_P0;
import com.liverpool.automatizacion.paginas.Checkout_P1;
import com.liverpool.automatizacion.paginas.Checkout_P2;
import com.liverpool.automatizacion.paginas.Checkout_P3;
import com.liverpool.automatizacion.paginas.PaypalSite;
import com.liverpool.automatizacion.paginas.Checkout_P4;
import com.liverpool.automatizacion.paginas.ThreeDSecure;
import com.liverpool.automatizacion.util.Excel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author aperezg03
 */
public class Tlog {

    private final WebDriver driver;
    private Interfaz interfaz;
    Login login;
    ArrayList<Sku> skus;
    Tienda tienda;
    Tarjeta tarjeta;
    Direccion direccionTar;
    Login loginPaypal;
    Guest guest;
    ArrayList<String> escenario = new ArrayList<>();
    ArrayList<ArrayList<String>> casos = new ArrayList<>();
    
    public String d; //Numero de tienda
    public String usuario, envio, direccion; //Tipo de usuario
    public String metodoPago;
    
    public Tlog (Interfaz interfaz, WebDriver driver, boolean excel){
        this.driver = driver;
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
            usuario = "Login";
            Excel excelArc = new Excel();
            casos=excelArc.getExcel(usuario);
        }
    }
    
    public void datosEscenarioExcel(int i){
        String [] tiendaE;
        String [] tarjetaFecha;
        String [] SKU;
        ArrayList<String> skuss = new ArrayList<>();
        
        escenario = casos.get(i);
        switch(usuario){
            case "Login":
                login = new Login(escenario.get(1), escenario.get(2));
                envio = escenario.get(4);
                direccion = escenario.get(6);
                tiendaE = escenario.get(5).split(",");
                if(tiendaE.length>1)
                    tienda = new Tienda(tiendaE[1],tiendaE[0]);
                metodoPago = escenario.get(7);
                tarjetaFecha=escenario.get(10).split("/");
                if(tarjetaFecha.length>1)
                    tarjeta = new Tarjeta(escenario.get(8), escenario.get(9), tarjetaFecha[0], tarjetaFecha[1]);
                else
                    tarjeta = new Tarjeta(escenario.get(8), escenario.get(9), "", "");
                loginPaypal = new Login(escenario.get(11),escenario.get(12));

                skuss.addAll(Arrays.asList(escenario.get(3).split("\\n")));
                skus = new ArrayList<Sku>();
                for(int j=0; j<skuss.size();j++){
                    SKU=skuss.get(j).split(",");
                    skus.add(new Sku(SKU[0], SKU[1]));
                }
                break;
            case "Guest":
                guest = new Guest(escenario.get(1), escenario.get(2), escenario.get(3), escenario.get(4), escenario.get(5), escenario.get(6));
                envio = escenario.get(8);
                direccion = escenario.get(8);
                tiendaE = escenario.get(7).split(",");
                if(tiendaE.length>1)
                    tienda = new Tienda(tiendaE[1],tiendaE[0]);
                metodoPago = escenario.get(9);
                tarjetaFecha=escenario.get(12).split("/");
                if(tarjetaFecha.length>1)
                    tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), tarjetaFecha[0], tarjetaFecha[1]);
                else
                    tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), "", "");
                loginPaypal = new Login(escenario.get(13),escenario.get(14));

                skuss.addAll(Arrays.asList(escenario.get(7).split("\\n")));
                skus = new ArrayList<Sku>();
                for(int j=0; j<skuss.size();j++){
                    SKU=skuss.get(j).split(",");
                    skus.add(new Sku(SKU[0], SKU[1]));
                }
                break;
        }
    }
    
    public void liverpool_TLOG(){
        boolean skuEncontrado;                                                                                  
        Ticket ticket;
        
        for(int e=1;e<casos.size();e++){
            datosEscenarioExcel(e);
            
            LivHome home = new LivHome(interfaz, driver, login);
            LivPDP pdp = new LivPDP(interfaz, driver);
            Checkout_P0 paso0 = new Checkout_P0(interfaz,driver);
            Checkout_P1 paso1 = new Checkout_P1(interfaz,driver, envio, tienda, direccion);
            Checkout_P2 paso2 = new Checkout_P2(interfaz,driver, tarjeta, direccionTar);
            Checkout_P3 paso3 = new Checkout_P3(interfaz,driver);
            PaypalSite paypal = new PaypalSite(interfaz, driver, loginPaypal);
            Checkout_P4 paso4 = new Checkout_P4(interfaz,driver);
            ThreeDSecure tds = new ThreeDSecure(interfaz,driver);
            
            if(usuario.equals("Login"))
                home.incioSesion();
            for(int i=0;i<skus.size();i++){
                skuEncontrado = home.buscarSKU(skus.get(i));
                if(skuEncontrado){
                    pdp.cantidadSKU(skus.get(i));
                    pdp.agregaraBolsa();
                }
            }
            pdp.irPaso0();
            //Login-Guest
            paso0.pasoCeroComprar();
            if(usuario.equals("Guest"))
                paso0.pasoCeroGuest();
            paso1.metodoEntrega();
            paso2.seleccionPago(metodoPago, usuario);
            paso3.terminarCompra();
            if(metodoPago.equals("Paypal"))
                paypal.paypalSandBox();
            tds.validacion3DS();
            
            ticket = paso4.extraccionDatos(metodoPago);
    //        Excel escritura = new Excel();
    //        escritura.writeExcel(ticket);
        }
    }
    
}
