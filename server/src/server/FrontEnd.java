/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class FrontEnd {
    
    static ArrayList<backEndStatus> registro;
    
    
     public static void main(String[] args){
        iniciarFrontServer();
    }
     
     private static void iniciarFrontServer(){
         iniciarFrontBeacons();
     }
     
     private static void iniciarFrontBeacons(){
         FrontBeacon frontbeacon = new FrontBeacon();
         frontbeacon.frontBeacons();
         
     }
   
    
    
    
    
    public static class FrontBeacon{
        
        private int portNumber=5000;
        private consolaBeacon consola;
        private double maxTimeLimit=20;
        private int sleepTime =1500;
        public  FrontBeacon(){
            consola = new consolaBeacon();
            consola.guardaRegistro(new ArrayList<backEndStatus>());
       
            
        }
        
        private void frontBeacons(){
           
            
            try {
                
                
                String status="";
                ServerSocket serversock = new ServerSocket(this.portNumber);
                
                do{
                    
                    ArrayList<backEndStatus>registroTemp = consola.leerRegistro();
                    if(registroTemp!=null){
                        registro=registroTemp;
                    }
                    else{
                        registro = new ArrayList<backEndStatus>();
                    }
                      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.print(dateFormat.format(date)); //2016/11/16 12:08:43
                 System.out.println("\tServidores backEnd activos: "+registro.size());
                
                    
                     Thread.sleep(sleepTime);
                    Socket socket = serversock.accept();
                     PrintWriter out =new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
                     status = in.readLine();
                     backEndStatus nuevoRegistro = consola.parsearBackEndStatus(status);
                     
                     registro = consola.refreshRegistro(registro, nuevoRegistro, maxTimeLimit);
                     consola.guardaRegistro(registro);
                     out.println("Se que estas vivo"+nuevoRegistro.id);
                     
                     
                
                }while(true);
            
            
            } catch (IOException ex) {
                Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    class FrontMSJ{
        
        private int portNumber;
        
        public FrontMSJ(){
            
        }
    }
    
}
