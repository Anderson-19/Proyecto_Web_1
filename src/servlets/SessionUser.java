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
import controllers.LoginController;
import controllers.RegisterController;
import controllers.UserController;

/**
 * Servlet implementation class SessionUser
 */
@MultipartConfig
@WebServlet("/SessionUser")
public class SessionUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	UserController imagen = new UserController();
		PrintWriter out = resp.getWriter();        
		resp.setContentType("application/json");	    	   	      
		try {
			String mensaje = req.getParameter("mensaje");
		
			if(mensaje.equals("Foto Subida")) {
				out.println(imagen.ModificarImagen(req.getPart("imagenes")));	
			}else {
				return;
			}
								
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	UserController usuario = new UserController();
		resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(usuario.modificarInformacion(req));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserController usuario = new UserController();
		response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(usuario.cerrarSesion(request));
	}

}
