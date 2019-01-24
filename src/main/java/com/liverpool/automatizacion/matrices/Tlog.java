/*
 * Paso 0
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.util.GuardarImagen;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.modelo.Ticket;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.modelo.Guest;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.paginas.LivHome;
import com.liverpool.automatizacion.paginas.LivPDP;
import com.liverpool.automatizacion.paginas.CheckoutP0;
import com.liverpool.automatizacion.paginas.CheckoutP1;
import com.liverpool.automatizacion.paginas.CheckoutP2;
import com.liverpool.automatizacion.paginas.CheckoutP3;
import com.liverpool.automatizacion.paginas.PaypalSite;
import com.liverpool.automatizacion.paginas.CheckoutP4;
import com.liverpool.automatizacion.paginas.ThreeDSecure;
import com.liverpool.automatizacion.principal.Navegador;
import com.liverpool.automatizacion.util.Excel;
import com.liverpool.automatizacion.util.Video;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author aperezg03
 */
public class Tlog {

    private WebDriver driver;
    Navegador browser;
    private Interfaz interfaz;
    Login login;
    ArrayList<Sku> skus;
    Tienda tienda;
    Tarjeta tarjeta;
    Direccion direccionTar;
    Direccion direccionGuest;
    Login loginPaypal;
    Guest guest;
    ArrayList<String> escenario = new ArrayList<>();
    ArrayList<ArrayList<String>> casos = new ArrayList<>();
    
    public String d; //Numero de tienda
    public String usuario, envio, direccion; //Tipo de usuario
    public String metodoPago;
    public String cupon;
    
    public Tlog (Interfaz interfaz, Navegador browser, boolean excel){
        this.interfaz = interfaz;
        this.browser = browser;
        
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
            Excel excelArc = new Excel("Compras.xlsx");
            casos=excelArc.getExcel(usuario);
        }
    }
    
    public void datosEscenarioExcel(int i){
        escenario = casos.get(i);
        switch(usuario){
            case "Login":
                datosPersonalLogin();
                break;
            case "Guest":
                datosPersonalGuest();
                break;
        }
    }
    
    public void liverpool_TLOG(){
        boolean skuEncontrado;                                                                                  
        List <Ticket> ticket = null;
        String folder = "D:\\Evidencia\\Compras\\";
        
        for(int e=1;e<casos.size();e++){
            datosEscenarioExcel(e);
            driver = browser.iniciarNavegador();
            
            Video video = new Video();
            video.iniciarVideo();
            
            LivHome home = new LivHome(interfaz, driver);
            LivPDP pdp = new LivPDP(interfaz, driver);
            CheckoutP0 paso0 = new CheckoutP0(interfaz,driver);
            CheckoutP1 paso1 = new CheckoutP1(interfaz,driver);
            CheckoutP2 paso2 = new CheckoutP2(interfaz,driver);
            CheckoutP3 paso3 = new CheckoutP3(interfaz,driver);
            PaypalSite paypal = new PaypalSite(interfaz, driver);
            CheckoutP4 paso4 = new CheckoutP4(interfaz,driver);
            ThreeDSecure tds = new ThreeDSecure(interfaz,driver);
            
            if(usuario.equals("Login"))
                home.incioSesion(login);
            for(int i=0;i<skus.size();i++){
                skuEncontrado = home.buscarSKU(skus.get(i));
                if(skuEncontrado){
                    pdp.cantidadSKU(skus.get(i));
                    pdp.agregaraBolsa();
                }
            }
            pdp.irPaso0();
            //Login-Guest Paso 0
            paso0.aplicarCupon(cupon);
            paso0.pasoCeroComprar();
            if(usuario.equals("Guest"))
                paso0.pasoCeroGuest();
            //Paso 1
            if(usuario.equals("Login"))
                paso1.envioLogin(envio, tienda, direccion);
            else
                paso1.envioGuest(envio, tienda, guest, direccionGuest);
            
            paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionGuest);
            paso3.terminarCompraTLOG();
            if(metodoPago.equals("Paypal"))
                paypal.paypalSandBox(loginPaypal);
            tds.validacion3DS();
            
            Excel escritura = new Excel("Resultado.xlsx");
            if(paso4.esperaTicket()){
                ticket = paso4.extraccionDatos(metodoPago);
                for(Ticket valores : ticket)
                    escritura.writeExcel(valores, escenario.get(0));
            }else
                escritura.writeExcel("No hay datos de la compra");
            GuardarImagen save = new GuardarImagen();
            save.guardarPantalla(escenario.get(0), folder, driver);
            
            video.detenerVideo();
        }
        JOptionPane.showMessageDialog(null, "Compras terminadas");
    }
    
    public void datosPersonalLogin(){
        String [] tiendaE;
        String [] tarjetaFecha;
        String [] SKU;
        ArrayList<String> skuss = new ArrayList<>();
        
        login = new Login(escenario.get(1), escenario.get(2));
        skuss.addAll(Arrays.asList(escenario.get(3).split("\\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size();j++){
            SKU=skuss.get(j).split(",");
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        cupon = escenario.get(4);
        envio = escenario.get(5);
        tiendaE = escenario.get(6).split(",");
        if(tiendaE.length>1)
            tienda = new Tienda(tiendaE[1],tiendaE[0]);
        direccion = escenario.get(7);
        metodoPago = escenario.get(8);
        tarjetaFecha=escenario.get(11).split("/");
        if(tarjetaFecha.length>1)
            tarjeta = new Tarjeta(escenario.get(9), escenario.get(10), tarjetaFecha[0], tarjetaFecha[1]);
        else
            tarjeta = new Tarjeta(escenario.get(9), escenario.get(10), "", "");
        loginPaypal = new Login(escenario.get(12),escenario.get(13));

    }
    
    public void datosPersonalGuest(){
        String [] tiendaE;
        String [] tarjetaFecha;
        String [] SKU;
        ArrayList<String> skuss = new ArrayList<>();
        
        guest = new Guest(escenario.get(1), escenario.get(2), escenario.get(3), escenario.get(4), escenario.get(5), escenario.get(6));
        skuss.addAll(Arrays.asList(escenario.get(7).split("\\n")));
        skus = new ArrayList<Sku>();
        for(int j=0; j<skuss.size();j++){
            SKU=skuss.get(j).split(",");
            skus.add(new Sku(SKU[0], SKU[1]));
        }
        cupon = escenario.get(8);
        envio = escenario.get(9);
        tiendaE = escenario.get(10).split(",");
        if(tiendaE.length>1)
            tienda = new Tienda(tiendaE[1],tiendaE[0]);
        direccionGuest = new Direccion(escenario.get(11),escenario.get(12),escenario.get(13),escenario.get(14),
            escenario.get(15),escenario.get(16),escenario.get(17),escenario.get(18),escenario.get(19),
            escenario.get(20),escenario.get(21));
        metodoPago = escenario.get(22);
        tarjetaFecha=escenario.get(26).split("/");
        if(tarjetaFecha.length>1)
            tarjeta = new Tarjeta(escenario.get(23), escenario.get(24), escenario.get(25), 
                    tarjetaFecha[0], tarjetaFecha[1], escenario.get(1), escenario.get(2), escenario.get(3));
        else
            tarjeta = new Tarjeta(escenario.get(23), escenario.get(24), escenario.get(25), 
                    "", "", escenario.get(1), escenario.get(2), escenario.get(3));
        loginPaypal = new Login(escenario.get(27),escenario.get(28));
    }
    
}
