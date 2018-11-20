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
import com.liverpool.automatizacion.properties.Checkout_Paso4;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author aperezg03
 */
public class Checkout_P4 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso4;
    public final Properties Cpaso4;
    Ticket ticket;
    
    public Checkout_P4(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        ticket = new Ticket();

        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso4 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso4 = new File(folder, Checkout_Paso4.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso4.getAbsolutePath(), Cpaso4)){
            System.out.println("No se encontro el archivo: " + paso4);
        }
    }
    
    public boolean esperaTicket(){
        int a=0;
        Utils.sleep(500);
        while(!driver.getCurrentUrl().contains(Cpaso4.getProperty(Checkout_Paso4.URLTICKET))){
            Utils.sleep(500);
            a++;
            if(a==10)
                return false;
        }
        return true;
    }
    
    public List<Ticket> extraccionDatos(String metodoPago){
        System.out.println("paso 4");
        List <WebElement> skuCobrados, skuNoCobrados, skus;
        List <Ticket> ticketValores = null;
        skuCobrados = Find.elements(driver, Cpaso4.getProperty(Checkout_Paso4.));
        skuNoCobrados = Find.elements(driver, Cpaso4.getProperty(Checkout_Paso4.GRUPOSSKU));
        int i=0; int j=0;
        
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
        while(i<skuCobrados.size()){
            facturacion(grupos.get(i));
            boleta(grupos.get(i));
            terminal(grupos.get(i));
            tienda(grupos.get(i));
            pedido(grupos.get(i));
            if(!metodoPago.equals("Paypal"))
                autoBancaria(grupos.get(i));
            else{
                folioPago(grupos.get(i));
                folioPaypal(grupos.get(i));
            }
            mesa(grupos.get(i));
            festejado(grupos.get(i));
            skus = grupos.get(0).findElements(By.xpath("//div[@class='titles-row-prods']"));
            skus = Find.elements(grupos.get(i), Cpaso4.getProperty(Checkout_Paso4.SKURENGLON));
            while(j<skus.size()){
                sku(skus.get(j));
                cantidadSKU(skus.get(j));
                precioSKU(skus.get(j));
                j++;
                imprimirValores();
                ticketValores.add(obtenerDatos());
            }
            i++;
        }
        
        return ticketValores;
    }
    
    public Ticket obtenerDatos(){
        return ticket;
    }
    
   public boolean cliente(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.CLIENTE))) != null){
            ticket.setCliente(element.getText());
            return true;
        }
        return false;
    }
   
    public boolean correoCliente(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.CORREOCLIENTE))) != null){
            ticket.setCorreoCliente(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean fecha(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.FECHA))) != null){
            ticket.setFecha(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean sku(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.SKU))) != null){
            ticket.setSku(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean cantidadSKU(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.CANTIDADSKU))) != null){
            ticket.setCantidadSKU(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean facturacion(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.FACTURACION))) != null){
            ticket.setFacturacion(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean boleta(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.BOLETA))) != null){
            ticket.setBoleta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean terminal(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.TERMINAL))) != null){
            ticket.setTerminal(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean tienda(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.TIENDA))) != null){
            ticket.setTienda(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean pedido(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.PEDIDO))) != null){
            ticket.setPedido(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean autoBancaria(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.AUTOBANCARIA))) != null){
            ticket.setAutoBancaria(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean precioSKU(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.PRECIOSKU))) != null){
            ticket.setPrecioSKU(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean calle(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.CALLE))) != null){
            ticket.setCalle(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean nExterior(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.NEXTERIOR))) != null){
            ticket.setNExterior(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean nInterior(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.NINTERIOR))) != null){
            ticket.setNInterior(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean colonia(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.COLONIA))) != null){
            ticket.setColonia(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean cp(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.CP))) != null){
            ticket.setCp(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean delegacion(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.DELEGACION))) != null){
            ticket.setDelegacion(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean ciudad(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.CIUDAD))) != null){
            ticket.setCiudad(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean folioPago(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.FOLIOPAGO))) != null){
            ticket.setFolioPago(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean folioPaypal(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.FOLIOPAYPAL))) != null){
            ticket.setFolioPaypal(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean tarjeta(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.TARJETA))) != null){
            ticket.setTarjeta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean nTarjeta(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.NTARJETA))) != null){
            ticket.setNTarjeta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean fechaTarjeta(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.FECHATARJETA))) != null){
            ticket.setFechaTarjeta(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean precioTotal(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.PRECIOTOTAL))) != null){
            ticket.setPrecioTotal(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean referenciaCIE(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.REFENCIACIE))) != null){
            ticket.setReferenciaCIE(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean referenciaOpenPay(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.REFERENCIAOPENPAY))) != null){
            ticket.setReferenciaOpenPay(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean mesa(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.MESA))) != null){
            ticket.setMesa(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean festejado(WebElement padre){
        WebElement element;
        if((element = Find.element(padre, Cpaso4.getProperty(Checkout_Paso4.FESTEJADO))) != null){
            ticket.setFestejado(element.getText());
            return true;
        }
        return false;
    }
    
    public boolean clickLogoLiverpool(){
        WebElement element;
        if((element = Find.element(driver, Cpaso4.getProperty(Checkout_Paso4.LOGOLIVERPOOL))) != null){
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