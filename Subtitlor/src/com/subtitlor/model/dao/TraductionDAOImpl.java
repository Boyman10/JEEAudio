package com.subtitlor.model.dao;

import java.sql.*;
import java.util.ArrayList;

import com.subtitlor.model.entity.FileToTranslate;
import com.subtitlor.model.entity.Traduction;
import com.subtitlor.model.dao.factory.DaoFactory;

public class TraductionDAOImpl implements TraductionDAO {

	private DaoFactory daoFactory;

	public TraductionDAOImpl(DaoFactory daoFactory) {
		        this.daoFactory = daoFactory;
		    }

	@Override
	public void add(FileToTranslate ftr) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null, preparedStatementB = null;

		try {
			
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO file_translate(file_name,date_file) VALUES( ? , current_date)  RETURNING file_id"); 
					    
			preparedStatement.setString(1, ftr.getFileName());
			ResultSet rs = preparedStatement.executeQuery();

			int fid = rs.getInt("file_id"); 

			// Now up to sequences :
			for (Traduction trad : ftr.getSequences()) {
				
				preparedStatement = connexion.prepareStatement("INSERT INTO sequence_translate(sequence_details, file_id) VALUES(?, ?) RETURNING sequence_id");
				
				preparedStatement.setString(1, trad.getSequence());
				preparedStatement.setInt(2, fid);
					
				rs = preparedStatement.executeQuery();
				
				int seqId = rs.getInt("sequence_id");
				
				// Now the strings FRENCH 1st :
				for (String str : trad.getMapString().get("FRENCH")) {
					
					preparedStatementB = connexion.prepareStatement("INSERT INTO string_translate (sequence_id, language_string, content_string) VALUES (?,?)");
					
					preparedStatementB.setString(1, "FRENCH");
					preparedStatementB.setInt(1, seqId);
					preparedStatementB.setString(1, str);
					
					preparedStatementB.executeUpdate();
					
				}
				
				// then englich :
				for (String str : trad.getMapString().get("ENGLISH")) {
					
					preparedStatementB = connexion.prepareStatement("INSERT INTO string_translate (sequence_id, language_string, content_string) VALUES (?,?)");
					
					preparedStatementB.setString(1, "ENGLISH");
					preparedStatementB.setInt(1, seqId);
					preparedStatementB.setString(1, str);
					
					preparedStatementB.executeUpdate();
					
				}						
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
						
			throw new DaoException(e.getMessage());
			
			
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

	/**
	 * Retrieve translation data from filename if there is any
	 * @param filename
	 * @return
	 * @throws DaoException
	 */
	@Override
	public ArrayList<Traduction> list(String filename) throws DaoException {
		
	/*	ArrayList<Traduction> traductions = new ArrayList<Traduction>();
		String queryFilename = " WHERE filename IN (SELECT filename FROM traduction ORDER BY id DESC LIMIT 1);";
		
		if (filename != null && !filename.isEmpty())
			queryFilename = " WHERE filename = ?"; 
				
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null, resultats = null;

		try {
			 connexion = daoFactory.getConnection();
			 statement = connexion.prepareStatement("SELECT * FROM traduction" + queryFilename);
			 
			 System.out.println("SELECT * FROM traduction" + queryFilename);
			 
			 if (filename != null && !filename.isEmpty())
				statement.setString(1, filename);
			
			resultat = statement.executeQuery();
			
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
				resultats = preparedStatement.executeQuery();
				String language = null;
				language = resultat.getString("language_str");

				ArrayList<String> allStrings = new ArrayList<>();
				while (resultats.next()) {
					
					String string = resultats.getString("translated_str");
					allStrings.add(string);
					
				}
				trad.setStrings(allStrings);
				trad.setLanguage(language);
				
				traductions.add(trad);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de donnees");
			}
		}
			return traductions
		*/
		return null;
	}

}