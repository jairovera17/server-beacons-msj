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
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class BackBeacon implements Runnable{
     int id = 10;
        int portNumber = 5000;
        String hostname = "127.0.0.1";
        String localname = "127.0.0.10";
        backEndStatus backStatus;
        
        public BackBeacon(int id){
            backStatus = new backEndStatus(id, localname, 0);
        }
        
        private void backBeacons(){
          String ack="";
        try {
            
            do{
            Socket backSocket = new Socket(hostname,portNumber);
            PrintWriter out = new PrintWriter(backSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(backSocket.getInputStream()));
            
            backStatus.lastLog=System.currentTimeMillis();
            
            
            out.println(backStatus.toString());
            ack = in.readLine();
           System.out.println("Front dice: "+ack);
            
            
            }while(true);
            
            
        } catch (IOException ex) {
            Logger.getLogger(BackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }

        @Override
        public void run() {
        backBeacons();
        }
        
        
    
}
