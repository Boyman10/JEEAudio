package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.Part;

import com.subtitlor.model.entity.Traduction;

public class FileHandler {

	// A few configuration parameters for file upload :
	public static final int SIZE_TAMPON = 10240;
	
	// Storing folder from tmp to here :
	public static final String FILE_FOLDER = "/home/bob/Downloads/";
	
	
	private ArrayList<String> seqStrings= new ArrayList<>();
	private ArrayList<Traduction> translateFile = new ArrayList<>();
	
	/**
	 * Initialy writing to file the uploaded temp translation srt
	 * now adding to DB
	 * @param part
	 * @param fileName
	 * @throws IOException
	 */
	   public void writeFile( Part part, String fileName) throws IOException {
	        BufferedInputStream entree = null;
	        //BufferedOutputStream sortie = null;
	        
	        
	    	//File myFile = new File(FILE_FOLDER, fileName);

	        try {
	            entree = new BufferedInputStream(part.getInputStream(), SIZE_TAMPON);
	          //  sortie = new BufferedOutputStream(new FileOutputStream(myFile), SIZE_TAMPON);

	            byte[] tampon = new byte[SIZE_TAMPON];
	            int longueur;
	            short newStart = 0;
	            Traduction trad;
	            
	            int tempId = 0;
	            String tempSeq = "";
	            
	            while ((longueur = entree.read(tampon)) > 0) {
	            
	            	String line = new String(tampon, 0, longueur);
	                        	
	            	System.out.println("String line : " + line);	
	            	
	            	// Starting over new Sequence
	            	if (newStart == 0) {
	            		newStart++;
	            		
	            		try {
	            			tempId = Integer.parseInt(line);
	            			
	            		
	            		} catch(Exception e) {
	            			
	            			e.printStackTrace();
	            		}
	            	} else if (newStart == 1) {
	            		
	            		// This is the sequence :
	            		tempSeq = line ;
	            		newStart++;
	            		
	            	} else if (line.isEmpty()) {
	            		
	            		trad = new Traduction(tempId,tempSeq,"FRENCH",seqStrings);
	            		translateFile.add(trad);
	            		
	            		// TeST output :
		            	System.out.println(trad);	      
		            	
	            		// We start over :
	            		newStart = 0;
	            		seqStrings.clear();
	            		
	            	} else {
	            		newStart++;
	            		seqStrings.add(line);
	            	}
	            		            
	            	
	            	
	            	
	            	
	            //    sortie.write(tampon, 0, longueur);
	            }
	        } finally {
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
