/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author jairo
 */
public class consolaMSJ {
    
    public Mensaje parsearMensaje (String msj,Socket socket){
        Mensaje respuesta = new Mensaje();
        StringTokenizer tokens = new StringTokenizer(msj,"@");
        respuesta.userName=tokens.nextToken();
        respuesta.userPassword=tokens.nextToken();
        respuesta.userMsj=tokens.nextToken();
        respuesta.userSocket=socket;
        return respuesta;
        
    }
}
