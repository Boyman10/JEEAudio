package com.subtitlor.model.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.subtitlor.model.dao.TraductionDAO;
import com.subtitlor.model.dao.TraductionDAOImpl;

public class DaoFactory {
	
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
           // Class.forName("com.mysql.jdbc.Driver");
        	 Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {

        }

        //DaoFactory instance = new DaoFactory(
          //      "jdbc:mysql://localhost:3306/struts-app", "test", "1234");
        
        	DaoFactory instance = new DaoFactory(
        	                "jdbc:postgresql://127.0.0.1:5432/struts_app", "test", "debian");
        	
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(url, username, password);
        connexion.setAutoCommit(false);
        return connexion; 
    }

    // R�cup�ration du Dao
    public TraductionDAO getTraductionDao() {
        return new TraductionDAOImpl(this);
    }
}