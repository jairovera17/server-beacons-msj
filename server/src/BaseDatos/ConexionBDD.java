/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class ConexionBDD {
    private Connection conn;
    
    public ConexionBDD(){
        try {
            Class.forName("org.sqlite.JDBC");
            String fileBDD="src/BaseDatos/cesarBDD.sqlite";
            conn = DriverManager.getConnection("jdbc:sqlite:"+fileBDD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBDD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validarUsuario(String nombre,String password){
        
        String query ="Select count(*) from USUARIO where USUARIO.nombre ='"+ nombre+"' and USUARIO.password ='"+password+"'";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if(rs.next()){
                if(rs.getInt(1)==1)
                    return true;
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    private int getIdUsuario(String nombreUsuario){
    String query ="Select idUsuario from USUARIO where USUARIO.nombre ='"+nombreUsuario+"'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if(rs.next()){
               return rs.getInt(1);
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -5;
        
    }
    
    public void almacenarMensaje(String nombreUsuario,String mensaje,String cesar){
        try {
          
            String query ="INSERT INTO 'main'.'MENSAJE'  (\"idUsuario\",\"mensajePlano\",\"mensajeCifrado\") VALUES ('"+getIdUsuario(nombreUsuario)+"','"+mensaje+"','"+cesar+"')";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
