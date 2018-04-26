package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.subtitlor.model.dao.DaoException;
import com.subtitlor.model.dao.TraductionDAO;
import com.subtitlor.model.dao.factory.DaoFactory;
import com.subtitlor.model.entity.FileToTranslate;
import com.subtitlor.model.entity.Traduction;

public class FileHandler {

	// A few configuration parameters for file upload :
	public static final int SIZE_TAMPON = 10240;
	static final Logger logger = LogManager.getLogger();
	// Storing folder from tmp to here :
	public static final String FILE_FOLDER = "/home/bob/Downloads/";
	//public static final String FILE_FOLDER = "C:\\Users\\John\\Downloads\\";
	
	private FileToTranslate fTrad;
	private ArrayList<String> seqStrings= new ArrayList<>();
	private ArrayList<Traduction> translateSeq = new ArrayList<>();
	
	/**
	 * Initialy writing to file the uploaded temp translation srt
	 * now adding to DB
	 * @param part
	 * @param fileName
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void writeFile( Part part, String fileName) throws IOException {
		   
	        BufferedInputStream entree = null;
	        //BufferedOutputStream sortie = null;	        
	        
	    	//File myFile = new File(FILE_FOLDER, fileName);

	        try {
	            entree = new BufferedInputStream(part.getInputStream(), SIZE_TAMPON);
	          //  sortie = new BufferedOutputStream(new FileOutputStream(myFile), SIZE_TAMPON);

	            // Set up the read of our buffered input 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(entree, StandardCharsets.UTF_8));
	            
	            short newStart = 0;
	            
	            
	            fTrad = new FileToTranslate();	            
	            fTrad.setFileName(fileName);
	            
	            
	            int tempId = 0;
	            String tempSeq = "";
	            String line;
	            
	            while ((line = reader.readLine()) != null) {
	            	                        	
	            	logger.debug("String line : " + line);	
	            	
	            	// Starting over new Sequence
	            	if (newStart == 0) {
	            		newStart++;
	            		
	            		try {
	            		tempId = Integer.parseInt(line);
	            		} catch (NumberFormatException e) {
	            			logger.debug("What is this ? " + line);
	            		}
	            		
	            	} else if (newStart == 1) {
	            		
	            		// This is the sequence :
	            		tempSeq = line ;
	            		newStart++;
	            		
	            	} else if (line.isEmpty()) {
	            		
	            		HashMap<String,ArrayList<String>> hMap = new HashMap<>();
	            		
	            		hMap.put("FRENCH",(ArrayList<String>) seqStrings.clone() );
	            		
	            		Traduction trad = new Traduction(tempSeq,hMap);
	            		translateSeq.add(trad);
	            		
	            		// We start over :
	            		newStart = 0;
	            		seqStrings.clear();
	            		
	            	} else {
	            		newStart++;
	            		seqStrings.add(line);
	            	}
	            		        
	            	// Finally filling in the File object :
	            	fTrad.setSequences(translateSeq);
	            	
	            //    sortie.write(tampon, 0, longueur);
	            }
	        }
	        
	        catch(Exception e) {
    			
    			e.printStackTrace();
    		}
	        finally {
	            /*try {
	                sortie.close();
	            } catch (IOException ignore) {
	            }
	            */
	            try {
	                entree.close();
	            } catch (IOException ignore) {
	            }
	        }
	        try {
	        	
	        	
		        	
		        	DaoFactory daoF = DaoFactory.getInstance();
		        	TraductionDAO tradDAO = daoF.getTraductionDao();
		        	tradDAO.add(fTrad);
		        	
	        
	        } catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	    
	   public String getFileName( Part part ) {
	        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	            if ( contentDisposition.trim().startsWith( "filename" ) ) {
	                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
	            }
	        }
	        return null;
	    }   
	    
	    
}
