/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Ticket;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.CheckoutPaso4;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author aperezg03
 */
public class CheckoutP4 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso4;
    public final Properties Cpaso4;
    Ticket ticket;
    
    public CheckoutP4(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        ticket = new Ticket();

        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso4 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso4 = new File(folder, CheckoutPaso4.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso4.getAbsolutePath(), Cpaso4)){
            System.out.println("No se encontro el archivo: " + paso4);
        }
    }
    
    public boolean esperaTicket(){
        int a=0;
        Utils.sleep(500);
        while(!driver.getCurrentUrl().contains(Cpaso4.getProperty(CheckoutPaso4.URLTICKET))){
            Utils.sleep(500);
            a++;
            if(a==10)
                return false;
        }
        return true;
    }
    
    public List<Ticket> extraccionDatos(String metodoPago){
        System.out.println("paso 4");
        List <WebElement> gruposSkusCobrados, gruposSkusNoCobrados, grupoProductos, grupoProductosMesa,skuRenglon;
        List <Ticket> ticketValores = new ArrayList<>();
        List <WebElement> grupoP = new ArrayList<>();
        
        gruposSkusCobrados = Find.elements(driver, Cpaso4.getProperty(CheckoutPaso4.GRUPOSSKUSCOBRADOS));
        gruposSkusNoCobrados = Find.elements(driver, Cpaso4.getProperty(CheckoutPaso4.GRUPOSSKUSNOCOBRADOS));
        
        grupoProductos = Find.elements(gruposSkusCobrados.get(0), Cpaso4.getProperty(CheckoutPaso4.GRUPOPRODUCTOS));
        grupoProductosMesa = Find.elements(gruposSkusCobrados.get(0), Cpaso4.getProperty(CheckoutPaso4.GRUPOPRODUCTOSMESA));
        grupoP.addAll(grupoProductos);
        grupoP.addAll(grupoProductosMesa);
        
        //Datos generales
        cliente();
        correoCliente();
        fecha();
        calle();
        nExterior();
        nInterior();
        colonia();
        cp();
        delegacion();
        ciudad();
        tarjeta();
        nTarjeta();
        fechaTarjeta();
        precioTotal();
        referenciaCIE();
        referenciaOpenPay();
        
//        grupoProductos = Find.elements(gruposSkusCobrados.get(0), Cpaso4.getProperty(CheckoutPaso4.GRUPOPRODUCTOS));
        for(int i=0;i<grupoP.size();i++){
            facturacion(grupoP.get(i));
            boleta(grupoP.get(i));
            terminal(grupoP.get(i));
            pedido(grupoP.get(i));
            tienda(grupoP.get(i));
            if(!metodoPago.equals("Paypal"))
                autoBancaria(grupoP.get(i));
            else{
                folioPago(grupoP.get(i));
                folioPaypal(grupoP.get(i));
            }
            mesa(grupoP.get(i));
            festejado(grupoP.get(i));
            
            skuRenglon = Find.elements(grupoP.get(i), Cpaso4.getProperty(CheckoutPaso4.SKURENGLON));
            for(int j=0;j<skuRenglon.size();j++){
                sku(skuRenglon.get(j));
                cantidadSKU(skuRenglon.get(j));
                precioSKU(skuRenglon.get(j));
                ticketValores.add(obtenerDatos());
                ticket = new Ticket("",ticket.getCliente(),ticket.getCorreoCliente(),ticket.getFecha(),"","",ticket.getMesa(),
                    ticket.getFestejado(),ticket.getFacturacion(),ticket.getBoleta(),ticket.getTerminal(),ticket.getTienda(),
                    ticket.getPedido(),ticket.getAutoBancaria(),ticket.getFolioPago(),ticket.getFolioPaypal(),ticket.getPrecioTotal(),
                    ticket.getReferenciaCIE(),ticket.getReferenciaOpenPay(),ticket.getCalle(),ticket.getNExterior(),
                    ticket.getNInterior(),ticket.getColonia(),ticket.getCp(),ticket.getDelegacion(),ticket.getCiudad(),
                    ticket.getTarjeta(),ticket.getNTarjeta(),ticket.getFechaTarjeta());
            }
            ticket = new Ticket("",ticket.getCliente(),ticket.getCorreoCliente(),ticket.getFecha(),"","","",
                "","","","","",
                "","","","",ticket.getPrecioTotal(),
                ticket.getReferenciaCIE(),ticket.getReferenciaOpenPay(),ticket.getCalle(),ticket.getNExterior(),
                ticket.getNInterior(),ticket.getColonia(),ticket.getCp(),ticket.getDelegacion(),ticket.getCiudad(),
                ticket.getTarjeta(),ticket.getNTarjeta(),ticket.getFechaTarjeta());
        }
        return ticketValores;
    }
    
    public Ticket obtenerDatos(){
        return ticket;
    }
    
   public boolean cliente(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.CLIENTE))) != null){
            ticket.setCliente(element.getText());
            return true;
        }
        return false;
    }
   
    public boolean correoCliente(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.CORREOCLIENTE))) != null){
            ticket.setCorreoCliente(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean fecha(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.FECHA))) != null){
            ticket.setFecha(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean sku(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.SKU))) != null){
            ticket.setSku(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean cantidadSKU(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.CANTIDADSKU))) != null){
            ticket.setCantidadSKU(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean facturacion(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.FACTURACION))) != null){
            ticket.setFacturacion(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean boleta(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.BOLETA))) != null){
            ticket.setBoleta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean terminal(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.TERMINAL))) != null){
            ticket.setTerminal(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean tienda(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.TIENDA))) != null){
            ticket.setTienda(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean pedido(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.PEDIDO))) != null){
            ticket.setPedido(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean autoBancaria(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.AUTOBANCARIA))) != null){
            ticket.setAutoBancaria(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean precioSKU(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.PRECIOSKU))) != null){
            ticket.setPrecioSKU(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean calle(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.CALLE))) != null){
            ticket.setCalle(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean nExterior(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.NEXTERIOR))) != null){
            ticket.setNExterior(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean nInterior(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.NINTERIOR))) != null){
            ticket.setNInterior(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean colonia(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.COLONIA))) != null){
            ticket.setColonia(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean cp(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.CP))) != null){
            ticket.setCp(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean delegacion(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.DELEGACION))) != null){
            ticket.setDelegacion(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean ciudad(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.CIUDAD))) != null){
            ticket.setCiudad(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean folioPago(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.FOLIOPAGO))) != null){
            ticket.setFolioPago(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean folioPaypal(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.FOLIOPAYPAL))) != null){
            ticket.setFolioPaypal(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean tarjeta(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.TARJETA))) != null){
            ticket.setTarjeta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean nTarjeta(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.NTARJETA))) != null){
            ticket.setNTarjeta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean fechaTarjeta(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.FECHATARJETA))) != null){
            ticket.setFechaTarjeta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean precioTotal(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.PRECIOTOTAL))) != null){
            ticket.setPrecioTotal(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean referenciaCIE(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.REFENCIACIE))) != null){
            ticket.setReferenciaCIE(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean referenciaOpenPay(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.REFERENCIAOPENPAY))) != null){
            ticket.setReferenciaOpenPay(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean mesa(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.MESA))) != null){
            ticket.setMesa(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean festejado(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(CheckoutPaso4.FESTEJADO))) != null){
            ticket.setFestejado(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean clickLogoLiverpool(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(CheckoutPaso4.LOGOLIVERPOOL))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public void imprimirValores(){
        System.out.println("1 "+ticket.getCliente());
        System.out.println("2 "+ticket.getCorreoCliente());
        System.out.println("3 "+ticket.getFecha());
        System.out.println("4 "+ticket.getSku());
        System.out.println("5 "+ticket.getCantidadSKU());
        System.out.println("6 "+ticket.getFacturacion());
        System.out.println("7 "+ticket.getBoleta());
        System.out.println("8 "+ticket.getTerminal());
        System.out.println("9 "+ticket.getTienda());
        System.out.println("10 "+ticket.getPedido());
        System.out.println("11 "+ticket.getAutoBancaria());
        System.out.println("12 "+ticket.getPrecioSKU());
        System.out.println("13 "+ticket.getCalle());
        System.out.println("14 "+ticket.getNExterior());
        System.out.println("15 "+ticket.getNInterior());
        System.out.println("16 "+ticket.getColonia());
        System.out.println("17 "+ticket.getCp());
        System.out.println("18 "+ticket.getDelegacion());
        System.out.println("19 "+ticket.getCiudad());
        System.out.println("20 "+ticket.getFolioPago());
        System.out.println("21 "+ticket.getFolioPaypal());
        System.out.println("22 "+ticket.getTarjeta());
        System.out.println("23 "+ticket.getNTarjeta());
        System.out.println("24 "+ticket.getFechaTarjeta());
        System.out.println("25 "+ticket.getPrecioTotal());
        System.out.println("26 "+ticket.getReferenciaCIE());
        System.out.println("27 "+ticket.getReferenciaOpenPay());
        System.out.println("28 "+ticket.getMesa());
        System.out.println("29 "+ticket.getFestejado());
    }
}