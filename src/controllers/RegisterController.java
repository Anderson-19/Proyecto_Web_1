package controllers;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import helpers.*;

public class RegisterController {
    private static PropertiesReader prop = new PropertiesReader();
    private static ConnectionDB DB = new ConnectionDB();
     		
    public RegisterController() {
    	prop.properties();
    	DB.conexion();	
    }
    
   public static String register(String name, String email, String pass) {
	   Connection conexion = null;
	   PreparedStatement query = null;
			  
		//Datos para la conexion
		String url = "jdbc:postgresql://localhost:5432/inventario";
		String password = "29758990";
		String usuario = "postgres";
	   
		String mail = "", nombre = "";
		mail = email;
		nombre = name;
		MessageDigest md = null;
		try {
		   md = MessageDigest.getInstance("SHA-256");
		   md.update(pass.getBytes());
		   byte[] hashPassword = md.digest();
		   String xpass = hashPassword.toString();
	
	        String[] obj = {nombre, mail, xpass};     
	        DB.dbPrepareStatement(prop.getValue("q1"), obj);
	        
	        conexion = DriverManager.getConnection(url,usuario,password);
			query = conexion.prepareStatement("SELECT * FROM registro WHERE email = ?");
			query.setString(1, mail);		   
			ResultSet rs = query.executeQuery();
			       
			while(rs.next()) {
			   if( mail.equals(rs.getString(3)) && xpass.equals(rs.getString(4)) ){	
				   return "{\"message\": \"Registrado Existosamente\", \"status\": 200 }";
			   }else {
					return"{\"message\": \"Registro Existente\", \"status\": 503 }";
			   }
			}
			  rs.close();
			  conexion.close();	              	
        }catch (Exception e) {
        	System.out.println("Conexion NO Exitosa"+e.getMessage());
        	e.printStackTrace();
            return"{\"message\": \"Registro Existente\", \"status\": 503 }";
        }
		return"{\"message\": \"Registro Existente\", \"status\": 503 }";
    }

}



