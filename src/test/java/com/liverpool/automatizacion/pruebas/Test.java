/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.pruebas;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iasancheza
 */
public class Test {
    
    public static void main(String[] args) {
        System.out.println("Inicia Test");
        File workspace = new File("tiendas");
        HashMap<File, ArrayList<File>> arbol = new HashMap<>();
        arbol.put(workspace, new ArrayList<>());
        try {
            Iterator<Path> paths = Files.walk(Paths.get(workspace.getName())).filter(Files::isDirectory).iterator();
            while(paths.hasNext()){
                File f = paths.next().toFile();
                //String name = f.getName();
                
                if(f.equals(workspace)){
                    continue;
                }
                
                //String parent = f.getParentFile().getName();
                File parent = f.getParentFile();
                
                ArrayList<File> list = arbol.get(parent);
                if(list == null){
                    list = new ArrayList<>();
                    arbol.put(parent, list);
                }
                list.add(f);
                System.out.println(f.getAbsolutePath());
            }
            System.out.println("Fin del hashmap");
            //Files.walk(Paths.get(workspace)).filter(Files::isDirectory).forEach(System.out::print); 
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }
}
