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


public class UserDbManager {
 
      public static boolean checkLogin(String email,String password){
        try{
            PreparedStatement ps = DBConnection.getInstance().prepareCall("Select * from user where email = ? AND password = ?");
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
    
      
      public static User getUserInformation(String email){
        User user = null;
        try{
            PreparedStatement ps = DBConnection.getInstance().prepareCall("Select * from user where email = ?");
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               user = new User();
               user.setUserId(rs.getInt(1));
               user.setName(rs.getString(2));
               user.setEmail(rs.getString(3));
               
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
      
    
      
      public static ArrayList<User> getAllUserInformation(){
        User user = null;
        ArrayList<User> arr = null;
        try{
            PreparedStatement ps = DBConnection.getInstance().prepareCall("Select * from user");
            arr = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                user = new User();
               user.setUserId(rs.getInt(1));
               user.setName(rs.getString(2));
               user.setEmail(rs.getString(3));
               arr.add(user);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return arr;
    }
      
      
      
    
}
