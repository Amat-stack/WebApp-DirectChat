/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnection {
    
    private static Connection con = null;
    
    private DBConnection(){
        
    }

    public static Connection getInstance(){
       
        if(con == null){
            try {
                makeConnection();
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database","root","");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
        
        return con;
    }
    
    private static void makeConnection() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();;
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
