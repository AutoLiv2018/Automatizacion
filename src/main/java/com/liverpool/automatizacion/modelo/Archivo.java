/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import java.io.File;
import java.net.URI;

/**
 *
 * @author iasancheza
 */
public class Archivo extends File {

    public Archivo(String pathname) {
        super(pathname);
    }

    public Archivo(String parent, String child) {
        super(parent, child);
    }

    public Archivo(File parent, String child) {
        super(parent, child);
    }

    public Archivo(URI uri) {
        super(uri);
    }

    @Override
    public String toString() {
        return this.getName().toUpperCase().replace("_", " ");
    }
}
