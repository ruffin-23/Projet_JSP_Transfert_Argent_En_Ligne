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
import com.transferArgent.DAO.EnvoyeDAO;
import com.transferArgent.DAO.FraisDAO;
import com.transferArgent.DAO.SommeFraiDAO;
import com.transferArgent.model.Client;
import com.transferArgent.model.Envoye;
import com.transferArgent.model.SommeFraisEnvoye;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@WebServlet("/envoye/*")
public class EnvoyeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EnvoyeDAO envoyeDAO;
    private ClientDAO clientDAO;
    private FraisDAO fraisDAO;
    private SommeFraiDAO sommeFraiDAO;

    public void init() {
        envoyeDAO = new EnvoyeDAO();
        clientDAO = new ClientDAO();
        fraisDAO = new FraisDAO();
        sommeFraiDAO = new SommeFraiDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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
                    case "/deleteE":
                        deleteEnvoye(request, response);
                        break;
                    case "/listeE":
                        listeEnvoye(request, response);
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
        List<Client> listNumTel = clientDAO.selectAllClients();
        req.setAttribute("listNumTel", listNumTel);
        String numTel = req.getParameter("numtel");
        req.setAttribute("numtel", numTel);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/Envoye/envoye-form.jsp");
        dispatcher.forward(req, res);
    }

    private void insertEnvoye(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        try {
            String idEnv = request.getParameter("idEnv");
            String numEnvoyeur = request.getParameter("numEnv");
            String numRecepteur = request.getParameter("numRec");
            float montant = Float.parseFloat(request.getParameter("montant"));
            String date = request.getParameter("date");
            String raison = request.getParameter("raison");

            Envoye newEnvoye = new Envoye(idEnv, numEnvoyeur, numRecepteur, montant, date, raison);
            envoyeDAO.insertEnvoye(newEnvoye);

            Client envoyeur = clientDAO.selectClient(numEnvoyeur);
            float montantInitialEnv = envoyeur.getSolde();

            Client email1 = clientDAO.selectMail(numEnvoyeur);
            Client email2 = clientDAO.selectMail(numRecepteur);

            String mail1 = email1.getMail();
            String mail2 = email2.getMail();

            Client recepteur = clientDAO.selectClient(numRecepteur);
            float montantInitialRec = recepteur.getSolde();

            float frais = fraisDAO.selectFrais(montant);

            SommeFraisEnvoye newSommeFrais = new SommeFraisEnvoye(frais);
            sommeFraiDAO.insertRecetOperateur(newSommeFrais);

            float montantFinalEnv = montantInitialEnv - (montant + frais);
            float montantFinalRec = montantInitialRec + montant;

            clientDAO.updateSoldeClient(numEnvoyeur, montantFinalEnv);
            clientDAO.updateSoldeClient(numRecepteur, montantFinalRec);

            // Envoi de la notification par e-mail
            sendNotificationEmail(mail1, mail2, numEnvoyeur, numRecepteur, montant);

            response.sendRedirect("listeE");
        } catch (NumberFormatException e) {
            throw new ServletException("Erreur lors de la conversion des montants en entier", e);
        }
    }

    private void listeEnvoye(HttpServletRequest req, HttpServletResponse res)
            throws SQLException, IOException, ServletException {
        try {
            List<Envoye> listeEnvoye = envoyeDAO.selectAllEnvoye();
            float sommeFraiEnvoye = sommeFraiDAO.selectSumFrais();
            req.setAttribute("listeEnv", listeEnvoye);
            req.setAttribute("sommefrais", sommeFraiEnvoye);
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

        request.setAttribute("listeEnv", envoyes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Envoye/envoyeSearch.jsp");
        dispatcher.forward(request, response);
    }

    public void sendNotificationEmail(String emailEnvoyeur, String emailRecepteur, String numEnvoyeur, String numRecepteur, float montant) {
        final String username = "sedramanantsoajr@gmail.com";
        final String password = "vjvfojkotsbhhyqj";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message messageEnvoyeur = new MimeMessage(session);
            messageEnvoyeur.setFrom(new InternetAddress(username));
            messageEnvoyeur.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEnvoyeur));
            messageEnvoyeur.setSubject("Notification de transfert d'argent");
            messageEnvoyeur.setText("Bonjour,\n\nVotre transfert d'argent a été effectué avec succès !\n\nDétails du transfert :\n- Montant : " + montant + " Euros\n- Destinataire : " + numRecepteur +
                    "\n\nMerci d'utiliser notre service de transfert d'argent.\n\nCordialement,\nVotre service de transfert d'argent");

            Transport.send(messageEnvoyeur);
            System.out.println("E-mail envoyé avec succès à l'envoyeur: " + emailEnvoyeur);

            Message messageRecepteur = new MimeMessage(session);
            messageRecepteur.setFrom(new InternetAddress(username));
            messageRecepteur.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecepteur));
            messageRecepteur.setSubject("Notification de réception d'argent");
            messageRecepteur.setText("Bonjour,\n\nVous avez reçu un transfert d'argent !\n\nDétails du transfert :\n- Montant : " + montant + " Euros\n- Expéditeur : " + numEnvoyeur +
                    "\n\nMerci d'utiliser notre service de transfert d'argent.\n\nCordialement,\nVotre service de transfert d'argent");

            Transport.send(messageRecepteur);
            System.out.println("E-mail envoyé avec succès au récepteur: " + emailRecepteur);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
