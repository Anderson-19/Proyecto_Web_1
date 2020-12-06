package helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.RegisterController;

public class LoginVerificacion {
		
	public static boolean authCheck(String email, String pass) {
		/*//Datos para la conexion
		String url = "jdbc:postgresql://localhost:5432/inventario";
		String password = "29758990";
		String usuario = "postgres";*/
		 
		 Connection conexion = null;
		 PreparedStatement query = null;
			  
		//Datos para la conexion
		String url = "jdbc:postgresql://ec2-3-233-236-188.compute-1.amazonaws.com:5432/dd7lesmb62pvp";
		String password = "cdd763f52d0dfdf4d60f4d361854cc7c9df0b773a6fb45a9d6adb71e033dbe69";
		String usuario = "tpeghjiavrlgmh";
		
		String mail = "";
		mail = email;
		MessageDigest md = null;

		try {
		   md = MessageDigest.getInstance("SHA-256");
		   md.update(pass.getBytes());
		   byte[] hashPassword = md.digest();	
		   String xpass = hashPassword.toString();
		  		   
		   conexion = DriverManager.getConnection(url,usuario,password);
		   query = conexion.prepareStatement("SELECT * FROM registro WHERE email = ?");
		   query.setString(1, mail);		   
		   ResultSet rs = query.executeQuery();
		       
		   while(rs.next()) {
		      if( mail.equals(rs.getString(3)) || xpass.equals(rs.getString(4)) ){
		    	  return true;	
		      }else {
		    	  return false;
		      }
		    }
		       rs.close();
		       conexion.close();	          	   	       	    
        }catch (SQLException | NoSuchAlgorithmException e) {
        	System.out.println("Conexion NO Exitosa"+e.getMessage());
        	e.printStackTrace();
        	return false;
        }
		return false;
		
	}
}


