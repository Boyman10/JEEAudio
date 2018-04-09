package com.subtitlor.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.subtitlor.model.entity.Traduction;
import com.subtitlor.model.dao.factory.DaoFactory;

public class TraductionDAOImpl implements TraductionDAO {

	private DaoFactory daoFactory;

	public TraductionDAOImpl(DaoFactory daoFactory) {
		        this.daoFactory = daoFactory;
		    }

	@Override
	public void add(Traduction trad) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO noms(nom, prenom) VALUES(?, ?);");
			
			preparedStatement.setString(1, trad.getName());
			preparedStatement.setString(2, trad.getFirstname());

			preparedStatement.executeUpdate();
			connexion.commit();
		} catch (SQLException e) {
			try {

				// in case of problem we cancel the transaction :
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("Impossible de communiquer avec la base de données");
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données");
			}
		}

	}

	@Override
	public List<Traduction> list() throws DaoException {
		
		List<Traduction> traductions = new ArrayList<Traduction>();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT nom, prenom FROM noms;");

			while (resultat.next()) {
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");

				Traduction trad = new Traduction();
				trad.setName(nom);
				trad.setFirstname(prenom);

				traductions.add(trad);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données");
		} catch (BeanException e) {
			throw new DaoException("Les données de la base sont invalides");
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données");
			}
		}
		return traductions;
	}

}