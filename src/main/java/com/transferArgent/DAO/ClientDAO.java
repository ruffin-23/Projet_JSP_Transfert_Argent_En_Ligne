package com.transferArgent.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.transferArgent.model.Client;

import common.DB_Connection;

public class ClientDAO {

	private static final String INSERT_CLIENT_SQL = "INSERT INTO client (numtel, nom, sexe, pays, solde, mail) VALUES(?,?,?,?,?,?)";
	private static final String SELECT_CLIENT_BY_ID = "SELECT numtel, nom, sexe, pays, solde, mail FROM client WHERE numtel = ? ";
	private static final String SELECT_ALL_CLIENTS = "SELECT * FROM client";
	private static final String DELETE_CLIENT_SQL = "DELETE from client WHERE numtel = ?";
	private static final String UPDATE_CLIENT_SQL = "UPDATE client SET nom=?, sexe=?, pays=?, solde=?, mail=? WHERE numtel = ?";
	private static final String SEARCH_CLIENT_SQL = "SELECT * FROM CLIENT WHERE numtel LIKE ? OR nom LIKE ? OR pays LIKE ?";
	
	public ClientDAO () {
		
	}
	
	public void insertClient (Client client) throws SQLException {
		//System.out.println(INSERT_CLIENT_SQL);
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(INSERT_CLIENT_SQL)) {
			
			stmt.setString(1, client.getNumtel());
			stmt.setString(2, client.getNom());
			stmt.setString(3, client.getSexe());
			stmt.setString(4, client.getPays());
			stmt.setInt(5, client.getSolde());
			stmt.setString(6, client.getMail());
			
			//System.out.println(stmt);
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Client selectClient(String numtel) {
		Client client = null;
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_CLIENT_BY_ID);) {
			stmt.setString(1, numtel);
			//System.out.println("Executing SQL statement: " + stmt);
			
			ResultSet rs = stmt.executeQuery();
			
			while ( rs.next() ) {
				String nom = rs.getString("nom");
				String sexe = rs.getString("sexe");
				String pays = rs.getString("pays");
				int solde = rs.getInt("solde");
				String mail = rs.getString("mail");
				
				client = new Client(numtel, nom, sexe, pays, solde, mail);
				
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		
		return client;
	}
	
	
	public List<Client> selectAllClients() {
		
		List<Client> clients = new ArrayList<>();
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_CLIENTS);) {
			
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String numtel = rs.getString("numtel");
				String nom = rs.getString("nom");
				String sexe = rs.getString("sexe");
				String pays = rs.getString("pays");
				int solde = rs.getInt("solde");
				String mail = rs.getString("mail");
				
				clients.add(new Client(numtel, nom, sexe, pays, solde, mail));
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return clients;
		
	}
	
	
	public boolean deleteClient (String numtel) throws SQLException {
		
		boolean rowDelete;
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement( DELETE_CLIENT_SQL);) {
			
			stmt.setString(1, numtel);
			rowDelete = stmt.executeUpdate() > 0;	
		} 
		
		return rowDelete;
		
	}
	
	
	public boolean updateClient(Client client) throws SQLException {
		boolean rowUpdate;
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(UPDATE_CLIENT_SQL);) {
			
			stmt.setString(1, client.getNom());
			stmt.setString(2, client.getSexe());
			stmt.setString(3, client.getPays());
			stmt.setInt(4, client.getSolde());
			stmt.setString(5, client.getMail());
			stmt.setString(6, client.getNumtel());
			
			rowUpdate = stmt.executeUpdate() > 0;
			
		} 
		return rowUpdate;
		
	}
	
	
	public List<Client> searchClients(String searchKeyword) {
		
		List<Client> clients = new ArrayList<>();
		DB_Connection obj_DB_Connection = new DB_Connection();
		try (Connection connection = obj_DB_Connection.get_connection();
	            PreparedStatement stmt = connection.prepareStatement(SEARCH_CLIENT_SQL);) {
			
			 // Définir les paramètres pour la requête SQL
			String likeKeyword = "%" + searchKeyword + "%";
			stmt.setString(1, likeKeyword); //pour numtel
			stmt.setString(2, likeKeyword); //pour nom
			stmt.setString(3, likeKeyword); //pour pays
			
			System.out.println("Executing SQL statement: " + stmt); // Vérification de la requête SQL
			
			ResultSet rs = stmt.executeQuery();
			
			 while (rs.next()) {
		            String numtel = rs.getString("numtel");
		            String nom = rs.getString("nom");
		            String sexe = rs.getString("sexe");
		            String pays = rs.getString("pays");
		            int solde = rs.getInt("solde");
		            String mail = rs.getString("mail");
		            
		            // Créer un objet Client avec les données récupérées
		            Client client = new Client(numtel, nom, sexe, pays, solde, mail);
		            clients.add(client);
		        }
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return clients;
		
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
	
	
}
