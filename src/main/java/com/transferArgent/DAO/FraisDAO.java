package com.transferArgent.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.transferArgent.model.Frais;
import common.DB_Connection;

public class FraisDAO {

    private static final String INSERT_FRAIS_SQL = "INSERT INTO frais (idfrais, montant1, montant2, frais) VALUES (?, ?, ?, ?)";
    private static final String SELECT_FRAIS_BY_ID = "SELECT id, idfrais, montant1, montant2, frais FROM frais WHERE id = ?";
    private static final String SELECT_ALL_FRAIS = "SELECT id, idfrais, montant1, montant2, frais FROM frais";
    private static final String DELETE_FRAIS_SQL = "DELETE FROM frais WHERE id = ?";
    private static final String UPDATE_FRAIS_SQL = "UPDATE frais SET idfrais = ?, montant1 = ?, montant2 = ?, frais = ? WHERE id = ?";
    //private static final String SELECT_F = "SELECT frais FROM frais WHERE ? BETWEEN montant1 AND montant2";
    private static final String SELECT_F = "SELECT frais FROM frais LEFT JOIN taux ON taux.idtaux = frais.idfrais WHERE ? BETWEEN frais.montant1 AND frais.montant2";
    private static final String SOMME_FRAIS_ENVOIS = "INSERT INTO recetTotalOperateur(somfrenv) VALUES(?)";
    
    private DB_Connection obj_DB_Connection = new DB_Connection();
    
    public FraisDAO() {
    }

    public float selectFrais(float montant) throws SQLException {
        float frais = 0;
        try (Connection connection = obj_DB_Connection.get_connection(); 
             PreparedStatement stmt = connection.prepareStatement(SELECT_F)) {
            stmt.setFloat(1, montant);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                frais = rs.getFloat("frais");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return frais;
    }
    
    public void insertFrais(Frais frais) throws SQLException {
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_FRAIS_SQL)) {
            stmt.setString(1, frais.getIdfrais());
            stmt.setFloat(2, frais.getMontant1());
            stmt.setFloat(3, frais.getMontant2());
            stmt.setFloat(4, frais.getFrais());
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Frais selectFrais(int id) {
        Frais frais = null;
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_FRAIS_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String idfrais = rs.getString("idfrais");
                float montant1 = rs.getFloat("montant1");
                float montant2 = rs.getFloat("montant2");
                float fraisValue = rs.getFloat("frais");
                frais = new Frais(id, idfrais, montant1, montant2, fraisValue);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return frais;
    }

    public List<Frais> selectAllFrais() {
        List<Frais> fraisList = new ArrayList<>();
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_FRAIS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String idfrais = rs.getString("idfrais");
                float montant1 = rs.getFloat("montant1");
                float montant2 = rs.getFloat("montant2");
                float fraisValue = rs.getFloat("frais");
                Frais frais = new Frais(id, idfrais, montant1, montant2, fraisValue);
                fraisList.add(frais);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return fraisList;
    }

    public boolean deleteFrais(int id) throws SQLException {
        boolean rowDeleted = false;
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_FRAIS_SQL)) {
            stmt.setInt(1, id);
            rowDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    public boolean updateFrais(Frais frais) throws SQLException {
        boolean rowUpdated = false;
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_FRAIS_SQL)) {
            stmt.setString(1, frais.getIdfrais());
            stmt.setFloat(2, frais.getMontant1());
            stmt.setFloat(3, frais.getMontant2());
            stmt.setFloat(4, frais.getFrais());
            stmt.setInt(5, frais.getId());
            rowUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
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
