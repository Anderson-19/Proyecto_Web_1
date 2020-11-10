package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.ArchivosController;

/**
 * Servlet implementation class BorradorArchivos
 */
@MultipartConfig
@WebServlet("/BorradorArchivos")
public class BorradorArchivos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorradorArchivos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArchivosController archivos = new ArchivosController();
		PrintWriter out = response.getWriter();        
		response.setContentType("application/json");
		
		String file = request.getParameter("file");
		String email = request.getParameter("email");
		System.out.println(file+"-----"+email);

		
		if(!file.equals("") && !email.equals("")) {
			out.println(archivos.borrarArchivos(email,file));
		}else {
			return;
		}
	}

}
