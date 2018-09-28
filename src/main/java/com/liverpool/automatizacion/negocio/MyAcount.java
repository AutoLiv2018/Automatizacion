/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.negocio;

import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.util.DB;
import com.liverpool.automatizacion.util.Log;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author iasancheza
 */
public class MyAcount extends DB {
    
    public MyAcount(String clase, String url, String host, String port, String name, String usr, String psw) {
        super(clase, url, host, port, name, usr, psw);
    }

    public MyAcount(Properties p) {
        super(p);
    }
    
    public ArrayList<Sku> getSkus(String query){
        ArrayList<Sku> skus = new ArrayList<>();
        ArrayList<ArrayList<Object>> result = this.getListBy(query,new ArrayList<>());
        if(result.isEmpty()){
            return new ArrayList<>();
        }
        for(ArrayList<Object> datos : result){
            skus.add(new Sku(datos));
        }
        return skus;
    }
    
    private ArrayList<ArrayList<Object>> getListBy(String query, ArrayList<String> datos){
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        try {
            result = this.getList(query,datos);
        } catch (SQLException ex) {
            Log.write("Ocurrio una excepcion al ejecutar el sig. query: ");
            Log.write(query);
            Log.write(ex.toString());
            result = new ArrayList<>();
        }
        return result;
    }
    
    
}
