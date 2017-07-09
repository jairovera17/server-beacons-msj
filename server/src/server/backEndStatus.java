/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.Serializable;

/**
 *
 * @author jairo
 */
public class backEndStatus implements Serializable{
    
    public int id;
    public String localname;
    public double lastLog;

    public backEndStatus(int id, String localname, double lastLog) {
        this.id = id;
        this.localname = localname;
        this.lastLog = lastLog;
    }

    @Override
    public String toString() {
        String salida="";
        salida+=this.id+"@";
        salida+=this.localname+"@";
        salida+=this.lastLog+"";
        
        return salida;
    }
    
    
    
    
}
