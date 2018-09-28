/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import com.liverpool.automatizacion.util.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author iasancheza
 */
public class Find {
    
    public static WebElement element(WebDriver driver, String nameElement){
        WebElement e = null;
        
        String[] data = nameElement.split("\\|");
        if(data.length != 2){
            return e;
        }
        
        // Evaluar la propiedad de busqueda
        switch(data[0]){
            case "class":
                return byClassName(driver, data[1]);
            case "id":
                return byId(driver, data[1]);
            case "css":
                return byCssSelector(driver, data[1]);
            case "link_test":
                return byLinkText(driver, data[1]);
            case "name":
                return byName(driver, data[1]);
            case "partial_link_test":
                return byPartialLinkTest(driver, data[1]);
            case "tag_name":
                return byTagName(driver, data[1]);
            case "xpath":
                return byXPath(driver, data[1]);
        }
        return e;
    }
    
    public static WebElement find(WebDriver driver, By by){
        WebElement e;
        
        try {
            e = driver.findElement(by);
        } catch (NoSuchElementException ex){
//            Log.write("Ocurrio una excepcion al recuperar el WebElement");
//            Log.write(ex.toString());
            e = null;
        }
        
        return e;
    }
    
    public static WebElement byClassName(WebDriver driver, String className){
        return find(driver, By.className(className));
    }
    
    public static WebElement byCssSelector(WebDriver driver, String cssSelector){
        return find(driver, By.cssSelector(cssSelector));
    }
    
    public static WebElement byId(WebDriver driver, String id){
        return find(driver, By.id(id));
    }
    
    public static WebElement byLinkText(WebDriver driver, String linkTest){
        return find(driver, By.linkText(linkTest));
    }
    
    public static WebElement byName(WebDriver driver, String name){
        return find(driver, By.name(name));
    }
    
    public static WebElement byPartialLinkTest(WebDriver driver, String partialLinkTest){
        return find(driver, By.partialLinkText(partialLinkTest));
    }
    
    public static WebElement byTagName(WebDriver driver, String tagName){
        return find(driver, By.tagName(tagName));
    }
    
    public static WebElement byXPath(WebDriver driver, String xpath){
        return find(driver, By.xpath(xpath));
    }
    
    public static WebDriver frame(WebDriver driver, WebElement element){
        WebDriver d;
        
        try {
            d = driver.switchTo().frame(element);
        } catch(NoSuchFrameException ex){
//            Log.write("Ocurrio una excepcion al cambiar de frame.");
//            Log.write(ex.toString());
            d = null;
        }
        
        return d;
    }
}
