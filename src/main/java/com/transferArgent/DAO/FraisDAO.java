package com.transferArgent.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.transferArgent.model.Frais;
import com.transferArgent.model.Taux;

import common.DB_Connection;

public class FraisDAO {

	private static final String INSERT_FRAIS_SQL = "INSERT INTO frais (idfrais, montant1, montant2, frais) VALUES (?, ?, ?, ?)";
	private static final String SELECT_FRAIS_BY_ID = "SELECT idfrais, montant1, montant2, frais FROM frais WHERE idfrais = ?";
	private static final String SELECT_ALL_FRAIS = "SELECT * FROM frais";
	private static final String DELETE_FRAIS_SQL = "DELETE FROM frais WHERE idfrais = ?";
	private static final String UPDATE_FRAIS_SQL = "UPDATE frais SET montan1 = ?, montant2 = ? frais = ? WHERE idfrais =?";
	
	public FraisDAO () {
		
	}
	
	public void insertFrais (Frais frais) throws SQLException {
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(INSERT_FRAIS_SQL);) {
			
			stmt.setString(1, frais.getIdfrais());
			stmt.setInt(2, frais.getMontant1());
			stmt.setInt(3, frais.getMontant2());
			stmt.setInt(4, frais.getFrais());
			stmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Frais selectFrais (String idfrais) {
		Frais frais = null;
		
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		try (Connection connection = obj_DB_Connection.get_connection();
				PreparedStatement stmt = connection.prepareStatement(SELECT_FRAIS_BY_ID);) {
			
			stmt.setString(1, idfrais);
			ResultSet rs = stmt.executeQuery();
			
			while ( rs.next() ) {
				int montant1 = rs.getInt("montant1");
				int montant2 = rs.getInt("montant2");
				int frai = rs.getInt("frais");
				
				frais = new Frais(idfrais, montant1, montant2, frai);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return frais;
		
	}
	
}
