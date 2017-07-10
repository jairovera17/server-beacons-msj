/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class FrontSendMsj implements Runnable{

    @Override
    public void run() {
    
        response();
    }
    
    private void response(){
        do{
        
            try {
                Thread.sleep(0000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrontSendMsj.class.getName()).log(Level.SEVERE, null, ex);
            }
        if(Singleton.getInstance().outputColaMsj.size()>0){
            System.out.println("Respondiendo");
            Mensaje msj = Singleton.getInstance().outputColaMsj.poll();
            Socket socket = msj.userSocket;
            PrintWriter out;
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                     out.println(msj.userCipher);
                } catch (IOException ex) {
                    Logger.getLogger(FrontSendMsj.class.getName()).log(Level.SEVERE, null, ex);
                }
           
            
        }
        
        
        }while(true);
    }
    
   
    
}
