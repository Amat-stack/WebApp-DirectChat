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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Messages;
import org.json.JSONArray;
import org.json.JSONObject;



@WebServlet(name = "ReceiveMessageServlet", urlPatterns = {"/ReceiveMessageServlet"})
public class ReceiveMessageServlet extends HttpServlet {

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
        int user_id = 1;// get from session
        boolean isAdmin = true;//get from sssion

        int reguser_id, admin_id, message_id;
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

                reguser_id = obj.getInt("student_id");
                
                admin_id = obj.getInt("teacher_id"); 
                
                message_id = obj.getInt("message_id");
                
                MessageDbManager mDB = new MessageDbManager();
                ArrayList<Messages> list = mDB.getAllMessagesAfterID(reguser_id, admin_id, message_id);
                System.out.println(list);
                    if(list!=null)
                    {
                        
                        JSONArray json = new JSONArray(list);
                        out.print(json.toString());
                        System.out.println(json.toString());
//                        for(Messages m : list){
//                            JSONObject json=new JSONObject(m);
//                            out.print(json.toString());
//                            System.out.println(json.toString());
//                        }
                        
                    }
                    else
                    {
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
        processRequest(request, response);
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
