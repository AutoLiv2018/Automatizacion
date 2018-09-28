/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

/**
 *
 * @author aperezg03
 */
public class Ticket {
    
    private String sku;
    private String cliente;
    private String correoCliente;
    private String fecha;
    private String cantidadSKU;
    private String precioSKU;
    private String mesa;
    private String festejado;
    private String facturacion;
    private String boleta;
    private String terminal;
    private String tienda;
    private String pedido;
    private String autoBancaria;
    private String folioPago;
    private String folioPaypal;
    private String PrecioTotal;
    private String referenciaCIE;
    private String calle;
    private String nExterior;
    private String nInterior;
    private String colonia;
    private String cp;
    private String delegacion;
    private String ciudad;
    private String tarjeta;
    private String nTarjeta;
    private String fechaTarjeta;
            
    public Ticket(){
        this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "");
    }
    
    public Ticket(String sku,String cliente,String correoCliente,String fecha,String cantidadSKU,
            String precioSKU,String mesa,String festejado,String facturacion,String boleta,String terminal,String tienda,
            String pedido,String autoBancaria,String folioPago,String folioPaypal,String PrecioTotal,
            String referenciaCIE,String calle,String nExterior,String nInterior,String colonia,
            String cp,String delegacion,String ciudad,String tarjeta,String nTarjeta,
            String fechaTarjeta){
        this.sku = sku;
        this.cliente = cliente;
        this.correoCliente = correoCliente;
        this.fecha = fecha;
        this.cantidadSKU = cantidadSKU;
        this.precioSKU = precioSKU;
        this.mesa = mesa;
        this.festejado = festejado;
        this.facturacion = facturacion;
        this.boleta = boleta;
        this.terminal = terminal;
        this.tienda = tienda;
        this.pedido = pedido;
        this.autoBancaria = autoBancaria;
        this.folioPago = folioPago;
        this.folioPaypal = folioPaypal;
        this.PrecioTotal = PrecioTotal;
        this.referenciaCIE = referenciaCIE;
        this.calle = calle;
        this.nExterior = nExterior;
        this.nInterior = nInterior;
        this.colonia = colonia;
        this.cp = cp;
        this.delegacion = delegacion;
        this.ciudad = ciudad;
        this.tarjeta = tarjeta;
        this.nTarjeta = nTarjeta;
        this.fechaTarjeta = fechaTarjeta;
    }
    
    public String getSku (){
        return sku;
    }
    
    public String getCliente (){
        return cliente;
    }
    
    public String getCorreoCliente (){
        return correoCliente;
    }
    
    public String getFecha (){
        return fecha;
    }
    
    public String getCantidadSKU (){
        return cantidadSKU;
    }

    public String getPrecioSKU (){
        return precioSKU;
    }
    
    public String getMesa (){
        return mesa;
    }
    
    public String getFestejado (){
        return festejado;
    }
    
    public String getFacturacion (){
        return facturacion;
    }
    
    public String getBoleta (){
        return boleta;
    }
    
    public String getTerminal (){
        return terminal;
    }
    
    public String getTienda (){
        return tienda;
    }
    
    public String getPedido (){
        return pedido;
    }
    
    public String getAutoBancaria (){
        return autoBancaria;
    }
    
    public String getFolioPago (){
        return folioPago;
    }
    
    public String getFolioPaypal (){
        return folioPaypal;
    }
    
    public String getPrecioTotal (){
        return PrecioTotal;
    }
    
    public String getReferenciaCIE (){
        return referenciaCIE;
    }
    
    public String getCalle (){
        return calle;
    }
    
    public String getNExterior (){
        return nExterior;
    }
    
    public String getNInterior (){
        return nInterior;
    }
    
    public String getColonia (){
        return colonia;
    }
    
    public String getCp (){
        return cp;
    }
    
    public String getDelegacion (){
        return delegacion;
    }
    
    public String getCiudad (){
        return ciudad;
    }
    
    public String getTarjeta (){
        return tarjeta;
    }
    
    public String getNTarjeta (){
        return nTarjeta;
    }
    
    public String getFechaTarjeta (){
        return fechaTarjeta;
    }

    public void setSku (String sku){
        this.sku = sku;
    }
    
    public void setCliente (String cliente){
        this.cliente = cliente;
    }
    
    public void setCorreoCliente (String correoCliente){
        this.correoCliente = correoCliente;
    }
    
    public void setFecha (String fecha){
        this.fecha = fecha;
    }
    
    public void setCantidadSKU (String cantidadSKU){
        this.cantidadSKU = cantidadSKU;
    }

    public void setPrecioSKU (String precioSKU){
        this.precioSKU = precioSKU;
    }
    
    public void setMesa (String mesa){
        this.mesa = mesa;
    }

    public void setFestejado (String festejado){
        this.festejado = festejado;
    }

    public void setFacturacion (String facturacion){
        this.facturacion = facturacion;
    }
    
    public void setBoleta (String boleta){
        this.boleta = boleta;
    }
    
    public void setTerminal (String terminal){
        this.terminal = terminal;
    }
    
    public void setTienda (String tienda){
        this.tienda = tienda;
    }
    
    public void setPedido (String pedido){
        this.pedido = pedido;
    }
    
    public void setAutoBancaria (String autoBancaria){
        this.autoBancaria = autoBancaria;
    }
    
    public void setFolioPago (String folioPago){
        this.folioPago = folioPago;
    }
    
    public void setFolioPaypal (String folioPaypal){
        this.folioPaypal = folioPaypal;
    }
    
    public void setPrecioTotal (String PrecioTotal){
        this.PrecioTotal = PrecioTotal;
    }
    
    public void setReferenciaCIE (String referenciaCIE){
        this.referenciaCIE = referenciaCIE;
    }
    
    public void setCalle (String calle){
        this.calle = calle;
    }
    
    public void setNExterior (String nExterior){
        this.nExterior = nExterior;
    }
    
    public void setNInterior (String nInterior){
        this.nInterior = nInterior;
    }
    
    public void setColonia (String colonia){
        this.colonia = colonia;
    }
    
    public void setCp (String cp){
        this.cp = cp;
    }
    
    public void setDelegacion (String delegacion){
        this.delegacion = delegacion;
    }
    
    public void setCiudad (String ciudad){
        this.ciudad = ciudad;
    }
    
    public void setTarjeta (String tarjeta){
        this.tarjeta = tarjeta;
    }
    
    public void setNTarjeta (String nTarjeta){
        this.nTarjeta = nTarjeta;
    }
    
    public void setFechaTarjeta (String fechaTarjeta){
        this.fechaTarjeta = fechaTarjeta;
    }

}
