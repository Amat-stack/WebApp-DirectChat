/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Admin;
import model.User;


public class AdminDbManager {
    
      public static boolean checkLogin(String email,String password){
        try{
            PreparedStatement ps = DBConnection.getInstance().prepareCall("Select * from admin where email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    
      public static Admin getAdminInformation(String email){
        Admin admin = null;
        try{
            PreparedStatement ps = DBConnection.getInstance().prepareCall("Select * from admin where email = ?");
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               admin = new Admin();
               admin.setAdminId(rs.getInt(1));
               admin.setName(rs.getString(2));
               admin.setEmail(rs.getString(3));
               
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return admin;
    }
      
      
       public static ArrayList<Admin> getAllAdminInformation(){
        Admin admin = null;
        ArrayList<Admin> arr = null;
        try{
            PreparedStatement ps = DBConnection.getInstance().prepareCall("Select * from admin");
            arr = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                admin = new Admin();
               admin.setAdminId(rs.getInt(1));
               admin.setName(rs.getString(2));
               admin.setEmail(rs.getString(3));
               arr.add(admin);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return arr;
    }
      
      
      
      
      
      
}
