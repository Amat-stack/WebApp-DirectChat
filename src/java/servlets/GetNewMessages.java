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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Messages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@WebServlet(name = "GetNewMessages", urlPatterns = {"/GetNewMessages"})
public class GetNewMessages extends HttpServlet {

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
            throws ServletException, IOException, JSONException { {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
             String jsonString = "";
                BufferedReader br
                        = new BufferedReader(new InputStreamReader(request.getInputStream()));
                String line;
                //String json = "";
                while((line=br.readLine())!=null) {
                    jsonString += line;
                    //System.out.println("recieve" + jsonString);
                }
                //System.out.println(jsonString != null);
                if (jsonString != null ) {
                    System.out.println(jsonString);
                    JSONObject obj = new JSONObject(jsonString);
                    int admin_id,user_id, message_id;
                    admin_id=obj.getInt("admin_id");
                    user_id=obj.getInt("user_id");
                    message_id=obj.getInt("message_id");
                    MessageDbManager db=new MessageDbManager();
                    ArrayList<Messages> arr=db.getAllMessagesAfterID(admin_id,user_id,message_id);
                    JSONArray jsonArr=new JSONArray(arr);
                    out.println(jsonArr.toString());
                    
                    
                    
                }
               
        } finally {
            out.close();
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GetNewMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
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
