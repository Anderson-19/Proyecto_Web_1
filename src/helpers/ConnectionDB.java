package helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public class ConnectionDB {
	
	private PropertiesReader pr = new PropertiesReader();
	
	//Conexion con la base dates
	private  Connection connect = null;
								
	//Datos para la conexion
	private String driver,url,usuario,pass;
	
	//CONSULTA
	private PreparedStatement query = null;
	
	public ConnectionDB() {
		pr.properties();	
		driver = pr.getValue("driver");
		url = pr.getValue("url");
		pass = pr.getValue("pass");
		usuario = pr.getValue("usuario");
	}
	
	public Connection conexion() {
		try {
			//Class.forName(driver);
			connect = DriverManager.getConnection(url, usuario,pass);
		} catch (SQLException e) {
			return null;
		}
		return  connect ;	}

	public void dbPrepareStatement(String value, String[]obj) {
		try {
			query = connect.prepareStatement(value);
			query.setString(1, obj[0]);
			query.setString(2, obj[1]);
			query.setString(3, obj[2]);
			query.executeUpdate();
			
		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
