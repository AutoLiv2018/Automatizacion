/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author isancheza
 */
public class DB {
    public static final String DB_CLASE = "db.clase";
    public static final String DB_URL = "db.url";
    public static final String DB_HOST = "db.host";
    public static final String DB_PORT = "db.port";
    public static final String DB_NAME = "db.name";
    public static final String DB_USR = "db.usr";
    public static final String DB_PSW = "db.psw";
    
    protected String clase;
    protected String url;
    protected String host;
    protected String port;
    protected String name;
    protected String usr;
    protected String psw;
    
    protected static final int MAX_SIZE = 1000;
    
    public DB(Properties p){
        this.clase = p.getProperty(DB_CLASE);
        this.url = p.getProperty(DB_URL);
        this.host = p.getProperty(DB_HOST);
        this.port = p.getProperty(DB_PORT);
        this.name = p.getProperty(DB_NAME);
        this.usr = p.getProperty(DB_USR);
        this.psw = p.getProperty(DB_PSW);
    }

    public DB(String clase, String url, String host, String port, String name, String usr, String psw) {
        this.clase = clase;
        this.url = url;
        this.host = host;
        this.port = port;
        this.name = name;
        this.usr = usr;
        this.psw = psw;
    }
    
    public ArrayList<ArrayList<String>> partList(ArrayList<String> list) {
        ArrayList<ArrayList<String>> total = new ArrayList<ArrayList<String>>();
        ArrayList<String> arr = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) { // Para recorrer toda la lista
            arr.add(list.get(i));
            if (arr.size() == MAX_SIZE) {
                total.add(arr);
                arr = new ArrayList<String>();
            }
        }
        total.add(arr); // Agregar la ultima sublista menor a 1000
        return total;
    }
    
    public Connection getConecction() throws SQLException{
        Connection conn = null;
        try{
            Class.forName(clase).newInstance();
            //String cadena = url+host+":"+port+"/"+name;
            String cadena = url.replace(DB_HOST, host).replace(DB_PORT, port).replace(DB_NAME, name);
            conn = DriverManager.getConnection(cadena,usr,psw);
            conn.setAutoCommit(false);
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            Log.write("Ocurrio una excepcion al conectar con la base de datos: ");
            Log.write(ex.toString());
        }
        return conn;
    }
    
    public boolean isALive(Connection conn) throws SQLException{
        return conn != null && !conn.isClosed();
    }
    
    public void close(Connection conn){
        try {
            if(isALive(conn)) conn.close();
        } catch (SQLException e) {
            Log.write("Ocurrio una excepcion al cerrar la conexion con la DB: ");
            Log.write(e.toString());
        }
    }
    
    protected boolean executeUpdate(String sql, ArrayList<String> data) throws SQLException{
        boolean result = false;
        Connection conn = getConecction();
        if(isALive(conn)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            for(int i=0; i<data.size(); i++){
                stmt.setString(i+1, data.get(i));
            }
            if(stmt.executeUpdate() > 0){
                conn.commit();
                result = true;
            }
            stmt.close();
            conn.close();
        }
        return result;
    }
    
    protected boolean executeUpdate(String sql, String[] data) throws SQLException{
        boolean result = false;
        Connection conn = getConecction();
        if(isALive(conn)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            for(int i=0; i<data.length; i++){
                stmt.setString(i+1, data[i]);
            }
            if(stmt.executeUpdate() > 0){
                conn.commit();
                result = true;
            }
            stmt.close();
            conn.close();
        }
        return result;
    }
    
    // Para obtener listas, ordenaciones, uniones, etc
    protected ArrayList<ArrayList<Object>> getList(String query, int data) throws SQLException{
        ArrayList<ArrayList<Object>> arr = null;
        Connection conn = getConecction();
        if(isALive(conn)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            arr = new ArrayList<>();
            while(rs.next()){
                ArrayList<Object> obj = new ArrayList<Object>();
                for(int i=1; i <= data; i++){
                    obj.add(rs.getObject(i));
                }
                arr.add(obj);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        return arr;
    }
    
    protected ArrayList<ArrayList<Object>> getList(String query, ArrayList<String> data) throws SQLException{
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();
        Connection conn = getConecction();
        if(isALive(conn)){
            PreparedStatement stmt = conn.prepareStatement(query);
            for(int i=0; i<data.size(); i++){
                stmt.setString(i+1, data.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            arr = new ArrayList<>();
            while(rs.next()){
                ArrayList<Object> obj = new ArrayList<>();
                ResultSetMetaData metadata = rs.getMetaData();
                for(int i=1; i <= metadata.getColumnCount(); i++){
                    obj.add(rs.getObject(i));
                }
                arr.add(obj);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        return arr;
    }
    
    protected ArrayList<Object> getFirst(String sql, ArrayList<String> data) throws SQLException{
        ArrayList<Object> array = new ArrayList<>();
        Connection conn = getConecction();
        if(isALive(conn)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            for(int i=0; i<data.size(); i++){
                stmt.setString(i+1, data.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ResultSetMetaData metadata = rs.getMetaData();
                for(int i=1; i <= metadata.getColumnCount(); i++){
                    array.add(i-1,rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        return array;
    }
    
    protected ArrayList<Object> getFirst(String query) throws SQLException{
        ArrayList<Object> array = new ArrayList<>();
        Connection conn = getConecction();
        if(isALive(conn)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                ResultSetMetaData metadata = rs.getMetaData();
                for(int i=1; i <= metadata.getColumnCount(); i++){
                    array.add(i-1,rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        return array;
    }
    
    public void printAll(String query) throws SQLException {
        Connection conn = getConecction();
        if (isALive(conn)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ResultSetMetaData metadata = rs.getMetaData();
                for(int i=1; i <= metadata.getColumnCount(); i++){
                    System.out.print(rs.getObject(i)+" ");
                }
                System.out.println("");
            }
            rs.close();
            stmt.close();
            conn.close();
        }
    }
    
    public BigDecimal max(String query) throws SQLException{
        BigDecimal max = null;
        Connection conn = getConecction();
        if (isALive(conn)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                max = rs.getBigDecimal(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        return max;
    }
    
    protected ArrayList<String> stringArray(ArrayList<Object> obj){
        ArrayList<String> str = new ArrayList<String>();
        for(Object o : obj){
            String s="";
            if(o==null){
                s="";
            } else if(o instanceof BigDecimal){
                s=String.valueOf(((BigDecimal)o).toString());
            } else if(o instanceof Integer){
                s=String.valueOf(o);
            } else if(o instanceof String){
                s=(String)o;
            }
            str.add(s);
        }
        return str;
    }
}
