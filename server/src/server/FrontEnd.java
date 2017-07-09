/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
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
     
     private static void iniciarFrontServer() {
         
         
         
         ExecutorService ex = Executors.newCachedThreadPool();
         ex.submit(new FrontBeacon());
         ex.submit(new FrontRecvMsj());
         statusBack();
        
         
         
     }
     private static void statusBack(){
         int numServer=Singleton.getInstance().registro.size();
         double tiempo = System.currentTimeMillis();
         double tiempo2;
         while(true){
            
             try {
                 Thread.sleep(1500);
             } catch (InterruptedException ex) {
                 Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
             }
             
              DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
             tiempo2=System.currentTimeMillis();
             int numServerAux=Singleton.getInstance().registro.size();
            if(numServer<numServerAux){
                System.out.println("Se ha ha establecido conexion con un nuevo Back-End");
                 System.out.println(dateFormat.format(date)+"\tServidores backEnd activos: "+Singleton.getInstance().registro.size());
            }
            if(numServer>numServerAux){
                 System.out.println("Se ha perdido conexion con un Back-End");
                  System.out.println(dateFormat.format(date)+"\tServidores backEnd activos: "+Singleton.getInstance().registro.size());
            }
           
            
            numServer=numServerAux;
            System.out.println("Mensajes en cola= "+Singleton.getInstance().inputColaMsj.size());
         }
     }
     
     
   
    
    
    
}
