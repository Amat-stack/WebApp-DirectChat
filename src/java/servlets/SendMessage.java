/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dbmanager.MessageDbManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Admin;
import model.Messages;
import model.User;
import org.json.JSONObject;

/**
 *
 * @author user1
 */
@WebServlet(name = "SendMessage", urlPatterns = {"/SendMessage"})
public class SendMessage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String user_type = (String)session.getAttribute("USER_TYPE");
        int user_id;
        boolean isAdmin = true;//get from sssion
        if(user_type.compareToIgnoreCase("ADMIN")==0){
            Admin a = (Admin)session.getAttribute("USERS");
            user_id = a.getAdminId();
        }else{
            User u = (User)session.getAttribute("USERS");
            user_id = u.getUserId();
            isAdmin = false;
        }
        
        

        int reguser_id, admin_id;
        String message;
        Messages dto = new Messages();

        if (isAdmin) {
            dto.setAdmin_id(user_id);
            dto.setSender(0);
        } else {
            dto.setUser_id(user_id);
            dto.setSender(1);

        }

        PrintWriter out = response.getWriter();
        try {
            String jsonString = "";
            BufferedReader br
                    = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            //String json = "";
            while ((line = br.readLine()) != null) {
                jsonString += line;
                //System.out.println("recieve" + jsonString);
            }
            //System.out.println(jsonString != null);
            if (jsonString != null) {
                System.out.println(jsonString);
                JSONObject obj = new JSONObject(jsonString);

                reguser_id = obj.getInt("user_id");
                
                admin_id = obj.getInt("admin_id"); 
                
                message = obj.getString("message");
                
                dto.setMessage(message);
                boolean isSessionMatched = false;
                if (isAdmin) {
                    dto.setUser_id(user_id);
                    if (reguser_id == dto.getUser_id()) {
                        isSessionMatched = true;
                    }

                } else {
                    dto.setAdmin_id(admin_id);
                    if (admin_id == dto.getAdmin_id()) {
                        isSessionMatched = true;
                    }
                }
                if (isSessionMatched) {
                    MessageDbManager db = new MessageDbManager();
                    Messages sendObj=db.getMessageByID(db.insert(dto));
                    if(sendObj!=null)
                    {
                        JSONObject json=new JSONObject(sendObj);
                        out.print(json.toString());
                    }
                    else
                    {
                    out.print("null");
                    }

                } else {
                    out.print("null");
                }

            }

        }catch(Exception ex){
            ex.printStackTrace();
        } 
        finally {
            
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
                return;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
