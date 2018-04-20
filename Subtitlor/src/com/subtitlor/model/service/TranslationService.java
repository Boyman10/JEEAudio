package com.subtitlor.model.service;

import java.util.ArrayList;

import com.subtitlor.model.dao.DaoException;
import com.subtitlor.model.dao.TraductionDAO;
import com.subtitlor.model.dao.factory.DaoFactory;
import com.subtitlor.model.entity.Traduction;

/**
 * Service manager to retrieve our DAO depending on request
 * @author bob
 * @version 1.0
 */
public class TranslationService {

	DaoFactory dao;
	
	
	/**
	 * Constructor retrieve the instance of the db connection
	 */
	public TranslationService() {
		
		this.dao = DaoFactory.getInstance();
	}
	
	/**
	 * SErvice method to get the last stored data
	 * @throws DaoException 
	 */
	public ArrayList<Traduction> getLastEntries() {
		
		TraductionDAO tradDAO = dao.getTraductionDao();
		
		try {
			return tradDAO.list(null);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
