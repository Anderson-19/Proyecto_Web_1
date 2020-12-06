package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.RegisterController;

/**
 * Servlet implementation class Registro
 */
@MultipartConfig
@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public Registro()  {    	
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stu
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegisterController registro = new RegisterController();
		response.setContentType("application/json");
		String nombre = request.getParameter("name");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
        PrintWriter out = response.getWriter();
        out.println(registro.register(nombre, email, pass));
 
	}

}
