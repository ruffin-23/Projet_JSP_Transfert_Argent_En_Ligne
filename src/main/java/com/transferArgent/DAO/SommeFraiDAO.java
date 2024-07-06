package com.transferArgent.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.transferArgent.model.SommeFraisEnvoye;

import common.DB_Connection;

public class SommeFraiDAO {
	
	 private static final String SOMME_FRAIS_ENVOIS = "insert into recetTotalOperateur(somfrenv) VALUES(?)";
	 private static final String selectSumFrais = "Select sum(somfrenv) as totalFrais from recetTotalOperateur";
	 
	 public SommeFraiDAO() {
		 
	 }
	 
	 DB_Connection obj_DB_Connection = new DB_Connection();
	 
   public float selectSumFrais() {
 	float totalFrais = 0.0f;
     try (Connection connection = obj_DB_Connection.get_connection();
          PreparedStatement stmt = connection.prepareStatement(selectSumFrais)) {
         ResultSet rs = stmt.executeQuery();
         System.out.println(stmt);
         if (rs.next()) {
             totalFrais = rs.getFloat("totalFrais");
         }

     } catch (SQLException e) {
         printSQLException(e);
     }
     return totalFrais;
    
 }
	 
	    public void insertRecetOperateur(SommeFraisEnvoye fraisEnv) throws SQLException {
	        try (Connection connection = obj_DB_Connection.get_connection();
	             PreparedStatement stmt = connection.prepareStatement(SOMME_FRAIS_ENVOIS)) {

	            stmt.setFloat(1, fraisEnv.getSomfrenv());
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }
	
	    private void printSQLException(SQLException ex) {
	        ex.printStackTrace();
	        System.err.println("SQLState: " + ex.getSQLState());
	        System.err.println("Error Code: " + ex.getErrorCode());
	        System.err.println("Message: " + ex.getMessage());
	        Throwable t = ex.getCause();
	        while (t != null) {
	            System.out.println("Cause: " + t);
	            t = t.getCause();
	        }
	    }
	    
}
