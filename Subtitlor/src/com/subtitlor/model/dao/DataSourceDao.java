package com.subtitlor.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import com.subtitlor.model.entity.Traduction;

/**
 * Using the JNDI DataSource provided by Tomcat 9
 * 
 * @link https://tomcat.apache.org/tomcat-9.0-doc/jndi-datasource-examples-howto.html
 * @author bob
 * @version 1.0
 */
public class DataSourceDao implements TraductionDAO {

	// Spring way to do that :
	// private NamedParameterJdbcTemplate jdbc ;

	// Using JDBC pool datasource from Tomcat
	DataSource ds;

	public DataSourceDao() {

		InitialContext cxt;
		try {
			cxt = new InitialContext();

			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");

			if (ds == null) {

				throw new Exception("Data source not found!");

			}

		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void add(Traduction trad) throws DaoException {
		// TODO Auto-generated method stub
//INSERT INTO file_translate( file_name,date_file) VALUES ( 'sdfgfdsg', '2001-10-05') RETURNING id;
	}

	@Override
	public ArrayList<Traduction> list(String filename) throws DaoException {

		ArrayList<Traduction> traductions = new ArrayList<Traduction>();
		String queryFilename = " ORDER BY date_file DESC LIMIT 1";
		
		if (filename != null && !filename.isEmpty())
			queryFilename = " WHERE file_name = ?"; 
				
		Connection connexion = null;
		PreparedStatement statement = null, preparedStatement = null;
		ResultSet resultat = null, resultats = null;

		try {
			// Replacing DAOFActory by DataSource here :
			 connexion = ds.getConnection();
			 statement = connexion.prepareStatement("SELECT * FROM file_translate" + queryFilename);
			 
			 System.out.println("SELECT * FROM file_translate" + queryFilename);
			 
			 if (filename != null && !filename.isEmpty())
				statement.setString(1, filename);
			
			resultat = statement.executeQuery();
			
			// Retrieve first and unique row :
			String fileName = resultat.getString("file_name");
			int fileId = resultat.getInt("id");

		    if (statement != null) {
			      statement.close(); 
			      statement = null;
			    }
		    
		    if (resultat != null) {
			      resultat.close();
			      resultat = null;
			    }
		    
			// now up to the sequence navigation :
			statement = connexion.prepareStatement("SELECT * FROM sequence_translate WHERE file_id = ?");
			statement.setInt(1, fileId);
			resultat = statement.executeQuery();
			
			while (resultat.next()) {
				
				
				String sequence = resultat.getString("sequence");
		
				int tradId = resultat.getInt("id");
				
				Traduction trad = new Traduction();
				trad.setFilename(fileName);
				trad.setSequence(sequence);
				trad.setId(tradId);
				
				// The strings now :
				preparedStatement  = connexion.prepareStatement("SELECT * FROM strings WHERE traduction_id = ?");
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
		/*catch (BeanException e) {
			throw new DaoException("Les donn�es de la base sont invalides");
		}*/catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				   // Always make sure result sets and statements are closed,
			    // and the connection is returned to the pool
			    if (resultat != null) {
			      resultat.close();
			      resultat = null;
			    }
			    if (resultats != null) {
				     resultats.close();
				      resultats = null;
				    }			    
			    if (statement != null) {
			      statement.close(); 
			      statement = null;
			    }
			    if (preparedStatement != null) {
				      preparedStatement.close(); 
				      preparedStatement = null;
				    }			    
			    if (connexion != null) {
			      try { connexion.close(); } catch (SQLException e) { ; }
			      connexion = null;
			    }
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de donnees");
			}
		}
		return traductions;
	}
	

}
