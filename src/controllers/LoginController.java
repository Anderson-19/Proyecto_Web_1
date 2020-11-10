package controllers;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.*;

import helpers.*;

public class LoginController {
	private  static LoginVerificacion logear = new LoginVerificacion();
	private static HttpSession session;
	
    public static String login(HttpServletRequest req)  {
    	String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        
  
        try {
            if(logear.authCheck(email, pass) == true) {           	
            	session = req.getSession();
	            session.setAttribute("email", email);
	            session.setAttribute("pass", pass);

	            JSONObject json = new JSONObject();
	            json.put("message", "Inicio Sesion Exitosamente");
	            json.put("status", 200);
	            json.put("email", session.getAttribute("email"));
               	 
	            return json.toString();
	            
            } else {
            	System.out.println("Inicio De Sesion Invalido");
                return"{\"message\": \"Inicio De Sesion Invalido\", \"status\": 500 }";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	
            return"{\"message\": \"Logeado Invalido\", \"status\": 500 }";
        }
    }


}
