package com.subtitlor.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.subtitlor.model.entity.Traduction;
import com.subtitlor.model.BeanException;
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
			preparedStatement = connexion.prepareStatement("INSERT INTO traduction(id,filename,sequence,language_str) VALUES(?, ?, ? , ?);");
			
			preparedStatement.setInt(1, trad.getId());
			preparedStatement.setString(2, trad.getFilename());
			preparedStatement.setString(3, trad.getSequence());
			preparedStatement.setString(4, trad.getLanguage());

			preparedStatement.executeUpdate();
			
			// Should be adding Strings now :
			for (String str : trad.getStrings()) {
				preparedStatement = connexion.prepareStatement("INSERT INTO strings(translated_str, traduction_id) VALUES(?, ?);");
				
				preparedStatement.setInt(2, trad.getId());
				preparedStatement.setString(1, str);
	
				preparedStatement.executeUpdate();
						
			}
			
			
			connexion.commit();	
			
			
		} catch (SQLException e) {
			try {

				// in case of problem we cancel the transaction :
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("Impossible de communiquer avec la base de donn�es");
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de donn�es");
			}
		}

	}

	@Override
	public List<Traduction> list() throws DaoException {
		
		List<Traduction> traductions = new ArrayList<Traduction>();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null, resultats = null;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT * FROM traduction");

			while (resultat.next()) {
				
				String fileName = resultat.getString("filename");
				String sequence = resultat.getString("sequence");
		
				int tradId = resultat.getInt("id");
				
				Traduction trad = new Traduction();
				trad.setFilename(fileName);
				trad.setSequence(sequence);
				trad.setId(tradId);
				
				// The strings now :
				PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM strings WHERE traduction_id = ?");
				preparedStatement.setInt(1, trad.getId());
				resultats = preparedStatement.getResultSet();
				String language = null;
				
				ArrayList<String> allStrings = new ArrayList<>();
				while (resultats.next()) {
					
					String string = resultat.getString("translated_str");
					language = resultat.getString("language_str");
					allStrings.add(string);
					
				}
				trad.setStrings(allStrings);
				trad.setLanguage(language);
				
				traductions.add(trad);
			}
		} 
		/*catch (BeanException e) {
			throw new DaoException("Les donn�es de la base sont invalides");
		}*/catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de donnees");
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de donnees");
			}
		}
		return traductions;
	}

}