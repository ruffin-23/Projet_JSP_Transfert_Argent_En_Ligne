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

import com.transferArgent.DAO.FraisDAO;
import com.transferArgent.DAO.TauxDAO;
import com.transferArgent.model.Frais;
import com.transferArgent.model.Taux;

@WebServlet("/frais/*")
public class FraisServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FraisDAO fraisDAO;
    private TauxDAO tauxDAO;

    public void init() {
        fraisDAO = new FraisDAO();
        tauxDAO = new TauxDAO();
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
                    case "/newF":
                        showNewForm(request, response);
                        break;
                    case "/insertF":
                        insertFrais(request, response);
                        break;
                    case "/deleteF":
                        deleteFrais(request, response);
                        break;
                    case "/editF":
                        showEditForm(request, response);
                        break;
                    case "/updateF":
                        updateFrais(request, response);
                        break;
                    case "/readByIdF":
                        showFraisById(request, response);
                        break;
                    case "/listF":
                        listFrais(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/frais/listF");
                        break;
                }

            } catch (SQLException ex) {
                throw new ServletException("Erreur lors de l'accès à la base de données", ex);
            } catch (Exception ex) {
                throw new ServletException("Une erreur s'est produite lors du traitement de la requête", ex);
            }
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, SQLException {
        List<Taux> listIdTaux = tauxDAO.selectAllTaux();
        req.setAttribute("listIdTaux", listIdTaux);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Frais/frais-form.jsp");
        dispatcher.forward(req, res);
    }

    private void insertFrais(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String idfrais = request.getParameter("idfrais");
        float montant1 = Float.parseFloat(request.getParameter("montant1"));
        float montant2 = Float.parseFloat(request.getParameter("montant2"));
        float frais = Float.parseFloat(request.getParameter("frais"));

        Frais newFrais = new Frais(0, idfrais, montant1, montant2, frais); // id = 0 pour nouvel enregistrement
        fraisDAO.insertFrais(newFrais);
        response.sendRedirect("listF");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        Frais existingFrais = fraisDAO.selectFrais(id);
        req.setAttribute("frais", existingFrais); // Transfert de l'objet Frais à la vue
        List<Taux> listIdTaux = tauxDAO.selectAllTaux();
        req.setAttribute("listIdTaux", listIdTaux); // Assurez-vous que listIdTaux est disponible dans la vue
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Frais/frais-form.jsp");
        dispatcher.forward(req, res);
    }

    private void updateFrais(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String idfrais = request.getParameter("idfrais");
        float montant1 = Float.parseFloat(request.getParameter("montant1"));
        float montant2 = Float.parseFloat(request.getParameter("montant2"));
        float frais = Float.parseFloat(request.getParameter("frais"));

        Frais updatedFrais = new Frais(id, idfrais, montant1, montant2, frais);
        fraisDAO.updateFrais(updatedFrais);
        response.sendRedirect("listF");
    }

    private void deleteFrais(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        fraisDAO.deleteFrais(id);
        response.sendRedirect("listF");
    }

    private void listFrais(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Frais> listeFrais = fraisDAO.selectAllFrais();
        request.setAttribute("listeF", listeFrais);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Frais/frais-liste.jsp");
        dispatcher.forward(request, response);
    }

    private void showFraisById(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Frais existingFrais = fraisDAO.selectFrais(id);

        if (existingFrais == null) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/Frais/frais-not-found.jsp");
            return;
        }

        request.setAttribute("frais", existingFrais);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Frais/frais-liste-byId.jsp");
        dispatcher.forward(request, response);
    }
}
