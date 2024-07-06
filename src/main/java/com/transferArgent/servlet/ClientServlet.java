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

@WebServlet("/Client/*")
public class ClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClientDAO clientDAO;

    public void init() {
        clientDAO = new ClientDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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
                    case "/new":
                        showNewForm(request, response);
                        break;
                    case "/insert":
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
                    case "/list":
                        listeClients(request, response);
                        break;
                    default:
                        listeClients(request, response);
                        break;
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            } catch (Exception ex) {
                throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
            }
        }
    }

    private void listeClients(HttpServletRequest req, HttpServletResponse res)
            throws SQLException, IOException, ServletException {
    	
    	
    	
        List<Client> listeClients = clientDAO.selectAllClients();
        req.setAttribute("listeClients", listeClients);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/client/client-liste.jsp");
        dispatcher.forward(req, res);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse res)
            throws SQLException, IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/client/client-form.jsp");
        dispatcher.forward(req, res);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse res)
            throws SQLException, IOException, ServletException {
        String numtel = req.getParameter("numtel");
        Client existingClient = clientDAO.selectClient(numtel);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/client/client-form.jsp");
        req.setAttribute("client", existingClient);
        dispatcher.forward(req, res);
    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String numtel = request.getParameter("numtel");
        String nom = request.getParameter("nom");
        String sexe = request.getParameter("sexe");
        String pays = request.getParameter("pays");
        float solde = Float.parseFloat(request.getParameter("solde"));
        String mail = request.getParameter("mail");
        float soldeFinal = 0.0f; // Initialise soldeFinal pour éviter les erreurs de non-initialisation

        /*switch (pays) {
            case "Madagascar":
                soldeFinal = solde / 4800;
                break;
            case "France":
                soldeFinal = solde; 
                break;
            case "Allemagne":
                soldeFinal = (float) (solde / 1.08);
                break;
            default:
                soldeFinal = solde; // Par défaut, aucune modification au solde initial
                break;
        }*/

        // Utilisation de soldeFinal selon les besoins
        //System.out.println("Solde final après traitement selon le pays : " + soldeFinal);
        
        Client newClient = new Client(numtel, nom, sexe, pays, solde, mail);
        clientDAO.insertClient(newClient);
        response.sendRedirect("list");
    }

    private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String numtel = request.getParameter("numtel");
        String nom = request.getParameter("nom");
        String sexe = request.getParameter("sexe");
        String pays = request.getParameter("pays");
        float solde = Float.parseFloat(request.getParameter("solde"));
        String mail = request.getParameter("mail");
        float soldeFinal = 0.0f;
        
        switch (pays) {
        case "Madagascar":
            soldeFinal = solde / 4800;
            break;
        case "France":
            soldeFinal = solde; 
            break;
        case "Allemagne":
            soldeFinal = (float) (solde / 1.08);
            break;
        default:
            soldeFinal = solde; // Par défaut, aucune modification au solde initial
            break;
    }
        
        Client updatedClient = new Client(numtel, nom, sexe, pays, soldeFinal, mail);
        clientDAO.updateClient(updatedClient);
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
        String numtel = request.getParameter("numtel");
        Client existingClient = clientDAO.selectClient(numtel);

        if (existingClient == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/client/client-not-found.jsp");
            return;
        }

        request.setAttribute("client", existingClient);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client/client-liste-byId.jsp");
        dispatcher.forward(request, response);
    }

    private void searchClients(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        String searchQuery = request.getParameter("searchQuery");
        
        if (searchQuery != null && !searchQuery.isEmpty()) {
            List<Client> clients = clientDAO.searchClients(searchQuery);
            request.setAttribute("listeClient", clients); // Utilisez "listeClient" pour la cohérence avec client-liste.jsp
        } else {
            List<Client> clients = clientDAO.selectAllClients(); // Ou une méthode pour récupérer tous les clients
            request.setAttribute("listeClient", clients); // Utilisez "listeClient" pour la cohérence avec client-liste.jsp
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client/clientSearch.jsp");
        dispatcher.forward(request, response);
    }

}
