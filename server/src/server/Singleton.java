/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author jairo
 */
public class Singleton {
    public static ArrayList<backEndStatus> registro ;
    public static Queue<Mensaje> inputColaMsj;
    public static Queue<Mensaje> outputColaMsj;
    
    private static  Singleton instance = null;
    
    
    
    private Singleton(){
        this.registro=new ArrayList<>();
        inputColaMsj=new LinkedList<>();
        outputColaMsj=new LinkedList<>();
      
    }
    
    public static synchronized Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
            if(instance ==null)
                instance = new Singleton();
            }
            
           
        }
        return instance;
    }
    
   
    
    
    
}
