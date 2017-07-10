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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class FrontBack  implements Runnable{
    
    int portNumber=5005;
    ServerSocket serversocket;
    @Override
    public void run() {
        
        gestionarColas();
    }
    
  
    private void gestionarColas(){
        try {
          serversocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            Logger.getLogger(FrontBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        do{
        
             
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrontBack.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            if(Singleton.getInstance().inputColaMsj.size()>0){
                try {
                  
                    Socket socket = serversocket.accept();
                    Mensaje msj = Singleton.getInstance().inputColaMsj.poll();
                    PrintWriter out =new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    
                    out.println(msj.userName+"@"+msj.userPassword+"@"+msj.userMsj);
                    String response =in.readLine();
                    System.out.println("BackBDD dice: "+response);
                    msj.userCipher=response;
                    Singleton.getInstance().outputColaMsj.add(msj);
                    socket.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(FrontBack.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            
        }while(true);
    }
}
