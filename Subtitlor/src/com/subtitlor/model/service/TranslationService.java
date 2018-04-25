package com.subtitlor.model.service;

import java.util.ArrayList;

import com.subtitlor.model.dao.DaoException;
import com.subtitlor.model.dao.DataSourceDao;
//import com.subtitlor.model.dao.TraductionDAO;
import com.subtitlor.model.dao.factory.DaoFactory;
import com.subtitlor.model.entity.Traduction;

/**
 * Service manager to retrieve our DAO depending on request
 * @author bob
 * @version 1.0
 */
public class TranslationService {

	@Deprecated
	DaoFactory dao;
	DataSourceDao dt;
	
	/**
	 * Constructor retrieve the instance of the db connection
	 */
	public TranslationService() {
		
		//this.dao = DaoFactory.getInstance();
		this.dt  = new DataSourceDao();
	}
	
	/**
	 * SErvice method to get the last stored data
	 * @throws DaoException 
	 */
	public ArrayList<Traduction> getLastEntries(String filename) {
		
		//TraductionDAO tradDAO = dao.getTraductionDao();

		try {
			//return tradDAO.list(null);
			return dt.list(filename);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
