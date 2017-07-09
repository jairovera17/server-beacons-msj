/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jairo
 */
public class BackEnd {
    
     public static void main(String[] args){
        
        iniciarBackEnd();
     
    }
     
     private static void iniciarBackEnd(){
         ExecutorService ex = Executors.newCachedThreadPool();
         ex.submit(new BackBeacon());
     }
    
   
}
