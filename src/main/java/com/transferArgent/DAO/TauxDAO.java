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
    private static final String SELECT_ALL_TAUX = "SELECT idtaux, montant1, montant2 FROM taux";
    private static final String DELETE_TAUX_SQL = "DELETE FROM taux WHERE idtaux = ?";
    private static final String UPDATE_TAUX_SQL = "UPDATE taux SET montant1 = ?, montant2 = ? WHERE idtaux = ?";

    public TauxDAO() {

    }

    DB_Connection obj_DB_Connection = new DB_Connection();
    
    public void insertTaux(Taux taux) throws SQLException {
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_TAUX_SQL)) {

            stmt.setString(1, taux.getIdtaux());
            stmt.setFloat(2, taux.getMontant1());
            stmt.setFloat(3, taux.getMontant2());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'insertion du taux dans la base de données", e);
        }
    }

    public Taux selectTaux(String idtaux) throws SQLException {
        Taux taux = null;
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_TAUX_BY_ID)) {

            stmt.setString(1, idtaux);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                float montant1 = rs.getFloat("montant1");
                float montant2 = rs.getFloat("montant2");

                taux = new Taux(idtaux, montant1, montant2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération du taux depuis la base de données", e);
        }
        return taux;
    }

    public List<Taux> selectAllTaux() throws SQLException {
        List<Taux> tauxs = new ArrayList<>();
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_TAUX)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idtaux = rs.getString("idtaux");
                float montant1 = rs.getFloat("montant1");
                float montant2 = rs.getFloat("montant2");

                Taux taux = new Taux(idtaux, montant1, montant2);
                tauxs.add(taux);
            }

        } catch (SQLException e) {
        	printSQLException(e);
            throw new SQLException("Erreur lors de la récupération de tous les taux depuis la base de données", e);
        }
        return tauxs;
    }

    public boolean deleteTaux(String idtaux) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_TAUX_SQL)) {

            stmt.setString(1, idtaux);
            rowDeleted = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la suppression du taux depuis la base de données", e);
        }
        return rowDeleted;
    }

    public boolean updateTaux(Taux taux) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = obj_DB_Connection.get_connection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_TAUX_SQL)) {

            stmt.setFloat(1, taux.getMontant1());
            stmt.setFloat(2, taux.getMontant2());
            stmt.setString(3, taux.getIdtaux());

            rowUpdated = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la mise à jour du taux dans la base de données", e);
        }
        return rowUpdated;
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
