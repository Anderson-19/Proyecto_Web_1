package controllers;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

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
		String url = "jdbc:postgresql://ec2-3-233-236-188.compute-1.amazonaws.com:5432/dd7lesmb62pvp";
		String password = "cdd763f52d0dfdf4d60f4d361854cc7c9df0b773a6fb45a9d6adb71e033dbe69";
		String usuario = "tpeghjiavrlgmh";
	   
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
	        //DB.dbPrepareStatement(prop.getValue("q1"), obj);
	        
	        conexion = DriverManager.getConnection(url,usuario,password);
			query = conexion.prepareStatement("INSERT INTO registro (name,email,password) values (?,?,?)");
			query.setString(1, nombre);		
			query.setString(2, mail);
			query.setString(3, xpass);
			query.executeUpdate();
			
			JSONObject json = new JSONObject();
	        json.put("message", "Registrado Existosamente");
	        json.put("status", 200);
            	 
	        return json.toString();
			       
			//while(rs.next()) {
			  // if( mail.equals(rs.getString(3)) && xpass.equals(rs.getString(4)) ){	
				  // return "{\"message\": \"Registrado Existosamente\", \"status\": 200 }";
			  // }else {
					//return"{\"message\": \"Registro Existente\", \"status\": 503 }";
			  // }
			//}
			  //rs.close();
			  //conexion.close();	              	
        }catch (Exception e) {
        	System.out.println("Conexion NO Exitosa"+e.getMessage());
        	e.printStackTrace();
            return"{\"message\": \"Registro Existente\", \"status\": 503 }";
        }
		//return"{\"message\": \"Registro Existente\", \"status\": 503 }";
    }

}



