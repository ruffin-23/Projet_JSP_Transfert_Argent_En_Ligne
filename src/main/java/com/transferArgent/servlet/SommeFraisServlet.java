package com.transferArgent.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.transferArgent.DAO.EnvoyeDAO;
import com.transferArgent.DAO.SommeFraiDAO;
import com.transferArgent.model.SommeFraisEnvoye;


@WebServlet("/somFrais/*")
public class SommeFraisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SommeFraiDAO sommeFraiDAO;
	private EnvoyeDAO envoyeDAO;

	public void init() {
		sommeFraiDAO = new SommeFraiDAO();
		envoyeDAO = new EnvoyeDAO();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Delegate POST requests to doGet
    }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
		
		if (path.startsWith(request.getContextPath() + "/css") || path.startsWith(request.getContextPath() + "/js")) {
            request.getRequestDispatcher(path).forward(request, response);
            return;
        } else {
        	String action = request.getPathInfo();
        	
        	try {
				switch (action) {
					case "/insertS":
						
						break;
				}
			} catch (Exception ex) {
                throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
            }
        	
        }
		
	}
	
	
    
	

}
