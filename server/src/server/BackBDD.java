/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import BaseDatos.ConexionBDD;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class BackBDD implements Runnable{
    int portNumber=5005;
    String hostname="127.0.0.1";
    ConexionBDD conexion;
    consolaMSJ consola;
    public BackBDD(){
        this.conexion=new ConexionBDD();
        this.consola=new consolaMSJ();
    }

    @Override
    public void run() {
     
      restApi();
       
        
    }
    
    
    private void restApi(){
        
        do{
        
            try {
                System.out.println("Espero Solicitudes del Front-End");
                Socket socket = new Socket(hostname,portNumber);
                PrintWriter out =new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               
                String input=in.readLine();
                String cifrado ="Usario INVALIDO";
                Mensaje msj = consola.parsearMensaje(input, null);
                if(conexion.validarUsuario(msj.userName, msj.userPassword)){
                    System.out.println("Validado Correctamente");
                    cifrado = msj.userCipher=consola.cifrar(msj.userMsj);
                    
                    conexion.almacenarMensaje(msj.userName, msj.userMsj, msj.userCipher);
                }
                else System.out.println("fake");
                
                
                out.println(cifrado);
                
            } catch (IOException ex) {
                Logger.getLogger(BackBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        
        
        }while(true);
        
    }
    
}
