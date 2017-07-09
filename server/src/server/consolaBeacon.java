/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author jairo
 */
public class consolaBeacon {
    
    
     public ArrayList<backEndStatus> leerRegistro(){
        ArrayList<backEndStatus> salida;
        InputStream file = null;
        try {
            file = new FileInputStream("registro.txt");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream (buffer);
            
            salida = (ArrayList<backEndStatus>)input.readObject();
            return salida;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
     
     public void guardaRegistro(ArrayList<backEndStatus> registro){
        OutputStream file = null;
        try {
            file = new FileOutputStream("registro.txt");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            
            output.writeObject(registro);
            output.close();
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(consolaBeacon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    backEndStatus parsearBackEndStatus(String status) {
        StringTokenizer tokens = new StringTokenizer(status, "@");
        int id = Integer.parseInt(tokens.nextToken());
        String localname = tokens.nextToken();
        double lastLog = Double.parseDouble(tokens.nextToken());
        
        return new backEndStatus(id, localname, lastLog);
    }

    ArrayList<backEndStatus> refreshRegistro(ArrayList<backEndStatus> registro, backEndStatus nuevoRegistro, double tiempoLimite) {
      
        ArrayList<backEndStatus> salida = new ArrayList<>();
        boolean nuevo = true;
        for(int i=0;i<registro.size();i++){
            backEndStatus registroTemp=registro.get(i);
            double currentTime= System.currentTimeMillis();
            double lastRegister =currentTime/1000-registroTemp.lastLog/1000;
           
            if(lastRegister<(tiempoLimite)){
            
                if(registroTemp.id==nuevoRegistro.id){
                    registroTemp.lastLog=nuevoRegistro.lastLog;
                    nuevo = false;
                    continue;
                }
                
               salida.add(registroTemp);
            }
        }
        
        if(nuevo){}
            salida.add(nuevoRegistro);
       return salida;
    }
    
}
