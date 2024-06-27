package com.transferArgent.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.transferArgent.model.Taux;

import common.DB_Connection;

public class TauxDAO {

	private static final String INSERT_TAUX_SQL = "INSERT INTO taux (idtaux, montant1, montant2) VALUES (?, ?, ?)";
	private static final String SELECT_TAUX_BY_ID = "SELECT idtaux, montant1, montant2 FROM taux WHERE idtaux = ?";
	private static final String SELECT_ALL_TAUX = "SELECT * FROM taux";
	private static final String DELETE_TAUX_SQL = "DELETE FROM taux WHERE idtaux = ?";
	private static final String UPDATE_TAUX_SQL = "UPDATE taux SET montan1 = ?, montant2 = ? WHERE idtaux =?";
	
	public TauxDAO () {
		
	}
	
	public void insertTaux(Taux taux) throws SQLException {
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(INSERT_TAUX_SQL)) {
			
			stmt.setString(1, taux.getIdtaux());
			stmt.setInt(2, taux.getMontant1());
			stmt.setInt(3, taux.getMontant2());
			
			//System.out.println(stmt);
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Taux selectTaux(String idtaux) {
		Taux taux = null;
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_TAUX_BY_ID);) {
			stmt.setString(1, idtaux);
			//System.out.println("Executing SQL statement: " + stmt);
			
			ResultSet rs = stmt.executeQuery();
			
			while ( rs.next() ) {
				int montant1 = rs.getInt("montant1");
				int montant2 = rs.getInt("montant2");
				
				taux = new Taux(idtaux, montant1, montant2);
				
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		
		return taux;
	}
	
	
	public List<Taux> selectAllTaux() {
		
		List<Taux> tauxs = new ArrayList<>();
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_TAUX);) {
			
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String idtaux = rs.getString("idtaux");
				int montant1 = rs.getInt("montant1");
				int montant2 = rs.getInt("montant2");
				
				
				tauxs.add(new Taux(idtaux, montant1, montant2));
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return tauxs;
		
	}
	
	
	public boolean deleteTaux (String idtaux) throws SQLException {
		
		boolean rowDelete;
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement( DELETE_TAUX_SQL);) {
			
			stmt.setString(1, idtaux);
			rowDelete = stmt.executeUpdate() > 0;	
		} 
		
		return rowDelete;
		
	}
	
	
	public boolean updateTaux(Taux taux) throws SQLException {
		boolean rowUpdate;
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(UPDATE_TAUX_SQL);) {
			
			stmt.setInt(1, taux.getMontant1());
			stmt.setInt(2, taux.getMontant2());
			
			rowUpdate = stmt.executeUpdate() > 0;
			
		} 
		return rowUpdate;
		
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
