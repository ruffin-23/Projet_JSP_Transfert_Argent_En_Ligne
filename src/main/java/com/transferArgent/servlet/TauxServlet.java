package com.transferArgent.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.transferArgent.DAO.TauxDAO;
import com.transferArgent.model.Taux;


@WebServlet("/taux/*")
public class TauxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private TauxDAO tauxDAO;
	
	public void init () {
		tauxDAO = new TauxDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
			
			switch(action) {
				case "/newT" :
					showNewForm(request, response);
					break;
				case "/insertT" :
					insertTaux(request, response);
					break;
				case "/deleteT":
					deleteTaux(request, response);
					break;
				case "/editT":
					showEditForm(request, response);
					break;
				case "/updateT":
					updateTaux(request, response);
					break;
				case "/readByIdT":
					showTauxById(request, response);
					break;
				case "/search":
					//searchClients(request, response);
					break;
				default:
					listeTaux(request, response);
					break;
			}
			
		}  catch (SQLException ex) {
	        throw new ServletException("Erreur lors de l'accès à la base de données", ex);
	    } catch (Exception ex) {
	        throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
	    }
		
	}
	
	private void insertTaux(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		String idtaux = request.getParameter("idtaux");
    	int montant1 = Integer.parseInt(request.getParameter("montant1"));
		int montant2 = Integer.parseInt(request.getParameter("montant2"));
		Taux newTaux = new Taux(idtaux, montant1, montant2);
		tauxDAO.insertTaux(newTaux);
		System.out.println(newTaux);
		response.sendRedirect("listT");
	}
	
	private void showNewForm(HttpServletRequest req, HttpServletResponse res) 
    		throws SQLException, IOException, ServletException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("taux-form.jsp");
    	dispatcher.forward(req, res);
    }
	
	private void listeTaux(HttpServletRequest req, HttpServletResponse res) 
    		throws SQLException, IOException, ServletException {
    	
		try {
	        List<Taux> listeTaux = tauxDAO.selectAllTaux();
	        req.setAttribute("listeTaux", listeTaux);
	        RequestDispatcher dispatcher = req.getRequestDispatcher("taux-liste.jsp");
	        dispatcher.forward(req, res);
	    } catch (IOException ex) {
	        throw new ServletException("Erreur IO lors de la redirection vers la page JSP", ex);
	    } catch (ServletException ex) {
	        throw ex; // Re-lancer ServletException pour les exceptions déjà gérées
	    } catch (Exception ex) {
	        throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
	    }
    	
    }
	
	private void showEditForm(HttpServletRequest req, HttpServletResponse res) 
    		throws SQLException, IOException, ServletException {
    	String idtaux = req.getParameter("idtaux");
    	Taux existingTaux = tauxDAO.selectTaux(idtaux);
    	RequestDispatcher dispatcher = req.getRequestDispatcher("taux-form.jsp");
    	req.setAttribute("taux", existingTaux);
    	dispatcher.forward(req, res);
    }
	
	private void updateTaux(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		String idTaux = request.getParameter("idTaux");
		int montant1 = Integer.parseInt(request.getParameter("montant1"));
		int montant2 = Integer.parseInt(request.getParameter("montant2"));

		Taux book = new Taux(idTaux, montant1, montant2 );
		tauxDAO.updateTaux(book);
		response.sendRedirect("listT");
	}
	
	private void deleteTaux(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		String idtaux = request.getParameter("idtaux");
		tauxDAO.deleteTaux(idtaux);
		response.sendRedirect("listT");

	}
	
	private void showTauxById(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {
    	
    	// Récupération de l'id du taux depuis les paramètres de la requête
    	String idtaux = request.getParameter("idtaux");
        // Utilisation du DAO pour récupérer le taux par son ID
    	Taux existingTaux = tauxDAO.selectTaux(idtaux);
    	
    	
    	// Vérification si le taux existe
        if (existingTaux == null) {
            // Gérer le cas où le taux n'est pas trouvé, par exemple rediriger vers une page d'erreur
            response.sendRedirect(request.getContextPath() + "/taux-not-found.jsp");
            return;
        }
    	
    	// Transmission des données à la page JSP pour affichage
    	request.setAttribute("taux", existingTaux);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("taux-liste-byId.jsp");
    	dispatcher.forward(request, response);
    }

}
