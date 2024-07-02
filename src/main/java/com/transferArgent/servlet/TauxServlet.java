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
    
    public void init() {
        tauxDAO = new TauxDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response); // Forward POST requests to doGet
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        if (path.startsWith(request.getContextPath() + "/css") || 
                path.startsWith(request.getContextPath() + "/js")) {
            request.getRequestDispatcher(path).forward(request, response);
            return;
        } else {
        
            String action = request.getPathInfo();
            
            try {
                
                switch(action) {
                    case "/newT":
                        System.out.println("tonga eto /newT");
                        showNewForm(request, response);
                        break;
                    case "/insertT":
                        System.out.println("tonga eto /insertT");
                        insertTaux(request, response);
                        break;
                    case "/deleteT":
                        System.out.println("tonga eto /deleteT");
                        deleteTaux(request, response);
                        break;
                    case "/editT":
                        System.out.println("tonga eto /editT");
                        showEditForm(request, response);
                        break;
                    case "/updateT":
                        System.out.println("tonga eto /updateT");
                        updateTaux(request, response);
                        break;
                    case "/readByIdT":
                        System.out.println("tonga eto /readByIdT");
                        showTauxById(request, response);
                        break;
                    case "/listeT":
                        System.out.println("tonga eto /listeT");
                        listeTaux(request, response);
                        break;
                    case "/search":
                        // Handle search request
                        break;
                    default:
                        // Handle unknown action
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        break;
                }
                
            } catch (SQLException ex) {
                throw new ServletException("Erreur lors de l'accès à la base de données", ex);
            } catch (Exception ex) {
                throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
            }
        }
        
    }
    
    private void insertTaux(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        try {
            System.out.println("Méthode insertTaux appelée");
            
            String idtaux = request.getParameter("idtaux");
            float montant1 = Float.parseFloat(request.getParameter("montant1"));
            float montant2 = Float.parseFloat(request.getParameter("montant2"));
            
            Taux newTaux = new Taux(idtaux, montant1, montant2);
            tauxDAO.insertTaux(newTaux);
            
            System.out.println("Taux inséré : " + newTaux);
            
            response.sendRedirect("listeT"); // Redirect to /listeT after successful insertion
        } catch (NumberFormatException e) {
            throw new ServletException("Erreur lors de la conversion des montants en entier", e);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'insertion du taux dans la base de données", e);
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse res) 
            throws SQLException, IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Taux/taux-form.jsp");
        dispatcher.forward(req, res);
    }
    
    private void listeTaux(HttpServletRequest req, HttpServletResponse res) 
            throws SQLException, IOException, ServletException {
        
        try {
            List<Taux> listeTaux = tauxDAO.selectAllTaux();
            req.setAttribute("listeT", listeTaux);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Taux/taux-liste.jsp");
            dispatcher.forward(req, res);
        } catch (IOException ex) {
            throw new ServletException("Erreur IO lors de la redirection vers la page JSP", ex);
        } catch (ServletException ex) {
            throw ex; 
        } catch (Exception ex) {
            throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
        }
        
    }
    
    private void showEditForm(HttpServletRequest req, HttpServletResponse res) 
            throws SQLException, IOException, ServletException {
        String idtaux = req.getParameter("idtaux");
        Taux existingTaux = tauxDAO.selectTaux(idtaux);
        System.out.println(existingTaux);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Taux/taux-form.jsp");
        req.setAttribute("taux", existingTaux);
        dispatcher.forward(req, res);
    }
    
    private void updateTaux(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String idTaux = request.getParameter("idtaux");
        float montant1 = Float.parseFloat(request.getParameter("montant1"));
        float montant2 = Float.parseFloat(request.getParameter("montant2"));

        Taux taux = new Taux(idTaux, montant1, montant2);
        tauxDAO.updateTaux(taux);
        response.sendRedirect("listeT");
    }
    
    private void deleteTaux(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String idtaux = request.getParameter("idtaux");
        tauxDAO.deleteTaux(idtaux);
        response.sendRedirect("listeT");
    }
    
    private void showTauxById(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        String idtaux = request.getParameter("idtaux");
        Taux existingTaux = tauxDAO.selectTaux(idtaux);
        
        if (existingTaux == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/Taux/taux-not-found.jsp");
            return;
        }
        
        request.setAttribute("taux", existingTaux);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Taux/taux-liste-byId.jsp");
        dispatcher.forward(request, response);
    }

}
