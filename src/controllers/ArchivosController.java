package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.json.JSONObject;

import helpers.*;


public class ArchivosController {
	
	
	public static String subirArchivos(String email, Part file) throws ServletException, IOException{

	    Part filePart = file; 
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    JSONObject json = new JSONObject();	
	    String mail = "", archivo ="";
	    mail = email;
	    archivo = fileName;

	    String path="C:/Users/Anderson/Desktop/Anderson/Cursos De Programacion/CursoDeSpring/--Codigo--/WebProyect/WebContent/vistas/ContenedorArchivos/";
	    File uploads = new File(path); 
	    if(uploads.exists()) {
	    }else {
	    	uploads.mkdirs(); 
	    }	    
  
	    Path destinoPath = Paths.get(path+archivo);
	    try (InputStream input = filePart.getInputStream()){
	        Files.copy(input, destinoPath, StandardCopyOption.REPLACE_EXISTING);
	    }
	    
	    try {
	    	//Datos para la conexion
			String url = "jdbc:postgresql://localhost:5432/inventario";
			String password = "29758990";
			String usuario = "postgres";
			
	    	Connection conexion = null;
	    	PreparedStatement query = null;
	    	
			if(!email.equals("") && !archivo.equals("")) {
				conexion = DriverManager.getConnection(url,usuario,password);
				query = conexion.prepareStatement("INSERT INTO archivos (useremail,filename) values (?,?)");
			    query.setString(1, mail);
			    query.setString(2, archivo);
			    query.executeUpdate(); 
			    
			    json.put("mensaje", "Archivo Subido");
		        json.put("status", 200);
		        json.put("filename", archivo);
		        json.put("email", email);  
		        return json.toString();
			}else {
				 json.put("mensaje", "Error Al Subir el Archivo");
			     json.put("status", 500);
			     return json.toString();
			}
			
			}catch(SQLException e) {
				System.out.println(e.getMessage());

			}
	     json.put("mensaje", "Error Al Subir el Archivo");
	     json.put("status", 500);
	     return json.toString();
	        
	   
	}
	
	public static String obtenerDatos(String email)throws ServletException {
		//Datos para la conexion
		String url = "jdbc:postgresql://localhost:5432/inventario";
		String password = "29758990";
		String usuario = "postgres";
		
		JSONObject json = new JSONObject();
    	Connection conexion = null;
    	PreparedStatement query = null;
    	String mail = ""; mail = email;
    	try {
    		if(!email.equals("") ) {			
				conexion = DriverManager.getConnection(url,usuario,password);
				query = conexion.prepareStatement("SELECT * FROM archivos WHERE useremail = ?");
			    query.setString(1, mail);
			    ResultSet rs = query.executeQuery();

			    json.put("mensaje","Se Encontraron Archivos" );
				json.put("status", 200);
				while(rs.next()) {	
					
			        json.accumulate("archivos",rs.getString(3));			        			        
				}				
				return json.toString();
    		}else {
			 json.put("status", 500);
		     json.put("mensaje","No Tiene Archivos Guardados" );	       	 
	         return json.toString();
    		}
    	}catch (SQLException e) {			
			e.printStackTrace();
		}
		 
		json.put("status", 500);
	    json.put("mensaje","No Tiene Archivos Guardados" );    	 
        return json.toString();
    }
	
	public static String borrarArchivos(String email, String filename)throws ServletException {
		//Datos para la conexion
				String url = "jdbc:postgresql://localhost:5432/inventario";
				String password = "29758990";
				String usuario = "postgres";
				
				JSONObject json = new JSONObject();
		    	Connection conexion = null;
		    	PreparedStatement query = null;
		    	String file = ""; file = filename;
		    	String mail = ""; mail =email;
		    	try {
		    		if(!file.equals("") ) {			
						conexion = DriverManager.getConnection(url,usuario,password);
						query = conexion.prepareStatement("DELETE FROM archivos WHERE useremail = ? AND filename = ?");
					    query.setString(1, mail);
					    query.setString(2, file);
					    query.executeUpdate();

					    json.put("mensaje","Archivo Borrado" );
						json.put("status", 200);																	        			        										
						return json.toString();
		    		}else {
					 json.put("status", 500);
				     json.put("mensaje","El Archivo No Pudo Ser Borrado" );	       	 
			         return json.toString();
		    		}
		    	}catch (SQLException e) {			
					e.printStackTrace();
				}
				 
		    	json.put("status", 500);
			    json.put("mensaje","El Archivo No Pudo Ser Borrado" );   	 
		        return json.toString();	
	}
		 				    		   	 	
}
	

