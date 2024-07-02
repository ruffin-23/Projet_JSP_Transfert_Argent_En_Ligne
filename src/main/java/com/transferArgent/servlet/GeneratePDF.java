package com.transferArgent.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.transferArgent.DAO.ClientDAO;
import com.transferArgent.DAO.EnvoyeDAO;
import com.transferArgent.model.Client;
import com.transferArgent.model.Envoye;

@WebServlet("/generatePDF")
public class GeneratePDF extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EnvoyeDAO envoyeDAO;
    private ClientDAO clientDAO;

    public void init() {
        envoyeDAO = new EnvoyeDAO();
        clientDAO = new ClientDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            String numEnv = request.getParameter("numEnv");
            List<Envoye> envoyes = envoyeDAO.selectpdf(numEnv);

            if (envoyes != null && !envoyes.isEmpty()) {
                // Récupérer les informations du client associé à cet envoyé (peut-être le premier envoyé)
                Envoye premierEnvoye = envoyes.get(0);
                Client client = clientDAO.selectClient(premierEnvoye.getNumEnvoyeur());

                // Extraire le mois et l'année de la date d'envoi (peut-être pour le premier envoyé)
                String date = premierEnvoye.getDate();
                String[] parts = date.split("-");
                String moisEtAnnee = convertirMoisTexte(date) + " " + parts[0]; // parts[1] contient le mois, parts[0] contient l'année

             // Créer un paragraphe pour la date avec mise en forme en gras et centré
                Paragraph dateParagraphe = new Paragraph();
                dateParagraphe.setAlignment(Element.ALIGN_CENTER); // Alignement au centre
                Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD); // Police en gras
                Chunk dateChunk = new Chunk("Date: " + moisEtAnnee, boldFont);
                dateParagraphe.add(dateChunk);

                // Ajouter la date au document PDF
                document.add(dateParagraphe);
                
                document.add(new Paragraph("\n"));
                
             // Créer un style de police en gras
                Font boldFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

                // Ajouter les informations du client avec les étiquettes en gras
                document.add(new Paragraph(new Chunk("Numéro de téléphone: ", boldFont2).toString() + client.getNumtel()));
                document.add(new Paragraph(new Chunk("Nom: ", boldFont2).toString() + client.getNom()));
                document.add(new Paragraph(new Chunk("Sexe: ", boldFont2).toString() + client.getSexe()));
                document.add(new Paragraph(new Chunk("Solde actuel: ", boldFont2).toString() + client.getSolde() + " Euros"));


                // Saut de ligne après les informations
                document.add(new Paragraph("\n"));

                // Créer le tableau PDF
                PdfPTable table = new PdfPTable(4); // 4 colonnes pour les attributs d'Envoye
                // Ajouter les en-têtes de colonne
                table.addCell("Date");
                table.addCell("Raison");
                table.addCell("Numéro du récepteur");
                table.addCell("Montant");

             // Définir le format de date souhaité
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                
                // Ajouter les données de tous les envoyés à une ligne du tableau
                float totalMontant = 0;
                for (Envoye envoye : envoyes) {
                	
                	// Formater la date au format jour/mois/année sans heure
                    //String formattedDate = dateFormat.format(envoye.getDate());
                	
                    table.addCell(envoye.getDate());
                    table.addCell(envoye.getRaison());
                    table.addCell(envoye.getNumRecepteur());
                    table.addCell(String.valueOf(envoye.getMontant()));
                    totalMontant += envoye.getMontant();
                }
                // Ajouter le tableau au document PDF
                document.add(table);
                
                document.add(new Paragraph("\n"));
                
                document.add(new Paragraph("Total Débit : " + totalMontant + " euros"));
            } else {
                throw new ServletException("Aucun envoyé trouvé avec le numéro d'envoyeur : " + numEnv);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }


    private String convertirMoisTexte(String date) {
        String[] parts = date.split("-");
        int mois = Integer.parseInt(parts[1]);

        switch (mois) {
            case 1:
                return "janvier";
            case 2:
                return "février";
            case 3:
                return "mars";
            case 4:
                return "avril";
            case 5:
                return "mai";
            case 6:
                return "juin";
            case 7:
                return "juillet";
            case 8:
                return "août";
            case 9:
                return "septembre";
            case 10:
                return "octobre";
            case 11:
                return "novembre";
            case 12:
                return "décembre";
            default:
                return "";
        }
    }
}
