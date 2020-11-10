package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import helpers.LoginVerificacion;

public class UserController {
	private static LoginVerificacion logear = new LoginVerificacion();

	public static String modificarInformacion(HttpServletRequest req) {
		Connection conexion = null;
		PreparedStatement query = null;
		String mail = req.getParameter("email");
		JSONObject json = new JSONObject();
		
		//Datos para la conexion
	    String url = "jdbc:postgresql://localhost:5432/inventario";
	    String password = "29758990";
	    String usuario = "postgres";
			   
		try {	        
			conexion = DriverManager.getConnection(url,usuario,password);
			query = conexion.prepareStatement("SELECT * FROM registro WHERE email = ?");
			query.setString(1, mail);		   
			ResultSet rs = query.executeQuery();
					       
			while(rs.next()) {
			    if( mail.equals(rs.getString(3)) ){	
			    	 json.put("mensaje", "Modificacion Hecha");
			         json.put("status", 200);
			         json.put("email",rs.getString(3));
			         json.put("name",rs.getString(2));
		            	 
			         return json.toString();
			    }else {
					return"{\"message\": \"Imposible Ralizar Las Modificaciones\", \"status\": 503 }";
				}
			}
				rs.close();
			    conexion.close();	              	
		   }catch (Exception e) {
		       System.out.println("Conexion NO Exitosa"+e.getMessage());
		       e.printStackTrace();
		       return"{\"message\": \"Imposible Ralizar Las Modificaciones\", \"status\": 503 }";
		   }
			   System.out.println("Registro Existente2");
			   return"{\"message\": \"Imposible Ralizar Las Modificaciones\", \"status\": 503 }";
	}
	
	public static String cerrarSesion(HttpServletRequest req) {
        String mensaje = req.getParameter("mensaje");
        JSONObject json = new JSONObject();
        
        if(mensaje.equals("cerrar Sesion")) {
        	HttpSession session = req.getSession();
        	session.invalidate();
        	 
	         json.put("mensaje", "Cerrando Sesion");
	         json.put("status", 200);
            	 
	         return json.toString();
        }else {
        	json.put("mensaje", "Error Al Cerrar Sesion");
	        json.put("status", 500);
           	 
	        return json.toString();
        }
		
	}
	
	public static String ModificarImagen(Part file)throws ServletException{

	    Part filePart = file; 
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    JSONObject json = new JSONObject();	
	    String mail = "", archivo ="";
	    archivo = fileName;
	    
	    try {
	    String path="C:/Users/Anderson/Desktop/Anderson/Cursos De Programacion/CursoDeSpring/--Codigo--/WebProyect/WebContent/vistas/ImgUser/";
	    File uploads = new File(path); 
	    if(uploads.exists()) {
	    }else {
	    	uploads.mkdirs(); 
	    }	    
  
	    Path destinoPath = Paths.get(path+archivo);
	    try (InputStream input = filePart.getInputStream()){
	        Files.copy(input, destinoPath, StandardCopyOption.REPLACE_EXISTING);
	    }	    	    	    	 
			    
	    json.put("mensaje", "Foto Subida");
		json.put("status", 200);
		json.put("filename", archivo); 
		return json.toString();					
			
	    }catch(Exception e) {
			System.out.println(e.getMessage());
			json.put("mensaje", "Error Al Subir el Archivo");
		    json.put("status", 500);
		    return json.toString();

		}
	    	        	   
	}

}
