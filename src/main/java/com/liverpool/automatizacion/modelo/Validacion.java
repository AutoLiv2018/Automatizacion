/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

/**
 *
 * @author iasancheza
 */
public class Validacion {
    public static final String PASS = "PASS";
    public static final String FAIL = "FAIL";
    public static final String NOT_EXECUTED = "NOT EXECUTED";
    public static final String INVALID = "INVALID";
    public static final String BLOCKED = "BLOCKED";
    
    private String numCaso;
    private String comentario;
    private String resultado;

    public Validacion() {
        this("",NOT_EXECUTED, "");
    }

    public Validacion(String numCaso, String resultado, String comentario) {
        this.comentario = comentario;
        this.resultado = resultado;
    }
    
    public Validacion(String numCaso, boolean resultado, String comentario){
        this(resultado);
        this.numCaso = numCaso;
        this.comentario = comentario;
    }
    
    public Validacion(boolean resultado){
        this.resultado = resultado? PASS : FAIL;
        this.numCaso = "";
        this.comentario = "";
    }

    @Override
    public String toString() {
        return numCaso + "," + resultado + "," + comentario;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
