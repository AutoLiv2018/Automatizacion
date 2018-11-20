/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import java.util.List;
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
    
    public static List<WebElement> elements(WebElement element, String nameElements){
        List<WebElement> elements = null;
        
        String[] data = nameElements.split("\\|");
        if(data.length != 2){
            return elements;
        }
        
        switch(data[0]){
            case "class":
                return elmtsByClassName(element, data[1]);
            case "id":
                return elmtsById(element, data[1]);
            case "css":
                return elmtsByCssSelector(element, data[1]);
            case "link_test":
                return elmtsByLinkText(element, data[1]);
            case "name":
                return elmtsByName(element, data[1]);
            case "partial_link_test":
                return elmtsByPartialLinkTest(element, data[1]);
            case "tag_name":
                return elmtsByTagName(element, data[1]);
            case "xpath":
                return elmtsByXPath(element, data[1]);
        }
        return elements;
    }
    
    public static List<WebElement> elements(WebDriver driver, String nameElements){
        List<WebElement> elements = null;
        
        String[] data = nameElements.split("\\|");
        if(data.length != 2){
            return elements;
        }
        
        switch(data[0]){
            case "class":
                return elmtsByClassName(driver, data[1]);
            case "id":
                return elmtsById(driver, data[1]);
            case "css":
                return elmtsByCssSelector(driver, data[1]);
            case "link_test":
                return elmtsByLinkText(driver, data[1]);
            case "name":
                return elmtsByName(driver, data[1]);
            case "partial_link_test":
                return elmtsByPartialLinkTest(driver, data[1]);
            case "tag_name":
                return elmtsByTagName(driver, data[1]);
            case "xpath":
                return elmtsByXPath(driver, data[1]);
        }
        return elements;
    }
    
    public static WebElement element(WebElement element, String nameElement){
        WebElement e = null;
        
        String[] data = nameElement.split("\\|");
        if(data.length != 2){
            return e;
        }
        
        // Evaluar la propiedad de busqueda
        switch(data[0]){
            case "class":
                return byClassName(element, data[1]);
            case "id":
                return byId(element, data[1]);
            case "css":
                return byCssSelector(element, data[1]);
            case "link_test":
                return byLinkText(element, data[1]);
            case "name":
                return byName(element, data[1]);
            case "partial_link_test":
                return byPartialLinkTest(element, data[1]);
            case "tag_name":
                return byTagName(element, data[1]);
            case "xpath":
                return byXPath(element, data[1]);
        }
        return e;
    }
    
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
    
    public static WebElement find(WebElement element, By by){
        WebElement e;
        
        try {
            e = element.findElement(by);
        } catch (Exception ex){
            e = null;
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
    
    public static List<WebElement> elmtsByClassName(WebElement element, String className){
        return element.findElements(By.className(className));
    }
    
    public static List<WebElement> elmtsByClassName(WebDriver driver, String className){
        return driver.findElements(By.className(className));
    }
    
    public static WebElement byClassName(WebElement element, String className){
        return find(element, By.className(className));
    }
    
    public static WebElement byClassName(WebDriver driver, String className){
        return find(driver, By.className(className));
    }
    
    public static List<WebElement> elmtsByCssSelector(WebElement element, String cssSelector){
        return element.findElements(By.cssSelector(cssSelector));
    }
    
    public static List<WebElement> elmtsByCssSelector(WebDriver driver, String cssSelector){
        return driver.findElements(By.cssSelector(cssSelector));
    }
    
    public static WebElement byCssSelector(WebElement element, String cssSelector){
        return find(element, By.cssSelector(cssSelector));
    }
    
    public static WebElement byCssSelector(WebDriver driver, String cssSelector){
        return find(driver, By.cssSelector(cssSelector));
    }
    
    public static List<WebElement> elmtsById(WebElement element, String id){
        return element.findElements(By.id(id));
    }
    
    public static List<WebElement> elmtsById(WebDriver driver, String id){
        return driver.findElements(By.id(id));
    }
    
    public static WebElement byId(WebElement element, String id){
        return find(element, By.id(id));
    }
    
    public static WebElement byId(WebDriver driver, String id){
        return find(driver, By.id(id));
    }
    
    public static List<WebElement> elmtsByLinkText(WebElement element, String linkTest){
        return element.findElements(By.linkText(linkTest));
    }
    
    public static List<WebElement> elmtsByLinkText(WebDriver driver, String linkTest){
        return driver.findElements(By.linkText(linkTest));
    }
    
    public static WebElement byLinkText(WebElement element, String linkTest){
        return find(element, By.linkText(linkTest));
    }
    
    public static WebElement byLinkText(WebDriver driver, String linkTest){
        return find(driver, By.linkText(linkTest));
    }
    
    public static List<WebElement> elmtsByName(WebElement element, String name){
        return element.findElements(By.name(name));
    }
    
    public static List<WebElement> elmtsByName(WebDriver driver, String name){
        return driver.findElements(By.name(name));
    }
    
    public static WebElement byName(WebElement element, String name){
        return find(element, By.name(name));
    }
    
    public static WebElement byName(WebDriver driver, String name){
        return find(driver, By.name(name));
    }
    
    public static List<WebElement> elmtsByPartialLinkTest(WebElement element, String partialLinkTest){
        return element.findElements(By.partialLinkText(partialLinkTest));
    }
    
    public static List<WebElement> elmtsByPartialLinkTest(WebDriver driver, String partialLinkTest){
        return driver.findElements(By.partialLinkText(partialLinkTest));
    }
    
    public static WebElement byPartialLinkTest(WebElement element, String partialLinkTest){
        return find(element, By.partialLinkText(partialLinkTest));
    }
    
    public static WebElement byPartialLinkTest(WebDriver driver, String partialLinkTest){
        return find(driver, By.partialLinkText(partialLinkTest));
    }
    
    public static List<WebElement> elmtsByTagName(WebElement element, String tagName){
        return element.findElements(By.tagName(tagName));
    }
    
    public static List<WebElement> elmtsByTagName(WebDriver driver, String tagName){
        return driver.findElements(By.tagName(tagName));
    }
    
    public static WebElement byTagName(WebElement element, String tagName){
        return find(element, By.tagName(tagName));
    }
    
    public static WebElement byTagName(WebDriver driver, String tagName){
        return find(driver, By.tagName(tagName));
    }
    
    public static List<WebElement> elmtsByXPath(WebElement element, String xpath){
        return element.findElements(By.xpath(xpath));
    }
    
    public static List<WebElement> elmtsByXPath(WebDriver driver, String xpath){
        return driver.findElements(By.xpath(xpath));
    }
    
    public static WebElement byXPath(WebElement element, String xpath){
        return find(element, By.xpath(xpath));
    }
    
    public static WebElement byXPath(WebDriver driver, String xpath){
        return find(driver, By.xpath(xpath));
    }
    
    public static WebDriver frame(WebDriver driver, WebElement element){
        WebDriver d;
        
        try {
            d = driver.switchTo().frame(element);
        } catch(NoSuchFrameException ex){
            d = null;
        }
        
        return d;
    }
}