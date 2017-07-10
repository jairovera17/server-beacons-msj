/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import BaseDatos.ConexionBDD;

/**
 *
 * @author jairo
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConexionBDD miBDD = new ConexionBDD();
        if(miBDD.validarUsuario("Jairo", "jairo")){
            for (int i = 0; i < 3; i++) {
                miBDD.almacenarMensaje("Jairo","hail","pulpos");
            }
    
        }
        
    }
    
}
