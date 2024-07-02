package com.transferArgent.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.transferArgent.model.Envoye;

import common.DB_Connection;

public class EnvoyeDAO {
	
	public static final String INSERT_ENVOYE_SQL = "INSERT INTO envoyer(idEnv, numEnvoyeur, numRecepteur, montant, date, raison) VALUES(?,?,?,?,?,?)";
	public static final String SELECT_ENVOYE_BY_ID = "SELECT idEnv, numEnvoyeur, numRecepteur, montant, date, raison FROM envoyer WHERE idEnv = ?";
	public static final String SELECT_ALL_ENVOYE = "SELECT * FROM envoyer";
	public static final String DELETE_ENVOYE_SQL = "DELETE FROM envoyer WHERE idEnv=?";
//	private static final String SELECT_ALL_NUM_TEL = "SELECT DISTINCT numtel FROM client";
	private static final String SELECT_FOR_PDF = "SELECT date, numRecepteur, montant, raison FROM envoyer WHERE numEnvoyeur = ?";
	private static final String SEARCH_ENVOYE_SQL = "Select * from envoyer where date LIKE ? ";
	
	public EnvoyeDAO () {
		
	}
	
	
	public List<Envoye> searchEnvoye(String searchKeyword) {
		
		List<Envoye> envoyes = new ArrayList<>();
		DB_Connection obj_DB_Connection = new DB_Connection();
		try (Connection connection = obj_DB_Connection.get_connection();
	            PreparedStatement stmt = connection.prepareStatement(SEARCH_ENVOYE_SQL);) {
			
			 // Définir les paramètres pour la requête SQL
			String likeKeyword = "%" + searchKeyword + "%";
			stmt.setString(1, likeKeyword); //pour date
			
			System.out.println("Executing SQL statement: " + stmt); // Vérification de la requête SQL
			
			ResultSet rs = stmt.executeQuery();
			
			 while (rs.next()) {
				 	String idEnv = rs.getString("idEnv");
					String numEnvoyeur = rs.getString("numEnvoyeur");
					String numRecepteur = rs.getString("numRecepteur");
					int montant = rs.getInt("montant");
					String date = rs.getString("date");
					String raison = rs.getString("raison");
		            
		            // Créer un objet envoye avec les données récupérées
					Envoye envoye  = new Envoye(idEnv, numEnvoyeur, numRecepteur, montant, date, raison);
					envoyes.add(envoye);
		        }
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return envoyes;
		
	}
	
	
	public  List<Envoye> selectpdf(String numEnvoyeur) {
		List<Envoye> envoyes = new ArrayList<>();
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		try (Connection connection  = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_FOR_PDF);) {
			
			stmt.setString(1, numEnvoyeur);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String numRecepteur = rs.getString("numRecepteur");
				float montant = rs.getFloat("montant");
				String date = rs.getString("date");
				String raison  = rs.getString("raison");
				
				Envoye envoye  = new Envoye( numEnvoyeur, numRecepteur, montant, date, raison);
				envoyes.add(envoye);
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return envoyes;
	}
	

	
	public void insertEnvoye(Envoye envoye) {
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(INSERT_ENVOYE_SQL);) {
			
			stmt.setString(1, envoye.getIdEnv());
			stmt.setString(2, envoye.getNumEnvoyeur());
			stmt.setString(3, envoye.getNumRecepteur());
			stmt.setFloat(4, envoye.getMontant());
			stmt.setString(5, envoye.getDate());
			stmt.setString(6, envoye.getRaison());
			
			System.out.println(stmt);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Envoye> selectAllEnvoye() {
		List<Envoye> envoyes = new ArrayList<>();
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		try ( Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_ENVOYE); ) {
			
			ResultSet rs = stmt.executeQuery();
			
			while( rs.next() ) {
				String idEnv = rs.getString("idEnv");
				String numEnvoyeur = rs.getString("numEnvoyeur");
				String numRecepteur = rs.getString("numRecepteur");
				int montant = rs.getInt("montant");
				String date = rs.getString("date");
				String raison = rs.getString("raison");
				
				envoyes.add(new Envoye(idEnv, numEnvoyeur, numRecepteur, montant, date, raison));
			}			
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return envoyes;
	}
	
	
	public Envoye selectEnvoyeById(String idEnv) {
		Envoye envoye = null;
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		try (Connection connection  = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_ENVOYE_BY_ID);) {
			
			stmt.setString(1, idEnv);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String numEnvoyeur = rs.getString("numEnvoyeur");
				String numRecepteur = rs.getString("numRecepteur");
				int montant = rs.getInt("montant");
				String date = rs.getString("date");
				String raison  = rs.getString("raison");
				
				envoye  = new Envoye(idEnv, numEnvoyeur, numRecepteur, montant, date, raison);
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return envoye;
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
	public boolean deleteEnvoye(String idEnv) throws SQLException {
	
	DB_Connection obj_DB_Connection = new DB_Connection();
	boolean rowDeleted = false;
	try (Connection connection = obj_DB_Connection.get_connection();
	     PreparedStatement stmt = connection.prepareStatement(DELETE_ENVOYE_SQL)) {
	
	    stmt.setString(1, idEnv);
	    rowDeleted = stmt.executeUpdate() > 0;
	
	} catch (SQLException e) {
	    printSQLException(e);
	}
	return rowDeleted;
	}
	
}



































//public static final String UPDATE_ENVOYE_SQL = "UPDATE envoyer SET numEnvoyeur=?, numRecepteur=?, montant=?, date=?, raison=? WHERE idEnv=?";
//public static final String DELETE_ENVOYE_SQL = "DELETE FROM envoyer WHERE idEnv=?";
//public static final String SEARCH_ENVOYE_SQL = "SELECT * FROM envoyer WHERE date=?";


//public boolean updateEnvoye(Envoye envoye) throws SQLException {
//boolean rowUpdated = false;
//
//DB_Connection obj_DB_Connection = new DB_Connection();
//
//try (Connection connection = obj_DB_Connection.get_connection();
//		PreparedStatement stmt = connection.prepareStatement(UPDATE_ENVOYE_SQL);) {
//	stmt.setString(1, envoye.getIdEnv());
//	stmt.setString(2, envoye.getNumEnvoyeur());
//	stmt.setString(3, envoye.getNumRecepteur());
//	stmt.setInt(4, envoye.getMontant());
//	stmt.setString(5, envoye.getDate());
//	stmt.setString(6, envoye.getRaison());
//	
//	rowUpdated = stmt.executeUpdate() > 0;
//} catch (SQLException e) {
//	printSQLException(e);
//}
//return rowUpdated;
//}


//public boolean deleteEnvoye(String idEnv) throws SQLException {
//
//DB_Connection obj_DB_Connection = new DB_Connection();
//boolean rowDeleted = false;
//try (Connection connection = obj_DB_Connection.get_connection();
//     PreparedStatement stmt = connection.prepareStatement(DELETE_ENVOYE_SQL)) {
//
//    stmt.setString(1, idEnv);
//    rowDeleted = stmt.executeUpdate() > 0;
//
//} catch (SQLException e) {
//    printSQLException(e);
//}
//return rowDeleted;
//}


//public List<String> selectAllNumRecepteur() {
//List<String> numRecepteurs = new ArrayList<>();
//
//DB_Connection obj_DB_Connection = new DB_Connection();
//try (Connection connection = obj_DB_Connection.get_connection();
//     PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_NUM_RECEPTEUR);) {
//    
//    ResultSet rs = stmt.executeQuery();
//    
//    while (rs.next()) {
//        String numRecepteur = rs.getString("numRecepteur");
//        numRecepteurs.add(numRecepteur);
//    }
//    
//} catch (SQLException e) {
//    printSQLException(e);
//}
//return numRecepteurs;
//}