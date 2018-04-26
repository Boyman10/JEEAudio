package com.subtitlor.model.dao;

import java.util.ArrayList;

import com.subtitlor.model.entity.FileToTranslate;
import com.subtitlor.model.entity.Traduction;

/**
 * Data Access Object for our traduction instances
 * @author John
 *
 */
public interface TraductionDAO {


	void add( Traduction trad, FileToTranslate ftr) throws DaoException;
	ArrayList<Traduction> list(String filename) throws DaoException;

}
