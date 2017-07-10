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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class FrontRecvMsj implements Runnable{
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
                    
                   Socket socket = serverMsjSocket.accept();
                   ex.submit(new inputSocket(socket, 5));
                   
                  
                    
                }while(true);
            } catch (IOException ex1) {
                Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    
        class inputSocket implements Runnable{
            
            int id;
            Socket clientSocket;
            public inputSocket(Socket socket,int id){
                this.clientSocket=socket;
                this.id=id;
                
                
            }
            
        @Override
        public void run() {
            
            consolaMSJ consola = new consolaMSJ();
            PrintWriter out = null;
            BufferedReader in=null;
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    
                    String msj = in.readLine();
                    Mensaje nuevoMensaje =consola.parsearMensaje(msj,clientSocket);
                    
                    Singleton.getInstance().inputColaMsj.add(nuevoMensaje);
                   
                    
                } catch (IOException ex) {
                    Logger.getLogger(FrontRecvMsj.class.getName()).log(Level.SEVERE, null, ex);
                } 
            
        }
            
        }
    
}
