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
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class FrontEnd {
    
    static ArrayList<backEndStatus> registro;
    static Queue<Mensaje>cola;
    
    
    
     public static void main(String[] args){
        iniciarFrontServer();
    }
     
     private static void iniciarFrontServer(){
         
         
         
         ExecutorService ex = Executors.newCachedThreadPool();
         ex.submit(new FrontBeacon());
         ex.submit(new FrontMsj());
         
         
     }
     
     private static void iniciarFrontBeacons(){
         FrontBeacon frontbeacon = new FrontBeacon();
         frontbeacon.frontBeacons();
         
     }
     
   
    
    
    public static class FrontMsj implements Runnable{
        private int portNumber=4200;
        
        @Override
        public void run() {
            recibirMsj();
         }
        
        private void recibirMsj(){
            try {
                ExecutorService ex = Executors.newCachedThreadPool();
                ServerSocket serverMsjSocket = new ServerSocket(portNumber);
                do{
                    System.out.print("khe");
                   Socket socket = serverMsjSocket.accept();
                   System.out.print("alooooo");
                   socket.close();
                    
                }while(true);
            } catch (IOException ex1) {
                Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    
    }
    
    public static class FrontBeacon implements Runnable{
        
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

        @Override
        public void run() {
            frontBeacons();
          }
        
    }
    class FrontMSJ{
        
        private int portNumber;
        
        public FrontMSJ(){
            
        }
    }
    
}
