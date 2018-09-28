/*
 * Valores de la tarjeta para paso 2
 * 
 * 
 */
package com.liverpool.automatizacion.modelo;

/**
 *
 * @author aperezg03
 */
public class Tarjeta {
    
    private String tipo;
    private String nombreCorto;
    private String numero;
    private String nip;
    private String mes;
    private String anio;
    private String nomCliente;
    private String paternoCliente;
    private String maternoCliente;
    
    //Datos Login paso 2
    public Tarjeta (String nombreCorto, String nip, String mes, String anio){
        this.nombreCorto = nombreCorto;
        this.nip = nip;
        this.mes = mes;
        this.anio = anio;
    }
    
    //Datos Guest paso 2
    public Tarjeta (String tipo, String numero,String nip, String mes, String anio, String nomCliente, String paternoCliente, String maternoCliente){
        this.tipo = tipo;
        this.nip = nip;
        this.mes = mes;
        this.anio = anio;
        this.nomCliente = nomCliente;
        this.paternoCliente = paternoCliente;
        this.maternoCliente = maternoCliente;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public String getNombreCorto(){
        return nombreCorto;
    }
    
    public void setNombreCorto(String nombreCorto){
        this.nombreCorto = nombreCorto;
    }
    
    public String getNumero(){
        return numero;
    }
    
    public void setNumero(String numero){
        this.numero = numero;
    }
    
    public String getNip(){
        return nip;
    }
    
    public void setNip(String nip){
        this.nip = nip;
    }
    
    public String getMes(){
        return mes;
    }
    
    public void setMes(String mes){
        this.mes = mes;
    }
    
    public String getAnio(){
        return anio;
    }
    
    public void setAnio(String anio){
        this.anio = anio;
    }
    
    public String getNomCliente(){
        return nomCliente;
    }
    
    public void setNomCliente(String nomCliente){
        this.nomCliente = nomCliente;
    }
    
    public String getPaternoCliente(){
        return paternoCliente;
    }
    
    public void setPaternoCliente(String paternoCliente){
        this.paternoCliente = paternoCliente;
    }
    
    public String getMaternoCliente(){
        return maternoCliente;
    }
    
    public void setMaternoCliente(String maternoCliente){
        this.maternoCliente = maternoCliente;
    }
}
