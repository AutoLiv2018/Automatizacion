/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import java.util.List;
import org.openqa.selenium.WebElement;

/**
 *
 * @author iasancheza
 */
public class Pasos {
    public static final int PASO_1 = 1;
    public static final int PASO_2 = 2;
    public static final int PASO_3 = 3;
            
    private int numPasos;
    private WebElement paso1;
    private WebElement paso2;
    private WebElement paso3;

    public Pasos() {
        this.numPasos = 0;
        this.paso1 = null;
        this.paso2 = null;
        this.paso3 = null;
    }
    
    public Pasos(List<WebElement> pasos){
        this.numPasos = pasos.size();
        for(int i=0; i < pasos.size(); i++){
            WebElement paso = pasos.get(i);
            switch(i){
                case 0: // paso 1
                    this.paso1 = paso;
                    break;
                case 1: // paso 2
                    this.paso2 = paso;
                    break;
                case 2: // paso 3
                    this.paso3 = paso;
                    break;
            }
        }
    }

    public int getNumPasos() {
        return numPasos;
    }

    public void setNumPasos(int numPasos) {
        this.numPasos = numPasos;
    }

    public WebElement getPaso1() {
        return paso1;
    }

    public void setPaso1(WebElement paso1) {
        this.paso1 = paso1;
    }

    public WebElement getPaso2() {
        return paso2;
    }

    public void setPaso2(WebElement paso2) {
        this.paso2 = paso2;
    }

    public WebElement getPaso3() {
        return paso3;
    }

    public void setPaso3(WebElement paso3) {
        this.paso3 = paso3;
    }
}
