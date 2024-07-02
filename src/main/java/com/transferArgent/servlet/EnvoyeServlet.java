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

//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfDocument;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.mysql.cj.xdevapi.Table;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.PdfDocument;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import com.transferArgent.DAO.ClientDAO;
import com.transferArgent.DAO.EnvoyeDAO;
import com.transferArgent.DAO.FraisDAO;
import com.transferArgent.model.Client;
import com.transferArgent.model.Envoye;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


@WebServlet("/envoye/*")
public class EnvoyeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EnvoyeDAO envoyeDAO;
	private ClientDAO clientDAO;
	private FraisDAO fraisDAO;
    
    public void init() {
        envoyeDAO = new EnvoyeDAO();
        clientDAO = new ClientDAO();
        fraisDAO = new FraisDAO();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response); // Forward POST requests to doGet
   
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//generatePdf(request, response);
		String path = request.getRequestURI();
		
	 	if (path.startsWith(request.getContextPath() + "/css") || 
                path.startsWith(request.getContextPath() + "/js")) {
            request.getRequestDispatcher(path).forward(request, response);
            return;
        } else {
        	
        	String action = request.getPathInfo();
        	
        	try  {
				switch (action) {
				
	        		case "/newE":
	        			showNewForm(request, response);
	        			break;
	        		case "/insertE":
	        			insertEnvoye(request, response);
	        			break;
	        		case "/editE":
	        			showEditForm(request, response);
	        			break;
	        		case "/search":
                        searchEnvoyes(request, response);
                        break;
	        		case "/readByIdE":
//	        			showEnvoyeById(request, response);
	        			break;
	        		case "/deleteE":
	        			deleteEnvoye(request, response);
	        			break;
	        		case "/listeE":
	        			listeEnvoye(request, response);
	        			break;
	        		case "/generatePdf":
	                    //generatePdf(request, response);
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
            throws SQLException, IOException, ServletException {
		

	 	//Récupérer la liste des clients avec numtel depuis la base de données
        List<Client> listNumTel = clientDAO.selectAllClients();
        System.out.println(listNumTel);
        // Ajouter la liste des clients comme attribut de la requête
        req.setAttribute("listNumTel", listNumTel);
		
		
		// Récupérer le numéro de téléphone depuis les paramètres de la requête
	    String numTel = req.getParameter("numtel");

	    // Ajouter le numéro de téléphone comme attribut de la requête
	    req.setAttribute("numtel", numTel);
		
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Envoye/envoye-form.jsp");
        dispatcher.forward(req, res);
    }
	
	private void insertEnvoye(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        try {
            System.out.println("Méthode insertEnvoye appelée");
            
            String idEnv = request.getParameter("idEnv");
            String numEnvoyeur = request.getParameter("numEnv");
            String numRecepteur = request.getParameter("numRec");
            float montant = Float.parseFloat(request.getParameter("montant"));
            String date = request.getParameter("date");
            String raison = request.getParameter("raison");
            
            Envoye newEnvoye = new Envoye(idEnv, numEnvoyeur, numRecepteur, montant, date, raison);
            envoyeDAO.insertEnvoye(newEnvoye);
            
            System.out.println("Taux inséré : " + newEnvoye);
            
            float montantInitialEnv;
            float montantFinalEnv;
            float montantInitialRec;
            float montantFinalRec;
            float frais;
            
            Client envoyeur = clientDAO.selectClient(numEnvoyeur);
            montantInitialEnv = envoyeur.getSolde();
            
            Client recepteur = clientDAO.selectClient(numRecepteur);
            montantInitialRec = recepteur.getSolde();
            
            frais = fraisDAO.selectFrais(montant);
            
            montantFinalEnv = montantInitialEnv - (montant + frais);
            montantFinalRec = montantInitialRec + montant;
            
            clientDAO.updateSoldeClient(numEnvoyeur, montantFinalEnv);
            clientDAO.updateSoldeClient(numRecepteur, montantFinalRec);
            
         // Envoi de la notification par e-mail
            sendNotificationEmail(numEnvoyeur, numRecepteur, montant);
            
            response.sendRedirect("listeE"); 
        } catch (NumberFormatException e) {
            throw new ServletException("Erreur lors de la conversion des montants en entier", e);
        }
    }
	
	private void listeEnvoye(HttpServletRequest req, HttpServletResponse res) 
            throws SQLException, IOException, ServletException {
        
        try {
            List<Envoye> listeEnvoye = envoyeDAO.selectAllEnvoye();
            req.setAttribute("listeEnv", listeEnvoye);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Envoye/envoye-liste.jsp");
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
        String idEnv = req.getParameter("idEnv");
        Envoye existingEnvoye = envoyeDAO.selectEnvoyeById(idEnv);
        System.out.println(existingEnvoye);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Envoye/envoye-form.jsp");
        req.setAttribute("envoye", existingEnvoye);
        dispatcher.forward(req, res);
    }

		
	private void deleteEnvoye(HttpServletRequest request, HttpServletResponse response) 
		throws SQLException, IOException {
		String idEnv = request.getParameter("idEnv");
		envoyeDAO.deleteEnvoye(idEnv);
		response.sendRedirect("listeE");
	}
	
	private void searchEnvoyes(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    String searchQuery = request.getParameter("searchQuery");
	    
	    
	        List<Envoye> envoyes;
	        if (searchQuery != null && !searchQuery.isEmpty()) {
	            envoyes = envoyeDAO.searchEnvoye(searchQuery);
	        } else {
	            envoyes = envoyeDAO.selectAllEnvoye();
	        }
	        
	        request.setAttribute("listeEnv", envoyes); // Utilisation de "listeEnv" pour la cohérence avec votre JSP
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Envoye/envoyeSearch.jsp");
	        dispatcher.forward(request, response);
	        
	}
	
	
	public void sendNotificationEmail(String numEnvoyeur, String numRecepteur, float montant) {
		
		final String username = "sedramanantsoajr@gmail.com"; // Remplacez par votre adresse e-mail
		final String password = "vjvfojkotsbhhyqj"; // Remplacez par votre mot de passe

		// Configuration des paramètres SMTP
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // Exemple : "smtp.example.com" pour d'autres fournisseurs
		props.put("mail.smtp.port", "587"); // Port SMTP pour Gmail
		props.put("mail.smtp.auth", "true"); // Authentification requise
		props.put("mail.smtp.starttls.enable", "true"); // Activation du STARTTLS
		
		// Création de la session
		Session session = Session.getInstance(props, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});
		
		try {
//		    Création du message e-mail
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(username)); // Votre adresse e-mail (expéditeur)
		    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sedramanantsoajr@gmail.com")); // Destinataire (numéro du récepteur)
		    message.setSubject("Notification de transfert d'argent");
		    message.setText("Bonjour,\n\nLe transfert d'argent du client avec le numéro " + numEnvoyeur +
		            " avec le montant: " + montant + " Euros à la numéro " + numRecepteur +
		            " a été effectué avec succès!");
		
		    // Envoi du message
		    Transport.send(message);
		
		    System.out.println("E-mail envoyé avec succès au destinataire: " + numRecepteur);
		
		} catch (MessagingException e) {
		    throw new RuntimeException(e);
		}
		
	}
	
}




















//private void showEnvoyeById(HttpServletRequest request, HttpServletResponse response)
//			throws SQLException, IOException, ServletException {
//
//String idEnv = request.getParameter("idEnv");
//Envoye existingEnvoye = envoyeDAO.selectEnvoyeById(idEnv);
//
//if (existingEnvoye == null) {
//  response.sendRedirect(request.getContextPath() + "/WEB-INF/Envoye/envoye-not-found.jsp");
//  return;
//}
//request.setAttribute("Envoye", existingEnvoye);
//RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Envoye/envoye-liste-byId.jsp");
//dispatcher.forward(request, response);
//}

//private void updateEnvoye(HttpServletRequest request, HttpServletResponse response) 
//throws SQLException, IOException {
//String idEnv = request.getParameter("idEnv");
//String numEnv = request.getParameter("numEnv");
//String numRec = request.getParameter("numRec");
//int montant = Integer.parseInt(request.getParameter("montant"));
//String date = request.getParameter("date");
//String raison = request.getParameter("raison");
//
//Envoye envoye = new Envoye(idEnv, numEnv, numRec, montant, date, raison);
//envoyeDAO.updateEnvoye(envoye);
//response.sendRedirect("listeE");
//}

//private void deleteEnvoye(HttpServletRequest request, HttpServletResponse response) 
//throws SQLException, IOException {
//String idEnv = request.getParameter("idEnv");
//envoyeDAO.deleteEnvoye(idEnv);
//response.sendRedirect("listeE");
//}

//private void showEnvoyeById(HttpServletRequest request, HttpServletResponse response)
//  throws SQLException, IOException, ServletException {
//
//String idEnv = request.getParameter("idEnv");
//Envoye existingEnvoye = envoyeDAO.selectEnvoyeById(idEnv);
//
//if (existingEnvoye == null) {
//  response.sendRedirect(request.getContextPath() + "/WEB-INF/Envoye/envoye-not-found.jsp");
//  return;
//}
//
//request.setAttribute("Envoye", existingEnvoye);
//RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Envoye/envoye-liste-byId.jsp");
//dispatcher.forward(request, response);
//}