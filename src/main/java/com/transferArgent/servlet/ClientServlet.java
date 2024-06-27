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

import com.transferArgent.DAO.ClientDAO;
import com.transferArgent.model.Client;


@WebServlet("/")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientDAO clientDAO;

    public void init () {
    	clientDAO = new ClientDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		doGet(request, response);
	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
			
			switch(action) {
				case "/new" :
					showNewForm(request, response);
					break;
				case "/insert" :
					insertClient(request, response);
					break;
				case "/delete":
					deleteClient(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					updateClient(request, response);
					break;
				case "/readById":
					showClientById(request, response);
					break;
				case "/search":
					searchClients(request, response);
					break;
				default:
					listeClient(request, response);
					break;
			}
			
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
		
	}

    private void listeClient(HttpServletRequest req, HttpServletResponse res) 
    		throws SQLException, IOException, ServletException {
    	
    	List<Client> listeClient = clientDAO.selectAllClients();
    	req.setAttribute("listeClient", listeClient);
    	RequestDispatcher dispatcher = req.getRequestDispatcher("client-liste.jsp");
    	dispatcher.forward(req, res);
    	
    }
    
    private void showNewForm(HttpServletRequest req, HttpServletResponse res) 
    		throws SQLException, IOException, ServletException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("client-form.jsp");
    	dispatcher.forward(req, res);
    }
    
    private void showEditForm(HttpServletRequest req, HttpServletResponse res) 
    		throws SQLException, IOException, ServletException {
    	String numtel = req.getParameter("numtel");
    	Client existingClient = clientDAO.selectClient(numtel);
    	RequestDispatcher dispatcher = req.getRequestDispatcher("client-form.jsp");
    	req.setAttribute("client", existingClient);
    	dispatcher.forward(req, res);
    }
    
    private void insertClient(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		String numtel = request.getParameter("numtel");
    	String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String pays = request.getParameter("pays");
		int solde = Integer.parseInt(request.getParameter("solde"));
		String mail = request.getParameter("mail");
		Client newClientr = new Client(numtel, nom, sexe, pays, solde, mail);
		clientDAO.insertClient(newClientr);
		response.sendRedirect("list");
	}
    
    private void updateClient(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		String numtel = request.getParameter("numtel");
		String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String pays = request.getParameter("pays");
		int solde = Integer.parseInt(request.getParameter("solde"));
		String mail = request.getParameter("mail");

		Client book = new Client(numtel, nom, sexe, pays, solde, mail );
		clientDAO.updateClient(book);
		response.sendRedirect("list");
	}
    
    private void deleteClient(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		String numtel = request.getParameter("numtel");
		clientDAO.deleteClient(numtel);
		response.sendRedirect("list");

	}
    
    private void showClientById(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {
    	
    	// Récupération de l'id du client depuis les paramètres de la requête
    	String numtel = request.getParameter("numtel");
        // Utilisation du DAO pour récupérer le client par son ID
    	Client existingClient = clientDAO.selectClient(numtel);
    	
    	
    	// Vérification si le client existe
        if (existingClient == null) {
            // Gérer le cas où le client n'est pas trouvé, par exemple rediriger vers une page d'erreur
            response.sendRedirect(request.getContextPath() + "/client-not-found.jsp");
            return;
        }
    	
    	// Transmission des données à la page JSP pour affichage
    	request.setAttribute("client", existingClient);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("client-liste-byId.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void searchClients(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException, ServletException {
    	String searchKeyword = request.getParameter("searchKeyword"); // Récupère le mot-clé de recherche depuis le formulaire ou autre source
    	// Vérifie si le mot-clé de recherche est vide ou null
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            List<Client> clients = clientDAO.searchClients(searchKeyword); // Utilise le DAO pour rechercher les clients
            request.setAttribute("clients", clients); // Met la liste de clients dans un attribut de requête
        } else {
            // Gérer le cas où aucun mot-clé n'est spécifié, par exemple afficher tous les clients
            List<Client> clients = clientDAO.selectAllClients(); // Méthode hypothétique pour obtenir tous les clients
            request.setAttribute("clients", clients); // Met la liste de clients dans un attribut de requête
        }
    }

}
