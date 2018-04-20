package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.servlet.http.Part;

import com.subtitlor.model.dao.DaoException;
import com.subtitlor.model.dao.TraductionDAO;
import com.subtitlor.model.dao.TraductionDAOImpl;
import com.subtitlor.model.dao.factory.DaoFactory;
import com.subtitlor.model.entity.Traduction;

public class FileHandler {

	// A few configuration parameters for file upload :
	public static final int SIZE_TAMPON = 10240;
	
	// Storing folder from tmp to here :
	//public static final String FILE_FOLDER = "/home/bob/Downloads/";
	public static final String FILE_FOLDER = "C:\\Users\\John\\Downloads\\";
	
	private ArrayList<String> seqStrings= new ArrayList<>();
	private ArrayList<Traduction> translateFile = new ArrayList<>();
	
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
	            Traduction trad;
	            
	            int tempId = 0;
	            String tempSeq = "";
	            String line;
	            
	            while ((line = reader.readLine()) != null) {
	            	                        	
	            	System.err.println("String line : " + line);	
	            	
	            	// Starting over new Sequence
	            	if (newStart == 0) {
	            		newStart++;
	            		
	            		try {
	            		tempId = Integer.parseInt(line);
	            		} catch (NumberFormatException e) {
	            			System.err.println("What is this ? " + line);
	            		}
	            		
	            	} else if (newStart == 1) {
	            		
	            		// This is the sequence :
	            		tempSeq = line ;
	            		newStart++;
	            		
	            	} else if (line.isEmpty()) {
	            		
	            		trad = new Traduction(tempId,tempSeq,"FRENCH",(ArrayList<String>) seqStrings.clone(), fileName);
	            		translateFile.add(trad);
	            		
	            		
		            	
	            		// We start over :
	            		newStart = 0;
	            		seqStrings.clear();
	            		
	            	} else {
	            		newStart++;
	            		seqStrings.add(line);
	            	}
	            		        
	            	
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
		        // Now place to DAO :
		        for (Traduction tradInstance : translateFile) {
		        	
		        	DaoFactory daoF = DaoFactory.getInstance();
		        	TraductionDAO tradDAO = daoF.getTraductionDao();
		        	
		        	// TeST output :
	            	System.out.println(tradInstance);	      
		        		
		        	tradDAO.add(tradInstance);
						
					
		        	
		        }
	        
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
