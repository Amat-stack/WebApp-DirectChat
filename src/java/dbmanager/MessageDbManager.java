/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Messages;


public class MessageDbManager {
    
    
    public int insert(Messages message)
    {
        PreparedStatement pst = null;
        int rowAffected=0;
        int index=-1;
        try {
            String query="INSERT INTO `messages`(`admin_id`, `user_id`, `sender`, `message`) VALUES (?,?,?,?)";
            pst = DBConnection.getInstance().prepareCall(query);
            
            pst.setInt(1, message.getAdmin_id());
            pst.setInt(2, message.getUser_id());
            pst.setInt(3,message.getSender());
            pst.setString(4,message.getMessage());
            
            rowAffected=pst.executeUpdate();
            if(rowAffected>0)
            {
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next())
                {
                    index= rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(MessageDbManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return index;
    }
    
    public ArrayList<Messages> getAllMessages(int adminID,int userID)
    {
        ArrayList<Messages> arr = new ArrayList<Messages>();
        String query="select * from messages where teacher_id=? and user_id=?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Messages obj = null;
        try {
            pst = DBConnection.getInstance().prepareCall(query);
            pst.setInt(1, adminID);
            pst.setInt(2, userID);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    obj = new Messages();
                    obj.setId(rs.getInt(1));
                    obj.setAdmin_id(rs.getInt(2));
                    obj.setUser_id(rs.getInt(3));
                    obj.setSender(rs.getInt(4));
                    obj.setMessage(rs.getString(5));
                     obj.setDateTime(rs.getString(6));
                    arr.add(obj);    
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(MessageDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        
        return arr;
    }
     public ArrayList<Messages> getAllMessagesAfterID(int userID,int adminID,int messageID)
    {
        ArrayList<Messages> arr = new ArrayList<Messages>();
        String query="select * from messages where admin_id=? and user_id=? and id> ?";
        PreparedStatement pst=null;
        
        ResultSet rs = null;
        Messages obj = null;
        try {
            pst = DBConnection.getInstance().prepareCall(query);
             pst.setInt(1, adminID);
            pst.setInt(2, userID);
            pst.setInt(3, messageID);
            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    obj=new Messages();
                    obj.setId(rs.getInt(1));
                    obj.setAdmin_id(rs.getInt(2));
                    obj.setUser_id(rs.getInt(3));
                    obj.setSender(rs.getInt(4));
                    obj.setMessage(rs.getString(5));
                    obj.setDateTime(rs.getString(6));
                    arr.add(obj);    
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(MessageDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        
        return arr;
    }
   public Messages getMessageByID(int id)
    {
        ArrayList<Messages> arr = new ArrayList<Messages>();
        String query="select * from messages where id=?";
        PreparedStatement pst = null;
        
        ResultSet rs=null;
        Messages obj=null;
        try {
            pst = DBConnection.getInstance().prepareCall(query);
             
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    obj=new Messages();
                    obj.setId(rs.getInt(1));
                    obj.setAdmin_id(rs.getInt(2));
                    obj.setUser_id(rs.getInt(3));
                    obj.setSender(rs.getInt(4));
                    obj.setMessage(rs.getString(5));
                    obj.setDateTime(rs.getString(6));
                       
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(MessageDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        
        return obj;
    }
   
   
    
}
